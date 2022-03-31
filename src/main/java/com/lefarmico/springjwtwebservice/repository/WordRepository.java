package com.lefarmico.springjwtwebservice.repository;

import com.lefarmico.springjwtwebservice.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM word WHERE word.categoryId=?1"
    )
    public List<Word> getWordsByCategoryId(Long categoryId);

//    public void updateWord(Word word);

}
