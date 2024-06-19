package com.daveace.greenspaces.docs;


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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.daveace.greenspaces.util.Constants.*;
import static com.daveace.greenspaces.util.ContentTestUtil.createContent;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
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
@WebMvcTest(UserController.class)
public class UserControllerDocsTest {

    @MockBean
    UserService userService;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    AuthorityService authorityService;

    @MockBean
    RoleService roleService;

    @Autowired
    MockMvc mockMvc;

    User user;

    String[] userProps = {
            "id", "username", "roles", "enabled",
            "credentialsNonExpired", "accountNonExpired", "accountNonLocked"
    };

    String[] roleProps = new String[]{"id", "role", "authorities"};

    @BeforeEach
    void setUp() {
        user = UserTestUtils.user;
    }

    @Test
    @DisplayName("Verify user can be created")
    @WithMockUser(username = "admin@greenspaces.com", roles = "ADMIN")
    void verifyUserCanBeCreated() throws Exception {

        given(userService.save(any())).willReturn(user);

        String content = createContent(user);

        ConstrainedFields fields = new ConstrainedFields(User.class);


        mockMvc.perform(post(USERS_PATH)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.keys()", containsInAnyOrder(userProps)))
                .andExpect(jsonPath("$.id", is(user.getId())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.enabled", is(true)))
                .andExpect(jsonPath("$.credentialsNonExpired", is(true)))
                .andExpect(jsonPath("$.accountNonExpired", is(true)))
                .andExpect(jsonPath("$.accountNonLocked", is(true)))
                .andDo(document("Create User", requestFields(
                        fields.withPath("id").ignored(),
                        fields.withPath("username").description("username of the user to be save").type(String.class),
                        fields.withPath("password").description("password of the user to save").type(String.class),
                        fields.withPath("roles").description("roles of the user to be saved").type(Set.class),
                        fields.withPath("roles.[].id").description("role id of a single role within roles of the user to be saved").type(Integer.class),
                        fields.withPath("roles.[].role").description("role name of a single role within roles of a single role within roles of the user to be saved").type(String.class),
                        fields.withPath("roles.[].authorities").description("authorities within a single role of roles of the user to be saved").type(Set.class),
                        fields.withPath("roles.[].authorities.[].id").description("id of a authority within a role of roles of the user to be saved").type(Integer.class),
                        fields.withPath("roles.[].authorities.[].authority").description("authority name of an authority within a role of roles of the user to be saved").type(String.class),
                        fields.withPath("enabled").description("status describing if the user account is enabled").type(Boolean.class),
                        fields.withPath("credentialsNonExpired").description("status describing the validity of user's credentials").type(Boolean.class),
                        fields.withPath("accountNonExpired").description("Status describing user's account validity").type(Boolean.class),
                        fields.withPath("accountNonLocked").description("Status describing whether user's account is locked or unlocked").type(Boolean.class)
                )));

    }

    @Test
    @DisplayName("Verify user can be retrieved by id")
    @WithMockUser(username = "admin@greenspaces", roles = "ADMIN")
    void verifyUserCanBeRetrievedById() throws Exception {
        given(userService.findUserById(any())).willReturn(user);

        mockMvc.perform(get(USER_PATH, user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keys()", containsInAnyOrder(userProps)))
                .andExpect(jsonPath("$.id", is(user.getId())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.enabled", is(true)))
                .andExpect(jsonPath("$.credentialsNonExpired", is(true)))
                .andExpect(jsonPath("$.accountNonExpired", is(true)))
                .andExpect(jsonPath("$.accountNonLocked", is(true)))
                .andDo(document("Retrieve User",
                        pathParameters(
                                parameterWithName("id").description("UUID of the user to retrieve")
                        ),
                        responseFields(
                                fieldWithPath("id").description("UUID of the  retrieved user"),
                                fieldWithPath("username").description("username of the user to be save").type(String.class),
                                fieldWithPath("roles").description("roles of the user to be saved").type(Set.class),
                                fieldWithPath("roles.[].id").description("role id of a single role within roles of the user to be saved").type(Integer.class),
                                fieldWithPath("roles.[].role").description("role name of a single role within roles of a single role within roles of the user to be saved").type(String.class),
                                fieldWithPath("roles.[].authorities").description("authorities within a single role of roles of the user to be saved").type(Set.class),
                                fieldWithPath("roles.[].authorities.[].id").description("id of a authority within a role of roles of the user to be saved").type(Integer.class),
                                fieldWithPath("roles.[].authorities.[].authority").description("authority name of an authority within a role of roles of the user to be saved").type(String.class),
                                fieldWithPath("enabled").description("status describing if the user account is enabled").type(Boolean.class),
                                fieldWithPath("credentialsNonExpired").description("status describing the validity of user's credentials").type(Boolean.class),
                                fieldWithPath("accountNonExpired").description("Status describing user's account validity").type(Boolean.class),
                                fieldWithPath("accountNonLocked").description("Status describing whether user's account is locked or unlocked").type(Boolean.class)
                        )));

    }

    @Test
    @DisplayName("Verify that all users can be retrieved")
    @WithMockUser(username = "admin@greenspaces", roles = "ADMIN")
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
                .andExpect(jsonPath("$[0].accountNonLocked", is(true)))
                .andDo(document("Retrieve All Users", responseFields(
                        fieldWithPath("[].id").description("UUID of the  retrieved user"),
                        fieldWithPath("[].username").description("username of the user to be save").type(String.class),
                        fieldWithPath("[].roles").description("roles of the user to be saved").type(Set.class),
                        fieldWithPath("[].roles.[].id").description("role id of a single role within roles of the user to be saved").type(Integer.class),
                        fieldWithPath("[].roles.[].role").description("role name of a single role within roles of a single role within roles of the user to be saved").type(String.class),
                        fieldWithPath("[].roles.[].authorities").description("authorities within a single role of roles of the user to be saved").type(Set.class),
                        fieldWithPath("[].roles.[].authorities.[].id").description("id of a authority within a role of roles of the user to be saved").type(Integer.class),
                        fieldWithPath("[].roles.[].authorities.[].authority").description("authority name of an authority within a role of roles of the user to be saved").type(String.class),
                        fieldWithPath("[].enabled").description("status describing if the user account is enabled").type(Boolean.class),
                        fieldWithPath("[].credentialsNonExpired").description("status describing the validity of user's credentials").type(Boolean.class),
                        fieldWithPath("[].accountNonExpired").description("Status describing user's account validity").type(Boolean.class),
                        fieldWithPath("[].accountNonLocked").description("Status describing whether user's account is locked or unlocked").type(Boolean.class)
                )));
    }

    @Test
    @DisplayName("Verify that a user's roles can be retrieved")
    @WithMockUser(username = "admin@greenspaces",  roles = "ADMIN")
    void verifyThatUserRolesCanBeRetrievedById() throws Exception {
        List<Role> roles = new ArrayList<>(user.getRoles());

        given(userService.findRolesById(any())).willReturn(roles);

        mockMvc.perform(get(USER_PATH + ROLES_PATH, user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].keys()", containsInAnyOrder(roleProps)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].role", is("ADMIN")))
                .andExpect(jsonPath("$[0].authorities[0].authority", notNullValue()))
                .andDo(document("Retrieve User Roles",
                        pathParameters(
                                parameterWithName("id").description("UUID of the user to retrieve")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("id of a role within roles of the user").type(Integer.class),
                                fieldWithPath("[].role").description("role name of role within roles of a user").type(String.class),
                                fieldWithPath("[].authorities").description("authorities within a single role of roles").type(Set.class),
                                fieldWithPath("[].authorities.[].id").description("id of authority within a role of roles").type(Integer.class),
                                fieldWithPath("[].authorities.[].authority").description("authority name of an authority within a role of roles").type(String.class)
                        )
                ));

    }

    @Test
    @DisplayName("Verify that a user's role can be retrieved by ids")
    @WithMockUser(username = "admin@greenspaces", roles = "ADMIN")
    void verifyThatUserRoleCanBeRetrievedById() throws Exception {
        given(userService.findUserById(any())).willReturn(user);

        mockMvc.perform(get(USER_PATH + ROLES_PATH + "/{roleId}", user.getId(), 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keys()", containsInAnyOrder(roleProps)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.role", is("ADMIN")))
                .andExpect(jsonPath("$.authorities", notNullValue()))
                .andDo(document("Retrieve User Role",
                        pathParameters(
                                parameterWithName("id").description("UUID of the user with role to retrieve"),
                                parameterWithName("roleId").description("id of role to retrieve")
                        ),
                        responseFields(
                                fieldWithPath("id").description("id of a role").type(Integer.class),
                                fieldWithPath("role").description("role name").type(String.class),
                                fieldWithPath("authorities").description("authorities within this role").type(Set.class),
                                fieldWithPath("authorities.[].id").description("id of an authority within a role of roles").type(Integer.class),
                                fieldWithPath("authorities.[].authority").description("authority name of an authority within a role").type(String.class)
                        )
                ));
    }

    @Test
    @DisplayName("Verify that user's authority can be retrieved by ids")
    @WithMockUser(username = "admin@greenspaces", roles = "ADMIN")
    void verifyThatUsersAuthorityCanBeRetrievedById() throws Exception {
        given(userService.findUserById(any())).willReturn(user);

        mockMvc.perform(get(USER_PATH + ROLES_PATH + "/{roleId}" + AUTHORITIES_PATH + "/{authorityId}", user.getId(), 1, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keys()", containsInAnyOrder("id", "authority")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.authority", is("read")))
                .andDo(document("Retrieve User Authority",
                        pathParameters(
                                parameterWithName("id").description("UUID of the user with authority to retrieve"),
                                parameterWithName("roleId").description("id of role associated with the authority to be retrieved"),
                                parameterWithName("authorityId").description("id of the authority to retrieve")
                        ),
                        responseFields(
                                fieldWithPath("id").description("id of the authority to retrieve").type(Integer.class),
                                fieldWithPath("authority").description("authority name of an authority to retrieve").type(String.class)
                        )
                ));
    }

    @Test
    @DisplayName("Verify that user can retrieve authorities by ids")
    @WithMockUser(username = "admin@greenspaces", roles = "ADMIN")
    void verifyThatUserCanRetrieveAuthoritiesById() throws Exception {

        List<Authority> authorities = user.getRoles()
                .stream()
                .map(Role::getAuthorities)
                .flatMap(Collection::stream)
                .toList();
        given(userService.findAuthoritiesById(user.getId())).willReturn(authorities);

        mockMvc.perform(get(USER_PATH + ROLES_PATH + AUTHORITIES_PATH, user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", notNullValue()))
                .andDo(document("Retrieve Authorities by User id",
                        pathParameters(
                                parameterWithName("id").description("id of the user associated with retrieved authorities")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("id of an authority within list of authorities retrieved"),
                                fieldWithPath("[].authority").description("authority name of an authority within list of authorities retrieved")
                        )
                ));
    }

    @Test
    @DisplayName("Verify that user can add roles")
    @WithMockUser(username = "admin@greenspaces", roles = "ADMIN")
    void verifyThatUserCanAddRoles() throws Exception {

        Set<Role> roles = Set.of(
                Role.builder().id(2).role(Role.Roles.USER.getRole()).build()
        );

        given(userService.findUserById(any())).willReturn(user);

        given(roleService.saveAll(roles)).willReturn(roles);

        String json = createContent(roles);

        mockMvc.perform(patch(USER_PATH + ROLES_PATH, user.getId())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(document("Add Roles to User",
                        pathParameters(
                                parameterWithName("id").description("UUID of the user with roles to be retrieved")
                        )));
    }

    @Test
    @DisplayName("Verify that user can add authorities")
    @WithMockUser(username = "admin@greenspaces", roles = "ADMIN")
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
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(document("Add Authorities to User",
                        pathParameters(
                                parameterWithName("id").description("UUID of the user adding authorities"),
                                parameterWithName("roleId").description("id of the role to add the authorities")
                        ),
                        requestFields(
                                fieldWithPath("[].id").ignored(),
                                fieldWithPath("[].authority").description("authority name of authority within authorities to add")
                        )));
    }

    @Test
    @DisplayName("Verify that user's password can be reset")
    @WithMockUser(username = "admin@greenspaces", roles = "ADMIN")
    void verifyThatUserPasswordCanBeReset() throws Exception {

        given(userService.findUserById(any())).willReturn(user);

        User data = User.builder()
                .username(user.getUsername())
                .password("P@55w07d4t35t")
                .build();

        String json = createContent(data);
        ConstrainedFields fields = new ConstrainedFields(User.class);
        mockMvc.perform(patch(USER_ACCOUNT_PASSWORD_RESET_PATH, user.getId())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(document("Reset User Password",
                        pathParameters(
                                parameterWithName("id").description("Id of the user with password to reset")
                        ),
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("username").description("username of the user to be save").type(String.class),
                                fields.withPath("password").description("password of the user to save").type(String.class),
                                fields.withPath("roles").ignored(),
                                fields.withPath("enabled").ignored(),
                                fields.withPath("credentialsNonExpired").ignored(),
                                fields.withPath("accountNonExpired").ignored(),
                                fields.withPath("accountNonLocked").ignored()
                        )));
    }

    @Test
    @DisplayName("Verify that user account status can be updated")
    @WithMockUser(username = "admin@greenspaces", roles = "ADMIN")
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

        ConstrainedFields fields = new ConstrainedFields(User.class);
        mockMvc.perform(patch(USER_ACCOUNT, user.getId())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(document("Update a User account status",
                        pathParameters(
                                parameterWithName("id").description("Id of the user account status to update")
                        ),
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("username").ignored(),
                                fields.withPath("password").ignored(),
                                fields.withPath("roles").ignored(),
                                fields.withPath("enabled").description("set user account to enabled").type(Boolean.class),
                                fields.withPath("credentialsNonExpired").description("set validity of user's credentials").type(Boolean.class),
                                fields.withPath("accountNonExpired").description("set user's account validity").type(Boolean.class),
                                fields.withPath("accountNonLocked").description("locked or unlocked user's account").type(Boolean.class)
                        )));

    }

    @Test
    @DisplayName("Verify that user can be deleted")
    @WithMockUser(username = "admin@greenspaces", roles = "ADMIN")
    void verifyThatUserCanBeDeleted() throws Exception {

        mockMvc.perform(delete(USER_PATH, user.getId())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("Delete a User", pathParameters(parameterWithName("id").description("Id of the user to delete"))
                ));
    }

}
