package com.server.attendance.repository;

import com.server.attendance.entity.MotherDetail;
import com.server.attendance.entity.StudentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentMotherRepository extends JpaRepository<MotherDetail, String> {

    MotherDetail findByStudentDetail(StudentDetail studentDetail);

    MotherDetail findByMotherEmail(String motherEmail);

    MotherDetail findByMotherEmailAndMotherPassword(String motherEmail, String motherPassword);
}
