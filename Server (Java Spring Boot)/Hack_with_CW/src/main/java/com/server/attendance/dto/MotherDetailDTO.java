package com.server.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
public class MotherDetailDTO {

    MotherDetailDTO() {
    }

    private String role;

    private String  motherClientId;

    private String motherName;

    private String motherEmail;

    private String motherPhone;

    private StudentDetailDTO studentDetailDTO;
}
