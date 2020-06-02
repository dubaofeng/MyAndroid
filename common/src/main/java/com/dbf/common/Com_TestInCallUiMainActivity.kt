package com.dbf.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dbf.common.incallui.GlowPadWrapper
import com.dbf.common.refreshlistview.Utils

class Com_TestInCallUiMainActivity : AppCompatActivity(), GlowPadWrapper.AnswerListener {
    private lateinit var gpw: GlowPadWrapper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_com__test_in_call_ui_main)
        gpw = findViewById(R.id.glow_pad_view)
        gpw.setAnswerListener(this)
        gpw.startPing()
    }

    override fun onAnswer() {
    }

    override fun onDecline() {
    }

    override fun onText() {
    }
}
