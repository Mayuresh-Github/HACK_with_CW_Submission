package com.server.attendance.dto;

import com.server.attendance.entity.TeacherDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseDetailDTO {

    CourseDetailDTO() {
    }

    private String courseId;

    private String courseName;

    private String department;

    private TeacherDetailDTO teacherDetailDTO;
}
