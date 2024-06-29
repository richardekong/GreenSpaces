package com.daveace.greenspaces.lightweight;

import com.daveace.greenspaces.authenticationmethod.AuthenticationMethod;
import com.daveace.greenspaces.authenticationmethod.AuthenticationMethodRepo;
import com.daveace.greenspaces.authenticationmethod.AuthenticationMethodService;
import com.daveace.greenspaces.authenticationmethod.AuthenticationMethods;
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
public class AuthenticationMethodServiceTest {

    @Mock
    AuthenticationMethodRepo repo;

    @InjectMocks
    AuthenticationMethodService service;

    private AuthenticationMethod authenticationMethod(){
        return AuthenticationMethod
                .builder()
                .id(1)
                .method(AuthenticationMethods.CLIENT_SECRET_BASIC.method())
                .build();
    }

    @Test
    @DisplayName("Verify that authentication method can be saved")
    void verifyThatAuthenticationMethodCanBeSaved() throws Exception{
        AuthenticationMethod method = authenticationMethod();
        when(repo.save(method)).thenReturn(method);
        service.save(method);
        verify(repo, times(1)).save(method);
    }

    @Test
    @DisplayName("Verify that all authentication methods can be saved")
    void verifyThatAllAuthenticationMethodsCanBeSaved() throws Exception{
        var methods = Set.of(authenticationMethod());
        when(repo.saveAll(methods)).thenReturn(new ArrayList<>(methods));
        service.saveAll(methods);
        verify(repo, times(1)).saveAll(methods);
    }

    @Test
    @DisplayName("Verify that authentication method can be read by id")
    void verifyThatAuthenticationMethodCanBeReadById() throws Exception{
        var method = authenticationMethod();
        when(repo.findById(1)).thenReturn(Optional.of(method));
        service.findById(1);
        verify(repo, times(1)).findById(1);
    }

    @Test
    @DisplayName("Verify that authentication method can be updated")
    void verifyThatAuthenticationMethodCanBeUpdated() throws Exception{
        var method = authenticationMethod();
        when(repo.findById(1)).thenReturn(Optional.of(method));
        when(repo.save(method)).thenReturn(method);
        service.update(1, am -> am.setMethod(AuthenticationMethods.CLIENT_SECRET_JWT.method()));
        verify(repo, times(1)).save(method);
    }

    @Test
    @DisplayName("Verify that authentication method can be deleted")
    void verifyThatAuthenticationMethodCanBeDeleted() throws Exception{
        service.delete(1);
        verify(repo, times(1)).existsById(1);
        verify(repo, times(1)).deleteById(1);
    }

}

