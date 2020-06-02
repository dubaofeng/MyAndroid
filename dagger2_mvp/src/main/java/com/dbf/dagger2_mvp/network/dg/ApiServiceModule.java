package com.dbf.dagger2_mvp.network.dg;

import com.dbf.dagger2_mvp.network.WanAndroidApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import retrofit2.Retrofit;

@Module
public class ApiServiceModule {
    @Provides
    HttpUrl provideBaseUrl() {
        return HttpUrl.parse("https://www.wanandroid.com/");
    }

    @Provides
    WanAndroidApi provideWanAndroidApi(Retrofit retrofit) {
        return retrofit.create(WanAndroidApi.class);
    }
}
