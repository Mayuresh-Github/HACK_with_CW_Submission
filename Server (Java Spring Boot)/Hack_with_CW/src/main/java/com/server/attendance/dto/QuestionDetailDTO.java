package com.server.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionDetailDTO {

    QuestionDetailDTO() {
    }

    private String questionDescription;

    private String questionId;

    private String choice1;

    private String choice2;

    private String choice3;

    private String choice4;

    private String choice5;

}
