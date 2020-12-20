package com.server.attendance.service.impl;

import com.server.attendance.dto.TeacherDetailDTO;
import com.server.attendance.entity.TeacherDetail;
import com.server.attendance.repository.HodRepository;
import com.server.attendance.repository.TeacherRepository;
import com.server.attendance.service.HoDService;
import com.server.attendance.service.TeacherService;
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
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    HoDService hoDService;

    @Autowired
    HodRepository hodRepository;

    @Override
    public HttpStatus createTeacher(TeacherDetailDTO teacherDetailDTO, String passwordToEncrypt) {
        if (teacherRepository.findByTeacherEmail(teacherDetailDTO.getTeacherEmail()) == null) {
            TeacherDetail teacherDetail = EntityDTOConverter.getTeacherDetailEntityFromDTO(teacherDetailDTO);
            teacherDetail.setTeacherDBId(UUID.randomUUID().toString());
            teacherDetail.setTeacherClientId(UUID.randomUUID().toString());
            teacherDetail.setTeacherPassword(EncryptUtils.encrypt(passwordToEncrypt));
            teacherDetail.setCreatedAt(new Date());

            teacherDetail.setHoDDetail(hoDService.findByEmail(teacherDetailDTO.getHoDDetailDTO().getHodEmail()));

            try {
                teacherRepository.saveAndFlush(teacherDetail);
                return HttpStatus.CREATED;
            } catch (Exception e) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            return HttpStatus.PRECONDITION_FAILED;
        }
    }

    @Override
    public TeacherDetailDTO checkTeacherForSignIn(String email, String password) {
        TeacherDetail teacherDetail = teacherRepository.findByTeacherEmailAndTeacherPassword(email, EncryptUtils.encrypt(password));
        if (teacherDetail != null) {
            TeacherDetailDTO teacherDetailDTO;
            teacherDetailDTO = EntityDTOConverter.getTeacherDetailDTOFromEntity(teacherDetail);
            teacherDetailDTO.setHoDDetailDTO(EntityDTOConverter.getHODDetailDTOFromEntity(teacherDetail.getHoDDetail()));
            return teacherDetailDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_CREDENTIALS);
        }
    }

    @Override
    public TeacherDetail findByTeacherEmail(String email) {
        return teacherRepository.findByTeacherEmail(email);
    }

    @Override
    public TeacherDetail findByTeacherId(String teacherId) {
        return teacherRepository.findByTeacherId(teacherId);
    }

}
