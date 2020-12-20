package com.server.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HashedFacePredictionsDTO {

    HashedFacePredictionsDTO() {
    }

    FacePredictionsDTO facePredictionsDTO;

    String SHA256_Hash;
}
