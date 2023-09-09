package com.ms.magasinbe.controllers.modals.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.magasinbe.common.utils.ParamError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class ForgotPasswordRequest {
  @NotBlank(message = ParamError.FIELD_NAME)
  @Size(max = 36, message = ParamError.MAX_LENGTH)
  @Size(min = 32, message = ParamError.MIN_LENGTH)
  private String passwordHash;

  @NotBlank(message = ParamError.FIELD_NAME)
  @Size(max = 36, message = ParamError.MAX_LENGTH)
  @Size(min = 32, message = ParamError.MIN_LENGTH)
  private String confirmPasswordHash;
}
