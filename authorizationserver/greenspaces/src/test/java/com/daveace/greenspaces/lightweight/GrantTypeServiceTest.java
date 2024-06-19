package com.daveace.greenspaces.lightweight;

import com.daveace.greenspaces.granttypes.GrantType;
import com.daveace.greenspaces.granttypes.GrantTypeRepo;
import com.daveace.greenspaces.granttypes.GrantTypeService;
import com.daveace.greenspaces.util.ClientTestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GrantTypeServiceTest {

    @Mock
    GrantTypeRepo repo;
    @InjectMocks
    GrantTypeService service;

    private GrantType grantType(){
        return GrantType
                .builder()
                .id(1)
                .grantType("authorization_code")
                .client(ClientTestUtils.client)
                .build();
    }

    @Test
    @DisplayName("Verify that grant type can be saved")
    void verifyThatGrantTypeCanBeSaved(){
        GrantType grantType = grantType();
        when(repo.save(grantType)).thenReturn(grantType);
        service.save(grantType);
        verify(repo, times(1)).save(grantType);
    }

    @Test
    @DisplayName("Verify that grant types can be saved")
    void verifyThatGrantTypesCanBeSaved(){
        Set<GrantType> grantTypes = Set.of(grantType());
        when(repo.saveAll(grantTypes)).thenReturn(new ArrayList<>(grantTypes));
        service.saveAll(grantTypes);
        verify(repo, times(1)).saveAll(grantTypes);
    }

    @Test
    @DisplayName("Verify that grant type can be read")
    void verifyThatGrantTypeCanBeRead(){
        GrantType grantType = grantType();
        when(repo.findById(grantType.getId())).thenReturn(Optional.of(grantType));
        service.findById(grantType.getId());
        verify(repo, times(1)).findById(grantType.getId());
    }

    @Test
    @DisplayName("Verify that grant types can be read")
    void verifyThatGrantTypesCanBeRead(){
        List<GrantType> grantTypes = List.of(grantType());
        when(repo.findAll()).thenReturn(grantTypes);
        service.findAll();
        verify(repo, times(1)).findAll();

    }

    @Test
    @DisplayName("Verify that grant type can be read by client id")
    void verifyThatGrantTypeCanBeReadByClientId(){
        GrantType grantType = grantType();
        when(repo.findGrantTypesByClientId(grantType.getClient().getId())).thenReturn(Optional.of(grantType));
        service.findByClientId(grantType.getClient().getId());
        verify(repo, times(1)).findGrantTypesByClientId(grantType.getClient().getId());
    }

    @Test
    @DisplayName("Verify that grant type can be updated")
    void verifyThatGrantTypeCanBeUpdated(){
        GrantType grantType = grantType();
        when(repo.findById(1)).thenReturn(Optional.of(grantType));
        service.update(1, gt -> gt.setGrantType("client_credentials"));
        verify(repo, times(1)).save(grantType);
    }

    @Test
    @DisplayName("Verify that grant type can be deleted by id")
    void verifyThatGrantTypeCanBeDeletedById(){

        when(repo.existsById(1)).thenReturn(true);
        doNothing().when(repo).deleteById(1);
        service.delete(1);
        verify(repo, times(1)).existsById(1);
        verify(repo, times(1)).deleteById(1);

    }

}
