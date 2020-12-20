package com.server.attendance.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "question_detail")
public class QuestionDetail extends AuditEntity {

    @Id
    @Column(name = "question_db_id")
    private String questionDBId;

    @Column(name = "question_id")
    private String questionId;

    @Column(name = "question_description", length = 2000)
    private String questionDescription;

    @Column(name = "choice1")
    private String choice1;

    @Column(name = "choice2")
    private String choice2;

    @Column(name = "choice3")
    private String choice3;

    @Column(name = "choice4")
    private String choice4;

    @Column(name = "choice5")
    private String choice5;

    @Column(name = "correct_choice")
    private int correctChoice;

    @ManyToOne
    @JoinColumn(name = "lecture_db_id", referencedColumnName = "lecture_db_id")
    private LectureDetail lectureDetail;
}
