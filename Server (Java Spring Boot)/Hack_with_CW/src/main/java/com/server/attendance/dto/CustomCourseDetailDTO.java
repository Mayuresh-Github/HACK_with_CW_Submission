package com.server.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomCourseDetailDTO {

    CustomCourseDetailDTO() {

    }

    private String courseId;

    private String courseName;

    private String department;

    private TeacherDetailDTO teacherDetailDTO;
}
