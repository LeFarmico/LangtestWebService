package com.lefarmico.springjwtwebservice.service;

import com.lefarmico.springjwtwebservice.entity.QuizData;
import com.lefarmico.springjwtwebservice.exception.ClientNotFoundException;
import com.lefarmico.springjwtwebservice.repository.QuizDataRepository;
import com.lefarmico.springjwtwebservice.repository.QuizWordRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class QuizDataService {

    @Autowired
    QuizDataRepository quizDataRepository;

    @Autowired
    QuizWordRepository quizWordRepository;

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

    public QuizData updateAndResetQuizDataForClient(
            String clientId, Short wordsInQuiz, Long nextQuizTime,
            Long languageId, Long categoryId
    ) throws ClientNotFoundException {
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

            // TODO: зупустить в отдельном потоке?
            if (languageId != null || categoryId != null) {
                quizWordRepository.deleteQuizWordsByClientId(clientId);
            }
            return updatedQuizDataDB;

        } else {
            throw new ClientNotFoundException("QuizData for clientId " + clientId + " is not found");
        }
    }

    public Optional<QuizData> getQuizDataByClientId(String clientId) {
        return quizDataRepository.findById(clientId);
    }

    public Boolean deleteQuizDataForClient(String clientId) {
        int deletedId = quizDataRepository.deleteQuizDataByClientId(clientId);
        return deletedId > 0;
    }

    public QuizData updateQuizData(QuizData quizData) throws ClientNotFoundException {
        Optional<QuizData> quizDataOptional = quizDataRepository.findById(quizData.getClientId());
        if (quizDataOptional.isPresent()) {
            return quizDataRepository.save(quizData);
        } else {
            throw new ClientNotFoundException("QuizData for clientId " + quizData.getClientId() + " is not found");
        }
    }

    private <T> T getOrElse(T nullableObject, @NotNull T notNullableObject) {
        return Objects.requireNonNullElse(nullableObject, notNullableObject);
    }
}
