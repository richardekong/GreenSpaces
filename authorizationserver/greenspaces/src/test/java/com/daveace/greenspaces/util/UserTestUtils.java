package com.daveace.greenspaces.util;

import com.daveace.greenspaces.authority.Authority;
import com.daveace.greenspaces.role.Role;
import com.daveace.greenspaces.user.User;

import java.util.Set;

public interface UserTestUtils {

    User user = User
            .builder()
            .id("b583b456-9300-4cbd-4bcd-199225f5d42c")
            .username("user.test@mail.com")
            .password("13Tm31n@16351t3001")
            .roles(
                    Set.of(
                            Role.builder()
                                    .id(1)
                                    .role(Role.Roles.ADMIN.getRole())
                                    .user(User
                                            .builder()
                                            .id("b583b456-9300-4cbd-4bcd-199225f5d42c")
                                            .build()
                                    )
                                    .authorities(
                                            Set.of(
                                                    Authority.builder()
                                                            .id(1)
                                                            .authority("read")
                                                            .role(
                                                                    Role
                                                                            .builder()
                                                                            .id(1)
                                                                            .build()
                                                            )
                                                            .build(),
                                                    Authority.builder()
                                                            .id(2)
                                                            .authority("write")
                                                            .build(),
                                                    Authority.builder()
                                                            .id(3)
                                                            .authority("execute")
                                                            .build()
                                            )
                                    )
                                    .build()
                    )
            )
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .isEnabled(true)
            .build();


}
