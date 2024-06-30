package com.daveace.greenspaces.authenticationmethod;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationMethodRepo extends JpaRepository<AuthenticationMethod, Integer> {
}
