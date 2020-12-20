package com.server.attendance.repository;

import com.server.attendance.entity.LectureDetail;
import com.server.attendance.entity.QuestionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionDetail, String> {

    @Query(value = "SELECT qd from QuestionDetail qd where " +
            "qd.lectureDetail=:lecture")
    List<QuestionDetail> getQuestionsByLectureId(@Param("lecture") LectureDetail lectureDetail);

    QuestionDetail findByQuestionId(String questionId);
}
