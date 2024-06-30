package com.daveace.greenspaces.authenticationmethod;

public enum AuthenticationMethods {

    CLIENT_SECRET_BASIC("client_secret_basic"),
    CLIENT_SECRET_POST("client_secret_post"),
    CLIENT_SECRET_JWT("client_secret_jwt"),
    PRIVATE_KEY_JWT("private_key_jwt"),
    NONE("none");

    private final String method;

    AuthenticationMethods(String method){
        this.method=method;
    }

    public String method(){
        return method;
    }
}
