package com.server.attendance.controller;

import com.server.attendance.dto.CourseDetailDTO;
import com.server.attendance.dto.CustomCourseDetailDTO;
import com.server.attendance.dto.ResponseDTO;
import com.server.attendance.service.CourseService;
import com.server.attendance.util.ErrorMessages;
import com.server.attendance.util.SuccessMessages;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {

    @Autowired
    CourseService courseService;

    private static final String COURSE_CREATE_TOKEN = "HELLO_CREATE_COURSE";

    @ApiOperation(value = "Create a new Course")
    @PostMapping(path = "/createNewCourse", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDTO createCourse(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam String courseId, @RequestParam String courseName, @RequestParam String department, @RequestParam String teacherEmail) {
        if (token.equals(COURSE_CREATE_TOKEN)) {

            CourseDetailDTO courseDetailDTO = new CourseDetailDTO(courseId, courseName, department, null);
            HttpStatus httpStatus = courseService.createNewCourse(courseDetailDTO, teacherEmail);
            if (httpStatus.is2xxSuccessful()) {
                return new ResponseDTO(201, HttpStatus.CREATED, SuccessMessages.COURSE_CREATED);
            } else if (httpStatus.compareTo(HttpStatus.PRECONDITION_FAILED) >= 0) {
                return new ResponseDTO(412, HttpStatus.PRECONDITION_FAILED, ErrorMessages.COURSE_ERROR);
            } else {
                return new ResponseDTO(500, HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
            }
        } else {
            return new ResponseDTO(401, HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }

    @ApiOperation(value = "Get Course details")
    @GetMapping(path = "/getCourseDetails", produces = {MediaType.APPLICATION_JSON_VALUE})
    public CustomCourseDetailDTO getCourse(@RequestParam String courseId) {
        return courseService.getCourseCustomDetails(courseId);
    }
}
