package com.xiekang.king.liangcang.share;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.ActionBar;
import android.text.format.DateUtils;
import android.util.Log;
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
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.squareup.picasso.Picasso;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.bean.Share.ShareBean;
import com.xiekang.king.liangcang.share.Http.HttpService;
import com.xiekang.king.liangcang.share.Impl.ShareImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ShareFragment extends Fragment implements HttpService.ICallback,View.OnClickListener{
    private Context mContext;
    private int count = 0;
    private Button set_bt;
    private View view_menu;
    private PopupWindow popupWindow;
    private List<String> mlist = new ArrayList<>();
    private ListView listview;

    private String TAG = "androidxx";
    private List<String>list = new ArrayList<>();
    private List<String>mainlist = new ArrayList<>();
    private Button search_bt;
    private MenuAdapter menuAdapter;

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
        View view = inflater.inflate(R.layout.fragment_share,container,false);
        initview(view);
        myadapter = new Myadapter();
        initgridview(view);
        initdata();
        gridview.setAdapter(myadapter);
        return view;
    }
    private void initview(View view) {
        initmenudata();
        search_bt = (Button)view.findViewById(R.id.share_search);
        set_bt = (Button)view.findViewById(R.id.share_set);
        set_bt.setOnClickListener(this);
        search_bt.setOnClickListener(this);
        Log.d(TAG, "initview: "+set_bt);
        //view_category = getLayoutInflater().inflate(R.layout.share_menu_category,null);
        view_menu = LayoutInflater.from(mContext).inflate(R.layout.share_menu,null);
        popupWindow = new PopupWindow(view_menu, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT,true);
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
    class MenuAdapter extends BaseAdapter{

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
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            MenuViewholder viewholder;
            if (view==null){
                view = LayoutInflater.from(mContext).inflate(R.layout.share_menu_item,parent,false);
                viewholder = new MenuViewholder(view);
            }else {
                viewholder = (MenuViewholder) view.getTag();
            }
            viewholder.textview.setText(mainlist.get(position));
            return view;
        }
    }
    class MenuViewholder{
        TextView textview;
        public MenuViewholder(View view){
            view.setTag(this);
            textview = (TextView) view.findViewById(R.id.share_menu_item_tv);
        }
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.share_search:

                break;
            case R.id.share_set:
                mainlist = list;
                listview = (ListView)view_menu.findViewById(R.id.menu_category_listview);
                menuAdapter = new MenuAdapter();

                listview.setAdapter(menuAdapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position==2){

                            count++;
                            if (count%2!=0){
                                //Toast.makeText(MainActivity.this, "ggg", Toast.LENGTH_SHORT).show();
                                mainlist.addAll(mlist);
                                menuAdapter.notifyDataSetChanged();
                            }else {
                                mainlist .removeAll(mlist);
                                listview.setAdapter(menuAdapter);
                            }
                        }
                    }
                });
                set_bt.setBackgroundResource(R.drawable.ease_search_clear_pressed);
                popupWindow.setTouchable(true);
                popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        return false;
                    }
                });
                popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.colorWhite));
                popupWindow.showAsDropDown(view);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        set_bt.setBackgroundResource(R.drawable.actionbar_navigation_menu);
                    }
                });

                break;
        }
    }



    private LinkedList<String> imglist = new LinkedList<>();
    private PullToRefreshGridView refreshgrid;
    private GridView gridview;
    private int page = 1;
    private Myadapter myadapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            refreshgrid.onRefreshComplete();
            myadapter.notifyDataSetChanged();
        }
    };

    private void initdata() {
        ShareImpl.down(ShareFragment.this,1,1);
    }


    private void initgridview(View view) {
        refreshgrid = (PullToRefreshGridView)view.findViewById(R.id.share_pull_grid);
        refreshgrid.setMode(PullToRefreshBase.Mode.BOTH);
        gridview = refreshgrid.getRefreshableView();
        gridview.setNumColumns(2);
        gridview.setVerticalSpacing(15);
        gridview.setHorizontalSpacing(15);
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
                        .setLastUpdatedLabel("最后更新"+label);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ShareImpl.down(ShareFragment.this,4,1);
                    }
                }).start();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("请稍后");
                page++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ShareImpl.down(ShareFragment.this,2,page);
                    }
                }).start();
            }
        });
    }

//    @Override
//    public void (View v) {
//
//    }

//    @Override
//    public void success(ShareBean shareBean, int requestcode) {
//
//    }

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
            ViewHolder viewHolder ;
            if (view==null){
                view = LayoutInflater.from(mContext).inflate(R.layout.share_item,parent,false);
                viewHolder = new ViewHolder(view);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }
            Log.d(TAG, "getView: "+imglist.get(position));
            viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
            Picasso.with(mContext).load(imglist.get(position)).into(viewHolder.imageView);
            Log.d(TAG, "getView: "+viewHolder.imageView);
            return view;
        }
    }
    class ViewHolder{
        ImageView imageView;
        public ViewHolder(View view ){
            view.setTag(this);
            imageView = (ImageView) view.findViewById(R.id.share_item_img);
        }
    }
    @Override
    public void success(ShareBean shareBean, int requestcode) {
        if (requestcode == 1){

            List<ShareBean.DataBean.ItemsBean> items = shareBean.getData().getItems();
            for (int i = 0; i < items.size(); i++) {
                String goods_image = items.get(i).getGoods_image();
                imglist.addFirst(goods_image);
                handler.sendEmptyMessage(1);
            }
        }
        if (requestcode ==2){

            List<ShareBean.DataBean.ItemsBean> items = shareBean.getData().getItems();
            for (int i = 0; i < items.size(); i++) {
                String goods_image = items.get(i).getGoods_image();
                imglist.addLast(goods_image);
                //刷新
                // myadapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);
            }
        }
        if (requestcode == 4){
            LinkedList<String> headlist = new LinkedList<>();
            List<ShareBean.DataBean.ItemsBean> items = shareBean.getData().getItems();
            for (int i = 0; i < items.size(); i++) {
                String goods_image = items.get(i).getGoods_image();
                headlist.addFirst(goods_image);
                imglist = headlist;
                handler.sendEmptyMessage(1);
            }
        }
    }
}
