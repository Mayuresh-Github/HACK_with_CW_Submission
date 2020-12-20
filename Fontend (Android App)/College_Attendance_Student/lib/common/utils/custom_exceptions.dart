class AppException implements Exception {
  final _message;

  const AppException([this._message]);

  String toString() {
    return "$_message";
  }
}

class FetchDataException extends AppException {
  const FetchDataException([message]) : super(message);
}

class ServerException extends AppException {
  const ServerException([message]) : super(message);
}
