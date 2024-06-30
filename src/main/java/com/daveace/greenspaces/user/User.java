package com.daveace.greenspaces.user;

import com.daveace.greenspaces.entity.BaseEntity;
import com.daveace.greenspaces.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.daveace.greenspaces.util.Constants.*;
import static com.daveace.greenspaces.util.Regexp.PASSWORD_REGEXP;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Accessors(chain = true)
@Entity
@Table(name="users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User extends BaseEntity<User> {

    @Email(message= INVALID_EMAIL)
    @NotBlank(message = PROVIDE_USERNAME)
    private String username;

    @Pattern(regexp = PASSWORD_REGEXP, message = INVALID_PASSWORD)
    private String password;

    @Size(min=1, message= MIN_ROLES)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REFRESH)
    private Set<Role>roles;

    @Column(name="account_non_expired")
    private boolean isAccountNonExpired;
    @Column(name="account_non_locked")
    private boolean isAccountNonLocked;
    
    @Column(name="credentials_non_expired")
    private boolean isCredentialsNonExpired;
    
    @Column(name="account_enabled")
    private boolean isEnabled;

    public User add(Collection<Role> roles){
        if(this.roles==null){
            this.roles = new HashSet<>();
            for(Role role : roles){
                this.roles.add(role);
                role.setUser(this);
            }
        }
        return this;
    }

    public UserDTO toDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setUsername(username);
        userDTO.setRoles(roles);
        userDTO.setEnabled(isEnabled);
        userDTO.setAccountNonExpired(isAccountNonExpired);
        userDTO.setAccountNonLocked(isAccountNonLocked);
        userDTO.setCredentialsNonExpired(isCredentialsNonExpired);
        return userDTO;
    }

    @Override
    public String toString() {
        return "User{" +
                "username:'" + username + '\'' +
                ", roles:" + roles +
                ", isAccountNonExpired:" + isAccountNonExpired +
                ", isAccountNonLocked:" + isAccountNonLocked +
                ", isCredentialsNonExpired:" + isCredentialsNonExpired +
                ", isEnabled:" + isEnabled +
                '}';
    }
}

