package com.xiekang.king.liangcang.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.shop.CgDetailsBean;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.DateUtils;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchActivity extends AppCompatActivity {

    private EditText mEditTxt;
    private ImageView mImageView;
    private GridView mGridView;
    private List<CgDetailsBean.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();
    private MyAdapter mAdapter;
    private int page = 1;
    private String keyWord;
    private boolean mIsBottom = false;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
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
        TextView titleTxt = (TextView)view.findViewById(R.id.brand_details_title_txt);
        titleTxt.setText("搜索商品");
    }

    private void initView() {
        mEditTxt = (EditText) findViewById(R.id.search_product_edit_view);
        mImageView = (ImageView) findViewById(R.id.search_product_image_view);

        mImageView.setOnClickListener(searchListener);

        mGridView = (GridView) findViewById(R.id.search_product_grid_view);
        mGridView.setVerticalSpacing(10);
        mGridView.setHorizontalSpacing(10);
        mAdapter = new MyAdapter();
        mGridView.setAdapter(mAdapter);
        mGridView.setOnScrollListener(scrollListener);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中请稍等~");
    }

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
                HttpUtils.load(GetUrl.getShopSearchUrl(keyWord,page)).callBack(new JsonCallBack() {
                    @Override
                    public void successJson(String result, int requestCode) {
                        if (requestCode == 39) {
                            Gson gson = new Gson();
                            CgDetailsBean cgDetailsBean = gson.fromJson(result, CgDetailsBean.class);
                            itemsBeanList.addAll(cgDetailsBean.getData().getItems());
                            mAdapter.notifyDataSetChanged();
                            Toast.makeText(ProductSearchActivity.this,"数据加载完毕",Toast.LENGTH_SHORT).show();
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


    private View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            keyWord = mEditTxt.getText().toString();
            if (TextUtils.isEmpty(keyWord)){
                Toast.makeText(ProductSearchActivity.this,"关键字不能为空",Toast.LENGTH_SHORT).show();
            }else {
                progressDialog.show();
                itemsBeanList.clear();
                mAdapter.notifyDataSetChanged();
                page = 1;
                HttpUtils.load(GetUrl.getShopSearchUrl(keyWord,page)).callBack(new JsonCallBack() {
                    @Override
                    public void successJson(String result, int requestCode) {
                        progressDialog.dismiss();
                        if (requestCode == 38) {
                            Gson gson = new Gson();
                            CgDetailsBean cgDetailsBean = gson.fromJson(result, CgDetailsBean.class);
                            itemsBeanList.addAll(cgDetailsBean.getData().getItems());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }, 38);
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
            return itemsBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHodler viewHodler;
            if (convertView == null) {
                convertView = LayoutInflater.from(ProductSearchActivity.this).inflate(R.layout.product_item_view, parent, false);
                viewHodler = new ViewHodler(convertView);
            } else {
                viewHodler = (ViewHodler) convertView.getTag();
            }
            CgDetailsBean.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);
            Glide.with(ProductSearchActivity.this).load(itemsBean.getGoods_image()).into(viewHodler.iconImg);
            viewHodler.goodNameTxt.setText(itemsBean.getGoods_name());
            viewHodler.brandNameTxt.setText(itemsBean.getBrand_info().getBrand_name());
            viewHodler.priceTxt.setText("¥" + itemsBean.getPrice());
            viewHodler.countTxt.setText(itemsBean.getLike_count());
            return convertView;
        }


        class ViewHodler {
            public ImageView iconImg;
            public TextView goodNameTxt;
            public TextView brandNameTxt;
            public TextView priceTxt;
            public TextView countTxt;

            public ViewHodler(View view) {
                view.setTag(this);
                iconImg = (ImageView) view.findViewById(R.id.product_item_icon_img);
                goodNameTxt = (TextView) view.findViewById(R.id.product_item_good_name_txt);
                brandNameTxt = (TextView) view.findViewById(R.id.product_item_brand_name_txt);
                priceTxt = (TextView) view.findViewById(R.id.product_item_price_txt);
                countTxt = (TextView) view.findViewById(R.id.product_item_count_txt);
            }
        }
    }
}
