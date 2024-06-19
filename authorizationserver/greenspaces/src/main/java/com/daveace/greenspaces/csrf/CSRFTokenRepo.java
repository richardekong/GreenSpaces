package com.daveace.greenspaces.csrf;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.Optional;

public interface CSRFTokenRepo extends JpaRepository<CSRFToken, String>{
    
    Optional<CSRFToken> findCSRFTokenByClientId(String clientId);

    default boolean isExpired(CSRFToken token){
        LocalTime tokenLifeTime = token.getIssuedAt().plusMinutes(token.getExpiresIn());
        return LocalTime.now().isAfter(tokenLifeTime);
    }
}
