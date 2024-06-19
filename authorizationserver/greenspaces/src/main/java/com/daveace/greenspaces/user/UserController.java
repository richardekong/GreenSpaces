package com.daveace.greenspaces.user;


import com.daveace.greenspaces.authority.Authority;
import com.daveace.greenspaces.role.Role;
import com.daveace.greenspaces.response.GreenSpacesException;
import com.daveace.greenspaces.authority.AuthorityService;
import com.daveace.greenspaces.role.RoleService;
import com.daveace.greenspaces.util.Regexp;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.daveace.greenspaces.util.Constants.*;

@RestController
public class UserController {
    //define instance variable
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private AuthorityService authorityService;
    private RoleService roleService;

    //define methods to inject services and other instance variables
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    //define CRUD operations to create user records
    @PostMapping(USERS_PATH)
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid @NotNull User user) {
        String  hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        return new ResponseEntity<>(userService.save(user).toDTO(), HttpStatus.CREATED);
    }

    //define CRUD operations to read user records
    @GetMapping(USERS_PATH)
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        var response = userService
                .findAllUsers()
                .stream()
                .map(User::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping(USER_PATH)
    public ResponseEntity<UserDTO> findUserById(@PathVariable String id) {
        var response = userService
                .findUserById(id)
                .toDTO();
        return ResponseEntity.ok(response);
    }

    @GetMapping(USER_PATH + ROLES_PATH)
    public ResponseEntity<List<Role>> findUserRolesById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findRolesById(id));
    }

    @GetMapping(USER_PATH + ROLES_PATH + "/{roleId}")
    public ResponseEntity<Role> findUserRoleById(
            @PathVariable String id,
            @PathVariable int roleId) {

        Predicate<Role> rolePredicate = r -> r.getId() == roleId;
        Role role = userService.findUserById(id)
                .getRoles()
                .stream()
                .filter(rolePredicate)
                .findFirst()
                .orElseThrow(()->new GreenSpacesException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(role);

    }

    @GetMapping(USER_PATH + ROLES_PATH + "/{roleId}" + AUTHORITIES_PATH + "/{authorityId}")
    public ResponseEntity<Authority> findUserAuthorityById(
            @PathVariable String id,
            @PathVariable int roleId,
            @PathVariable int authorityId){
        
        Authority authority = userService
                .findUserById(id)
                .getRoles()
                .stream()
                .filter(r -> r.getId() == roleId)
                .map(Role::getAuthorities)
                .flatMap(Collection::stream)
                .filter(a -> a.getId() == authorityId)
                .findFirst()
                .orElseThrow(()->new GreenSpacesException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(authority);
    }

    @GetMapping(USER_PATH + ROLES_PATH + AUTHORITIES_PATH)
    public ResponseEntity<List<Authority>> findUserRolesAuthorities(@PathVariable String id) {
        return ResponseEntity.ok(userService.findAuthoritiesById(id));
    }

    @PatchMapping(USER_PATH + ROLES_PATH)
    public ResponseEntity<String> addRoles(
            @PathVariable String id,
            @RequestBody Set<@Valid Role> roles) {
        try {
            String username = userService.findUserById(id).getUsername();
            String response = userService.update(username, u -> {
                User userToUpdate = userService.findUserByUsername(u);
                Set<Role> savedRoles = roleService.saveAll(roles);
                userToUpdate.add(savedRoles);
                return userToUpdate;
            });
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }
    }

    @PatchMapping(USER_PATH + ROLES_PATH + "/{roleId}" + AUTHORITIES_PATH)
    public ResponseEntity<String> addAuthorities(
            @PathVariable String id,
            @PathVariable int roleId,
            @RequestBody @Valid Set<Authority> authorities) {
        String username = userService.findUserById(id).getUsername();
        String response = userService.update(username, u -> {
            User userToUpdate = userService.findUserByUsername(u);
            final Role roleToUpdate = userToUpdate
                    .getRoles()
                    .stream()
                    .filter(role -> role.getId() == roleId)
                    .findFirst()
                    .orElseThrow(() -> new GreenSpacesException(
                            "No role to associate authority",
                            HttpStatus.NOT_FOUND)
                    );

            Set<Authority> savedAuthorities = authorityService.saveAll(
                    authorities.stream().map(authority -> authority.setRole(roleToUpdate))
                    .collect(Collectors.toSet())
            );

            System.out.println(savedAuthorities);
            roleToUpdate.add(savedAuthorities);
            return userToUpdate;
        });
        return ResponseEntity.ok(response);
    }

    //define CRUD operations to update user records
    @PatchMapping(USER_ACCOUNT_PASSWORD_RESET_PATH)
    public ResponseEntity<String> resetPassword(
            @PathVariable String id,
            @RequestBody @Valid User data) {
        try {
            String username = userService.findUserById(id).getUsername();
            String response = userService.update(username, u -> {
                User userToUpdate = userService.findUserByUsername(u);
                String newPassword =data.getPassword();
                if (!Pattern.matches(Regexp.PASSWORD_REGEXP, newPassword))
                    throw new GreenSpacesException(INVALID_PASSWORD, HttpStatus.BAD_REQUEST);
                else
                    userToUpdate.setPassword(passwordEncoder.encode(newPassword));
                return userToUpdate;
            });
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PatchMapping(USER_ACCOUNT)
    public ResponseEntity<String> updateUserAccountStatus(
            @PathVariable String id,
            @RequestBody @Valid User data) {
        try {
            String username = userService.findUserById(id).getUsername();
            if (username.isBlank()) throw new GreenSpacesException(USERNAME_CANT_BE_BLANK);
            return ResponseEntity.ok(userService.update(username, payload -> {
                User userToUpdate = userService.findUserByUsername(payload);
                if (!String.valueOf(data.isAccountNonLocked()).isBlank()) {
                    userToUpdate.setAccountNonLocked(data.isAccountNonLocked());
                }
                if (!String.valueOf(data.isEnabled()).isBlank()) {
                    userToUpdate.setEnabled(data.isEnabled());
                }
                if (!String.valueOf(data.isCredentialsNonExpired()).isBlank()) {
                    userToUpdate.setCredentialsNonExpired(data.isCredentialsNonExpired());
                }
                if (!String.valueOf(data.isAccountNonExpired()).isBlank()) {
                    userToUpdate.setAccountNonExpired(data.isAccountNonExpired());
                }
                return userToUpdate;
            }));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    //define CRUD operations to delete user records
    @DeleteMapping(USER_PATH)
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.delete(id));
    }
}

