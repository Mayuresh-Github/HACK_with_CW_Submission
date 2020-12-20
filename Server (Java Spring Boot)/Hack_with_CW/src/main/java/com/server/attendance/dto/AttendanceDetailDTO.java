package com.server.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class AttendanceDetailDTO {

    AttendanceDetailDTO() {
    }

    private Timestamp lectureStartTimeByStudent;

    private Timestamp lectureEndTimeByStudent;

    private String coordinatesStart;

    private String coordinatesStart10;

    private String coordinatesStart20;

    private String coordinatesStart30;

    private String coordinatesStart40;

    private String coordinatesStart50;

    private String coordinatesEnd;
}
