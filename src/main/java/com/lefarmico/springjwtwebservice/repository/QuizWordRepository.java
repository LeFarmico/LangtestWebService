package com.lefarmico.springjwtwebservice.repository;

import com.lefarmico.springjwtwebservice.entity.QuizWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizWordRepository extends JpaRepository<QuizWord, Long> {

    // TOD add all queries
}
