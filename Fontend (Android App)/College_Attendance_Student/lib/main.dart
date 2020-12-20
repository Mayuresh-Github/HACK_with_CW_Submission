import 'package:Student_Attendance_App/Attendance/controller/attendance_controller.dart';
import 'package:Student_Attendance_App/HomePage/homepage.dart';
import 'package:Student_Attendance_App/Login&SignUp/screens/login_page.dart';
import 'package:Student_Attendance_App/Student/controller/student_controller.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:splashscreen/splashscreen.dart';

import 'controller/login_controller.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();

  final homePage = HomePage();
  final loginPage = LoginPage();

  final loginController = LoginController();
  bool isAuthorized = await loginController.isUserAuthorized();
  Widget defaultHome = isAuthorized ? homePage : loginPage;

  runApp(
    MultiProvider(
      providers: [
        Provider<StudentController>(create: (_) => StudentController()),
        Provider<AttendanceController>(create: (_) => AttendanceController())
      ],
      child: MyApp(startupPage: defaultHome),
    ),
  );
}

class MyApp extends StatefulWidget {
  final Widget startupPage;

  const MyApp({this.startupPage});

  @override
  MyAppState createState() => MyAppState(startupPage);
}

class MyAppState extends State<MyApp> {
  final Widget startupPage;

  MyAppState(this.startupPage);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Attendance App',
      home: Splash2(startupPage: startupPage),
    );
  }
}

class Splash2 extends StatelessWidget {
  final Widget startupPage;

  const Splash2({this.startupPage});

  @override
  Widget build(BuildContext context) {
    return SplashScreen(
      seconds: 3,
      navigateAfterSeconds: new SecondScreen(startupPage: startupPage),
      title: new Text(
        'Attendance App',
        textScaleFactor: 2,
      ),
      image: new Image.asset('assets/images/splash.jpg'),
      loadingText: Text("Loading"),
      photoSize: 100.0,
      loaderColor: Colors.blue,
    );
  }
}

class SecondScreen extends StatelessWidget {
  final Widget startupPage;

  const SecondScreen({this.startupPage});

  @override
  Widget build(BuildContext context) {
    return startupPage;
  }
}
