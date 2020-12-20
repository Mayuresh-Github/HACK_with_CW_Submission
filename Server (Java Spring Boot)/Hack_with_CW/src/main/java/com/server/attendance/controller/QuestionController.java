package com.server.attendance.controller;

import com.server.attendance.dto.QuestionDetailDTO;
import com.server.attendance.dto.ResponseDTO;
import com.server.attendance.service.QuestionService;
import com.server.attendance.util.ErrorMessages;
import com.server.attendance.util.SuccessMessages;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController extends BaseController {

    @Autowired
    QuestionService questionService;

    private static final String QUESTION_CREATE_TOKEN = "CREATE_A_QUESTION";

    private static final String GET_QUESTION_TOKEN = "GET_QUESTIONS";

    @ApiOperation(value = "Create a new Question for a Lecture")
    @GetMapping(path = "/createQuestion", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDTO createNewQuestion(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam String questionDescription, @RequestParam String questionId, @RequestParam String choice1, @RequestParam String choice2, @RequestParam String choice3, @RequestParam String choice4, @RequestParam String choice5, @RequestParam int correctChoice, @RequestParam String lectureId) {
        if (token.equals(QUESTION_CREATE_TOKEN)) {
            QuestionDetailDTO questionDetailDTO = new QuestionDetailDTO(questionDescription, questionId, choice1, choice2, choice3, choice4, choice5);
            int result = questionService.createAQuestionForLecture(questionDetailDTO, correctChoice, lectureId);
            if (result == 1) {
                return new ResponseDTO(201, HttpStatus.CREATED, SuccessMessages.QUESTION_CREATED);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }

    @ApiOperation(value = "Get Questions for a Particular Lecture")
    @GetMapping(path = "/getQuestions", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuestionDetailDTO> getQuestionsFromLectureId(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam String lectureId) {
        if (token.equals(GET_QUESTION_TOKEN)) {
            return questionService.getQuestionsForALecture(lectureId);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }
}
