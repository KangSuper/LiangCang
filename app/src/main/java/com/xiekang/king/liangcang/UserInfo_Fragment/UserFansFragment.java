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
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.squareup.picasso.Picasso;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.UserInfo.UserFans;
import com.xiekang.king.liangcang.bean.UserInfo.UserFollow;

import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by Administrator on 2016/9/12.
 */
public class UserFansFragment extends Fragment implements JsonCallBack,UserInfo {
    private Context context;
    public String url;
    public int page;
    private PullToRefreshGridView refreshGridView;
    private GridView gridview;
    private LinkedList<UserFollow> mlist = new LinkedList<>();
    private Myadapter4 myadapter3;
    public String id;
    public UserFansFragment(String id, int page){
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
        url = GetUrl.getUserFans(id,page);
        myadapter3 = new Myadapter4();
        initview(view);
        gridview.setAdapter(myadapter3);
        return view;
    }

    private void initview(View view) {
        refreshGridView = (PullToRefreshGridView) view.findViewById(R.id.use_fragment_pullgrid);
        gridview = refreshGridView.getRefreshableView();
        gridview.setNumColumns(3);
        initdata();
        refreshGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                page++;
                url = GetUrl.getUserFans(id,page);
                HttpUtils.load(url).callBack(UserFansFragment.this,2);
            }
        });
    }

    private void initdata() {
        HttpUtils.load(url).callBack(this,1);
    }


    class Myadapter4 extends BaseAdapter {

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
            ViewHolderUser viewHolderUser;
            if (view==null){
                view = LayoutInflater.from(context).inflate(R.layout.userinfofragment_follow_item,parent,false);
                viewHolderUser = new ViewHolderUser(view);

            }else {
                viewHolderUser = (ViewHolderUser) view.getTag();
            }

            UserFollow userFollow = mlist.get(position);
            Picasso.with(context).load(userFollow.img).into(viewHolderUser.imageView);
            viewHolderUser.name.setText(userFollow.name);
            viewHolderUser.job.setText(userFollow.job);
            return view;
        }
    }
    class ViewHolderUser{
        public ImageView imageView;
        public TextView name,job;
        public ViewHolderUser(View view){
            view.setTag(this);
            imageView = (ImageView) view.findViewById(R.id.use_fragment_item_follow_img);
            name = (TextView) view.findViewById(R.id.use_fragment_item_follow_name);
            job = (TextView) view.findViewById(R.id.use_fragment_item_follow_job);
        }
    }
    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode==1){
            Gson gson = new Gson();
            UserFans bean = gson.fromJson(result, UserFans.class);
            List<UserFans.DataBean.ItemsBean.UsersBean> users = bean.getData().getItems().getUsers();

            for (int i = 0; i < users.size(); i++) {
                UserFollow userFollow = new UserFollow();
                userFollow.id = users.get(i).getUser_id();
                userFollow.img = users.get(i).getUser_image().getOrig();
                userFollow.job = users.get(i).getUser_desc();
                userFollow.name = users.get(i).getUser_name();
                mlist.add(userFollow);
                //刷新适配器
                myadapter3.notifyDataSetChanged();
            }
        }
        if (requestCode==2){
            Gson gson = new Gson();
            UserFans bean = gson.fromJson(result, UserFans.class);
            List<UserFans.DataBean.ItemsBean.UsersBean> users = bean.getData().getItems().getUsers();

            for (int i = 0; i < users.size(); i++) {
                UserFollow userFollow = new UserFollow();
                userFollow.id = users.get(i).getUser_id();
                userFollow.img = users.get(i).getUser_image().getOrig();
                userFollow.job = users.get(i).getUser_desc();
                userFollow.name = users.get(i).getUser_name();
                mlist.addLast(userFollow);
                //刷新适配器
                myadapter3.notifyDataSetChanged();
            }
            refreshGridView.onRefreshComplete();
        }
    }
    @Override
    public void successUse(int page) {
        this.page = 1;
        url = GetUrl.getUserFans(id,page);
        HttpUtils.load(url).callBack(this,page);
    }

}
