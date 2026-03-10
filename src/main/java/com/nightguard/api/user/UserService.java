package com.nightguard.api.user;

import org.springframework.stereotype.Service;

import com.nightguard.api.dto.UpdateUserRequest;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findOrCreate(String uid, String email) {
    return userRepository.findById(uid).orElseGet(() -> {
      User user = new User();
      user.setId(uid);
      user.setEmail(email);
      return userRepository.save(user);
    });
  }

  public User getByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND));
  }

  public User update(String uid, String email, UpdateUserRequest request) {
    User user = findOrCreate(uid, email);
    if (request.getFirstName() != null)
      user.setFirstName(request.getFirstName());
    if (request.getLastName() != null)
      user.setLastName(request.getLastName());
    if (request.getEmail() != null)
      user.setEmail(request.getEmail());
    if (request.getPhoneNumber() != null)
      user.setPhoneNumber(request.getPhoneNumber());
    return userRepository.save(user);
  }
}
