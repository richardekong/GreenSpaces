package com.daveace.greenspaces.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

import com.daveace.greenspaces.authenticationmethod.AuthenticationMethod;
import com.daveace.greenspaces.authenticationmethod.AuthenticationMethods;
import com.daveace.greenspaces.authority.Authority;
import com.daveace.greenspaces.client.Client;
import com.daveace.greenspaces.granttypes.GrantType;
import com.daveace.greenspaces.redirecturi.RedirectURI;
import com.daveace.greenspaces.role.Role;
import com.daveace.greenspaces.scope.Scope;
import com.daveace.greenspaces.tokensettings.TokenSettings;
import com.daveace.greenspaces.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class GreenSpacesUnitTest {

    private User user;
    private Set<Role> roles;
    private Set<Authority> authorities;
    private Client client;
    private Set<AuthenticationMethod> authenticationMethods;
    private Set<GrantType> grantTypes;
    private RedirectURI uris;
    private Set<Scope> scopes;
    private TokenSettings settings;

    @BeforeAll
    void init() {

        authorities = Set.of(
                Authority.builder().id(1).authority("read").role(null).build(),
                Authority.builder().id(2).authority("write").role(null).build(),
                Authority.builder().id(3).authority("delete").role(null).build()
        );

        roles = Set.of(
                Role.builder()
                        .id(1)
                        .role("ADMIN")
                        .user(null)
                        .build()
                        .add(authorities)
        );

        user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("admin.greenspaces@mail.com")
                .password("P@55w07d")
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();

        client = Client
                .builder()
                .id(UUID.randomUUID().toString())
                .clientId(UUID.randomUUID().toString())
                .name("greenspaces_client")
                .secret("my53cr3t1559fw1thy0u")
                .scopes(null)
                .scopes(null)
                .redirectUris(null)
                .authenticationMethods(authenticationMethods)
                .grantTypes(null)
                .build();

        authenticationMethods = Set.of(
                AuthenticationMethod.builder().method(
                        AuthenticationMethods.CLIENT_SECRET_BASIC.method()
                ).build()
        );

        scopes = Set.of(
                Scope.builder().scope(Scope.Scopes.EMAIL.scope()).build(),
                Scope.builder().scope(Scope.Scopes.PROFILE.scope()).build(),
                Scope.builder().scope(Scope.Scopes.OPENID.scope()).build()
        );

        grantTypes = Set.of(
                GrantType.builder().grantType("authorization_code").build(),
                GrantType.builder().grantType("refresh_token").build(),
                GrantType.builder().grantType("client_credentials").build()
        );

        uris = RedirectURI
                .builder()
                .id(1L)
                .uri("http://greenspaces.io/authorization_server")
                .build();

        settings = TokenSettings
                .builder()
                .id(1)
                .format("self-contained")
                .accessTokenTTL((int) Duration.ofHours(1).toMinutes())
                .build();

    }

    @Test
    @Order(1)
    @DisplayName("Verify that user can set role")
    void userSetsRoles() {
        // add roles
        user.add(this.roles);
        // verify that the user has roles
        assertThat(user.getRoles()).isNotEmpty();
        Set<Authority> authoritiesFromRole = roles.stream()
                .findAny()
                .orElseThrow()
                .getAuthorities();
        List<String> roles = user.getRoles().stream().map(Role::getRole).toList();
        Role role = user.getRoles().stream().findAny().orElseThrow();
        Authority authority = authorities.stream().findAny().orElseThrow();
        //verify that a role has relationship with an authority
        assertThat(authority.getRole()).isEqualTo(role);
        // verify that user has admin role
        assertThat(roles).contains("ADMIN");
        // verify that admin role has authorities
        assertThat(authoritiesFromRole).isNotEmpty();
        //verify that user has read, write and delete authorities
        assertThat(user.getRoles()
                .stream().map(Role::getAuthorities)
                .flatMap(Collection::stream)
                .map(Authority::getAuthority)
                .toList())
                .contains("read", "write", "delete");
    }

    @Test
    @Order(2)
    @DisplayName("Verify that client can set scopes")
    void clientSetsScopes() {
        client.setScopes(scopes);
        // verify that client's scopes exists
        assertNotNull(client.getScopes());
        // verify that client's scope contains these values
        assertThat(
                client
                        .getScopes()
                        .stream()
                        .map(Scope::getScope)
                        .toList())
                .contains("email", "profile", "openid");

    }

    @Test
    @Order(3)
    @DisplayName("Verify that client can set authorization grant types")
    void clientSetsAuthorizationGrantTypes() {
        client.setGrantTypes(grantTypes);
        // verify that client's grant types exists
        assertNotNull(client.getGrantTypes());
        // verify that client's grant types contains these values
        assertThat(
                client
                        .getGrantTypes()
                        .stream()
                        .map(GrantType::getGrantType)
                        .toList())
                .contains("authorization_code", "refresh_token", "client_credentials");
    }

    @Test
    @Order(4)
    @DisplayName("Verify that client can add redirect uri")
    void clientAddsRedirectUri() {
        client.addRedirectUris(Set.of(uris));
        // verify that client now has a redirect uri
        assertNotNull(client.getRedirectUris());
        // verify that client's redirect uris contains a uri
        assertThat(client.getRedirectUris()).isNotEmpty();
        // verify that client's redirect uris contains
        String uri = client.getRedirectUris()
                .stream()
                .map(RedirectURI::getUri)
                .findAny()
                .orElseThrow();
        assertEquals(uri, "http://greenspaces.io/authorization_server");
    }

    @Test
    @Order(6)
    @DisplayName("Verify that client can add authentication methods")
    void clientCanAddAuthenticationMethods() throws Exception{
        client.addAuthenticationMethods(authenticationMethods);
        //verify that client now has authentication methods
        assertNotNull(client.getAuthenticationMethods());
        //Verify that client's authentication methods contains an authentication method
        assertThat(client.getAuthenticationMethods()).isNotEmpty();
        //verify that client's authentication method contain client_secret_basic method
        Set<String> methods = client.getAuthenticationMethods()
                        .stream().map(AuthenticationMethod::getMethod)
                        .collect(Collectors.toSet());
        assertThat(methods).contains("client_secret_basic");
        //verify relationship between client and authentication method
        AuthenticationMethod method = new ArrayList<AuthenticationMethod>(client.getAuthenticationMethods()).get(0);
        assertThat(client).isEqualTo(method.getClient());
    }

    @Test
    @Order(7)
    @DisplayName("Verify that clients can configure token settings")
    void clientConfiguresTokenSettings() {
        client.setTokenSettings(settings);
        // verify that client has token settings
        assertNotNull(client.getTokenSettings());
        // verify that client's token settings
        assertEquals(client.getTokenSettings().getId(), 1);
        assertEquals(client.getTokenSettings().getFormat(), "self-contained");
        assertEquals(client.getTokenSettings().getAccessTokenTTL(), 60);

    }

    @Test
    @Order(8)
    @DisplayName("Verify that client has a relationship with token settings")
    void clientIsRelatedToTokenSettings() {
        assertNotNull(settings.getClient());
        assertEquals(settings, client.getTokenSettings());
    }

}
