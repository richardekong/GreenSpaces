package com.daveace.greenspaces.lightweight;

import com.daveace.greenspaces.scope.Scope;
import com.daveace.greenspaces.scope.ScopeRepo;
import com.daveace.greenspaces.scope.ScopeService;
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
public class ScopeServiceTest {

    @Mock
    ScopeRepo repo;

    @InjectMocks
    ScopeService service;

    private Scope createScope(){
        return Scope.builder()
                .id(1)
                .scope(Scope.Scopes.OPENID.scope())
                .build();

    }

    @Test
    @DisplayName("Verify that scopes can be created")
    void verifyThatScopesCanBeCreated(){
        Scope scope = createScope();
        when(repo.save(scope)).thenReturn(scope);
        Scope savedScope = service.save(scope);
        verify(repo, times(1)).save(scope);

        Set<Scope> scopes = Set.of(scope);
        when(repo.saveAll(scopes)).thenReturn(new ArrayList<>(scopes));
        Set<Scope> savedScopes = service.saveAll(scopes);
        verify(repo, times(1)).saveAll(scopes);
    }

    @Test
    @DisplayName("Verify that scopes can be read")
    void verifyThatScopesCanBeRead(){
        Scope scope = createScope();
        when(repo.findById(scope.getId())).thenReturn(Optional.of(scope));
        Scope foundScope = service.findById(scope.getId());
        verify(repo, times(1)).findById(scope.getId());

        Set<Scope> scopes = Set.of(scope);
        when(repo.findAll()).thenReturn(new ArrayList<>(scopes));
        service.findAll();
        verify(repo, times(1)).findAll();

    }

    @Test
    @DisplayName("Verify that scope can be deleted")
    void verifyThatScopeCanBeDeleted(){
        Scope scope = createScope();
        doNothing().when(repo).deleteById(scope.getId());
        service.delete(scope.getId());
        verify(repo, times(1)).deleteById(scope.getId());
    }

}
