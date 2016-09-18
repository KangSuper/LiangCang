package com.xiekang.king.liangcang.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.ShopInfo.ShopInfo;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

/**
 * Created by Administrator on 2016/9/14.
 */
public class Fragment_Good_Guide extends Fragment implements JsonCallBack{
    private Context context;
    private String url;
    private TextView textView;
    private Button bt;
    public Fragment_Good_Guide(String url){
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
        View view = inflater.inflate(R.layout.fragment_good_guide,container,false);
        textView = (TextView) view.findViewById(R.id.guide_content);
        bt = (Button) view.findViewById(R.id.konw);
        HttpUtils.load(url).callBack(this,1);
        return view;
    }

    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode==1){
            Gson gson = new Gson();
            ShopInfo shopInfo = gson.fromJson(result, ShopInfo.class);
            ShopInfo.DataBean.ItemsBean items = shopInfo.getData().getItems();
            String content = items.getGood_guide().getContent();
            textView.setText(content);
        }
    }
}
