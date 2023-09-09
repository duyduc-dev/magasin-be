package com.ms.magasinbe.common.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PasswordHash {
  private String passwordSalt;
  private String passwordHash;
}
