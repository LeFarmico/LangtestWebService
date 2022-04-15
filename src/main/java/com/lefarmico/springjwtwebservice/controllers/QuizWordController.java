package com.lefarmico.springjwtwebservice.controllers;

import com.lefarmico.springjwtwebservice.entity.QuizWord;
import com.lefarmico.springjwtwebservice.exception.ClientNotFoundException;
import com.lefarmico.springjwtwebservice.service.QuizWordService;
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
    QuizWordService quizWordService;

    @PostMapping(value = "/{client_id}/createQuizWords", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuizWord>> createQuizForClient(
            @PathVariable("client_id") String clientId
    ) {
        try {
            List<QuizWord> quizWordList = quizWordService.createQuizForClient(clientId);
            if (!quizWordList.isEmpty()) {
                return ResponseEntity.of(Optional.of(quizWordList));
            } else {
                return ResponseEntity.noContent().build();
            }

        } catch (ClientNotFoundException e) {
            log.error("Error in createQuizForClient function", e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{client_id}/quiz_word/{quiz_word_id}")
    public ResponseEntity<Integer> setAnswerForQuizWord(
            @PathVariable("client_id") String clientId,
            @PathVariable("quiz_word_id") Long quizWordId,
            @RequestParam("answer") Boolean answer
    ) {
        try {
            Optional<Integer> updatedQuizWordOptional =
                    quizWordService.setAnswerForQuizWord(clientId, quizWordId, answer);
            if (updatedQuizWordOptional.isPresent()) {
                return ResponseEntity.of(updatedQuizWordOptional);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ClientNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping(value = "/{client_id}/resetQuiz")
    public ResponseEntity<Boolean> resetQuizWords(
            @PathVariable("client_id") String clientId
    ) {
        try {
            Boolean isReseted = quizWordService.resetQuizWordsForClient(clientId);
            if (isReseted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (ClientNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{client_id}/quiz_word/next")
    public ResponseEntity<QuizWord> getNextQuizWord(
            @PathVariable("client_id") String clientId
    ) {
        try {
            Optional<QuizWord> quizWordOptional = quizWordService.getNextNotAnsweredQuizWord(clientId);
            return quizWordOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
        } catch (ClientNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{client_id}/quiz_word")
    public ResponseEntity<List<QuizWord>> getQuizWords(
            @PathVariable("client_id") String clientId
    ) {
        try {
            List<QuizWord> quizWordListDB = quizWordService.getQuizWordsByClientId(clientId);
            if (!quizWordListDB.isEmpty()) {
                return ResponseEntity.ok(quizWordListDB);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (ClientNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{client_id}/quiz_word/delete")
    public ResponseEntity<Object> deleteQuizWord(
            @PathVariable("client_id") String clientId
    ) {
        try {
            Boolean isDeleted = quizWordService.deleteQuizWordsByClientId(clientId);
            if (isDeleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
