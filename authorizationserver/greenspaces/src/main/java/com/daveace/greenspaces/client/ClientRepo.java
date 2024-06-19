package com.daveace.greenspaces.client;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, String>{

    Optional<Client> findClientById(String id);

    Optional<Client> findClientByName(String name);

    Optional<Client> findClientByClientId(String clientId);

    boolean existsByName(String name);


}
