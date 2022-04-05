package com.lefarmico.springjwtwebservice.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "quiz_word")
public class QuizWord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String clientId;

    @Column
    private Boolean isAnswered;

    @Column
    private String originalWord;

    @Column
    private String correctTranslation;

    @Column
    private Long quizId;

    @ElementCollection
    private List<String> wrongTranslations;
}