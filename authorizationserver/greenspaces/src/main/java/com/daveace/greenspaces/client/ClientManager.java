package com.daveace.greenspaces.client;

import com.daveace.greenspaces.authenticationmethod.AuthenticationMethod;
import com.daveace.greenspaces.authenticationmethod.AuthenticationMethodService;
import com.daveace.greenspaces.granttypes.GrantType;
import com.daveace.greenspaces.granttypes.GrantTypeService;
import com.daveace.greenspaces.redirecturi.RedirectURI;
import com.daveace.greenspaces.redirecturi.RedirectUriService;
import com.daveace.greenspaces.scope.Scope;
import com.daveace.greenspaces.scope.ScopeService;
import com.daveace.greenspaces.tokensettings.TokenSettings;
import com.daveace.greenspaces.tokensettings.TokenSettingsService;

import java.util.Set;

public interface ClientManager {


    default TokenSettings createTokenSettings(
            Client client,
            Client clientToUpdate,
            TokenSettingsService settingsService) {
        TokenSettings tokenSettings = client.getTokenSettings();
        tokenSettings.setClient(clientToUpdate);
        return settingsService.save(tokenSettings);
    }

    default Set<RedirectURI> createRedirectURIS(
            Client client,
            Client clientToUpdate,
            RedirectUriService redirectUriService) {
        Set<RedirectURI> redirectUris = client.getRedirectUris();
        redirectUris.forEach(redirectUri -> redirectUri.setClient(clientToUpdate));
        return redirectUriService.saveAll(redirectUris);
    }

    default Set<GrantType> createGrantTypes(
            Client client,
            Client clientToUpdate,
            GrantTypeService grantTypeService) {
        Set<GrantType> grantTypesToSave = client.getGrantTypes();
        grantTypesToSave.forEach(grantType -> grantType.setClient(clientToUpdate));
        return grantTypeService.saveAll(grantTypesToSave);
    }

    default Set<AuthenticationMethod> createAuthenticationMethods(
            Client client,
            Client clientToUpdate,
            AuthenticationMethodService authenticationMethodService){
        Set<AuthenticationMethod> authenticationMethodsToSave = client.getAuthenticationMethods();
        authenticationMethodsToSave.forEach(method -> method.setClient(clientToUpdate));
        return authenticationMethodService.saveAll(authenticationMethodsToSave);
    }

    default Set<Scope> createScopes(
            Client client,
            Client clientToUpdate,
            ScopeService scopeService) {
        Set<Scope> scopes = client.getScopes();
        scopes.forEach(scope -> scope.setClient(clientToUpdate));
        return scopeService.saveAll(scopes);
    }

}

