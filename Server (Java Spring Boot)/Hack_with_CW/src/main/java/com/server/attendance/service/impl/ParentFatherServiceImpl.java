package com.server.attendance.service.impl;

import com.server.attendance.dto.FatherDetailDTO;
import com.server.attendance.entity.FatherDetail;
import com.server.attendance.entity.StudentDetail;
import com.server.attendance.repository.ParentFatherRepository;
import com.server.attendance.service.ParentFatherService;
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
public class ParentFatherServiceImpl implements ParentFatherService {

    @Autowired
    ParentFatherRepository parentFatherRepository;

    @Autowired
    StudentService studentService;

    @Override
    public int createParentFatherOfStudent(FatherDetailDTO fatherDetailDTO, String password, String studentEmail) {
        StudentDetail studentDetail = studentService.findStudentAllDetailsByEmail(studentEmail);
        if (studentDetail != null) {
            FatherDetail fatherEmailToCheck = parentFatherRepository.findByFatherEmail(fatherDetailDTO.getFatherEmail());
            if (fatherEmailToCheck == null) {
                FatherDetail fatherDetailToCheck = parentFatherRepository.findByStudentDetail(studentDetail);
                if (fatherDetailToCheck == null) {
                    FatherDetail fatherDetail = EntityDTOConverter.getParentFatherDetailEntityFromDTO(fatherDetailDTO);
                    fatherDetail.setParentFatherDBId(UUID.randomUUID().toString());
                    fatherDetail.setFatherClientId(UUID.randomUUID().toString());
                    fatherDetail.setFatherPassword(EncryptUtils.encrypt(password));
                    fatherDetail.setCreatedAt(new Date());
                    fatherDetail.setStudentDetail(studentDetail);

                    try {
                        parentFatherRepository.saveAndFlush(fatherDetail);
                        return 1;
                    } catch (Exception e) {
                        return -1;
                    }
                } else {
                    throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ErrorMessages.FATHER_EXISTS_FOR_STUDENT);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ErrorMessages.DUPLICATE_EMAIL);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ErrorMessages.STUDENT_DOES_NOT_EXISTS);
        }
    }

    @Override
    public FatherDetailDTO getFatherDetailsFromFatherEmailOrStudentEmail(String fatherEmail, String studentEmail) {
        if (fatherEmail == null) {
            if (studentEmail == null) {
                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ErrorMessages.INPUT_EMPTY);
            } else {
                StudentDetail studentDetail = studentService.findStudentAllDetailsByEmail(studentEmail);
                if (studentDetail != null) {
                    FatherDetail fatherDetail = parentFatherRepository.findByStudentDetail(studentDetail);
                    if (fatherDetail != null) {
                        FatherDetailDTO fatherDetailDTO = EntityDTOConverter.getParentFatherDetailDTOFromEntity(fatherDetail);
                        fatherDetailDTO.setStudentDetailDTO(EntityDTOConverter.getStudentDetailDTOFromEntity(fatherDetail.getStudentDetail()));
                        return fatherDetailDTO;
                    } else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.FATHER_DOES_NOT_EXISTS);
                    }

                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.STUDENT_DOES_NOT_EXISTS);
                }
            }
        } else {
            FatherDetail fatherDetail = parentFatherRepository.findByFatherEmail(fatherEmail);
            if (fatherDetail != null) {
                FatherDetailDTO fatherDetailDTO = EntityDTOConverter.getParentFatherDetailDTOFromEntity(fatherDetail);
                fatherDetailDTO.setStudentDetailDTO(EntityDTOConverter.getStudentDetailDTOFromEntity(fatherDetail.getStudentDetail()));
                return fatherDetailDTO;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.FATHER_DOES_NOT_EXISTS);
            }
        }
    }

    @Override
    public FatherDetailDTO checkSignInDetails(String fatherEmail, String fatherPassword) {
        FatherDetail fatherDetail = parentFatherRepository.findByFatherEmailAndFatherPassword(fatherEmail, EncryptUtils.encrypt(fatherPassword));
        if (fatherDetail != null) {
            FatherDetailDTO fatherDetailDTO = EntityDTOConverter.getParentFatherDetailDTOFromEntity(fatherDetail);
            fatherDetailDTO.setStudentDetailDTO(EntityDTOConverter.getStudentDetailDTOFromEntity(fatherDetail.getStudentDetail()));
            return fatherDetailDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_CREDENTIALS);
        }
    }

}
