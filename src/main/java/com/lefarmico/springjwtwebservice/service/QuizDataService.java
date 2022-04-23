package com.lefarmico.springjwtwebservice.service;

import com.lefarmico.springjwtwebservice.entity.QuizData;
import com.lefarmico.springjwtwebservice.exception.DataNotFoundException;
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
            Long chatId, int wordsInQuiz, Long breakTimeInMillis,
            Long languageId, Long categoryId
    ) {
        QuizData quizData = QuizData.builder()
                .chatId(chatId)
                .wordsInQuiz(wordsInQuiz)
                .breakTimeInMillis(breakTimeInMillis)
                .languageId(languageId)
                .categoryId(categoryId)
                .build();
        return quizDataRepository.save(quizData);
    }

    public QuizData updateAndResetQuizDataForClient(
            Long chatId, int wordsInQuiz, Long nextQuizTime,
            Long languageId, Long categoryId
    ) throws DataNotFoundException {
        Optional<QuizData> optionalQuizDataDB = quizDataRepository.findById(chatId);
        if (optionalQuizDataDB.isPresent()) {
            QuizData quizDataDB = optionalQuizDataDB.get();
            Long updatedNextQuizTime = getOrElse(nextQuizTime, quizDataDB.getBreakTimeInMillis());
            Long updatedLanguageId = getOrElse(languageId, quizDataDB.getLanguageId());
            Long updatedCategoryId = getOrElse(categoryId, quizDataDB.getCategoryId());
            int updatedWordsInQuiz = getOrElse(wordsInQuiz, quizDataDB.getWordsInQuiz());

            QuizData quizDataForUpdate = QuizData.builder()
                    .chatId(quizDataDB.getChatId())
                    .breakTimeInMillis(updatedNextQuizTime)
                    .languageId(updatedLanguageId)
                    .categoryId(updatedCategoryId)
                    .wordsInQuiz(updatedWordsInQuiz)
                    .status(QuizData.Status.DEFAULT.name())
                    .currentWordNumber((short) 0)
                    .build();

            QuizData updatedQuizDataDB = quizDataRepository.save(quizDataForUpdate);

            // TODO: зупустить в отдельном потоке?
            if (languageId != null || categoryId != null) {
                quizWordRepository.deleteQuizWordsByChatId(chatId);
            }
            return updatedQuizDataDB;

        } else {
            throw new DataNotFoundException("QuizData for chatId " + chatId + " is not found");
        }
    }

    public Optional<QuizData> getQuizDataByClientId(Long chatId) {
        return quizDataRepository.findById(chatId);
    }

    public Boolean deleteQuizDataForClient(Long chatId) {
        int deletedId = quizDataRepository.deleteQuizDataByClientId(chatId);
        return deletedId > 0;
    }

    public QuizData updateQuizData(QuizData quizData) throws DataNotFoundException {
        Optional<QuizData> quizDataOptional = quizDataRepository.findById(quizData.getChatId());
        if (quizDataOptional.isPresent()) {
            return quizDataRepository.save(quizData);
        } else {
            throw new DataNotFoundException("QuizData for chatId " + quizData.getChatId() + " is not found");
        }
    }

    private <T> T getOrElse(T nullableObject, @NotNull T notNullableObject) {
        return Objects.requireNonNullElse(nullableObject, notNullableObject);
    }
}
