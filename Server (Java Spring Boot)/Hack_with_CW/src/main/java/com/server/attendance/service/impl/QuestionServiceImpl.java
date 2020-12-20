package com.server.attendance.service.impl;

import com.server.attendance.dto.QuestionDetailDTO;
import com.server.attendance.entity.LectureDetail;
import com.server.attendance.entity.QuestionDetail;
import com.server.attendance.repository.QuestionRepository;
import com.server.attendance.service.LectureService;
import com.server.attendance.service.QuestionService;
import com.server.attendance.util.EntityDTOConverter;
import com.server.attendance.util.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    LectureService lectureService;

    @Override
    public int createAQuestionForLecture(QuestionDetailDTO questionDetailDTO, int correctChoice, String lectureId) {
        LectureDetail lectureDetail = lectureService.findLectureByLectureId(lectureId);

        if (lectureDetail != null) {
            QuestionDetail questionDetail = EntityDTOConverter.getQuestionDetailEntityFromDTO(questionDetailDTO);
            questionDetail.setQuestionDBId(UUID.randomUUID().toString());
            questionDetail.setCreatedAt(new Date());
            questionDetail.setCorrectChoice(correctChoice);
            questionDetail.setLectureDetail(lectureDetail);

            try {
                questionRepository.save(questionDetail);
                return 1;
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.CONTACT_ADMIN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.LECTURE_NOT_FOUND);
        }
    }

    @Override
    public List<QuestionDetailDTO> getQuestionsForALecture(String lectureId) {
        LectureDetail lectureDetail = lectureService.findLectureByLectureId(lectureId);

        if (lectureDetail != null) {
            List<QuestionDetail> questionDetailList = questionRepository.getQuestionsByLectureId(lectureDetail);
            List<QuestionDetailDTO> questionDetailDTOList = new ArrayList<>();
            if (questionDetailList != null) {
                for (QuestionDetail q : questionDetailList) {
                    questionDetailDTOList.add(EntityDTOConverter.getQuestionDetailDTOFromEntity(q));
                }
                return questionDetailDTOList;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.QUESTIONS_DOES_NOT_EXIST);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.LECTURE_NOT_FOUND);
        }
    }

    @Override
    public QuestionDetail findByQuestionId(String questionId) {
        QuestionDetail questionDetail = questionRepository.findByQuestionId(questionId);
        if (questionDetail != null) {
            return questionDetail;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.QUESTIONS_DOES_NOT_EXIST);
        }
    }


}
