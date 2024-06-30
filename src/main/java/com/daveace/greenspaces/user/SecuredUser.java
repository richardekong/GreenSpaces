package com.daveace.greenspaces.user;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.daveace.greenspaces.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecuredUser implements UserDetails {

    private final String username;
    @JsonIgnore
    private final String password;
    private final Set<GrantedAuthority> authorities;
    private final Set<Role> roles;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;

    public SecuredUser(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();

        this.authorities = user.getRoles().stream()
                .map(Role::getAuthorities)
                .flatMap(Collection::stream)
                .map(authority -> (GrantedAuthority) new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toSet());
        this.roles = user.getRoles();
        isAccountNonExpired = user.isAccountNonExpired();
        isAccountNonLocked = user.isAccountNonLocked();
        isCredentialsNonExpired = user.isCredentialsNonExpired();
        isEnabled = user.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Collection<Role> getRoles(){ return roles;}

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String toString() {
        return "SecuredUser{" +
                "username='" + username + '\'' +
                ", authorities=" + authorities +
                ", roles=" + roles +
                ", isAccountNonExpired=" + isAccountNonExpired +
                ", isAccountNonLocked=" + isAccountNonLocked +
                ", isCredentialsNonExpired=" + isCredentialsNonExpired +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
