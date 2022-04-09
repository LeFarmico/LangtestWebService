package com.lefarmico.springjwtwebservice.manager;

import com.lefarmico.springjwtwebservice.entity.QuizWord;

import java.util.List;
import java.util.Optional;

public interface IQuizWordManager {

    List<QuizWord> createQuizForClient(String clientId);

    Boolean deleteQuizWordsByClientId(String clientId);

    Optional<QuizWord> getNextNotAnsweredQuizWord(String clientId);

    Optional<QuizWord> setAnswerForQuizWord(
            String clientId,
            Long quizWordId,
            Boolean answer
    );

    List<QuizWord> resetQuizWordsForClient(String clientId);
}
