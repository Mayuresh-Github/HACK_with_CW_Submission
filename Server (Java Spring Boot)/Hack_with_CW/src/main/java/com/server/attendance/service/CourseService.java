package com.server.attendance.service;

import com.server.attendance.dto.CourseDetailDTO;
import com.server.attendance.dto.CustomCourseDetailDTO;
import com.server.attendance.entity.CourseDetail;
import org.springframework.http.HttpStatus;

public interface CourseService {

    HttpStatus createNewCourse(CourseDetailDTO courseDetailDTO, String teacherEmail);

    CourseDetailDTO getCourseDetails(String courseId);

    CustomCourseDetailDTO getCourseCustomDetails(String courseId);

    CourseDetail getCourseDetailsEntity(String courseId);
}
