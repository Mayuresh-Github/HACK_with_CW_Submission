class FaceDetailsDTO {
  FaceDetailsDTO(
      {this.studentClientId,
      this.Face1,
      this.Face2,
      this.Face3,
      this.Face4,
      this.Face5,
      this.sha256_Hash,
      this.facePredictionsDTO});

  String studentClientId;
  FaceDetailsDTO facePredictionsDTO;
  String Face1;
  String Face2;
  String Face3;
  String Face4;
  String Face5;
  String sha256_Hash;

  Map<String, String> toJson() {
    final data = new Map<String, String>();
    data['studentClientId'] = this.studentClientId;
    data['Face1'] = this.Face1;
    data['Face2'] = this.Face2;
    data['Face3'] = this.Face3;
    data['Face4'] = this.Face4;
    data['Face5'] = this.Face5;
    return data;
  }

  Map<String, String> toJsonCheck() {
    final data = new Map<String, String>();
    data['studentClientId'] = this.studentClientId;
    return data;
  }

  factory FaceDetailsDTO.fromJson(Map<String, dynamic> json) {
    return FaceDetailsDTO(
      facePredictionsDTO:
          FaceDetailsDTO.fromJsonCheck(json['facePredictionsDTO']),
      sha256_Hash: json['sha256_Hash'] ?? "",
    );
  }

  factory FaceDetailsDTO.fromJsonCheck(Map<String, dynamic> json) {
    return FaceDetailsDTO(
      studentClientId: json['studentClientId'] ?? "",
      Face1: json['Face1'] ?? "",
      Face2: json['Face2'] ?? "",
      Face3: json['Face3'] ?? "",
      Face4: json['Face4'] ?? "",
      Face5: json['Face5'] ?? "",
    );
  }
}

class FaceDetailsCheck {
  FaceDetailsCheck({this.studentClientId});

  String studentClientId;

  Map<String, String> toJsonCheck() {
    final data = new Map<String, String>();
    data['studentClientId'] = this.studentClientId;
    return data;
  }
}
