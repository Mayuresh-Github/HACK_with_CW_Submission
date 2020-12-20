package com.server.attendance.service;

import com.server.attendance.dto.HoDDetailDTO;
import com.server.attendance.entity.HoDDetail;
import org.springframework.http.HttpStatus;

public interface HoDService {

    HttpStatus createHoD(HoDDetailDTO hoDDetailDTO, String passwordToEncrypt);

    HoDDetailDTO checkHODForSignIn(String email, String password);

    HoDDetail findByEmail(String email);
}
