package com.xiekang.king.liangcang.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.talent.Talent;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class UserSearchActivity extends AppCompatActivity {

    private EditText mEditTxt;
    private ImageView mImageView;
    private GridView mGridView;
    private ProgressDialog progressDialog;
    private String keyWord;
    private MyAdapter mAdapter;
    private int page = 1;
    private boolean mIsBottom;
    private List<Talent.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        initActionBar();
        initView();
    }


    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        View view = LayoutInflater.from(this).inflate(R.layout.brand_details_bar, null, false);
        ImageView backImg = (ImageView) view.findViewById(R.id.brand_details_bak_img);
        actionBar.setCustomView(view);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView titleTxt = (TextView) view.findViewById(R.id.brand_details_title_txt);
        titleTxt.setText("搜索用户");
    }

    private void initView() {
        mEditTxt = (EditText) findViewById(R.id.search_user_edit_view);
        mImageView = (ImageView) findViewById(R.id.search_user_image_view);

        mImageView.setOnClickListener(searchListener);

        mGridView = (GridView) findViewById(R.id.search_user_grid_view);
        mGridView.setVerticalSpacing(10);
        mGridView.setHorizontalSpacing(10);
        mAdapter = new MyAdapter();
        mGridView.setAdapter(mAdapter);
        mGridView.setOnScrollListener(scrollListener);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中请稍等~");
    }

    private View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            keyWord = mEditTxt.getText().toString();
            if (TextUtils.isEmpty(keyWord)) {
                Toast.makeText(UserSearchActivity.this, "关键字不能为空", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                itemsBeanList.clear();
                mAdapter.notifyDataSetChanged();
                page = 1;
                try {
                    keyWord = URLEncoder.encode(keyWord, "utf-8");
                    Log.d("androidxx", "onClick: keyWord"+keyWord);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                HttpUtils.load(GetUrl.getTalentSearchUrl(keyWord, page)).callBack(new JsonCallBack() {
                    @Override
                    public void successJson(String result, int requestCode) {
                        progressDialog.dismiss();
                        if (requestCode == 38) {
                            Gson gson = new Gson();
                            Talent talent = gson.fromJson(result, Talent.class);
                            itemsBeanList.addAll(talent.getData().getItems());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }, 38);
            }
        }
    };

    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        // 滑动状态的监听：
        // scrollState有三个值：
        // 1，SCROLL_STATE_IDLE(0)：表示ListView滑动最终停止
        // 2，SCROLL_STATE_TOUCH_SCROLL（1）：表示手触摸ListView，并开始滑动
        // 3，SCROLL_STATE_FLING（2）：表示手离开屏幕，惯性滑动的时候
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (mIsBottom && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                page ++;
                HttpUtils.load(GetUrl.getTalentSearchUrl(keyWord,page)).callBack(new JsonCallBack() {
                    @Override
                    public void successJson(String result, int requestCode) {
                        if (requestCode == 39) {
                            Gson gson = new Gson();
                            Talent talent = gson.fromJson(result, Talent.class);
                            itemsBeanList.addAll(talent.getData().getItems());
                            mAdapter.notifyDataSetChanged();
                            Toast.makeText(UserSearchActivity.this,"数据加载完毕",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 39);
            }
        }

        // 滑动过程的监听
        // 三个参数：
        // 1，firstVisibleItem：ListView最顶部可见Item的下标值（position）
        // 2，visibleItemCount：屏幕上可见的item的数量
        // 3，totalItemCount：ListView总共Item的数量
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem + visibleItemCount == totalItemCount) {
                mIsBottom = true;
            }else {
                mIsBottom = false;
            }
        }
    };


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return itemsBeanList == null ? 0 : itemsBeanList.size();
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
            ViewholderTalent viewholderTalent;
            if (view == null) {
                view = LayoutInflater.from(UserSearchActivity.this).inflate(R.layout.userinfofragment_follow_item, parent, false);
                viewholderTalent = new ViewholderTalent(view);
            } else {
                viewholderTalent = (ViewholderTalent) view.getTag();
            }
            final Talent.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);
            viewholderTalent.name.setText(itemsBean.getNickname());
            Picasso.with(UserSearchActivity.this).load(itemsBean.getUser_images().getOrig()).into(viewholderTalent.imageView);

            viewholderTalent.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uid = itemsBean.getUid();
                    Intent intent = new Intent(UserSearchActivity.this, UserInformationActivity.class);
                    intent.putExtra("id", uid);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    class ViewholderTalent {
        public ImageView imageView;
        public TextView name;

        public ViewholderTalent(View view) {
            view.setTag(this);
            imageView = (ImageView) view.findViewById(R.id.use_fragment_item_follow_img);
            name = (TextView) view.findViewById(R.id.use_fragment_item_follow_name);
        }
    }
}
