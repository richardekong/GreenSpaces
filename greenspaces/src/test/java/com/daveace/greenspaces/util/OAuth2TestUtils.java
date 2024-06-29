package com.daveace.greenspaces.util;

public interface OAuth2TestUtils {

  String OAUTH2_AUTH_QUERY_PARAMS =
            "response_type=%s&" +
                    "client_id=%s&" +
                    "scope=%s&" +
                    "redirect_uri=%s&" +
                    "code_challenge=%s&" +
                    "code_challenge_method=%s";

   String ACCESS_TOKEN_QUERY_PARAMS = "client_id=%s&" +
            "redirect_uri=%s&" +
            "grant_type=%s&" +
            "code=%s&" +
            "code_verifier=%s";


}
