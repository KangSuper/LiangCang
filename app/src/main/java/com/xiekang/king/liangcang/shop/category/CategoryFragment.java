package com.xiekang.king.liangcang.shop.category;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.shop.CategoryBean;
import com.xiekang.king.liangcang.shop.ProductFragment;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.BitmapUtils;
import com.xiekang.king.liangcang.utils.DateUtils;
import com.xiekang.king.liangcang.utils.FragmentCallBack;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment implements JsonCallBack {
    private static final String TAG = "androidxx";
    private Context mContext;
    private GridView mGridView;
    private List<CategoryBean.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();
    private MyAdapter mAdapter;
    private PullToRefreshGridView mRefreshView;
    private FragmentCallBack fragmentCallBack;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAdapter.notifyDataSetChanged();
            mRefreshView.setLastUpdatedLabel("最后更新:" + DateUtils.getCurrentTime());
            mRefreshView.onRefreshComplete();
        }
    };

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        if (mContext instanceof FragmentCallBack){
            fragmentCallBack = (FragmentCallBack) mContext;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        HttpUtils.load(GetUrl.SHOP_CATAGORY_URL).callBack(this, 1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        mRefreshView = (PullToRefreshGridView) view.findViewById(R.id.shop_category_grid_view);
        mRefreshView.setOnRefreshListener(refreshListener2);
        mGridView = mRefreshView.getRefreshableView();
        mGridView.setNumColumns(2);
        mGridView.setHorizontalSpacing(20);
        mGridView.setVerticalSpacing(8);

        mAdapter = new MyAdapter();
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(itemClickListener);
        return view;
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String cat_id = itemsBeanList.get(position).getCat_id();
            fragmentCallBack.dataCall(cat_id);
        }
    };

    private PullToRefreshBase.OnRefreshListener2 refreshListener2 = new PullToRefreshBase.OnRefreshListener2() {
        //上拉
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            itemsBeanList.clear();
            HttpUtils.load(GetUrl.SHOP_CATAGORY_URL).callBack(new JsonCallBack() {
                @Override
                public void successJson(String result, int requestCode) {
                    if (requestCode == 3) {
                        Gson gson = new Gson();
                        CategoryBean categoryBean = gson.fromJson(result, CategoryBean.class);
                        itemsBeanList.addAll(categoryBean.getData().getItems());
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }, 3);
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {

        }
    };


    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode == 1) {
            itemsBeanList.clear();
            Gson gson = new Gson();
            CategoryBean categoryBean = gson.fromJson(result, CategoryBean.class);
            itemsBeanList.addAll(categoryBean.getData().getItems());
            mRefreshView.setLastUpdatedLabel("最后更新:" + DateUtils.getCurrentTime());
            mAdapter.notifyDataSetChanged();
        }
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return itemsBeanList == null ? 0 : itemsBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemsBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = (ImageView) convertView;
            if (imageView == null) {
                imageView = new ImageView(mContext);
            }
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String coverNewImg = itemsBeanList.get(position).getCover_new_img();
            Glide.with(mContext).load(coverNewImg).into(imageView);
            return imageView;
        }
    }
}
