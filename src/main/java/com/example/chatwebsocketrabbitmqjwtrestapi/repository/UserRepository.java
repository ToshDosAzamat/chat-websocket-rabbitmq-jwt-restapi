package com.example.chatwebsocketrabbitmqjwtrestapi.repository;

import com.example.chatwebsocketrabbitmqjwtrestapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean deleteByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
}
