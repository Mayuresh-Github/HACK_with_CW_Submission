package com.server.attendance.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "student_father_details")
public class FatherDetail extends AuditEntity {

    @Id
    @Column(name = "parent_father_db_id")
    private String parentFatherDBId;

    @Column(name = "father_client_id")
    private String fatherClientId;

    @Column(name = "role")
    private String role;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "father_email")
    private String fatherEmail;

    @Column(name = "father_password")
    private String fatherPassword;

    @Column(name = "father_phone")
    private String fatherPhone;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentDetail studentDetail;
}
