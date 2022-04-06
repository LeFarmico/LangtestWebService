package com.lefarmico.springjwtwebservice.service;

import com.lefarmico.springjwtwebservice.entity.QuizData;
import com.lefarmico.springjwtwebservice.repository.QuizDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Boolean deleteQuizDataForClient(String clientId) {
        int deletedId = quizDataRepository.deleteQuizDataByClientId(clientId);
        return deletedId > 0;
    }
}
