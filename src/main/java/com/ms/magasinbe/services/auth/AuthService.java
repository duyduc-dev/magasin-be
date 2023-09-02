package com.ms.magasinbe.services.auth;

import com.ms.magasinbe.common.enums.UserRole;
import com.ms.magasinbe.controllers.modals.request.LoginRequest;
import com.ms.magasinbe.controllers.modals.request.SignupRequest;
import com.ms.magasinbe.controllers.modals.response.TokenResponse;
import com.ms.magasinbe.entities.User;
import com.ms.magasinbe.services.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface AuthService {
  TokenResponse signupAccount(SignupRequest signupRequest, UserRole userRole);

  TokenResponse loginAccount(LoginRequest loginRequest, UserRole userRole);

  User getAuthUser(String userId);
}
