package com.lefarmico.springjwtwebservice.controllers;

import com.lefarmico.springjwtwebservice.entity.QuizData;
import com.lefarmico.springjwtwebservice.service.QuizDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/quiz", produces = APPLICATION_JSON_VALUE)
public class QuizDataController {

    @Autowired
    QuizDataService quizDataService;

    @PostMapping(value = "/quizData", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizData> addQuizDataForClient(
            @RequestParam("client_id") String clientId, @RequestParam("words_in_quiz") Short wordsInQuiz,
            @RequestParam("next_quiz_time") Long nextQuizTime, @RequestParam("language_id") Long languageId,
            @RequestParam("category_id") Long categoryId
    ) {
        try {
            QuizData quizDataDB =
                    quizDataService.createQuizDataForClient(clientId, wordsInQuiz, nextQuizTime, languageId, categoryId);

            return ResponseEntity.of(Optional.of(quizDataDB));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
