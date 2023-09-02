package com.ms.magasinbe.controllers;

import com.ms.magasinbe.common.utils.RestAPIResponse;
import com.ms.magasinbe.common.utils.UniqueID;
import com.ms.magasinbe.common.utils.Validator;
import com.ms.magasinbe.configs.emailsender.EmailService;
import com.ms.magasinbe.controllers.modals.request.EmailOtpRequest;
import com.ms.magasinbe.controllers.modals.request.EmailRequest;
import com.ms.magasinbe.services.otp.OtpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPath.OTP_API)
public class OtpController extends AbstractBaseController {

  @Autowired
  OtpService otpService;

  @PostMapping(ApiPath.SEND_OTP_EMAIL_SIGNUP)
  public ResponseEntity<RestAPIResponse<Object>> sendOtpEmailSignup(@Valid @RequestBody EmailRequest emailRequest) {
    otpService.sendOtpEmailSignup(emailRequest);
    return responseUtil.successResponse("ok");
  }

  @PostMapping(ApiPath.VERIFY_OTP_EMAIL_SIGNUP)
  public ResponseEntity<RestAPIResponse<Object>> verifyOtpEmailSignup(@Valid @RequestBody EmailOtpRequest emailOtpRequest) {
    otpService.verifyOtpEmailSignup(emailOtpRequest);
    return responseUtil.successResponse("ok");
  }

}
