package com.lefarmico.springjwtwebservice;

import com.lefarmico.springjwtwebservice.entity.User;
import com.lefarmico.springjwtwebservice.repository.UserRepository;
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
public class JPAUserRepositoryTests {

//    @Autowired
//    UserRepository userRepository;
//
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        user = User.builder()
//                .email("1234@gmail.com")
//                .password("1234")
//                .build();
//    }
//
//    @DisplayName("JUnit test for save user operation")
//    @Test
//    void givenUserObject_whenSave_thenReturn_SavedUser() {
//        User user = User.builder()
//                .email("564@gmail.com")
//                .password("4567")
//                .build();
//        User savedUser = userRepository.save(user);
//        assertThat(savedUser).isNotNull();
//        assertThat(savedUser.getId()).isGreaterThan(0);
//    }
//
//    @DisplayName("JUnit test for get all user operation")
//    @Test
//    void givenUserList_whenFindAll_thenUserList() {
//        User user = User.builder()
//                .email("5164@gmail.com")
//                .password("4567")
//                .build();
//        User user2 = User.builder()
//                .email("15164@gmail.com")
//                .password("4567")
//                .build();
//
//        List<User> userList = userRepository.findAll();
//
//        userRepository.save(user2);
//        userRepository.save(user);
//
//        List<User> usersList = userRepository.findAll();
//
//        assertThat(usersList).isNotNull();
//        assertThat(usersList.size()).isEqualTo(userList.size() + 2);
//    }
//
//    @DisplayName("JUnit test for get user by id operation")
//    @Test
//    void givenUserObject_whenFindById_thenReturnUserObject() {
//
//        userRepository.save(user);
//        Optional<User> optionalUser = userRepository.findById(user.getId());
//        if (optionalUser.isPresent()) {
//            User userFromDB = optionalUser.get();
//            assertThat(userFromDB).isNotNull();
//        } else {
//            fail("User not found.");
//        }
//
//    }
//
//    @DisplayName("JUnit test for update user operation")
//    @Test
//    void givenUserObject_whenUpdateUser_thenReturnUserUpdated() {
//
//        User user = User.builder()
//                .email("aaa@gmail.com")
//                .password("4567")
//                .build();
//
//        userRepository.save(user);
//        Optional<User> optionalUser = userRepository.findById(user.getId());
//        if (optionalUser.isPresent()) {
//            User userDB = optionalUser.get();
//            userDB.setEmail("qwert@mail.ru");
//            userDB.setPassword("ббб");
//
//            User updatedUserForDb = userRepository.save(userDB);
//            assertThat(updatedUserForDb.getEmail()).isEqualTo("qwert@mail.ru");
//            assertThat(updatedUserForDb.getPassword()).isEqualTo("ббб");
//        } else {
//            fail("User not found.");
//        }
//    }
//
//    @DisplayName("JUnit test for delete user operation")
//    @Test
//    void givenUserObject_whenDelete_thenRemoveUser() {
//
//        userRepository.save(user);
//
//        userRepository.deleteById(user.getId());
//        Optional<User> userOptional = userRepository.findById(user.getId());
//
//        assertThat(userOptional).isEmpty();
//    }
}
