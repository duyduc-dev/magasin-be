package com.ms.magasinbe.controllers.modals.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.magasinbe.common.utils.ParamError;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class LoginRequest {
  @NotBlank(message = ParamError.FIELD_NAME)
  private String username;

  @NotBlank(message = ParamError.FIELD_NAME)
  private String passwordHash;
}
