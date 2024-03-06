package com.example.chatwebsocketrabbitmqjwtrestapi.repository;

import com.example.chatwebsocketrabbitmqjwtrestapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
