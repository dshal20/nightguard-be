package com.nightguard.api.user;

import org.springframework.stereotype.Service;

import com.nightguard.api.dto.UpdateUserRequest;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findOrCreate(String uid) {
    return userRepository.findById(uid).orElseGet(() -> {
      User user = new User();
      user.setId(uid);
      return userRepository.save(user);
    });
  }

  public User update(String uid, UpdateUserRequest request) {
    User user = findOrCreate(uid);
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
