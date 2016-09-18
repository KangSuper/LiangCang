package com.xiekang.king.liangcang.share;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.squareup.picasso.Picasso;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.activity.ShopInfoActivity;
import com.xiekang.king.liangcang.bean.Share.ShareBean;
import com.xiekang.king.liangcang.bean.Share.Share_conver;
import com.xiekang.king.liangcang.detail.Goods_DetailActivity;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ShareFragment extends Fragment implements View.OnClickListener, JsonCallBack {
    private Context mContext;
    private int count = 0;
    private Button set_bt;
    private View view_menu;
    private PopupWindow popupWindow;
    private List<String> mlist = new ArrayList<>();
    private ListView listview;

    private List<String> list = new ArrayList<>();
    private List<String> mainlist = new ArrayList<>();
    private Button search_bt;
    private MenuAdapter menuAdapter;
    private String url;
    private RelativeLayout mRelative;

    public static ShareFragment newInstance() {
        ShareFragment fragment = new ShareFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        url = GetUrl.getShareAllUrl(page);
        initview(view);
        myadapter = new Myadapter();
        initgridview(view);
        initdata();
        gridview.setAdapter(myadapter);
        return view;
    }

    private void initview(View view) {
        initmenudata();
        search_bt = (Button) view.findViewById(R.id.share_search);
        set_bt = (Button) view.findViewById(R.id.share_set);
        mRelative = (RelativeLayout) view.findViewById(R.id.re);
        set_bt.setOnClickListener(this);
        search_bt.setOnClickListener(this);
        view_menu = LayoutInflater.from(mContext).inflate(R.layout.share_menu, null);
        popupWindow = new PopupWindow(view_menu, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
    }

    private void initmenudata() {
        list.add("全部分享");
        list.add("商店分享");
        list.add("分类分享");

        mlist.add("男士");
        mlist.add("女士");
        mlist.add("家居");
        mlist.add("数码");
        mlist.add("工具");
        mlist.add("玩具");
        mlist.add("美容");
        mlist.add("孩子");
        mlist.add("宠物");
        mlist.add("运动");
        mlist.add("饮食");
        mlist.add("文化");

    }


    class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mainlist.size();
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
        public View getView(final int position, View convertView, final ViewGroup parent) {
            View view = convertView;
            MenuViewholder viewholder;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.share_menu_item, parent, false);
                viewholder = new MenuViewholder(view);
            } else {
                viewholder = (MenuViewholder) view.getTag();
            }
            viewholder.textview.setText(mainlist.get(position));
            return view;
        }
    }

    class MenuViewholder {
        TextView textview;

        public MenuViewholder(View view) {
            view.setTag(this);
            textview = (TextView) view.findViewById(R.id.share_menu_item_tv);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_search:

                break;
            case R.id.share_set:
                mainlist = list;
                listview = (ListView) view_menu.findViewById(R.id.menu_category_listview);
                menuAdapter = new MenuAdapter();

                listview.setAdapter(menuAdapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        mPosition = position;
                        if (position == 0) {
                            page = 1;
                            url = GetUrl.getShareAllUrl(page);
                            HttpUtils.load(url).callBack(ShareFragment.this, 4);
                            popupWindow.dismiss();

                        } else if (position == 1) {
                            page = 1;
                            url = GetUrl.getShareShopUrl(page);
                            HttpUtils.load(url).callBack(ShareFragment.this, 4);
                            popupWindow.dismiss();

                        } else if (position > 2) {
                            page = 1;
                            url = GetUrl.getShareCategoryUrl(position - 2, page);
                            HttpUtils.load(url).callBack(ShareFragment.this, 4);
                            popupWindow.dismiss();

                        }
                        if (position == 2) {

                            count++;
                            if (count % 2 != 0) {
                                mainlist.addAll(mlist);
                                menuAdapter.notifyDataSetChanged();
                            } else {
                                mainlist.removeAll(mlist);
                                listview.setAdapter(menuAdapter);
                            }
                        }
                    }
                });
                set_bt.setBackgroundResource(R.drawable.close);
                popupWindow.setTouchable(true);
                popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        return false;
                    }
                });
                popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.colorWhite));
                popupWindow.showAsDropDown(mRelative);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        set_bt.setBackgroundResource(R.drawable.actionbar_navigation_menu);
                    }
                });

                break;
        }
    }


    private int mPosition;
    private LinkedList<Share_conver> imglist = new LinkedList<>();
    private PullToRefreshGridView refreshgrid;
    private GridView gridview;
    private int page = 1;
    private Myadapter myadapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            refreshgrid.onRefreshComplete();
            myadapter.notifyDataSetChanged();
        }
    };

    private void initdata() {


        HttpUtils.load(url).callBack(ShareFragment.this, 1);
    }


    private void initgridview(View view) {
        refreshgrid = (PullToRefreshGridView) view.findViewById(R.id.share_pull_grid);
        refreshgrid.setMode(PullToRefreshBase.Mode.BOTH);
        gridview = refreshgrid.getRefreshableView();
        gridview.setNumColumns(2);
        gridview.setVerticalSpacing(20);
        gridview.setHorizontalSpacing(20);
        refreshgrid.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                String label = DateUtils.formatDateTime(
                        mContext.getApplicationContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel("最后更新" + label);
                page = 1;
                HttpUtils.load(url).callBack(ShareFragment.this, 4);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("请稍后");
                page++;
                if (mPosition == 0) {
                    url = GetUrl.getShareAllUrl(page);
                }
                if (mPosition == 1) {
                    url = GetUrl.getShareShopUrl(page);
                } else if (mPosition > 2) {
                    url = GetUrl.getShareCategoryUrl(mPosition - 2, page);
                }

                HttpUtils.load(url).callBack(ShareFragment.this, 2);
            }
        });
    }


    class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imglist.size();
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
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.share_item, parent, false);
                viewHolder = new ViewHolder(view);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
            final Share_conver share_conver = imglist.get(position);
            Picasso.with(mContext).load(share_conver.img).into(viewHolder.imageView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (share_conver.sale_by.equals("other")){
                        Intent intent = new Intent(mContext, Goods_DetailActivity.class);
                        intent.putExtra("id",share_conver.id);
                        startActivity(intent);
                    }else {
                        Intent intent1 = new Intent(mContext, ShopInfoActivity.class);
                        intent1.putExtra("id",share_conver.id);
                        startActivity(intent1);
                    }
                }
            });
            return view;
        }
    }

    class ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            view.setTag(this);
            imageView = (ImageView) view.findViewById(R.id.share_item_img);
        }

    }

    public void successJson(String result, int requestCode) {
        Gson gson = new Gson();
        ShareBean shareBean = gson.fromJson(result, ShareBean.class);

        if (requestCode == 1) {

            List<ShareBean.DataBean.ItemsBean> items = shareBean.getData().getItems();
            for (int i = 0; i < items.size(); i++) {
                String goods_image = items.get(i).getGoods_image();
                String goods_id = items.get(i).getGoods_id();
                String sale_by = items.get(i).getSale_by();
                Share_conver share_conver = new Share_conver(goods_image, goods_id,sale_by);
                imglist.addFirst(share_conver);
                handler.sendEmptyMessage(1);
            }
        }
        //上拉
        if (requestCode == 2) {

            List<ShareBean.DataBean.ItemsBean> items = shareBean.getData().getItems();
            for (int i = 0; i < items.size(); i++) {

                String goods_image = items.get(i).getGoods_image();
                String goods_id = items.get(i).getGoods_id();
                String sale_by = items.get(i).getSale_by();
                Share_conver share_conver = new Share_conver(goods_image, goods_id,sale_by);
                imglist.addLast(share_conver);
                //刷新
                // myadapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);
            }
        }
        //下拉
        if (requestCode == 4) {
            LinkedList<Share_conver> headlist = new LinkedList<>();
            List<ShareBean.DataBean.ItemsBean> items = shareBean.getData().getItems();
            for (int i = 0; i < items.size(); i++) {
                String goods_image = items.get(i).getGoods_image();

                String goods_id = items.get(i).getGoods_id();
                String sale_by = items.get(i).getSale_by();
                Share_conver share_conver = new Share_conver(goods_image, goods_id,sale_by);
                headlist.addFirst(share_conver);
                imglist = headlist;
                handler.sendEmptyMessage(1);
            }
        }
    }
}
