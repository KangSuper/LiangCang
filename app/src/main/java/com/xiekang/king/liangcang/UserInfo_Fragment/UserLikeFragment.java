package com.xiekang.king.liangcang.UserInfo_Fragment;

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
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.squareup.picasso.Picasso;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.UserInfo.UserLikeAndRecommendBean;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.DateUtils;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class UserLikeFragment extends Fragment implements JsonCallBack {
    private Context context;
    public String url;
    public int page = 1;
    private PullToRefreshGridView refreshGridView;
    private GridView gridview;
    private List<UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean> goodsBeanList = new ArrayList<>();
    private Myadapter1 myadapter1;
    public String id;

    public UserLikeFragment(String id) {
        this.id = id;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myadapter1.notifyDataSetChanged();
            refreshGridView.setLastUpdatedLabel("最后更新:" + DateUtils.getCurrentTime());
            refreshGridView.onRefreshComplete();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_fragment, container, false);
        myadapter1 = new Myadapter1();
        initview(view);
        gridview.setAdapter(myadapter1);
        return view;
    }

    private void initview(View view) {
        refreshGridView = (PullToRefreshGridView) view.findViewById(R.id.use_fragment_pullgrid);
        gridview = refreshGridView.getRefreshableView();
        gridview.setNumColumns(2);
        gridview.setVerticalSpacing(10);
        gridview.setHorizontalSpacing(10);
        initdata();
        refreshGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                page++;
                HttpUtils.load(GetUrl.getUserLike(id, page)).callBack(new JsonCallBack() {
                    @Override
                    public void successJson(String result, int requestCode) {
                        if (requestCode == 2) {
                            Gson gson = new Gson();
                            UserLikeAndRecommendBean bean = gson.fromJson(result, UserLikeAndRecommendBean.class);
                            List<UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean> goods = bean.getData().getItems().getGoods();
                            goodsBeanList.addAll(goods);
                            mHandler.sendEmptyMessage(1);
                        }
                    }
                }, 2);
            }
        });
    }

    private void initdata() {
        HttpUtils.load(GetUrl.getUserLike(id, page)).callBack(this, 1);
    }


    class Myadapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return goodsBeanList.size();
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
            ViewHolderUser viewHolderUser;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.userinofragment_like_item, parent, false);
                viewHolderUser = new ViewHolderUser(view);

            } else {
                viewHolderUser = (ViewHolderUser) view.getTag();
            }
            UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean goodsBean = goodsBeanList.get(position);
            Picasso.with(context).load(goodsBean.getGoods_image()).into(viewHolderUser.imageView);

            return view;
        }
    }

    class ViewHolderUser {
        ImageView imageView;

        public ViewHolderUser(View view) {
            view.setTag(this);
            imageView = (ImageView) view.findViewById(R.id.use_like_item_img);
        }
    }

    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode == 1) {
            Gson gson = new Gson();
            UserLikeAndRecommendBean bean = gson.fromJson(result, UserLikeAndRecommendBean.class);
            List<UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean> goods = bean.getData().getItems().getGoods();
            if (goods != null) {
                goodsBeanList.addAll(goods);
            }
            myadapter1.notifyDataSetChanged();
        }
    }
}
