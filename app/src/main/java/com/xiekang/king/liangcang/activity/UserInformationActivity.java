package com.xiekang.king.liangcang.activity;




import android.content.Context;
import android.os.Bundle;
;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.squareup.picasso.Picasso;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.UserInfo_Fragment.UseFollowFragment;
import com.xiekang.king.liangcang.UserInfo_Fragment.UserFansFragment;
import com.xiekang.king.liangcang.UserInfo_Fragment.UserInfo;
import com.xiekang.king.liangcang.UserInfo_Fragment.UserLikeFragment;
import com.xiekang.king.liangcang.UserInfo_Fragment.UserLikePull;
import com.xiekang.king.liangcang.UserInfo_Fragment.UserRecommendFragment;
import com.xiekang.king.liangcang.bean.UserInfo.UserFollow;
import com.xiekang.king.liangcang.bean.UserInfo.UserLikeAndRecommendBean;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInformationActivity extends AppCompatActivity implements JsonCallBack{
    @BindView(R.id.user_title)TextView use_title;
    @BindView(R.id.user_head_img)ImageView head_img;
    @BindView(R.id.user_forcus)TextView focus_tv;
    @BindView(R.id.user_msg)TextView msg_tv;
    @BindView(R.id.user_name)TextView user_name;
    @BindView(R.id.user_job)TextView user_job;
    @BindView(R.id.user_radio_group)RadioGroup use_rg;
    @BindView(R.id.user_radio_bt_fans)RadioButton user_fans;
    @BindView(R.id.user_radio_bt_like)RadioButton user_like;
    @BindView(R.id.user_radio_bt_focus)RadioButton user_focus;
    @BindView(R.id.user_radio_bt_recommend)RadioButton user_recommend;
    @BindView(R.id.user_pullscroll)PullToRefreshScrollView refreshScrollView;
    @BindView(R.id.user_fram)FrameLayout frameLayout;
    private String url ;
    private String id;
    private int page = 1;
    private String server_time;
    private String name;
    private FragmentManager fragmentManager;
    private UserInfo userInfo;
    private ActionBar supportActionBar;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

      //  userInfo = new UserLikeFragment(id,page);
        supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        context = this;
        fragmentManager = getSupportFragmentManager();
        id = getIntent().getExtras().getString("id");
        initview();
        new UserLikePull(this).getPull(id);
        user_like.setChecked(true);
    }

    private void initdata() {
        HttpUtils.load(GetUrl.getUserLike(id,1)).callBack(this,1);
    }

    private void initview() {
        ButterKnife.bind(this);

        refreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        //填充数据
        initdata();

        use_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    //传入page,url
                    case R.id.user_radio_bt_like:

                        url = GetUrl.getUserLike(id,page);
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        UserLikeFragment userLikeFragment = new UserLikeFragment(id, page);
                        userInfo = userLikeFragment;
                        transaction.replace(R.id.user_fram,userLikeFragment);
                        transaction.commit();
                        break;
                    case R.id.user_radio_bt_recommend:
                        url = GetUrl.getUserTuijian(id,page);
                        FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                        UserRecommendFragment userRecommendFragment = new UserRecommendFragment(id, page);
                        userInfo = userRecommendFragment;
                        transaction1.replace(R.id.user_fram,userRecommendFragment);
                        transaction1.commit();
                        break;
                    case R.id.user_radio_bt_focus:
                        url = GetUrl.getUserFollow(id,page);
                        FragmentTransaction transaction2 = fragmentManager.beginTransaction();
                        UseFollowFragment useFollowFragment = new UseFollowFragment(id, page);
                        userInfo = useFollowFragment;
                        transaction2.replace(R.id.user_fram,useFollowFragment);
                        transaction2.commit();
                        break;
                    case R.id.user_radio_bt_fans:
                        url = GetUrl.getUserFans(id,page);
                        FragmentTransaction transaction3 = fragmentManager.beginTransaction();

                        UserFansFragment userFansFragment = new UserFansFragment(id, page);
                        userInfo = userFansFragment;
                        transaction3.replace(R.id.user_fram,userFansFragment);
                        transaction3.commit();
                        break;
                }
            }
        });

        refreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                String label = DateUtils.formatDateTime(
                        context.getApplicationContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel("最后更新 "+label);
                HttpUtils.load(GetUrl.getUserLike(id,1)).callBack(UserInformationActivity.this,2);
                userInfo.successUse(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });
    }

    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode ==1){
            Gson gson = new Gson();
            UserLikeAndRecommendBean bean = gson.fromJson(result, UserLikeAndRecommendBean.class);
            server_time = bean.getMeta().getServer_time();
            UserLikeAndRecommendBean.DataBean.ItemsBean items = bean.getData().getItems();
            name = items.getUser_name();
            String orig = items.getUser_image().getOrig();
            String user_desc = items.getUser_desc();
            user_job.setText(user_desc);
            user_name.setText(name);
            use_title.setText(name);
            Picasso.with(this).load(orig).into(head_img);
            user_like.setText("喜欢\n"+items.getLike_count());
            user_recommend.setText("推荐\n"+items.getRecommendation_count());
            user_focus.setText("关注\n"+items.getFollowing_count());
            user_fans.setText("粉丝\n"+items.getFollowed_count());

//           userInfo.successUse(page);
        }
        if (requestCode ==2){
            Gson gson = new Gson();
            UserLikeAndRecommendBean bean = gson.fromJson(result, UserLikeAndRecommendBean.class);
            server_time = bean.getMeta().getServer_time();
            UserLikeAndRecommendBean.DataBean.ItemsBean items = bean.getData().getItems();
            name = items.getUser_name();
            String orig = items.getUser_image().getOrig();
            String user_desc = items.getUser_desc();
            user_job.setText(user_desc);
            user_name.setText(name);
            use_title.setText(name);
            Picasso.with(this).load(orig).into(head_img);
            user_like.setText("喜欢\n"+items.getLike_count());
            user_recommend.setText("推荐\n"+items.getRecommendation_count());
            user_focus.setText("关注\n"+items.getFollowing_count());
            user_fans.setText("粉丝\n"+items.getFollowed_count());

            refreshScrollView.onRefreshComplete();
        }
    }
}


