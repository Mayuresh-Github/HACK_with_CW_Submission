package com.server.attendance.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "student_details")
public class StudentDetail extends AuditEntity {

    @Id
    @Column(name = "student_db_id")
    private String studentDBId;

    @Column(name = "student_client_id")
    private String studentClientId;

    @Column(name = "role")
    private String role;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "student_email")
    private String studentEmail;

    @Column(name = "student_password")
    private String studentPassword;

    @Column(name = "student_dob")
    private String studentDOB;

    @Column(name = "department")
    private String department;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "enrolled_date")
    private LocalDate enrolledDate;

    @Column(name = "date_of_graduation")
    private LocalDate graduationDate;

    @Column(name = "face_1_predicted", length = 10000, columnDefinition = "TEXT")
    private String face1;

    @Column(name = "face_2_predicted", length = 10000, columnDefinition = "TEXT")
    private String face2;

    @Column(name = "face_3_predicted", length = 10000, columnDefinition = "TEXT")
    private String face3;

    @Column(name = "face_4_predicted", length = 10000, columnDefinition = "TEXT")
    private String face4;

    @Column(name = "face_5_predicted", length = 10000, columnDefinition = "TEXT")
    private String face5;

}
