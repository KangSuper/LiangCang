package com.xiekang.king.liangcang.activity;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.ShopInfo.ShopInfo;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayActivity extends AppCompatActivity implements JsonCallBack,View.OnClickListener{
    @BindView(R.id.pay_img)ImageView img;
    @BindView(R.id.pay_back) ImageView back;
    @BindView(R.id.pay_brand_name)TextView brand_name;
    @BindView(R.id.pay_good_name)TextView good_name;
    @BindView(R.id.pay_price)TextView price;
    @BindView(R.id.pay_type)TextView type;
    @BindView(R.id.pay_radio_group)RadioGroup radioGroup;
    @BindView(R.id.pay_rb1)RadioButton rb1;
    @BindView(R.id.pay_rb2)RadioButton rb2;
    @BindView(R.id.pay_reduce)Button reduce;
    @BindView(R.id.pay_count)Button count;
    @BindView(R.id.pay_add)Button add;
    @BindView(R.id.pay_sure)Button sure;
    private String id;
    private String url;
    private ShopInfo.DataBean.ItemsBean items;
    private ActionBar supportActionBar;
    private String attr_id;
    private String attr_id1;

    private int num = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        id = getIntent().getExtras().getString("id");

        initview();
    }

    private void initview() {
        ButterKnife.bind(this);
        sure.setOnClickListener(this);
        rb1.setChecked(true);
        url = GetUrl.getGoodsDetail(id);
        rb1.setOnClickListener(this);
        back.setOnClickListener(this);
        reduce.setOnClickListener(this);
        add.setOnClickListener(this);
        initdata();
    }

    private void initdata() {
        HttpUtils.load(url).callBack(this,1);
    }

    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode==1){
            Gson gson = new Gson();
            ShopInfo shopInfo = gson.fromJson(result, ShopInfo.class);
            items = shopInfo.getData().getItems();

            Picasso.with(this).load(items.getGoods_image()).into(img);
            brand_name.setText(items.getBrand_info().getBrand_name());
            good_name.setText(items.getGoods_name());

           // price.setText("¥ "+items.getSku_inv().get(0).getPrice());
            ShopInfo.DataBean.ItemsBean.SkuInfoBean skuInfoBean = items.getSku_info().get(0);
            type.setText(skuInfoBean.getType_name());
            rb1.setText(skuInfoBean.getAttrList().get(0).getAttr_name());
            if (skuInfoBean.getAttrList().size()>1){
                rb2.setText(skuInfoBean.getAttrList().get(1).getAttr_name());
                attr_id1 = skuInfoBean.getAttrList().get(1).getAttr_id();
                rb2.setOnClickListener(this);
            }else {
                rb2.setVisibility(View.GONE);
            }

            attr_id = skuInfoBean.getAttrList().get(0).getAttr_id();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pay_back:
                finish();
                break;
            case R.id.pay_rb1:
                price.setText("¥ "+items.getSku_inv().get(0).getPrice());
                break;
            case R.id.pay_rb2:
                price.setText("¥ "+items.getSku_inv().get(1).getPrice());
                break;
            case R.id.pay_reduce:
                num--;
                if (num<0){
                    count.setText("0");
                }else {
                    count.setText(num+"");
                }
                break;
            case R.id.pay_add:
                num++;
                count.setText(num+"");
                break;
            case R.id.pay_sure:
                Toast.makeText(PayActivity.this, "加入购物车", Toast.LENGTH_SHORT).show();
        }
    }
}
