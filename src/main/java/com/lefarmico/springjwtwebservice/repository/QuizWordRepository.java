package com.lefarmico.springjwtwebservice.repository;

import com.lefarmico.springjwtwebservice.entity.QuizWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface QuizWordRepository extends JpaRepository<QuizWord, Long> {

    @Query(nativeQuery = true,
           value = "SELECT * FROM quiz_word WHERE client_id = :clientId"
    )
    public List<QuizWord> getQuizWordsByClientId(@Param("clientId") String clientId);

    @Query(nativeQuery = true,
           value = "SELECT * FROM quiz_word WHERE client_id = :clientId AND is_answered = false"
    )
    public List<QuizWord> getUnansweredQuizWordsByClientId(@Param("clientId") String clientId);

    @Query(nativeQuery = true,
           value = "SELECT * FROM quiz_word WHERE id = :quizWordId AND client_id = :clientId LIMIT 1"
    )
    public Optional<QuizWord> getQuizWordByQuizWordIdAndClientId(
            @Param("clientId") String clientId,
            @Param("quizWordId") Long quizWordId
    );

    @Query(nativeQuery = true,
            name = "DELETE * FROM quiz_word WHERE client_id = :clientId"
    )
    public Integer deleteQuizWordsByClientId(@Param("clientId") String clientId);

    @Query(nativeQuery = true,
            value = "UPDATE quiz_word " +
                    "SET is_answered = false " +
                    "WHERE client_id = :clientId"
    )
    @Modifying
    public Integer updateIsAnsweredForClientId(@Param("clientId") String clientId);
}
