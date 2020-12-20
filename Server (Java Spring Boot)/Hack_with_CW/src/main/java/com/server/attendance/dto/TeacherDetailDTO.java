package com.server.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeacherDetailDTO {

    TeacherDetailDTO() {
    }

    private String teacherId;

    private String teacherClientId;

    private String role;

    private String teacherName;

    private String teacherEmail;

    private String teacherPhone;

    private String department;

    private HoDDetailDTO hoDDetailDTO;
}
