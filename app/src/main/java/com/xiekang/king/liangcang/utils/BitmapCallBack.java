package com.xiekang.king.liangcang.utils;

import android.graphics.Bitmap;

/**
 * Created by King on 2016/8/11.
 * 回调接口
 */
public interface BitmapCallBack {

    /**
     * 请求bitmap成功时回调的接口
     * @param bitmap
     * @param requestCode
     */
    void successBitmap(Bitmap bitmap, int requestCode);

}
