package com.example.bank.repository;

import com.example.bank.models.Users;
import org.apache.catalina.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsByUsername(String username);

    Optional<Users> findByUsername(String username);
}
