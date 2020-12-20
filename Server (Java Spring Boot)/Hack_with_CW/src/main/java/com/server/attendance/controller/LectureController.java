package com.server.attendance.controller;

import com.server.attendance.dto.*;
import com.server.attendance.entity.LectureDetail;
import com.server.attendance.service.HoDService;
import com.server.attendance.service.LectureService;
import com.server.attendance.service.TeacherService;
import com.server.attendance.util.EntityDTOConverter;
import com.server.attendance.util.ErrorMessages;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/lecture")
public class LectureController extends BaseController {

    @Autowired
    LectureService lectureService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    HoDService hoDService;

    private static final String CREATE_LECTURE_TOKEN = "CREATE_A_LECTURE";


    @ApiOperation(value = "Create a new Lecture for a Course")
    @GetMapping(path = "/createLecture", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDTO createANewLecture(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam String lectureId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate lectureDate, @RequestParam Timestamp startTime, @RequestParam Timestamp endTime, @RequestParam String teacherId, @RequestParam String courseId) {
        if (token.equals(CREATE_LECTURE_TOKEN)) {
            if (startTime.before(endTime)) {
                if (lectureDate.compareTo(startTime.toLocalDateTime().toLocalDate()) == 0 && lectureDate.compareTo(endTime.toLocalDateTime().toLocalDate()) == 0) {
                    LectureDetailDTO lectureDetailDTO = new LectureDetailDTO(lectureId, lectureDate, startTime, endTime, null);
                    String result = lectureService.createALecture(lectureDetailDTO, teacherId, courseId);
                    if (result.equals(lectureId)) {
                        return new ResponseDTO(201, HttpStatus.CREATED, result);
                    } else {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
                    }
                } else {
                    throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ErrorMessages.DATES_ERROR);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ErrorMessages.TIMING_ERROR);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }

    @ApiOperation(value = "Get Lecture details")
    @GetMapping(path = "/getLectureDetails", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<LectureDetailDTO> getDetails(@RequestParam String courseId) {
        return lectureService.getLectureDetailsFrommCourseId(courseId);
    }

    @ApiOperation(value = "Get Lecture details")
    @GetMapping(path = "/getOneLectureDetails", produces = {MediaType.APPLICATION_JSON_VALUE})
    public LectureDetailDTO getOneLectureDetails(@RequestParam String lectureId) {
        LectureDetail lectureDetail = lectureService.findLectureByLectureId(lectureId);
        LectureDetailDTO lectureDetailDTO = EntityDTOConverter.getLectureDetailDTOFromEntity(lectureService.findLectureByLectureId(lectureId));
        CourseDetailDTO courseDetailDTO = EntityDTOConverter.getCourseDetailDTOFromEntity(lectureDetail.getCourseDetail());
        lectureDetailDTO.setCourseDetailDTO(courseDetailDTO);
        return lectureDetailDTO;
    }
}
