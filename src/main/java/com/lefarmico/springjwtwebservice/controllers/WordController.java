package com.lefarmico.springjwtwebservice.controllers;

import com.lefarmico.springjwtwebservice.entity.Word;
import com.lefarmico.springjwtwebservice.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/word")
public class WordController {

    @Autowired
    private WordRepository wordRepository;

    @GetMapping("/")
    public List<Word> getAllWords() {
        return wordRepository.findAll();
    }
}
