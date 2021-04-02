package com.dbf.studyandtest.custom_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dbf.studyandtest.R;
import com.dbf.studyandtest.databinding.ActivityShowFrameAnimBinding;

import java.util.UUID;

public class ShowFrameAnimActivity extends AppCompatActivity {
    ActivityShowFrameAnimBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowFrameAnimBinding.inflate(getLayoutInflater());
        android.util.Log.i("dfb", "onClick: " + getUserUniqid());
        setContentView(binding.getRoot());
        binding.showFrameAnim1.setResours(ShowFrameAnim.picRun1).setIntervalDuration(10).setShowOnEnd(true);
        binding.showFrameAnim2.setResours(ShowFrameAnim.picRun2).setIntervalDuration(10).setShowOnEnd(true);
        binding.showFrameAnim3.setResours(ShowFrameAnim.picRun3).setIntervalDuration(10).setShowOnEnd(true);
        binding.clicBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.showFrameAnim1.startAnima();
//                binding.showFrameAnim2.startAnima();
//                binding.showFrameAnim3.startAnima();
//                android.util.Log.i("dfb", "onClick: " + getUserUniqid());
                Toast.makeText(ShowFrameAnimActivity.this, "测试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getUserUniqid() {
        return UUID.randomUUID().toString();
    }
}