package com.dbf.studyandtest.generic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.dbf.studyandtest.R;

import java.util.ArrayList;

public class GenericActivity extends AppCompatActivity {
    private final String TAG = GenericActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);
        ClassImpl<String> stringClass = new ClassImpl<>();
        stringClass.setT("ClassImpl");
        Log.i(TAG, "onCreate: " + stringClass.getT());
        Class2Impl string2Class = new Class2Impl();
        string2Class.setT("Class2Impl");
        Log.i(TAG, "onCreate: " + string2Class.getT());
        Log.i(TAG, "onCreate: " + getT("Generic function"));
//        Log.i(TAG, "onCreate: "+ strings.get(0).getClass().getName());
    }
    private ArrayList<? super Class1> strings=new ArrayList<>();

    public static final <T> T getT(T t) {
        return t;
    }


}