package com.lefarmico.springjwtwebservice.service;

import com.lefarmico.springjwtwebservice.dto.QuizAnswerDetailsDTO;
import com.lefarmico.springjwtwebservice.entity.*;
import com.lefarmico.springjwtwebservice.exception.DataNotFoundException;
import com.lefarmico.springjwtwebservice.factory.QuizWordFactory;
import com.lefarmico.springjwtwebservice.repository.CategoryRepository;
import com.lefarmico.springjwtwebservice.repository.LanguageRepository;
import com.lefarmico.springjwtwebservice.repository.QuizWordRepository;
import com.lefarmico.springjwtwebservice.repository.WordRepository;
import com.lefarmico.springjwtwebservice.utils.ListUtils;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<QuizWord> createQuizForClient(Long chatId) throws DataNotFoundException {
        QuizData quizDataDB = checkForClientQuizData(chatId);

        quizDataDB.setCurrentWordNumber((short) 0);
        quizWordRepository.deleteQuizWordsByChatId(chatId);

        List<Word> wordList = wordRepository.getWordsByCategoryId(quizDataDB.getCategoryId());
        List<QuizWord> quizWordList = new ArrayList<>();
        wordList.forEach( word -> {
            List<String> translationsList = wordRepository.getWordsTranslationsExcept(
                    quizDataDB.getCategoryId(),
                    word.getWordTranslation()
            );
            QuizWord quizWord = quizWordFactory.createQuizWord(chatId, word, translationsList);
            quizWordList.add(quizWord);
        });
        return quizWordRepository.saveAll(quizWordList);
    }

    @Override
    public Boolean deleteQuizWordsByClientId(Long chatId) {
        return quizWordRepository.deleteQuizWordsByChatId(chatId) > 0;
    }

    @Override
    public Optional<QuizWord> getNextNotAnsweredQuizWord(Long chatId) throws DataNotFoundException {
        checkForClientQuizData(chatId);
        List<QuizWord> quizWordListDB = quizWordRepository.getUnansweredQuizWordsByChatId(chatId);
        if (!quizWordListDB.isEmpty()) {

            QuizWord randomQuizWord = ListUtils.getRandomElementFromList(quizWordListDB);
            return Optional.of(randomQuizWord);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<QuizWord> getQuizWordsByClientId(Long chatId) throws DataNotFoundException {
        checkForClientQuizData(chatId);
        return quizWordRepository.getQuizWordsByChatId(chatId);
    }

    @Override
    public QuizAnswerDetailsDTO setAnswerForQuizWord(
            Long chatId, Long quizWordId, Boolean answer
    ) throws DataNotFoundException {
        QuizData quizDataDB = checkForClientQuizData(chatId);
        QuizWord quizWordDB = checkForQuizWord(chatId, quizWordId);


        QuizAnswerDetailsDTO answerDetailsDTO = QuizAnswerDetailsDTO.builder()
                .wordId(quizWordDB.getId())
                .summaryWordCount(quizDataDB.getWordsInQuiz())
                .currentWordNumber(quizDataDB.getCurrentWordNumber())
                .build();

        if (
            answer &&
            quizDataDB.getCurrentWordNumber() < quizDataDB.getWordsInQuiz() &&
            !quizWordDB.getIsAnswered()) {
            int currentWordNumber = (quizDataDB.getCurrentWordNumber() + 1);
            quizDataDB.setCurrentWordNumber(currentWordNumber);
            answerDetailsDTO.setCurrentWordNumber(currentWordNumber);
        }
        if (answer && quizDataDB.getCurrentWordNumber() == quizDataDB.getWordsInQuiz()) {
            answerDetailsDTO.setNextQuizTime(System.currentTimeMillis() + quizDataDB.getBreakTimeInMillis());
        }
        quizWordDB.setIsAnswered(answer);
        quizWordRepository.save(quizWordDB);
        return answerDetailsDTO;
    }

    @Override
    public Boolean resetQuizWordsForClient(Long chatId) throws DataNotFoundException {
        QuizData quizData = checkForClientQuizData(chatId);
        quizData.setCurrentWordNumber((short) 0);
        Integer numberOfChangerRows = quizWordRepository.updateIsAnsweredForClientId(chatId);
        // TODO: Add check for existed quiz words
        return numberOfChangerRows > 0;
    }

    @Override
    public Boolean resetQuizWordNumberForClient(Long chatId) throws DataNotFoundException {
        QuizData quizData = checkForClientQuizData(chatId);
        quizData.setCurrentWordNumber((short) 0);
        QuizData updatedData = quizDataService.updateQuizData(quizData);
        return updatedData != null;
    }

    private QuizData checkForClientQuizData(Long chatId) throws DataNotFoundException {
        Optional<QuizData> quizDataDBOptional = quizDataService.getQuizDataByClientId(chatId);
        if (quizDataDBOptional.isEmpty()) {
            throw new DataNotFoundException("QuizData for chatId: " + chatId + " is not found.");
        } else {
            return quizDataDBOptional.get();
        }
    }

    private QuizWord checkForQuizWord(Long chatId, Long quizWordId) throws DataNotFoundException {
        Optional<QuizWord> quizWordOptional = quizWordRepository.getQuizWordByQuizWordIdAndChatId(chatId, quizWordId);
        if (quizWordOptional.isEmpty()) {
            throw new DataNotFoundException(
                    "QuizWord with id: " + quizWordId + " for chatId: " + chatId + "is not found"
            );
        } else {
            return quizWordOptional.get();
        }
    }
}
