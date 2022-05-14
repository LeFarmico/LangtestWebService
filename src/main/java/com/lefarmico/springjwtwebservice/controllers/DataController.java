package com.lefarmico.springjwtwebservice.controllers;

import com.lefarmico.springjwtwebservice.entity.Category;
import com.lefarmico.springjwtwebservice.entity.Language;
import com.lefarmico.springjwtwebservice.entity.FileWord;
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
import java.util.Optional;

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
    public ResponseEntity<List<Category>> getCategoryListByLanguageId(@PathVariable("language_id") Long languageId) {
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

    @GetMapping("/language/{language_id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable("language_id") Long languageId) {
        try {
            Optional<Language> optionalLanguageDB = languageRepository.findById(languageId);
            if (optionalLanguageDB.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(optionalLanguageDB.get());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
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
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/language/category/{category_id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("category_id") Long categoryId) {
        try {
            Optional<Category> optionalCategoryDB = categoryRepository.findById(categoryId);
            if (optionalCategoryDB.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(optionalCategoryDB.get());
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
