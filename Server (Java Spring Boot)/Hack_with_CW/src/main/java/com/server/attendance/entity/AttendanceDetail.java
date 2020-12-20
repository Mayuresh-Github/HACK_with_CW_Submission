package com.server.attendance.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "attendance_students")
public class AttendanceDetail extends AuditEntity {

    @Id
    @Column(name = "db_id")
    private String attendanceDBId;

    @ManyToOne
    @JoinColumn(name = "lecture_db_id", nullable = false)
    private LectureDetail lectureDetail;

    @ManyToOne
    @JoinColumn(name = "student_db_id", nullable = false)
    private StudentDetail studentDetail;

    @Column(name = "score_of_lecture")
    private int score;

    @Column(name = "lecture_start_time_by_student")
    private Timestamp lectureStartTimeByStudent;

    @Column(name = "lecture_end_time_by_student")
    private Timestamp lectureEndTimeByStudent;

    @Column(name = "coordinates_start")
    private String coordinatesStart;

    @Column(name = "coordinates_start_10")
    private String coordinatesStart10;

    @Column(name = "coordinates_start_20")
    private String coordinatesStart20;

    @Column(name = "coordinates_start_30")
    private String coordinatesStart30;

    @Column(name = "coordinates_start_40")
    private String coordinatesStart40;

    @Column(name = "coordinates_start_50")
    private String coordinatesStart50;

    @Column(name = "coordinates_end")
    private String coordinatesEnd;

    @ManyToOne
    @JoinColumn(name = "question_db_id", nullable = false)
    private QuestionDetail questionDetail;
}
