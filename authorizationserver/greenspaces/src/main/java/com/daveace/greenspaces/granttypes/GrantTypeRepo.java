package com.daveace.greenspaces.granttypes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrantTypeRepo extends JpaRepository<GrantType, Integer> {

    Optional<GrantType> findGrantTypesByClientId(String clientId);
}

