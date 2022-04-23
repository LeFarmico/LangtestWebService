package com.lefarmico.springjwtwebservice.repository;

import com.lefarmico.springjwtwebservice.entity.QuizData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface QuizDataRepository extends JpaRepository<QuizData, Long> {

    @Query(
            nativeQuery = true,
            value = "DELETE FROM quiz_data WHERE chat_id = :chatId"
    )
    @Modifying
    public int deleteQuizDataByClientId(@Param("chatId") Long chatId);
}
