package com.daveace.greenspaces.util;

import com.daveace.greenspaces.authenticationmethod.AuthenticationMethod;
import com.daveace.greenspaces.authenticationmethod.AuthenticationMethods;
import com.daveace.greenspaces.client.Client;
import com.daveace.greenspaces.granttypes.GrantType;
import com.daveace.greenspaces.redirecturi.RedirectURI;
import com.daveace.greenspaces.scope.Scope;
import com.daveace.greenspaces.tokensettings.TokenSettings;

import java.util.Set;

public interface ClientTestUtils {
    Client client = Client
            .builder()
            .id("001i")
            .clientId("001ci")
            .name("Client03")
            .secret("53cr3t50$9feW1t6m3")
            .authenticationMethods(
                    Set.of(
                            AuthenticationMethod.builder()
                                    .id(1)
                                    .method(AuthenticationMethods.CLIENT_SECRET_BASIC.method())
                                    .build()
                    )
            )
            .scopes(
                    Set.of(
                            Scope.builder()
                                    .id(1)
                                    .scope("openid")
                                    .build()
                    ))
            .redirectUris(
                    Set.of(
                            RedirectURI.builder()
                                    .id(1L)
                                    .uri("http://127.0.0.1:1000")
                                    .build()
                    ))
            .grantTypes(
                    Set.of(
                            GrantType.builder()
                                    .id(1)
                                    .grantType("authorization_code")
                                    .build()
                    ))
            .tokenSettings(
                    TokenSettings.builder()
                            .id(1)
                            .format("self-contained")
                            .accessTokenTTL(30)
                            .build()
            )
            .build();
}
