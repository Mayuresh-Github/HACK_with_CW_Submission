import 'package:flutter/material.dart';

class FutureWidget<T> extends StatelessWidget {
  final Future<T> future;
  final Widget Function(T item) builder;

  const FutureWidget({this.future, this.builder});

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: future,
      builder: (BuildContext context, AsyncSnapshot snapshot) {
        if (snapshot.hasData) {
          return builder(snapshot.data);
        } else if (snapshot.hasError) {
          return buildErrorIndicator(snapshot.error);
        } else {
          return buildProgressIndicator();
        }
      },
    );
  }

  static Widget buildProgressIndicator() {
    return Expanded(
      child: Container(
        child: Center(
          child: Container(
            width: 50.0,
            height: 50.0,
            child: CircularProgressIndicator(),
          ),
        ),
      ),
    );
  }

  static Widget buildErrorIndicator(Object error) {
    return Expanded(
      child: Padding(
        padding: const EdgeInsets.all(10.0),
        child: Center(
          child: Text(
            "ERROR IN LOADING:\n" + error.toString(),
            style: TextStyle(color: Colors.red, fontSize: 18.0),
          ),
        ),
      ),
    );
  }

  static Widget buildNoDataIndicator() {
    return Expanded(
      child: Container(
        child: Center(
          child: Text(
            "No Data Found",
            style: TextStyle(color: Colors.white, fontSize: 24.0),
          ),
        ),
      ),
    );
  }
}
