package com.ms.magasinbe.configs.security;

import com.ms.magasinbe.common.enums.UserRole;
import com.ms.magasinbe.common.exceptions.ApplicationException;
import com.ms.magasinbe.common.utils.Constant;
import com.ms.magasinbe.common.utils.RestAPIStatus;
import com.ms.magasinbe.common.utils.Validator;
import io.jsonwebtoken.Claims;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class JWTTokenResolver implements HandlerMethodArgumentResolver {

  public JWTTokenResolver() {}

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterAnnotation(AuthSession.class) != null;
  }

  @Override
  public Object resolveArgument(
          MethodParameter parameter,
          ModelAndViewContainer mavContainer,
          NativeWebRequest webRequest,
          WebDataBinderFactory binderFactory
  ) throws Exception {

    AuthUser authUser = null;
    AuthSession authSession = parameter.getParameterAnnotation(AuthSession.class);
    if (authSession != null) {
      try {
        Claims user =
                (Claims) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authUser = new AuthUser(
                        user.getSubject(),
                        user.get(Constant.USERNAME, String.class),
                        UserRole.valueOf(user.get(Constant.USER_ROLE, String.class))
                );

        Validator.notNull(authUser, RestAPIStatus.UNAUTHORIZED, "");
      } catch (Exception e) {
        throw new ApplicationException(RestAPIStatus.UNAUTHORIZED, e.getMessage());
      }
    }
    System.out.println(authUser);
    return authUser;

  }
}
