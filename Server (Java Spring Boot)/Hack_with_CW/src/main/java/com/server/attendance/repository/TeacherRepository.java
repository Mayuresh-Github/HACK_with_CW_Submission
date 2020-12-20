package com.server.attendance.repository;

import com.server.attendance.entity.TeacherDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherDetail, String> {

    TeacherDetail findByTeacherEmail(String teacherEmail);

    TeacherDetail findByTeacherEmailAndTeacherPassword(String teacherEmail, String teacherPassword);

    TeacherDetail findByTeacherId(String teacherId);

}
