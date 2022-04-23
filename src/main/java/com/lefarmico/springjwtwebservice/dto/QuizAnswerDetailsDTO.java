package com.lefarmico.springjwtwebservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class QuizAnswerDetailsDTO {

    private Long wordId;
    private int currentWordNumber;
    private int summaryWordCount;
    private Long nextQuizTime;
}
