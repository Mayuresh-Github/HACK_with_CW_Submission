class ResponseDTO {
  final int status;
  final String httpStatus;
  final String message;

  const ResponseDTO({
    this.status,
    this.httpStatus,
    this.message,
  });

  factory ResponseDTO.fromJson(Map<String, dynamic> json) {
    return ResponseDTO(
      status: json['status'],
      httpStatus: json['httpStatus'] ?? "",
      message: json['message'] ?? "",
    );
  }
}
