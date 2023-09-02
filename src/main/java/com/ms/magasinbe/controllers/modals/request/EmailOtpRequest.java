package com.ms.magasinbe.controllers.modals.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.magasinbe.common.utils.Constant;
import com.ms.magasinbe.common.utils.ParamError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class EmailOtpRequest {
  @NotBlank(message = ParamError.FIELD_NAME)
  @Pattern(regexp = Constant.EMAIL_REGEX, message = ParamError.PATTERN)
  @Size(max = 120, message = ParamError.MAX_LENGTH)
  private String email;

  @NotBlank(message = ParamError.FIELD_NAME)
  private String otp;
}
