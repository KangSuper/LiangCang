package com.xiekang.king.liangcang.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiekang.king.liangcang.R;

import java.util.ArrayList;
import java.util.List;

public class FirstShowActivity extends Activity {

    private ViewPager mViewPager;
    private ImageView mImageView;
    private List<Integer> imgList = new ArrayList<>();
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_show);
        loadData();
        initView();
        SharedPreferences sharedPreferences = getSharedPreferences("liangcang", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("isFirst",false);
        edit.commit();
    }

    private void loadData() {
        imgList.add(R.drawable.feature1);
        imgList.add(R.drawable.feature2);
        imgList.add(R.drawable.feature3);
        imgList.add(R.drawable.feature4);
        imgList.add(R.drawable.feature5);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.first_view_pager);
        myPagerAdapter = new MyPagerAdapter();
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.addOnPageChangeListener(pageChangeListener);
        mImageView = (ImageView) findViewById(R.id.first_image_btn);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstShowActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 4){
                mImageView.setVisibility(View.VISIBLE);
            }else {
                mImageView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(FirstShowActivity.this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            if (position < 4) {
                Glide.with(FirstShowActivity.this).load(imgList.get(position)).asGif().into(imageView);
            }else if (position == 4){
                Glide.with(FirstShowActivity.this).load(imgList.get(position)).into(imageView);
            }
            container.addView(imageView);
            return imageView;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
