package com.daveace.greenspaces.docs;


import com.daveace.greenspaces.authenticationmethod.AuthenticationMethodService;
import com.daveace.greenspaces.client.Client;
import com.daveace.greenspaces.client.ClientController;
import com.daveace.greenspaces.client.ClientService;
import com.daveace.greenspaces.granttypes.GrantType;
import com.daveace.greenspaces.granttypes.GrantTypeService;
import com.daveace.greenspaces.redirecturi.RedirectUriService;
import com.daveace.greenspaces.scope.ScopeService;
import com.daveace.greenspaces.tokensettings.TokenSettings;
import com.daveace.greenspaces.tokensettings.TokenSettingsService;
import com.daveace.greenspaces.util.ClientTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static com.daveace.greenspaces.util.Constants.*;
import static com.daveace.greenspaces.util.ContentTestUtil.createContent;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(ClientController.class)
public class ClientControllerDocsTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClientService clientService;

    @MockBean
    AuthenticationMethodService authenticationMethodService;

    @MockBean
    RedirectUriService redirectUriService;

    @MockBean
    GrantTypeService grantTypeService;

    @MockBean
    ScopeService scopeService;

    @MockBean
    TokenSettingsService settingsService;

    @MockBean
    PasswordEncoder passwordEncoder;

    Client client;

    @BeforeEach
    void setUp() {
        client = ClientTestUtils.client;
    }

    @Test
    @DisplayName("Verify that client create post request responds with 201")
    @WithMockUser(username = "admin@greenspaces.com", roles = "ADMIN")
    void verifyRequestToCreateClient() throws Exception {
        given(clientService.save(any(Client.class))).willReturn(client);

        String json = createContent(client);

        ConstrainedFields fields = new ConstrainedFields(Client.class);

        mockMvc.perform(post(CLIENTS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(client.getId())))
                .andExpect(jsonPath("$.clientId", is(client.getClientId())))
                .andExpect(jsonPath("$.authenticationMethods", notNullValue()))
                .andExpect(jsonPath("$.grantTypes", notNullValue()))
                .andExpect(jsonPath("$.redirectUris", notNullValue()))
                .andExpect(jsonPath("$.scopes[0].scope", containsString("openid")))
                .andExpect(jsonPath("$.tokenSettings.format", is("self-contained")))
                .andDo(document("Creating a Client",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("clientId").ignored(),
                                fields.withPath("name").description("Desired client name").type(String.class),
                                fields.withPath("secret").description("Desired client secret").type(String.class),
                                fieldWithPath("authenticationMethods").description("Authentication Methods of the client").type(Set.class),
                                fieldWithPath("authenticationMethods.[].id").ignored().description("Id of an authentication method of the client").type(Integer.class),
                                fieldWithPath("authenticationMethods.[].method").description("Authentication method name of an authentication method of the client").type(String.class),
                                fields.withPath("grantTypes").description("Desired list/set of auth2.0 authentication grant types").type(Set.class),
                                fields.withPath("grantTypes.[].id").ignored(),
                                fields.withPath("grantTypes.[].grantType").description("authorization grant type name of a grant type within a list of grant types").type(String.class),
                                fields.withPath("redirectUris").description("Desired list/set of redirect URIs").type(String.class),
                                fields.withPath("redirectUris.[].id").ignored(),
                                fields.withPath("redirectUris.[].uri").description("redirect uri within a list of redirect uris").type(String.class),
                                fields.withPath("scopes").description("Desired oauth2.0 authentication scopes").type(Set.class),
                                fields.withPath("scopes.[].id").ignored(),
                                fields.withPath("scopes.[].scope").description("Scope's name within a list of scopes").type(String.class),
                                fields.withPath("tokenSettings").description("Desired oauth2.0 token settings").type(TokenSettings.class),
                                fields.withPath("tokenSettings.id").ignored(),
                                fields.withPath("tokenSettings.format").description("Format of the token from the authorization server"),
                                fields.withPath("tokenSettings.accessTokenTTL").description("Time to live or expiry time of an access token")
                        ),
                        responseFields(
                                fieldWithPath("id").description("id of created client"),
                                fieldWithPath("clientId").description("client id of created client"),
                                fieldWithPath("name").description("name of created client"),
                                fieldWithPath("authenticationMethods").description("Authentication Methods of the client").type(Set.class),
                                fieldWithPath("authenticationMethods.[].id").ignored().description("Id of an authentication method of the client").type(Integer.class),
                                fieldWithPath("authenticationMethods.[].method").description("Authentication method name of an authentication method of the client").type(String.class),
                                fieldWithPath("grantTypes").description("Grant types of the created clients"),
                                fieldWithPath("grantTypes.[].id").ignored(),
                                fieldWithPath("grantTypes.[].grantType").description("authorization grant type name of a grant type within a list of grant types").type(String.class),
                                fieldWithPath("redirectUris").description("Redirect URIs of the created client"),
                                fieldWithPath("redirectUris.[].id").ignored(),
                                fieldWithPath("redirectUris.[].uri").description("redirect uri within a list of redirect uris").type(String.class),
                                fieldWithPath("scopes").description("Oauth2.0 Scopes of the create client"),
                                fieldWithPath("scopes.[].id").ignored(),
                                fieldWithPath("scopes.[].scope").description("Scope's name within a list of scopes").type(String.class),
                                fieldWithPath("tokenSettings").description("Token Settings of the create client"),
                                fieldWithPath("tokenSettings.id").ignored(),
                                fieldWithPath("tokenSettings.format").description("Format of the token from the authorization server"),
                                fieldWithPath("tokenSettings.accessTokenTTL").description("Time to live or expiry time of an access token")
                        )
                ));
    }

    @Test
    @DisplayName("Verify that a client can be retrieved by id")
    @WithMockUser(username = "admin@greenspaces.com", roles = "ADMIN")
    void verifyThatClientCanBeRetrievedById() throws Exception {
        given(clientService.findClientById(any())).willReturn(client);

        mockMvc.perform(get(CLIENT_PATH, client.getId())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(client.getId())))
                .andExpect(jsonPath("$.clientId", is(client.getClientId())))
                .andExpect(jsonPath("$.authenticationMethods", notNullValue()))
                .andExpect(jsonPath("$.grantTypes", notNullValue()))
                .andExpect(jsonPath("$.redirectUris", notNullValue()))
                .andExpect(jsonPath("$.scopes[0].scope", containsString("openid")))
                .andExpect(jsonPath("$.tokenSettings.format", is("self-contained")))
                .andDo(document("Retrieving a Client",
                        pathParameters(parameterWithName("id").description("UUID of client to retrieve")),
                        responseFields(
                                fieldWithPath("id").description("id of retrieved client"),
                                fieldWithPath("clientId").description("client id of retrieved client"),
                                fieldWithPath("name").description("name of retrieved client"),
                                fieldWithPath("authenticationMethods").description("Authentication Methods of the retrieved client").type(Set.class),
                                fieldWithPath("authenticationMethods.[].id").description("Id of an authentication method of the retrieved client").type(Integer.class),
                                fieldWithPath("authenticationMethods.[].method").description("Authentication method name of an authentication method in the retrieved client").type(String.class),
                                fieldWithPath("grantTypes").description("Grant types of the retrieved clients"),
                                fieldWithPath("grantTypes.[].id").ignored(),
                                fieldWithPath("grantTypes.[].grantType").description("authorization grant type name of a grant type within a list of grant types").type(String.class),
                                fieldWithPath("redirectUris").description("Redirect URIs of the retrieved client"),
                                fieldWithPath("redirectUris.[].id").ignored(),
                                fieldWithPath("redirectUris.[].uri").description("redirect uri within a list of redirect uris").type(String.class),
                                fieldWithPath("scopes").description("Oauth2.0 Scopes of the retrieved client"),
                                fieldWithPath("scopes.[].id").ignored(),
                                fieldWithPath("scopes.[].scope").description("Scope's name within a list of scopes").type(String.class),
                                fieldWithPath("tokenSettings").description("Token Settings of the retrieved client"),
                                fieldWithPath("tokenSettings.id").description("id of the token setting from the authorization server"),
                                fieldWithPath("tokenSettings.format").description("Format of the token from the authorization server"),
                                fieldWithPath("tokenSettings.accessTokenTTL").description("Time to live or expiry time of an access token")
                        )
                ));
    }

    @Test
    @DisplayName("Verify that all clients can be read")
    @WithMockUser(username = "admin@greenspaces.com", roles = "ADMIN")
    void verifyThatAllClientsBeRetrieved() throws Exception {
        List<Client> clients = List.of(client);
        given(clientService.findAllClients()).willReturn(clients);

        mockMvc.perform(get(CLIENTS_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", notNullValue()))
                .andExpect(jsonPath("$[0].id", is(client.getId())))
                .andExpect(jsonPath("$[0].clientId", is(client.getClientId())))
                .andExpect(jsonPath("$[0].authenticationMethods", notNullValue()))
                .andExpect(jsonPath("$[0].grantTypes", notNullValue()))
                .andExpect(jsonPath("$[0].redirectUris", notNullValue()))
                .andExpect(jsonPath("$[0].scopes[0].scope", containsString("openid")))
                .andExpect(jsonPath("$[0].tokenSettings.format", is("self-contained")))
                .andDo(document("Retrieving All Clients",
                        responseFields(
                                fieldWithPath("[].id").description("id of retrieved client within a list of clients").type(Integer.class),
                                fieldWithPath("[].clientId").description("client id of a retrieved client within a list of clients").type(String.class),
                                fieldWithPath("[].name").description("name of a retrieved client within a list of clients").type(String.class),
                                fieldWithPath("[].authenticationMethods").description("Authentication Methods of a retrieved client within a list of clients").type(Set.class),
                                fieldWithPath("[].authenticationMethods.[].id").description("id of one of the authentication methods of a retrieved client within a list of clients").type(Integer.class),
                                fieldWithPath("[].authenticationMethods.[].method").description("One of the authentication methods' name of a retrieved client within a list of clients").type(String.class),
                                fieldWithPath("[].grantTypes").description("Grant types of a retrieved client within a list of clients").type(Set.class),
                                fieldWithPath("[].grantTypes.[].id").description("id of a Grant type of the retrieved clients").type(Integer.class),
                                fieldWithPath("[].grantTypes.[].grantType").description("Grant type name of one of the retrieved clients").type(String.class),
                                fieldWithPath("[].redirectUris").description("Redirect URIs of a retrieved client within a list of clients").type(Set.class),
                                fieldWithPath("[].redirectUris.[].id").ignored(),
                                fieldWithPath("[].redirectUris.[].uri").description("redirect uri within a list of redirect uris").type(String.class),
                                fieldWithPath("[].scopes").description("Oauth2.0 Scopes of a retrieved client within a list of clients").type(Set.class),
                                fieldWithPath("[].scopes.[].id").ignored(),
                                fieldWithPath("[].scopes.[].scope").description("Scope's name within a list of scopes").type(String.class),
                                fieldWithPath("[].tokenSettings").description("Token Settings of a retrieved client within a list of clients").type(TokenSettings.class),
                                fieldWithPath("[].tokenSettings.id").description("id of a token setting of a retrieved client within a list of clients").type(Integer.class),
                                fieldWithPath("[].tokenSettings.format").description("Format of the token from the authorization server").type(String.class),
                                fieldWithPath("[].tokenSettings.accessTokenTTL").description("Time to live or expiry time of an access token").type(Integer.class)
                        )
                ));

    }

    @Test
    @DisplayName("Verify that a client can be updated")
    @WithMockUser(username = "admin@greenspaces.com", roles = "ADMIN")
    void verifyThatClientCanBeUpdated() throws Exception {
        String id = client.getId();
        String name = client.getName();
        String json = createContent(client);

        ConstrainedFields fields = new ConstrainedFields(Client.class);
        given(clientService.findClientById(any())).willReturn(client);
        given(clientService.update(name, c -> c.setSecret(passwordEncoder.encode("L3tm31nw1th0ut9p@55w07d")))).willReturn(CLIENT_UPDATED);
        mockMvc.perform(patch(CLIENT_PATH, id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(document("Updating a Client",
                        pathParameters(
                                parameterWithName("id").description("UUID of the client to update")
                        ),
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("clientId").ignored(),
                                fields.withPath("name").description("Desired client name of client to be updated").type(String.class),
                                fields.withPath("secret").description("Desired client secret of client to be updated").type(String.class),
                                fields.withPath("authenticationMethods").description("Desired oauth2.0 authentication methods of client to be updated").type(Set.class),
                                fields.withPath("authenticationMethods.[].id").description("Id of desired oauth2.0 authentication methods of client to be updated").ignored().type(Integer.class),
                                fields.withPath("authenticationMethods.[].method").description("Authentication method name of desired oauth2.0 authentication methods of client to be updated").type(String.class),
                                fields.withPath("grantTypes").description("Desired list/set of auth2.0 authentication grant types of client to be updated").type(Set.class),
                                fields.withPath("grantTypes.[].id").ignored(),
                                fields.withPath("grantTypes.[].grantType").description("Authorization grant type of the client to be updated"),
                                fields.withPath("redirectUris").description("Desired list/set of redirect URIs of client to be updated").type(String.class),
                                fields.withPath("redirectUris.[].id").ignored(),
                                fields.withPath("redirectUris.[].uri").description("A redirect Uri of the client to be updated").type(String.class),
                                fields.withPath("scopes").description("Desired oauth2.o authentication scopes of client to be updated").type(Set.class),
                                fields.withPath("scopes.[].id").ignored(),
                                fields.withPath("scopes.[].scope").description("A scope of the client to be updated").type(String.class),
                                fields.withPath("tokenSettings").description("Desired oauth2.0 token settings of client to be updated").type(TokenSettings.class),
                                fields.withPath("tokenSettings.id").ignored(),
                                fields.withPath("tokenSettings.format").description("Format of the access token of the client to be updated").type(String.class),
                                fields.withPath("tokenSettings.accessTokenTTL").description("Expiry time of the access token of the client to be updated").type(Integer.class)
                        )
                ));
    }

    @Test
    @DisplayName("Verify that client can be deleted")
    @WithMockUser(username = "admin@greenspaces.com", roles = "ADMIN")
    void verifyThatClientCanBeDeleted() throws Exception {

        mockMvc.perform(delete(CLIENT_PATH, client.getId())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("Deleting a Client",
                        pathParameters(
                                parameterWithName("id").description("UUID of client to delete")
                        )
                ));
    }


}
