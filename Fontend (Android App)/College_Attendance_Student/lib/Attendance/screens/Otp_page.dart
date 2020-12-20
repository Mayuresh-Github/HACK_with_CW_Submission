import 'package:Student_Attendance_App/Attendance/controller/attendance_controller.dart';
import 'package:Student_Attendance_App/Attendance/models/AttendanceDetailsDTO.dart';
import 'package:Student_Attendance_App/HomePage/homepage.dart';
import 'package:Student_Attendance_App/common/models/response_dto.dart';
import 'package:flushbar/flushbar.dart';
import 'package:flutter/material.dart';

class OTPPage extends StatefulWidget {
  final AttendanceDetailsDTO attendanceDetailsDTO;

  const OTPPage({this.attendanceDetailsDTO});

  @override
  _OTPPageState createState() => _OTPPageState();
}

class _OTPPageState extends State<OTPPage> {
  String Otp;
  String EndTime;
  AttendanceController attendanceController = new AttendanceController();

  @override
  void initState() {
    super.initState();
  }

  void markClientAttendance(AttendanceDetailsDTO attendanceDetailsDTO) async {
    widget.attendanceDetailsDTO.lectureId = "LEC_SDL_10";
    widget.attendanceDetailsDTO.otp = Otp;
    ResponseDTO responseDTO =
        await attendanceController.markAttendance(widget.attendanceDetailsDTO);

    if (responseDTO.status == 201 ||
        responseDTO.message ==
            'Attendance for given lecture marked successfully') {
      Navigator.pop(context);
      Navigator.of(context).push(
        MaterialPageRoute(
          builder: (_) => HomePage(),
        ),
      );
      Flushbar(
        margin: EdgeInsets.all(8),
        borderRadius: 8,
        message: "Attendance marked successfully!!",
        icon: Icon(
          Icons.info_outline,
          size: 20,
          color: Colors.lightBlue[800],
        ),
        duration: Duration(seconds: 3),
      )..show(context);
    } else {
      Flushbar(
        margin: EdgeInsets.all(8),
        borderRadius: 8,
        message: "Error marking attendance!!",
        icon: Icon(
          Icons.info_outline,
          size: 20,
          color: Colors.lightBlue[800],
        ),
        duration: Duration(seconds: 3),
      )..show(context);
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: GestureDetector(
        onTap: () {
          FocusScope.of(context).requestFocus(new FocusNode());
        },
        child: Scaffold(
          appBar: AppBar(
            title: Text('OTP Page'),
            backgroundColor: Colors.deepPurple[400],
          ),
          body: SingleChildScrollView(
            child: Column(
              children: [
                Container(
                  margin: EdgeInsets.fromLTRB(35, 25, 35, 10),
                  child: Center(
                    child: TextField(
                      onChanged: (value) {
                        Otp = value;
                      },
                      decoration: InputDecoration(
                        counterText: "",
                        focusedBorder: OutlineInputBorder(
                          borderSide: BorderSide(color: Colors.deepPurple),
                          borderRadius: BorderRadius.circular(20.0),
                        ),
                        enabledBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10.0),
                        ),
                        labelText: 'OTP',
                        hintText: 'Enter OTP given by Teacher',
                        focusColor: Colors.deepPurple[500],
                      ),
                      maxLines: 1,
                      maxLength: 100,
                      textAlign: TextAlign.center,
                    ),
                  ),
                ),
                FlatButton(
                  padding: EdgeInsets.all(10),
                  color: Colors.deepPurple[200],
                  onPressed: () {
                    markClientAttendance(widget.attendanceDetailsDTO);
                  },
                  child: Text('Mark Attendance'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
