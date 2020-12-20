package com.server.attendance.service;

import com.server.attendance.dto.MotherDetailDTO;

public interface ParentMotherService {

    int createParentMotherOfStudent(MotherDetailDTO motherDetailDTO, String password, String studentEmail);

    MotherDetailDTO getMotherDetailsFromMotherEmailOrStudentEmail(String motherEmail, String studentEmail);

    MotherDetailDTO checkSignInDetails(String motherEmail, String motherPassword);
}
