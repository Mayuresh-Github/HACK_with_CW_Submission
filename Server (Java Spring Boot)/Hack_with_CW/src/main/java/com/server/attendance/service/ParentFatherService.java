package com.server.attendance.service;

import com.server.attendance.dto.FatherDetailDTO;

public interface ParentFatherService {

    int createParentFatherOfStudent(FatherDetailDTO fatherDetailDTO, String password, String studentEmail);

    FatherDetailDTO getFatherDetailsFromFatherEmailOrStudentEmail(String fatherEmail, String studentEmail);

    FatherDetailDTO checkSignInDetails(String fatherEmail, String fatherPassword);
}
