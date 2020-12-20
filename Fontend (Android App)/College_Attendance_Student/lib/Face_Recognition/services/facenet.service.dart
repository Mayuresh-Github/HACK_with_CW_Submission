import 'dart:convert';
import 'dart:math';
import 'dart:typed_data';

import 'package:Student_Attendance_App/Face_Recognition/pages/db/database.dart';
import 'package:Student_Attendance_App/Student/models/FaceDimensionsDetailDTO.dart';
import 'package:Student_Attendance_App/common/services/shared_pref.dart';
import 'package:camera/camera.dart';
import 'package:firebase_ml_vision/firebase_ml_vision.dart';
import 'package:image/image.dart' as imglib;
import 'package:tflite_flutter/tflite_flutter.dart' as tflite;

class FaceNetService {
  // singleton boilerplate
  static final FaceNetService _faceNetService = FaceNetService._internal();

  factory FaceNetService() {
    return _faceNetService;
  }

  // singleton boilerplate
  FaceNetService._internal();

  DataBaseService _dataBaseService = DataBaseService();

  tflite.Interpreter _interpreter;

  double threshold = 1.0;

  List _predictedData;

  List get predictedData => this._predictedData;

  //  saved users data
  dynamic data = {};

  Future loadModel() async {
    try {
      final gpuDelegateV2 = tflite.GpuDelegateV2(
          options: tflite.GpuDelegateOptionsV2(
              false,
              tflite.TfLiteGpuInferenceUsage.fastSingleAnswer,
              tflite.TfLiteGpuInferencePriority.minLatency,
              tflite.TfLiteGpuInferencePriority.auto,
              tflite.TfLiteGpuInferencePriority.auto));

      var interpreterOptions = tflite.InterpreterOptions()
        ..addDelegate(gpuDelegateV2);
      this._interpreter = await tflite.Interpreter.fromAsset(
          'mobilefacenet.tflite',
          options: interpreterOptions);
      print('model loaded successfully');
    } catch (e) {
      print('Failed to load model.');
      print(e);
    }
  }

  setCurrentPrediction(CameraImage cameraImage, Face face) {
    /// crops the face from the image and transforms it to an array of data
    List input = _preProcess(cameraImage, face);

    /// then reshapes input and ouput to model format 🧑‍🔧
    input = input.reshape([1, 112, 112, 3]);
    List output = List(1 * 192).reshape([1, 192]);

    /// runs and transforms the data 🤖
    this._interpreter.run(input, output);
    output = output.reshape([192]);

    this._predictedData = List.from(output);
  }

  /// takes the predicted data previously saved and do inference
  /*String predict() {
    /// search closer user prediction if exists
    return searchResult(this._predictedData);
  }*/

  /// _preProess: crops the image to be more easy
  /// to detect and transforms it to model input.
  /// [cameraImage]: current image
  /// [face]: face detected
  List _preProcess(CameraImage image, Face faceDetected) {
    // crops the face 💇
    imglib.Image croppedImage = _cropFace(image, faceDetected);
    imglib.Image img = imglib.copyResizeCropSquare(croppedImage, 112);

    // transforms the cropped face to array data
    Float32List imageAsList = imageToByteListFloat32(img);
    return imageAsList;
  }

  /// crops the face from the image 💇
  /// [cameraImage]: current image
  /// [face]: face detected
  _cropFace(CameraImage image, Face faceDetected) {
    imglib.Image convertedImage = _convertCameraImage(image);
    double x = faceDetected.boundingBox.left - 10.0;
    double y = faceDetected.boundingBox.top - 10.0;
    double w = faceDetected.boundingBox.width + 10.0;
    double h = faceDetected.boundingBox.height + 10.0;
    return imglib.copyCrop(
        convertedImage, x.round(), y.round(), w.round(), h.round());
  }

  /// converts ___CameraImage___ type to ___Image___ type
  /// [image]: image to be converted
  imglib.Image _convertCameraImage(CameraImage image) {
    int width = image.width;
    int height = image.height;
    var img = imglib.Image(width, height);
    const int hexFF = 0xFF000000;
    final int uvyButtonStride = image.planes[1].bytesPerRow;
    final int uvPixelStride = image.planes[1].bytesPerPixel;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        final int uvIndex =
            uvPixelStride * (x / 2).floor() + uvyButtonStride * (y / 2).floor();
        final int index = y * width + x;
        final yp = image.planes[0].bytes[index];
        final up = image.planes[1].bytes[uvIndex];
        final vp = image.planes[2].bytes[uvIndex];
        int r = (yp + vp * 1436 / 1024 - 179).round().clamp(0, 255);
        int g = (yp - up * 46549 / 131072 + 44 - vp * 93604 / 131072 + 91)
            .round()
            .clamp(0, 255);
        int b = (yp + up * 1814 / 1024 - 227).round().clamp(0, 255);
        img.data[index] = hexFF | (b << 16) | (g << 8) | r;
      }
    }
    var img1 = imglib.copyRotate(img, -90);
    return img1;
  }

  Float32List imageToByteListFloat32(imglib.Image image) {
    /// input size = 112
    var convertedBytes = Float32List(1 * 112 * 112 * 3);
    var buffer = Float32List.view(convertedBytes.buffer);
    int pixelIndex = 0;

    for (var i = 0; i < 112; i++) {
      for (var j = 0; j < 112; j++) {
        var pixel = image.getPixel(j, i);

        /// mean: 128
        /// std: 128
        buffer[pixelIndex++] = (imglib.getRed(pixel) - 128) / 128;
        buffer[pixelIndex++] = (imglib.getGreen(pixel) - 128) / 128;
        buffer[pixelIndex++] = (imglib.getBlue(pixel) - 128) / 128;
      }
    }
    return convertedBytes.buffer.asFloat32List();
  }

  /// searchs the result in the DDBB (this function should be performed by Backend)
  /// [predictedData]: Array that represents the face by the MobileFaceNet model
  Future<String> searchResult(
      List predictedData, FaceDetailsDTO faceDetailsDTO) async {
    final _shared = SharedPref.instance;

    List<dynamic> dyna = new List<dynamic>();
    List<dynamic> list1 = [];
    list1 = json.decode(await _shared.getFace1());

    List<dynamic> list2 = [];
    list2 = json.decode(await _shared.getFace2());

    List<dynamic> list3 = [];
    list3 = json.decode(await _shared.getFace3());

    List<dynamic> list4 = [];
    list4 = json.decode(await _shared.getFace4());

    List<dynamic> list5 = [];
    list5 = json.decode(await _shared.getFace5());

    dyna.clear();
    dyna.add(list1);
    dyna.add(list2);
    dyna.add(list3);
    dyna.add(list4);
    dyna.add(list5);

    /// loads 'database' 🙄

    /// if no faces saved
    double minDist = 999;
    double currDist = 0.0;
    String predRes;

    /// search the closest result 👓
    for (List<dynamic> label in dyna) {
      currDist = _euclideanDistance(label, predictedData);
      if (currDist <= threshold && currDist < minDist) {
        minDist = currDist;
        predRes = label.toString();
      }
    }
    return predRes;
  }

  /// Adds the power of the difference between each point
  /// then computes the sqrt of the result 📐
  double _euclideanDistance(List e1, List e2) {
    double sum = 0.0;
    for (int i = 0; i < e1.length; i++) {
      sum += pow((e1[i] - e2[i]), 2);
    }
    return sqrt(sum);
  }

  void setPredictedData(value) {
    this._predictedData = value;
  }
}
