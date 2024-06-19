package com.daveace.greenspaces.client;

import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.daveace.greenspaces.authenticationmethod.AuthenticationMethod;
import com.daveace.greenspaces.granttypes.GrantType;
import com.daveace.greenspaces.redirecturi.RedirectURI;
import com.daveace.greenspaces.scope.Scope;
import com.daveace.greenspaces.tokensettings.TokenSettings;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import com.daveace.greenspaces.response.GreenSpacesException;


public interface ClientConverter {

    default RegisteredClient toRegisteredClient(Client client) {

        Consumer<Set<AuthorizationGrantType>> grantTypes = gts -> {
            client.getGrantTypes().forEach(
                    grantType -> gts.add(new AuthorizationGrantType(grantType.getGrantType())));
        };

        Consumer<Set<String>> scopes = s -> client
                .getScopes()
                .stream()
                .map(Scope::getScope)
                .forEach(s::add);
        Consumer<Set<String>> redirectUris = uris -> client
                .getRedirectUris()
                .stream()
                .map(RedirectURI::getUri)
                .forEach(uris::add);

        Consumer<Set<ClientAuthenticationMethod>> authenticationMethods = methods -> client
                .getAuthenticationMethods()
                .stream()
                .map(AuthenticationMethod::getMethod)
                .map(ClientAuthenticationMethod::new)
                .forEach(methods::add);

        String id = client.getId(), clientId  = client.getClientId();

        return RegisteredClient
                .withId(id != null ? id : UUID.randomUUID().toString())
                .clientId(clientId != null ? clientId : UUID.randomUUID().toString())
                .clientSecret(client.getSecret())
                .clientName(client.getName())
                .clientAuthenticationMethods(authenticationMethods)
                .authorizationGrantTypes(grantTypes)
                .scopes(scopes)
                .redirectUris(redirectUris)
                .build();
    }

    default Client fromRegisteredClient(RegisteredClient client) {

        Set<GrantType> grantTypes = client.getAuthorizationGrantTypes()
                .stream()
                .map(AuthorizationGrantType::getValue)
                .collect(Collectors.toSet())
                .stream()
                .map(grantType-> GrantType.builder().grantType(grantType).build())
                .collect(Collectors.toSet());
        Set<Scope> scopes = client.getScopes()
                .stream()
                .map(scope -> Scope.builder().scope(scope).build())
                .collect(Collectors.toSet());

        Set<AuthenticationMethod> authenticationMethods = client.getClientAuthenticationMethods()
                .stream()
                .map(ClientAuthenticationMethod::getValue)
                .collect(Collectors.toSet())
                .stream()
                .map(method -> AuthenticationMethod.builder().method(method).build())
                .collect(Collectors.toSet());

        TokenSettings tokenSettings = TokenSettings.builder()
                .format(client.getTokenSettings().getAccessTokenFormat().getValue())
                .accessTokenTTL((int) client.getTokenSettings().getAccessTokenTimeToLive().toMinutes())
                .build();

        Client clientToSave = Client.builder()
                .clientId(client.getClientId())
                .name(client.getClientName())
                .secret(client.getClientSecret())
                .grantTypes(grantTypes)
                .authenticationMethods(authenticationMethods)
                .scopes(scopes)
                .tokenSettings(tokenSettings)
                .build();

        client.getRedirectUris()
                .forEach(uri -> {
                    clientToSave.addRedirectUris(Set.of(RedirectURI.builder().uri(uri).build()));
                });

        return clientToSave;
    }

} 
