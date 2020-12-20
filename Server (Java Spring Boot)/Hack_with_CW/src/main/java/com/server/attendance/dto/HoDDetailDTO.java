package com.server.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HoDDetailDTO {

    HoDDetailDTO() {
    }

    private String hodId;

    private String hodClientId;

    private String role;

    private String hodName;

    private String hodEmail;

    private String hodPhone;

    private String department;
}
