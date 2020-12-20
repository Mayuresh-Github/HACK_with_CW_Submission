package com.server.attendance.repository;

import com.server.attendance.entity.FatherDetail;
import com.server.attendance.entity.StudentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentFatherRepository extends JpaRepository<FatherDetail, String> {

    FatherDetail findByStudentDetail(StudentDetail studentDetail);

    FatherDetail findByFatherEmail(String fatherEmail);

    FatherDetail findByFatherEmailAndFatherPassword(String fatherEmail, String fatherPassword);
}
