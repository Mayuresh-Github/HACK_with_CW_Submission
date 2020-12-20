import 'package:Student_Attendance_App/Login&SignUp/screens/login_page.dart';
import 'package:Student_Attendance_App/Student/controller/student_controller.dart';
import 'package:Student_Attendance_App/Student/models/StudentDetailsDTO.dart';
import 'package:Student_Attendance_App/common/models/response_dto.dart';
import 'package:flushbar/flushbar.dart';
import 'package:flutter/material.dart';

class SignUpPage extends StatefulWidget {
  @override
  _SignUpPageState createState() => _SignUpPageState();
}

class _SignUpPageState extends State<SignUpPage> {
  String studentName,
      studentEmail,
      studentDOB,
      department,
      password,
      courseName,
      teacherEmail,
      enrolledDate,
      graduationDate;
  ResponseDTO responseDTO;
  StudentController studentController = new StudentController();

  void onSignUpButtonClick(StudentDetailsDTO studentDetailsDTO) async {
    responseDTO = await studentController.signUpStudent(studentDetailsDTO);

    print(responseDTO.status.toString() +
        " " +
        responseDTO.httpStatus.toString() +
        " " +
        responseDTO.message.toString());

    if (responseDTO.status == 201 ||
        responseDTO.message == "Student created successfully") {
      Navigator.of(context).pop();
      Navigator.of(context).push(
        MaterialPageRoute(
          builder: (_) => LoginPage(),
        ),
      );
      Flushbar(
        margin: EdgeInsets.all(8),
        borderRadius: 8,
        message: "Student created successfully!\nLogin to continue",
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
        message: responseDTO.message.toString(),
        icon: Icon(
          Icons.info_outline,
          size: 20,
          color: Colors.lightBlue[800],
        ),
        duration: Duration(seconds: 2),
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
                      15, MediaQuery.of(context).size.height / 10, 15, 20),
                  child: Column(
                    children: [
                      Center(
                        child: Text(
                          'Student Signup',
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
                          studentName = value;
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
                          labelText: 'Student Name',
                          hintText: 'Enter name',
                        ),
                        maxLines: 1,
                        maxLength: 100,
                      ),
                      SizedBox(height: 25),
                      TextField(
                        onChanged: (value) {
                          studentEmail = value;
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
                          labelText: 'Student Email',
                          hintText: 'Enter email',
                        ),
                        maxLines: 1,
                        maxLength: 100,
                      ),
                      SizedBox(height: 25),
                      TextField(
                        onChanged: (value) {
                          studentDOB = value;
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
                          labelText: 'Student DOB',
                          hintText: 'Enter DOB',
                        ),
                        maxLines: 1,
                        maxLength: 100,
                      ),
                      SizedBox(height: 25),
                      TextField(
                        onChanged: (value) {
                          department = value;
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
                          labelText: 'Student Department',
                          hintText: 'Enter department',
                        ),
                        maxLines: 1,
                        maxLength: 100,
                      ),
                      SizedBox(height: 25),
                      TextField(
                        onChanged: (value) {
                          courseName = value;
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
                          labelText: 'Student Course name',
                          hintText: 'Enter course name',
                        ),
                        maxLines: 1,
                        maxLength: 100,
                      ),
                      SizedBox(height: 25),
                      TextField(
                        onChanged: (value) {
                          teacherEmail = value;
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
                          labelText: 'Teacher Email',
                          hintText: 'Enter teacher email',
                        ),
                        maxLines: 1,
                        maxLength: 100,
                      ),
                      SizedBox(height: 25),
                      TextField(
                        onChanged: (value) {
                          enrolledDate = value;
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
                          labelText: 'Student Enrolled Date',
                          hintText: 'Enter enrolled date',
                        ),
                        maxLines: 1,
                        maxLength: 100,
                      ),
                      SizedBox(height: 25),
                      TextField(
                        onChanged: (value) {
                          graduationDate = value;
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
                          labelText: 'Student Graduation Date',
                          hintText: 'Enter graduation date',
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
                          hintText: 'Enter password',
                        ),
                        maxLines: 1,
                        obscureText: true,
                        maxLength: 100,
                      ),
                      SizedBox(height: 25),
                      TextField(
                        decoration: InputDecoration(
                          counterText: "",
                          focusedBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Colors.deepPurple),
                            borderRadius: BorderRadius.circular(20.0),
                          ),
                          enabledBorder: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(10.0),
                          ),
                          labelText: 'Confirm Password',
                          hintText: 'Enter confirm password',
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
                          StudentDetailsDTO studentDetailsDTO =
                              new StudentDetailsDTO(
                                  studentName: studentName,
                                  studentEmail: studentEmail,
                                  studentDOB: studentDOB,
                                  department: department,
                                  courseName: courseName,
                                  studentPassword: password,
                                  teacherEmail: teacherEmail,
                                  enrolledDate: enrolledDate,
                                  graduationDate: graduationDate);
                          onSignUpButtonClick(studentDetailsDTO);
                        },
                        child: Text('Sign-Up',
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
                            'Have an account?',
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
                                  builder: (_) => LoginPage(),
                                ),
                              );
                            },
                            child: Text(
                              'Login here!',
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
      ),
    );
  }
}
