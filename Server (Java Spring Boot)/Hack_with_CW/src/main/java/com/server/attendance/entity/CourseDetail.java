package com.server.attendance.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "course")
public class CourseDetail extends AuditEntity {

    @Id
    @Column(name = "course_db_id")
    private String courseDBId;

    @Column(name = "course_id", unique = true)
    private String courseId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "department")
    private String department;

    @ManyToOne
    @JoinColumn(name = "teacher_db_id", nullable = false)
    private TeacherDetail teacherDetail;
}
