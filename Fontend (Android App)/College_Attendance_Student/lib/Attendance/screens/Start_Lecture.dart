import 'package:Student_Attendance_App/Attendance/models/AttendanceDetailsDTO.dart';
import 'package:Student_Attendance_App/Attendance/screens/Otp_page.dart';
import 'package:Student_Attendance_App/Lecture/models/LectureDetailsDTO.dart';
import 'package:Student_Attendance_App/common/services/shared_pref.dart';
import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'package:intl/intl.dart';
import 'package:workmanager/workmanager.dart';

// ignore: camel_case_types
class StartAttendancePage extends StatefulWidget {
  final LectureDetailsDTO lectureDetailsDTO;

  const StartAttendancePage({this.lectureDetailsDTO});

  @override
  _OTPPage_state createState() => _OTPPage_state();
}

// ignore: camel_case_types
class _OTPPage_state extends State<StartAttendancePage> {
  dynamic currentStartTime;
  dynamic EndTime;
  static List coordinates = [];
  String coordinatesStart;
  String coordinatesStart10;
  String coordinatesStart20;
  String coordinatesStart30;
  String coordinatesStart40;
  String coordinatesStart50;
  String coordinatesEnd;

  static final _shared = SharedPref.instance;

  static void callbackDispatcher() async {
    GeolocationStatus geolocationStatus =
        await Geolocator().checkGeolocationPermissionStatus();

    Workmanager.executeTask((task, inputData) async {
      switch (task) {
        case fetchBackground:
          Position userLocation = await Geolocator()
              .getCurrentPosition(desiredAccuracy: LocationAccuracy.high);
          coordinates.add(userLocation);
          print(coordinates.toString());
          break;
      }
      return Future.value(true);
    });
  }

  @override
  void initState() {
    DateFormat dateFormat = DateFormat("yyyy-MM-dd HH:mm:ss");
    currentStartTime = dateFormat.format(DateTime.now());
    super.initState();

    Workmanager.initialize(
      callbackDispatcher,
      isInDebugMode: true,
    );

    Workmanager.registerPeriodicTask(
      "1",
      fetchBackground,
      frequency: Duration(minutes: 1),
    );
  }

  static const fetchBackground = "fetchBackground";

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: GestureDetector(
        onTap: () {
          FocusScope.of(context).requestFocus(new FocusNode());
        },
        child: Scaffold(
          appBar: AppBar(
            title: Text('Lecture time'),
            backgroundColor: Colors.deepPurple[400],
          ),
          body: Center(
            child: SingleChildScrollView(
              child: Column(
                children: [
                  Text(
                    'Lecture start time: \n' + currentStartTime,
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 15,
                    ),
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  FlatButton(
                    color: Colors.deepPurple[500],
                    padding: EdgeInsets.all(10.0),
                    onPressed: () async {
                      DateFormat dateFormat = DateFormat("yyyy-MM-dd HH:mm:ss");
                      EndTime = dateFormat.format(DateTime.now());

                      AttendanceDetailsDTO attendaceDTO =
                          new AttendanceDetailsDTO(
                              lectureStartTimeByStudent: currentStartTime,
                              lectureEndTimeByStudent: EndTime,
                              coordinatesStart: coordinatesStart,
                              coordinatesStart10: coordinatesStart10,
                              coordinatesStart20: coordinatesStart20,
                              coordinatesStart30: coordinatesStart30,
                              coordinatesStart40: coordinatesStart40,
                              coordinatesStart50: coordinatesStart50,
                              coordinatesEnd: coordinatesEnd,
                              otp: null,
                              studentClientId: await _shared.getUserToken(),
                              lectureId: widget.lectureDetailsDTO.lectureId,
                              selectedChoice: 1,
                              questionId: "QUES_SDL_10_1");
                      Navigator.pop(context);
                      Navigator.of(context).push(
                        MaterialPageRoute(
                          builder: (_) => OTPPage(
                            attendanceDetailsDTO: attendaceDTO,
                          ),
                        ),
                      );
                    },
                    child: Text('End Lecture',
                        style: TextStyle(color: Colors.white)),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
