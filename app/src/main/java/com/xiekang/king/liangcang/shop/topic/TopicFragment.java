package com.xiekang.king.liangcang.shop.topic;


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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.activity.WebActivity;
import com.xiekang.king.liangcang.bean.topic.TopicBean;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.BitmapUtils;
import com.xiekang.king.liangcang.utils.DateUtils;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.List;


public class TopicFragment extends Fragment {
    private Context mContext;
    private PullToRefreshListView mRefreshView;
    private int page = 1;
    private List<TopicBean.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();
    private ListView mListView;
    private MyAdapter myAdapter;

    public static TopicFragment newInstance() {
        TopicFragment fragment = new TopicFragment();
        return fragment;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myAdapter.notifyDataSetChanged();
            mRefreshView.setLastUpdatedLabel("最后更新:" + DateUtils.getCurrentTime());
            mRefreshView.onRefreshComplete();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        HttpUtils.load(GetUrl.getShopTopicUrl(page)).callBack(new JsonCallBack() {
            @Override
            public void successJson(String result, int requestCode) {
                if (requestCode == 10) {
                    Gson gson = new Gson();
                    TopicBean topicBean = gson.fromJson(result, TopicBean.class);
                    itemsBeanList.addAll(topicBean.getData().getItems());
                    mRefreshView.setLastUpdatedLabel("最后更新:" + DateUtils.getCurrentTime());
                    myAdapter.notifyDataSetChanged();
                }
            }
        }, 10);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        mRefreshView = (PullToRefreshListView) view.findViewById(R.id.topic_list_view);
        mRefreshView.setOnRefreshListener(refreshListener2);
        mRefreshView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView = mRefreshView.getRefreshableView();
        myAdapter = new MyAdapter();
        mListView.setAdapter(myAdapter);
        mListView.setDividerHeight(10);
        mListView.setOnItemClickListener(itemClickListener);
        return view;
    }


    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(mContext, WebActivity.class);
            intent.putExtra("name",itemsBeanList.get(position).getTopic_name());
            intent.putExtra("url",itemsBeanList.get(position).getTopic_url());
            startActivity(intent);
        }
    };

    private PullToRefreshBase.OnRefreshListener2 refreshListener2 = new PullToRefreshBase.OnRefreshListener2() {
        //上拉
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            page = 1;
            itemsBeanList.clear();
            HttpUtils.load(GetUrl.getShopTopicUrl(page)).callBack(new JsonCallBack() {
                @Override
                public void successJson(String result, int requestCode) {
                    if (requestCode == 11) {
                        Gson gson = new Gson();
                        TopicBean topicBean = gson.fromJson(result, TopicBean.class);
                        itemsBeanList.addAll(topicBean.getData().getItems());
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }, 11);
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            page ++;
            HttpUtils.load(GetUrl.getShopTopicUrl(page)).callBack(new JsonCallBack() {
                @Override
                public void successJson(String result, int requestCode) {
                    if (requestCode == 12) {
                        Gson gson = new Gson();
                        TopicBean topicBean = gson.fromJson(result, TopicBean.class);
                        itemsBeanList.addAll(topicBean.getData().getItems());
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }, 12);
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
            if (convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_topic_item,parent,false);
                viewHodler = new ViewHodler(convertView);
            }else {
                viewHodler = (ViewHodler) convertView.getTag();
            }
            TopicBean.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);
            viewHodler.topicTxt.setText(itemsBean.getTopic_name());
            Glide.with(mContext).load(itemsBean.getCover_img_new()).into(viewHodler.imageView);
            return convertView;
        }

        class ViewHodler{
            public ImageView imageView;
            public TextView topicTxt;

            public ViewHodler(View view) {
                view.setTag(this);
                imageView = (ImageView) view.findViewById(R.id.topic_item_img);
                imageView.setAlpha(0.8f);
                topicTxt = (TextView) view.findViewById(R.id.topic_item_txt);
            }
        }
    }


}
