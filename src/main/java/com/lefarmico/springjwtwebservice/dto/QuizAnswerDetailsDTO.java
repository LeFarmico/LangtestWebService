package com.lefarmico.springjwtwebservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class QuizAnswerDetailsDTO {

    private Long wordId;
    private Long currentWordNumber;
    private Long summaryWordCount;
    private Long nextQuizTime;
}
