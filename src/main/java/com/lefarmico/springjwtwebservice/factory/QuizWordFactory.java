package com.lefarmico.springjwtwebservice.factory;

import com.lefarmico.springjwtwebservice.entity.QuizWord;
import com.lefarmico.springjwtwebservice.entity.FileWord;
import com.lefarmico.springjwtwebservice.entity.Word;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.lefarmico.springjwtwebservice.utils.ListUtils.getRandomElementsFromList;

@Component
public class QuizWordFactory {

    public QuizWord createQuizWord(Long chatId, Word word, List<String> wrongTranslationList) {

        List<String> filteredWordTranslationsList = wrongTranslationList.stream()
                .filter(wordTranslation -> !Objects.equals(word.getWordTranslation(), wordTranslation))
                .collect(Collectors.toList());

        Collections.shuffle(filteredWordTranslationsList);
        List<String> randomisedWordTranslations = getRandomElementsFromList(filteredWordTranslationsList, 2);

        return QuizWord.builder()
                .originalWord(word.getWordOriginal())
                .correctTranslation(word.getWordTranslation())
                .wrongTranslations(randomisedWordTranslations)
                .chatId(chatId)
                .isAnswered(false)
                .build();
    }
}
