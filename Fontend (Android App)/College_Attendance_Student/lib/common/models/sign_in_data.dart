import 'package:flutter/cupertino.dart';

class SignInData {
  SignInData({@required this.email, @required this.password});

  String email;
  String password;

  Map<String, String> toJson() {
    final data = new Map<String, String>();
    data['email'] = this.email;
    data['password'] = this.password;
    return data;
  }

  factory SignInData.fromJson(Map<String, dynamic> json) {
    return SignInData(
      email: json['email'] ?? "",
      password: json['password'] ?? "",
    );
  }

  Map<String, String> toJsonStudent() {
    final data = new Map<String, String>();
    data['studentEmail'] = this.email;
    data['studentPassword'] = this.password;
    return data;
  }
}
