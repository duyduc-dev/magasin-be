package com.ms.magasinbe.common.utils;

public interface Constant {

    String USERNAME = "username";
    String USER_ROLE = "user_role";

    String FULL_NAME = "full_name";

    String PHONE_NUMBER_REGEX = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";

    String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    String DATE_PATTERN = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
    String NAME = "^[0-9a-zA-Z-./()']+$";
    int DATE_DIFFERENCE = 88;
    String DATE_YEAR_PATTERN = "^(19|30)\\d{2}$";
    String DATE_MONTH_PATTERN = "^(0?[1-9]|1[0-2])$";
    String DEFAULT_VALUE_OTP = "000000";
    String HEADER_TOKEN = "auth-token";
    String DATE_FORMAT = "yyyy-MM-dd";
    String DATE_FORMAT_MONTH_YEAR = "MM/yyyy";
    String FORMAT_MONTH_YEAR = "MM/yy";
    String API_FORMAT_DATE = "yyyy/MM/dd HH:mm:ss";
    String API_FORMAT_DATE_YEAR = "yyyy/MM/dd";
    String HTTP_HEADER = "Access-Control-Allow-Headers";
    String HEADERS = "Content-Type, x-requested-with, X-Custom-Header";



    int MAX_TOKENS_CAN_BE_CREATED = 3;
    int SALT_LENGTH = 6;
    long MINUTE = 300000; // 5 minute
    long JWT_EXPIRATION = 86400000;
    long EIGHTEEN_YEAR = 568036800000L; //milliseconds

    // extension file
    String PDF = "pdf";
    String DOCX = "docx";
    String DOC = "doc";


    String ROLE_TYPE = "roleType";
}
