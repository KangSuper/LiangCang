package com.xiekang.king.liangcang.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by King on 2016/8/4.
 */
public class LruCacheTool {

    private static long memory = Runtime.getRuntime().maxMemory() / 1024;
    public static LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>((int) memory/8) {
        /**
         * 用来计算存入的Bitmap的大小
         * @param key 是一个URL
         * @param value 存入一个Bitmap
         * @return
         */
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount()/1024;

        }
    };


    public static void writeCache(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
    }

    public static Bitmap readCache(String key) {
        return lruCache.get(key);
    }


}
