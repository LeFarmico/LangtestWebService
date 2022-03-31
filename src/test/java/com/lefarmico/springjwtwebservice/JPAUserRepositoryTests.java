package com.lefarmico.springjwtwebservice;

import com.lefarmico.springjwtwebservice.entity.User;
import com.lefarmico.springjwtwebservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class JPAUserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("1234@gmail.com")
                .password("1234")
                .build();
    }

    @DisplayName("JUnit test for save user operation")
    @Test
    void givenUserObject_whenSave_thenReturn_SavedUser() {
        User user = User.builder()
                .email("564@gmail.com")
                .password("4567")
                .build();
        User savedUser = userRepository.save(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get all user operation")
    @Test
    void givenUserList_whenFindAll_thenUserList() {
        User user = User.builder()
                .email("564@gmail.com")
                .password("4567")
                .build();

        List<User> wordsList = userRepository.findAll();

        userRepository.save(this.user);
        userRepository.save(user);

        List<User> usersList = userRepository.findAll();

        assertThat(usersList).isNotNull();
        assertThat(usersList.size()).isEqualTo(wordsList.size() + 2);
    }

    @DisplayName("JUnit test for get user by id operation")
    @Test
    void givenUserObject_whenFindById_thenReturnUserObject() {

        userRepository.save(user);
        User userFromDB = userRepository.findById(user.getId()).get();

        assertThat(userFromDB).isNotNull();
    }

    @DisplayName("JUnit test for update user operation")
    @Test
    void givenUserObject_whenUpdateEmployee_thenReturnUserEmployee() {

        userRepository.save(user);
        User user = userRepository.findById(this.user.getId()).get();
        user.setEmail("qwert@mail.ru");
        user.setPassword("ббб");

        User updatedUserForDb = userRepository.save(user);

        assertThat(updatedUserForDb.getEmail()).isEqualTo("qwert@mail.ru");
        assertThat(updatedUserForDb.getPassword()).isEqualTo("ббб");
    }

    @DisplayName("JUnit test for delete user operation")
    @Test
    void givenUserObject_whenDelete_thenRemoveUser() {

        userRepository.save(user);

        userRepository.deleteById(user.getId());
        Optional<User> userOptional = userRepository.findById(user.getId());

        assertThat(userOptional).isEmpty();
    }
}
