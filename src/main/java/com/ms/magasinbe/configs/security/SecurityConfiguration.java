package com.ms.magasinbe.configs.security;

import static org.springframework.security.config.Customizer.withDefaults;

import com.ms.magasinbe.common.utils.ApplicationValueConfigure;
import com.ms.magasinbe.common.utils.AuthHelper;
import com.ms.magasinbe.controllers.ApiPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

  @Autowired
  AuthEntryPointException unauthorizedHandler;

  @Autowired
  ApplicationValueConfigure applicationValueConfigure;

  @Autowired
  JwtService jwtService;

  @Bean
  public JWTTokenFilter authenticationTokenFilterBean() {
    return new JWTTokenFilter();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            // we don't need CSRF because our token is invulnerable
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
            // don't create session
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(
                    (authz) ->
                            authz
                                    // Allow access public resource
                                    .requestMatchers(
                                            HttpMethod.GET,
                                            "/",
                                            "/favicon.ico",
                                            "/**/*.html",
                                            "/**/*.css",
                                            "/**/*.js",
                                            "/**/*.png",
                                            "/**/*.gif",
                                            "/public/**",
                                            "/**/public",
                                            "/**/public/**",
                                            "/**/*.json",
                                            "/**/*.jpg",
                                            // enable swagger endpoints
                                            "/swagger-resources/**",
                                            "/swagger-ui.html**",
                                            "/api/**",
                                            "/webjars/**",
                                            "/v2/api-docs",
                                            "/configuration/ui",
                                            "/configuration/security",
                                            "/manage/api-docs",
                                            "/v3/api-docs/**"
                                            // hecto process
                                    )
                                    .permitAll()
                                    // allow CORS option calls
                                    .requestMatchers(HttpMethod.OPTIONS, "/api/**")
                                    .permitAll()
                                    .requestMatchers(ApiPath.AUTHENTICATE_API + ApiPath.SIGN_UP)
                                    .permitAll()
                                    .requestMatchers(ApiPath.AUTHENTICATE_API + ApiPath.LOGIN)
                                    .permitAll()
                                    .requestMatchers(ApiPath.AUTHENTICATE_API + ApiPath.REFRESH_TOKEN)
                                    .permitAll()
                                    .requestMatchers(ApiPath.AUTHENTICATE_API + ApiPath.LOGOUT)
                                    .permitAll()
                                    .requestMatchers(ApiPath.AUTHENTICATE_API + ApiPath.RESET_PASSWORD)
                                    .permitAll()
                                    .requestMatchers(ApiPath.OTP_API + ApiPath.SEND_OTP_EMAIL_SIGNUP)
                                    .permitAll()
                                    .requestMatchers(ApiPath.OTP_API + ApiPath.VERIFY_OTP_EMAIL_SIGNUP)
                                    .permitAll()
                                    .anyRequest()
                                    .authenticated())
            .httpBasic(withDefaults());
    // Custom JWT based security filter
    httpSecurity.addFilterBefore(
            authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

    // disable page caching
    httpSecurity.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
    return httpSecurity.build();
  }
}
