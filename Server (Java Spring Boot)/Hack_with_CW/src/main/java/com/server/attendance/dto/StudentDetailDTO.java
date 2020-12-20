package com.server.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class StudentDetailDTO {

    StudentDetailDTO() {
    }

    private String role;

    private String studentName;

    private String studentClientId;

    private String studentEmail;

    private String studentDOB;

    private String department;

    private String courseName;

    private LocalDate enrolledDate;

    private LocalDate graduationDate;

}
