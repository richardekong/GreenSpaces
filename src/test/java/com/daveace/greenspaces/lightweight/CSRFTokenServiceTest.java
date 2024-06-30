package com.daveace.greenspaces.lightweight;

import com.daveace.greenspaces.csrf.CSRFToken;
import com.daveace.greenspaces.csrf.CSRFTokenRepo;
import com.daveace.greenspaces.csrf.CSRFTokenService;
import com.daveace.greenspaces.response.GreenSpacesException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfToken;

import java.util.Optional;

import static com.daveace.greenspaces.util.Constants.LOGIN_AGAIN;
import static com.daveace.greenspaces.util.Constants.X_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CSRFTokenServiceTest {

    @Mock
    private CSRFTokenRepo repo;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private CSRFTokenService service;

    private final String clientId = "b583b456-9300-4cbd-4bcd-199225f5d42c";

    private CsrfToken generatedToken;

    private final CSRFToken existingToken =  CSRFToken
            .builder()
            .id("c1")
            .token("a236b456-9693-4b4a-ad69-5b5325f5d42c")
            .clientId(clientId)
            .build();

    @BeforeEach
    void init(){
        generatedToken = service.generateToken(request);
    }

    @Test
    @DisplayName("Verify that CSRF token can be generated")
    void verifyThatCSRFTokenCanBeGenerated() {
        assertThat(generatedToken).isNotNull();
        assertThat(generatedToken.getToken().length()).isEqualTo(36);
    }

    @Test
    @DisplayName("Verify that CSRF token can be saved")
    void verifyThatCSRFTokenCanBeSaved() {
        when(request.getHeader(X_IDENTIFIER)).thenReturn(clientId);
        when(repo.findCSRFTokenByClientId(clientId)).thenReturn(Optional.empty());
        when(repo.save(any(CSRFToken.class))).thenReturn(any(CSRFToken.class));

        service.saveToken(generatedToken, request, response);

        verify(repo).findCSRFTokenByClientId(clientId);
        verify(repo).save(any(CSRFToken.class));
    }

    @Test
    @DisplayName("Verify that unexpired existing token can be reused")
    void verifyThatUnexpiredExistingTokenCanBeReusedWhenSavedTokenIsInvoked() {
        when(request.getHeader(X_IDENTIFIER)).thenReturn(clientId);
        when(repo.findCSRFTokenByClientId(clientId)).thenReturn(Optional.of(existingToken));
        when(repo.isExpired(existingToken)).thenReturn(false);
        service.saveToken(generatedToken, request, response);
        verify(repo).findCSRFTokenByClientId(clientId);
        verify(repo).isExpired(existingToken);
        assertEquals(generatedToken.getToken(), existingToken.getToken());
    }

    @Test
    @DisplayName("Verify that an expired existing token can't be reused when saveToken is invoked")
    void verifyThatExpiredExistingTokenCannotBeReusedWhenSavedTokenIsInvoked() {
        when(request.getHeader(X_IDENTIFIER)).thenReturn(clientId);
        when(repo.findCSRFTokenByClientId(clientId)).thenReturn(Optional.of(existingToken));
        when(repo.isExpired(existingToken)).thenReturn(true);

        service.saveToken(generatedToken, request, response);

        verify(repo).deleteById(existingToken.getId());
        verify(repo).save(any(CSRFToken.class));

    }

    @Test
    @DisplayName("Verify that CSRF token can be loaded")
    void verifyThatCSRFTokenCanBeLoaded() {
        when(request.getHeader(X_IDENTIFIER)).thenReturn(clientId);
        when(repo.findCSRFTokenByClientId(clientId)).thenReturn(Optional.of(existingToken));
        when(repo.isExpired(existingToken)).thenReturn(false);
        var loadedToken = service.loadToken(request).getToken();

        verify(repo).findCSRFTokenByClientId(clientId);
        verify(repo).isExpired(existingToken);
        assertThat(loadedToken).isNotNull();
        assertThat(loadedToken).isNotBlank();
        assertThat(loadedToken.length()).isEqualTo(36);
        assertThat(loadedToken).isEqualTo(existingToken.getToken());
    }

    @Test
    @DisplayName("Verify that expired CSRF token can't be loaded")
    void verifyThatExpiredCSRFTokenCannotBeLoaded() {
        when(request.getHeader(X_IDENTIFIER)).thenReturn(clientId);
        when(repo.findCSRFTokenByClientId(clientId)).thenReturn(Optional.of(existingToken));
        when(repo.isExpired(existingToken)).thenReturn(true);
        GreenSpacesException ex = assertThrows(GreenSpacesException.class, () -> service.loadToken(request));
        assertThat(ex.getMessage()).isEqualTo(LOGIN_AGAIN);
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.FORBIDDEN);

    }

}
