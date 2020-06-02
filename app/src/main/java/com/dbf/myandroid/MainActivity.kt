package com.dbf.myandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dbf.common.refreshlistview.Utils
import com.dbf.myandroid.testincallui.TestInCallUiActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Utils.measureView(retrofitBut)
    }

    fun clickButton(view: View) {
        when (view.id) {
            R.id.retrofitBut -> Log.i(TAG, "点击了retrofit")
            R.id.toHomePageMainActivity -> {
                var intent: Intent = Intent()
                intent.setClassName(this, "com.dbf.homepage.HomePageMainActivity")
                startActivity(intent)
            }
            R.id.testInCallUiBut -> {
                var intent: Intent = Intent()
                intent.setClass(this, TestInCallUiActivity::class.java)
                startActivity(intent)
            }
            R.id.comTestInCallUiBut -> {
                var intent: Intent = Intent()
                intent.setClassName(this, "com.dbf.common.Com_TestInCallUiMainActivity")
                startActivity(intent)
            }
            else -> {
            }
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}
