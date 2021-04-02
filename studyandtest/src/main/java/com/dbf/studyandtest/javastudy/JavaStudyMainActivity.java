package com.dbf.studyandtest.javastudy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dbf.studyandtest.R;
import com.dbf.studyandtest.javastudy.dagger2.Eat;
import com.dbf.studyandtest.javastudy.proxy.ProxyMain;

public class JavaStudyMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_study_main);
//        new ReflectMian().main(new String[]{});
//        new ProxyMain().main(new String[]{});
//        new MyLambda().mian();
//        new Eat().eatFoot();
    }

}
