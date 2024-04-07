package base.api.user.internal.controller;

import base.api.user.UserDto;
import base.api.user.UserService;
import base.api.user.internal.dto.LoginRequest;
import base.api.user.internal.dto.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  UserService userService;
  private Logger log = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/{id}")
  UserDto getUserInfo(@PathVariable("id") Long id) {
    // TODO implement
    return null;
  }

  @GetMapping("/myInfo")
  ResponseEntity<UserDto> getMyUserInfo(Authentication auth) {
    if (auth == null) return new ResponseEntity<>(null, HttpStatusCode.valueOf(403));
    Long userId = Long.valueOf( (String) auth.getPrincipal());

    return ResponseEntity.ok(userService.getUserById(userId));
  }

  // Upsert
  @PostMapping("/{id}")
  UserDto updateUserInfo(@PathVariable("id") Long id) {
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
  ResponseEntity createUser(@RequestBody UserDto newUserDto) {
    UserDto createdUser = null;
    try {
      createdUser = userService.createUser(newUserDto);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("Error creating new user");
      return new ResponseEntity("Error creating new user because reasons", HttpStatus.CONFLICT);
    }
    return ResponseEntity.ok(createdUser);
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
