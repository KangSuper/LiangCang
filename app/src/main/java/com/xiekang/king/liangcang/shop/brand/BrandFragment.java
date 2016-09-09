package com.xiekang.king.liangcang.shop.brand;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.brand.BrandBean;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.BitmapUtils;
import com.xiekang.king.liangcang.utils.DateUtils;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.List;


public class BrandFragment extends Fragment {
    private Context mContext;
    private ListView mListView;
    private PullToRefreshListView mRefreshView;
    private MyAdapter mAdapter;
    private List<BrandBean.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();
    private int page = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAdapter.notifyDataSetChanged();
            mRefreshView.setLastUpdatedLabel("最后更新:" + DateUtils.getCurrentTime());
            mRefreshView.onRefreshComplete();
        }
    };

    public static BrandFragment newInstance() {
        BrandFragment fragment = new BrandFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        String shopBrandUrl = GetUrl.getShopBrandUrl(page);
        HttpUtils.load(shopBrandUrl).callBack(new JsonCallBack() {
            @Override
            public void successJson(String result, int requestCode) {
                Gson gson = new Gson();
                BrandBean brandBean = gson.fromJson(result, BrandBean.class);
                itemsBeanList.addAll(brandBean.getData().getItems());
                mAdapter.notifyDataSetChanged();
                mRefreshView.setLastUpdatedLabel("最后更新:" + DateUtils.getCurrentTime());
            }
        }, 5);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand, container, false);
        mRefreshView = (PullToRefreshListView) view.findViewById(R.id.shop_brand_list_view);
        mRefreshView.setMode(PullToRefreshBase.Mode.BOTH);
        mRefreshView.setOnRefreshListener(refreshListener2);
        mListView = mRefreshView.getRefreshableView();
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        return view;
    }


    private PullToRefreshBase.OnRefreshListener2 refreshListener2 = new PullToRefreshBase.OnRefreshListener2() {
        //上拉
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            itemsBeanList.clear();
            page = 1;
            HttpUtils.load(GetUrl.getShopBrandUrl(page)).callBack(new JsonCallBack() {
                @Override
                public void successJson(String result, int requestCode) {
                    if (requestCode == 4) {
                        Gson gson = new Gson();
                        BrandBean brandBean = gson.fromJson(result, BrandBean.class);
                        itemsBeanList.addAll(brandBean.getData().getItems());
                        mHandler.sendEmptyMessageDelayed(1, 100);
                    }
                }
            }, 4);
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            page++;
            HttpUtils.load(GetUrl.getShopBrandUrl(page)).callBack(new JsonCallBack() {
                @Override
                public void successJson(String result, int requestCode) {
                    if (requestCode == 6) {
                        Gson gson = new Gson();
                        BrandBean brandBean = gson.fromJson(result, BrandBean.class);
                        List<BrandBean.DataBean.ItemsBean> items = brandBean.getData().getItems();
                        itemsBeanList.addAll(items);
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }, 6);
        }
    };


    class MyAdapter extends BaseAdapter {

        private static final String TAG = "androidxx";

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
            View view = convertView;
            ViewHodler viewHodler;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.brand_item_view, parent, false);
                viewHodler = new ViewHodler(view);
            } else {
                viewHodler = (ViewHodler) view.getTag();
            }
            BrandBean.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);
            String brand_logo = itemsBean.getBrand_logo();
            ImageView imageView = viewHodler.imageView;
            imageView.setImageResource(R.drawable.brand_logo_empty);
            BitmapUtils.bitmapIntoImageView(brand_logo, imageView, 7, 80, 80);
//            Picasso.with(mContext).load(brand_logo).resize(80,80).into(viewHodler.imageView);
            viewHodler.textView.setText(itemsBean.getBrand_name());
            return view;
        }


        class ViewHodler {
            public ImageView imageView;
            public TextView textView;

            public ViewHodler(View view) {
                view.setTag(this);
                imageView = (ImageView) view.findViewById(R.id.brand_item_image);
                textView = (TextView) view.findViewById(R.id.brand_item_text);
            }
        }
    }
}
