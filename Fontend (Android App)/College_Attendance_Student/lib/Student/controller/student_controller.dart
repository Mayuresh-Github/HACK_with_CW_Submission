import 'package:Student_Attendance_App/Student/models/FaceDimensionsDetailDTO.dart';
import 'package:Student_Attendance_App/Student/models/StudentDetailsDTO.dart';
import 'package:Student_Attendance_App/common/models/response_dto.dart';
import 'package:Student_Attendance_App/common/models/sign_in_data.dart';
import 'package:Student_Attendance_App/common/services/http_service.dart';
import 'package:Student_Attendance_App/common/services/shared_pref.dart';

class StudentController {
  static final _sharedPref = SharedPref.instance;
  final httpService = HttpService();

  Future<ResponseDTO> signUpStudent(StudentDetailsDTO studentDetailsDTO) async {
    const endPointUrl = "/server/student/signUpStudent";
    final parameters = studentDetailsDTO.toJson();

    var response =
        await httpService.post("CREATE_A_STUDENT", endPointUrl, parameters);
    return ResponseDTO.fromJson(response);
  }

  Future<StudentDetailsDTO> signInStudent(SignInData signInData) async {
    const endPointUrl = "/server/student/signInStudent";
    final parameters = signInData.toJsonStudent();

    var response = await httpService.get(null, endPointUrl, parameters);
    return StudentDetailsDTO.fromJson(response);
  }

  Future<FaceDetailsDTO> getFaceDetails(
      FaceDetailsCheck faceDetailsCheck) async {
    const endPointUrl = "/server/student/getFacePredictions";
    final parameters = faceDetailsCheck.toJsonCheck();

    var response = await httpService.get(
        "GET_FACE_PREDICTIONS_OF_STUDENT", endPointUrl, parameters);

    print(response);
    return FaceDetailsDTO.fromJson(response);
  }

  Future<ResponseDTO> updateFaceDetails(FaceDetailsDTO faceDetailsDTO) async {
    const endPointUrl = "/server/student/updateFacePredictions";
    final parameters = faceDetailsDTO.toJson();

    var response = await httpService.post(
        "UPDATE_FACE_PREDICTIONS_OF_STUDENT", endPointUrl, parameters);

    print(response);
    return ResponseDTO.fromJson(response);
  }
}
