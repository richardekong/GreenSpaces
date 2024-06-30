package com.daveace.greenspaces.util;

public interface Regexp {
    String USERNAME_REGEXP = "^[a-zA-Z0-9._-]{7,30}$";
    String PASSWORD_REGEXP = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+[{]};:'\",/?]).{7,}$";
    String LETTER_REGEX = "^[\\p{L}]+$";
    String ALPHA_NUM_REGEX = "^[a-zA-Z0-9!@#%^&*()_-]{7,}$";
}
