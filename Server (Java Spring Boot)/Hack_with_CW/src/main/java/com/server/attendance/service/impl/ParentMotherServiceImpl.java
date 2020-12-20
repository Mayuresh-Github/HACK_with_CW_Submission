package com.server.attendance.service.impl;

import com.server.attendance.dto.MotherDetailDTO;
import com.server.attendance.entity.MotherDetail;
import com.server.attendance.entity.StudentDetail;
import com.server.attendance.repository.ParentMotherRepository;
import com.server.attendance.service.ParentMotherService;
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
public class ParentMotherServiceImpl implements ParentMotherService {

    @Autowired
    ParentMotherRepository parentMotherRepository;

    @Autowired
    StudentService studentService;

    @Override
    public int createParentMotherOfStudent(MotherDetailDTO motherDetailDTO, String password, String studentEmail) {
        StudentDetail studentDetail = studentService.findStudentAllDetailsByEmail(studentEmail);
        if (studentDetail != null) {
            MotherDetail motherEmailToCheck = parentMotherRepository.findByMotherEmail(motherDetailDTO.getMotherEmail());
            if (motherEmailToCheck == null) {
                MotherDetail motherDetailToCheck = parentMotherRepository.findByStudentDetail(studentDetail);
                if (motherDetailToCheck == null) {
                    MotherDetail motherDetail = EntityDTOConverter.getParentMotherDetailEntityFromDTO(motherDetailDTO);
                    motherDetail.setParentMotherDBId(UUID.randomUUID().toString());
                    motherDetail.setMotherClientId(UUID.randomUUID().toString());
                    motherDetail.setMotherPassword(EncryptUtils.encrypt(password));
                    motherDetail.setCreatedAt(new Date());
                    motherDetail.setStudentDetail(studentDetail);

                    try {
                        parentMotherRepository.saveAndFlush(motherDetail);
                        return 1;
                    } catch (Exception e) {
                        return -1;
                    }
                } else {
                    throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ErrorMessages.MOTHER_EXISTS_FOR_STUDENT);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ErrorMessages.DUPLICATE_EMAIL);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ErrorMessages.STUDENT_DOES_NOT_EXISTS);
        }
    }

    @Override
    public MotherDetailDTO getMotherDetailsFromMotherEmailOrStudentEmail(String motherEmail, String studentEmail) {
        if (motherEmail == null) {
            if (studentEmail == null) {
                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ErrorMessages.INPUT_EMPTY);
            } else {
                StudentDetail studentDetail = studentService.findStudentAllDetailsByEmail(studentEmail);
                if (studentDetail != null) {
                    MotherDetail motherDetail = parentMotherRepository.findByStudentDetail(studentDetail);
                    if (motherDetail != null) {
                        MotherDetailDTO motherDetailDTO = EntityDTOConverter.getParentMotherDetailDTOFromEntity(motherDetail);
                        motherDetailDTO.setStudentDetailDTO(EntityDTOConverter.getStudentDetailDTOFromEntity(motherDetail.getStudentDetail()));
                        return motherDetailDTO;
                    } else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.MOTHER_DOES_NOT_EXISTS);
                    }

                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.STUDENT_DOES_NOT_EXISTS);
                }
            }
        } else {
            MotherDetail motherDetail = parentMotherRepository.findByMotherEmail(motherEmail);
            if (motherDetail != null) {
                MotherDetailDTO motherDetailDTO = EntityDTOConverter.getParentMotherDetailDTOFromEntity(motherDetail);
                motherDetailDTO.setStudentDetailDTO(EntityDTOConverter.getStudentDetailDTOFromEntity(motherDetail.getStudentDetail()));
                return motherDetailDTO;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.MOTHER_DOES_NOT_EXISTS);
            }
        }
    }

    @Override
    public MotherDetailDTO checkSignInDetails(String motherEmail, String motherPassword) {
        MotherDetail motherDetail = parentMotherRepository.findByMotherEmailAndMotherPassword(motherEmail, EncryptUtils.encrypt(motherPassword));
        if (motherDetail != null) {
            MotherDetailDTO motherDetailDTO = EntityDTOConverter.getParentMotherDetailDTOFromEntity(motherDetail);
            motherDetailDTO.setStudentDetailDTO(EntityDTOConverter.getStudentDetailDTOFromEntity(motherDetail.getStudentDetail()));
            return motherDetailDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_CREDENTIALS);
        }
    }

}
