package com.xiekang.king.liangcang.utils;

import android.graphics.Bitmap;

/**
 * Created by King on 2016/8/11.
 * 回调接口
 */
public interface JsonCallBack {

    /**
     * 请求json数据成功时，回调的接口方法
     * @param result
     * @param requestCode
     */
    void successJson(String result, int requestCode);
}
