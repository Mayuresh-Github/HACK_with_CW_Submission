import 'package:flutter/material.dart';

class StudentDetailsDTO {
  StudentDetailsDTO(
      {@required this.studentName,
      @required this.studentEmail,
      @required this.studentDOB,
      @required this.department,
      this.role,
      this.studentPassword,
      this.teacherEmail,
      this.studentClientId,
      @required this.courseName,
      @required this.enrolledDate,
      @required this.graduationDate});

  String studentName;
  String studentEmail;
  String studentDOB;
  String department;
  String role;
  String studentClientId;
  String studentPassword;
  String teacherEmail;
  String courseName;
  String enrolledDate;
  String graduationDate;

  Map<String, String> toJson() {
    final data = new Map<String, String>();
    data['studentName'] = this.studentName;
    data['studentEmail'] = this.studentEmail;
    data['studentDOB'] = this.studentDOB;
    data['studentPassword'] = this.studentPassword;
    data['department'] = this.department;
    data['courseName'] = this.courseName;
    data['teacherEmail'] = this.teacherEmail;
    data['enrolledDate'] = this.enrolledDate;
    data['graduationDate'] = this.graduationDate;
    return data;
  }

  factory StudentDetailsDTO.fromJson(Map<String, dynamic> json) {
    return StudentDetailsDTO(
      studentName: json['studentName'] ?? "",
      studentEmail: json['studentEmail'] ?? "",
      studentDOB: json['studentDOB'] ?? "",
      department: json['department'] ?? "",
      role: json['role'] ?? "",
      courseName: json['courseName'] ?? "",
      enrolledDate: json['enrolledDate'] ?? "",
      graduationDate: json['graduationDate'] ?? "",
      studentClientId: json['studentClientId'] ?? "",
    );
  }
}
