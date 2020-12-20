package com.server.attendance.controller;

import com.server.attendance.dto.FatherDetailDTO;
import com.server.attendance.dto.ResponseDTO;
import com.server.attendance.service.ParentFatherService;
import com.server.attendance.util.ErrorMessages;
import com.server.attendance.util.SuccessMessages;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/parentFather")
public class ParentFatherController extends BaseController {

    @Autowired
    ParentFatherService parentFatherService;

    private static final String FATHER_CREATE_TOKEN = "HELLO_CREATE_FATHER";

    private static final String FATHER_LOOKUP_TOKEN = "HELLO_GET_DETAILS_FATHER";

    @ApiOperation(value = "Create a new Father as a Parent for Student")
    @PostMapping(path = "/signUpFather", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDTO createNewParentFather(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam String fatherName, @RequestParam String fatherEmail, @RequestParam String fatherPassword, @RequestParam String fatherPhone, @RequestParam String studentEmail) {
        if (token.equals(FATHER_CREATE_TOKEN)) {
            FatherDetailDTO fatherDetailDTO = new FatherDetailDTO("", "Father", fatherName, fatherEmail, fatherPhone, null);
            int result = parentFatherService.createParentFatherOfStudent(fatherDetailDTO, fatherPassword, studentEmail);
            if (result == 1) {
                return new ResponseDTO(201, HttpStatus.CREATED, SuccessMessages.FATHER_CREATED);
            } else {
                return new ResponseDTO(500, HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
            }
        } else {
            return new ResponseDTO(401, HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }

    @ApiOperation(value = "Sign-in Parent (Father)")
    @GetMapping(path = "/signInFather", produces = {MediaType.APPLICATION_JSON_VALUE})
    public FatherDetailDTO signInParentFather(@RequestParam String fatherEmail, @RequestParam String fatherPassword) {
        return parentFatherService.checkSignInDetails(fatherEmail, fatherPassword);
    }

    @ApiOperation(value = "Get Parent (Father) details")
    @GetMapping(path = "/getFatherDetails", produces = {MediaType.APPLICATION_JSON_VALUE})
    public FatherDetailDTO getFatherDetails(@RequestHeader("X-AUTH-TOKEN") String token, String fatherEmail, String studentEmail) {
        if (token.equals(FATHER_LOOKUP_TOKEN)) {
            return parentFatherService.getFatherDetailsFromFatherEmailOrStudentEmail(fatherEmail, studentEmail);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }
}
