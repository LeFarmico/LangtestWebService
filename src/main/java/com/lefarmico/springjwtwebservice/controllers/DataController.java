package com.lefarmico.springjwtwebservice.controllers;

import com.lefarmico.springjwtwebservice.entity.Category;
import com.lefarmico.springjwtwebservice.entity.Language;
import com.lefarmico.springjwtwebservice.entity.Word;
import com.lefarmico.springjwtwebservice.repository.CategoryRepository;
import com.lefarmico.springjwtwebservice.repository.LanguageRepository;
import com.lefarmico.springjwtwebservice.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/word")
    public List<Word> getAllWords() {
        return wordRepository.findAll();
    }

    @GetMapping("/language")
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    @GetMapping("/language/{language_id}/category")
    public ResponseEntity<List<Category>> getCategoryByLanguageId(@PathVariable("language_id") Long languageId) {
        try {
            List<Category> categoryListDB = categoryRepository.findByLanguageId(languageId);
            if (!categoryListDB.isEmpty()) {
                return ResponseEntity.ok(categoryListDB);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/language/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categoryListDB = categoryRepository.findAll();
            if (!categoryListDB.isEmpty()) {
                return ResponseEntity.ok(categoryListDB);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
