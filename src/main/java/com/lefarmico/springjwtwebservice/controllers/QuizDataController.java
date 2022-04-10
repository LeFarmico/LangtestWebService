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

    @PostMapping(value = "/create", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizData> addQuizDataForClient(
            @RequestParam("client_id") String clientId,
            @RequestParam("words_in_quiz") Short wordsInQuiz,
            @RequestParam("next_quiz_time") Long nextQuizTime,
            @RequestParam("language_id") Long languageId,
            @RequestParam("category_id") Long categoryId
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

    @GetMapping(value = "/{client_id}")
    public ResponseEntity<QuizData> getQuizDataByClientId(@PathVariable("client_id") String clientId) {
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

    @DeleteMapping(value = "/{client_id}/delete")
    public ResponseEntity<Object> deleteQuizDataByClientId(@PathVariable("client_id") String clientId) {
        try {
            Boolean deleteState = quizDataService.deleteQuizDataForClient(clientId);
            if (deleteState) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{client_id}/update")
    public ResponseEntity<QuizData> updateQuizDataByClientId(
            @PathVariable("client_id") String clientId,
            @RequestParam(value = "words_in_quiz", required = false) Short wordsInQuiz,
            @RequestParam(value = "next_quiz_time", required = false) Long nextQuizTime,
            @RequestParam(value = "language_id", required = false) Long languageId,
            @RequestParam(value = "category_id", required = false) Long categoryId
    ) {
        try {
            Optional<QuizData> updatedQuizData = quizDataService.updateAndResetQuizDataForClient(
                    clientId, wordsInQuiz, nextQuizTime, languageId, categoryId);
            return updatedQuizData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
