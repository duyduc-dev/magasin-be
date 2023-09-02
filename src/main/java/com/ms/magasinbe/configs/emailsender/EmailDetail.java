package com.ms.magasinbe.configs.emailsender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetail {
  private String toEmail;
  private String subject;
  private String body;
}
