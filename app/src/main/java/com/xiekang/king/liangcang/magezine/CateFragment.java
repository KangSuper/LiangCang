package com.xiekang.king.liangcang.magezine;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.magazine.MgzCategory;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.DateUtils;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;
import com.xiekang.king.liangcang.utils.MgzCallBack;
import com.xiekang.king.liangcang.utils.MgzSeCallBack;

import java.util.ArrayList;
import java.util.List;


public class CateFragment extends Fragment implements JsonCallBack {
    private Context mContext;
    private PullToRefreshGridView mRefresh;
    private GridView mGridVIew;
    private List<MgzCategory.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();
    private MyAdapter myAdapter;
    private MgzCallBack mgzCallBack;
    private MgzSeCallBack mgzSeCallBack;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myAdapter.notifyDataSetChanged();
            mRefresh.setLastUpdatedLabel("最后更新:" + DateUtils.getCurrentTime());
            mRefresh.onRefreshComplete();
        }
    };


    public static CateFragment newInstance() {
        CateFragment fragment = new CateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        if (mContext instanceof MgzCallBack) {
            mgzCallBack = (MgzCallBack) mContext;
        }
        if (mContext instanceof MgzSeCallBack) {
            mgzSeCallBack = (MgzSeCallBack) mContext;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        HttpUtils.load(GetUrl.MGZ_CATEGORY_URL).callBack(this, 45);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mgz_category, container, false);
        mRefresh = (PullToRefreshGridView) view.findViewById(R.id.mgz_category_grid_view);
        mRefresh.setOnRefreshListener(refreshListener2);
        mGridVIew = mRefresh.getRefreshableView();
        mGridVIew.setHorizontalSpacing(40);
        mGridVIew.setVerticalSpacing(10);
        myAdapter = new MyAdapter();
        mGridVIew.setAdapter(myAdapter);
        return view;
    }


    private PullToRefreshBase.OnRefreshListener2 refreshListener2 = new PullToRefreshBase.OnRefreshListener2() {
        //上拉
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            HttpUtils.load(GetUrl.MGZ_CATEGORY_URL).callBack(new JsonCallBack() {
                @Override
                public void successJson(String result, int requestCode) {
                    Gson gson = new Gson();
                    List<MgzCategory.DataBean.ItemsBean> items = gson.fromJson(result, MgzCategory.class).getData().getItems();
                    itemsBeanList.clear();
                    itemsBeanList.addAll(items);
                    mHandler.sendEmptyMessage(1);
                }
            }, 45);
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {

        }
    };

    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode == 45) {
            Gson gson = new Gson();
            List<MgzCategory.DataBean.ItemsBean> items = gson.fromJson(result, MgzCategory.class).getData().getItems();
            itemsBeanList.addAll(items);
            myAdapter.notifyDataSetChanged();
        }
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return itemsBeanList == null ? 0 : itemsBeanList.size() + 2;
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
            View view = convertView;
            ViewHodler viewHodler;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.mgz_category_item, parent, false);
                viewHodler = new ViewHodler(view);
            } else {
                viewHodler = (ViewHodler) view.getTag();
            }
            switch (position) {
                case 0:
                    viewHodler.textView.setText("我的收藏");
                    viewHodler.imageView.setImageResource(R.drawable.bg_topic_favour);
                    Drawable drawable = getResources().getDrawable(R.drawable.icon_my_topics);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    viewHodler.textView.setCompoundDrawables(drawable, null, null, null);
                    break;
                case 1:
                    viewHodler.textView.setText("所有杂志");
                    viewHodler.imageView.setImageResource(R.drawable.bg_topic_all);
                    Drawable drawable1 = getResources().getDrawable(R.drawable.icon_all_topics);
                    drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                    viewHodler.textView.setCompoundDrawables(drawable1, null, null, null);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mgzCallBack.mBackCall();
                        }
                    });
                    break;
                default:
                    final MgzCategory.DataBean.ItemsBean itemsBean = itemsBeanList.get(position - 2);
                    viewHodler.textView.setText(itemsBean.getCat_name());
                    viewHodler.textView.setCompoundDrawables(null, null, null, null);
                    Glide.with(mContext).load(itemsBean.getThumb()).into(viewHodler.imageView);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mgzSeCallBack.msDataCall("cat_id",itemsBean.getCat_id(),itemsBean.getCat_name());
                        }
                    });
                    break;
            }
            return view;
        }


        class ViewHodler {
            public ImageView imageView;
            public TextView textView;

            public ViewHodler(View view) {
                view.setTag(this);
                imageView = (ImageView) view.findViewById(R.id.mgz_category_image);
                textView = (TextView) view.findViewById(R.id.mgz_category_text);
            }
        }
    }
}
