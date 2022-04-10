package com.lefarmico.springjwtwebservice.initialData;

import com.lefarmico.springjwtwebservice.entity.Word;
import com.lefarmico.springjwtwebservice.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WordsInitialData {

    @Autowired
    WordRepository wordRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        List<Word> wordList = new ArrayList<>();
        Word word1 = Word.builder()
                .id(1L)
                .categoryId(1L)
                .languageId(1L)
                .wordOriginal("CAT")
                .wordTranslation("Кошка")
                .build();
        Word word2 = Word.builder()
                .id(2L)
                .categoryId(1L)
                .languageId(1L)
                .wordOriginal("DOG")
                .wordTranslation("Собака")
                .build();
        Word word3 = Word.builder()
                .id(3L)
                .categoryId(1L)
                .languageId(1L)
                .wordOriginal("DUCK")
                .wordTranslation("утка")
                .build();
        wordList.add(word1);
        wordList.add(word2);
        wordList.add(word3);
        wordRepository.saveAll(wordList);
    }
}
