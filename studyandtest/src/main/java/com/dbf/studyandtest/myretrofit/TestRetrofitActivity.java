package com.dbf.studyandtest.myretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.dbf.studyandtest.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * @author raden
 */
public class TestRetrofitActivity extends AppCompatActivity {
    private final String TAG = TestRetrofitActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_retrofit);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WanAndroidServiceApi wanAndroidServiceApi = retrofit.create(WanAndroidServiceApi.class);
        Call call = wanAndroidServiceApi.getThePublicList();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });
    }

    interface WanAndroidServiceApi {
        @GET("/wxarticle/chapters/json")
        Call<ResponseBody> getThePublicList();
    }
}