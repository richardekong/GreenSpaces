package com.daveace.greenspaces.scope;

import com.daveace.greenspaces.response.GreenSpacesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class ScopeService {

    private ScopeRepo repo;

    @Autowired
    public void setRepo(ScopeRepo repo) {
        this.repo = repo;
    }

    public Scope save(Scope scope) {
        return CompletableFuture
                .supplyAsync(() ->
                        repo.save(scope)
                ).handle((res, err) -> {
                    if (err != null) {
                        throw new GreenSpacesException(err.getMessage());
                    }
                    return res;
                }).join();
    }

    public Set<Scope> saveAll(Set<Scope> scopes) {
        return CompletableFuture
                .supplyAsync(() ->
                        new HashSet<>(repo.saveAll(scopes))
                ).handle((res, err) -> {
                    if (err != null) {
                        throw new GreenSpacesException(err.getMessage());
                    }
                    return res;
                }).join();
    }

    public Scope findById(int id) {
        return CompletableFuture
                .supplyAsync(() ->
                repo.findById(id).orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND))
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    public Set<Scope> findAll() {
        return CompletableFuture
                .supplyAsync(() ->
                        new HashSet<>(repo.findAll())
                ).handle((res, err) -> {
                    if (err != null) {
                        throw new GreenSpacesException(err.getMessage());
                    }
                    return res;
                }).join();
    }

    public String delete(int id) {
        return CompletableFuture
                .supplyAsync(() -> {
                    boolean deleted;
                    repo.deleteById(id);
                    deleted = !repo.existsById(id);
                    return deleted ? "Deleted" : "Not Deleted";
                }).handle((res, err) -> {
                    if (err != null) {
                        throw new GreenSpacesException(err.getMessage());
                    }
                    return res;
                }).join();
    }

}

