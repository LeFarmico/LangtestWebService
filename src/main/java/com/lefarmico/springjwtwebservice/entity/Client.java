package com.lefarmico.springjwtwebservice.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "client")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String clientId;

    @Column
    private Long categoryId;

    @Column
    private Long nextQuizTime;

    @Column
    private Long wordsInTest;

    @Column
    private Long languageId;
}