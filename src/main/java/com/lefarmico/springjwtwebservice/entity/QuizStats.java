package com.lefarmico.springjwtwebservice.entity;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuizStats {

    private int currentWordNumber;
    private int summaryWordCount;
    private String languageName;
    private String categoryName;
    private Long nextQuizTime;
}
