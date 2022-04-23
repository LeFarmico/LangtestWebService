package com.lefarmico.springjwtwebservice;

import com.lefarmico.springjwtwebservice.entity.QuizData;
import com.lefarmico.springjwtwebservice.repository.QuizDataRepository;
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
public class JPAQuizDataRepositoryTests {

    @Autowired
    QuizDataRepository quizDataRepository;

    private QuizData testQuizData;

    @BeforeEach
    void setUp() {
        testQuizData = QuizData.builder()
                .chatId(1651651L)
                .currentWordNumber((short) 1)
                .breakTimeInMillis(456L)
                .wordsInQuiz((short) 5)
                .categoryId(1L)
                .languageId(1L)
                .status("launched")
                .build();
    }

    @DisplayName("JUnit test for save quiz operation")
    @Test
    void givenQuizObject_whenSave_thenReturn_Quiz() {
        QuizData quizData = QuizData.builder()
                .chatId(49498498L)
                .currentWordNumber((short) 1)
                .breakTimeInMillis(456L)
                .wordsInQuiz((short) 5)
                .categoryId(1L)
                .languageId(1L)
                .status("launched")
                .build();

        QuizData savedQuizData = quizDataRepository.save(quizData);
        assertThat(savedQuizData).isNotNull();
        assertThat(savedQuizData.getChatId()).isEqualTo(49498498L);
    }

    @DisplayName("JUnit test for get all quiz operation")
    @Test
    void givenQuizList_whenFindAll_thenQuizList() {
        int countOfInsertedEntities = 0;
        QuizData quizData = QuizData.builder()
                .chatId(9519819L)
                .currentWordNumber((short) 1)
                .breakTimeInMillis(456L)
                .wordsInQuiz((short) 5)
                .categoryId(1L)
                .languageId(1L)
                .status("launched")
                .build();

        List<QuizData> quizDataList = quizDataRepository.findAll();

        quizDataRepository.save(quizData);
        countOfInsertedEntities++;

        if (quizDataRepository.findById(testQuizData.getChatId()).isPresent()) {
            quizDataRepository.save(testQuizData);
            countOfInsertedEntities++;
        }

        List<QuizData> newLanguageList = quizDataRepository.findAll();

        assertThat(newLanguageList).isNotNull();
        assertThat(newLanguageList.size()).isEqualTo(quizDataList.size() + countOfInsertedEntities);
    }

    @DisplayName("JUnit test for get quiz by id operation")
    @Test
    void givenQuizObject_whenFindById_thenReturnQuizObject() {

        Optional<QuizData> optionalQuiz = quizDataRepository.findById(testQuizData.getChatId());
        if (optionalQuiz.isPresent()) {
            QuizData quizDataDB = optionalQuiz.get();
            assertThat(quizDataDB).isNotNull();
        } else {
            quizDataRepository.save(testQuizData);
            Optional<QuizData> savedQuiz = quizDataRepository.findById(testQuizData.getChatId());
            assertThat(savedQuiz.isPresent()).isEqualTo(true);
        }

    }

    @DisplayName("JUnit test for update quiz operation")
    @Test
    void givenQuizObject_whenUpdateQuiz_thenReturnUpdatedQuiz() {

        quizDataRepository.save(testQuizData);
        Optional<QuizData> optionalQuiz = quizDataRepository.findById(testQuizData.getChatId());
        if (optionalQuiz.isPresent()) {
            QuizData quizDataDB = optionalQuiz.get();
            quizDataDB.setChatId(111444555L);
            quizDataDB.setStatus("stopped");
            quizDataDB.setCurrentWordNumber((short) 10L);
            quizDataDB.setCategoryId(25L);
            quizDataDB.setLanguageId(25L);
            quizDataDB.setBreakTimeInMillis(4862L);
            quizDataDB.setWordsInQuiz((short) 20);

            QuizData updatedQuizData = quizDataRepository.save(quizDataDB);

            assertThat(updatedQuizData.getChatId()).isEqualTo(111444555L);
            assertThat(updatedQuizData.getStatus()).isEqualTo("stopped");
            assertThat(updatedQuizData.getCurrentWordNumber()).isEqualTo((short) 10);
            assertThat(updatedQuizData.getCategoryId()).isEqualTo(25L);
            assertThat(updatedQuizData.getLanguageId()).isEqualTo(25L);
            assertThat(updatedQuizData.getBreakTimeInMillis()).isEqualTo(4862L);
            assertThat(updatedQuizData.getWordsInQuiz()).isEqualTo((short) 20);
        } else {
            fail("Quiz not found.");
        }

    }

    @DisplayName("JUnit test for delete quiz operation")
    @Test
    void givenQuizObject_whenDelete_thenRemoveQuiz() {

        if (quizDataRepository.findById(testQuizData.getChatId()).isPresent()) {
            quizDataRepository.save(testQuizData);
        }

        quizDataRepository.deleteQuizDataByClientId(testQuizData.getChatId());
        Optional<QuizData> quizOptional = quizDataRepository.findById(testQuizData.getChatId());

        assertThat(quizOptional).isEmpty();
    }
}
