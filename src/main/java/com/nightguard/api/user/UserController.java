package com.nightguard.api.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseToken;
import com.nightguard.api.dto.UserResponse;

@RestController
@RequestMapping("/users")
public class UserController {

  @GetMapping("/me")
  public ResponseEntity<UserResponse> getMe(Authentication authentication) {
    FirebaseToken token = (FirebaseToken) authentication.getDetails();
    return ResponseEntity.ok(UserResponse.fromToken(token));
  }
}
