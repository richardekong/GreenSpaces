package com.daveace.greenspaces.lightweight;

import com.daveace.greenspaces.client.Client;
import com.daveace.greenspaces.client.ClientRepo;
import com.daveace.greenspaces.client.ClientService;
import com.daveace.greenspaces.util.ClientTestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.daveace.greenspaces.util.Constants.DELETED_SUCCESSFULLY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    ClientRepo repo;

    @InjectMocks
    ClientService service;


    Client createClient() {
        //set up client object
        return ClientTestUtils.client;
    }

    @Test
    @DisplayName("Verify if client record can be saved")
    void verifyClientRecordCanBeSaved() {
        Client client = createClient();
        repo.existsByName(client.getName());
        when(repo.save(client)).thenReturn(client);
        Client savedClient = service.save(client);
        assertThat(savedClient).isNotNull();
        verify(repo, times(1)).save(client);
    }

    @Test
    @DisplayName("Verify that client record can be read by id")
    void verifyThatClientCanBeReadBy() {
        Client client = createClient();
        when(repo.findClientById(client.getId())).thenReturn(Optional.of(client));
        Client foundClient = service.findClientById(client.getId());
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getId()).isEqualTo(client.getId());
        verify(repo, times(1)).findClientById(client.getId());
    }

    @Test
    @DisplayName("Verify that all client records can be found")
    void verifyThatAllClientsCanBeFound() {
        List<Client> clients = List.of(createClient());
        when(repo.findAll()).thenReturn(clients);
        List<Client> foundClients = service.findAllClients();
        assertThat(foundClients).isNotEmpty();
        assertThat(foundClients).size().isEqualTo(1);
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("Verify that client record can be updated")
    void verifyThatClientCanBeUpdated() {
        Client client = createClient();
        String clientName = client.getName();
        when(repo.save(client)).thenReturn(client);
        when(repo.findClientByName(clientName)).thenReturn(Optional.of(client));
        service.update(clientName, clientToUpdate -> clientToUpdate.setSecret("53cr3t967a53"));
        assertThat(client.getSecret()).isEqualTo("53cr3t967a53");
        verify(repo, times(1)).findClientByName(clientName);
    }

    @Test
    @DisplayName("Verify that client record can be deleted")
    void verifyThatClientCanBeDeleted() {
        Client client = createClient();
        String id = client.getId();
        doNothing().when(repo).deleteById(id);
        String response = service.delete(id);
        assertThat(response).isEqualTo(DELETED_SUCCESSFULLY);
        verify(repo, times(1)).deleteById(id);

    }

}

