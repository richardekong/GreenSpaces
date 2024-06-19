package com.daveace.greenspaces.authenticationmethod;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationMethodRepository extends JpaRepository<AuthenticationMethod, Integer> {
}
