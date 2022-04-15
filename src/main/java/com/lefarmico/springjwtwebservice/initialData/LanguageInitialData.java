package com.lefarmico.springjwtwebservice.initialData;

import com.lefarmico.springjwtwebservice.entity.Language;
import com.lefarmico.springjwtwebservice.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LanguageInitialData {

    @Autowired
    LanguageRepository languageRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        List<Language> langList = new ArrayList<>();
        Language language1 = Language.builder()
                .id(1L)
                .languageName("English")
                .build();
        Language language2 = Language.builder()
                .id(2L)
                .languageName("Spanish")
                .build();
        langList.add(language1);
        langList.add(language2);
        languageRepository.saveAll(langList);
    }
}
