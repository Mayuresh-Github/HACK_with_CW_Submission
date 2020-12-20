import 'dart:async';
import 'dart:io';
import 'dart:math' as math;

import 'package:Student_Attendance_App/Face_Recognition/pages/widgets/FacePainter.dart';
import 'package:Student_Attendance_App/Face_Recognition/services/camera.service.dart';
import 'package:Student_Attendance_App/Face_Recognition/services/facenet.service.dart';
import 'package:Student_Attendance_App/Face_Recognition/services/ml_vision_service.dart';
import 'package:Student_Attendance_App/Student/controller/student_controller.dart';
import 'package:Student_Attendance_App/Student/models/FaceDimensionsDetailDTO.dart';
import 'package:Student_Attendance_App/common/models/response_dto.dart';
import 'package:Student_Attendance_App/common/services/shared_pref.dart';
import 'package:camera/camera.dart';
import 'package:firebase_ml_vision/firebase_ml_vision.dart';
import 'package:flushbar/flushbar.dart';
import 'package:flutter/material.dart';
import 'package:path/path.dart';
import 'package:path_provider/path_provider.dart';

class Face_Updates extends StatefulWidget {
  final CameraDescription cameraDescription;

  const Face_Updates({this.cameraDescription});

  @override
  _Face_UpdatesState createState() => _Face_UpdatesState();
}

class _Face_UpdatesState extends State<Face_Updates> {
  String imagePath;
  Face faceDetected;
  Size imageSize;
  int counter = 0;
  var predictedDataListUp = [];
  var predictedDataList = [];
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

  @override
  void initState() {
    super.initState();
    checkFaceUpdates();

    /// starts the camera & start framing faces
    _start();
  }

  Future<bool> checkFaceUpdates() async {
    if (await _sharedPrefs.getFace1() != null) {
      if (await _sharedPrefs.getFace2() != null) {
        if (await _sharedPrefs.getFace3() != null) {
          if (await _sharedPrefs.getFace4() != null) {
            if (await _sharedPrefs.getFace5() != null) {
              FaceDetailsDTO faceDetailsDTO = new FaceDetailsDTO(
                studentClientId: await _sharedPrefs.getUserToken(),
                Face1: await _sharedPrefs.getFace1(),
                Face2: await _sharedPrefs.getFace2(),
                Face3: await _sharedPrefs.getFace3(),
                Face4: await _sharedPrefs.getFace4(),
                Face5: await _sharedPrefs.getFace5(),
              );
              ResponseDTO responseDTO =
                  await studentController.updateFaceDetails(faceDetailsDTO);
              if (responseDTO.status == 202 ||
                  responseDTO.message ==
                      "Face predictions of given Student updated successfully") {
                Flushbar(
                  margin: EdgeInsets.all(8),
                  borderRadius: 8,
                  message: "Face dimensions are already upto-date!!",
                  icon: Icon(
                    Icons.info_outline,
                    size: 20,
                    color: Colors.lightBlue[800],
                  ),
                  duration: Duration(seconds: 3),
                )..show(this.context);
              } else if (responseDTO.status == 500) {
                Flushbar(
                  margin: EdgeInsets.all(8),
                  borderRadius: 8,
                  message:
                      "Some error occurred while uploading details to Server",
                  icon: Icon(
                    Icons.info_outline,
                    size: 20,
                    color: Colors.lightBlue[800],
                  ),
                  duration: Duration(seconds: 3),
                )..show(this.context);
              }
            } else {
              Flushbar(
                margin: EdgeInsets.all(8),
                borderRadius: 8,
                message:
                    "Sorry we don't have your Facial data.\nPress Capture to record.",
                icon: Icon(
                  Icons.info_outline,
                  size: 20,
                  color: Colors.lightBlue[800],
                ),
                duration: Duration(seconds: 3),
              )..show(this.context);
            }
          } else {
            Flushbar(
              margin: EdgeInsets.all(8),
              borderRadius: 8,
              message:
                  "Sorry we don't have your Facial data.\nPress Capture to record.",
              icon: Icon(
                Icons.info_outline,
                size: 20,
                color: Colors.lightBlue[800],
              ),
              duration: Duration(seconds: 3),
            )..show(this.context);
          }
        } else {
          Flushbar(
            margin: EdgeInsets.all(8),
            borderRadius: 8,
            message:
                "Sorry we don't have your Facial data.\nPress Capture to record.",
            icon: Icon(
              Icons.info_outline,
              size: 20,
              color: Colors.lightBlue[800],
            ),
            duration: Duration(seconds: 3),
          )..show(this.context);
        }
      } else {
        Flushbar(
          margin: EdgeInsets.all(8),
          borderRadius: 8,
          message:
              "Sorry we don't have your Facial data.\nPress Capture to record.",
          icon: Icon(
            Icons.info_outline,
            size: 20,
            color: Colors.lightBlue[800],
          ),
          duration: Duration(seconds: 3),
        )..show(this.context);
      }
    } else {
      Flushbar(
        margin: EdgeInsets.all(8),
        borderRadius: 8,
        message:
            "Sorry we don't have your Facial data.\nPress Capture to record.",
        icon: Icon(
          Icons.info_outline,
          size: 20,
          color: Colors.lightBlue[800],
        ),
        duration: Duration(seconds: 3),
      )..show(this.context);
    }
  }

  @override
  void dispose() {
    // Dispose of the controller when the widget is disposed.
    _cameraService.dispose();
    super.dispose();
  }

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

  /// handles the button pressed event
  Future<void> onShot() async {
    print('onShot performed');

    if (faceDetected == null) {
      showDialog(
          context: this.context,
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
        pictureTaked = true;
      });

      return true;
    }
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

          if (faces.length > 0) {
            setState(() {
              faceDetected = faces[0];
            });

            if (_saving) {
              _faceNetService.setCurrentPrediction(image, faceDetected);
              List predictedData = _faceNetService.predictedData;
              if (await _sharedPrefs.getFace1() == null) {
                await _sharedPrefs.saveFace1(predictedData.toString());
                print(_sharedPrefs.getFace1());
              } else if (await _sharedPrefs.getFace2() == null) {
                await _sharedPrefs.saveFace2(predictedData.toString());
                print(_sharedPrefs.getFace2());
              } else if (await _sharedPrefs.getFace3() == null) {
                await _sharedPrefs.saveFace3(predictedData.toString());
                print(_sharedPrefs.getFace3());
              } else if (await _sharedPrefs.getFace4() == null) {
                await _sharedPrefs.saveFace4(predictedData.toString());
                print(_sharedPrefs.getFace4());
              } else if (await _sharedPrefs.getFace5() == null) {
                await _sharedPrefs.saveFace5(predictedData.toString());
                print(_sharedPrefs.getFace5());
              } else {
                Flushbar(
                  margin: EdgeInsets.all(8),
                  borderRadius: 8,
                  message: "Face dimensions are already upto-date.",
                  icon: Icon(
                    Icons.info_outline,
                    size: 20,
                    color: Colors.lightBlue[800],
                  ),
                  duration: Duration(seconds: 3),
                )..show(this.context);
              }
              setState(() {
                _saving = false;
              });
            }
          } else {
            setState(() {
              faceDetected = null;
            });
          }

          _detectingFaces = false;
        } catch (e) {
          print(e);
          _detectingFaces = false;
        }
      }
    });
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
