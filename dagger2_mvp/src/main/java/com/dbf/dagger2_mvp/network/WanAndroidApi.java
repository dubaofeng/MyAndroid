package com.dbf.dagger2_mvp.network;

import com.dbf.dagger2_mvp.network.bean.BaseResponse;

import io.reactivex.Maybe;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WanAndroidApi {
    @POST("user/register")
    @FormUrlEncoded
    Maybe<BaseResponse> register(@Field("username") String name, @Field("password") String password, @Field("repassword") String repassword);
}
