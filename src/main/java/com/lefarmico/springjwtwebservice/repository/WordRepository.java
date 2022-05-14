package com.lefarmico.springjwtwebservice.repository;

import com.lefarmico.springjwtwebservice.entity.FileWord;
import com.lefarmico.springjwtwebservice.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface WordRepository extends JpaRepository<Word, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM word WHERE category_id = :categoryId"
    )
    public List<Word> getWordsByCategoryId(@Param("categoryId") Long categoryId);

    @Query(
            nativeQuery = true,
            value = "SELECT word_translation FROM word " +
                    "WHERE category_id = :categoryId " +
                    "AND word_translation <> :exceptTranslation"
    )
    public List<String> getWordsTranslationsExcept(
            @Param("categoryId") Long categoryId,
            @Param("exceptTranslation") String exceptWordTranslation);
}
