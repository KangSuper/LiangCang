package com.xiekang.king.liangcang.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.squareup.picasso.Picasso;
import com.xiekang.king.liangcang.R;

import com.xiekang.king.liangcang.activity.UserInformationActivity;
import com.xiekang.king.liangcang.activity.WebActivity;
import com.xiekang.king.liangcang.bean.detail.Good_DetailsBean;
import com.xiekang.king.liangcang.bean.detail.GoodsComments;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;


import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class Goods_DetailActivity extends Activity implements JsonCallBack,View.OnClickListener{
    @BindView(R.id.share_detail_link)Button link_bt;

    @BindView(R.id.share_detail_img)ImageView img;
    @BindView(R.id.share_detail_goods_name)TextView goods_name;
    @BindView(R.id.share_detail_price)TextView goods_price;
    @BindView(R.id.share_detail_like_count)TextView like_count;
    @BindView(R.id.share_detail_owner)ImageView owner_img;
    @BindView(R.id.share_detail_owner_name)TextView owner_name;
    @BindView(R.id.share_detail_listview)ListView listView;
    @BindView(R.id.share_detail_loadcomment)Button loadcomment;
    @BindView(R.id.share_detail_pullscroll)PullToRefreshScrollView pullscroview;
    private String id;
    private String owner_id;
    private Context context = this;
    private List<GoodsComments.DataBean.ItemsBean> items = new ArrayList<>();
    private Myadapter1 myadapter1;
    private int page = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pullscroview.onRefreshComplete();
            myadapter1.notifyDataSetChanged();
        }
    };
    private String name;
    private String goods_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods__detail);
        myadapter1 = new Myadapter1();
        initView();
        loaddata();
        listView.setAdapter(myadapter1);
        owner_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserInformationActivity.class);
                intent.putExtra("id",owner_id);
                startActivity(intent);
            }
        });
    }
    private void initView() {

        //context = this;
        ButterKnife.bind(this);
        link_bt.setOnClickListener(this);

        pullscroview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ScrollView refreshableView = pullscroview.getRefreshableView();
        pullscroview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                String label = DateUtils.formatDateTime(
                        context.getApplicationContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel("最后更新"+label);
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        Looper.prepare();
                        HttpUtils.load(GetUrl.getGoodsComments(id,1)).callBack(Goods_DetailActivity.this,3);
                        Looper.loop();
                    }
                }).start();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });
    }
    private void loaddata() {
        Intent intent = getIntent();
        id =  intent.getExtras().getString("id");
        HttpUtils.load(GetUrl.getGoodsDetail(id)).callBack(this,1);
        HttpUtils.load(GetUrl.getGoodsComments(id,page)).callBack(this,2);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("url",goods_url);
        startActivity(intent);
    }

    class Myadapter1 extends BaseAdapter{

        @Override
        public int getCount() {
            return items==null?0:items.size();
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
            ViewHolder viewHolder;
            if (view == null){
                view = LayoutInflater.from(context).inflate(R.layout.goods_comment_lsit_item,parent,false);
                viewHolder = new ViewHolder(view);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }
            final GoodsComments.DataBean.ItemsBean itemsBean = items.get(position);
            String user_image = itemsBean.getUser_image();
            Picasso.with(context).load(user_image).into(viewHolder.imageView);
            viewHolder.content.setText(itemsBean.getUser_name()+":"+itemsBean.getMsg());
            viewHolder.time.setText(itemsBean.getCreate_time());

            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user_id = itemsBean.getUser_id()+"";
                    Intent intent = new Intent(context, UserInformationActivity.class);
                    intent.putExtra("id",user_id);
                    startActivity(intent);
                }
            });
            return view;
        }
    }
    class ViewHolder {
        ImageView imageView;
        TextView content,time;
        public ViewHolder(View view){
            view.setTag(this);
            imageView = (ImageView) view.findViewById(R.id.goods_comment_head);
            content = (TextView) view.findViewById(R.id.goods_comment_content);
            time = (TextView) view.findViewById(R.id.goods_comment_time);
        }
    }




    @Override
    public void successJson(String result, int requestCode) {
        //返回商品详情
        if (requestCode==1){
                Gson gson = new Gson();
            Good_DetailsBean good_detailsBean = gson.fromJson(result, Good_DetailsBean.class);
            Good_DetailsBean.DataBean.ItemsBean items = good_detailsBean.getData().getItems();
            Picasso.with(this).load(items.getGoods_image()).into(img);
            name = items.getGoods_name();
            goods_url = items.getGoods_url();

            goods_name.setText(items.getGoods_name());
            goods_price.setText("¥ "+items.getPrice());
            Picasso.with(this).load(items.getHeadimg()).into(owner_img);
            like_count.setText(items.getLike_count());
            owner_id = items.getOwner_id();
            owner_name.setText(items.getOwner_name());
        }
        //加载评论
        if(requestCode == 2){
            Gson gson2 = new Gson();
            GoodsComments goodsComments = gson2.fromJson(result, GoodsComments.class);
            items = goodsComments.getData().getItems();
            myadapter1.notifyDataSetChanged();
        }
        if(requestCode == 3){
            Gson gson3 = new Gson();
            GoodsComments goodsComments = gson3.fromJson(result, GoodsComments.class);
            List<GoodsComments.DataBean.ItemsBean> items1 = goodsComments.getData().getItems();
            items =  items1;
            handler.sendEmptyMessage(1);
        }
    }
}
