package com.ms.magasinbe.configs.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.magasinbe.common.utils.RestAPIResponse;
import com.ms.magasinbe.common.utils.RestAPIStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.Serializable;

@Component
@RestControllerAdvice
public class AuthEntryPointException  implements AuthenticationEntryPoint, Serializable {

  @Autowired
  private ObjectMapper objectMapper;
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

    // This is invoked when user tries to access a secured REST resource without supplying any credentials
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.getWriter().write(objectMapper.writeValueAsString(new RestAPIResponse<>(RestAPIStatus.UNAUTHORIZED, null)));
  }

  @ExceptionHandler(value = {AccessDeniedException.class})
  public void commence(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType("application/json");
    response.getWriter().write(objectMapper.writeValueAsString(new RestAPIResponse<>(RestAPIStatus.FORBIDDEN, null)));
  }
}
