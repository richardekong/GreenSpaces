package com.daveace.greenspaces.lightweight;

import com.daveace.greenspaces.authority.Authority;
import com.daveace.greenspaces.authority.AuthorityRepo;
import com.daveace.greenspaces.authority.AuthorityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorityServiceTest {

    @Mock
    AuthorityRepo repo;

    @InjectMocks
    AuthorityService service;

    private Authority authority(){
        return Authority
                .builder()
                .id(1)
                .authority("read")
                .build();
    }

    @Test
    @DisplayName("Verify that authority can be saved")
    void verifyThatAuthorityCanBeSaved(){
        Authority authority = authority();
        when(repo.save(authority)).thenReturn(authority);
        service.save(authority);
        verify(repo, times(1)).save(authority);
    }

    @Test
    @DisplayName("Verify that authorities can all be saved")
    void verifyThatAuthoritiesCanAllBeSaved(){
        Set<Authority> authorities = Set.of(authority());
        when(repo.saveAll(authorities)).thenReturn(new ArrayList<>(authorities));
        service.saveAll(authorities);
        verify(repo, times(1)).saveAll(authorities);

    }

    @Test
    @DisplayName("Verify that authority can be read")
    void verifyThatAuthorityCanBeRead(){
        Authority authority = authority();
        when(repo.findById(1)).thenReturn(Optional.of(authority));
        service.findById(1);
        verify(repo, times(1)).findById(1);

    }

    @Test
    @DisplayName("Verify that authorities can be read")
    void verifyThatAuthoritiesCanBeRead(){
        Set<Authority> authorities = Set.of(authority());
        when(repo.findAll()).thenReturn(new ArrayList<>(authorities));
        service.findAll();
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("Verify that authority can be updated")
    void verifyThatAuthorityCanBeUpdated(){
        Authority authority = authority();
        when(repo.findById(1)).thenReturn(Optional.of(authority));
        service.update(1, a -> a.setAuthority("execute"));
        verify(repo, times(1)).save(authority);
    }

    @Test
    @DisplayName("Verify that authority can be deleted")
    void verifyThatAuthorityCanBeDeleted(){
        service.delete(1);
        verify(repo, times(1)).existsById(1);
        verify(repo, times(1)).deleteById(1);
    }
}

