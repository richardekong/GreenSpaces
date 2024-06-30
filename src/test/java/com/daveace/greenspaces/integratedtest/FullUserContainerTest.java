package com.daveace.greenspaces.integratedtest;


import com.daveace.greenspaces.authority.AuthorityRepo;
import com.daveace.greenspaces.role.RoleRepo;
import com.daveace.greenspaces.user.User;
import com.daveace.greenspaces.user.UserRepo;
import com.daveace.greenspaces.util.UserTestUtils;
import org.junit.jupiter.api.BeforeEach;
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
public class FullUserContainerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthorityRepo authorityRepo;

    @Autowired
    RoleRepo roleRepo;

    User user;

    @BeforeEach
    void setup() {
        user = UserTestUtils
                .user
                .setRoles(null);
    }

    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @Test
    @Order(1)
    void canCreateUser() throws Exception {

        String json = createContent(UserTestUtils.user);
        mockMvc.perform(post(USERS_PATH)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.id", hasLength(36)))
                .andExpect(jsonPath("$.username", is("user.test@mail.com")));
    }

    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @DisplayName("Verify that user record can't be created without a valid CSRF TOKEN")
    @Test
    void cantCreateClientWithOutCSRFToken() throws Exception {
        String json = createContent(user);

        mockMvc.perform(post(USERS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @DisplayName("Verify that user record can be retrieved by an authenticated admin")
    @Test
    @Order(2)
    void canRetrieveUser() throws Exception {
        String id = "6f13b449-71b5-4f5f-9f9e-6e5c3a23d5a2";

        mockMvc.perform(get(USER_PATH, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.username", is("admin@greenspaces.com")));
    }


    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @DisplayName("Verify that user records can be retrieved by an authenticated admin")
    @Test
    @Order(3)
    void canRetrieveUsers() throws Exception {

        mockMvc.perform(get(USERS_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0]", notNullValue()))
                .andExpect(jsonPath("$[0].id", hasLength(36)));
    }

    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @DisplayName("Verify that user roles can be retrieved")
    @Test
    @Order(4)
    void canRetrieveUserRoles() throws Exception {

        String id = "6f13b449-71b5-4f5f-9f9e-6e5c3a23d5a2";


        mockMvc.perform(get(USER_PATH + ROLES_PATH, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0]", notNullValue()))
                .andExpect(jsonPath("$[0].id", isA(Integer.class)))
                .andExpect(jsonPath("$[0].role", isA(String.class)))
                .andExpect(jsonPath("$[0].authorities", notNullValue()));

    }

    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @DisplayName("Verify that user's role can be retrieved")
    @Test
    @Order(5)
    void canRetrieveUserRole() throws Exception {

        String id = "6f13b449-71b5-4f5f-9f9e-6e5c3a23d5a2";

        mockMvc.perform(get(USER_PATH + ROLES_PATH + "/{roleId}", id, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.role", notNullValue()))
                .andExpect(jsonPath("$.role", isA(String.class)));
    }

    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @DisplayName("Verify that user's authorities can be retrieved")
    @Test
    @Order(6)
    void canRetrieveUserAuthority() throws Exception {

        String id = "6f13b449-71b5-4f5f-9f9e-6e5c3a23d5a2";

        mockMvc.perform(get(USER_PATH + ROLES_PATH + "/{roleId}" + AUTHORITIES_PATH + "/{authorityId}", id, 1, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.authority", notNullValue()))
                .andExpect(jsonPath("$.authority", isA(String.class)));

    }


    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @DisplayName("Verify that user's authorities can be retrieved")
    @Test
    @Order(7)
    void canRetrieveUserAuthorities() throws Exception {
        String id = "6f13b449-71b5-4f5f-9f9e-6e5c3a23d5a2";

        mockMvc.perform(get(USER_PATH + ROLES_PATH + AUTHORITIES_PATH, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].authority", notNullValue()))
                .andExpect(jsonPath("$[0].authority", isA(String.class)));
    }

    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @DisplayName("Verify that user's password can be reset")
    @Test
    @Order(8)
    void canResetUserPassword() throws Exception {

        String id = "6f13b449-71b5-4f5f-9f9e-6e5c3a23d5a2";

        Map<String, String> data = Map.of(
                "username", "admin@greenspaces.com",
                "password", "masterP@55w0rd"
        );

        String json = createContent(data);

        mockMvc.perform(patch(USER_ACCOUNT_PASSWORD_RESET_PATH, id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(UPDATED)));
    }

    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @DisplayName("Verify that user's account status can be updated")
    @Test
    @Order(9)
    void canUpdateUserAccountStatus() throws Exception {
        String id = "6f13b449-71b5-4f5f-9f9e-6e5c3a23d5a2";

        Map<String, String> data = Map.of(
                "username","admin@greenspaces.com",
                "isAccountNonExpired", "false",
                "isAccountNonLocked", "false",
                "isCredentialsNonExpired", "false",
                "isEnabled", "false"
        );

        String json = createContent(data);

        mockMvc.perform(patch(USER_ACCOUNT, id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(UPDATED)));

    }

    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @DisplayName("Verify that user's record can be deleted")
    @Test
    @Order(10)
    void canBeDeleted() throws Exception{

        mockMvc.perform(delete(USER_PATH, user.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(DELETED_SUCCESSFULLY)));

    }


}
