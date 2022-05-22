package com.lefarmico.springjwtwebservice.controllers;

import com.lefarmico.springjwtwebservice.entity.QuizData;
import com.lefarmico.springjwtwebservice.exception.DataNotFoundException;
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
            @RequestParam("chat_id") Long chatId,
            @RequestParam("words_in_quiz") int wordsInQuiz,
            @RequestParam("break_time_in_millis") Long breakTimeInMillis,
            @RequestParam("language_id") Long languageId,
            @RequestParam("category_id") Long categoryId
    ) {
        try {
            QuizData quizDataDB =
                    quizDataService.createQuizDataForClient(
                            chatId,
                            wordsInQuiz,
                            breakTimeInMillis,
                            languageId,
                            categoryId
                    );
            return ResponseEntity.of(Optional.of(quizDataDB));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{chat_id}")
    public ResponseEntity<QuizData> getQuizDataByClientId(@PathVariable("chat_id") Long chatId) {
        try {
            Optional<QuizData> optionalQuizData = quizDataService.getQuizDataByClientId(chatId);
            if (optionalQuizData.isPresent()) {
                return ResponseEntity.of(optionalQuizData);
            } else  {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // +
    @DeleteMapping(value = "/{chat_id}/delete")
    public ResponseEntity<String> deleteQuizDataByClientId(@PathVariable("chat_id") Long chatId) {
        try {
            Boolean deleteState = quizDataService.deleteQuizDataForClient(chatId);
            if (deleteState) {
                return ResponseEntity.ok("true");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{chat_id}/update")
    public ResponseEntity<QuizData> updateQuizDataByClientId(
            @PathVariable("chat_id") Long chatId,
            @RequestParam(value = "words_in_quiz", required = false) Integer wordsInQuiz,
            @RequestParam(value = "break_time_in_millis", required = false) Long nextQuizTime,
            @RequestParam(value = "language_id", required = false) Long languageId,
            @RequestParam(value = "category_id", required = false) Long categoryId
    ) {
        try {
            QuizData updatedQuizData = quizDataService.updateAndResetQuizDataForClient(
                    chatId, wordsInQuiz, nextQuizTime, languageId, categoryId);
            return ResponseEntity.ok(updatedQuizData);
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
