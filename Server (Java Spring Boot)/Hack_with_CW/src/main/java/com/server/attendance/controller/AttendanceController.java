package com.server.attendance.controller;

import com.server.attendance.dto.AttendanceDetailDTO;
import com.server.attendance.dto.ResponseDTO;
import com.server.attendance.service.AttendanceService;
import com.server.attendance.util.ErrorMessages;
import com.server.attendance.util.SuccessMessages;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;

@RestController
@RequestMapping("/attendance")
public class AttendanceController extends BaseController {

    @Autowired
    AttendanceService attendanceService;

    private static final String ATTENDANCE_TOKEN = "MARK_MY_ATTENDANCE";

    private static final String ATTENDANCE_DETAILS_TOKEN = "GET_ATTENDANCE";

    @ApiOperation(value = "Mark attendance for a Lecture")
    @GetMapping(path = "/markAttendance", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDTO markAttendanceForALecture(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam Timestamp lectureStartTimeByStudent, @RequestParam Timestamp lectureEndTimeByStudent, @RequestParam String coordinatesStart, @RequestParam String coordinatesStart10, @RequestParam String coordinatesStart20, @RequestParam String coordinatesStart30, @RequestParam String coordinatesStart40, @RequestParam String coordinatesStart50, @RequestParam String coordinatesEnd, @RequestParam String studentClientId, @RequestParam String otp, @RequestParam String lectureId, @RequestParam String questionId, @RequestParam int selectedChoice) {
        if (token.equals(ATTENDANCE_TOKEN)) {
            AttendanceDetailDTO attendanceDetailDTO = new AttendanceDetailDTO(lectureStartTimeByStudent, lectureEndTimeByStudent, coordinatesStart, coordinatesStart10, coordinatesStart20, coordinatesStart30, coordinatesStart40, coordinatesStart50, coordinatesEnd);
            int result = attendanceService.markAttendanceForALecture(attendanceDetailDTO, studentClientId, otp, lectureId, questionId, selectedChoice);
            if (result == 1) {
                return new ResponseDTO(201, HttpStatus.CREATED, SuccessMessages.ATTENDANCE_MARKED);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }

    @ApiOperation(value = "Get Details of a Student using Lecture Id")
    @GetMapping(path = "/getAttendanceDetails", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AttendanceDetailDTO getDetails(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam String studentClientId, @RequestParam String lectureId) {
        if (token.equals(ATTENDANCE_DETAILS_TOKEN)) {
            return attendanceService.getDetailOfStudent(studentClientId, lectureId);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
        }
    }
}
