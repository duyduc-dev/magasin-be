package com.ms.magasinbe.configs.security;

import com.ms.magasinbe.common.exceptions.ApplicationException;
import com.ms.magasinbe.common.utils.Constant;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JWTTokenFilter extends OncePerRequestFilter {


  @Autowired
  JwtService jwtService;

  /**
   * @param request
   * @param response
   * @param filterChain
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String requestTokenHeader = request.getHeader(Constant.HEADER_TOKEN);
    if(requestTokenHeader != null) {
      try {
        String userId = null;
        String jwtToken = null;
        if (requestTokenHeader.startsWith("Bearer ")) {
          jwtToken = requestTokenHeader.substring(7);
          try {
            userId = jwtService.getUserIdFromToken(jwtToken);
          } catch (IllegalArgumentException e) {
            log.error(">>> Unable to get JWT Token");
          } catch (ExpiredJwtException e) {
            log.error(">>> JWT Token has expired");
          }
        } else {
          logger.warn("JWT Token does not begin with Bearer String");
        }

        // validate token
        if (userId != null
                && SecurityContextHolder.getContext().getAuthentication() == null
                && jwtService.validateExpirationToken(jwtToken)) {
          UsernamePasswordAuthenticationToken authentication =
                  new UsernamePasswordAuthenticationToken(
                          jwtService.getAllClaimsFromToken(jwtToken), null, null);
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (ApplicationException ex) {
        log.debug("doFilterInternal >> ", ex);
      }
    }

    // Allow cross domain
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
    response.setHeader("Access-Control-Max-Age", "3600");
    // Allow set custom header token
    response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header " + Constant.HEADER_TOKEN);

    filterChain.doFilter(request, response);
  }
}
