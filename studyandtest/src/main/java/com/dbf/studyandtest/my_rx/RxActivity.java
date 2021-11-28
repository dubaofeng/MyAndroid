package com.dbf.studyandtest.my_rx;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.dbf.studyandtest.R;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class RxActivity extends AppCompatActivity {
    private final String TAG = RxActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        textRx();
    }

    private void textRx() {
        Flowable.just("hello Rxjava2").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "accept: s=" + s);
            }
        });
    }
}