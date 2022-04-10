package com.lefarmico.springjwtwebservice.service;

import com.lefarmico.springjwtwebservice.entity.QuizWord;

import java.util.List;
import java.util.Optional;

public interface IQuizWordService {

    List<QuizWord> createQuizForClient(String clientId);

    Boolean deleteQuizWordsByClientId(String clientId);

    Optional<QuizWord> getNextNotAnsweredQuizWord(String clientId);

    List<QuizWord> getQuizWordsByClientId(String clientId);

    Optional<QuizWord> setAnswerForQuizWord(
            String clientId,
            Long quizWordId,
            Boolean answer
    );

    Boolean resetQuizWordsForClient(String clientId);
}
