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
    @Column(name = "client_id", nullable = false, unique = true)
    @NonNull
    private String clientId;

    @Column(name = "status", nullable = false)
    @Builder.Default
    private String status = "DEFAULT";

    @Column(name = "words_in_quiz", nullable = false)
    @NonNull
    private Short wordsInQuiz;

    @Column(name = "current_word_number", nullable = false)
    @Builder.Default
    private Short currentWordNumber = 0;

    @Column(name = "next_quiz_time", nullable = false)
    @NonNull
    private Long nextQuizTime;

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