package com.daveace.greenspaces.role;


import com.daveace.greenspaces.authority.Authority;
import com.daveace.greenspaces.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.daveace.greenspaces.util.Constants.*;
import static com.daveace.greenspaces.util.Regexp.LETTER_REGEX;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@Entity
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Pattern(regexp = LETTER_REGEX, message = INVALID_ROLE)
    private String role;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    @Size(min=1, message= MIN_AUTHORITIES)
    @OneToMany(fetch = FetchType.EAGER, mappedBy="role", cascade = CascadeType.REFRESH)
    private Set<Authority> authorities;

    public Role add(Collection<Authority> authorities){
        if (this.authorities == null){
            this.authorities = new HashSet<>();
            for (Authority authority : authorities){
                this.authorities.add(authority);
                authority.setRole(this);
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    public enum Roles{
        ADMIN("ADMIN"),
        USER("USER");

        private final String role;

        Roles(String role){
            this.role = role;
        }

        public String getRole(){
            return role;
        }


    }
}
