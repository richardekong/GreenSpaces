package com.daveace.greenspaces.user;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.daveace.greenspaces.authority.Authority;
import com.daveace.greenspaces.role.Role;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.daveace.greenspaces.response.GreenSpacesException;

import static com.daveace.greenspaces.util.Constants.*;

import jakarta.validation.constraints.NotNull;

@Service
public class UserService implements UserDetailsService {

    private UserRepo repo;

    @Autowired
    public void setRepo(UserRepo repo) {
        this.repo = repo;
    }

    public User save(@NotNull User user) {
        return CompletableFuture
                .supplyAsync(() -> {
                    boolean exists = repo.existsByUsername(user.getUsername());
                    if (exists) {
                        throw new GreenSpacesException(HttpStatus.CONFLICT);
                    }
                    return repo.save(user);
                })
                .handle((res, err) -> {
                    if (err != null) {
                        throw new RuntimeException(err.getMessage());
                    }
                    return res;
                })
                .join();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return CompletableFuture
                .supplyAsync(() -> repo
                        .findUserByUsername(username)
                        .orElseThrow(() -> new GreenSpacesException(HttpStatus.UNAUTHORIZED)))
                .handle((res, err) -> {
                    if (err != null) {
                        throw new RuntimeException(err.getMessage());
                    }
                    return new SecuredUser(res);
                })
                .join();
    }

    public User findUserByUsername(String username) {
        return CompletableFuture
                .supplyAsync(() -> repo
                        .findUserByUsername(username)
                        .orElseThrow(() -> new GreenSpacesException(HttpStatus.UNAUTHORIZED)))
                .handle((res, err) -> {
                    if (err != null) {
                        throw new RuntimeException(err.getMessage());
                    }
                    return res;
                })
                .join();
    }

    public User findUserById(String id) {
        return CompletableFuture
                .supplyAsync(() -> repo
                        .findUserById(id)
                        .orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND)))
                .handle((res, err) -> {
                    if (err != null) {
                        throw new RuntimeException(err.getMessage());
                    }
                    return res;
                })
                .join();
    }

    public List<User> findAllUsers() {
        return CompletableFuture.supplyAsync(() -> repo.findAll())
                .handle((res, err) -> {
                    if (err != null) {
                        throw new GreenSpacesException(
                                err.getMessage(),
                                HttpStatus.INTERNAL_SERVER_ERROR
                        );
                    }
                    return res;
                }).join();
    }

    public List<Role> findRolesById(String id) {
        return CompletableFuture
                .supplyAsync(() ->
                        repo.findUserById(id)
                                .map(User::getRoles)
                                .orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND))
                                .stream()
                                .toList())
                .handle((res, err) -> {
                    if (err != null) {
                        throw new RuntimeException(err.getMessage());
                    }
                    return res;
                }).join();
    }

    public List<Authority> findAuthoritiesById(String id) {
        return CompletableFuture.supplyAsync(() ->
                        repo.findUserById(id)
                                .map(User::getRoles)
                                .stream()
                                .flatMap(Collection::stream)
                                .map(Role::getAuthorities)
                                .flatMap(Collection::stream)
                                .toList())
                .handle((res, err) -> {
                    if (err != null) {
                        throw new RuntimeException(err.getMessage());
                    }
                    return res;
                }).join();
    }

    @Transactional
    public String update(String username, Function<String, User> operation) {
        return CompletableFuture.supplyAsync(() ->
        {
            User oldUser = repo.findUserByUsername(username).orElseThrow(() ->
                    new GreenSpacesException(USER_UNAUTHORIZED,
                            HttpStatus.UNAUTHORIZED)
            );
            //perform an update operation
            User alteredUser = operation.apply(username);
            User updatedUser = repo.save(alteredUser);
            return oldUser.equals(updatedUser) ? NOT_UPDATED : UPDATED;
        }).handle((res, err) -> {
            if (err != null) {
                throw new RuntimeException(err.getMessage());
            }
            return res;
        }).join();
    }

    public String delete(String id) {
        return CompletableFuture.supplyAsync(() ->
                repo.findUserById(id)
                        .map(userToDelete -> {
                            boolean deleted;
                            repo.deleteById(id);
                            deleted = !repo.existsById(id);
                            return deleted ? DELETED_SUCCESSFULLY : NOT_DELETED;
                        }).orElseThrow(() -> new GreenSpacesException(HttpStatus.UNAUTHORIZED))).handle((res, err) -> {
            if (err != null) {
                throw new RuntimeException(err.getMessage());
            }
            return res;
        }).join();
    }
}

