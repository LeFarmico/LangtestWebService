package com.lefarmico.springjwtwebservice.service;

import com.lefarmico.springjwtwebservice.entity.Client;
import com.lefarmico.springjwtwebservice.repository.ClientRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@NoArgsConstructor
@Component
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Client addClient(String clientId) {
        Client client = Client.builder().clientId(clientId).build();
        return clientRepository.save(client);
    }

    public Boolean deleteClientByClientId(String clientId) {
        int id = clientRepository.deleteClientByClientId(clientId);
        return id > 0;
    }

    public Optional<Client> getClientByClientId(String clientId) {
        return clientRepository.findClientByClientId(clientId);
    }
}
