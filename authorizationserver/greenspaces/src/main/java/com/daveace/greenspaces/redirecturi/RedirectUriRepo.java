package com.daveace.greenspaces.redirecturi;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RedirectUriRepo extends JpaRepository<RedirectURI, Long>{

    Optional<RedirectURI> findRedirectURIByUri(String uri);

}
