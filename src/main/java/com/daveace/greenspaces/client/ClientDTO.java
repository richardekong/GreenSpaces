package com.daveace.greenspaces.client;

import com.daveace.greenspaces.authenticationmethod.AuthenticationMethod;
import com.daveace.greenspaces.granttypes.GrantType;
import com.daveace.greenspaces.redirecturi.RedirectURI;
import com.daveace.greenspaces.scope.Scope;
import com.daveace.greenspaces.tokensettings.TokenSettings;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ClientDTO {
    private String id;
    private String clientId;
    private String name;
    private Set<AuthenticationMethod> authenticationMethods;
    private Set<GrantType>grantTypes;
    private Set<RedirectURI> redirectUris;
    private Set<Scope>scopes;
    private TokenSettings tokenSettings;


    public ClientDTO() {}

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id :'" + id + '\'' +
                ", clientId :'" + clientId + '\'' +
                ", name :'" + name + '\'' +
                ", authenticationMethods :" + authenticationMethods +
                ", grantTypes :" + grantTypes +
                ", redirectUris :" + redirectUris +
                ", scopes :" + scopes +
                ", tokenSettings :" + tokenSettings +
                '}';
    }
}
