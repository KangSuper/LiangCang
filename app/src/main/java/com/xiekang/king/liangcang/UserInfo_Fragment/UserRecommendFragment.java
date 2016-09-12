package com.xiekang.king.liangcang.UserInfo_Fragment;

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

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.squareup.picasso.Picasso;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.UserInfo.UserLike;
import com.xiekang.king.liangcang.bean.UserInfo.UserLikeAndRecommendBean;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class UserRecommendFragment extends Fragment implements JsonCallBack,UserInfo{
    private Context context;
    public String url;
    public int page;
    private PullToRefreshGridView refreshGridView;
    private GridView gridview;
    private LinkedList<UserLike> mlist = new LinkedList<>();
    private Myadapter2 myadapter2;
    public String id;
    public UserRecommendFragment(String id,int page){
        this.id= id;
        this.page = page;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.userinfo_fragment,container,false);
        url = GetUrl.getUserTuijian(id,page);
        myadapter2 = new Myadapter2();
        initview(view);
        gridview.setAdapter(myadapter2);
        return view;
    }

    private void initview(View view) {
        refreshGridView = (PullToRefreshGridView) view.findViewById(R.id.use_fragment_pullgrid);
        gridview = refreshGridView.getRefreshableView();
        gridview.setNumColumns(2);
        initdata();
        refreshGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                page++;
                url = GetUrl.getUserTuijian(id,page);
                HttpUtils.load(url).callBack(UserRecommendFragment.this,2);
            }
        });
    }

    private void initdata() {
        HttpUtils.load(url).callBack(this,1);
    }


    class Myadapter2 extends BaseAdapter {

        @Override
        public int getCount() {
            return mlist.size();
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
            if (view==null){
                view = LayoutInflater.from(context).inflate(R.layout.userinofragment_like_item,parent,false);
                viewHolderUser = new ViewHolderUser2(view);

            }else {
                viewHolderUser = (ViewHolderUser2) view.getTag();
            }
            UserLike userLike = mlist.get(position);
            Picasso.with(context).load(userLike.img).into(viewHolderUser.imageView);

            return view;
        }
    }
    class ViewHolderUser2{
        ImageView imageView;
        public ViewHolderUser2(View view){
            view.setTag(this);
            imageView = (ImageView) view.findViewById(R.id.use_like_item_img);
        }
    }
    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode==1){
            Gson gson = new Gson();
            UserLikeAndRecommendBean bean = gson.fromJson(result, UserLikeAndRecommendBean.class);
            List<UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean> goods = new ArrayList<>();
                   goods =  bean.getData().getItems().getGoods();
            for (int i = 0; i < goods.size(); i++) {
                UserLike userLike = new UserLike();
                userLike.id = goods.get(i).getGoods_id();
                userLike.img = goods.get(i).getGoods_image();
                mlist.add(userLike);
                //刷新适配器
                myadapter2.notifyDataSetChanged();
            }
        }
        if (requestCode==2){
            Gson gson = new Gson();
            UserLikeAndRecommendBean bean = gson.fromJson(result, UserLikeAndRecommendBean.class);
            List<UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean> goods = bean.getData().getItems().getGoods();
            for (int i = 0; i < goods.size(); i++) {
                UserLike userLike = new UserLike();
                userLike.id = goods.get(i).getGoods_id();
                userLike.img = goods.get(i).getGoods_image();
                mlist.addLast(userLike);
                //刷新适配器
                myadapter2.notifyDataSetChanged();
            }
        }
    }
    @Override
    public void successUse(int page) {
        this.page = 1;
        url = GetUrl.getUserTuijian(id,page);
        HttpUtils.load(url).callBack(this,page);
    }
}
