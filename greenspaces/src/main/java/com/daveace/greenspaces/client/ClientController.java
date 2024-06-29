package com.daveace.greenspaces.client;

import com.daveace.greenspaces.authenticationmethod.AuthenticationMethod;
import com.daveace.greenspaces.authenticationmethod.AuthenticationMethodService;
import com.daveace.greenspaces.authenticationmethod.AuthenticationMethods;
import com.daveace.greenspaces.granttypes.GrantType;
import com.daveace.greenspaces.granttypes.GrantTypeService;
import com.daveace.greenspaces.redirecturi.RedirectURI;
import com.daveace.greenspaces.redirecturi.RedirectUriService;
import com.daveace.greenspaces.scope.Scope;
import com.daveace.greenspaces.scope.ScopeService;
import com.daveace.greenspaces.tokensettings.TokenSettings;
import com.daveace.greenspaces.tokensettings.TokenSettingsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.daveace.greenspaces.util.Constants.CLIENTS_PATH;
import static com.daveace.greenspaces.util.Constants.CLIENT_PATH;

@RestController
public class ClientController implements ClientManager{

    private ClientService clientService;
    private PasswordEncoder passwordEncoder;
    private RedirectUriService redirectUriService;
    private AuthenticationMethodService authenticationService;
    private GrantTypeService grantTypeService;
    private ScopeService scopeService;
    private TokenSettingsService settingsService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRedirectUriService(RedirectUriService redirectUriService) {
        this.redirectUriService = redirectUriService;
    }

    @Autowired
    public void setScopeService(ScopeService scopeService) {
        this.scopeService = scopeService;
    }

    @Autowired
    public void setGrantTypeService(GrantTypeService grantTypeService) {
        this.grantTypeService = grantTypeService;
    }

    @Autowired
    public void setSettingsService(TokenSettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Autowired
    public void setAuthenticationService(AuthenticationMethodService authenticationService) {
        this.authenticationService = authenticationService;
    }

    //CRUD operations for creating client record
    @PostMapping("/V1"+CLIENTS_PATH)
    public ResponseEntity<String> createClientV1(@RequestBody @Valid Client client){
        try{
            String hash = passwordEncoder.encode(client.getSecret());
            client.setSecret(hash);
            RegisteredClient rc = clientService.toRegisteredClient(client);
            clientService.save(rc);
            if (clientService.exists(client.getName())) {
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Client saved");
            } else {
                return ResponseEntity.ok("Client not saved");
            }
        }catch (RuntimeException ex){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }
    }

    @PostMapping(CLIENTS_PATH)
    public ResponseEntity<ClientDTO> createClient(@RequestBody @Valid Client client) {
        String hash = passwordEncoder.encode(client.getSecret());
        client.setSecret(hash);
        ClientDTO response = clientService.save(client).toDTO();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //CRUD operations for reading client records from the  datasource
    @GetMapping(CLIENTS_PATH)
    public ResponseEntity<List<ClientDTO>> findAllClients() {
        List<ClientDTO> response = clientService.findAllClients()
                .stream()
                .map(Client::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping(CLIENT_PATH)
    public ResponseEntity<ClientDTO> findClientById(@PathVariable String id) {
        ClientDTO client = clientService.findClientById(id)
                .toDTO();
        return ResponseEntity.ok(client);
    }

    //CRUD operations for updating client records in the datasource
    @PatchMapping(CLIENT_PATH)
    public ResponseEntity<String> updateClient(
            @PathVariable String id,
            @RequestBody @Valid Client client) {
        try {
            String clientName = Objects.requireNonNull(clientService.findClientById(id)).getName();
            return ResponseEntity.ok(clientService.update(clientName, (clientToUpdate) -> {
                if (client.getSecret() != null) {
                    clientToUpdate.setSecret(passwordEncoder.encode(client.getSecret()));
                }
                if (client.getAuthenticationMethods() != null) {
                    Set<AuthenticationMethod> savedAuthenticationMethods = createAuthenticationMethods(client, clientToUpdate, authenticationService);
                    clientToUpdate.addAuthenticationMethods(savedAuthenticationMethods);
                }
                if (client.getScopes() != null) {
                    Set<Scope> savedScopes = createScopes(client, clientToUpdate, scopeService);
                    clientToUpdate.addScopes(savedScopes);
                }
                if (client.getGrantTypes() != null) {
                    Set<GrantType> savedGrantTypes = createGrantTypes(client, clientToUpdate, grantTypeService);
                    clientToUpdate.addGrantTypes(savedGrantTypes);
                }
                if (client.getRedirectUris() != null) {
                    Set<RedirectURI> savedRedirectURIs = createRedirectURIS(client, clientToUpdate, redirectUriService);
                    clientToUpdate.addRedirectUris(savedRedirectURIs);
                }
                if (client.getTokenSettings() != null) {
                    TokenSettings settings = createTokenSettings(client, clientToUpdate,settingsService);
                    clientToUpdate.setTokenSettings(settings);
                }
                return clientToUpdate;
            }));
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }

    }

    //CRUD operations for deleting client records held in the datasource
    @DeleteMapping(CLIENT_PATH)
    public ResponseEntity<String> deleteClientById(@PathVariable String id) {
        String response = clientService.delete(id);
        return ResponseEntity.ok(response);
    }


}
