package com.daveace.greenspaces.tokensettings;


import com.daveace.greenspaces.response.GreenSpacesException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.daveace.greenspaces.util.Constants.*;

@Service
public class TokenSettingsService {

    private TokenSettingsRepo repo;

    @Autowired
    public void setRepo(TokenSettingsRepo repo) {
        this.repo = repo;
    }

    //CRUD  operation to add token settings to a datasource
    public TokenSettings save(@NotNull TokenSettings settings) {
        return CompletableFuture.supplyAsync(() ->
                repo.save(settings)
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    public Set<TokenSettings> saveAll(@NotNull Set<TokenSettings> settings) {
        return CompletableFuture.supplyAsync(() ->
                new HashSet<>(repo.saveAll(settings))
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    //CRUD operations to read token settings to a datasource
    public Set<TokenSettings> findByClientId(@NotNull String clientId) {
        return CompletableFuture.supplyAsync(() ->
                repo.findAllByClientId(clientId)
                        .orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND))
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    public TokenSettings findById(int id) {
        return CompletableFuture.supplyAsync(() ->
                repo
                        .findById(id)
                        .orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND))
        ).handleAsync((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    //CRUD operations to update token settings in a datasource
    public String update(int id, Function<TokenSettings, TokenSettings> operation) {
        return CompletableFuture.supplyAsync(() ->
                repo.findById(id).map(settings -> {
                    try {
                        TokenSettings alteredSettings = operation.apply(settings);
                        repo.save(alteredSettings);
                        return TOKEN_SETTINGS_UPDATED;
                    } catch (Exception e) {
                        throw new GreenSpacesException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }).orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND))
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    //CRUD operations to delete token settings from a datasource
    public String delete(int id) {
        return CompletableFuture.supplyAsync(() -> {
                    boolean deleted;
                    repo.deleteById(id);
                    deleted = !repo.existsById(id);
                    return deleted ? DELETED : NOT_DELETED;
                }
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }
}
