package com.xiekang.king.liangcang.share.Http;


import com.xiekang.king.liangcang.bean.Share.ShareBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface HttpService {
    @GET("/goods/goodsShare?app_key=Android&count=10&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&type=0&v=1.0")
    Call<ShareBean>queryAll(@Query("page") int page);

    interface ICallback{
        void success(ShareBean shareBean, int requestcode);
    }
}
