package com.server.attendance.repository;

import com.server.attendance.entity.CourseDetail;
import com.server.attendance.entity.LectureDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LectureRepository extends JpaRepository<LectureDetail, String> {

    @Query(value = "SELECT ld from LectureDetail ld where " +
            "ld.courseDetail=:course_db_id")
    List<LectureDetail> getLecturesByCourseId(@Param("course_db_id") CourseDetail courseDetail);

    LectureDetail findByLectureId(String lectureId);
}
