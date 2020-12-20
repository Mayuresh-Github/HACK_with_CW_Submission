package com.server.attendance.repository;

import com.server.attendance.entity.AttendanceDetail;
import com.server.attendance.entity.LectureDetail;
import com.server.attendance.entity.StudentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<AttendanceDetail, String> {

    AttendanceDetail findByStudentDetailAndLectureDetail(StudentDetail studentDetail, LectureDetail lectureDetail);
}
