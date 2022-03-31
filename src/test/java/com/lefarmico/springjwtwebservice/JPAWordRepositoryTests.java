package com.lefarmico.springjwtwebservice;

import com.lefarmico.springjwtwebservice.entity.Word;
import com.lefarmico.springjwtwebservice.repository.WordRepository;
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
public class JPAWordRepositoryTests {

    @Autowired
    WordRepository wordRepository;

    private Word testWord;

    @BeforeEach
    void setUp() {
        testWord = Word.builder()
                .wordOriginal("fish_set_before")
                .wordTranslation("рыба_set_before")
                .categoryId(1L)
                .languageId(1L)
                .build();
    }

    @DisplayName("JUnit test for save word operation")
    @Test
    void givenWordObject_whenSave_thenReturn_SavedWord() {
        Word word = Word.builder()
                .wordOriginal("bird")
                .wordTranslation("птица")
                .categoryId(1L)
                .languageId(1L)
                .build();
        Word savedWord = wordRepository.save(word);
        assertThat(savedWord).isNotNull();
        assertThat(savedWord.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get all word operation")
    @Test
    void givenWordList_whenFindAll_thenWordList() {
        Word word1 = Word.builder()
                .wordOriginal("pool")
                .wordTranslation("бассейн")
                .categoryId(1L)
                .languageId(1L)
                .build();

        List<Word> wordsList = wordRepository.findAll();

        wordRepository.save(testWord);
        wordRepository.save(word1);

        List<Word> newWordList = wordRepository.findAll();

        assertThat(newWordList).isNotNull();
        assertThat(newWordList.size()).isEqualTo(wordsList.size() + 2);
    }

    @DisplayName("JUnit test for get word by id operation")
    @Test
    void givenWordObject_whenFindById_thenReturnWordObject() {

        wordRepository.save(testWord);
        Word wordDb = wordRepository.findById(testWord.getId()).get();

        assertThat(wordDb).isNotNull();
    }

    @DisplayName("JUnit test for update word operation")
    @Test
    void givenWordObject_whenUpdateEmployee_thenReturnWordEmployee() {

        wordRepository.save(testWord);
        Word wordDb = wordRepository.findById(testWord.getId()).get();
        wordDb.setWordOriginal("bbb");
        wordDb.setWordTranslation("ббб");
        wordDb.setLanguageId(999L);
        wordDb.setCategoryId(999L);

        Word updatedWordDb = wordRepository.save(wordDb);

        assertThat(updatedWordDb.getWordOriginal()).isEqualTo("bbb");
        assertThat(updatedWordDb.getWordTranslation()).isEqualTo("ббб");
        assertThat(updatedWordDb.getLanguageId()).isEqualTo(999L);
        assertThat(updatedWordDb.getCategoryId()).isEqualTo(999L);
    }

    @DisplayName("JUnit test for delete word operation")
    @Test
    void givenWordObject_whenDelete_thenRemoveWord() {

        wordRepository.save(testWord);

        wordRepository.deleteById(testWord.getId());
        Optional<Word> wordOptional = wordRepository.findById(testWord.getId());

        assertThat(wordOptional).isEmpty();
    }
}
