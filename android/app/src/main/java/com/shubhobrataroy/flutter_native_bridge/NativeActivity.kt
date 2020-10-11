package com.shubhobrataroy.flutter_native_bridge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class NativeActivity : Activity() {

    val receivedMsgTv by lazy { findViewById<TextView>(R.id.tv_received_msg) }
    val previousFlutterButton by lazy { findViewById<Button>(R.id.bt_previous_flutter_page) }
    val msgTxt by lazy { findViewById<EditText>(R.id.et_message) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)
        actionBar.title = "Native Activity"
        receivedMsgTv.text = intent.getStringExtra(MSG_TAG)?.run {
            "Received Message : $this"
        } ?: ""

        previousFlutterButton.setOnClickListener {
            setResult(RESULT_OK, Intent().putExtra(MSG_TAG, msgTxt.text.toString()))
            finish()
        }

    }
}