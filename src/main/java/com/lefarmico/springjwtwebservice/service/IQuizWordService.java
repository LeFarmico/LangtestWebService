package com.lefarmico.springjwtwebservice.service;

import com.lefarmico.springjwtwebservice.dto.QuizAnswerDetailsDTO;
import com.lefarmico.springjwtwebservice.entity.QuizStats;
import com.lefarmico.springjwtwebservice.entity.QuizWord;
import com.lefarmico.springjwtwebservice.exception.DataNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IQuizWordService {

    List<QuizWord> createQuizForClient(Long chatId) throws DataNotFoundException;

    Boolean deleteQuizWordsByClientId(Long chatId);

    Optional<QuizWord> getNextNotAnsweredQuizWord(Long chatId) throws DataNotFoundException;

    List<QuizWord> getQuizWordsByClientId(Long chatId) throws DataNotFoundException;

    /**
     *
     * @return must be empty if next quiz is not scheduled, also must be next quiz time in millis if scheduled
     */
    QuizAnswerDetailsDTO setAnswerForQuizWord(
            Long chatId,
            Long quizWordId,
            Boolean answer
    ) throws DataNotFoundException;

    Boolean resetQuizWordsForClient(Long chatId) throws DataNotFoundException;

    Boolean resetQuizWordNumberForClient(Long chatId) throws DataNotFoundException;
}
