import 'package:Student_Attendance_App/Face_Recognition/pages/db/database.dart';
import 'package:Student_Attendance_App/Face_Recognition/screens/attendace_marking.dart';
import 'package:Student_Attendance_App/Face_Recognition/services/facenet.service.dart';
import 'package:Student_Attendance_App/Face_Recognition/services/ml_vision_service.dart';
import 'package:Student_Attendance_App/Lecture/controller/lecture_controller.dart';
import 'package:Student_Attendance_App/Lecture/models/LectureDetailsDTO.dart';
import 'package:camera/camera.dart';
import 'package:flushbar/flushbar.dart';
import 'package:flutter/material.dart';

// ignore: camel_case_types
class Attendance_Pre extends StatefulWidget {
  @override
  _Attendance_PreState createState() => _Attendance_PreState();
}

// ignore: camel_case_types
class _Attendance_PreState extends State<Attendance_Pre> {
  FaceNetService _faceNetService = FaceNetService();
  MLVisionService _mlVisionService = MLVisionService();
  DataBaseService _dataBaseService = DataBaseService();

  CameraDescription cameraDescription;
  bool loading = false;

  @override
  void initState() {
    super.initState();
    _startUp();
  }

  /// 1 Obtain a list of the available cameras on the device.
  /// 2 loads the face net model
  _startUp() async {
    _setLoading(true);

    List<CameraDescription> cameras = await availableCameras();

    /// takes the front camera
    cameraDescription = cameras.firstWhere(
      (CameraDescription camera) =>
          camera.lensDirection == CameraLensDirection.front,
    );

    // start the services
    await _faceNetService.loadModel();
    await _dataBaseService.loadDB();
    _mlVisionService.initialize();

    _setLoading(false);
  }

  // shows or hides the circular progress indicator
  _setLoading(bool value) {
    setState(() {
      loading = value;
    });
  }

  String lectureId;
  LectureController lectureController = new LectureController();

  void getLectureDetails(String courseId) async {
    LectureDetailsDTO lectureDetailsDTO =
        await lectureController.getLectureDetails(lectureId);
    if (lectureDetailsDTO.status == 200) {
      Flushbar(
        margin: EdgeInsets.all(8),
        borderRadius: 8,
        message: "Check Lecture Id again",
        icon: Icon(
          Icons.info_outline,
          size: 20,
          color: Colors.lightBlue[800],
        ),
        duration: Duration(seconds: 3),
      )..show(context);
    } else {
      Navigator.pop(context);
      Navigator.of(context).push(
        MaterialPageRoute(
          builder: (_) => Face_Recog(
            lectureDetailsDTO: lectureDetailsDTO,
            cameraDescription: cameraDescription,
          ),
        ),
      );
      Flushbar(
        margin: EdgeInsets.all(8),
        borderRadius: 8,
        message: "Got the details successfully!",
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
            title: Text('Lecture Details'),
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
                        lectureId = value;
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
                        labelText: 'Lecture Id',
                        hintText: 'Enter lecture-id',
                        focusColor: Colors.deepPurple[500],
                      ),
                      maxLines: 1,
                      maxLength: 100,
                      textAlign: TextAlign.center,
                    ),
                  ),
                ),
                !loading
                    ? FlatButton(
                        padding: EdgeInsets.all(10),
                        color: Colors.deepPurple[200],
                        onPressed: () {
                          getLectureDetails(lectureId);
                        },
                        child: Text('Get Details'),
                      )
                    : Center(
                        child: CircularProgressIndicator(),
                      ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
