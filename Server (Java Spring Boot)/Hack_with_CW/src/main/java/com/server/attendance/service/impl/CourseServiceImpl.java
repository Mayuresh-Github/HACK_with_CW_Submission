package com.server.attendance.service.impl;

import com.server.attendance.dto.CourseDetailDTO;
import com.server.attendance.dto.CustomCourseDetailDTO;
import com.server.attendance.dto.TeacherDetailDTO;
import com.server.attendance.entity.CourseDetail;
import com.server.attendance.entity.TeacherDetail;
import com.server.attendance.repository.CourseRepository;
import com.server.attendance.repository.TeacherRepository;
import com.server.attendance.service.CourseService;
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
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public HttpStatus createNewCourse(CourseDetailDTO courseDetailDTO, String teacherEmail) {
        if (courseRepository.findByCourseId(courseDetailDTO.getCourseId()) == null) {
            CourseDetail courseDetail = EntityDTOConverter.getCourseDetailEntityFromDTO(courseDetailDTO);
            courseDetail.setCourseDBId(UUID.randomUUID().toString());
            courseDetail.setCreatedAt(new Date());
            TeacherDetail teacherDetail = teacherRepository.findByTeacherEmail(teacherEmail);
            if (teacherDetail != null) {
                courseDetail.setTeacherDetail(teacherDetail);
                try {
                    courseRepository.saveAndFlush(courseDetail);
                    return HttpStatus.CREATED;
                } catch (Exception e) {
                    return HttpStatus.INTERNAL_SERVER_ERROR;
                }
            } else {
                return HttpStatus.PRECONDITION_FAILED;
            }
        } else {
            return HttpStatus.PRECONDITION_FAILED;
        }
    }

    @Override
    public CourseDetailDTO getCourseDetails(String courseId) {
        CourseDetail courseDetail = courseRepository.findByCourseId(courseId);
        if (courseDetail != null) {
            return EntityDTOConverter.getCourseDetailDTOFromEntity(courseDetail);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.COURSE_NOT_FOUND);
        }
    }

    @Override
    public CustomCourseDetailDTO getCourseCustomDetails(String courseId) {
        CourseDetail courseDetail = courseRepository.findByCourseId(courseId);
        if (courseDetail != null) {
            TeacherDetailDTO teacherDetailDTO = EntityDTOConverter.getTeacherDetailDTOFromEntity(courseDetail.getTeacherDetail());
            teacherDetailDTO.setHoDDetailDTO(EntityDTOConverter.getHODDetailDTOFromEntity(courseDetail.getTeacherDetail().getHoDDetail()));
            return new CustomCourseDetailDTO(courseDetail.getCourseId(), courseDetail.getCourseName(), courseDetail.getDepartment(), teacherDetailDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.COURSE_NOT_FOUND);
        }
    }

    @Override
    public CourseDetail getCourseDetailsEntity(String courseId) {
        CourseDetail courseDetail = courseRepository.findByCourseId(courseId);
        if (courseDetail != null) {
            return courseDetail;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.COURSE_NOT_FOUND);
        }
    }
}