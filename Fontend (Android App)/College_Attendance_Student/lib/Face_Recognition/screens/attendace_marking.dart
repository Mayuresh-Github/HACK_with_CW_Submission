import 'dart:async';
import 'dart:io';
import 'dart:math' as math;

import 'package:Student_Attendance_App/Attendance/screens/get_Lecture.dart';
import 'package:Student_Attendance_App/Attendance/screens/Start_Lecture.dart';
import 'package:Student_Attendance_App/Face_Recognition/pages/widgets/FacePainter.dart';
import 'package:Student_Attendance_App/Face_Recognition/services/camera.service.dart';
import 'package:Student_Attendance_App/Face_Recognition/services/facenet.service.dart';
import 'package:Student_Attendance_App/Face_Recognition/services/ml_vision_service.dart';
import 'package:Student_Attendance_App/Lecture/models/LectureDetailsDTO.dart';
import 'package:Student_Attendance_App/Student/controller/student_controller.dart';
import 'package:Student_Attendance_App/Student/models/FaceDimensionsDetailDTO.dart';
import 'package:Student_Attendance_App/common/services/shared_pref.dart';
import 'package:camera/camera.dart';
import 'package:firebase_ml_vision/firebase_ml_vision.dart';
import 'package:flushbar/flushbar.dart';
import 'package:flutter/material.dart';
import 'package:path/path.dart' show join;
import 'package:path_provider/path_provider.dart';

class Face_Recog extends StatefulWidget {
  final LectureDetailsDTO lectureDetailsDTO;
  final CameraDescription cameraDescription;

  const Face_Recog({@required this.cameraDescription, this.lectureDetailsDTO});

  @override
  _Face_RecogState createState() => _Face_RecogState();
}

class _Face_RecogState extends State<Face_Recog> {
  String imagePath;
  Face faceDetected;
  Size imageSize;
  int counter = 0;
  var predictedDataList = [];
  var userDataList = [];
  static final _sharedPrefs = SharedPref.instance;

  StudentController studentController = new StudentController();

  bool _detectingFaces = false;
  bool pictureTaked = false;

  Future _initializeControllerFuture;
  bool cameraInitializated = false;

  // switchs when the user press the camera
  bool _saving = false;
  bool _bottomSheetVisible = false;

  // service injection
  MLVisionService _mlVisionService = MLVisionService();
  CameraService _cameraService = CameraService();
  FaceNetService _faceNetService = FaceNetService();
  FaceDetailsDTO faceDetailsDTO = new FaceDetailsDTO();

  @override
  void initState() {
    super.initState();
    getFaceUpdates();

    /// starts the camera & start framing faces
    _start();
  }

  void getFaceUpdates() async {
    FaceDetailsCheck faceDetailsCheck = new FaceDetailsCheck(
      studentClientId: await _sharedPrefs.getUserToken(),
    );
    faceDetailsDTO = await studentController.getFaceDetails(faceDetailsCheck);
    userDataList.add(faceDetailsDTO.facePredictionsDTO.Face1);
    userDataList.add(faceDetailsDTO.facePredictionsDTO.Face2);
    userDataList.add(faceDetailsDTO.facePredictionsDTO.Face3);
    userDataList.add(faceDetailsDTO.facePredictionsDTO.Face4);
    userDataList.add(faceDetailsDTO.facePredictionsDTO.Face5);
  }

  @override
  void dispose() {
    // Dispose of the controller when the widget is disposed.
    _cameraService.dispose();
    super.dispose();
  }

  /// starts the camera & start framing faces
  /// starts the camera & start framing faces
  _start() async {
    _initializeControllerFuture =
        _cameraService.startService(widget.cameraDescription);
    await _initializeControllerFuture;

    setState(() {
      cameraInitializated = true;
    });

    _frameFaces();
  }

