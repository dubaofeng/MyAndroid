package com.dbf.myandroid.testincallui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dbf.common.incallui.GlowPadWrapper
import com.dbf.myandroid.Constant
import com.dbf.myandroid.R

class TestInCallUiActivity : AppCompatActivity(), GlowPadWrapper.AnswerListener {

    private lateinit var mGlowpad: GlowPadWrapper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_in_call_ui)
        mGlowpad = findViewById(R.id.glow_pad_view);//kotlin可以不写findViewById
        mGlowpad.setAnswerListener(this);
        mGlowpad.startPing();
    }

    override fun onAnswer() {
        TODO("Not yet implemented")
        Log.i(Constant.TAG, "onAnswer");
    }

    override fun onDecline() {
        TODO("Not yet implemented")
        Log.i(Constant.TAG, "onAnswer");
    }

    override fun onText() {
        TODO("Not yet implemented")
        Log.i(Constant.TAG, "onAnswer");
    }
}
