package com.server.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FacePredictionsDTO {

    FacePredictionsDTO() {
    }

    String studentEmail;

    String face1;

    String face2;

    String face3;

    String face4;

    String face5;

    @Override
    public String toString() {
        return this.studentEmail + "," + this.face1 + "," + this.face2 + "," + this.face3 + "," + this.face4 + "," + this.face5;
    }
}
