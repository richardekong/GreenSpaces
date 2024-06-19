package com.daveace.greenspaces.lightweight;

import com.daveace.greenspaces.client.Client;
import com.daveace.greenspaces.tokensettings.TokenSettings;
import com.daveace.greenspaces.tokensettings.TokenSettingsRepo;
import com.daveace.greenspaces.tokensettings.TokenSettingsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenSettingsServiceTest {

    @Mock
    TokenSettingsRepo repo;

    @InjectMocks
    TokenSettingsService service;

    private TokenSettings settings() {
        return TokenSettings
                .builder()
                .id(1)
                .accessTokenTTL(10)
                .format("self-contained")
                .client(Client.builder().id("c1").build())
                .build();
    }

    @Test
    @DisplayName("Verify that token settings can be saved")
    void verifyThatTokenSettingsCanBeSaved() {
        TokenSettings settings = settings();
        when(repo.save(settings)).thenReturn(settings);
        service.save(settings);
        verify(repo, times(1)).save(settings);
    }

    @Test
    @DisplayName("Verify that all token settings can be saved")
    void verifyThatAllTokenSettingsCanBeSaved() {
        Set<TokenSettings> settings = Set.of(settings());
        when(repo.saveAll(settings)).thenReturn(new ArrayList<>(settings));
        service.saveAll(settings);
        verify(repo, times(1)).saveAll(settings);
    }

    @Test
    @DisplayName("Verify that token settings can be read")
    void verifyThatTokenSettingsCanBeRead() {
        TokenSettings settings = settings();
        when(repo.findById(settings.getId())).thenReturn(Optional.of(settings));
        service.findById(settings.getId());
        verify(repo, times(1)).findById(settings.getId());
    }

    @Test
    @DisplayName("verify that token settings can be read by client id")
    void verifyThatTokenSettingsCanBeReadByClientId(){
        Set<TokenSettings> settings = Set.of(settings());
        when(repo.findAllByClientId("c1")).thenReturn(Optional.of(settings));
        service.findByClientId("c1");
        verify(repo, times(1)).findAllByClientId("c1");
    }

    @Test
    @DisplayName("Verify that token settings can be updated")
    void verifyThatTokenSettingsCanBeUpdated() {
        TokenSettings settings = settings();
        when(repo.findById(1)).thenReturn(Optional.of(settings));
        service.update(1, ts-> ts.setAccessTokenTTL(30));
        assertThat(settings.getAccessTokenTTL()).isEqualTo(30);
        verify(repo, times(1)).findById(1);
    }

    @Test
    @DisplayName("Verify that token settings can be deleted")
    void verifyThatTokenSettingsCanBeDeleted() {
        service.delete(1);
        verify(repo, times(1)).existsById(1);
        verify(repo, times(1)).deleteById(1);
    }

}
