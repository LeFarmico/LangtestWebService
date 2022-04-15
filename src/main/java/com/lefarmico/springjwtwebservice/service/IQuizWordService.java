package com.lefarmico.springjwtwebservice.service;

import com.lefarmico.springjwtwebservice.entity.QuizWord;
import com.lefarmico.springjwtwebservice.exception.ClientNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IQuizWordService {

    List<QuizWord> createQuizForClient(String clientId) throws ClientNotFoundException;

    Boolean deleteQuizWordsByClientId(String clientId);

    Optional<QuizWord> getNextNotAnsweredQuizWord(String clientId) throws ClientNotFoundException;

    List<QuizWord> getQuizWordsByClientId(String clientId) throws ClientNotFoundException;

    /**
     *
     * @param clientId
     * @param quizWordId
     * @param answer
     * @return must be empty if next quiz is not scheduled, also must be next quiz time in millis if scheduled
     */
    Optional<Integer> setAnswerForQuizWord(
            String clientId,
            Long quizWordId,
            Boolean answer
    ) throws ClientNotFoundException;

    Boolean resetQuizWordsForClient(String clientId) throws ClientNotFoundException;
}
