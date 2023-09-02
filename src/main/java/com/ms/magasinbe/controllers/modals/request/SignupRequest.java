package com.ms.magasinbe.controllers.modals.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.magasinbe.common.utils.Constant;
import com.ms.magasinbe.common.utils.ParamError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class SignupRequest {

  @NotBlank(message = ParamError.FIELD_NAME)
  @Size(max = 50, message = ParamError.MAX_LENGTH)
  private String firstName;

  @NotBlank(message = ParamError.FIELD_NAME)
  @Size(max = 50, message = ParamError.MAX_LENGTH)
  private String lastName;

  @NotBlank(message = ParamError.FIELD_NAME)
  @Size(max = 50, message = ParamError.MAX_LENGTH)
  private String middleName;

  @NotBlank(message = ParamError.FIELD_NAME)
  @Pattern(regexp = Constant.EMAIL_REGEX, message = ParamError.PATTERN)
  @Size(max = 120, message = ParamError.MAX_LENGTH)
  private String email;

  @NotBlank(message = ParamError.FIELD_NAME)
  @Size(max = 20, message = ParamError.MAX_LENGTH)
  private String phoneNumber;

  @NotBlank(message = ParamError.FIELD_NAME)
  @Size(max = 36, message = ParamError.MAX_LENGTH)
  @Size(min = 32, message = ParamError.MIN_LENGTH)
  private String passwordHash;
}
