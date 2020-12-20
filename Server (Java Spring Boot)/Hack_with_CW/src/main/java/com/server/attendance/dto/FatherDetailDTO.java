package com.server.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FatherDetailDTO {

    FatherDetailDTO() {
    }

    private String fatherClientId;

    private String role;

    private String fatherName;

    private String fatherEmail;

    private String fatherPhone;

    private StudentDetailDTO studentDetailDTO;
}
