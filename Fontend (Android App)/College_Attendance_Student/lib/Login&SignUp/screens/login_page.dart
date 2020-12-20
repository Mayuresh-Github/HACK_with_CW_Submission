import 'package:Student_Attendance_App/HomePage/homepage.dart';
import 'package:Student_Attendance_App/Login&SignUp/screens/signup_page.dart';
import 'package:Student_Attendance_App/Student/controller/student_controller.dart';
import 'package:Student_Attendance_App/Student/models/StudentDetailsDTO.dart';
import 'package:Student_Attendance_App/common/models/sign_in_data.dart';
import 'package:Student_Attendance_App/common/services/shared_pref.dart';
import 'package:flushbar/flushbar.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  String email, password;
  StudentController studentController = new StudentController();
  static final _sharedPref = SharedPref.instance;

  void onLoginButtonClick(SignInData signInData) async {
    StudentDetailsDTO studentDetailsDTO =
        await studentController.signInStudent(signInData);

    if (studentDetailsDTO.studentEmail.compareTo(email) == 0) {
      _sharedPref.saveIsLoggedIn(true);
      _sharedPref.saveUser(
          studentDetailsDTO.studentClientId, studentDetailsDTO.role);
      Navigator.of(context).pop();
      Navigator.of(context).push(
        MaterialPageRoute(
          builder: (_) => HomePage(),
        ),
      );
      Flushbar(
        margin: EdgeInsets.all(8),
        borderRadius: 8,
        message: "Login Successfull",
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
        body: SingleChildScrollView(
            child: Column(
          children: [
            Center(
              child: Container(
                margin: EdgeInsets.fromLTRB(
                    15, MediaQuery.of(context).size.height / 5, 15, 20),
                child: Column(
                  children: [
                    SizedBox(height: 100),
                    Center(
                      child: Text(
                        'Student Login',
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          color: Colors.deepPurple[600],
                          fontSize: MediaQuery.of(context).size.height / 22,
                        ),
                      ),
                    ),
                    SizedBox(height: 50),
                    TextField(
                      onChanged: (value) {
                        email = value;
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
                        labelText: 'Email',
                        hintText: 'Enter Email',
                      ),
                      maxLines: 1,
                      maxLength: 100,
                    ),
                    SizedBox(height: 25),
                    TextField(
                      onChanged: (value) {
                        password = value;
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
                        labelText: 'Password',
                        hintText: 'Enter Password',
                        focusColor: Colors.deepPurple[500],
                      ),
                      maxLines: 1,
                      obscureText: true,
                      maxLength: 100,
                    ),
                    SizedBox(height: 40),
                    RaisedButton(
                      color: Colors.grey[400],
                      padding: EdgeInsets.fromLTRB(25, 10, 25, 10),
                      onPressed: () {
                        SignInData signInData =
                            new SignInData(email: email, password: password);
                        onLoginButtonClick(signInData);
                      },
                      child: Text('Login',
                          style: TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 20,
                            color: Colors.deepPurple,
                          )),
                    ),
                    SizedBox(height: 40),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Text(
                          'New Student?',
                          style: TextStyle(
                            color: Colors.black,
                            fontSize: 20,
                          ),
                        ),
                        FlatButton(
                          padding: EdgeInsets.fromLTRB(5, 0, 0, 5),
                          onPressed: () {
                            Navigator.of(context).pop();
                            Navigator.of(context).push(
                              MaterialPageRoute(
                                builder: (_) => SignUpPage(),
                              ),
                            );
                          },
                          child: Text(
                            'SignUp here!',
                            style: TextStyle(
                              color: Colors.deepPurple[400],
                              fontWeight: FontWeight.bold,
                              fontSize: 20,
                            ),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
          ],
        )),
      ),
    ));
  }
}
