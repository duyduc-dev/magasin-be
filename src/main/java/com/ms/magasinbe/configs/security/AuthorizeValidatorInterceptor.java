package com.ms.magasinbe.configs.security;


import com.ms.magasinbe.common.enums.UserRole;
import com.ms.magasinbe.common.exceptions.ApplicationException;
import com.ms.magasinbe.common.utils.Constant;
import com.ms.magasinbe.common.utils.RestAPIStatus;
import com.ms.magasinbe.common.utils.Validator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class AuthorizeValidatorInterceptor {

  @Autowired
  JwtService jwtService;

  @Before(
          value =
                  "@annotation(com.ms.magasinbe.configs.security.AuthorizeValidator)  && @annotation(roles)")
  public void before(JoinPoint caller, AuthorizeValidator roles) {
    // Capture access token from current request
    HttpServletRequest httpServletRequest =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String authToken = httpServletRequest.getHeader(Constant.HEADER_TOKEN);
    if (authToken == null) throw new ApplicationException(RestAPIStatus.UNAUTHORIZED);
    String jwtToken = null;
    Claims claims = null;
    // Check Get current Authenticate user from access token
    if (authToken != null && authToken.startsWith("Bearer ")) {
      jwtToken = authToken.substring(7);
      if (jwtService.validateExpirationToken(jwtToken)) {
        try {
          claims = jwtService.getAllClaimsFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
          log.error("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
          log.error("JWT Token has expired");
        }
      }
    } else {
      throw new ApplicationException(RestAPIStatus.UNAUTHORIZED);
    }
    Validator.notNull(claims, RestAPIStatus.UNAUTHORIZED, "");
    // Validate Role
    boolean isValid =
            isValidate(UserRole.valueOf(claims.get(Constant.USER_ROLE, String.class)), roles);
    if (!isValid) throw new ApplicationException(RestAPIStatus.FORBIDDEN);
  }

  public boolean isValidate(UserRole userRole, AuthorizeValidator types) {
    for (UserRole type : types.value()) {
      if (type == userRole) return true;
    }

    return false;
  }
}