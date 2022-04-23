package com.lefarmico.springjwtwebservice.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "quiz_data")
public class QuizData {

    @Id
    @NonNull
    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;


    @Column(name = "status", nullable = false)
    @Builder.Default
    private String status = "DEFAULT";

    @Column(name = "words_in_quiz", nullable = false)
    @NonNull
    private int wordsInQuiz;

    @Column(name = "current_word_number", nullable = false)
    @Builder.Default
    @NonNull
    private int currentWordNumber = 0;

    @Column(name = "break_time_in_millis", nullable = false)
    @NonNull
    private Long breakTimeInMillis;

    @Column(name = "language_id", nullable = false)
    @NonNull
    private Long languageId;

    @Column(name = "category_id", nullable = false)
    @NonNull
    private Long categoryId;

    public static enum Status {
        DEFAULT, PAUSE, RUNNING, ERROR
    }
}