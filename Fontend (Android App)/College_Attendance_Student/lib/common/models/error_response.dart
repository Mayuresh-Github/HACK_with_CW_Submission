class ErrorResponse {
  final String timestamp;
  final int status;
  final String error;
  final String message;
  final String path;

  const ErrorResponse({
    this.timestamp,
    this.status,
    this.error,
    this.message,
    this.path,
  });

  factory ErrorResponse.fromJson(Map<String, dynamic> json) {
    return ErrorResponse(
      timestamp: json['timestamp'] ?? "",
      status: json['status'],
      error: json['error'] ?? "",
      message: json['message'] ?? "",
      path: json['path'] ?? "",
    );
  }
}
