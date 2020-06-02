package com.dbf.myandroid.network_interface_api

import androidx.lifecycle.LiveData
import com.dbf.myandroid.bean.WanAndroidProjectBean
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface StudyApi {
    @GET("project/tree/json")
    fun getProject(): Call<WanAndroidProjectBean>

    @GET("")
    fun getProject1(): LiveData<WanAndroidProjectBean>

    @HTTP(method = "get", path = "project/tree/json", hasBody = false)
    fun example(): Call<ResponseBody>

    //表示响应体的数据用流的方式返回，适用于返回的数据比较大，该注解在在下载大文件的特别有用
    @Streaming
    @GET
    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>

    @Headers("")//使用 `@Headers`注解设置固定的请求头，所有请求头不会相互覆盖，即使名字相同。
    @GET("")
    fun example1(): Call<ResponseBody>

    @GET("")//使用 `@Header`注解动态更新请求头，匹配的参数必须提供给 @Header ，若参数值为 null ，这个头会被省略，否则，会使用参数值的 toString 方法的返回值
    fun example2(@Header("xx") xx: String): Call<ResponseBody>

    @POST("") //多用于post请求发送非表单数据,比如想要以post方式传递json格式数据
    fun example3(@Body wanAndroidProjectBean: WanAndroidProjectBean): Call<ResponseBody>

    @FormUrlEncoded //配合@Field以表单的形式发送Http请求
    @POST
    fun loggin(
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("array") array: Array<String>,
        @FieldMap map: Map<String, String>
    ): Call<ResponseBody>

    @GET("xxx/{id}/xx")//替换url中的id
    fun example4(@Path("id") id: Int): Call<ResponseBody>


    @GET("xxx")//在整个url后面加?name=xx&page=xx
    fun example5(
        @Query("name") name: String,
        @QueryMap map: Map<String, String>,
        @QueryName() page: Int
    ): Call<ResponseBody>

    /**
     * Multipart：表示请求实体是一个支持文件上传的Form表单，需要配合使用@Part,适用于 有文件 上传的场景
     * Part:用于表单字段,Part和PartMap与Multipart注解结合使用,适合文件上传的情况
     * PartMap:用于表单字段,默认接受的类型是Map<String,REquestBody>，可用于实现多文件上传
     * Part 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型；
     *
     * @param file 服务器指定的上传图片的key值
     * @return
     */
    @Multipart
    @POST("xx/xx")
    fun upload(@Part("file\";filename\";test.png") file: RequestBody): Call<ResponseBody>

    @Multipart
    @POST("xx/xx")
    fun upload2(@Part file:MultipartBody.Part):Call<ResponseBody>

}
