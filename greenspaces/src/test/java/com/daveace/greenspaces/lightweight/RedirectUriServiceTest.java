package com.daveace.greenspaces.lightweight;

import com.daveace.greenspaces.redirecturi.RedirectURI;
import com.daveace.greenspaces.redirecturi.RedirectUriRepo;
import com.daveace.greenspaces.redirecturi.RedirectUriService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RedirectUriServiceTest {

    @Mock
    RedirectUriRepo repo;

    @InjectMocks
    RedirectUriService service;

    private RedirectURI createURI() {
        return RedirectURI.builder()
                .id(1L)
                .uri("http://127.0.0.1:1000")
                .build();
    }

    @Test
    @DisplayName("Verify that uri can be saved")
    void verifyThatUriCanBeSaved() {
        RedirectURI uri = createURI();
        when(repo.save(uri)).thenReturn(uri);
        service.save(uri);
        verify(repo, times(1)).save(uri);

    }

    @Test
    @DisplayName("Verify that uris can be saved")
    void verifyThatUrisCanBeSaved() {
        Set<RedirectURI> uris = Set.of(createURI());
        when(repo.saveAll(uris)).thenReturn(new ArrayList<>(uris));
        service.saveAll(uris);
        verify(repo, times(1)).saveAll(uris);
    }

    @Test
    @DisplayName("Verify that uri can be read")
    void verifyThatUriCanBeRead() {
        RedirectURI uri = createURI();
        when(repo.findById(1L)).thenReturn(Optional.of(uri));
        service.findRedirectURIById(1L);
        verify(repo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Verify that uris can be read")
    void verifyThatUrisCanBeRead() {
        List<RedirectURI> uris = List.of(createURI());
        when(repo.findAll()).thenReturn(uris);
        service.findAll();
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("Verify that uri can be read by uri string")
    void verifyThatUriCanBeReadByUriString() {
        RedirectURI uri = createURI();
        String uriString = "http://127.0.0.1:1000";
        when(repo.findRedirectURIByUri(uriString)).thenReturn(Optional.of(uri));
        service.findRedirectURIByUri(uriString);
        verify(repo, times(1)).findRedirectURIByUri(uriString);
    }

    @Test
    @DisplayName("verify that redirectURI can be updated")
    void verifyThatRedirectURICanBeUpdated() {
        RedirectURI uri = createURI();
        when(repo.save(uri)).thenReturn(uri);
        service.save(uri);
        when(repo.findById(1L)).thenReturn(Optional.of(uri));
        service.update(1L, u -> u.setUri("https://127.0.0.1:1000"));
        verify(repo, times(1)).findById(1L);
        verify(repo, times(2)).save(uri);
    }

    @Test
    @DisplayName("Verify that uri can be deleted")
    void verifyThatUrisCanBeDeleted() {
        doNothing().when(repo).deleteById(1L);
        service.delete(1L);
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).deleteById(1L);
    }


}
