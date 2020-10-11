package com.shubhobrataroy.flutter_native_bridge

import android.app.Activity
import android.content.Intent
import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant


class MainActivity : FlutterActivity() {

    lateinit var mainPageMethodChannel: MethodChannel
    lateinit var secondPageMethodChannel: MethodChannel

    val MAIN_PAGE_REQ_CODE = 547

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        mainPageMethodChannel = flutterEngine.createMethodChannel("mainPage")
        listenMainPageMethodCalls()
    }

    fun listenMainPageMethodCalls() {
        mainPageMethodChannel.setMethodCallHandler { call, result ->

            val args: Map<String, Any> = call.arguments()

            when (call.method) {
                SEND_METHOD_NAME -> startActivityForResult(
                        Intent(this, NativeActivity::class.java)
                                .putExtra(MSG_TAG, args[MSG_TAG].toString()), MAIN_PAGE_REQ_CODE
                )
            }
        }
    }

    fun sendMessageToMainPage(message: String?) {
        val args: HashMap<String, Any?> = HashMap()
        args[MSG_TAG] = message
        mainPageMethodChannel.invokeMethod("sendFromNative", args)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == MAIN_PAGE_REQ_CODE && resultCode == Activity.RESULT_OK)
            sendMessageToMainPage(data?.getStringExtra(MSG_TAG))
    }
}

fun FlutterEngine.createMethodChannel(channelName: String): MethodChannel = MethodChannel(this.dartExecutor.binaryMessenger, channelName)


const val SEND_METHOD_NAME = "sendMessage"
const val RECEIVE_METHOD_NAME = "receiveMessage"
const val MSG_TAG = "message"