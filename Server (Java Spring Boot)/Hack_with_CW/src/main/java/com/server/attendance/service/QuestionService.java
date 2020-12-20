package com.server.attendance.service;

import com.server.attendance.dto.QuestionDetailDTO;
import com.server.attendance.entity.QuestionDetail;

import java.util.List;

public interface QuestionService {

    int createAQuestionForLecture(QuestionDetailDTO questionDetailDTO, int correctChoice, String lectureId);

    List<QuestionDetailDTO> getQuestionsForALecture(String lectureId);

    QuestionDetail findByQuestionId(String questionId);
}
