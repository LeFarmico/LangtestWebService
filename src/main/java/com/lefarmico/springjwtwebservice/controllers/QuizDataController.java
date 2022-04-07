package com.lefarmico.springjwtwebservice.controllers;

import com.lefarmico.springjwtwebservice.entity.QuizData;
import com.lefarmico.springjwtwebservice.service.QuizDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/quiz", produces = APPLICATION_JSON_VALUE)
public class QuizDataController {

    @Autowired
    QuizDataService quizDataService;

    @PostMapping(value = "/quizData/create", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizData> addQuizDataForClient(
            @RequestParam("client_id") String clientId,
            @RequestParam("words_in_quiz") Short wordsInQuiz,
            @RequestParam("next_quiz_time") Long nextQuizTime,
            @RequestParam("language_id") Long languageId,
            @RequestParam("category_id") Long categoryId
//            @RequestParam(value = "status", required = false) String status,
//            @RequestParam(value = "current_word_number", required = false) Long currentWordNumber
    ) {
        try {
            if (quizDataService.getQuizDataByClientId(clientId).isPresent()) {
                return ResponseEntity.unprocessableEntity().build();
            }
            QuizData quizDataDB =
                    quizDataService.createQuizDataForClient(
                            clientId,
                            wordsInQuiz,
                            nextQuizTime,
                            languageId,
                            categoryId
                    );
            return ResponseEntity.of(Optional.of(quizDataDB));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/quizData")
    public ResponseEntity<QuizData> getQuizDataByClientId(@RequestParam("client_id") String clientId) {
        try {
            Optional<QuizData> optionalQuizData = quizDataService.getQuizDataByClientId(clientId);
            if (optionalQuizData.isPresent()) {
                return ResponseEntity.of(optionalQuizData);
            } else  {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
