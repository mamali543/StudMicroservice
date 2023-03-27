package com.redacode.redacode.repo;

import com.redacode.redacode.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    //Try to retrieve user by email
    Optional<User> findByEmail(String email);
}
