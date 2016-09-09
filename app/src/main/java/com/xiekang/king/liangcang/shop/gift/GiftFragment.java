package com.xiekang.king.liangcang.shop.gift;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.xiekang.king.liangcang.R;

import java.util.ArrayList;
import java.util.List;


public class GiftFragment extends Fragment {
    private Context mContext;
    private ImageView mHeaderImg;
    private GridView mGridView;
    private List<Integer> imgResList = new ArrayList<>();
    private MyAdapter myAdapter;

    public static GiftFragment newInstance() {
        GiftFragment fragment = new GiftFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        loadDatas();
    }

    private void loadDatas() {
        imgResList.add(R.drawable.ic_present_1);
        imgResList.add(R.drawable.ic_present_2);
        imgResList.add(R.drawable.ic_present_3);
        imgResList.add(R.drawable.ic_present_4);
        imgResList.add(R.drawable.ic_present_5);
        imgResList.add(R.drawable.ic_present_6);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift, container, false);
        mHeaderImg = (ImageView) view.findViewById(R.id.gift_header_img);
        mGridView = (GridView) view.findViewById(R.id.gift_grid_view);
        myAdapter = new MyAdapter();
        mGridView.setAdapter(myAdapter);
        return view;
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imgResList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = (ImageView) convertView;
            if (imageView == null){
                imageView = new ImageView(mContext);
            }
            imageView.setImageResource(imgResList.get(position));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setMaxHeight(150);
            return imageView;
        }
    }
}
