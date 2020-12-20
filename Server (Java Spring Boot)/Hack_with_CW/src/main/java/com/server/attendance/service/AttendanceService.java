package com.server.attendance.service;

import com.server.attendance.dto.AttendanceDetailDTO;

public interface AttendanceService {

    int markAttendanceForALecture(AttendanceDetailDTO attendanceDetailDTO, String studentEmail, String otp, String lectureId, String questionId, int selectedChoice);

    AttendanceDetailDTO getDetailOfStudent(String studentClientId, String lectureId);
}
