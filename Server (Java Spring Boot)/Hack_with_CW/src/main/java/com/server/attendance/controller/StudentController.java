package com.server.attendance.controller;

import com.server.attendance.dto.*;
import com.server.attendance.service.StudentService;
import com.server.attendance.service.TeacherService;
import com.server.attendance.util.ErrorMessages;
import com.server.attendance.util.SuccessMessages;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

@RestController
@RequestMapping("/student")
public class StudentController extends BaseController {

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    private static final String STUDENT_CREATE_TOKEN = "CREATE_A_STUDENT";

    private static final String STUDENT_DETAILS_TOKEN = "DETAILS_OF_STUDENT";

    private static final String FACE_PREDICTION_DETAILS_TOKEN = "UPDATE_FACE_PREDICTIONS_OF_STUDENT";

    private static final String GET_FACE_PREDICTION_DETAILS_TOKEN = "GET_FACE_PREDICTIONS_OF_STUDENT";

    @ApiOperation(value = "Create a new Student")
    @PostMapping(path = "/signUpStudent", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDTO createNewStudent(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam String studentName, @RequestParam String studentEmail, @RequestParam String studentPassword, @RequestParam String studentDOB, @RequestParam String department, @RequestParam String courseName, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate enrolledDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate graduationDate, @RequestParam String teacherEmail) {

        if (token.equals(STUDENT_CREATE_TOKEN)) {
            if (teacherService.findByTeacherEmail(teacherEmail) != null) {
                StudentDetailDTO studentDetailDTO = new StudentDetailDTO("Student", studentName, "", studentEmail, studentDOB, department, courseName, enrolledDate, graduationDate);
                int result = studentService.createNewStudent(studentDetailDTO, studentPassword);
                if (result == 1) {
                    return new ResponseDTO(201, HttpStatus.CREATED, SuccessMessages.STUDENT_CREATED);
                } else if (result == -1) {
                    return new ResponseDTO(412, HttpStatus.PRECONDITION_FAILED, ErrorMessages.STUDENT_EMAIL_EXISTS);
                } else {
                    return new ResponseDTO(500, HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
                }
            } else {
                return new ResponseDTO(401, HttpStatus.UNAUTHORIZED, ErrorMessages.TEACHER_DOES_NOT_EXISTS);
            }
        } else {
            return new ResponseDTO(401, HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }

    @ApiOperation(value = "Get Student details")
    @GetMapping(path = "/detailsStudent", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StudentDetailDTO getStudent(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam String studentEmail) {

        if (token.equals(STUDENT_DETAILS_TOKEN)) {
            StudentDetailDTO studentDetailDTO = studentService.findStudentByEmail(studentEmail);
            if (studentDetailDTO == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.STUDENT_DOES_NOT_EXISTS);
            } else {
                return studentDetailDTO;
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }

    @ApiOperation(value = "Sign-in Student")
    @GetMapping(path = "/signInStudent", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StudentDetailDTO signInStudent(@RequestParam String studentEmail, @RequestParam String studentPassword) {
        return studentService.signInStudent(studentEmail, studentPassword);
    }

    @ApiOperation(value = "Update Face Predictions for a Student")
    @PostMapping(path = "/updateFacePredictions", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDTO updateFacePredictions(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam String studentClientId, @RequestParam String Face1, @RequestParam String Face2, @RequestParam String Face3, @RequestParam String Face4, @RequestParam String Face5) {
        if (token.equals(FACE_PREDICTION_DETAILS_TOKEN)) {
            int result = studentService.updateFacePredictions(Face1, Face2, Face3, Face4, Face5, studentClientId);
            if (result == 1) {
                return new ResponseDTO(202, HttpStatus.ACCEPTED, SuccessMessages.FACE_PREDICTIONS_UPDATE_SUCCESSFUL);
            } else {
                return new ResponseDTO(500, HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }

    @ApiOperation(value = "Get Face Predictions of a Student")
    @GetMapping(path = "/getFacePredictions", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashedFacePredictionsDTO getFacePredictions(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam String studentClientId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (token.equals(GET_FACE_PREDICTION_DETAILS_TOKEN)) {
            FacePredictionsDTO facePredictionsDTO = studentService.getFacePredictionsOfStudent(studentClientId);
            return new HashedFacePredictionsDTO(facePredictionsDTO, DigestUtils.sha256Hex(facePredictionsDTO.toString()));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }

    @ApiOperation(value = "Get Face Predictions of a Student")
    @GetMapping(path = "/getAttendanceDetails", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDTO getFacePredictions(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam String studentClientId, @RequestParam String lectureId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (token.equals(STUDENT_DETAILS_TOKEN)) {
            AttendanceDetailDTO attendanceDetailDTO = studentService.getAttendanceDetails(studentClientId, lectureId);
            if (attendanceDetailDTO == null) {
                return new ResponseDTO(404, HttpStatus.NOT_FOUND, "Attendance not Found");
            } else {
                return new ResponseDTO(302, HttpStatus.FOUND, "Attendance was marked");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }

}
