package com.lefarmico.springjwtwebservice.repository;

import com.lefarmico.springjwtwebservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
}