package com.server.attendance.service.impl;

import com.server.attendance.dto.AttendanceDetailDTO;
import com.server.attendance.dto.FacePredictionsDTO;
import com.server.attendance.dto.ResponseDTO;
import com.server.attendance.dto.StudentDetailDTO;
import com.server.attendance.entity.AttendanceDetail;
import com.server.attendance.entity.LectureDetail;
import com.server.attendance.entity.StudentDetail;
import com.server.attendance.repository.AttendanceRepository;
import com.server.attendance.repository.StudentRepository;
import com.server.attendance.service.AttendanceService;
import com.server.attendance.service.LectureService;
import com.server.attendance.service.StudentService;
import com.server.attendance.util.EncryptUtils;
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
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    LectureService lectureService;


    @Override
    public int createNewStudent(StudentDetailDTO studentDetailDTO, String password) {
        if (findStudentByEmail(studentDetailDTO.getStudentEmail()) == null) {
            StudentDetail studentDetail = EntityDTOConverter.getStudentDetailEntityFromDTO(studentDetailDTO);
            studentDetail.setStudentDBId(UUID.randomUUID().toString());
            studentDetail.setStudentClientId(UUID.randomUUID().toString());
            studentDetail.setCreatedAt(new Date());
            studentDetail.setStudentPassword(EncryptUtils.encrypt(password));

            try {
                studentRepository.saveAndFlush(studentDetail);
                return 1;
            } catch (Exception e) {
                return -2;
            }
        } else {
            return -1;
        }
    }

    @Override
    public StudentDetailDTO signInStudent(String studentEmail, String studentPassword) {
        StudentDetail studentDetail = studentRepository.findByStudentEmailAndStudentPassword(studentEmail, EncryptUtils.encrypt(studentPassword));
        if (studentDetail != null) {
            return EntityDTOConverter.getStudentDetailDTOFromEntity(studentDetail);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_CREDENTIALS);
        }
    }

    @Override
    public StudentDetailDTO findStudentByEmail(String emailId) {
        StudentDetail studentDetail = studentRepository.findByStudentEmail(emailId);
        if (studentDetail != null) {
            return EntityDTOConverter.getStudentDetailDTOFromEntity(studentDetail);
        } else {
            return null;
        }
    }

    @Override
    public StudentDetail findStudentAllDetailsByEmail(String emailId) {
        return studentRepository.findByStudentEmail(emailId);
    }

    @Override
    public int updateFacePredictions(String face1, String face2, String face3, String face4, String face5, String studentClientId) {
        StudentDetail studentDetail = studentRepository.findByStudentClientId(studentClientId);
        if (studentDetail != null) {
            studentDetail.setFace1(face1);
            studentDetail.setFace2(face2);
            studentDetail.setFace3(face3);
            studentDetail.setFace4(face4);
            studentDetail.setFace5(face5);

            try {
                studentRepository.saveAndFlush(studentDetail);
                return 1;
            } catch (Exception e) {
                return -1;
            }

        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_CREDENTIALS);
        }
    }

    @Override
    public FacePredictionsDTO getFacePredictionsOfStudent(String studentClientId) {
        StudentDetail studentDetail = studentRepository.findByStudentClientId(studentClientId);
        if (studentDetail != null) {
            return new FacePredictionsDTO(studentDetail.getStudentEmail(), studentDetail.getFace1(), studentDetail.getFace2(), studentDetail.getFace3(), studentDetail.getFace4(), studentDetail.getFace5());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_CREDENTIALS);
        }
    }

    @Override
    public AttendanceDetailDTO getAttendanceDetails(String studentClientId, String lectureId) {
        StudentDetail studentDetail = studentRepository.findByStudentClientId(studentClientId);
        if (studentDetail != null) {
            LectureDetail lectureDetail = lectureService.findLectureByLectureId(lectureId);
            if (lectureDetail != null) {
                AttendanceDetail attendanceDetail = attendanceRepository.findByStudentDetailAndLectureDetail(studentDetail, lectureDetail);
                if (attendanceDetail != null) {
                    AttendanceDetailDTO attendanceDetailDTO = EntityDTOConverter.getAttendanceDetailDTOFromEntity(attendanceDetail);
                    return attendanceDetailDTO;
                } else {
                    return null;
                }
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.LECTURE_NOT_FOUND);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.STUDENT_DOES_NOT_EXISTS);
        }

    }

}
