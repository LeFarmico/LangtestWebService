package com.lefarmico.springjwtwebservice.initialData;

import com.lefarmico.springjwtwebservice.entity.*;
import com.lefarmico.springjwtwebservice.repository.CategoryRepository;
import com.lefarmico.springjwtwebservice.repository.LanguageRepository;
import com.lefarmico.springjwtwebservice.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.lefarmico.springjwtwebservice.entity.JsonDictionaryEntityKt.getDictionaryFromJson;

@Component
public class LanguageInitialData {

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    WordRepository wordRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        Dictionary dictionarySpanish = getDictionaryFromJson(
                "C:\\Users\\Flyin\\IdeaProjects\\" +
                        "spring-jwt-web-service\\src\\main\\resources\\" +
                        "spanish_dictionary.json"
        );
        Dictionary dictionaryEnglish = getDictionaryFromJson(
                "C:\\Users\\Flyin\\IdeaProjects\\" +
                        "spring-jwt-web-service\\src\\main\\resources\\" +
                        "english_dictionary.json"
        );

        saveDictionary(dictionarySpanish);
        saveDictionary(dictionaryEnglish);

    }

    private void saveDictionary(Dictionary dictionary) {
        Language language = Language.builder()
                .languageName(dictionary.getLanguageName())
                .build();
        Language savedLanguage = languageRepository.save(language);
        List<FileCategory> fileCategoryList = dictionary.getFileCategoryList();
        for (FileCategory fileCategory : fileCategoryList) {
            Category category = Category.builder()
                    .categoryName(fileCategory.getCategoryName())
                    .languageId(savedLanguage.getId())
                    .immutable(true)
                    .build();
            Category savedCategory = categoryRepository.save(category);
            List<Word> wordsList = new ArrayList<>();
            for (FileWord fileWord: fileCategory.getWordsList()) {
                Word word = Word.builder()
                        .categoryId(savedCategory.getId())
                        .languageId(savedLanguage.getId())
                        .wordOriginal(fileWord.getWordOriginal())
                        .wordTranslation(fileWord.getWordTranslation())
                        .build();
                wordsList.add(word);
            }
            wordRepository.saveAll(wordsList);
        }
    }
}
