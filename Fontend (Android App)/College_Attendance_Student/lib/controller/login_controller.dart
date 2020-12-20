import 'package:Student_Attendance_App/common/services/http_service.dart';
import 'package:Student_Attendance_App/common/services/shared_pref.dart';

class LoginController {
  static final _sharedPref = SharedPref.instance;
  final httpService = HttpService();

  Future<bool> isUserAuthorized() async {
    return await SharedPref.instance.readIsLoggedIn();
  }

  Future<bool> logOut() {
    _sharedPref.removeUser();
    return _sharedPref.removeIsLoggedIn();
  }
}