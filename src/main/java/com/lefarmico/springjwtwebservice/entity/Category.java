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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String categoryName;

    @Column
    private Long languageId;

    @Column
    @Builder.Default
    private Boolean immutable = true;
}