import 'package:shared_preferences/shared_preferences.dart';

class SharedPref {
  static const String _isLoggedInKey = "isLoggedIn";
  static const String _authTokenKey = "authToken";
  static const String _userInfo = "userInfo";
  static const String _role = "role";
  static const String Face1 = "Face1";
  static const String Face2 = "Face2";
  static const String Face3 = "Face3";
  static const String Face4 = "Face4";
  static const String Face5 = "Face5";
  static final SharedPref instance = SharedPref._instantiate();

  SharedPref._instantiate();

  Future<String> getFace1() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(Face1);
  }

  Future<String> getFace2() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(Face2);
  }

  Future<String> getFace3() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(Face3);
  }

  Future<String> getFace4() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(Face4);
  }

  Future<String> getFace5() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(Face5);
  }

  saveFace1(value) async {
    final prefs = await SharedPreferences.getInstance();
    prefs.setString(Face1, value);
  }

  saveFace2(value) async {
    final prefs = await SharedPreferences.getInstance();
    prefs.setString(Face2, value);
  }

  saveFace3(value) async {
    final prefs = await SharedPreferences.getInstance();
    prefs.setString(Face3, value);
  }

  saveFace4(value) async {
    final prefs = await SharedPreferences.getInstance();
    prefs.setString(Face4, value);
  }

  saveFace5(value) async {
    final prefs = await SharedPreferences.getInstance();
    prefs.setString(Face5, value);
  }

  readIsLoggedIn() async {
    final prefs = await SharedPreferences.getInstance();
    if (prefs.getBool(_isLoggedInKey) == null)
      return false;
    else
      return prefs.getBool(_isLoggedInKey);
  }

  /*Future<LoginResponse> getUserInfo() async {
    final prefs = await SharedPreferences.getInstance();
    Map<String, dynamic> map = json.decode(prefs.getString(_userInfo));
    return LoginResponse.fromJson(map);
  }*/

  Future<String> getAuthToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(_authTokenKey);
  }

  Future<String> getUserToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(_userInfo);
  }

  saveIsLoggedIn(value) async {
    final prefs = await SharedPreferences.getInstance();
    prefs.setBool(_isLoggedInKey, value);
  }

  saveUserAuthToken(value, role) async {
    final prefs = await SharedPreferences.getInstance();
    prefs.setString(_authTokenKey, value);
    prefs.setString(_role, role);
  }

  saveUser(value, role) async {
    final prefs = await SharedPreferences.getInstance();
    prefs.setString(_userInfo, value);
    prefs.setString(_role, role);
  }

  Future<bool> removeIsLoggedIn() async {
    final prefs = await SharedPreferences.getInstance();
    prefs.remove(_userInfo);
    return prefs.remove(_isLoggedInKey);
  }

  Future<bool> removeUser() async {
    final prefs = await SharedPreferences.getInstance();
    prefs.remove(_isLoggedInKey);
    return prefs.remove(_userInfo);
  }

  Future<bool> removeAuthToken() async {
    final prefs = await SharedPreferences.getInstance();
    prefs.remove(_isLoggedInKey);
    return prefs.remove(_authTokenKey);
  }
}