  /// draws rectangles when detects faces
  _frameFaces() {
    imageSize = _cameraService.getImageSize();

    _cameraService.cameraController.startImageStream((image) async {
      if (_cameraService.cameraController != null) {
        // if its currently busy, avoids overprocessing
        if (_detectingFaces) return;

        _detectingFaces = true;

        try {
          List<Face> faces = await _mlVisionService.getFacesFromImage(image);

          if (faces != null) {
            if (faces.length > 0) {
              // preprocessing the image
              setState(() {
                faceDetected = faces[0];
              });

              if (_saving) {
                _saving = false;
                _faceNetService.setCurrentPrediction(image, faceDetected);
                predictedDataList = _faceNetService.predictedData;
                String output = await _faceNetService.searchResult(
                    predictedDataList, faceDetailsDTO);
                if (output == null) {
                  Navigator.of(context).pop();
                  Navigator.of(context).push(
                    MaterialPageRoute(
                      builder: (_) => Attendance_Pre(),
                    ),
                  );
                  Flushbar(
                    margin: EdgeInsets.all(8),
                    borderRadius: 8,
                    message:
                        "Sorry your Face Details don't match our records.\nTry again!",
                    icon: Icon(
                      Icons.info_outline,
                      size: 20,
                      color: Colors.lightBlue[800],
                    ),
                    duration: Duration(seconds: 3),
                  )..show(context);
                } else if (output != null) {
                  Navigator.of(context).pop();
                  Navigator.of(context).push(
                    MaterialPageRoute(
                      builder: (_) => StartAttendancePage(
                        lectureDetailsDTO: widget.lectureDetailsDTO,
                      ),
                    ),
                  );
                  Flushbar(
                    margin: EdgeInsets.all(8),
                    borderRadius: 8,
                    message: "Authenticated Successfully!",
                    icon: Icon(
                      Icons.info_outline,
                      size: 20,
                      color: Colors.lightBlue[800],
                    ),
                    duration: Duration(seconds: 3),
                  )..show(context);
                }
              }
            } else {
              setState(() {
                faceDetected = null;
              });
            }
          }

          _detectingFaces = false;
        } catch (e) {
          print(e);
          _detectingFaces = false;
        }
      }
    });
  }

  /// handles the button pressed event
  Future<void> onShot() async {
    if (faceDetected == null) {
      showDialog(
          context: context,
          child: AlertDialog(
            content: Text('No face detected!'),
          ));

      return false;
    } else {
      imagePath =
          join((await getTemporaryDirectory()).path, '${DateTime.now()}.png');

      _saving = true;

      await Future.delayed(Duration(milliseconds: 500));
      await _cameraService.cameraController.stopImageStream();
      await Future.delayed(Duration(milliseconds: 200));
      await _cameraService.takePicture(imagePath);

      setState(() {
        _bottomSheetVisible = true;
        pictureTaked = true;
      });

      return true;
    }
  }

  @override
  Widget build(BuildContext context) {
    final double mirror = math.pi;
    final width = MediaQuery.of(context).size.width;
    return MaterialApp(
      home: GestureDetector(
        onTap: () {
          FocusScope.of(context).requestFocus(new FocusNode());
        },
        child: Scaffold(
            appBar: AppBar(
              title: Text('Face Authentication'),
              backgroundColor: Colors.deepPurple[400],
            ),
            body: SingleChildScrollView(
              child: Column(
                children: [
                  FutureBuilder<void>(
                    future: _initializeControllerFuture,
                    builder: (context, snapshot) {
                      if (snapshot.connectionState == ConnectionState.done) {
                        if (pictureTaked) {
                          return Container(
                            width: width,
                            child: Transform(
                                alignment: Alignment.center,
                                child: Image.file(File(imagePath)),
                                transform: Matrix4.rotationY(mirror)),
                          );
                        } else {
                          return Transform.scale(
                            scale: 1.0,
                            child: AspectRatio(
                              aspectRatio:
                                  MediaQuery.of(context).size.aspectRatio,
                              child: OverflowBox(
                                alignment: Alignment.center,
                                child: FittedBox(
                                  fit: BoxFit.fitHeight,
                                  child: Container(
                                    width: width,
                                    height: width /
                                        _cameraService
                                            .cameraController.value.aspectRatio,
                                    child: Stack(
                                      fit: StackFit.expand,
                                      children: <Widget>[
                                        CameraPreview(
                                            _cameraService.cameraController),
                                        CustomPaint(
                                          painter: FacePainter(
                                              face: faceDetected,
                                              imageSize: imageSize),
                                        ),
                                      ],
                                    ),
                                  ),
                                ),
                              ),
                            ),
                          );
                        }
                      } else {
                        return Center(child: CircularProgressIndicator());
                      }
                    },
                  ),
                ],
              ),
            ),
            floatingActionButtonLocation:
                FloatingActionButtonLocation.centerFloat,
            floatingActionButton: !_bottomSheetVisible
                ? FlatButton(
                    color: Colors.grey,
                    padding: EdgeInsets.fromLTRB(15, 10, 15, 10),
                    onPressed: () async {
                      try {
                        await _initializeControllerFuture;
                        onShot();
                        // Ensure that the camera is initialized.
                        // onShot event (takes the image and predict output)
                      } catch (e) {
                        Flushbar(
                          margin: EdgeInsets.all(8),
                          borderRadius: 8,
                          message: e.toString(),
                          icon: Icon(
                            Icons.info_outline,
                            size: 20,
                            color: Colors.lightBlue[800],
                          ),
                          duration: Duration(seconds: 3),
                        )..show(context);
                      }
                    },
                    child: Text("Capture"),
                  )
                : Container()),
      ),
    );
  }
}
