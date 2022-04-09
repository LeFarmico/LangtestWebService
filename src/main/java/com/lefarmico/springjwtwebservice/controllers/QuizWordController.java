package com.lefarmico.springjwtwebservice.controllers;

import com.lefarmico.springjwtwebservice.entity.QuizWord;
import com.lefarmico.springjwtwebservice.manager.QuizWordManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/quiz", produces = APPLICATION_JSON_VALUE)
public class QuizWordController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    QuizWordManager quizWordManager;

    @PostMapping(value = "/{client_id}/createQuizWords", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuizWord>> createQuizForClient(@PathVariable("client_id") String clientId) {
        try {
            List<QuizWord> quizWordList = quizWordManager.createQuizForClient(clientId);
            return ResponseEntity.of(Optional.of(quizWordList));
        } catch (Exception e) {
            log.error("Error in createQuizForClient function", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{client_id}/{quiz_word_id}")
    public ResponseEntity<QuizWord> setAnswerForQuizWord(
            @PathVariable("client_id") String clientId,
            @PathVariable("quiz_word_id") Long quizWordId,
            @RequestParam("answer") Boolean answer
    ) {
        try {
            Optional<QuizWord> updatedQuizWordOptional =
                    quizWordManager.setAnswerForQuizWord(clientId, quizWordId, answer);
            if (updatedQuizWordOptional.isPresent()) {
                return ResponseEntity.of(updatedQuizWordOptional);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
