package com.xiekang.king.liangcang.share.Impl;


import com.xiekang.king.liangcang.bean.Share.ShareBean;
import com.xiekang.king.liangcang.share.Http.HttpService;
import com.xiekang.king.liangcang.share.Http.Httputils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/9/8.
 */
public class ShareImpl {
    public  static void down(final HttpService.ICallback icall, final int requestcode, int page){
        Httputils.create().queryAll(page).enqueue(new Callback<ShareBean>() {
            @Override
            public void onResponse(Call<ShareBean> call, Response<ShareBean> response) {
                    icall.success(response.body(),requestcode);
            }

            @Override
            public void onFailure(Call<ShareBean> call, Throwable t) {

            }
        });
    }
}
