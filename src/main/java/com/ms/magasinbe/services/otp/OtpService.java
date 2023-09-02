package com.ms.magasinbe.services.otp;

import com.ms.magasinbe.controllers.modals.request.EmailOtpRequest;
import com.ms.magasinbe.controllers.modals.request.EmailRequest;

public interface OtpService {

  void sendOtpEmailSignup(EmailRequest emailRequest);
  void verifyOtpEmail(EmailOtpRequest emailOtpRequest);
  void verifyOtpEmailSignup(EmailOtpRequest emailOtpRequest);

}
