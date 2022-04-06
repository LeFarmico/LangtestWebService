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

    @PostMapping(value = "/createQuizWords", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuizWord>> createQuizForClient(@RequestParam("client_id") String clientId) {
        try {
            List<QuizWord> quizWordList = quizWordManager.createQuizForClient(clientId);
            return ResponseEntity.of(Optional.of(quizWordList));
        } catch (Exception e) {
            log.error("Error in createQuizForClient function", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
