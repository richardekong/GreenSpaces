package com.daveace.greenspaces.integratedtest;

import com.daveace.greenspaces.authenticationmethod.AuthenticationMethodRepo;
import com.daveace.greenspaces.client.ClientRepo;
import com.daveace.greenspaces.granttypes.GrantTypeRepo;
import com.daveace.greenspaces.redirecturi.RedirectUriRepo;
import com.daveace.greenspaces.scope.ScopeRepo;
import com.daveace.greenspaces.tokensettings.TokenSettingsRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static com.daveace.greenspaces.util.Constants.*;
import static com.daveace.greenspaces.util.ContentTestUtil.createContent;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class FullClientContainerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ClientRepo clientRepo;

    @Autowired
    AuthenticationMethodRepo authenticationMethodRepo;

    @Autowired
    RedirectUriRepo redirectUriRepo;

    @Autowired
    GrantTypeRepo grantTypeRepo;

    @Autowired
    ScopeRepo scopeRepo;

    @Autowired
    TokenSettingsRepo settingsRepo;

    public static final Map<String, Object> DATA = Map.of(
            "name", "Green-dynamics",
            "secret", "53cr3t50$9feW1t6m3"
    );

    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @Test
    @DisplayName("Verify that client can be create by an authenticated user")
    @Order(1)
    void canCreateClient() throws Exception {
        String json = createContent(DATA);

        mockMvc.perform(post(CLIENTS_PATH)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.id", hasLength(36)))
                .andExpect(jsonPath("$.clientId", notNullValue()))
                .andExpect(jsonPath("$.clientId", hasLength(36)))
                .andExpect(jsonPath("$.name", is("Green-dynamics")));
    }

    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @DisplayName("Verify that client can't be created without a valid CSRF TOKEN")
    @Test
    void cantCreateClientWithOutCSRFToken() throws Exception {

        String json = createContent(DATA);

        mockMvc.perform(post(CLIENTS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Verify that anonymous user can't create a client record")
    void anonymousUserCantCreateClient() throws Exception {
        String json = createContent(DATA);

        mockMvc.perform(post(CLIENTS_PATH)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @DisplayName("Verify that admin can retrieve client records")
    @Order(2)
    void adminCanRetrieveClient() throws Exception {

        String id = "76e09d7a-1095-4b47-80c5-5f8bcba70f1d";

        mockMvc.perform(get(CLIENT_PATH, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.clientId", notNullValue()))
                .andExpect(jsonPath("$.authenticationMethods[0].method", notNullValue()))
                .andExpect(jsonPath("$.grantTypes", notNullValue()))
                .andExpect(jsonPath("$.redirectUris", notNullValue()))
                .andExpect(jsonPath("$.scopes[0].scope", containsString("read")))
                .andExpect(jsonPath("$.tokenSettings.format", is("self-contained")));
    }

    @Test
    @DisplayName("Verify that anonymous user can't retrieve all client record")
    void anonymousUserCantRetrieveAllClient() throws Exception {
        mockMvc.perform(get(CLIENTS_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Verify that admin can retrieve all clients record")
    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @Order(3)
    void adminCanRetrieveAllClients() throws Exception {

        mockMvc.perform(get(CLIENTS_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", notNullValue()))
                .andExpect(jsonPath("$[0].id", hasLength(36)))
                .andExpect(jsonPath("$[0].clientId", hasLength(36)));
    }

    @Test
    @DisplayName("Verify that admin can update a client record")
    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @Order(4)
    void adminCanUpdateClient() throws Exception {
        String id = "76e09d7a-1095-4b47-80c5-5f8bcba70f1d";
        Map<String, String> data = Map.of(
                "secret", "P@55w07dI$53cr3t"
                , "name", "Super-Dynamics"
        );

        String json = createContent(data);
        mockMvc.perform(patch(CLIENT_PATH, id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(CLIENT_UPDATED)));
    }

    @Test
    @DisplayName("Verify that anonymous user can't update a client record")
    void anonymousUserCantUpdateClient() throws Exception {
        String id = "76e09d7a-1095-4b47-80c5-5f8bcba70f1d";
        Map<String, String> data = Map.of(
                "secret", "P@55w07dI$53cr3t"
                , "name", "Green-Dynamics"
        );

        String json = createContent(data);
        mockMvc.perform(patch(CLIENT_PATH, id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Verify that admin can delete a client record")
    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @Order(5)
    void adminCanDeleteClient() throws Exception {
        String id = "76e09d7a-1095-4b47-80c5-5f8bcba70f1d";
        mockMvc.perform(delete(CLIENT_PATH, id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(DELETED_SUCCESSFULLY)));
    }


}
