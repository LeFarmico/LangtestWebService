package com.lefarmico.springjwtwebservice.repository;

import com.lefarmico.springjwtwebservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<Client, Long> {

    // TODO add update queries for all parameters
    @Query(
            nativeQuery = true,
            value = "DELETE FROM client WHERE client_id = :clientId"
    )
    @Modifying
    int deleteClientByClientId(@Param("clientId") String clientId);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM client WHERE client_id = :clientId"
    )
    Optional<Client> findClientByClientId(@Param("clientId") String clientId);
}
