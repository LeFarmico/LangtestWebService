package com.lefarmico.springjwtwebservice.manager;

import com.lefarmico.springjwtwebservice.entity.Quiz;
import com.lefarmico.springjwtwebservice.entity.Word;
import com.lefarmico.springjwtwebservice.factory.QuizWordFactory;
import com.lefarmico.springjwtwebservice.entity.Client;
import com.lefarmico.springjwtwebservice.entity.QuizWord;
import com.lefarmico.springjwtwebservice.repository.QuizRepository;
import com.lefarmico.springjwtwebservice.repository.QuizWordRepository;
import com.lefarmico.springjwtwebservice.repository.WordRepository;
import com.lefarmico.springjwtwebservice.service.ClientService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@NoArgsConstructor
@Component
public class QuizWordManager {

    private final Logger log = LoggerFactory.getLogger(QuizWordManager.class);

    @Autowired
    QuizWordFactory quizWordFactory;

    @Autowired
    ClientService clientService;

    @Autowired
    QuizWordRepository quizWordRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    WordRepository wordRepository;

    public List<QuizWord> createQuizForClient(String clientId) {

        // TODO register client first
//        Optional<Client> clientDB = clientService.getClientByClientId(clientId);
        Optional<Client> clientDB = Optional.of(
                Client.builder()
                        .clientId(clientId)
                        .languageId(1L)
                        .categoryId(1L)
                        .nextQuizTime(1000L)
                        .wordsInTest(5L)
                        .build()
        );
        if (clientDB.isPresent()) {
            // TODO do it by using service
            quizRepository.save(
                    Quiz.builder()
                            .status("default")
                            .clientId(clientId)
                            .currentSequenceNumber(0L)
                            .build()
            );
            List<Word> wordList = wordRepository.getWordsByCategoryId(clientDB.get().getCategoryId());
            List<QuizWord> quizWordList = new ArrayList<>();
            wordList.forEach( word -> {
                List<String> translationsList = wordRepository.getWordsTranslationsExcept(
                        clientDB.get().getCategoryId(),
                        word.getWordTranslation()
                );
                QuizWord quizWord = quizWordFactory.createQuizWord(clientId, word, translationsList);
                quizWordList.add(quizWord);
            });
            return quizWordRepository.saveAll(quizWordList);
        } else {
            log.warn("User by id: " + clientId + " is not found!");
            return Collections.emptyList();
        }
    }
}
