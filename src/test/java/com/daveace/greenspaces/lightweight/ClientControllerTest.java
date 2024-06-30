package com.daveace.greenspaces.lightweight;


import com.daveace.greenspaces.client.Client;
import com.daveace.greenspaces.client.ClientController;
import com.daveace.greenspaces.client.ClientService;
import com.daveace.greenspaces.granttypes.GrantTypeService;
import com.daveace.greenspaces.redirecturi.RedirectUriService;
import com.daveace.greenspaces.scope.ScopeService;
import com.daveace.greenspaces.tokensettings.TokenSettingsService;
import com.daveace.greenspaces.util.ClientTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.daveace.greenspaces.util.Constants.*;
import static com.daveace.greenspaces.util.ContentTestUtil.createContent;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    @Mock(strictness = Mock.Strictness.LENIENT)
    ClientService clientService;

    @Mock(strictness = Mock.Strictness.LENIENT)
    RedirectUriService redirectUriService;

    @Mock(strictness = Mock.Strictness.LENIENT)
    GrantTypeService grantTypeService;

    @Mock(strictness = Mock.Strictness.LENIENT)
    ScopeService scopeService;

    @Mock(strictness = Mock.Strictness.LENIENT)
    TokenSettingsService settingsService;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    ClientController clientController;

    MockMvc mockMvc;

    Client client;

    @BeforeEach
    void setUp() {
        client = ClientTestUtils.client;
        mockMvc = MockMvcBuilders
                .standaloneSetup(clientController)
                .build();
    }

    @Test
    @DisplayName("Verify that client create post request responds with 201")
    void verifyRequestToCreateClient() throws Exception {
        given(clientService.save(any(Client.class))).willReturn(client);

        String json = createContent(client);

        mockMvc.perform(post(CLIENTS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(client.getId())))
                .andExpect(jsonPath("$.clientId", is(client.getClientId())))
                .andExpect(jsonPath("$.authenticationMethod", is("client-secret-basic")))
                .andExpect(jsonPath("$.grantTypes", notNullValue()))
                .andExpect(jsonPath("$.redirectUris", notNullValue()))
                .andExpect(jsonPath("$.scopes[0].scope", containsString("openid")))
                .andExpect(jsonPath("$.tokenSettings.format", is("self-contained")));
    }

    @Test
    @DisplayName("Verify that a client can be retrieved by id")
    void verifyThatClientCanBeRetrievedById() throws Exception {
        given(clientService.findClientById(any())).willReturn(client);

        mockMvc.perform(get(CLIENT_PATH, client.getId())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(client.getId())))
                .andExpect(jsonPath("$.clientId", is(client.getClientId())))
                .andExpect(jsonPath("$.authenticationMethod", is("client-secret-basic")))
                .andExpect(jsonPath("$.grantTypes", notNullValue()))
                .andExpect(jsonPath("$.redirectUris", notNullValue()))
                .andExpect(jsonPath("$.scopes[0].scope", containsString("openid")))
                .andExpect(jsonPath("$.tokenSettings.format", is("self-contained")));
    }

    @Test
    @DisplayName("Verify that all clients can be read")
    void verifyThatAllClientsBeRetrieved() throws Exception {
        List<Client> clients = List.of(client);
        given(clientService.findAllClients()).willReturn(clients);

        mockMvc.perform(get(CLIENTS_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", notNullValue()))
                .andExpect(jsonPath("$[0].id", is(client.getId())))
                .andExpect(jsonPath("$[0].clientId", is(client.getClientId())))
                .andExpect(jsonPath("$[0].authenticationMethod", is("client-secret-basic")))
                .andExpect(jsonPath("$[0].grantTypes", notNullValue()))
                .andExpect(jsonPath("$[0].redirectUris", notNullValue()))
                .andExpect(jsonPath("$[0].scopes[0].scope", containsString("openid")))
                .andExpect(jsonPath("$[0].tokenSettings.format", is("self-contained")));

    }

    @Test
    @DisplayName("Verify that a client can be updated")
    void verifyThatClientCanBeUpdated() throws Exception {
        String id = client.getId();
        String name = client.getName();
        String json = createContent(client);

        given(clientService.findClientById(any())).willReturn(client);
        given(clientService.update(name, c -> c.setSecret(passwordEncoder.encode("L3tm31nw1th0ut9p@55w07d")))).willReturn(CLIENT_UPDATED);
        mockMvc.perform(patch(CLIENT_PATH, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Verify that client can be deleted")
    void verifyThatClientCanBeDeleted() throws Exception {

        mockMvc.perform(delete(CLIENT_PATH, client.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}

