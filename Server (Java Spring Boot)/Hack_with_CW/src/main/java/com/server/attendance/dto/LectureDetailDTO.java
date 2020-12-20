package com.server.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class LectureDetailDTO {

    LectureDetailDTO() {
    }

    private String lectureId;

    private LocalDate lectureDate;

    private Timestamp startTime;

    private Timestamp endTime;

    CourseDetailDTO courseDetailDTO;
}
