package com.lefarmico.springjwtwebservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "quiz_word")
public class QuizWord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private Long clientId;

    @Column
    private Boolean isAnswered;

    @Column
    private String originalWord;

    @Column
    private String correctTranslation;

    @ElementCollection
    private List<String> wrongTranslations;
}