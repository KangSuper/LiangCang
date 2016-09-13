package com.xiekang.king.liangcang.UserInfo_Fragment;

import android.content.Context;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class UserLikeFragment extends Fragment implements JsonCallBack,UserInfo{
     private Context context;
    public String url;
    public int page;
    private PullToRefreshGridView refreshGridView;
    private GridView gridview;
  // private LinkedList<UserLike>mlist = new LinkedList<>();

    private Myadapter1 myadapter1;
    public String id;
    private String TAG = "androidhello";
    private List<UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean> goods;

    public UserLikeFragment(String id,int page){
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
        url = GetUrl.getUserLike(id,page);
        Log.d(TAG, "onCreateView: "+url);
        myadapter1 = new Myadapter1();
        initview(view);
        gridview.setAdapter(myadapter1);
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
                url = GetUrl.getUserLike(id,page);
                HttpUtils.load(url).callBack(UserLikeFragment.this,2);
            }
        });
    }

    private void initdata() {
        HttpUtils.load(url).callBack(this,1);
    }


    class Myadapter1 extends BaseAdapter{

        @Override
        public int getCount() {
            Log.d(TAG, "getCount: "+(goods==null?0:goods.size()));
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
            ViewHolderUser viewHolderUser;
            if (view==null){
                view = LayoutInflater.from(context).inflate(R.layout.userinofragment_like_item,parent,false);
                viewHolderUser = new ViewHolderUser(view);

            }else {
                viewHolderUser = (ViewHolderUser) view.getTag();
            }
           // UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean goodsBean = goods.get(position);
            Log.d(TAG, "position: "+position);

            Log.d(TAG, "getView: "+goods.get(position).getGoods_image());
            Picasso.with(context).load(goods.get(position).getGoods_image()).into(viewHolderUser.imageView);

            return view;
        }
    }
    class ViewHolderUser{
        ImageView imageView;
        public ViewHolderUser(View view){
            view.setTag(this);
            imageView = (ImageView) view.findViewById(R.id.use_like_item_img);
        }
    }
    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode==1){
            Gson gson = new Gson();
            UserLikeAndRecommendBean bean = gson.fromJson(result, UserLikeAndRecommendBean.class);
            goods = bean.getData().getItems().getGoods();
           //  Log.d(TAG, "successJson: "+goods);
           myadapter1.notifyDataSetChanged();
        }
        if (requestCode == 2){
            Gson gson = new Gson();
            UserLikeAndRecommendBean bean = gson.fromJson(result, UserLikeAndRecommendBean.class);
            List<UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean> goods2 = bean.getData().getItems().getGoods();
            goods.addAll(goods2);
            myadapter1.notifyDataSetChanged();
            refreshGridView.onRefreshComplete();
        }
        if (requestCode == 3){
            Gson gson = new Gson();
            UserLikeAndRecommendBean bean = gson.fromJson(result, UserLikeAndRecommendBean.class);
            List<UserLikeAndRecommendBean.DataBean.ItemsBean.GoodsBean> goods3 = bean.getData().getItems().getGoods();
            goods = goods3;
            myadapter1.notifyDataSetChanged();
            refreshGridView.onRefreshComplete();
        }
    }
    @Override
    public void successUse(int page) {
        this.page = 1;
        url = GetUrl.getUserLike(id,page);
        HttpUtils.load(url).callBack(this,3);
    }

}
