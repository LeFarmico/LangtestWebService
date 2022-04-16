package com.lefarmico.springjwtwebservice.service;

import com.lefarmico.springjwtwebservice.dto.QuizAnswerDetailsDTO;
import com.lefarmico.springjwtwebservice.entity.QuizData;
import com.lefarmico.springjwtwebservice.entity.Word;
import com.lefarmico.springjwtwebservice.exception.DataNotFoundException;
import com.lefarmico.springjwtwebservice.factory.QuizWordFactory;
import com.lefarmico.springjwtwebservice.entity.QuizWord;
import com.lefarmico.springjwtwebservice.repository.QuizWordRepository;
import com.lefarmico.springjwtwebservice.repository.WordRepository;
import com.lefarmico.springjwtwebservice.utils.ListUtils;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@Component
public class QuizWordService implements IQuizWordService {

    private final Logger log = LoggerFactory.getLogger(QuizWordService.class);

    @Autowired
    QuizWordFactory quizWordFactory;

    @Autowired
    QuizWordRepository quizWordRepository;

    @Autowired
    QuizDataService quizDataService;

    @Autowired
    WordRepository wordRepository;

    @Override
    public List<QuizWord> createQuizForClient(String clientId) throws DataNotFoundException {
        QuizData quizDataDB = checkForClientQuizData(clientId);

        quizDataDB.setCurrentWordNumber((short) 0);
        quizWordRepository.deleteQuizWordsByClientId(clientId);

        List<Word> wordList = wordRepository.getWordsByCategoryId(quizDataDB.getCategoryId());
        List<QuizWord> quizWordList = new ArrayList<>();
        wordList.forEach( word -> {
            List<String> translationsList = wordRepository.getWordsTranslationsExcept(
                    quizDataDB.getCategoryId(),
                    word.getWordTranslation()
            );
            QuizWord quizWord = quizWordFactory.createQuizWord(clientId, word, translationsList);
            quizWordList.add(quizWord);
        });
        return quizWordRepository.saveAll(quizWordList);
    }

    @Override
    public Boolean deleteQuizWordsByClientId(String clientId) {
        return quizWordRepository.deleteQuizWordsByClientId(clientId) > 0;
    }

    @Override
    public Optional<QuizWord> getNextNotAnsweredQuizWord(String clientId) throws DataNotFoundException {
        checkForClientQuizData(clientId);
        List<QuizWord> quizWordListDB = quizWordRepository.getUnansweredQuizWordsByClientId(clientId);
        if (!quizWordListDB.isEmpty()) {

            QuizWord randomQuizWord = ListUtils.getRandomElementFromList(quizWordListDB);
            return Optional.of(randomQuizWord);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<QuizWord> getQuizWordsByClientId(String clientId) throws DataNotFoundException {
        checkForClientQuizData(clientId);
        return quizWordRepository.getQuizWordsByClientId(clientId);
    }

    @Override
    public QuizAnswerDetailsDTO setAnswerForQuizWord(
            String clientId, Long quizWordId, Boolean answer
    ) throws DataNotFoundException {
        QuizData quizDataDB = checkForClientQuizData(clientId);
        QuizWord quizWordDB = checkForQuizWord(clientId, quizWordId);


        QuizAnswerDetailsDTO answerDetailsDTO = QuizAnswerDetailsDTO.builder()
                .wordId(quizWordDB.getId())
                .summaryWordCount(quizDataDB.getWordsInQuiz().longValue())
                .currentWordNumber(quizDataDB.getCurrentWordNumber().longValue())
                .build();

        if (
                answer &&
                quizDataDB.getCurrentWordNumber() < quizDataDB.getWordsInQuiz() &&
                !quizWordDB.getIsAnswered()) {
            Short currentWordNumber = (short) (quizDataDB.getCurrentWordNumber() + (short) 1);
            quizDataDB.setCurrentWordNumber(currentWordNumber);
            answerDetailsDTO.setCurrentWordNumber(currentWordNumber.longValue());
        }
        if (answer && quizDataDB.getCurrentWordNumber().equals(quizDataDB.getWordsInQuiz())) {
            answerDetailsDTO.setNextQuizTime(System.currentTimeMillis() + quizDataDB.getNextQuizTime());
        }
        quizWordDB.setIsAnswered(answer);
        quizWordRepository.save(quizWordDB);
        return answerDetailsDTO;
    }

    @Override
    public Boolean resetQuizWordsForClient(String clientId) throws DataNotFoundException {
        QuizData quizData = checkForClientQuizData(clientId);
        quizData.setCurrentWordNumber((short) 0);
        Integer numberOfChangerRows = quizWordRepository.updateIsAnsweredForClientId(clientId);
        // TODO: Add check for existed quiz words
        return numberOfChangerRows > 0;
    }

    private QuizData checkForClientQuizData(String clientId) throws DataNotFoundException {
        Optional<QuizData> quizDataDBOptional = quizDataService.getQuizDataByClientId(clientId);
        if (quizDataDBOptional.isEmpty()) {
            throw new DataNotFoundException("QuizData for clientId: " + clientId + " is not found.");
        } else {
            return quizDataDBOptional.get();
        }
    }

    private QuizWord checkForQuizWord(String clientId, Long quizWordId) throws DataNotFoundException {
        Optional<QuizWord> quizWordOptional = quizWordRepository.getQuizWordByQuizWordIdAndClientId(clientId, quizWordId);
        if (quizWordOptional.isEmpty()) {
            throw new DataNotFoundException(
                    "QuizWord with id: " + quizWordId + " for clientId: " + clientId + "is not found"
            );
        } else {
            return quizWordOptional.get();
        }
    }
}
