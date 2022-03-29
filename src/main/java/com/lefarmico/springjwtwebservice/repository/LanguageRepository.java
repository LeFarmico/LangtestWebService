package com.lefarmico.springjwtwebservice.repository;

import com.lefarmico.springjwtwebservice.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
