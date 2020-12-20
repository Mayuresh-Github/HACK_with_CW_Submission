package com.server.attendance.service;

import com.server.attendance.dto.LectureDetailDTO;
import com.server.attendance.entity.LectureDetail;

import java.util.List;

public interface LectureService {

    String createALecture(LectureDetailDTO lectureDetailDTO, String teacherId, String courseId);

    List<LectureDetailDTO> getLectureDetailsFrommCourseId(String courseId);

    LectureDetail findLectureByLectureId(String lectureId);
}
