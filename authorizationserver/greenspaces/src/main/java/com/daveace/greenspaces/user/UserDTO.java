package com.daveace.greenspaces.user;


import com.daveace.greenspaces.role.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private String id;
    private String username;
    private Set<Role>roles;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private boolean isAccountNonLocked;
    private boolean isAccountNonExpired;

    @Override
    public String toString() {
        return "UserDTO{" +
                "id:'" + id + '\'' +
                ", username:'" + username + '\'' +
                ", roles:" + roles +
                ", isCredentialNonExpired:" + isCredentialsNonExpired +
                ", isEnabled:" + isEnabled +
                ", isAccountNonLocked:" + isAccountNonLocked +
                ", isAccountNonExpired:" + isAccountNonExpired +
                '}';
    }
}
