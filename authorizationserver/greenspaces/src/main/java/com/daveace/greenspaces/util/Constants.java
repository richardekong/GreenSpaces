package com.daveace.greenspaces.util;

public interface Constants {

    String DELETED_SUCCESSFULLY = "Deleted Successfully!";

    String NOT_DELETED = "Not Deleted!";

    String ACCOUNT_ENABLED = "Account enabled!";

    String ACCOUNT_DISABLED = "Account disabled!";

    String ACCOUNT_IS_ALREADY_DISABLED = "Account is already disabled!";

    String ACCOUNT_UNLOCKED = "Account unlocked";

    String ACCOUNTED_LOCKED = "Accounted locked!";

    String ACCOUNT_IS_ALREADY_LOCKED = "Account is already locked";

    String PASSWORD_REST_FAILED = "Password reset failed!";

    String PASSWORD_SUCCESSFULLY_RESET = "Password successfully reset!";

    String CLIENT_ALREADY_EXISTS = "Client already exists";

    String CLIENT_NOT_FOUND = "Client not found";

    String CLIENT_SAVED = "Client saved successfully";

    String CLIENT_NOT_SAVED = "Client not saved!";

    String X_CSRF_TOKEN = "X-CSRF-TOKEN";

    String CSRF_PARAM_NAME = "_csrf";

    String X_IDENTIFIER = "X-IDENTIFIER";

    String PROVIDE_USERNAME = "Provide username";
    String INVALID_USERNAME = "Invalid Username";
    String INVALID_EMAIL = "Invalid Email";
    String INVALID_ROLE = "Invalid role. role must contain letters only!";
    String INVALID_AUTHORITY = "Invalid authority. Authority must contain letters only!";
    String INVALID_PASSWORD = "Invalid password. Password must contain at least 7 characters including lower, upper case letters, digits and one special character";
    String MIN_ROLES = "Must have at least one role";
    String MIN_AUTHORITIES = "Must have at least one authority";
    String USERNAME_CANT_BE_BLANK = "Username cannot be blank";
    String OPERATION_FAILED = "Operation failed";
    String USER_UNAUTHORIZED = "User unauthorized";
    String NOT_UPDATED = "Not Updated";
    String UPDATED = "Updated successfully";

    String INVALID_NAME = "Invalid name, Please provide 7 or more characters";
    String CLIENT_UPDATED = "Client updated";
    String CLIENT_NOT_UPDATED = "Client not updated";
    String MIN_REDIRECT_URI = "Client must have one redirect URI";
    String MIN_SCOPE = "Must have at least one scope";
    String TOKEN_SETTINGS_UPDATED = "Token settings updated successfully";
    String DELETED = "Deleted";
    String AUTHORITY_UPDATED = "Authority updated!";
    String AUTHORITY_DELETED = "Authority Deleted!";
    String AUTHORITY_NOT_DELETED = "Authority Not Deleted!";
    String LOGIN_AGAIN = "Login again!";

    //API ROUTES/PATHS DECLARATIONS
    String CLIENTS_PATH = "/clients";
    String CLIENT_PATH = "/clients/{id}";

    String USER_PATH = "/users/{id}";
    String USERS_PATH = "/users";
    String ROLES_PATH = "/roles";
    String AUTHORITIES_PATH = "/authorities";
    String USER_ACCOUNT_PASSWORD_RESET_PATH = "/users/{id}/accounts/password-reset";
    String USER_ACCOUNT = "/users/{id}/accounts";
    String MIN_AUTHENTICATION_METHODS = "Client must have at least one authentication method";
    String MIN_GRANT_TYPES = "Client must have at least one authentication grant type";
}
