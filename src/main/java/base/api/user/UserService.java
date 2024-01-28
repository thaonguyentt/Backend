package base.api.user;

import base.api.common.CommonValidator;
import base.api.system.security.JwtService;
import base.api.user.internal.entity.User;
import base.api.user.internal.entity.UserPassword;
import base.api.user.internal.mapper.UserMapper;
import base.api.user.internal.repository.UserPasswordRepository;
import base.api.user.internal.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

  private final Logger log = LoggerFactory.getLogger(UserService.class);

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final UserPasswordRepository userPasswordRepository;
  private final JwtService jwtService;

  @Autowired
  public UserService(
    UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder,
    UserPasswordRepository userPasswordRepository, JwtService jwtService) {
    this.userMapper = userMapper;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userPasswordRepository = userPasswordRepository;
    this.jwtService = jwtService;
  }

  public UserDTO getUserById(Long userId) {
    User user = userRepository.getReferenceById(userId);
    UserDTO userDTO = userMapper.userDTOFromUser(user);
    return userDTO;
  }

  @Transactional
  public UserDTO createUser(UserDTO userDTO) {
    // TODO validate input
    User user = userMapper.userFromUserDTO(userDTO);
    User savedUser = userRepository.save(user);
    String encodedPassword = passwordEncoder.encode(userDTO.password());
    UserPassword userPassword = new UserPassword(null, savedUser.getId(), encodedPassword);
    userPasswordRepository.save(userPassword);
    UserDTO savedUserDTO = userMapper.userDTOFromUser(savedUser);
    return savedUserDTO;
  }

  // Authorize user edit his own data, and admins
  @Transactional
  public UserDTO editUser(UserDTO userDTO) {
    // validate input ??
    User editingUser = userMapper.userFromUserDTO(userDTO);
    User editedUser = userRepository.saveAndFlush(editingUser);
    return userMapper.userDTOFromUser(editedUser);
  }

  private Optional<User> getUserByLoginName(String loginName) {
    if (StringUtils.isBlank(loginName)) {
      return Optional.empty();
    }

    // check login name is phone number, email or username
    if (CommonValidator.isEmail(loginName)) {
      // if email => get user by email
      return userRepository.getByEmail(loginName);
    } else if (CommonValidator.isVnMobilePhoneNumber(loginName)) {
      // if phone number => get user by phone number
      return userRepository.getByPhoneNumber(loginName);
    } else {
      return userRepository.getByUsername(loginName);
    }
  }

  private boolean validatePassword(Long userId, String password) {
    if (StringUtils.isBlank(password)) {
      return false;
    }
    Optional<UserPassword> userPasswordMaybe = userPasswordRepository.getByUserId(userId);

    var o1 = userPasswordMaybe
      .map(userPassword -> passwordEncoder.matches(password, userPassword.getPassword()));
    var o2 = o1.orElse(false);
    return o2;
  }

  /**
   * @param loginName Login name (username or email or phone number)
   * @param password Password
   * @return JWT token
   */
  public Optional<String> authenticateUserByPassword(String loginName, String password) {
    var o1 = getUserByLoginName(loginName);
    var o2 = o1.filter(user -> validatePassword(user.getId(), password));
    var o3 = o2.map(user -> jwtService.makeTokenWithUserIdAndRoles(user.getId(), Collections.singleton("NONE")));
    return o3;
  }

}
