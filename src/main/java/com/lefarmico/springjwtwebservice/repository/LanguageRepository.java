package com.lefarmico.springjwtwebservice.repository;

import com.lefarmico.springjwtwebservice.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LanguageRepository extends JpaRepository<Language, Long> {
}
