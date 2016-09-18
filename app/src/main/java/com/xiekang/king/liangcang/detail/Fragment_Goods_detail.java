package com.xiekang.king.liangcang.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.ShopInfo.ShopInfo;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

/**
 * Created by Administrator on 2016/9/14.
 */
public class Fragment_Goods_detail extends Fragment implements JsonCallBack{
    private Context context;
    private TextView desc,brand_info,recommend;
    private String url;
    private int brand_id;

    public Fragment_Goods_detail(String url){
        this.url = url;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_good_detail,container,false);
        initView(view);
        initdata();
        return view;
    }

    private void initdata() {
        HttpUtils.load(url).callBack(Fragment_Goods_detail.this,1);
    }

    private void initView(View view) {
        desc = (TextView) view.findViewById(R.id.shop_good_desc);
        brand_info = (TextView) view.findViewById(R.id.shop_Info_brand);
        recommend = (TextView) view.findViewById(R.id.shop_info_recommend);
    }

    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode==1){
            Gson gson = new Gson();
            ShopInfo shopInfo = gson.fromJson(result, ShopInfo.class);
            ShopInfo.DataBean.ItemsBean items = shopInfo.getData().getItems();
            String goods_desc = items.getGoods_desc();
            desc.setText(goods_desc);
            String brand_name = items.getBrand_info().getBrand_name();
            String brand_desc = items.getBrand_info().getBrand_desc();
            brand_info.setText(brand_name+"\n"+brand_desc);
            recommend.setText(items.getRec_reason());
        }
    }
}
