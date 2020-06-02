package com.dbf.studyandtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dbf.common.myutils.MyLog
import com.dbf.studyandtest.javastudy.classtest.TestClass2Activity
import com.dbf.studyandtest.myaidl.MyTestAidlMainActivity
import com.dbf.studyandtest.myaidl.location.TestIPCActivity
import com.dbf.studyandtest.myrecyclerview.RecyclerViewActivity
import com.dbf.studyandtest.mywidget.WidgetMainActivity
import com.dbf.studyandtest.springanimation.SpringAnimationActivity
import com.dbf.studyandtest.testrefreshlistview.TestRefreshListViewActivity

class StudyAndTestMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_and_test_main)

    }

    fun clickButton(view: View) {
        when (view.id) {
            R.id.refreshListView -> {
                var intent: Intent = Intent()
                intent.setClass(this, TestRefreshListViewActivity::class.java)
                startActivity(intent)
            }
            R.id.springAnimation -> {
                var intent: Intent = Intent()
                intent.setClass(this, SpringAnimationActivity::class.java)
                startActivity(intent)
            }
            R.id.recyclerView -> {
                var intent: Intent = Intent()
                intent.setClass(this, RecyclerViewActivity::class.java)
                startActivity(intent)
            }
            R.id.testMetaData -> {
                var intent: Intent = Intent()
                intent.setClass(this, TestMetaDataActivity::class.java)
                startActivity(intent)
            }
            R.id.testClass -> {
                var intent: Intent = Intent()
                intent.setClass(this, TestClass2Activity::class.java)
                startActivity(intent)
            }
            R.id.testWidget -> {
                var intent: Intent = Intent()
                intent.setClass(this, WidgetMainActivity::class.java)
                startActivity(intent)
            }
            R.id.testAidl -> {
                var intent: Intent = Intent()
                intent.setClass(this, MyTestAidlMainActivity::class.java)
                startActivity(intent)
            }
            R.id.testIPC -> {
                var intent: Intent = Intent()
                intent.setClass(this, TestIPCActivity::class.java)
                startActivity(intent)
            }
            else -> {
            }
        }
    }
}
