package com.ms.magasinbe.services.otp;

import com.ms.magasinbe.common.enums.SystemStatus;
import com.ms.magasinbe.common.exceptions.ApplicationException;
import com.ms.magasinbe.common.utils.*;
import com.ms.magasinbe.configs.emailsender.EmailService;
import com.ms.magasinbe.controllers.modals.request.EmailOtpRequest;
import com.ms.magasinbe.controllers.modals.request.EmailRequest;
import com.ms.magasinbe.controllers.modals.response.ActiveCodeResponse;
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
    Validator.mustNull(otp, RestAPIStatus.FAIL, ConstantData.OTP_CANNOT_BE_USED_ANYMORE);
    otp = otpRepository.findKeyAndStatusLatest(emailOtpRequest.getEmail(), SystemStatus.ACTIVE);
    Validator.notNull(otp, RestAPIStatus.NOT_FOUND, ConstantData.EMAIL_HAS_BEEN_RECEIVED_OTP_NOTFOUND);
    if(DateUtil.convertToUTC(new Date()).getTime() >= otp.getExpiredDate()) {
      otp.setStatus(SystemStatus.IN_ACTIVE);
      otpRepository.save(otp);
      throw new ApplicationException(RestAPIStatus.EXPIRED, ConstantData.OTP_HAS_EXPIRED, ParamError.OTP_HAS_EXPIRED);
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
    Validator.notNull(otp, RestAPIStatus.NON_AUTHORITATIVE_INFORMATION, ConstantData.OTP_IS_INCORRECT);
    User user = userRepository.findFirstByEmailAndStatus(emailOtpRequest.getEmail(), SystemStatus.ACTIVE);
    Validator.mustNull(user, RestAPIStatus.EXISTED, ParamError.EMAIL_EXISTED);
    verifyOtpEmail(emailOtpRequest);
  }

  /**
   * @param emailRequest
   */
  @Override
  public void SendOtp(EmailRequest emailRequest) {
    Validator.validateEmail(emailRequest.getEmail());
    User user = userRepository.findFirstByEmailAndStatus(emailRequest.getEmail(), SystemStatus.ACTIVE);
    Validator.notNull(user, RestAPIStatus.NOT_FOUND, ConstantData.USER_NOT_FOUND, ParamError.USER_NOT_FOUND);
    String otpStr = UniqueID.getRandomOTP();
    user.setOtpCode(otpStr);
    user.setOtpExpiryDate(DateUtil.convertToUTC(new Date()).getTime() + Constant.MINUTE);
    userRepository.save(user);
    emailService.sendOtp(user.getEmail(), user.getOtpCode());
  }

  /**
   * @param emailOtpRequest
   */
  @Override
  public ActiveCodeResponse verifyOtp(EmailOtpRequest emailOtpRequest) {
    Validator.validateEmail(emailOtpRequest.getEmail());
    User user = userRepository.findFirstByEmailAndStatus(emailOtpRequest.getEmail(), SystemStatus.ACTIVE);
    Validator.notNull(user, RestAPIStatus.NOT_FOUND, ConstantData.USER_NOT_FOUND, ParamError.USER_NOT_FOUND);
    if(user.getOtpCode().equals(emailOtpRequest.getOtp())) {
      if(DateUtil.convertToUTC(new Date()).getTime() >= user.getOtpExpiryDate()) {
        user.setOtpCode(null);
        user.setOtpExpiryDate(0);
        userRepository.save(user);
        throw new ApplicationException(RestAPIStatus.EXPIRED, ConstantData.OTP_HAS_EXPIRED, ParamError.OTP_HAS_EXPIRED);
      }
    } else {
      throw new ApplicationException(RestAPIStatus.NON_AUTHORITATIVE_INFORMATION, ConstantData.OTP_IS_INCORRECT, ParamError.VERIFY_OTP);
    }
    user.setOtpCode(null);
    user.setOtpExpiryDate(0);
    user.setActiveCode(UniqueID.getUUID());
    user.setExpiryDate(DateUtil.convertToUTC(new Date()).getTime() + Constant.MINUTE);
    user = userRepository.save(user);

    return ActiveCodeResponse.builder()
            .activeCode(user.getActiveCode())
            .codeExpired(user.getExpiryDate())
            .build();
  }

}
