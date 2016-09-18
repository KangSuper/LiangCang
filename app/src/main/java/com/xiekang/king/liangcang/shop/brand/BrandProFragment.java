package com.xiekang.king.liangcang.shop.brand;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.activity.ShopInfoActivity;
import com.xiekang.king.liangcang.bean.shop.CgDetailsBean;
import com.xiekang.king.liangcang.bean.shop.OrderBean;
import com.xiekang.king.liangcang.detail.Goods_DetailActivity;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.DateUtils;
import com.xiekang.king.liangcang.utils.FragmentCallBack;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.List;


public class BrandProFragment extends Fragment {
    private Context mContext;
    private String id;
    private int page = 1;
    private List<CgDetailsBean.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();
    private PullToRefreshGridView mRefreshView;
    private GridView mGridView;
    private MyAdapter mAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAdapter.notifyDataSetChanged();
            mRefreshView.setLastUpdatedLabel("最后更新:" + DateUtils.getCurrentTime());
            mRefreshView.onRefreshComplete();
        }
    };

    public static BrandProFragment newInstance(String gid) {
        BrandProFragment fragment = new BrandProFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gid", gid);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        loadData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand_details, container, false);
        mRefreshView = (PullToRefreshGridView) view.findViewById(R.id.fragment_brand_details_grid_view);
        mRefreshView.setMode(PullToRefreshBase.Mode.BOTH);
        mRefreshView.setOnRefreshListener(refreshListener2);
        mGridView = mRefreshView.getRefreshableView();
        mGridView.setVerticalSpacing(10);
        mGridView.setHorizontalSpacing(10);
        mAdapter = new MyAdapter();
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(itemClickListener);
        return view;
    }

   private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
       @Override
       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           CgDetailsBean.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);
           Intent intent = new Intent();
           if (itemsBean.getSale_by().equals("other")) {
               intent.setClass(mContext, Goods_DetailActivity.class);
           } else {
               intent.setClass(mContext, ShopInfoActivity.class);
           }
           intent.putExtra("id", itemsBean.getGoods_id());
           startActivity(intent);
       }
   };


    private void loadData() {
        itemsBeanList.clear();
        id = getArguments().getString("gid");
        String shopDetailsUrl = GetUrl.getShopBraDetailsUrl(id, page);
        HttpUtils.load(shopDetailsUrl).callBack(new JsonCallBack() {
            @Override
            public void successJson(String result, int requestCode) {
                if (requestCode == 34) {
                    Gson gson = new Gson();
                    CgDetailsBean cgDetailsBean = gson.fromJson(result, CgDetailsBean.class);
                    itemsBeanList.addAll(cgDetailsBean.getData().getItems());
                    mAdapter.notifyDataSetChanged();
                }
            }
        }, 34);
    }


    private PullToRefreshBase.OnRefreshListener2 refreshListener2 = new PullToRefreshBase.OnRefreshListener2() {
        //上拉
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            page = 1;
            itemsBeanList.clear();
            HttpUtils.load(GetUrl.getShopBraDetailsUrl(id, page)).callBack(new JsonCallBack() {
                @Override
                public void successJson(String result, int requestCode) {
                    if (requestCode == 35) {
                        Gson gson = new Gson();
                        CgDetailsBean cgDetailsBean = gson.fromJson(result, CgDetailsBean.class);
                        itemsBeanList.addAll(cgDetailsBean.getData().getItems());
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }, 35);
        }
        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            page++;
            HttpUtils.load(GetUrl.getShopBraDetailsUrl(id, page)).callBack(new JsonCallBack() {
                @Override
                public void successJson(String result, int requestCode) {
                    if (requestCode == 36) {
                        Gson gson = new Gson();
                        CgDetailsBean cgDetailsBean = gson.fromJson(result, CgDetailsBean.class);
                        itemsBeanList.addAll(cgDetailsBean.getData().getItems());
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }, 36);
        }
    };


   

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
            ViewHodler viewHodler;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.product_item_view, parent, false);
                viewHodler = new ViewHodler(convertView);
            } else {
                viewHodler = (ViewHodler) convertView.getTag();
            }
            CgDetailsBean.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);
            Glide.with(mContext).load(itemsBean.getGoods_image()).into(viewHodler.iconImg);
            viewHodler.goodNameTxt.setText(itemsBean.getGoods_name());
            viewHodler.brandNameTxt.setText(itemsBean.getBrand_info().getBrand_name());
            viewHodler.priceTxt.setText("¥" + itemsBean.getPrice());
            viewHodler.countTxt.setText(itemsBean.getLike_count());
            return convertView;
        }
        class ViewHodler {
            public ImageView iconImg;
            public TextView goodNameTxt;
            public TextView brandNameTxt;
            public TextView priceTxt;
            public TextView countTxt;
            public ViewHodler(View view) {
                view.setTag(this);
                iconImg = (ImageView) view.findViewById(R.id.product_item_icon_img);
                goodNameTxt = (TextView) view.findViewById(R.id.product_item_good_name_txt);
                brandNameTxt = (TextView) view.findViewById(R.id.product_item_brand_name_txt);
                priceTxt = (TextView) view.findViewById(R.id.product_item_price_txt);
                countTxt = (TextView) view.findViewById(R.id.product_item_count_txt);
            }
        }
    }
}
