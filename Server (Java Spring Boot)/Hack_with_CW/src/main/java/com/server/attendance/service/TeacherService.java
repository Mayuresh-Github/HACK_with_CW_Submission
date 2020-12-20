package com.server.attendance.service;

import com.server.attendance.dto.TeacherDetailDTO;
import com.server.attendance.entity.TeacherDetail;
import org.springframework.http.HttpStatus;

public interface TeacherService {

    HttpStatus createTeacher(TeacherDetailDTO teacherDetailDTO, String passwordToEncrypt);

    TeacherDetailDTO checkTeacherForSignIn(String email, String password);

    TeacherDetail findByTeacherEmail(String email);

    TeacherDetail findByTeacherId(String teacherId);
}
