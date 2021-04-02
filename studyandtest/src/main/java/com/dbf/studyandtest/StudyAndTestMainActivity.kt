package com.dbf.studyandtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.dbf.studyandtest.custom_view.ShowFrameAnimActivity
import com.dbf.studyandtest.eventdispatch.EventDispatchActivity
import com.dbf.studyandtest.javastudy.JavaStudyMainActivity
import com.dbf.studyandtest.javastudy.classtest.TestClass2Activity
import com.dbf.studyandtest.myaidl.MyTestAidlMainActivity
import com.dbf.studyandtest.myaidl.location.TestIPCActivity
import com.dbf.studyandtest.myglide.TestGlideActivity
import com.dbf.studyandtest.myrecyclerview.RecyclerViewActivity
import com.dbf.studyandtest.myretrofit.TestRetrofitActivity
import com.dbf.studyandtest.mywidget.WidgetMainActivity
import com.dbf.studyandtest.navigator.BottomNavigationActivity
import com.dbf.studyandtest.practice_service.TestServiceActivity
import com.dbf.studyandtest.single.SingletonClass
import com.dbf.studyandtest.springanimation.SpringAnimationActivity
import com.dbf.studyandtest.testpermissionx.TestPermissionActivity
import com.dbf.studyandtest.testrefreshlistview.TestRefreshListViewActivity

class StudyAndTestMainActivity : AppCompatActivity() {
    val TAG: String = "StudyAndTestMainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_and_test_main)
    }

    fun clickButton(view: View) {
        var intent = Intent()
        when (view.id) {
            R.id.showFrameAnim -> {
                intent.setClass(this, ShowFrameAnimActivity::class.java)
            }
            R.id.testRetrofit -> {
                intent.setClass(this, TestRetrofitActivity::class.java)
            }
            R.id.testNavigation -> {
                intent.setClass(this, BottomNavigationActivity::class.java)
            }
            R.id.testHandler -> {
                intent.setClass(this, TestHandlerActivity::class.java)
            }
            R.id.testService -> {
                intent.setClass(this, TestServiceActivity::class.java)
            }
            R.id.testJava -> {
                intent.setClass(this, TestMetaDataActivity::class.java)
            }
            R.id.refreshListView -> {
                intent.setClass(this, TestRefreshListViewActivity::class.java)
            }
            R.id.springAnimation -> {
                intent.setClass(this, SpringAnimationActivity::class.java)
            }
            R.id.recyclerView -> {
                intent.setClass(this, RecyclerViewActivity::class.java)
            }
            R.id.testMetaData -> {
                intent.setClass(this, TestMetaDataActivity::class.java)
            }
            R.id.testClass -> {
                intent.setClass(this, TestClass2Activity::class.java)
            }
            R.id.testWidget -> {
                intent.setClass(this, WidgetMainActivity::class.java)
            }
            R.id.testAidl -> {
                intent.setClass(this, MyTestAidlMainActivity::class.java)
            }
            R.id.testIPC -> {
                intent.setClass(this, TestIPCActivity::class.java)
            }
            R.id.testGlide -> {
                intent.setClass(this, TestGlideActivity::class.java)
            }
            R.id.testPermiss -> {
                intent.setClass(this, TestPermissionActivity::class.java)
            }
            R.id.testJavaTest -> {
                intent.setClass(this, JavaStudyMainActivity::class.java)
            }
            R.id.testEventDispatch -> {
                intent.setClass(this, EventDispatchActivity::class.java)
            }
            else -> {
            }
        }
        startActivity(intent)
    }
}
