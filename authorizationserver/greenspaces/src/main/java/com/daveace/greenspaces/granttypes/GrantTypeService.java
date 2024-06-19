package com.daveace.greenspaces.granttypes;

import com.daveace.greenspaces.response.GreenSpacesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.daveace.greenspaces.util.Constants.*;

@Service
public class GrantTypeService {

    private GrantTypeRepo repo;

    @Autowired
    public void setRepo(GrantTypeRepo repo) {
        this.repo = repo;
    }

    public GrantType save(GrantType grantType) {
        return CompletableFuture.supplyAsync(() -> repo.save(grantType)).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    public Set<GrantType> saveAll(Set<GrantType> grantTypes) {
        return CompletableFuture.supplyAsync(() ->
                        new HashSet<>(repo.saveAll(grantTypes))
                ).handle((res, err) -> {
                    if (err != null) {
                        throw new GreenSpacesException(err.getMessage());
                    }
                    return res;
                })
                .join();
    }

    public GrantType findById(int id) {
        return CompletableFuture
                .supplyAsync(() -> repo.findById(id)
                        .orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND)))
                .handle((res, err) -> {
                    if (err != null) {
                        throw new GreenSpacesException(err.getMessage());
                    }
                    return res;
                })
                .join();
    }

    public GrantType findByClientId(String clientId) {
        return CompletableFuture.supplyAsync(() ->
                        repo.findGrantTypesByClientId(clientId)
                                .orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND))
                )
                .handle((res, err) -> {
                    if (err != null) {
                        throw new GreenSpacesException(err.getMessage());
                    }
                    return res;
                }).join();
    }

    public List<GrantType> findAll() {
        return CompletableFuture.supplyAsync(() -> repo.findAll())
                .handle((res, err) -> {
                    if (err != null) {
                        throw new GreenSpacesException(err.getMessage());
                    }
                    return res;
                })
                .join();
    }

    public String update(int id, Function<GrantType, GrantType> ops) {
        return CompletableFuture.supplyAsync(() ->
                {
                    Optional<GrantType> unconfirmedGrantType = repo.findById(id);
                    if (unconfirmedGrantType.isPresent()) {
                        GrantType grantType = unconfirmedGrantType.get();
                        GrantType alteredGrantType = ops.apply(grantType);
                        GrantType updatedGrantType = repo.save(alteredGrantType);
                        return grantType.equals(updatedGrantType) ? NOT_UPDATED : UPDATED;
                    } else {
                        throw new GreenSpacesException(HttpStatus.NOT_FOUND);
                    }
                }
        ).handle((res, err) ->
        {
            if (err != null) {
                throw new RuntimeException(err.getMessage());
            }
            return res;
        }).join();
    }

    public String delete(int id) {
        return CompletableFuture.supplyAsync(() -> {
            boolean exists;
            repo.deleteById(id);
            exists = repo.existsById(id);
            return exists ? NOT_DELETED : DELETED;
        }).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

}
