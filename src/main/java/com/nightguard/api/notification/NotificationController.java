package com.nightguard.api.notification;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nightguard.api.user.UserRepository;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

  private final UserRepository userRepository;

  public NotificationController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping("/register-device")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void registerDevice(
      @RequestBody RegisterDeviceRequest request,
      Authentication authentication) {
    com.nightguard.api.user.User user = userRepository.findById(authentication.getName())
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND));
    user.setFcmToken(request.getFcmToken());
    userRepository.save(user);
  }
}
