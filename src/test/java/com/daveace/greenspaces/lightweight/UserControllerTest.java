package com.daveace.greenspaces.lightweight;

import com.daveace.greenspaces.authority.Authority;
import com.daveace.greenspaces.authority.AuthorityService;
import com.daveace.greenspaces.role.Role;
import com.daveace.greenspaces.role.RoleService;
import com.daveace.greenspaces.user.User;
import com.daveace.greenspaces.user.UserController;
import com.daveace.greenspaces.user.UserService;
import com.daveace.greenspaces.util.UserTestUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.daveace.greenspaces.util.Constants.*;
import static com.daveace.greenspaces.util.ContentTestUtil.createContent;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock(strictness = Mock.Strictness.LENIENT)
    UserService userService;

    @Mock(strictness = Mock.Strictness.LENIENT)
    PasswordEncoder passwordEncoder;

    @Mock(strictness = Mock.Strictness.LENIENT)
    AuthorityService authorityService;

    @Mock(strictness = Mock.Strictness.LENIENT)
    RoleService roleService;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    User user;

    String[] userProps = {
            "id", "username", "roles", "enabled",
            "credentialsNonExpired", "accountNonExpired", "accountNonLocked"
    };

    String [] roleProps = new String [] {"id","role","authorities"};

    @BeforeEach
    void setUp() {
        user = UserTestUtils.user;

        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();

    }

    @Test
    @DisplayName("Verify user can be created")
    void verifyUserCanBeCreated() throws Exception {

        given(userService.save(any())).willReturn(user);

        String content = createContent(user);

        mockMvc.perform(post(USERS_PATH).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.keys()", containsInAnyOrder(userProps)))
                .andExpect(jsonPath("$.id", is(user.getId())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.enabled", is(true)))
                .andExpect(jsonPath("$.credentialsNonExpired", is(true)))
                .andExpect(jsonPath("$.accountNonExpired", is(true)))
                .andExpect(jsonPath("$.accountNonLocked", is(true)));

    }

    @Test
    @DisplayName("Verify user can be retrieved by id")
    void verifyUserCanBeRetrievedById() throws Exception {
        given(userService.findUserById(any())).willReturn(user);

        mockMvc.perform(get("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keys()", containsInAnyOrder(userProps)))
                .andExpect(jsonPath("$.id", is(user.getId())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.enabled", is(true)))
                .andExpect(jsonPath("$.credentialsNonExpired", is(true)))
                .andExpect(jsonPath("$.accountNonExpired", is(true)))
                .andExpect(jsonPath("$.accountNonLocked", is(true)));;
    }

    @Test
    @DisplayName("Verify that all users can be retrieved")
    void verifyThatAllUsersCanBeRetrieved() throws Exception {
        given(userService.findAllUsers()).willReturn(List.of(user));

        mockMvc.perform(get(USERS_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", notNullValue()))
                .andExpect(jsonPath("$[0].keys()", containsInAnyOrder(userProps)))
                .andExpect(jsonPath("$[0].id", is(user.getId())))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())))
                .andExpect(jsonPath("$[0].enabled", is(true)))
                .andExpect(jsonPath("$[0].credentialsNonExpired", is(true)))
                .andExpect(jsonPath("$[0].accountNonExpired", is(true)))
                .andExpect(jsonPath("$[0].accountNonLocked", is(true)));
    }

    @Test
    @DisplayName("Verify that a user's roles can be retrieved")
    void verifyThatUserRolesCanBeRetrievedById() throws Exception {
        List<Role> roles = new ArrayList<>(user.getRoles());

        given(userService.findRolesById(any())).willReturn(roles);

        mockMvc.perform(get(USER_PATH + ROLES_PATH, user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].keys()", containsInAnyOrder(roleProps)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].role", is("ADMIN")))
                .andExpect(jsonPath("$[0].authorities[0].authority", notNullValue()));

    }

    @Test
    @DisplayName("Verify that a user's role can be retrieved by ids")
    void verifyThatUserRoleCanBeRetrievedById() throws Exception {
        given(userService.findUserById(any())).willReturn(user);

        mockMvc.perform(get(USER_PATH + ROLES_PATH + "/{roleId}", user.getId(), 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keys()", containsInAnyOrder(roleProps)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.role", is("ADMIN")))
                .andExpect(jsonPath("$.authorities", notNullValue()));
    }

    @Test
    @DisplayName("Verify that user's authority can be retrieved by ids")
    void verifyThatUsersAuthorityCanBeRetrievedById() throws Exception {
        given(userService.findUserById(any())).willReturn(user);

        mockMvc.perform(get(USER_PATH +
                                ROLES_PATH +
                                "/{roleId}" +
                                AUTHORITIES_PATH +
                                "/{authorityId}",
                        user.getId(), 1, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keys()", containsInAnyOrder("id","authority")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.authority", is("read")));
    }

    @Test
    @DisplayName("Verify that user can retrieve authorities by ids")
    void verifyThatUserCanRetrieveAuthoritiesById() throws Exception {

        given(userService.findAuthoritiesById(any())).willReturn(anyList());

        mockMvc.perform(get(USER_PATH + ROLES_PATH + AUTHORITIES_PATH, user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", notNullValue()));
    }

    @Test
    @DisplayName("Verify that user can add roles")
    void verifyThatUserCanAddRoles() throws Exception {

        Set<Role> roles = Set.of(
                Role.builder().id(2).role(Role.Roles.USER.getRole()).build()
        );

        given(userService.findUserById(any())).willReturn(user);

        given(roleService.saveAll(roles)).willReturn(roles);

        String json = createContent(roles);

        mockMvc.perform(patch(USER_PATH + ROLES_PATH, user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Verify that user can add authorities")
    void verifyThatUserCanAddAuthorities() throws Exception {

        Set<Authority> authorities = Set.of(
                Authority.builder()
                        .id(2)
                        .authority("update")
                        .build()
        );

        given(userService.findUserById(any())).willReturn(user);

        given(authorityService.saveAll(anySet())).willReturn(authorities);

        String json = createContent(authorities);

        mockMvc.perform(patch(USER_PATH + ROLES_PATH + "/{roleId}" + AUTHORITIES_PATH, user.getId(), 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Verify that user's password can be reset")
    void verifyThatUserPasswordCanBeReset() throws Exception {

        given(userService.findUserById(any())).willReturn(user);

        User data = User.builder()
                .username(user.getUsername())
                .password("P@55w07d4t35t")
                .build();

        String json = createContent(data);

        mockMvc.perform(patch(USER_ACCOUNT_PASSWORD_RESET_PATH, user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Verify that user account status can be updated")
    void verifyThatUserAccountStatusCanBeUpdated() throws Exception {

        given(userService.findUserById(any())).willReturn(user);

        var data = User.builder()
                .username(user.getUsername())
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();
        String json = createContent(data);

        mockMvc.perform(patch(USER_ACCOUNT, user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Verify that user can be deleted")
    void verifyThatUserCanBeDeleted() throws Exception {

        mockMvc.perform(delete(USER_PATH, user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
}

