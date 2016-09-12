package com.xiekang.king.liangcang.shop.brand;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiekang.king.liangcang.bean.shop.CgDetailsBean;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.List;


public class BrandStoryFragment extends Fragment {
    private Context mContext;
    private List<CgDetailsBean.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();
    public static BrandStoryFragment newInstance(String desc) {
        BrandStoryFragment fragment = new BrandStoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("desc", desc);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(mContext);
        String desc = getArguments().getString("desc");
        textView.setText(desc);
        return textView;
    }
}
