package base.api.user.internal.controller;

import base.api.user.UserDTO;
import base.api.user.UserService;
import base.api.user.internal.dto.LoginRequest;
import base.api.user.internal.dto.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  UserService userService;
  private Logger log = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/{id}")
  UserDTO getUserInfo(@PathVariable("id") Long id) {
    // TODO implement
    return null;
  }

  // Upsert
  @PostMapping("/{id}")
  UserDTO updateUserInfo(@PathVariable("id") Long id) {
    // TODO implement
    return null;
  }

  // Delete delete (soft delete ?)
  @DeleteMapping("/{id}")
  Long deleteUser(@PathVariable("id") Long id) {
    // TODO implement
    return id;
  }

  @PostMapping("/register")
  UserDTO createUser(@RequestBody UserDTO newUserDTO) {
    UserDTO createdUser = null;
    try {
      createdUser = userService.createUser(newUserDTO);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("Error creating new user");
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return createdUser;
  }

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest loginRequest) {
    Optional<String> jwtToken = userService.authenticateUserByPassword(loginRequest.loginName(), loginRequest.password());
    if (jwtToken.isPresent()) {
      return new LoginResponse(jwtToken.get());
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
  }

  @GetMapping("/hello")
  public String hello() {
    var authen = SecurityContextHolder.getContext().getAuthentication();
    String name = authen.getName();
    var authorities = authen.getAuthorities();
//    String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(String::valueOf).findFirst().get();
    return "hello " + name + " with role " + authorities.stream().toList().get(0).getAuthority();
  }
}
