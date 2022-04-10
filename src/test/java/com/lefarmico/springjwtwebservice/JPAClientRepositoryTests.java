package com.lefarmico.springjwtwebservice;

import com.lefarmico.springjwtwebservice.entity.Client;
import com.lefarmico.springjwtwebservice.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JPAClientRepositoryTests {

    @Autowired
    ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .clientId("erljgptrnpioevn")
                .build();
    }

    @DisplayName("JUnit test for save client operation")
    @Test
    void givenClientObject_whenSave_thenReturn_SavedClient() {
        client = Client.builder()
                .clientId("WERGWEPRGPE")
                .build();
        Client savedClient = clientRepository.save(client);
        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get all client operation")
    @Test
    void givenClientList_whenFindAll_thenClientList() {
        Client client = Client.builder()
                .clientId("WERGWEPRGPE")
                .build();

        List<Client> wordsList = clientRepository.findAll();

        clientRepository.save(this.client);
        clientRepository.save(client);

        List<Client> clientsList = clientRepository.findAll();

        assertThat(clientsList).isNotNull();
        assertThat(clientsList.size()).isEqualTo(wordsList.size() + 2);
    }

    @DisplayName("JUnit test for get client by id operation")
    @Test
    void givenClientObject_whenFindById_thenReturnClientObject() {

        clientRepository.save(client);
        Optional<Client> optionalClient = clientRepository.findById(client.getId());
        if (optionalClient.isPresent()) {
            Client clientFromDB = optionalClient.get();
            assertThat(clientFromDB).isNotNull();
        } else {
            fail("Client not found.");
        }
    }

    @DisplayName("JUnit test for update client operation")
    @Test
    void givenClientObject_whenUpdateClient_thenReturnClientUpdated() {

        clientRepository.save(client);
        Optional<Client> optionalClient = clientRepository.findById(client.getId());
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setClientId("ewpfjewopriw");

            Client updatedClientForDb = clientRepository.save(client);

            assertThat(updatedClientForDb.getClientId()).isEqualTo("ewpfjewopriw");
        } else {
            fail("Client not found.");
        }

    }

    @DisplayName("JUnit test for delete client by clientId operation")
    @Test
    void deleteGivenClientObject_byClientId() {
        Client savedClient = clientRepository.save(client);
        clientRepository.deleteClientByClientId(savedClient.getClientId());
        Integer invalidClientIdResult = clientRepository.deleteClientByClientId(savedClient.getClientId());

        Optional<Client> clientOptional = clientRepository.findById(client.getId());
        assertThat(clientOptional).isEmpty();
        assertThat(invalidClientIdResult).isEqualTo(0);
    }

    @DisplayName("JUnit test for get client by clientId operation")
    @Test
    void givenClient_whenFindByClientId_thenReturnClient() {
        Client savedClient = clientRepository.save(client);
        Optional<Client> clientDB = clientRepository.findClientByClientId(savedClient.getClientId());
        Optional<Client> clientDBInvalid = clientRepository.findClientByClientId("XXX");

        assertThat(clientDB.isPresent()).isEqualTo(true);
        assertThat(clientDBInvalid.isPresent()).isEqualTo(false);
    }

    @DisplayName("JUnit test for delete client operation")
    @Test
    void givenClientObject_whenDelete_thenRemoveClient() {

        clientRepository.save(client);

        clientRepository.deleteById(client.getId());
        Optional<Client> clientOptional = clientRepository.findById(client.getId());

        assertThat(clientOptional).isEmpty();
    }
}