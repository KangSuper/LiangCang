package com.xiekang.king.liangcang.shop;


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


public class ProductFragment extends Fragment {
    private Context mContext;
    private String id;
    private int page = 1;
    private List<CgDetailsBean.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();
    private List<OrderBean> orderBeanList = new ArrayList<>();
    private ImageView mBackImg;
    private ImageView mCartImg;
    private ListView mOrderList;
    private PullToRefreshGridView mRefreshView;
    private GridView mGridView;
    private MyAdapter mAdapter;
    private String params = null;
    private TextView mCategoryTxt;
    private RelativeLayout mRelative;
    private FragmentCallBack fragmentCallBack;
    private boolean isShow;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAdapter.notifyDataSetChanged();
            mRefreshView.setLastUpdatedLabel("最后更新:" + DateUtils.getCurrentTime());
            mRefreshView.onRefreshComplete();
        }
    };

    public static ProductFragment newInstance(String gid) {
        ProductFragment fragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gid", gid);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        if (mContext instanceof FragmentCallBack) {
            fragmentCallBack = (FragmentCallBack) mContext;
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadData();
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        mBackImg = (ImageView) view.findViewById(R.id.product_back_img);
        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCallBack.backCall();
            }
        });
        mCartImg = (ImageView) view.findViewById(R.id.product_cart_img);
        mCategoryTxt = (TextView) view.findViewById(R.id.product_category_txt);
        mRelative = (RelativeLayout) view.findViewById(R.id.product_show_relative);
        mRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShow) {
                    mOrderList.setVisibility(View.VISIBLE);
                    isShow = true;
                } else {
                    mOrderList.setVisibility(View.INVISIBLE);
                    isShow = false;
                }
            }
        });

        mOrderList = (ListView) view.findViewById(R.id.product_order_list);
        MyListAdapter myListAdapter = new MyListAdapter();
        mOrderList.setAdapter(myListAdapter);
        mOrderList.setOnItemClickListener(itemClickListener);

        mRefreshView = (PullToRefreshGridView) view.findViewById(R.id.product_good_grid);
        mRefreshView.setMode(PullToRefreshBase.Mode.BOTH);
        mRefreshView.setOnRefreshListener(refreshListener2);
        mGridView = mRefreshView.getRefreshableView();
        mGridView.setVerticalSpacing(10);
        mGridView.setHorizontalSpacing(10);
        mAdapter = new MyAdapter();
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(itemClickListener1);
        return view;
    }

    private AdapterView.OnItemClickListener itemClickListener1 = new AdapterView.OnItemClickListener() {
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

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long lid) {
            if (isShow) {
                OrderBean orderBean = orderBeanList.get(position);
                params = orderBean.getOrderParams();
                page = 1;
                itemsBeanList.clear();
                String shopDetailsUrl = GetUrl.getShopDetailsUrl(id, page, params);
                HttpUtils.load(shopDetailsUrl).callBack(new JsonCallBack() {
                    @Override
                    public void successJson(String result, int requestCode) {
                        if (requestCode == 25) {
                            Gson gson = new Gson();
                            CgDetailsBean cgDetailsBean = gson.fromJson(result, CgDetailsBean.class);
                            itemsBeanList.addAll(cgDetailsBean.getData().getItems());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }, 25);
                changeColor(position);
                mCategoryTxt.setText(orderBean.getShow());
                mOrderList.setVisibility(View.INVISIBLE);
                isShow = false;
            }
        }
    };


    private void changeColor(int position) {
        LinearLayout childView = (LinearLayout) mOrderList.getChildAt(position);
        for (int i = 0; i < mOrderList.getChildCount(); i++) {
            LinearLayout childAt = (LinearLayout) mOrderList.getChildAt(i);
            childAt.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }
        childView.setBackgroundColor(getResources().getColor(R.color.colorGery));
    }


    private void loadData() {
        itemsBeanList.clear();
        id = getArguments().getString("gid");
        if (id.length() == 2) {
            id = 0 + id;
        }
        String shopDetailsUrl = GetUrl.getShopDetailsUrl(id, page, params);
        HttpUtils.load(shopDetailsUrl).callBack(new JsonCallBack() {
            @Override
            public void successJson(String result, int requestCode) {
                if (requestCode == 15) {
                    Gson gson = new Gson();
                    CgDetailsBean cgDetailsBean = gson.fromJson(result, CgDetailsBean.class);
                    itemsBeanList.addAll(cgDetailsBean.getData().getItems());
                    mAdapter.notifyDataSetChanged();
                }
            }
        }, 15);


        orderBeanList.add(new OrderBean("价格筛选", "全部", null));
        orderBeanList.add(new OrderBean("0-200", "0-200", "max=200&orderby=price&"));
        orderBeanList.add(new OrderBean("201-500", "201-500", "max=500&min=201&orderby=price&"));
        orderBeanList.add(new OrderBean("501-1000", "501-1000", "max=1000&min=501&orderby=price&"));
        orderBeanList.add(new OrderBean("1001-3000", "1001-3000", "max=3000&min=1001&orderby=price&"));
        orderBeanList.add(new OrderBean("3000以上", "3000以上", "min=3000&orderby=price&"));
    }


    private PullToRefreshBase.OnRefreshListener2 refreshListener2 = new PullToRefreshBase.OnRefreshListener2() {
        //上拉
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            page = 1;
            itemsBeanList.clear();
            HttpUtils.load(GetUrl.getShopDetailsUrl(id, page, params)).callBack(new JsonCallBack() {
                @Override
                public void successJson(String result, int requestCode) {
                    if (requestCode == 22) {
                        Gson gson = new Gson();
                        CgDetailsBean cgDetailsBean = gson.fromJson(result, CgDetailsBean.class);
                        itemsBeanList.addAll(cgDetailsBean.getData().getItems());
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }, 22);
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            page++;
            HttpUtils.load(GetUrl.getShopDetailsUrl(id, page, params)).callBack(new JsonCallBack() {
                @Override
                public void successJson(String result, int requestCode) {
                    if (requestCode == 23) {
                        Gson gson = new Gson();
                        CgDetailsBean cgDetailsBean = gson.fromJson(result, CgDetailsBean.class);
                        itemsBeanList.addAll(cgDetailsBean.getData().getItems());
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }, 23);
        }
    };


    class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return orderBeanList.size();
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
            ViewHodler viewHodler;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_txt, parent, false);
                viewHodler = new ViewHodler(convertView);
            } else {
                viewHodler = (ViewHodler) convertView.getTag();
            }
            viewHodler.mOrderTxt.setText(orderBeanList.get(position).getOrder());
            return convertView;
        }

        class ViewHodler {
            public TextView mOrderTxt;

            public ViewHodler(View view) {
                view.setTag(this);
                mOrderTxt = (TextView) view.findViewById(R.id.list_item_order_txt);
            }
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
