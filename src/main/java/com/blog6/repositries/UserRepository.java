package com.blog6.repositries;

import com.blog6.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User>findByUsernameOrEmail(String username,String email);
    Optional<User>findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String Email);
}
