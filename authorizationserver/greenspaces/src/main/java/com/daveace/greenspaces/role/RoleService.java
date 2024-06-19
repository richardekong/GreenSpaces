package com.daveace.greenspaces.role;

import com.daveace.greenspaces.response.GreenSpacesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Service
public class RoleService {

    private RoleRepo repo;

    @Autowired
    public void setRepo(RoleRepo repo) {
        this.repo = repo;
    }

    //CRUD operation to create a role in the datasource
    public Role save(Role role) {
        return CompletableFuture
                .supplyAsync(() -> {
                    boolean exists = repo.existsById(role.getId());
                    if (exists) {
                        throw new DuplicateKeyException("Role already exists");
                    }
                    return repo.save(role);
                })
                .handle((res, err) -> {
                    if (err != null) {
                        throw new RuntimeException(err.getMessage());
                    }
                    return res;
                })
                .join();
    }

    public Set<Role> saveAll(Set<Role> roles) {
        return CompletableFuture.supplyAsync(() ->
                new HashSet<>(repo.saveAll(roles))
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    //CRUD  operation to read a role from the datasource
    public Role findById(int id) {
        return CompletableFuture
                .supplyAsync(() ->
                        repo.findById(id)
                                .orElseThrow(() ->
                                        new GreenSpacesException(HttpStatus.NOT_FOUND))
                ).handle((res, err) -> {
                    if (err != null) {
                        throw new RuntimeException(err.getMessage());
                    }
                    return res;
                })
                .join();
    }

    public List<Role> findAll() {
        return CompletableFuture
                .supplyAsync(() -> repo.findAll())
                .handle((res, err) -> {
                    if (err != null) {
                        throw new GreenSpacesException(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    return res;
                }).join();
    }

    //CRUD operation to update a role in a datasource
    public String update(int id, Function<Role, Role> ops) {
        return CompletableFuture.supplyAsync(() ->
                {
                    Role oldRole = repo.findById(id)
                            .orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND));
                    Role alteredRole = ops.apply(oldRole);
                    Role updatedRole = repo.save(alteredRole);
                    return oldRole.equals(updatedRole) ? "Update successful!" : "Update failed!";
                }
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    //CRUD operation to delete a role from the datasource
    public String delete(int id) {
        return CompletableFuture.supplyAsync(() -> {
            repo.deleteById(id);
            return !repo.existsById(id) ? "Role deleted!" : "Role not deleted!";
        }).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return res;
        }).join();
    }
}

