import 'package:Student_Attendance_App/Teacher/models/TeacherDetailsDTO.dart';

class LectureDetailsDTO {
  LectureDetailsDTO(
      {this.lectureId,
      this.startTime,
      this.lectureDate,
      this.endTime,
      this.status,
      this.courseDetailsDTO});

  String lectureId;
  int status;
  String lectureDate;
  String startTime;
  String endTime;
  CourseDetailsDTO courseDetailsDTO;

  Map<String, String> toJson() {
    final data = new Map<String, String>();
    data['lectureId'] = this.lectureId;
    data['lectureDate'] = this.lectureDate;
    data['startTime'] = this.startTime;
    data['endTime'] = this.endTime;
    return data;
  }

  factory LectureDetailsDTO.fromJson(Map<String, dynamic> json) {
    return LectureDetailsDTO(
      lectureId: json['lectureId'] ?? "",
      status: json['status'] ?? "",
      lectureDate: json['lectureDate'] ?? "",
      startTime: json['startTime'] ?? "",
      endTime: json['endTime'] ?? "",
    );
  }

  factory LectureDetailsDTO.fromJsonCustom(Map<String, dynamic> json) {
    return LectureDetailsDTO(
      courseDetailsDTO:
          CourseDetailsDTO.fromJson(json['courseDetailDTO']) ?? "",
      lectureId: json['lectureId'] ?? "",
      lectureDate: json['lectureDate'] ?? "",
      startTime: json['startTime'] ?? "",
      endTime: json['endTime'] ?? "",
    );
  }
}

class CourseDetailsDTO {
  CourseDetailsDTO(
      {this.courseId,
      this.courseName,
      this.department,
      this.teacherDetailsDTO});

  String courseId;
  String courseName;
  String department;
  TeacherDetailsDTO teacherDetailsDTO;

  Map<String, String> toJson() {
    final data = new Map<String, String>();
    data['courseId'] = this.courseId;
    data['courseName'] = this.courseName;
    data['department'] = this.department;
    return data;
  }

  factory CourseDetailsDTO.fromJson(Map<String, dynamic> json) {
    return CourseDetailsDTO(
      courseId: json['courseId'] ?? "",
      courseName: json['courseName'] ?? "",
      department: json['department'] ?? "",
    );
  }

  factory CourseDetailsDTO.fromJsonCustom(Map<String, dynamic> json) {
    return CourseDetailsDTO(
      teacherDetailsDTO: TeacherDetailsDTO.fromJson(json['teacherDetailDTO']),
      courseId: json['courseId'] ?? "",
      courseName: json['courseName'] ?? "",
      department: json['department'] ?? "",
    );
  }
}

class Lecture {
  Lecture({
    this.lectureId,
  });

  String lectureId;

  Map<String, String> toJson() {
    final data = new Map<String, String>();
    data['lectureId'] = this.lectureId;
    return data;
  }
}

class Error {
  Error({
    this.status,
    this.message,
  });

  String status;
  String message;

  factory Error.fromJson(Map<String, dynamic> json) {
    return Error(
      status: json['status'] ?? "",
      message: json['message'] ?? "",
    );
  }
}
