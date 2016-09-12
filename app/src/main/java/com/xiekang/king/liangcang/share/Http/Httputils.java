package com.xiekang.king.liangcang.share.Http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/9/8.
 */
public class Httputils {
    public static HttpService httpService;
    public static final String BASE_URL = "http://mobile.iliangcang.com";
    public static HttpService create(){
        if (httpService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            httpService = retrofit.create(HttpService.class);

        }
        return httpService;
    }
}
