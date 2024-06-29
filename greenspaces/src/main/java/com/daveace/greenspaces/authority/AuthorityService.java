package com.daveace.greenspaces.authority;

import com.daveace.greenspaces.response.GreenSpacesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.daveace.greenspaces.util.Constants.*;

@Service
public class AuthorityService {

    private AuthorityRepo repo;

    @Autowired
    public void setRepo(AuthorityRepo repo) {
        this.repo = repo;
    }

    //CRUD operation to create a record in the datasource
    public Authority save(Authority authority) {
        return CompletableFuture.supplyAsync(() -> {
            boolean exists = repo.existsById(authority.getId());
            if (exists) {
                throw new GreenSpacesException(HttpStatus.CONFLICT);
            }
            return repo.save(authority);
        }).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    public Set<Authority> saveAll(Set<Authority> authorities) {
        return CompletableFuture.supplyAsync(() ->
                new HashSet<>(repo.saveAll(authorities))
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    //CRUD operation to read a record from the datasource
    public Authority findById(int id) {
        return CompletableFuture.supplyAsync(() ->
                repo.findById(id)
                        .orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND))
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    public Set<Authority> findAll() {
        return CompletableFuture.supplyAsync(() ->
                new HashSet<>(repo.findAll())
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    //CRUD operation to update a record in the datasource
    public String update(int id, Function<Authority, Authority> ops) {
        return CompletableFuture.supplyAsync(() ->
                repo.findById(id)
                        .map(authority -> {
                            //perform update operation
                            Authority alteredAuthority = ops.apply(authority);
                            repo.save(alteredAuthority);
                            return AUTHORITY_UPDATED;
                        })
                        .orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND))
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    //CRUD operation to delete a record from the datasource
    public String delete(int id) {
        return CompletableFuture.supplyAsync(() -> {
                    boolean exists = repo.existsById(id);
                    repo.deleteById(id);
                    return !exists ? AUTHORITY_DELETED : AUTHORITY_NOT_DELETED;
                }
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }


}
