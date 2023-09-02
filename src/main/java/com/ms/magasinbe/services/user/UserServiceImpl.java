package com.ms.magasinbe.services.user;

import com.ms.magasinbe.common.enums.SystemStatus;
import com.ms.magasinbe.common.enums.UserRole;
import com.ms.magasinbe.common.utils.*;
import com.ms.magasinbe.controllers.modals.request.SignupRequest;
import com.ms.magasinbe.entities.User;
import com.ms.magasinbe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  UserRepository userRepository;

  /**
   * @param signupRequest
   * @return
   */
  @Override
  public User createUser(SignupRequest signupRequest, UserRole userRole) {
    String passwordSalt = AppUtil.generateSalt();
    String passwordHash = passwordEncoder.encode(signupRequest.getPasswordHash().trim().concat(passwordSalt));
    return User.builder()
            .username(UniqueID.getUUID())
            .id(UniqueID.getUUID())
            .email(signupRequest.getEmail())
            .userRole(userRole)
            .phoneNumber(signupRequest.getPhoneNumber().trim())
            .lastName(signupRequest.getLastName())
            .middleName(signupRequest.getMiddleName())
            .firstName(signupRequest.getFirstName())
            .passwordHash(passwordHash)
            .passwordSalt(passwordSalt)
            .status(SystemStatus.ACTIVE)
            .build();
  }

  /**
   * @param id
   * @return
   */
  @Override
  public User getUserById(String id) {
    User user = userRepository.findByIdAndStatus(id, SystemStatus.ACTIVE);
    Validator.notNull(user, RestAPIStatus.NOT_FOUND, ParamError.USER_NOT_FOUND);
    return user;
  }
}
