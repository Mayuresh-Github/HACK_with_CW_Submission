package com.server.attendance.service.impl;

import com.server.attendance.dto.*;
import com.server.attendance.entity.CourseDetail;
import com.server.attendance.entity.LectureDetail;
import com.server.attendance.entity.TeacherDetail;
import com.server.attendance.repository.LectureRepository;
import com.server.attendance.service.CourseService;
import com.server.attendance.service.HoDService;
import com.server.attendance.service.LectureService;
import com.server.attendance.service.TeacherService;
import com.server.attendance.util.EntityDTOConverter;
import com.server.attendance.util.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class LectureServiceImpl implements LectureService {

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    TeacherService teacherService;

    @Autowired
    HoDService hoDService;

    @Autowired
    CourseService courseService;

    @Override
    public String createALecture(LectureDetailDTO lectureDetailDTO, String teacherId, String courseId) {
        TeacherDetail teacherDetail = teacherService.findByTeacherId(teacherId);
        if (teacherDetail != null) {
            CourseDetail courseDetail = courseService.getCourseDetailsEntity(courseId);
            if (courseDetail != null) {
                if (courseDetail.getTeacherDetail().getTeacherId().equals(teacherDetail.getTeacherId())) {
                    LectureDetail lectureDetail = EntityDTOConverter.getLectureDetailEntityFromDTO(lectureDetailDTO);
                    lectureDetail.setLectureDBId(UUID.randomUUID().toString());
                    lectureDetail.setOtp(RandomStringUtils.randomAlphanumeric(6));
                    lectureDetail.setCreatedAt(new Date());
                    lectureDetail.setCourseDetail(courseDetail);

                    try {
                        lectureRepository.saveAndFlush(lectureDetail);
                        return lectureDetail.getLectureId();
                    } catch (Exception e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
                    }
                } else {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.COURSE_TEACHER_NOT_MATCHED);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.COURSE_NOT_FOUND);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.TEACHER_DOES_NOT_EXISTS);
        }
    }

    @Override
    public List<LectureDetailDTO> getLectureDetailsFrommCourseId(String courseId) {
        CourseDetail courseDetail = courseService.getCourseDetailsEntity(courseId);
        if (courseDetail != null) {
            List<LectureDetailDTO> lectureDetailDTOList = new ArrayList<>();
            List<LectureDetail> lectureDetailList = lectureRepository.getLecturesByCourseId(courseDetail);
            if (lectureDetailList.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Lectures registered for this Course");
            } else {
                for (LectureDetail l : lectureDetailList) {
                    LectureDetailDTO lectureDetailDTO = EntityDTOConverter.getLectureDetailDTOFromEntity(l);
                    CourseDetailDTO courseDetailDTO = EntityDTOConverter.getCourseDetailDTOFromEntity(courseService.getCourseDetailsEntity(l.getCourseDetail().getCourseId()));
                    TeacherDetailDTO teacherDetailDTO = EntityDTOConverter.getTeacherDetailDTOFromEntity(teacherService.findByTeacherEmail(l.getCourseDetail().getTeacherDetail().getTeacherEmail()));
                    HoDDetailDTO hoDDetailDTO = EntityDTOConverter.getHODDetailDTOFromEntity(hoDService.findByEmail(l.getCourseDetail().getTeacherDetail().getHoDDetail().getHodEmail()));
                    teacherDetailDTO.setHoDDetailDTO(hoDDetailDTO);
                    courseDetailDTO.setTeacherDetailDTO(teacherDetailDTO);
                    lectureDetailDTO.setCourseDetailDTO(courseDetailDTO);
                    lectureDetailDTOList.add(lectureDetailDTO);
                }
                return lectureDetailDTOList;
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.COURSE_NOT_FOUND);
        }
    }

    @Override
    public LectureDetail findLectureByLectureId(String lectureId) {
        LectureDetail lectureDetail = lectureRepository.findByLectureId(lectureId);
        if (lectureDetail != null) {
            return lectureDetail;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.LECTURE_NOT_FOUND);
        }
    }
}
