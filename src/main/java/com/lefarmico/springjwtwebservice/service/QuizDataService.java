package com.lefarmico.springjwtwebservice.service;

import com.lefarmico.springjwtwebservice.entity.QuizData;
import com.lefarmico.springjwtwebservice.repository.QuizDataRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class QuizDataService {

    @Autowired
    QuizDataRepository quizDataRepository;

    public QuizData createQuizDataForClient(
            String clientId, Short wordsInQuiz, Long nextQuizTime,
            Long languageId, Long categoryId
    ) {
        QuizData quizData = QuizData.builder()
                .clientId(clientId)
                .wordsInQuiz(wordsInQuiz)
                .nextQuizTime(nextQuizTime)
                .languageId(languageId)
                .categoryId(categoryId)
                .build();
        return quizDataRepository.save(quizData);
    }

    public Optional<QuizData> updateAndResetQuizDataForClient(
            String clientId, Short wordsInQuiz, Long nextQuizTime,
            Long languageId, Long categoryId
    ) {
        Optional<QuizData> optionalQuizDataDB = quizDataRepository.findById(clientId);
        if (optionalQuizDataDB.isPresent()) {
            QuizData quizDataDB = optionalQuizDataDB.get();
            Long updatedNextQuizTime = getOrElse(nextQuizTime, quizDataDB.getNextQuizTime());
            Long updatedLanguageId = getOrElse(languageId, quizDataDB.getLanguageId());
            Long updatedCategoryId = getOrElse(categoryId, quizDataDB.getCategoryId());
            Short updatedWordsInQuiz = getOrElse(wordsInQuiz, quizDataDB.getWordsInQuiz());

            QuizData quizDataForUpdate = QuizData.builder()
                    .clientId(quizDataDB.getClientId())
                    .nextQuizTime(updatedNextQuizTime)
                    .languageId(updatedLanguageId)
                    .categoryId(updatedCategoryId)
                    .wordsInQuiz(updatedWordsInQuiz)
                    .status(QuizData.Status.DEFAULT.name())
                    .currentWordNumber((short) 0)
                    .build();

            QuizData updatedQuizDataDB = quizDataRepository.save(quizDataForUpdate);
            return Optional.of(updatedQuizDataDB);

        } else {
            return Optional.empty();
        }
    }

    public Optional<QuizData> getQuizDataByClientId(String clientId) {
        return quizDataRepository.findById(clientId);
    }

    public Boolean deleteQuizDataForClient(String clientId) {
        int deletedId = quizDataRepository.deleteQuizDataByClientId(clientId);
        return deletedId > 0;
    }

    private <T> T getOrElse(T nullableObject, @NotNull T notNullableObject) {
        return Objects.requireNonNullElse(nullableObject, notNullableObject);
    }
}
