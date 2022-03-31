package com.lefarmico.springjwtwebservice.repository;

import com.lefarmico.springjwtwebservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    // TODO add update queries for all parameters
}
