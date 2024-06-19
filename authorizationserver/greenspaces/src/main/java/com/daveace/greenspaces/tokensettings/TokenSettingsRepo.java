package com.daveace.greenspaces.tokensettings;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface TokenSettingsRepo extends JpaRepository<TokenSettings, Integer> {

    Optional<Set<TokenSettings>> findAllByClientId(String clientId);

}
