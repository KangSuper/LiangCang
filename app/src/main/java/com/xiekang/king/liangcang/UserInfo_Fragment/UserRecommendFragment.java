package com.xiekang.king.liangcang.UserInfo_Fragment;

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
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.squareup.picasso.Picasso;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.activity.ShopInfoActivity;
import com.xiekang.king.liangcang.bean.UserInfo.UserLikeAndRecommendBean;
import com.xiekang.king.liangcang.detail.Goods_DetailActivity;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.DateUtils;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class UserRecommendFragment extends Fragment implements JsonCallBack{
    private Context context;
    public String url;
    public int page = 1;
    private PullToRefreshGridView refreshGridView;
    private GridView gridview;
    private List<UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean> goodsBeanList = new ArrayList<>();
    private Myadapter2 myadapter2;
    public String id;
    private List<UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean> goods = new ArrayList<>();
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            myadapter2.notifyDataSetChanged();
            refreshGridView.onRefreshComplete();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    public UserRecommendFragment(String id) {
        this.id = id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_fragment, container, false);
        myadapter2 = new Myadapter2();
        initview(view);
        gridview.setAdapter(myadapter2);
        gridview.setVerticalSpacing(10);
        gridview.setHorizontalSpacing(10);
        return view;
    }

    private void initview(View view) {
        refreshGridView = (PullToRefreshGridView) view.findViewById(R.id.use_fragment_pullgrid);
        gridview = refreshGridView.getRefreshableView();
        gridview.setNumColumns(2);
        gridview.setVerticalSpacing(15);
        gridview.setHorizontalSpacing(15);
        initdata();
        refreshGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                page++;
                HttpUtils.load(GetUrl.getUserTuijian(id, page)).callBack(new JsonCallBack() {
                    @Override
                    public void successJson(String result, int requestCode) {
                        if (requestCode == 2) {
                            Gson gson = new Gson();
                            UserLikeAndRecommendBean bean = gson.fromJson(result, UserLikeAndRecommendBean.class);
                            List<UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean> goods = bean.getData().getItems().getGoods();
                            if (goods!= null){
                                goodsBeanList.addAll(goods);
                            }
                            mHandler.sendEmptyMessage(1);
                        }
                    }
                }, 2);
                url = GetUrl.getUserTuijian(id,page);
                HttpUtils.load(url).callBack(UserRecommendFragment.this,1);
            }
        });
    }

    private void initdata() {
        HttpUtils.load(GetUrl.getUserTuijian(id, page)).callBack(this, 1);
    }


    class Myadapter2 extends BaseAdapter {

        @Override
        public int getCount() {
            return goods==null?0:goods.size();
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
            ViewHolderUser2 viewHolderUser;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.userinofragment_like_item, parent, false);
                viewHolderUser = new ViewHolderUser2(view);

            } else {
                viewHolderUser = (ViewHolderUser2) view.getTag();
            }
            final UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean goodsBean = goods.get(position);
            Picasso.with(context).load(goodsBean.getGoods_image()).into(viewHolderUser.imageView);
            viewHolderUser.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sale_by = goodsBean.getSale_by();
                    String goods_id = goodsBean.getGoods_id();
                    if (sale_by.equals("other")){
                        Intent intent = new Intent(context, Goods_DetailActivity.class);
                        intent.putExtra("id",goods_id);
                        startActivity(intent);
                    }else {
                        Intent intent1 = new Intent(context, ShopInfoActivity.class);
                        intent1.putExtra("id",goods_id);
                        startActivity(intent1);
                    }
                }
            });
            return view;
        }
    }

    class ViewHolderUser2 {
        ImageView imageView;
        public ViewHolderUser2(View view) {
            view.setTag(this);
            imageView = (ImageView) view.findViewById(R.id.use_like_item_img);
        }
    }

    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode == 1) {
            Gson gson = new Gson();
            UserLikeAndRecommendBean bean = gson.fromJson(result, UserLikeAndRecommendBean.class);
            List<UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean> goods1 = bean.getData().getItems().getGoods();
            if (goods1!=null){
                goods.addAll(goods1);
            }
            mHandler.sendEmptyMessageDelayed(1,1000);
        }
//
        }
    }


