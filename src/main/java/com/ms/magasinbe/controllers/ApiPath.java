package com.ms.magasinbe.controllers;

public interface ApiPath {
  // Base url
  String BASE_API_URL = "/api";

  String AUTHENTICATE_API = BASE_API_URL + "/auth";
  String USER_API = BASE_API_URL + "/user";
  String PRODUCT_API = BASE_API_URL + "/product";
  String ROLE_API = BASE_API_URL + "/role";
  String OTP_API = BASE_API_URL + "/otp";


  // common
  String ID = "/{id}";
  String DETAIL = "/detail";
  String ADD = "/add";
  String EDIT = "/edit";
  String DELETE = "/delete";
  String CANCEL = "/cancel";
  String GET_PAGE = "/page";
  String EXPORT = "/export";

  // Authenticate APIs
  String LOGIN = "/login";
  String LOGOUT = "/logout";
  String SIGN_UP = "/sign-up";
  String RESET_PASSWORD = "/reset-password/{active-code}";
  String AuthInFo = "/auth-info";
  String CHECK_PHONE_NUMBER_SIGNUP = "/check-phone-signup";


  // User APIs
  String REFERRAL = "/referral";
  String CHECK_NICKNAME = "/check-nickname";
  String REFRESH_TOKEN = "/refresh-token";
  String CHANGE_PASSWORD = "/change-password";
  String LOYALTY = "/loyalty";
  String MEMBER = "/member";



  // OTP API
  String SEND_OTP_EMAIL_SIGNUP = "/send-otp-email-signup";
  String VERIFY_OTP_EMAIL_SIGNUP = "/verify-otp-email-signup";
  String SEND_OTP_EMAIL = "/send-otp";
  String VERIFY_OTP = "/verify-otp";
}
