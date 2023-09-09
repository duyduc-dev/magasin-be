package com.ms.magasinbe.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;


public enum TypeVerifyAccount {
  NONE ,FORGOT_PASSWORD , VERIFY_ACCOUNT;

  @JsonCreator
  public static TypeVerifyAccount create(String value) {
    if(value == null) {
      return null;
    }
    for(TypeVerifyAccount v : values()) {
      if(value.equals(v.name())) {
        return v;
      }
    }
    return null;
  }
}
