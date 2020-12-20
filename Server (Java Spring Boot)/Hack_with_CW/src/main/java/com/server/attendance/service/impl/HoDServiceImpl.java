package com.server.attendance.service.impl;

import com.server.attendance.dto.HoDDetailDTO;
import com.server.attendance.entity.HoDDetail;
import com.server.attendance.repository.HodRepository;
import com.server.attendance.service.HoDService;
import com.server.attendance.util.EncryptUtils;
import com.server.attendance.util.EntityDTOConverter;
import com.server.attendance.util.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class HoDServiceImpl implements HoDService {

    @Autowired
    HodRepository hodRepository;

    @Override
    public HttpStatus createHoD(HoDDetailDTO hoDDetailDTO, String passwordToEncrypt) {
        if (hodRepository.findByHodEmail(hoDDetailDTO.getHodEmail()) == null) {
            HoDDetail hoDDetail = EntityDTOConverter.getHoDDetailEntityFromDTO(hoDDetailDTO);
            hoDDetail.setAdmin(true);
            hoDDetail.setHodDBId(UUID.randomUUID().toString());
            hoDDetail.setHodClientId(UUID.randomUUID().toString());
            hoDDetail.setHodPassword(EncryptUtils.encrypt(passwordToEncrypt));
            hoDDetail.setCreatedAt(new Date());
            try {
                hodRepository.saveAndFlush(hoDDetail);
                return HttpStatus.CREATED;
            } catch (Exception e) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            return HttpStatus.PRECONDITION_FAILED;
        }
    }

    @Override
    public HoDDetailDTO checkHODForSignIn(String email, String password) {
        HoDDetail hoDDetail = hodRepository.findByHodEmailAndHodPassword(email, EncryptUtils.encrypt(password));
        if (hoDDetail != null) {
            return EntityDTOConverter.getHODDetailDTOFromEntity(hoDDetail);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_CREDENTIALS);
        }
    }

    @Override
    public HoDDetail findByEmail(String email) {
        return hodRepository.findByHodEmail(email);
    }
}
