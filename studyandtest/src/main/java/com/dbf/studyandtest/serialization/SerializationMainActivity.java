package com.dbf.studyandtest.serialization;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.dbf.studyandtest.R;
import com.dbf.studyandtest.proto.Person;

/**
 * @author raden
 */
public class SerializationMainActivity extends AppCompatActivity {
    private final String TAG = SerializationMainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serialization_main);
        Person person = Person.newBuilder().setName("张三").setAge(18).build();
        Log.i(TAG, "onCreate: " + person.getName() + "\n" + person.getAge());
        PPerson person1 = new PPerson();
        person1.setName("张三");
        person1.setAge(18);
    }
}