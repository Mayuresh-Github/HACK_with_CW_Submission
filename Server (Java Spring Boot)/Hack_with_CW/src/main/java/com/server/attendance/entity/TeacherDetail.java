package com.server.attendance.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "teachers")
public class TeacherDetail extends AuditEntity {

    @Id
    @Column(name = "teacher_db_id")
    private String teacherDBId;

    @Column(name = "teacher_client_id")
    private String teacherClientId;

    @Column(name = "teacher_id")
    private String teacherId;

    @Column(name = "role")
    private String role;

    @Column(name = "teacher_name")
    private String teacherName;

    @Column(name = "teacher_email")
    private String teacherEmail;

    @Column(name = "teacher_password")
    private String teacherPassword;

    @Column(name = "teacher_phone")
    private String teacherPhone;

    @Column(name = "department")
    private String department;

    @ManyToOne
    @JoinColumn(name = "hod_db_id", nullable = false)
    private HoDDetail hoDDetail;

}
