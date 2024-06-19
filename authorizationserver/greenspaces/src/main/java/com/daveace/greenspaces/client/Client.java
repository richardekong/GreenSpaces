package com.daveace.greenspaces.client;

import com.daveace.greenspaces.authenticationmethod.AuthenticationMethod;
import com.daveace.greenspaces.entity.*;
import com.daveace.greenspaces.granttypes.GrantType;
import com.daveace.greenspaces.redirecturi.RedirectURI;
import com.daveace.greenspaces.scope.Scope;
import com.daveace.greenspaces.tokensettings.TokenSettings;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.daveace.greenspaces.util.Constants.*;
import static com.daveace.greenspaces.util.Regexp.ALPHA_NUM_REGEX;
import static com.daveace.greenspaces.util.Regexp.PASSWORD_REGEXP;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Accessors(chain = true)
@Entity
@Table(name = "Clients", uniqueConstraints = @UniqueConstraint(columnNames = ("name")))
public class Client extends BaseEntity<Client> {

    private String clientId;

    @Pattern(regexp = ALPHA_NUM_REGEX, message = INVALID_NAME)
    private String name;

    @Pattern(regexp = PASSWORD_REGEXP, message = INVALID_PASSWORD)
    private String secret;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Size(min = 1, message = MIN_AUTHENTICATION_METHODS)
    @JsonManagedReference
    private Set<AuthenticationMethod> authenticationMethods;

    @Size(min = 1, message = MIN_GRANT_TYPES)
    @OneToMany(mappedBy="client",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<GrantType> grantTypes;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client", cascade = CascadeType.ALL)
    @Size(min = 1, message = MIN_REDIRECT_URI)
    @JsonManagedReference
    private Set<RedirectURI> redirectUris;

    @OneToMany(mappedBy = "client", fetch= FetchType.EAGER,cascade = CascadeType.ALL)
    @Size(min = 1, message = MIN_SCOPE)
    @JsonManagedReference
    private Set<Scope> scopes;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference
    private TokenSettings tokenSettings;

    @PrePersist
    public void init(){
        clientId = UUID.randomUUID().toString();
    }

    public void addAuthenticationMethods(Collection<AuthenticationMethod>methods){
        if (authenticationMethods == null){
            authenticationMethods = new HashSet<>();
        }
        methods.forEach(method -> {
            authenticationMethods.add(method);
            method.setClient(this);
        });
    }

    public void addRedirectUris(Collection<RedirectURI> redirectUris) {
        if (this.redirectUris == null) {
            this.redirectUris = new HashSet<>();
        }
        redirectUris.forEach(redirectUri -> {
            this.redirectUris.add(redirectUri);
            redirectUri.setClient(this);
        });
    }

    public void addScopes(Collection<Scope> scopes){
        if (this.scopes == null){
            this.scopes = new HashSet<>();
        }
        scopes.forEach(scope ->{
            this.scopes.add(scope);
            scope.setClient(this);
        });
    }

    public void addGrantTypes(Collection<GrantType>grantTypes){
        if (this.grantTypes == null){
            this.grantTypes = new HashSet<>();
        }
        grantTypes.forEach(grantType ->{
            this.grantTypes.add(grantType);
            grantType.setClient(this);
        });
    }

    public void setTokenSettings(TokenSettings settings) {
            this.tokenSettings = settings;
            settings.setClient(this);

    }

    public ClientDTO toDTO() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(this.id);
        clientDTO.setClientId(this.clientId);
        clientDTO.setName(this.name);
        clientDTO.setAuthenticationMethods(this.authenticationMethods);
        clientDTO.setGrantTypes(this.grantTypes);
        clientDTO.setRedirectUris(this.redirectUris);
        clientDTO.setScopes( this.scopes);
        clientDTO.setTokenSettings(this.tokenSettings);
        return clientDTO;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id:'" + id + '\'' +
                ", clientId:'" + clientId + '\'' +
                ", name:'" + name + '\'' +
                ", authenticationMethods:'" + authenticationMethods + '\'' +
                ", scopes:" + scopes +
                ", grantTypes:" + grantTypes +
                ", redirectUris:" + redirectUris +
                ", tokenSettings:" + tokenSettings +
                '}';
    }

}
