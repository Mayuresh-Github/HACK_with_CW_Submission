package com.server.attendance.controller;

import com.server.attendance.dto.HoDDetailDTO;
import com.server.attendance.dto.ResponseDTO;
import com.server.attendance.dto.TeacherDetailDTO;
import com.server.attendance.entity.HoDDetail;
import com.server.attendance.service.HoDService;
import com.server.attendance.service.TeacherService;
import com.server.attendance.util.EntityDTOConverter;
import com.server.attendance.util.ErrorMessages;
import com.server.attendance.util.SuccessMessages;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/teacher")
public class TeacherController extends BaseController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    HoDService hoDService;

    private static final String TEACHER_CREATE_TOKEN = "HELLO_CREATE_TEACHER";

    @ApiOperation(value = "Create new Teacher")
    @PostMapping(path = "/signUpTeacher", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDTO signUpTeacher(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam String teacherId, @RequestParam String teacherName, @RequestParam String teacherEmail, @RequestParam String password, @RequestParam String teacherPhone, @RequestParam String department, @RequestParam String HODEmail) {

        if (token.equals(TEACHER_CREATE_TOKEN)) {

            HoDDetail hoDDetail = hoDService.findByEmail(HODEmail);
            if (hoDDetail != null) {
                HoDDetailDTO hoDDetailDTO = EntityDTOConverter.getHODDetailDTOFromEntity(hoDDetail);
                TeacherDetailDTO teacherDetailDTO = new TeacherDetailDTO(teacherId, "", "Teacher", teacherName, teacherEmail, teacherPhone, department, hoDDetailDTO);
                HttpStatus httpStatus = teacherService.createTeacher(teacherDetailDTO, password);
                if (httpStatus.is2xxSuccessful()) {
                    return new ResponseDTO(201, HttpStatus.CREATED, SuccessMessages.TEACHER_CREATED);
                } else if (httpStatus.compareTo(HttpStatus.PRECONDITION_FAILED) >= 0) {
                    return new ResponseDTO(412, HttpStatus.PRECONDITION_FAILED, ErrorMessages.DUPLICATE_EMAIL);
                } else {
                    return new ResponseDTO(500, HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
                }
            } else {
                return new ResponseDTO(412, HttpStatus.PRECONDITION_FAILED, ErrorMessages.HOD_DOES_NOT_EXISTS);
            }
        } else {
            return new ResponseDTO(401, HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }


    @ApiOperation(value = "Sign-in Teacher")
    @GetMapping(path = "/signInTeacher", produces = {MediaType.APPLICATION_JSON_VALUE})
    public TeacherDetailDTO signInTeacher(@RequestParam String teacherEmail, @RequestParam String password) {
        TeacherDetailDTO teacherDetailDTO = teacherService.checkTeacherForSignIn(teacherEmail, password);
        if (teacherDetailDTO != null) {
            return teacherDetailDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_CREDENTIALS);
        }
    }
}
