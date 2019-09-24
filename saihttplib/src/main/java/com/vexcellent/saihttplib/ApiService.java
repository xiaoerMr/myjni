package com.vexcellent.saihttplib;

import com.vexcellent.saihttplib.core.ResponseBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author 店小二
 * @date 2019/9/19.
 * GitHub：
 * email：
 * description：
 */
public interface ApiService {

//    @Headers("")

    @GET("word/word")
    Observable<String> doLogin1(@Query("num") String num, @Query("page") String page);


//    @Headers("User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0) Gecko/20100101 Firefox/6.0")

    @GET("api/qqmusic/84466266/json")
    Observable<ResponseBean> doLogin();

}
