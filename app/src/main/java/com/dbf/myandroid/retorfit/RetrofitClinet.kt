package com.dbf.myandroid.retorfit

import com.dbf.myandroid.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClinet private constructor() {
    lateinit var retrofit: Retrofit

    companion object {
        const val TAG = "Dbf"
        val instance = lazy(
            LazyThreadSafetyMode.NONE//*使用锁确保只有一个线程可以初始化[Lazy]实例。
        ) { RetrofitClinet() }
    }

    init {
        creatRetrofit()
    }

    private fun creatRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)//baseUrl必须以‘/’斜杠结束
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    inline fun <reified T> getService(service: Class<T>): T = retrofit.create(service)
}