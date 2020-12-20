package com.server.attendance.service;

import com.server.attendance.dto.AttendanceDetailDTO;
import com.server.attendance.dto.FacePredictionsDTO;
import com.server.attendance.dto.ResponseDTO;
import com.server.attendance.dto.StudentDetailDTO;
import com.server.attendance.entity.StudentDetail;

public interface StudentService {

    StudentDetailDTO findStudentByEmail(String emailId);

    StudentDetail findStudentAllDetailsByEmail(String emailId);

    int createNewStudent(StudentDetailDTO studentDetailDTO, String password);

    StudentDetailDTO signInStudent(String studentEmail, String studentPassword);

    int updateFacePredictions(String face1, String face2, String face3, String face4, String face5, String studentClientId);

    FacePredictionsDTO getFacePredictionsOfStudent(String studentClientId);

    AttendanceDetailDTO getAttendanceDetails(String studentClientId, String lectureId);
}
