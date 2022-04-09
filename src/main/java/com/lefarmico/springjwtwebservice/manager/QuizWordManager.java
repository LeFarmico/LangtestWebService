package com.lefarmico.springjwtwebservice.manager;

import com.lefarmico.springjwtwebservice.entity.QuizData;
import com.lefarmico.springjwtwebservice.entity.Word;
import com.lefarmico.springjwtwebservice.factory.QuizWordFactory;
import com.lefarmico.springjwtwebservice.entity.QuizWord;
import com.lefarmico.springjwtwebservice.repository.QuizWordRepository;
import com.lefarmico.springjwtwebservice.repository.WordRepository;
import com.lefarmico.springjwtwebservice.service.ClientService;
import com.lefarmico.springjwtwebservice.service.QuizDataService;
import com.lefarmico.springjwtwebservice.utils.ListUtils;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@NoArgsConstructor
@Component
public class QuizWordManager implements IQuizWordManager {

    private final Logger log = LoggerFactory.getLogger(QuizWordManager.class);

    @Autowired
    QuizWordFactory quizWordFactory;

    @Autowired
    ClientService clientService;

    @Autowired
    QuizWordRepository quizWordRepository;

    @Autowired
    QuizDataService quizDataService;

    @Autowired
    WordRepository wordRepository;

    @Override
    public List<QuizWord> createQuizForClient(String clientId) {
        Optional<QuizData> optionalQuizData = quizDataService.getQuizDataByClientId(clientId);
        if (optionalQuizData.isPresent()) {
            QuizData quizData = optionalQuizData.get();
            List<Word> wordList = wordRepository.getWordsByCategoryId(quizData.getCategoryId());
            List<QuizWord> quizWordList = new ArrayList<>();
            wordList.forEach( word -> {
                List<String> translationsList = wordRepository.getWordsTranslationsExcept(
                        quizData.getCategoryId(),
                        word.getWordTranslation()
                );
                QuizWord quizWord = quizWordFactory.createQuizWord(clientId, word, translationsList);
                quizWordList.add(quizWord);
            });
            return quizWordRepository.saveAll(quizWordList);
        } else {
            log.warn("QuizData by id: " + clientId + " is not found!");
            return Collections.emptyList();
        }
    }

    @Override
    public Boolean deleteQuizWordsByClientId(String clientId) {
        return quizWordRepository.deleteQuizWordsByClientId(clientId) > 0;
    }

    @Override
    public Optional<QuizWord> getNextNotAnsweredQuizWord(String clientId) {
        List<QuizWord> quizWordListDB = quizWordRepository.getQuizWordsByClientId(clientId);
        if (!quizWordListDB.isEmpty()) {
            QuizWord randomQuizWord = ListUtils.getRandomElementFromList(quizWordListDB);
            return Optional.of(randomQuizWord);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<QuizWord> setAnswerForQuizWord(String clientId, Long quizWordId, Boolean answer) {
        Optional<QuizWord> quizWordDB = quizWordRepository.getQuizWordByQuizWordIdAndClientId(clientId, quizWordId);
        if (quizWordDB.isPresent()) {
            QuizWord quizWord = quizWordDB.get();
            quizWord.setIsAnswered(answer);
            return Optional.of(quizWordRepository.save(quizWord));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<QuizWord> resetQuizWordsForClient(String clientId) {
        return null;
    }
}
