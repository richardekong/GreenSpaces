package com.daveace.greenspaces.redirecturi;

import com.daveace.greenspaces.response.GreenSpacesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.daveace.greenspaces.util.Constants.*;

@Service
public class RedirectUriService {

    private RedirectUriRepo repo;

    @Autowired
    public void setRepo(RedirectUriRepo repo) {
        this.repo = repo;
    }

    // create uri
    public RedirectURI save(RedirectURI uri) {
        return CompletableFuture.supplyAsync(() -> {
            if (repo.existsById(uri.getId())) {
                throw new GreenSpacesException("Record exists!", HttpStatus.CONFLICT);
            }
            return repo.save(uri);
        }).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();

    }

    public Set<RedirectURI> saveAll(Set<RedirectURI> uris) {
        return CompletableFuture.supplyAsync(() ->
                new HashSet<>(repo.saveAll(uris))
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    // read uri
    public List<RedirectURI> findAll() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repo.findAll();
            } catch (RuntimeException ex) {
                throw new GreenSpacesException("Internal Server error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    public RedirectURI findRedirectURIById(Long id) {
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

    public RedirectURI findRedirectURIByUri(String uri) {
        return CompletableFuture.supplyAsync(() ->
                repo.findRedirectURIByUri(uri)
                        .orElseThrow(() -> new GreenSpacesException("No such redirectUri", HttpStatus.NOT_FOUND))
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    // update uri
    public String update(Long id, Function<RedirectURI, RedirectURI> operation) {
        return CompletableFuture.supplyAsync(() ->
        {
            RedirectURI oldURI = repo
                    .findById(id)
                    .orElseThrow(
                            () -> new GreenSpacesException(HttpStatus.NOT_FOUND)
                    );
            try {
                // perform operation
                RedirectURI alteredURI = operation.apply(oldURI);
                // save changes
                alteredURI = repo.save(alteredURI);
                return oldURI.equals(alteredURI) ? UPDATED : NOT_UPDATED;
            } catch (Exception ex) {
                throw new GreenSpacesException(
                        "Operation failed!",
                        HttpStatus.INTERNAL_SERVER_ERROR
                );
            }

        }).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    // delete uri
    public String delete(Long id) {
        return CompletableFuture.supplyAsync(() ->
                {
                    try {
                        //perform delete operation
                        repo.deleteById(id);
                        //verify deletion operation
                        boolean deleted = !repo.existsById(id);
                        return deleted ? DELETED_SUCCESSFULLY : NOT_DELETED;
                    } catch (GreenSpacesException ex) {
                        throw new GreenSpacesException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

}

