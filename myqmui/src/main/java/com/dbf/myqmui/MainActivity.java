package com.dbf.myqmui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.qmuiteam.qmui.arch.QMUIActivity;

public class MainActivity extends QMUIActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
