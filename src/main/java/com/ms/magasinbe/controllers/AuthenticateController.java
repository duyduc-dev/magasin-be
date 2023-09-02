package com.ms.magasinbe.controllers;

import com.ms.magasinbe.common.enums.UserRole;
import com.ms.magasinbe.common.utils.RestAPIResponse;
import com.ms.magasinbe.configs.security.AuthSession;
import com.ms.magasinbe.configs.security.AuthUser;
import com.ms.magasinbe.configs.security.AuthorizeValidator;
import com.ms.magasinbe.controllers.modals.request.LoginRequest;
import com.ms.magasinbe.controllers.modals.request.SendOtpRequest;
import com.ms.magasinbe.controllers.modals.request.SignupRequest;
import com.ms.magasinbe.controllers.modals.response.TokenResponse;
import com.ms.magasinbe.services.auth.AuthService;
import com.ms.magasinbe.services.user.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ApiPath.AUTHENTICATE_API)
public class AuthenticateController extends AbstractBaseController {

  @Autowired
  AuthService authService;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  UserService userService;

  @PostMapping(ApiPath.SIGN_UP)
  public ResponseEntity<RestAPIResponse<Object>> signup(@Valid @RequestBody SignupRequest signupRequest) {
    return responseUtil.successResponse(authService.signupAccount(signupRequest, UserRole.CUSTOMER));
  }

  @PostMapping(ApiPath.LOGIN)
  public ResponseEntity<RestAPIResponse<Object>> login(@Valid @RequestBody LoginRequest loginRequest) {
    return responseUtil.successResponse(authService.loginAccount(loginRequest, UserRole.CUSTOMER));
  }

  @AuthorizeValidator({ UserRole.CUSTOMER })
  @GetMapping(ApiPath.AuthInFo)
  public ResponseEntity<RestAPIResponse<Object>> getInfo(@AuthSession AuthUser authUser) {
    return responseUtil.successResponse(authService.getAuthUser(authUser.getId()));
  }

}
