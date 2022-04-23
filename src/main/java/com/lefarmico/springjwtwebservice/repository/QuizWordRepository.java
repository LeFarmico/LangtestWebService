package com.lefarmico.springjwtwebservice.repository;

import com.lefarmico.springjwtwebservice.entity.QuizWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface QuizWordRepository extends JpaRepository<QuizWord, Long> {

    @Query(nativeQuery = true,
           value = "SELECT * FROM quiz_word WHERE chat_id = :chatId"
    )
    public List<QuizWord> getQuizWordsByChatId(@Param("chatId") Long chatId);

    @Query(nativeQuery = true,
           value = "SELECT * FROM quiz_word WHERE chat_id = :chatId AND is_answered = false"
    )
    public List<QuizWord> getUnansweredQuizWordsByChatId(@Param("chatId") Long chatId);

    @Query(nativeQuery = true,
           value = "SELECT * FROM quiz_word WHERE id = :quizWordId AND chat_id = :chatId LIMIT 1"
    )
    public Optional<QuizWord> getQuizWordByQuizWordIdAndChatId(
            @Param("chatId") Long chatId,
            @Param("quizWordId") Long quizWordId
    );

    @Query(nativeQuery = true,
            name = "DELETE * FROM quiz_word WHERE chat_id = :chatId"
    )
    public Integer deleteQuizWordsByChatId(@Param("chatId") Long chatId);

    @Query(nativeQuery = true,
            value = "UPDATE quiz_word " +
                    "SET is_answered = false " +
                    "WHERE chat_id = :chatId"
    )
    @Modifying
    public Integer updateIsAnsweredForClientId(@Param("chatId") Long chatId);
}
