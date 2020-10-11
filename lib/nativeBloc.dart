import 'dart:collection';
import 'dart:wasm';

import 'package:flutter/services.dart';
import 'package:rxdart/rxdart.dart';

class NativeBloc {
  MethodChannel methodChannel;
  var _receivedData = BehaviorSubject<String>();

  Future<Void> sendMessage(String message) async {
    var map = HashMap<String, dynamic>();
    map["message"] = message;
    await methodChannel.invokeMethod("sendMessage", map);
  }

  Stream<String> get receivedMessage {
    return _receivedData.stream;
  }

  NativeBloc(String methodChannelName) {
    this.methodChannel = MethodChannel(methodChannelName);
    this.methodChannel.setMethodCallHandler((MethodCall call) async {
      switch (call.method) {
        case 'sendFromNative':
          updateReceivedData(call);
          return true;
        default:
          return true;
      }
    });
  }

  void updateReceivedData(MethodCall call) {
    var data = call.arguments;
    _receivedData.add(data["message"].toString());
  }

  void dispose() {
    _receivedData.close();
  }
}
