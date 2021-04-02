package com.dbf.studyandtest.eventdispatch.exemple;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.dbf.studyandtest.R;
import com.dbf.studyandtest.databinding.ActivityEventConflictExempleBinding;
import com.dbf.studyandtest.databinding.ScrollviewInViewpagerBinding;

import java.util.ArrayList;
import java.util.List;

public class EventConflictExempleActivity extends AppCompatActivity {
    private ActivityEventConflictExempleBinding binding;
    private ScrollviewInViewpagerBinding scrollViewBind;
    private ArrayList<View> page1Views = new ArrayList<>();
    private ArrayList<View> page2Views = new ArrayList<>();
    private ArrayList<String> strs = new ArrayList<>();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventConflictExempleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        scrollViewBind = ScrollviewInViewpagerBinding.inflate(getLayoutInflater());
        page1Views.add(scrollViewBind.getRoot());
        strs.add("11");
        strs.add("22");
        strs.add("33");
        strs.add("44");
        strs.add("55");
        strs.add("66");
        strs.add("77");
        strs.add("88");
        strs.add("99");
        strs.add("10");
        strs.add("1111");
        strs.add("11111");
        strs.add("111111");
        scrollViewBind.listv.setAdapter(
                new ArrayAdapter<String>(
                        this, android.R.layout.simple_expandable_list_item_1, strs));
        page1Views.add(createTextView("第一页"));
        page1Views.add(createTextView("第二页"));
        page1Views.add(createTextView("第三页"));
        binding.vp1.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return page1Views.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(page1Views.get(position));
                return page1Views.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(page1Views.get(position));
            }
        });
        page2Views.add(createViewpager());
        page2Views.add(createViewpager());
        page2Views.add(createViewpager());
        binding.vp2.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return page2Views.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(page2Views.get(position));
                return page2Views.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(page2Views.get(position));
            }
        });
        binding.vp2.setVisibility(View.GONE);
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(26);
        textView.setGravity(Gravity.CENTER);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        layoutParams.setMargins(0, 60, 0, 0);
        textView.setLayoutParams(layoutParams);
        return textView;

    }

    private View createScrollView() {

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.BLUE);
        linearLayout.addView(createTextView("1"));
        linearLayout.addView(createTextView("1"));
        linearLayout.addView(createTextView("1"));
        linearLayout.addView(createTextView("1"));
        linearLayout.addView(createTextView("1"));
        linearLayout.addView(createTextView("1"));
        linearLayout.addView(createTextView("1"));
        linearLayout.addView(createTextView("1"));
        linearLayout.addView(createTextView("2"));
        LayoutParams layoutParams0 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams0);


        ScrollView scrollView = new ScrollView(this);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        scrollView.setLayoutParams(layoutParams);
        scrollView.addView(linearLayout);
        scrollView.setBackgroundColor(Color.parseColor("#ABCDEF"));
        return scrollView;

    }

    private View createViewpager() {
        ArrayList<View> views = new ArrayList<>();
        views.add(createTextView("第一页"));
        views.add(createTextView("第二页"));
        views.add(createTextView("第三页"));
        ViewPager viewPager = new ViewPager(this);
        LayoutParams layoutParams0 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(layoutParams0);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(views.get(position));
            }
        });

        return viewPager;

    }
}