package com.daveace.greenspaces.user;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String>{

        Optional<User> findUserById(String id);

        Optional<User> findUserByUsername(String username);

        boolean existsByUsername(String username);
}
