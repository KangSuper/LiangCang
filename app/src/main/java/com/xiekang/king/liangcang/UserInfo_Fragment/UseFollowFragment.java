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
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.squareup.picasso.Picasso;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.UserInfo.UserFollowAndFollowedsBean;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.DateUtils;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class UseFollowFragment extends Fragment implements JsonCallBack{
    private Context context;
    public String url;
    public int page = 1;
    private PullToRefreshGridView refreshGridView;
    private GridView gridview;
    private List<UserFollowAndFollowedsBean.DataBean.ItemsBean.UsersBean> usersBeanList = new ArrayList<>();
    private Myadapter3 myadapter3;
    public String id;

    public UseFollowFragment(String id) {
        this.id = id;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myadapter3.notifyDataSetChanged();
            refreshGridView.setLastUpdatedLabel("最后更新:" + DateUtils.getCurrentTime());
            refreshGridView.onRefreshComplete();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public void onStart() {
        super.onStart();
        initdata();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_fragment, container, false);
        myadapter3 = new Myadapter3();
        initview(view);
        gridview.setAdapter(myadapter3);
        gridview.setVerticalSpacing(10);
        gridview.setHorizontalSpacing(10);
        return view;
    }

    private void initview(View view) {
        refreshGridView = (PullToRefreshGridView) view.findViewById(R.id.use_fragment_pullgrid);
        gridview = refreshGridView.getRefreshableView();
        gridview.setNumColumns(3);
        refreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                page++;
                HttpUtils.load(GetUrl.getUserFollow(id, page)).callBack(new JsonCallBack() {
                    @Override
                    public void successJson(String result, int requestCode) {
                        Gson gson = new Gson();
                        UserFollowAndFollowedsBean bean = gson.fromJson(result, UserFollowAndFollowedsBean.class);
                        List<UserFollowAndFollowedsBean.DataBean.ItemsBean.UsersBean> users = bean.getData().getItems().getUsers();
                        usersBeanList.addAll(users);
                        mHandler.sendEmptyMessage(1);
                    }
                },51);
            }
        });
    }

    private void initdata() {
        HttpUtils.load(GetUrl.getUserFollow(id, page)).callBack(this, 50);
    }


    class Myadapter3 extends BaseAdapter {

        @Override
        public int getCount() {
            return usersBeanList == null ? 0 : usersBeanList.size();
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
                view = LayoutInflater.from(context).inflate(R.layout.userinfofragment_follow_item, parent, false);
                viewHolderUser = new ViewHolderUser(view);

            } else {
                viewHolderUser = (ViewHolderUser) view.getTag();
            }
            UserFollowAndFollowedsBean.DataBean.ItemsBean.UsersBean usersBean = usersBeanList.get(position);
            Picasso.with(context).load(usersBean.getUser_image().getOrig()).into(viewHolderUser.imageView);
            viewHolderUser.name.setText(usersBean.getUser_name());
            viewHolderUser.job.setText(usersBean.getUser_desc());
            return view;
        }
    }

    class ViewHolderUser {
        public ImageView imageView;
        public TextView name, job;

        public ViewHolderUser(View view) {
            view.setTag(this);
            imageView = (ImageView) view.findViewById(R.id.use_fragment_item_follow_img);
            name = (TextView) view.findViewById(R.id.use_fragment_item_follow_name);
            job = (TextView) view.findViewById(R.id.use_fragment_item_follow_job);
        }
    }

    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode == 50) {
            Gson gson = new Gson();
            UserFollowAndFollowedsBean bean = gson.fromJson(result, UserFollowAndFollowedsBean.class);
            List<UserFollowAndFollowedsBean.DataBean.ItemsBean.UsersBean> users = bean.getData().getItems().getUsers();
            usersBeanList.addAll(users);
            myadapter3.notifyDataSetChanged();

        }
    }


}
