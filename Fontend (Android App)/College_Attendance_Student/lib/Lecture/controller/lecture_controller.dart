import 'package:Student_Attendance_App/Lecture/models/LectureDetailsDTO.dart';
import 'package:Student_Attendance_App/common/services/http_service.dart';

class LectureController {
  final httpService = HttpService();

  Future<LectureDetailsDTO> getLectureDetails(String lectureId) async {
    const endPointUrl = "/server/lecture/getOneLectureDetails";
    final parameters = new Lecture(lectureId: lectureId).toJson();

    var response = await httpService.get(null, endPointUrl, parameters);

    try {
      Error.fromJson(response);
      return new LectureDetailsDTO(status: 404);
    } catch (e) {
      try {
        LectureDetailsDTO lectureDetailsDTO =
            new LectureDetailsDTO.fromJsonCustom(response);
        lectureDetailsDTO.status = 200;
        return lectureDetailsDTO;
      } catch (e) {
        return null;
      }
    }
  }
}
