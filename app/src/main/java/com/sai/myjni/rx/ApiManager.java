package com.sai.myjni.rx;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiManager {

    @Headers("apikey:81bf9da930c7f9825a3c3383f1d8d766")
    @GET("word/word")
    Observable<BaseResponse> getNews(@Query("num") String num, @Query("page")String page);


    //PDF文件Retrofit下载
    // @Streaming 是注解大文件的，小文件可以忽略不加注释，但是大文件一定需要注释，不然会出现OOM。
    @Streaming
    @GET
    Observable<ResponseBody> retrofitDownloadFile(@Url String fileUrl);

    @Multipart
    @POST
    Observable<ResponseBody> uploadFile(@Url String url, @Body MultipartBody.Part body);
}
