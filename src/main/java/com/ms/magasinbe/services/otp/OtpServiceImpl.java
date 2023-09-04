package com.ms.magasinbe.services.otp;

import com.ms.magasinbe.common.enums.SystemStatus;
import com.ms.magasinbe.common.exceptions.ApplicationException;
import com.ms.magasinbe.common.utils.*;
import com.ms.magasinbe.configs.emailsender.EmailService;
import com.ms.magasinbe.controllers.modals.request.EmailOtpRequest;
import com.ms.magasinbe.controllers.modals.request.EmailRequest;
import com.ms.magasinbe.entities.Otp;
import com.ms.magasinbe.entities.User;
import com.ms.magasinbe.repositories.OtpRepository;
import com.ms.magasinbe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OtpServiceImpl implements OtpService {

  @Autowired
  EmailService emailService;
  @Autowired
  OtpRepository otpRepository;
  @Autowired
  UserRepository userRepository;

  /**
   * @param emailRequest
   */
  @Override
  public void sendOtpEmailSignup(EmailRequest emailRequest) {
    Validator.validateEmail(emailRequest.getEmail());
    String otpStr = UniqueID.getRandomOTP();
    Otp otp = Otp.builder()
            .id(UniqueID.getUUID())
            .key(emailRequest.getEmail())
            .otp(otpStr)
            .expiredDate(new Date().getTime() + Constant.MINUTE)
            .status(SystemStatus.ACTIVE)
            .build();
    otp = otpRepository.save(otp);
    emailService.sendOtp(emailRequest.getEmail(), otp.getOtp());
  }

  /**
   * @param emailOtpRequest
   */
  @Override
  public void verifyOtpEmail(EmailOtpRequest emailOtpRequest) {
    Validator.validateEmail(emailOtpRequest.getEmail());
    Otp otp = null;
    otp = otpRepository.findKeyAndOtpAndStatusLatest(emailOtpRequest.getEmail(), emailOtpRequest.getOtp(), SystemStatus.IN_ACTIVE);
    Validator.mustNull(otp, RestAPIStatus.FAIL, "OTP cannot be used anymore");
    otp = otpRepository.findKeyAndStatusLatest(emailOtpRequest.getEmail(), SystemStatus.ACTIVE);
    Validator.notNull(otp, RestAPIStatus.NOT_FOUND, "Email has been received otp not found");
    if(DateUtil.convertToUTC(new Date()).getTime() >= otp.getExpiredDate()) {
      otp.setStatus(SystemStatus.IN_ACTIVE);
      otpRepository.save(otp);
      throw new ApplicationException(RestAPIStatus.EXPIRED, "OTP has expired");
    }
    otp.setStatus(SystemStatus.IN_ACTIVE);
    otpRepository.save(otp);
  }

  /**
   * @param emailOtpRequest
   */
  @Override
  public void verifyOtpEmailSignup(EmailOtpRequest emailOtpRequest) {
    Otp otp = otpRepository.findKeyAndOtpAndStatusLatest(emailOtpRequest.getEmail(), emailOtpRequest.getOtp(), SystemStatus.ACTIVE);
    Validator.notNull(otp, RestAPIStatus.NON_AUTHORITATIVE_INFORMATION, "Otp is not correct.");
    User user = userRepository.findFirstByEmailAndStatus(emailOtpRequest.getEmail(), SystemStatus.ACTIVE);
    Validator.mustNull(user, RestAPIStatus.EXISTED, ParamError.EMAIL_EXISTED);
    verifyOtpEmail(emailOtpRequest);
  }


}
