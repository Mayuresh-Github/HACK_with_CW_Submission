package com.server.attendance.repository;

import com.server.attendance.entity.StudentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentDetail, String> {

    StudentDetail findByStudentEmail(String studentEmail);

    StudentDetail findByStudentEmailAndStudentPassword(String studentEmail, String studentPassword);

    StudentDetail findByStudentClientId(String studentClientId);

}
