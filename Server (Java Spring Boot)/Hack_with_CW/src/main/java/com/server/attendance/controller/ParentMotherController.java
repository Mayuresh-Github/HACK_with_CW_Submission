package com.server.attendance.controller;

import com.server.attendance.dto.MotherDetailDTO;
import com.server.attendance.dto.ResponseDTO;
import com.server.attendance.service.ParentMotherService;
import com.server.attendance.util.ErrorMessages;
import com.server.attendance.util.SuccessMessages;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("parentMother")
public class ParentMotherController extends BaseController {

    @Autowired
    ParentMotherService parentMotherService;

    private static final String MOTHER_CREATE_TOKEN = "HELLO_CREATE_MOTHER";

    private static final String MOTHER_LOOKUP_TOKEN = "HELLO_GET_DETAILS_MOTHER";

    @ApiOperation(value = "Create a new Mother as a Parent for Student")
    @PostMapping(path = "/signUpMother", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDTO createNewParentMother(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam String motherName, @RequestParam String motherEmail, @RequestParam String motherPassword, @RequestParam String motherPhone, @RequestParam String studentEmail) {
        if (token.equals(MOTHER_CREATE_TOKEN)) {
            MotherDetailDTO motherDetailDTO = new MotherDetailDTO("Mother", "", motherName, motherEmail, motherPhone, null);
            int result = parentMotherService.createParentMotherOfStudent(motherDetailDTO, motherPassword, studentEmail);
            if (result == 1) {
                return new ResponseDTO(201, HttpStatus.CREATED, SuccessMessages.MOTHER_CREATED);
            } else {
                return new ResponseDTO(500, HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
            }
        } else {
            return new ResponseDTO(401, HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }

    @ApiOperation(value = "Sign-in Parent (Mother)")
    @GetMapping(path = "/signInMother", produces = {MediaType.APPLICATION_JSON_VALUE})
    public MotherDetailDTO signInParentMother(@RequestParam String motherEmail, @RequestParam String motherPassword) {
        return parentMotherService.checkSignInDetails(motherEmail, motherPassword);
    }

    @ApiOperation(value = "Get Parent (Mother) details")
    @GetMapping(path = "/getMotherDetails", produces = {MediaType.APPLICATION_JSON_VALUE})
    public MotherDetailDTO getMotherDetails(@RequestHeader("X-AUTH-TOKEN") String token, String motherEmail, String studentEmail) {
        if (token.equals(MOTHER_LOOKUP_TOKEN)) {
            return parentMotherService.getMotherDetailsFromMotherEmailOrStudentEmail(motherEmail, studentEmail);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }
}
