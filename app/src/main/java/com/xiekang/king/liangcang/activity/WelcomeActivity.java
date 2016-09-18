package com.xiekang.king.liangcang.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.xiekang.king.liangcang.R;

public class WelcomeActivity extends Activity {

    private ImageView imageView;
    private boolean isFirst = true;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isFirst){
                Intent intent = new Intent(WelcomeActivity.this,FirstShowActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SharedPreferences sharedPreferences = getSharedPreferences("liangcang", MODE_PRIVATE);
        isFirst = sharedPreferences.getBoolean("isFirst",true);

        imageView = (ImageView) findViewById(R.id.welcome_image_view);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
        int duration = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            duration += animationDrawable.getDuration(i);
        }
        mHandler.sendEmptyMessageDelayed(1,duration+500);

    }
}
