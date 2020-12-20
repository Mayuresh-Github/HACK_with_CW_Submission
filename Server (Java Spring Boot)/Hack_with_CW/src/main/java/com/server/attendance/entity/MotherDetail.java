package com.server.attendance.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "student_mother_details")
public class MotherDetail extends AuditEntity {

    @Id
    @Column(name = "parent_mother_db_id")
    private String parentMotherDBId;

    @Column(name = "mother_client_id")
    private String motherClientId;

    @Column(name = "role")
    private String role;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "mother_email")
    private String motherEmail;

    @Column(name = "mother_password")
    private String motherPassword;

    @Column(name = "mother_phone")
    private String motherPhone;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentDetail studentDetail;
}
