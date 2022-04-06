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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @Builder.Default
    private String status = "DEFAULT";

    @Column
    @NonNull
    private String clientId;

    @Column(name = "words_in_quiz")
    @NonNull
    private Short wordsInQuiz;

    @Column(name = "current_word_number")
    @Builder.Default
    private Short currentWordNumber = 0;

    @Column
    @NonNull
    private Long nextQuizTime;

    @Column
    @NonNull
    private Long languageId;

    @Column
    @NonNull
    private Long categoryId;
}