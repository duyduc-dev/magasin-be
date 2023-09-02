package com.ms.magasinbe.configs.emailsender;

import com.ms.magasinbe.common.exceptions.ApplicationException;
import com.ms.magasinbe.common.utils.ResponseUtil;
import com.ms.magasinbe.common.utils.RestAPIStatus;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class EmailService {
  @Autowired
  JavaMailSender mailSender;
  @Autowired
  ResponseUtil responseUtil;

  @Value("${spring.mail.username}")
  private String fromEmail;

  public void sendEmail(EmailDetail emailDetail) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message);
      helper.setFrom(this.fromEmail);
      helper.setTo(emailDetail.getToEmail());
      boolean html = true;
      helper.setText(emailDetail.getBody(), html);
      helper.setSubject(emailDetail.getSubject());
      mailSender.send(message);
    } catch (Exception e) {
      throw new ApplicationException(RestAPIStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public void sendOtp(String email, String otp) {
    EmailDetail emailDetail = EmailDetail.builder()
            .toEmail(email)
            .subject("[Magasin] Your OTP")
            .body(MessageFormat.format("Hi. Your OTP is <b>{0}</b>", otp))
            .build();

    this.sendEmail(emailDetail);
  }

}
