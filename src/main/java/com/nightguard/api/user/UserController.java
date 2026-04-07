package com.nightguard.api.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseToken;
import com.nightguard.api.dto.UpdateFcmTokenRequest;
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
    FirebaseToken token = (FirebaseToken) authentication.getDetails();
    User user = userService.findOrCreate(token.getUid(), token.getEmail());
    return ResponseEntity.ok(UserResponse.fromUser(user));
  }

  @PostMapping("/me")
  public ResponseEntity<UserResponse> updateMe(
      Authentication authentication,
      @RequestBody UpdateUserRequest request) {
    FirebaseToken token = (FirebaseToken) authentication.getDetails();
    User user = userService.update(token.getUid(), token.getEmail(), request);
    return ResponseEntity.ok(UserResponse.fromUser(user));
  }

  @GetMapping("/{email}")
  public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
    return ResponseEntity.ok(UserResponse.fromUser(userService.getByEmail(email)));
  }

  @PutMapping("/me/fcm-token")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateFcmToken(
      Authentication authentication,
      @RequestBody UpdateFcmTokenRequest request) {
    FirebaseToken token = (FirebaseToken) authentication.getDetails();
    userService.updateFcmToken(token.getUid(), request.getToken());
  }
}
