package com.lefarmico.springjwtwebservice.service;

import com.lefarmico.springjwtwebservice.dto.QuizAnswerDetailsDTO;
import com.lefarmico.springjwtwebservice.entity.QuizWord;
import com.lefarmico.springjwtwebservice.exception.DataNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IQuizWordService {

    List<QuizWord> createQuizForClient(String clientId) throws DataNotFoundException;

    Boolean deleteQuizWordsByClientId(String clientId);

    Optional<QuizWord> getNextNotAnsweredQuizWord(String clientId) throws DataNotFoundException;

    List<QuizWord> getQuizWordsByClientId(String clientId) throws DataNotFoundException;

    /**
     *
     * @param clientId
     * @param quizWordId
     * @param answer
     * @return must be empty if next quiz is not scheduled, also must be next quiz time in millis if scheduled
     */
    QuizAnswerDetailsDTO setAnswerForQuizWord(
            String clientId,
            Long quizWordId,
            Boolean answer
    ) throws DataNotFoundException;

    Boolean resetQuizWordsForClient(String clientId) throws DataNotFoundException;
}
