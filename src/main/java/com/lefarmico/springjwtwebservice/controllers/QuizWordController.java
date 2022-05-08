package com.lefarmico.springjwtwebservice.controllers;

import com.lefarmico.springjwtwebservice.dto.QuizAnswerDetailsDTO;
import com.lefarmico.springjwtwebservice.entity.QuizStats;
import com.lefarmico.springjwtwebservice.entity.QuizWord;
import com.lefarmico.springjwtwebservice.exception.DataNotFoundException;
import com.lefarmico.springjwtwebservice.service.QuizWordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/quiz", produces = APPLICATION_JSON_VALUE)
public class QuizWordController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    QuizWordService quizWordService;

    @PostMapping(value = "/{chat_id}/createQuizWords", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuizWord>> createQuizForChat(
            @PathVariable("chat_id") Long chatId
    ) {
        try {
            List<QuizWord> quizWordList = quizWordService.createQuizForClient(chatId);
            if (!quizWordList.isEmpty()) {
                return ResponseEntity.of(Optional.of(quizWordList));
            } else {
                return ResponseEntity.noContent().build();
            }

        } catch (DataNotFoundException e) {
            log.error("Error in createQuizForClient function", e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{chat_id}/quiz_word/{quiz_word_id}")
    public ResponseEntity<Map<String, Object>> setAnswerForQuizWord(
            @PathVariable("chat_id") Long chatId,
            @PathVariable("quiz_word_id") Long quizWordId,
            @RequestParam("answer") Boolean answer
    ) {
        Map<String, Object> responseMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> response;
        try {
            QuizAnswerDetailsDTO answerDetailsDTO =
                    quizWordService.setAnswerForQuizWord(chatId, quizWordId, answer);

            responseMap.put("quiz_word_id", answerDetailsDTO.getWordId());
            responseMap.put("current_word_number", answerDetailsDTO.getCurrentWordNumber());
            responseMap.put("summary_word_count", answerDetailsDTO.getSummaryWordCount());
            if (answerDetailsDTO.getNextQuizTime() != null) {
                responseMap.put("next_quiz_time", answerDetailsDTO.getNextQuizTime());
            }
            response = ResponseEntity.ok(responseMap);

        } catch (DataNotFoundException e) {
            responseMap.put("error", "data is not found");

            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
        } catch (Exception e) {
            responseMap.put("error", "internal server error");
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
        }
        return response;
    }
    
    @PutMapping(value = "/{chat_id}/resetQuiz")
    public ResponseEntity<String> resetQuizWords(
            @PathVariable("chat_id") Long chatId
    ) {
        try {
            Boolean isReseted = quizWordService.resetQuizWordsForClient(chatId);
            if (isReseted) {
                return ResponseEntity.ok("true");
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{chat_id}/quiz_word/next")
    public ResponseEntity<QuizWord> getNextQuizWord(
            @PathVariable("chat_id") Long chatId
    ) {
        try {
            Optional<QuizWord> quizWordOptional = quizWordService.getNextNotAnsweredQuizWord(chatId);
            return quizWordOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{chat_id}/quiz_word")
    public ResponseEntity<List<QuizWord>> getQuizWords(
            @PathVariable("chat_id") Long chatId
    ) {
        try {
            List<QuizWord> quizWordListDB = quizWordService.getQuizWordsByClientId(chatId);
            if (!quizWordListDB.isEmpty()) {
                return ResponseEntity.ok(quizWordListDB);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{chat_id}/quiz_word/delete")
    public ResponseEntity<Object> deleteQuizWord(
            @PathVariable("chat_id") Long chatId
    ) {
        try {
            Boolean isDeleted = quizWordService.deleteQuizWordsByClientId(chatId);
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
