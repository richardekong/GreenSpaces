package com.daveace.greenspaces.authenticationmethod;

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
public class AuthenticationMethodService {

    private AuthenticationMethodRepo repo;

    @Autowired
    public void setRepo(AuthenticationMethodRepo repo) {
        this.repo = repo;
    }

    public AuthenticationMethod save(AuthenticationMethod method) {
        return CompletableFuture.supplyAsync(() ->
                {
                    boolean exists = repo.existsById(method.getId());
                    if (exists) throw new GreenSpacesException(
                            "Authentication method already exists",
                            HttpStatus.CONFLICT
                    );
                    return repo.save(method);
                })
                .handle((res, err) ->
                {
                    if (err != null)
                    {
                        if(err instanceof GreenSpacesException) throw (GreenSpacesException)err;
                        throw new RuntimeException(err.getMessage());
                    }
                    return res;
                })
                .join();
    }

    public Set<AuthenticationMethod> saveAll(Set<AuthenticationMethod> methods) {
        return CompletableFuture.supplyAsync(() -> new HashSet<>(repo.saveAll(methods)))
                .handle((res, err) ->
                {
                    if (err != null) throw new GreenSpacesException(
                            err.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR
                    );
                    return res;
                })
                .join();
    }

    public AuthenticationMethod findById(int id) {
        return CompletableFuture.supplyAsync(() ->
                        repo.findById(id).orElseThrow(() ->
                                {
                                    HttpStatus status = HttpStatus.NOT_FOUND;
                                    return new GreenSpacesException(
                                            status.getReasonPhrase(),
                                            status
                                    );
                                }
                        ))
                .handle((res, err) ->
                {
                    if (err != null) {
                        if (err instanceof GreenSpacesException) throw (GreenSpacesException) err;
                        throw new RuntimeException(err.getMessage());
                    }
                    return res;
                })
                .join();
    }

    public Set<AuthenticationMethod> findAll() {
        return CompletableFuture
                .supplyAsync(() -> new HashSet<>(repo.findAll()))
                .handle((res, err) ->
                {
                    if (err != null) throw new GreenSpacesException(
                            err.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR
                    );
                    return res;
                })
                .join();
    }

    public String update(int id, Function<AuthenticationMethod, AuthenticationMethod> ops) {
        return CompletableFuture.supplyAsync(() ->
                {
                    AuthenticationMethod oldMethod = repo.findById(id).orElseThrow(() ->
                            {
                                HttpStatus status = HttpStatus.NOT_FOUND;
                                return new GreenSpacesException(status.getReasonPhrase(), status);
                            }
                    );
                    AuthenticationMethod alteredMethod = ops.apply(oldMethod);
                    AuthenticationMethod updatedMethod = repo.save(alteredMethod);
                    return oldMethod.equals(updatedMethod) ? UPDATED : NOT_UPDATED;
                })
                .handle((res, err) ->
                {
                    if (err != null) {
                        if (err instanceof GreenSpacesException) throw (GreenSpacesException) err;
                        throw new RuntimeException(err.getMessage());
                    }
                    return res;
                })
                .join();
    }

    public String delete(int id) {
        return CompletableFuture.supplyAsync(() ->
                {
                    repo.deleteById(id);
                    return !repo.existsById(id) ? DELETED : NOT_DELETED;
                })
                .handle((res, err) ->
                {
                    if (err != null) throw new GreenSpacesException(
                            err.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR
                    );
                    return res;
                })
                .join();
    }
}

