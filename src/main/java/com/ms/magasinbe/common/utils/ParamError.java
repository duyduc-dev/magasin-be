package com.ms.magasinbe.common.utils;

public interface ParamError {
    String FIELD_NAME = "{fieldName} is ${validatedValue == null ? 'null' : 'empty'}";

    String MAX_LENGTH = "Maximum length {fieldName} is {max} characters";
    String MIN_LENGTH = "Minimum length {fieldName} is {min} characters";

    String MIN_VALUE = "Minimum {fieldName} is {value}";

    String MAX_VALUE = "Maximum {fieldName} is {value}";

    String MIN_NUMBER = "{fieldName} should not be less than {value}";

    String DEFAULT = "{fieldName} default is {value}";

    String MAX_SELECTION = "Maximum Created selection option is {max}";

    String PATTERN = "{fieldName} is incorrect format";


    //user

    String EMAIL_EXISTED = "Email already existed";
    String PHONE_EXISTED = "Phone number phone existed";
    String USER_NOT_FOUND = "User not found";
    String VERIFY_FAIL = "Verify otp failed";
    String EMAIL_OR_PHONE_EMPTY = "Please input your email or phone number!";
    String EMAIL_PASS_INVALID = "Email or Password invalid";
    String PASSWORD_NOT_MATCH = "New Password and Confirm Password not match";
    String CANNOT_FORGOT_PASSWORD = "You do not have permission to change your password";
    String CHANGE_PASSWORD_EXPIRED = "Time change password is expired";
    String FORGOT_PASSWORD_EXPIRED = "Time forgot password is expired";
    // holder
    String HOLDER_NOT_FOUND = "Holder not found";
    String HOLDER_CREATED_CARD = "Holder created card";
    String PROGRAMME_NOT_FOUND = "Programme not found";
    String CURRENCY_NOT_FOUND = "Currency not found";
    String CARD_NOT_FOUND = "Card not found";
    String INVALID_STATUS = " Invalid status";
    String CUSTOMER_REF_ID_EMPTY = "Customer RefId must have for frozen Card";
    String MANAGER_NOT_FOUND = "Manager not found";
    // Card Design
    String CARD_DESIGN_NOT_FOUND = "Card Design not found";
    String NATIONALITY_RESTRICTED_COUNTRY = "Nationality is in a restricted country";
    String TRANSACTION_NOT_FOUND = "Transaction Not Found";
    String SUPPORT_CARD_NOT_FOUND = "Support Case Not Found";
    String CUSTOMER_REF_NOT_FOUND = "Customer Ref not found";

    String HOLDER_18YEAR = "Holder must be over 18 years old";
    String DUPLICATE = "Type duplicate";
    String BILLING_REGISTER_IS_NULL = "Please input register and billing";
    String NATIONALITY_NOT_FOUND = "Nationality is in a restricted country";
    String LIMIT_GROUP_NOT_FOUND = "Not Limit Group Has Not Been Set";
    String BILLING_REGISTER_NULL = "Please input register and billing";
    String REGISTER_NULL = "Please input register";
    String NATIONALITY_NULL = "Nationality is in a restricted country";
    String LIMIT_GROUP_NULL = "Not Limit Group Has Not Been Set";
    String CARD_DESIGN = "CardDesign not found";
    String CANNOT_ISSUE_CARD = "Cannot Issue Card";
    String KEY_NOT_FOUND = "Key not found";
    String VERIFY_OTP = "Verify failed";
    String MANAGER_KEY_NULL = "Manager key not found";
    String CANNOT_UPDATE_ROLE = "Cannot update Role this";
    String ROLE_NOT_FOUND = "Role not found";
    String COMPANY_NULL = "Company not found";
    String ROLE_NAME_EXISTED = "Role name already existed";
    String ROLE_IS_BEING_USED = "Role is being used";
    String PERMISSIONS_IS_DUPLICATE = "Permissions is duplicate";


    // Token
    String TOKEN_NOT_FOUND = "Token not found.";
    String OTP_HAS_EXPIRED = "Otp has expired";
    String INVALID_EMAIL_FORMAT = "Invalid email format";
}
