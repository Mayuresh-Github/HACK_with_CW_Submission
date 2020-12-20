import 'package:Student_Attendance_App/Attendance/screens/face_updates.dart';
import 'package:Student_Attendance_App/Attendance/screens/get_Lecture.dart';
import 'package:Student_Attendance_App/Face_Recognition/pages/db/database.dart';
import 'package:Student_Attendance_App/Face_Recognition/services/facenet.service.dart';
import 'package:Student_Attendance_App/Face_Recognition/services/ml_vision_service.dart';
import 'package:Student_Attendance_App/Login&SignUp/screens/login_page.dart';
import 'package:Student_Attendance_App/Student/controller/student_controller.dart';
import 'package:Student_Attendance_App/Student/models/FaceDimensionsDetailDTO.dart';
import 'package:Student_Attendance_App/common/services/shared_pref.dart';
import 'package:camera/camera.dart';
import 'package:flushbar/flushbar.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  FaceNetService _faceNetService = FaceNetService();
  MLVisionService _mlVisionService = MLVisionService();
  DataBaseService _dataBaseService = DataBaseService();
  StudentController studentController;
  FaceDetailsDTO faceDetailsDTO;
  static final _sharedPrefs = SharedPref.instance;

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

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: GestureDetector(
        onTap: () {
          FocusScope.of(context).requestFocus(new FocusNode());
        },
        child: Scaffold(
          appBar: AppBar(
            title: Text('Attendance App'),
            backgroundColor: Colors.deepPurple[400],
          ),
          drawer: Drawer(
            child: ListView(
              padding: EdgeInsets.zero,
              children: <Widget>[
                SizedBox(
                  height: 95,
                ),
                Container(
                  margin: EdgeInsets.fromLTRB(20.0, 0.0, 0.0, 5.0),
                  child: Text(
                    'MMCOE',
                    style: TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 30.0,
                        color: Colors.deepPurple[300]),
                  ),
                ),
                SizedBox(
                  height: 5,
                ),
                Divider(
                  color: Colors.grey,
                  thickness: 0.4,
                ),
                ListTile(
                  leading: Icon(Icons.home, color: Colors.deepPurple[700]),
                  title: Text('Home'),
                  onTap: () {
                    Navigator.of(context).pop();
                    Navigator.of(context).pop();
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (_) => LoginPage(),
                      ),
                    );
                  },
                ),
                ListTile(
                  leading: Icon(Icons.face, color: Colors.deepPurple[700]),
                  title: Text('Face Dimensions updates'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (_) =>
                            Face_Updates(cameraDescription: cameraDescription),
                      ),
                    );
                  },
                ),
                ListTile(
                  leading:
                      Icon(Icons.control_point, color: Colors.deepPurple[700]),
                  title: Text('Mark Attendance'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (_) => Attendance_Pre(),
                      ),
                    );
                  },
                ),
                ListTile(
                  leading: Icon(Icons.info, color: Colors.deepPurple[700]),
                  title: Text('Get Attendance details'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (_) => LoginPage(),
                      ),
                    );
                  },
                ),
                ListTile(
                    leading:
                        Icon(Icons.exit_to_app, color: Colors.deepPurple[700]),
                    title: Text('Logout'),
                    onTap: () async {
                      await _sharedPrefs.removeUser();
                      await _sharedPrefs.removeIsLoggedIn();
                      bool status = await _sharedPrefs.readIsLoggedIn();
                      if (status) {
                        Flushbar(
                          margin: EdgeInsets.all(8),
                          borderRadius: 8,
                          message:
                              "Error Signing out. Please try later or contact Admin",
                          icon: Icon(
                            Icons.info_outline,
                            size: 20,
                            color: Colors.lightBlue[800],
                          ),
                          duration: Duration(seconds: 4),
                        )..show(context);
                      } else {
                        Navigator.of(context).pop();
                        Navigator.of(context).pop();
                        Navigator.of(context).push(
                          MaterialPageRoute(
                            builder: (_) => LoginPage(),
                          ),
                        );
                      }
                    }),
              ],
            ),
          ),
          body: SingleChildScrollView(
            child: Column(
              children: [
                Container(
                  margin: EdgeInsets.fromLTRB(10, 10, 10, 10),
                  child: Column(
                    children: [
                      ExpansionTile(
                        initiallyExpanded: true,
                        title: Text(
                          "Announcements",
                          style: TextStyle(
                            fontWeight: FontWeight.bold,
                            color: Colors.deepPurple[300],
                            fontSize: 22,
                          ),
                        ),
                        children: <Widget>[
                          Container(
                              margin: EdgeInsets.fromLTRB(15, 15, 10, 15),
                              child: Text(
                                'Lecture of MP is scheduled at tomorrow 10 AM',
                                style: TextStyle(color: Colors.black),
                              )),
                          SizedBox(height: 10),
                          Container(
                              margin: EdgeInsets.fromLTRB(15, 15, 10, 15),
                              child: Text(
                                'Tomorrow CN Lecture is postponed for next week',
                                style: TextStyle(color: Colors.black),
                              )),
                          Divider(
                            thickness: 1.0,
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
