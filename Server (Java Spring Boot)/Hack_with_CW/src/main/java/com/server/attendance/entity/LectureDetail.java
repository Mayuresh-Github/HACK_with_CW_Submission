package com.server.attendance.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "lecture_details")
public class LectureDetail extends AuditEntity {

    @Id
    @Column(name = "lecture_db_id")
    private String lectureDBId;

    @Column(name = "lecture_id")
    private String lectureId;

    @Column(name = "lecture_date")
    private LocalDate lectureDate;

    @Column(name = "lecture_start_time")
    private Timestamp startTime;

    @Column(name = "lecture_end_time")
    private Timestamp endTime;

    @Column(name = "otp")
    private String otp;

    @ManyToOne
    @JoinColumn(name = "course_db_id", nullable = false)
    private CourseDetail courseDetail;
}
