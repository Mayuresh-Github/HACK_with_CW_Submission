import 'package:flutter/material.dart';

class TeacherDetailsDTO {
  TeacherDetailsDTO(
      {@required this.teacherId,
      this.role,
      @required this.teacherName,
      @required this.teacherEmail,
      @required this.teacherPhone,
      @required this.department,
      this.HODEmail});

  String teacherId;
  String role;
  String teacherName;
  String teacherEmail;
  String teacherPhone;
  String department;
  String HODEmail;

  Map<String, String> toJson() {
    final data = new Map<String, String>();
    data['teacherId'] = this.teacherId;
    data['teacherName'] = this.teacherName;
    data['teacherEmail'] = this.teacherEmail;
    data['teacherPhone'] = this.teacherPhone;
    data['department'] = this.department;
    data['HODEmail'] = this.HODEmail;
    return data;
  }

  factory TeacherDetailsDTO.fromJson(Map<String, dynamic> json) {
    return TeacherDetailsDTO(
      teacherId: json['teacherId'] ?? "",
      teacherName: json['teacherName'] ?? "",
      teacherEmail: json['teacherEmail'] ?? "",
      teacherPhone: json['teacherPhone'] ?? "",
      role: json['role'] ?? "",
      department: json['department'] ?? "",
    );
  }
}
