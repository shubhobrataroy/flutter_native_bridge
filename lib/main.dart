import 'package:flutter/material.dart';
import 'package:flutter_native_bridge/nativeBloc.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  var _nativeTextChangeController = TextEditingController();

  static var nativeBloc = NativeBloc("mainPage");

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.only(bottom: 16),
              child: StreamBuilder(
                  stream: nativeBloc.receivedMessage,
                  builder: (context, snapshot) {
                    var data = snapshot.data;
                    if (data == null) {
                      return Text("");
                    }
                    return Text("Received from Native: ${snapshot.data}");
                  }),
            ),
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: TextField(
                decoration: InputDecoration(
                    labelText: "Write a text to Send to Native Here"),
                controller: _nativeTextChangeController,
              ),
            ),
            RaisedButton(
                child: Text("Send Message to Native"),
                onPressed: () {
                  nativeBloc
                      .sendMessage(_nativeTextChangeController.value.text);
                }),
          ],
        ),
      ),
    );
  }
}
