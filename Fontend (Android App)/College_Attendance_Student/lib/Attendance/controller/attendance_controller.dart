import 'package:Student_Attendance_App/Attendance/models/AttendanceDetailsDTO.dart';
import 'package:Student_Attendance_App/common/models/response_dto.dart';
import 'package:Student_Attendance_App/common/services/http_service.dart';

class AttendanceController {
  final httpService = HttpService();

  Future<ResponseDTO> markAttendance(
      AttendanceDetailsDTO attendanceDetailsDTO) async {
    const endPointUrl = "/server/attendance/markAttendance";
    final parameters = attendanceDetailsDTO.toJson();

    var response =
        await httpService.get("MARK_MY_ATTENDANCE", endPointUrl, parameters);
    return ResponseDTO.fromJson(response);
  }
}
