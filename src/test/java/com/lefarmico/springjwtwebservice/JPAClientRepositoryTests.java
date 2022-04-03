package com.lefarmico.springjwtwebservice;

import com.lefarmico.springjwtwebservice.entity.Client;
import com.lefarmico.springjwtwebservice.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
                .categoryId(1L)
                .languageId(2L)
                .nextQuizTime(134234L)
                .wordsInTest(5L)
                .build();
    }

    @DisplayName("JUnit test for save client operation")
    @Test
    void givenClientObject_whenSave_thenReturn_SavedClient() {
        client = Client.builder()
                .clientId("WERGWEPRGPE")
                .categoryId(2L)
                .languageId(3L)
                .nextQuizTime(8965844L)
                .wordsInTest(54L)
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
                .categoryId(2L)
                .languageId(3L)
                .nextQuizTime(8965844L)
                .wordsInTest(54L)
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
            client.setCategoryId(45L);
            client.setWordsInTest(85L);
            client.setNextQuizTime(5494L);
            client.setLanguageId(4895L);

            Client updatedClientForDb = clientRepository.save(client);

            assertThat(updatedClientForDb.getClientId()).isEqualTo("ewpfjewopriw");
            assertThat(updatedClientForDb.getCategoryId()).isEqualTo(45L);
            assertThat(updatedClientForDb.getWordsInTest()).isEqualTo(85L);
            assertThat(updatedClientForDb.getNextQuizTime()).isEqualTo(5494L);
            assertThat(updatedClientForDb.getLanguageId()).isEqualTo(4895L);
        } else {
            fail("Client not found.");
        }

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