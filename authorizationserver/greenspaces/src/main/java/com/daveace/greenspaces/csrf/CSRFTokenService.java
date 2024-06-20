package com.daveace.greenspaces.csrf;

import com.daveace.greenspaces.response.GreenSpacesException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.daveace.greenspaces.util.Constants.*;

@Service
public class CSRFTokenService implements CsrfTokenRepository {

    private CSRFTokenRepo repo;

    @Autowired
    public void setRepo(CSRFTokenRepo repo) {
        this.repo = repo;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String token = UUID.randomUUID().toString();
        return new DefaultCsrfToken(X_CSRF_TOKEN, CSRF_PARAM_NAME, token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        String clientId = request.getHeader(X_IDENTIFIER);
        Optional<CSRFToken> existingToken = repo.findCSRFTokenByClientId(clientId);
        try{
            if (existingToken.isPresent())
            {
                CSRFToken csrfToken = existingToken.get();
                if (repo.isExpired(csrfToken))
                {
                    repo.deleteById(csrfToken.getId());
                    CSRFToken newToken = createNewToken(token, clientId);
                    repo.save(newToken);
                    return;
                }
                csrfToken.setToken(token.getToken());
            }
            else
            {
                CSRFToken newToken = createNewToken(token, clientId);
                repo.save(newToken);
            }
        }
        catch(RuntimeException e)
        {
            throw new GreenSpacesException(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String clientId = request.getHeader(X_IDENTIFIER);
        Optional<CSRFToken> existingToken = repo.findCSRFTokenByClientId(clientId);
        if (existingToken.isPresent()) {
            CSRFToken csrfToken = existingToken.get();
            if (repo.isExpired(csrfToken)){
                throw new GreenSpacesException(LOGIN_AGAIN,HttpStatus.FORBIDDEN);
            }
            return new DefaultCsrfToken(X_CSRF_TOKEN, CSRF_PARAM_NAME, csrfToken.getToken());
        }
        return null;
    }

    private CSRFToken createNewToken(CsrfToken token, String clientId) {
        return CSRFToken.builder()
                .clientId(clientId)
                .token(token.getToken())
                .expiresIn(30L)
                .build();
    }

}
