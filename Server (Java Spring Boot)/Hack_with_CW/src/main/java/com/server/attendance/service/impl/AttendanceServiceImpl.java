package com.server.attendance.service.impl;

import com.server.attendance.dto.AttendanceDetailDTO;
import com.server.attendance.entity.AttendanceDetail;
import com.server.attendance.entity.LectureDetail;
import com.server.attendance.entity.QuestionDetail;
import com.server.attendance.entity.StudentDetail;
import com.server.attendance.repository.AttendanceRepository;
import com.server.attendance.repository.StudentRepository;
import com.server.attendance.service.AttendanceService;
import com.server.attendance.service.LectureService;
import com.server.attendance.service.QuestionService;
import com.server.attendance.service.StudentService;
import com.server.attendance.util.EntityDTOConverter;
import com.server.attendance.util.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    LectureService lectureService;

    @Autowired
    QuestionService questionService;

    @Autowired
    StudentRepository studentRepository;

    @Override
    public int markAttendanceForALecture(AttendanceDetailDTO attendanceDetailDTO, String studentClientId, String otp, String lectureId, String questionId, int selectedChoice) {
        StudentDetail studentDetail = studentRepository.findByStudentClientId(studentClientId);
        if (studentDetail != null) {
            LectureDetail lectureDetail = lectureService.findLectureByLectureId(lectureId);
            if (lectureDetail.getOtp().equals(otp)) {
                QuestionDetail questionDetail = questionService.findByQuestionId(questionId);
                if (questionDetail.getLectureDetail().getLectureDBId().equals(lectureDetail.getLectureDBId())) {
                    if (selectedChoice == questionDetail.getCorrectChoice()) {
                        if (attendanceRepository.findByStudentDetailAndLectureDetail(studentDetail, lectureDetail) == null) {
                            AttendanceDetail attendanceDetail = EntityDTOConverter.getAttendanceDetailEntityFromDTO(attendanceDetailDTO);
                            attendanceDetail.setAttendanceDBId(UUID.randomUUID().toString());
                            attendanceDetail.setCreatedAt(new Date());

                            attendanceDetail.setStudentDetail(studentDetail);
                            attendanceDetail.setQuestionDetail(questionDetail);
                            attendanceDetail.setScore(100);
                            attendanceDetail.setLectureDetail(lectureDetail);

                            try {
                                attendanceRepository.save(attendanceDetail);
                                return 1;
                            } catch (Exception e) {
                                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
                            }
                        } else {
                            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Attendance was already marked");
                        }
                    } else {
                        if (attendanceRepository.findByStudentDetailAndLectureDetail(studentDetail, lectureDetail) == null) {
                            AttendanceDetail attendanceDetail = EntityDTOConverter.getAttendanceDetailEntityFromDTO(attendanceDetailDTO);
                            attendanceDetail.setAttendanceDBId(UUID.randomUUID().toString());
                            attendanceDetail.setCreatedAt(new Date());

                            attendanceDetail.setStudentDetail(studentDetail);
                            attendanceDetail.setQuestionDetail(questionDetail);
                            attendanceDetail.setScore(0);
                            attendanceDetail.setLectureDetail(lectureDetail);

                            try {
                                attendanceRepository.save(attendanceDetail);
                                return 1;
                            } catch (Exception e) {
                                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
                            }
                        } else {
                            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Attendance was already marked");
                        }
                    }
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.LECTURE_NOT_FOUND);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.OTP_NOT_MATCH);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.STUDENT_DOES_NOT_EXISTS);
        }
    }

    @Override
    public AttendanceDetailDTO getDetailOfStudent(String studentClientId, String lectureId) {
        StudentDetail studentDetail = studentRepository.findByStudentClientId(studentClientId);
        if (studentDetail != null) {
            LectureDetail lectureDetail = lectureService.findLectureByLectureId(lectureId);
            if (lectureDetail != null) {
                return EntityDTOConverter.getAttendanceDetailDTOFromEntity(attendanceRepository.findByStudentDetailAndLectureDetail(studentDetail, lectureDetail));
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.LECTURE_NOT_FOUND);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.STUDENT_DOES_NOT_EXISTS);
        }
    }

}
