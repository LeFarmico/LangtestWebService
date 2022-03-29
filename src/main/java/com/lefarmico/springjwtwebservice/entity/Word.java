package com.lefarmico.springjwtwebservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "word")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String wordOriginal;

    @Column
    private String wordTranslation;

    @Column
    private Long categoryId;

    @Column
    private Long languageId;
}