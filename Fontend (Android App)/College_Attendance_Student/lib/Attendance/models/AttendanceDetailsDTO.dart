import 'package:flutter/material.dart';

class AttendanceDetailsDTO {
  AttendanceDetailsDTO({
    @required this.lectureStartTimeByStudent,
    @required this.lectureEndTimeByStudent,
    @required this.coordinatesStart,
    @required this.coordinatesStart10,
    @required this.coordinatesStart20,
    @required this.coordinatesStart30,
    @required this.coordinatesStart40,
    @required this.coordinatesStart50,
    @required this.coordinatesEnd,
    @required this.otp,
    @required this.studentClientId,
    @required this.lectureId,
    @required this.selectedChoice,
    @required this.questionId,
  });

  String lectureStartTimeByStudent;
  String lectureEndTimeByStudent;
  String coordinatesStart;
  String coordinatesStart10;
  String coordinatesStart20;
  String coordinatesStart30;
  String coordinatesStart40;
  String coordinatesStart50;
  String coordinatesEnd;
  String otp;
  String studentClientId;
  String lectureId;
  String questionId;
  int selectedChoice;

  Map<String, String> toJson() {
    final data = new Map<String, String>();
    data['lectureStartTimeByStudent'] = this.lectureStartTimeByStudent;
    data['lectureEndTimeByStudent'] = this.lectureEndTimeByStudent;
    data['coordinatesStart'] = this.coordinatesStart;
    data['coordinatesStart10'] = this.coordinatesStart10;
    data['coordinatesStart20'] = this.coordinatesStart20;
    data['coordinatesStart30'] = this.coordinatesStart30;
    data['coordinatesStart40'] = this.coordinatesStart40;
    data['coordinatesStart50'] = this.coordinatesStart50;
    data['coordinatesEnd'] = this.coordinatesEnd;
    data['otp'] = this.otp;
    data['studentClientId'] = this.studentClientId;
    data['lectureId'] = this.lectureId;
    data['questionId'] = this.questionId;
    data['selectedChoice'] = this.selectedChoice.toString();
    return data;
  }
}
