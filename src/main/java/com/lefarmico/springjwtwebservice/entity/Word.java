package com.lefarmico.springjwtwebservice.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "word")
@Entity
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "word_original")
    private String wordOriginal;

    @Column(name = "word_translation")
    private String wordTranslation;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "language_id")
    private Long languageId;
}