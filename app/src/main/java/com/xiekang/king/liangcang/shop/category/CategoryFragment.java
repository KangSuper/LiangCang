package com.xiekang.king.liangcang.shop.category;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.utils.HttpUtils;


public class CategoryFragment extends Fragment {
    private Context mContext;
    private GridView mGridVIew;

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
//        HttpUtils.load()
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        mGridVIew = (GridView) view.findViewById(R.id.shop_category_grid_view);
        return view;
    }


}
