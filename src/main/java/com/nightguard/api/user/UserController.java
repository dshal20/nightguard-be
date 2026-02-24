package com.nightguard.api.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nightguard.api.dto.UpdateUserRequest;
import com.nightguard.api.dto.UserResponse;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  public ResponseEntity<UserResponse> getMe(Authentication authentication) {
    String uid = (String) authentication.getPrincipal();
    User user = userService.findOrCreate(uid);
    return ResponseEntity.ok(UserResponse.fromUser(user));
  }

  @PostMapping("/me")
  public ResponseEntity<UserResponse> updateMe(
      Authentication authentication,
      @RequestBody UpdateUserRequest request) {
    String uid = (String) authentication.getPrincipal();
    User user = userService.update(uid, request);
    return ResponseEntity.ok(UserResponse.fromUser(user));
  }
}
