package com.ms.magasinbe.common.utils;


import com.ms.magasinbe.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {

  @Autowired
  PasswordEncoder passwordEncoder;

  public boolean checkPassword(String passwordHash, User user) {
    return passwordEncoder.matches(
            passwordHash.trim().concat(user.getPasswordSalt()), user.getPasswordHash());
  }

  public String generatePasswordHash(String passwordHash, User user) {
    return passwordEncoder.encode(passwordHash.trim().concat(user.getPasswordSalt()));
  }
}
