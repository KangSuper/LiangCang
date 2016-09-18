package com.xiekang.king.liangcang.activity;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.ShopInfo.ShopInfo;
import com.xiekang.king.liangcang.detail.Fragment_Good_Guide;
import com.xiekang.king.liangcang.detail.Fragment_Goods_detail;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopInfoActivity extends AppCompatActivity implements JsonCallBack,RadioGroup.OnCheckedChangeListener,View.OnClickListener{
    @BindView(R.id.shop_info_back)Button back_bt;
    @BindView(R.id.shop_info_shopcar)Button shopcar_bt;
    @BindView(R.id.shop_info_joinCar)Button joincar_bt;
    @BindView(R.id.shop_info_Buy)Button buy_bt;

    @BindView(R.id.shop_info_share)ImageView share;
    @BindView(R.id.shop_info_viewpager)ViewPager viewPager;
    @BindView(R.id.shop_info_name)TextView name;
    @BindView(R.id.shop_info_goodsname)TextView goodsname;
    @BindView(R.id.shop_info_likecount)TextView likecount;
    @BindView(R.id.shop_info_price)TextView price;
    @BindView(R.id.shop_info_size)RelativeLayout re ;
    @BindView(R.id.shop_info_logo)RelativeLayout logo;
    @BindView(R.id.shop_info_logo_img)ImageView logo_img;
    @BindView(R.id.shop_info_logo_name)TextView logo_name;
    @BindView(R.id.shop_info_radio_group)RadioGroup radioGroup;
    @BindView(R.id.shop_info_good_detail)RadioButton good_detail_bt;
    @BindView(R.id.shop_info_good_guide)RadioButton good_guide;
    @BindView(R.id.shop_info_fram)FrameLayout frameLayout;
    @BindView(R.id.shop_info_customer)ImageView customerImg;


    private String id;
    private String url;
    private List<String> images_item;
    private String like_count;
    private Context context;
    private Myadapter myadapter;
    private FragmentManager supportFragmentManager;
    private int brand_id;
    private ActionBar supportActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
        supportActionBar = getSupportActionBar();
        supportActionBar.hide();

        context = this;
        id = getIntent().getExtras().getString("id");
        url = GetUrl.getGoodsDetail(id);
        initview();
        initdata();
        viewPager.setAdapter(myadapter);
    }
    //加载网络数据
    private void initdata() {
        HttpUtils.load(url).callBack(this,1);
    }
    //初始化视图
    private void initview() {
        ButterKnife.bind(this);
        supportFragmentManager = getSupportFragmentManager();
        radioGroup.setOnCheckedChangeListener(this);
        myadapter = new Myadapter();
        good_detail_bt.setChecked(true);
        back_bt.setOnClickListener(this);
        shopcar_bt.setOnClickListener(this);
        joincar_bt.setOnClickListener(this);
        buy_bt.setOnClickListener(this);
        customerImg.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.shop_info_good_detail:
                FragmentTransaction transaction = supportFragmentManager.beginTransaction();
                Fragment_Goods_detail fragment_goods_detail = new Fragment_Goods_detail(url);
                transaction.replace(R.id.shop_info_fram,fragment_goods_detail);
                transaction.commit();
                break;
            case R.id.shop_info_good_guide:
                FragmentTransaction transaction1 = supportFragmentManager.beginTransaction();
                Fragment_Good_Guide fragment_good_guide = new Fragment_Good_Guide(url);
                transaction1.replace(R.id.shop_info_fram,fragment_good_guide);
                transaction1.commit();
                break;
        }
    }
    //按钮的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shop_info_back:
                finish();
                break;
            case R.id.shop_info_shopcar:

                break;
            case R.id.shop_info_joinCar:

            case R.id.shop_info_Buy:

                break;
            case R.id.shop_info_customer:
                break;
        }
    }

    class Myadapter extends PagerAdapter{

        @Override
        public int getCount() {
            return images_item==null?0:images_item.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.shop_viewpager_item,container,false);
            ImageView imageView;
            imageView = (ImageView) view.findViewById(R.id.viewpager_item_img);
            Picasso.with(context).load(images_item.get(position)).into(imageView);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
//    class Viewholder{
//        public ImageView imageView;
//        public Viewholder(View view){
//            view.setTag(this);
//            imageView = (ImageView) view.findViewById(R.id.viewpager_item_img);
//        }
 //   }

    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode ==1){
            Gson gson = new Gson();
            ShopInfo shopInfo = gson.fromJson(result, ShopInfo.class);
            ShopInfo.DataBean.ItemsBean items = shopInfo.getData().getItems();

            brand_id = items.getBrand_info().getBrand_id();
            images_item = items.getImages_item();
            String brand_name = items.getBrand_info().getBrand_name();
            name.setText(brand_name);
            goodsname.setText(items.getGoods_name());
            like_count = items.getLike_count();
            likecount.setText(like_count);
            price.setText("¥ "+items.getSku_inv().get(0).getPrice());
            Picasso.with(this).load(items.getHeadimg()).into(logo_img);
            logo_name.setText(brand_name);
            myadapter.notifyDataSetChanged();
        }
    }
}
