package com.server.attendance.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "hod")
public class HoDDetail extends AuditEntity {

    @Id
    @Column(name = "hod_db_id")
    private String hodDBId;

    @Column(name = "hod_client_id")
    private String hodClientId;

    @Column(name = "hod_id")
    private String hodId;

    @Column(name = "role")
    private String role;

    @Column(name = "hod_name")
    private String hodName;

    @Column(name = "hod_email")
    private String hodEmail;

    @Column(name = "password")
    private String hodPassword;

    @Column(name = "hod_phone")
    private String hodPhone;

    @Column(name = "department")
    private String department;

    @Column(name = "admin")
    boolean admin;

}
