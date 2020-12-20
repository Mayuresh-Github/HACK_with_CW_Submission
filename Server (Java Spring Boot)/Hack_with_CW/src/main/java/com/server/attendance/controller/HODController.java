package com.server.attendance.controller;

import com.server.attendance.dto.HoDDetailDTO;
import com.server.attendance.dto.ResponseDTO;
import com.server.attendance.service.HoDService;
import com.server.attendance.util.ErrorMessages;
import com.server.attendance.util.SuccessMessages;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/hod")
public class HODController extends BaseController {

    @Autowired
    private HoDService hoDService;

    private static final String HOD_CREATE_TOKEN = "HELLO_CREATE_HOD";

    @ApiOperation(value = "Create a new HOD")
    @PostMapping(path = "/signUpHOD", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDTO signUpHOD(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam String hodId, @RequestParam String hodName, @RequestParam String hodEmail, @RequestParam String password, @RequestParam String hodPhone, @RequestParam String department) {
        if (token.equals(HOD_CREATE_TOKEN)) {
            HoDDetailDTO hoDDetailDTO = new HoDDetailDTO(hodId, "","HOD", hodName, hodEmail, hodPhone, department);
            HttpStatus httpStatus = hoDService.createHoD(hoDDetailDTO, password);
            if (httpStatus.is2xxSuccessful()) {
                return new ResponseDTO(201, HttpStatus.CREATED, SuccessMessages.HOD_CREATED);
            } else if (httpStatus.compareTo(HttpStatus.PRECONDITION_FAILED) >= 0) {
                return new ResponseDTO(412, HttpStatus.PRECONDITION_FAILED, ErrorMessages.HOD_EMAIL_EXISTS);
            } else {
                return new ResponseDTO(500, HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
            }
        } else {
            return new ResponseDTO(401, HttpStatus.UNAUTHORIZED, ErrorMessages.UNAUTHENTICATED);
        }
    }

    @ApiOperation(value = "Sign-in HOD")
    @GetMapping(path = "/signInHOD", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HoDDetailDTO signInHOD(@RequestParam String hodEmail, @RequestParam String password) {
        HoDDetailDTO hoDDetailDTO = hoDService.checkHODForSignIn(hodEmail, password);
        if (hoDDetailDTO != null) {
            return hoDDetailDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_CREDENTIALS);
        }
    }
}
