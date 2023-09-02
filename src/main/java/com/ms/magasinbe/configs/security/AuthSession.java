package com.ms.magasinbe.configs.security;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Documented
public @interface AuthSession {
  /**
   * If true then throw ApplicationException when access token header not found
   *
   * @return
   */
  boolean required() default true;
}
