package com.xiekang.king.liangcang.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by King on 2016/8/11.
 */
public class BitmapUtils {
    private static ExecutorService executorService;

    /**
     * 程序的入口
     *
     * @param urlString 请求的url
     */
    public static HttpThread load(String urlString) {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(10);
        }
        HttpThread httpThread = new HttpThread();
        httpThread.start(urlString);
        return httpThread;
    }

    public static class HttpThread {
        private BitmapCallBack callBack;
        private int requestCode;
        private boolean isCompress;
        private int resW;
        private int resH;

        /**
         * 出口
         *
         * @param callBack
         * @param requestCode
         */
        public void callBack(BitmapCallBack callBack, int requestCode) {
            this.callBack = callBack;
            this.requestCode = requestCode;
        }

        private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bitmap bitmap = (Bitmap) msg.obj;
                if (callBack != null) {
                    callBack.successBitmap(bitmap, requestCode);
                }
            }
        };

        /**
         * 图片需要压缩必须执行此方法
         *
         * @param resW
         * @param rewH
         * @return
         */
        public HttpThread compress(int resW, int rewH) {
            isCompress = true;
            this.resW = resW;
            this.resH = rewH;
            return this;
        }


        /**
         * 启动线程
         *
         * @param urlString
         * @return
         */
        private HttpThread start(String urlString) {
            executorService.execute(new ImageRunnable(urlString));
            return this;
        }

        /**
         * 网络请求线程
         */
        class ImageRunnable implements Runnable {
            private String urlString;

            public ImageRunnable(String urlString) {
                this.urlString = urlString;
            }

            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = conn.getInputStream();
                        int len = 0;
                        byte buffer[] = new byte[1024];
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        while ((len = inputStream.read(buffer)) != -1) {
                            baos.write(buffer, 0, len);
                        }
                        baos.flush();
                        inputStream.close();
                        byte[] bytes = baos.toByteArray();
                        Bitmap bitmap;
                        if (isCompress) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                            options.inSampleSize = caculateInSampleSize(options, resW, resH);
                            options.inJustDecodeBounds = false;
                            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                        } else {
                            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        }
                        Message message = mHandler.obtainMessage();
                        message.obj = bitmap;
                        message.sendToTarget();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private static int caculateInSampleSize(BitmapFactory.Options options, int rqsW, int rqsH) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (rqsW == 0 || rqsH == 0) return 1;
        if (height > rqsH || width > rqsW) {
            final int heightRatio = Math.round((float) height / (float) rqsH);
            final int widthRatio = Math.round((float) width / (float) rqsW);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
