package com.ms.magasinbe.services.auth;

import com.ms.magasinbe.common.enums.SystemStatus;
import com.ms.magasinbe.common.enums.UserRole;
import com.ms.magasinbe.common.exceptions.ApplicationException;
import com.ms.magasinbe.common.utils.ApplicationValueConfigure;
import com.ms.magasinbe.common.utils.RestAPIStatus;
import com.ms.magasinbe.common.utils.Validator;
import com.ms.magasinbe.configs.security.JwtService;
import com.ms.magasinbe.controllers.modals.request.LoginRequest;
import com.ms.magasinbe.controllers.modals.request.SignupRequest;
import com.ms.magasinbe.controllers.modals.response.TokenResponse;
import com.ms.magasinbe.entities.Role;
import com.ms.magasinbe.entities.User;
import com.ms.magasinbe.repositories.RoleRepository;
import com.ms.magasinbe.repositories.UserRepository;
import com.ms.magasinbe.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  UserRepository userRepository;
  @Autowired
  RoleRepository roleRepository;
  @Autowired
  UserService userService;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  JwtService jwtService;
  @Autowired
  ApplicationValueConfigure valueConfigure;

  /**
   * @param signupRequest
   * @param userRole
   * @return
   */
  @Override
  public TokenResponse signupAccount(SignupRequest signupRequest, UserRole userRole) {
    validateEmailAndPhoneNumberNotExists(signupRequest.getEmail(), signupRequest.getPhoneNumber());
    Role role = roleRepository.findByRoleTypeAndStatus(userRole, SystemStatus.ACTIVE);
    User user = userService.createUser(signupRequest, userRole);

    if(role != null) {
      user.setRoleId(role.getId());
    }
    user = userRepository.save(user);
    String token = jwtService.generateToken(user, valueConfigure.JWT_EXPIRATION);
    return TokenResponse.builder()
            .accessToken(token)
            .tokenExpired(jwtService.getExpirationDateFromToken(token))
            .build();
  }

  /**
   * @param loginRequest
   * @param userRole
   * @return
   */
  @Override
  public TokenResponse loginAccount(LoginRequest loginRequest, UserRole userRole) {
    String username = loginRequest.getUsername();
    boolean isEmail = Validator.isEmailFormat(username);
    boolean isPhone = Validator.isPhoneNumber(username);
    User user = null;
    if(isEmail) {
      user = userRepository.findFirstByEmailAndStatus(username, SystemStatus.ACTIVE);
    } else if(isPhone) {
      user = userRepository.findFirstByPhoneNumberAndStatus(username, SystemStatus.ACTIVE);
    } else {
      user = userRepository.findByUsernameAndStatus(username, SystemStatus.ACTIVE);
    }
    Validator.notNull(user, RestAPIStatus.INVALID_AUTHENTICATE_CREDENTIAL, "User not found");
    if (!passwordEncoder.matches(loginRequest.getPasswordHash().trim().concat(user.getPasswordSalt()), user.getPasswordHash())) {
      throw new ApplicationException(RestAPIStatus.INVALID_AUTHENTICATE_CREDENTIAL, "Wrong password");
    }
    String token = jwtService.generateToken(user, valueConfigure.JWT_EXPIRATION);

    return TokenResponse.builder()
            .accessToken(token)
            .tokenExpired(jwtService.getExpirationDateFromToken(token))
            .build();
  }

  /**
   * @param userId
   * @return
   */
  @Override
  public User getAuthUser(String userId) {
    return userService.getUserById(userId);
  }


  private void validateEmailAndPhoneNumberNotExists(String email, String phoneNumber) {
    // Check format email & phone number
    Validator.validatePhoneNumber(phoneNumber.trim());

    User userEmail = userRepository.findFirstByEmailAndStatus(email, SystemStatus.ACTIVE);
    Validator.mustNull(userEmail, RestAPIStatus.EXISTED, "Email already existed");

    User userPhone = userRepository.findFirstByPhoneNumberAndStatus(phoneNumber, SystemStatus.ACTIVE);
    Validator.mustNull(userPhone, RestAPIStatus.EXISTED, "Phone Number already existed");
  }

}
