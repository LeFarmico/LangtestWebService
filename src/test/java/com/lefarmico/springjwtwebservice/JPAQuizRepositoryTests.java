package com.lefarmico.springjwtwebservice;

import com.lefarmico.springjwtwebservice.entity.Quiz;
import com.lefarmico.springjwtwebservice.repository.QuizRepository;
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
public class JPAQuizRepositoryTests {

    @Autowired
    QuizRepository quizRepository;

    private Quiz testQuiz;

    @BeforeEach
    void setUp() {
        testQuiz = Quiz.builder()
                .clientId("ewiufweiuf")
                .currentSequenceNumber(1L)
                .status("launched")
                .build();
    }

    @DisplayName("JUnit test for save quiz operation")
    @Test
    void givenQuizObject_whenSave_thenReturn_Quiz() {
        Quiz quiz = Quiz.builder()
                .clientId("wejfnwepoifn")
                .currentSequenceNumber(2L)
                .status("paused")
                .build();
        Quiz savedQuiz = quizRepository.save(quiz);
        assertThat(savedQuiz).isNotNull();
        assertThat(savedQuiz.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get all quiz operation")
    @Test
    void givenQuizList_whenFindAll_thenQuizList() {
        Quiz quiz = Quiz.builder()
                .clientId("wejfnwepoifn")
                .currentSequenceNumber(2L)
                .status("paused")
                .build();

        List<Quiz> quizList = quizRepository.findAll();

        quizRepository.save(quiz);
        quizRepository.save(testQuiz);

        List<Quiz> newLanguageList = quizRepository.findAll();

        assertThat(newLanguageList).isNotNull();
        assertThat(newLanguageList.size()).isEqualTo(quizList.size() + 2);
    }

    @DisplayName("JUnit test for get quiz by id operation")
    @Test
    void givenQuizObject_whenFindById_thenReturnQuizObject() {

        quizRepository.save(testQuiz);
        Optional<Quiz> optionalQuiz = quizRepository.findById(testQuiz.getId());
        if (optionalQuiz.isPresent()) {
            Quiz quizDB = optionalQuiz.get();
            assertThat(quizDB).isNotNull();
        } else {
            fail("Quiz not found!");
        }

    }

    @DisplayName("JUnit test for update quiz operation")
    @Test
    void givenQuizObject_whenUpdateQuiz_thenReturnUpdatedQuiz() {

        quizRepository.save(testQuiz);
        Optional<Quiz> optionalQuiz = quizRepository.findById(testQuiz.getId());
        if (optionalQuiz.isPresent()) {
            Quiz quizDB = optionalQuiz.get();
            quizDB.setClientId("133r4");
            quizDB.setStatus("stopped");
            quizDB.setCurrentSequenceNumber(10L);

            Quiz updatedQuiz = quizRepository.save(quizDB);

            assertThat(updatedQuiz.getClientId()).isEqualTo("133r4");
            assertThat(updatedQuiz.getStatus()).isEqualTo("stopped");
            assertThat(updatedQuiz.getCurrentSequenceNumber()).isEqualTo(10L);
        } else {
            fail("Quiz not found.");
        }

    }

    @DisplayName("JUnit test for delete quiz operation")
    @Test
    void givenQuizObject_whenDelete_thenRemoveQuiz() {

        quizRepository.save(testQuiz);

        quizRepository.deleteById(testQuiz.getId());
        Optional<Quiz> quizOptional = quizRepository.findById(testQuiz.getId());

        assertThat(quizOptional).isEmpty();
    }
}
