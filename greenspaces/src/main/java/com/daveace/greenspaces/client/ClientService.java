package com.daveace.greenspaces.client;

import com.daveace.greenspaces.response.GreenSpacesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.daveace.greenspaces.util.Constants.*;

@Service
public class ClientService implements RegisteredClientRepository, ClientConverter {

    private ClientRepo repo;

    @Autowired
    public void setRepo(ClientRepo repo) {
        this.repo = repo;
    }

    // create client
    @Override
    public void save(RegisteredClient client) {
        CompletableFuture.runAsync(() -> {
            if (repo.existsByName(client.getClientName())) {
                throw new GreenSpacesException(CLIENT_ALREADY_EXISTS, HttpStatus.CONFLICT);
            }

            Client clientToSave = fromRegisteredClient(client);
            repo.save(clientToSave);
        }).exceptionally(ex -> {
            if (ex instanceof GreenSpacesException) {
                throw (GreenSpacesException) ex;
            }
            throw new RuntimeException(ex.getMessage());
        });

    }

    public Client save(Client client) {
        return CompletableFuture.supplyAsync(() -> {
            String clientName = client.getName();
            if (repo.existsByName(clientName)) {
                throw new GreenSpacesException(CLIENT_ALREADY_EXISTS, HttpStatus.CONFLICT);
            }
            return repo.save(client);
        }).handle((res, err) -> {
            if (err instanceof GreenSpacesException) {
                throw (GreenSpacesException) err;
            } else if (err != null) {
                throw new RuntimeException(err.getMessage());
            } else {
                return res;
            }
        }).join();
    }

    // read client
    @Override
    public RegisteredClient findByClientId(String id) {
        return CompletableFuture.supplyAsync(() ->
                repo.findById(id)
                        .map(this::toRegisteredClient)
                        .orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND))
        ).handle((res, err) -> {
            if (err instanceof GreenSpacesException) {
                throw (GreenSpacesException) err;
            } else if (err != null) {
                throw new RuntimeException(err.getMessage());
            } else {
                return res;
            }
        }).join();
    }

    @Override
    public RegisteredClient findById(String id) {
        return CompletableFuture.supplyAsync(() ->
                repo.findById(id)
                        .map(this::toRegisteredClient)
                        .orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND))
        ).handle((res, err) -> {
            if (err != null) {
                throw new RuntimeException(err.getMessage());
            }
            return res;
        }).join();
    }

    public Client findClientById(String id) {
        return CompletableFuture.supplyAsync(() ->
                repo.findClientById(id)
                        .orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND))
        ).handle((res, err) -> {
            if (err != null) {
                throw new RuntimeException(err.getMessage());
            }
            return res;
        }).join();
    }

    public List<Client> findAllClients() {
        return CompletableFuture.supplyAsync(() ->
                Optional
                        .of(repo.findAll())
                        .orElseThrow(() -> new GreenSpacesException(HttpStatus.NOT_FOUND))
        ).handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

    // update client
    public String update(String clientName, Function<Client, Client> operation) {
        return CompletableFuture.supplyAsync(() ->
                {
                    try {
                        Client oldClient = repo.findClientByName(clientName)
                                .orElseThrow(() -> new GreenSpacesException(CLIENT_NOT_FOUND, HttpStatus.NOT_FOUND));
                        // perform arbitrary update operation
                        Client alteredClient = operation.apply(oldClient);
                        Client updatedClient = repo.save(alteredClient);
                        return oldClient.equals(updatedClient) ? CLIENT_NOT_UPDATED : CLIENT_UPDATED;
                    } catch (Exception ex) {
                        throw new GreenSpacesException(
                                ex.getMessage(),
                                HttpStatus.INTERNAL_SERVER_ERROR
                        );
                    }
                }
        ).handle((res, err) -> {
            if (err != null) {
                throw new RuntimeException(err.getMessage());
            }
            return res;
        }).join();

    }

    public boolean exists(String name) {
        return CompletableFuture
                .supplyAsync(() -> repo.existsByName(name))
                .handle((res, err) -> {
                    if (err != null) throw new RuntimeException(err.getMessage());
                    return res;
                })
                .join();
    }

    // delete client
    public String delete(String id) {
        CompletableFuture<String> response = CompletableFuture.supplyAsync(() -> {
            boolean deleted;
            // delete the client
            repo.deleteById(id);
            // verify delete operation
            deleted = !repo.existsById(id);
            return deleted ? DELETED_SUCCESSFULLY : NOT_DELETED;
        });

        return response.handle((res, err) -> {
            if (err != null) {
                throw new GreenSpacesException(err.getMessage());
            }
            return res;
        }).join();
    }

}

