package com.ms.magasinbe.controllers.modals.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActiveCodeResponse {
  private String activeCode;
  private long codeExpired;
}
