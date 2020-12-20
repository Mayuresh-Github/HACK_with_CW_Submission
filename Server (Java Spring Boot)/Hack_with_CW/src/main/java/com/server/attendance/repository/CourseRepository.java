package com.server.attendance.repository;

import com.server.attendance.entity.CourseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseDetail, String> {

    CourseDetail findByCourseId(String courseId);

}
