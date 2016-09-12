package com.xiekang.king.liangcang.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.shop.CgDetailsBean;
import com.xiekang.king.liangcang.shop.brand.BrandProFragment;
import com.xiekang.king.liangcang.shop.brand.BrandStoryFragment;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BrandDetailsActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private Fragment mCurrentShowFragment;
    private CircleImageView mCircleImg;
    private TextView mNameTxt;
    private RadioGroup mRadioGroup;
    private BrandProFragment brandProFragment;
    private BrandStoryFragment brandStoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_details);
        mFragmentManager = getSupportFragmentManager();
        initActionBar();
        initView();
        loadData();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        View view = LayoutInflater.from(this).inflate(R.layout.brand_details_bar, null, false);
        ImageView backImg = (ImageView) view.findViewById(R.id.brand_details_bak_img);
        actionBar.setCustomView(view);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mCircleImg = (CircleImageView) findViewById(R.id.brand_details_circle_img);
        mNameTxt = (TextView) findViewById(R.id.brand_details_name_txt);
        mRadioGroup = (RadioGroup) findViewById(R.id.brand_details_rg_layout);
    }

    private RadioGroup.OnCheckedChangeListener checkListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.brand_details_product:
                    ctrlFragment(brandProFragment);
                    break;
                case R.id.brand_details_story:
                    ctrlFragment(brandStoryFragment);
                    break;
            }
        }
    };


    private void loadData() {
        String id = getIntent().getStringExtra("id");
        brandProFragment = BrandProFragment.newInstance(id);
        brandStoryFragment = brandStoryFragment.newInstance("");
        ctrlFragment(brandProFragment);

        String url = getIntent().getStringExtra("url");
        Glide.with(this).load(url).into(mCircleImg);
        String name = getIntent().getStringExtra("name");
        mNameTxt.setText(name);
        HttpUtils.load(GetUrl.getShopBraDetailsUrl(id, 1)).callBack(new JsonCallBack() {
            @Override
            public void successJson(String result, int requestCode) {
                if (requestCode == 33) {
                    Gson gson = new Gson();
                    CgDetailsBean cgDetailsBean = gson.fromJson(result, CgDetailsBean.class);
                    List<CgDetailsBean.DataBean.ItemsBean> beanList = cgDetailsBean.getData().getItems();
                    if (beanList.size() > 0) {
                        String brand_desc = beanList.get(0).getBrand_info().getBrand_desc();
                        brandStoryFragment = BrandStoryFragment.newInstance(brand_desc);
                    }
                }
            }
        }, 33);

        mRadioGroup.setOnCheckedChangeListener(checkListener);
    }

    /**
     * @param fragment 当前要显示的Fragment
     */
    private void ctrlFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mCurrentShowFragment != null && mCurrentShowFragment.isAdded()) {
            fragmentTransaction.hide(mCurrentShowFragment);
        }
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.brand_details_frame_layout, fragment);
        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.commit();

        mCurrentShowFragment = fragment;
    }


}
