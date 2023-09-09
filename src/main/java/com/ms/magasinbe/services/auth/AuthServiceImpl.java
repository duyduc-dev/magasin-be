package com.ms.magasinbe.services.auth;

import com.ms.magasinbe.common.enums.SystemStatus;
import com.ms.magasinbe.common.enums.TypeVerifyAccount;
import com.ms.magasinbe.common.enums.UserRole;
import com.ms.magasinbe.common.exceptions.ApplicationException;
import com.ms.magasinbe.common.utils.*;
import com.ms.magasinbe.configs.security.JwtService;
import com.ms.magasinbe.controllers.modals.request.ForgotPasswordRequest;
import com.ms.magasinbe.controllers.modals.request.LoginRequest;
import com.ms.magasinbe.controllers.modals.request.PhoneNumberRequest;
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

import java.util.Date;

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
  @Autowired
  AuthHelper authHelper;

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
    Validator.notNull(user, RestAPIStatus.INVALID_AUTHENTICATE_CREDENTIAL, ConstantData.USER_NOT_FOUND);
    if (!passwordEncoder.matches(loginRequest.getPasswordHash().trim().concat(user.getPasswordSalt()), user.getPasswordHash())) {
      throw new ApplicationException(RestAPIStatus.INVALID_AUTHENTICATE_CREDENTIAL, ConstantData.WRONG_PASSWORD);
    }
    String token = jwtService.generateToken(user, valueConfigure.JWT_EXPIRATION);

    return TokenResponse.builder()
            .accessToken(token)
            .tokenExpired(jwtService.getExpirationDateFromToken(token))
            .build();
  }

  /**
   * @param forgotPasswordRequest
   * @return
   */
  @Override
  public TokenResponse forgotPassword(String activeCode, ForgotPasswordRequest forgotPasswordRequest) {
    User user = userRepository.findFirstByActiveCodeAndStatus(activeCode, SystemStatus.ACTIVE);
    Validator.notNull(user, RestAPIStatus.NOT_FOUND, ConstantData.USER_NOT_FOUND, ParamError.USER_NOT_FOUND);
    if(DateUtil.convertToUTC(new Date()).getTime() >= user.getExpiryDate()) {
      user.setActiveCode(null);
      user.setExpiryDate(null);
      userRepository.save(user);
      throw new ApplicationException(RestAPIStatus.EXPIRED, ConstantData.CANNOT_FORGOT_PASSWORD, ParamError.FORGOT_PASSWORD_EXPIRED);
    }
    Validator.mustEquals(forgotPasswordRequest.getPasswordHash(), forgotPasswordRequest.getConfirmPasswordHash(), RestAPIStatus.BAD_REQUEST,ConstantData.PASSWORD_NOT_MATCH, ParamError.PASSWORD_NOT_MATCH);
    PasswordHash pw = authHelper.createPasswordHash(forgotPasswordRequest.getConfirmPasswordHash());
    user.setActiveCode(null);
    user.setExpiryDate(null);
    user.setPasswordSalt(pw.getPasswordSalt());
    user.setPasswordHash(pw.getPasswordHash());
    user = userRepository.save(user);
    String authToken = jwtService.generateToken(user, valueConfigure.JWT_EXPIRATION);
    return TokenResponse.builder()
            .accessToken(authToken)
            .tokenExpired(jwtService.getExpirationDateFromToken(authToken))
            .build();
  }

  /**
   * @param userId
   * @return
   */
  @Override
  public User getAuthUser(String userId) {
    boolean isSave = false;
    User user = userService.getUserById(userId);
    Validator.notNull(user, RestAPIStatus.NOT_FOUND, ConstantData.USER_NOT_FOUND, ParamError.USER_NOT_FOUND);
    if(user.getOtpCode() != null && DateUtil.convertToUTC(new Date()).getTime() >= user.getOtpExpiryDate()) {
      isSave = true;
      user.setOtpCode(null);
      user.setOtpExpiryDate(0);
    }
    if(user.getActiveCode() != null && DateUtil.convertToUTC(new Date()).getTime() >= user.getExpiryDate()) {
      isSave = true;
      user.setActiveCode(null);
      user.setExpiryDate(null);
    }
    if(isSave) {
      user = userRepository.save(user);
    }
    return user;
  }

  /**
   * @param phoneNumberRequest
   * @return
   */
  @Override
  public void checkPhoneNumberSignup(PhoneNumberRequest phoneNumberRequest) {
    Validator.validatePhoneNumber(phoneNumberRequest.getPhoneNumber().trim());
    User user = userRepository.findFirstByPhoneNumberAndStatus(phoneNumberRequest.getPhoneNumber().trim(), SystemStatus.ACTIVE);
    Validator.mustNull(user, RestAPIStatus.EXISTED, ConstantData.PHONE_NUMBER_ALREADY_EXISTED);
  }


  private void validateEmailAndPhoneNumberNotExists(String email, String phoneNumber) {
    // Check format email & phone number
    Validator.validatePhoneNumber(phoneNumber.trim());

    User userEmail = userRepository.findFirstByEmailAndStatus(email, SystemStatus.ACTIVE);
    Validator.mustNull(userEmail, RestAPIStatus.EXISTED, ConstantData.EMAIL_ALREADY_EXISTED);

    User userPhone = userRepository.findFirstByPhoneNumberAndStatus(phoneNumber, SystemStatus.ACTIVE);
    Validator.mustNull(userPhone, RestAPIStatus.EXISTED, ConstantData.PHONE_NUMBER_ALREADY_EXISTED);
  }

}
