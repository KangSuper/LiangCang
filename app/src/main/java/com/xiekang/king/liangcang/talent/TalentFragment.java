package com.xiekang.king.liangcang.talent;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.Layout;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.squareup.picasso.Picasso;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.UserInfo_Fragment.UserInfo;
import com.xiekang.king.liangcang.activity.UserInformationActivity;
import com.xiekang.king.liangcang.bean.talent.Talent;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class TalentFragment extends Fragment implements JsonCallBack,View.OnClickListener{
    private Context mContext;
    private ImageView search,menu;
    private PullToRefreshGridView refreshGridView;
    private GridView gridview;
    private String url;
    private int page = 1;
    private List<Talent.DataBean.ItemsBean> items = new ArrayList<>();
    private MyadapterTalent myadapterTalent;
    private String server_time;
    private PopupWindow popupWindow;
    private View view_menu;
    private ListView listView;
    private List<String>menuList = new ArrayList<>();
    private int mPosition;
    private MyMenuAdapter myMenuAdapter;

    public static TalentFragment newInstance() {
        TalentFragment fragment = new TalentFragment();
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
       View view = inflater.inflate(R.layout.fragment_talent,container,false);
        myadapterTalent = new MyadapterTalent();
        initview(view);
        initdata();
        initMenuData();
        gridview.setAdapter(myadapterTalent);
        return view;
    }

    private void initMenuData() {
        menuList.add("默认推荐");
        menuList.add("最多推荐");
        menuList.add("最受欢迎");
        menuList.add("最新推荐");
        menuList.add("最新加入");
    }

    private void initdata() {
        url= GetUrl.getOrderDefaultUrl(page);
        HttpUtils.load(url).callBack(this,1);
    }

    private void initview(View view) {
        view_menu = LayoutInflater.from(mContext).inflate(R.layout.talent_menu,null,false);
        popupWindow = new PopupWindow(view_menu, ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
        search = (ImageView) view.findViewById(R.id.talent_search);
        menu = (ImageView) view.findViewById(R.id.talent_set);
        search.setOnClickListener(this);
        menu.setOnClickListener(this);
        refreshGridView = (PullToRefreshGridView) view.findViewById(R.id.talent_pullgrid);
        gridview = refreshGridView.getRefreshableView();
        refreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        gridview.setNumColumns(3);
        gridview.setVerticalSpacing(15);
        gridview.setHorizontalSpacing(15);
        refreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
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
                page = 1;
                if (mPosition==0){
                    url = GetUrl.getOrderDefaultUrl(page);
                    HttpUtils.load(url).callBack(TalentFragment.this,2);
                }
                else if (mPosition==1){
                    url = GetUrl.getOrderSumUrl(page);
                    HttpUtils.load(url).callBack(TalentFragment.this,2);
                }
                else if (mPosition==2){
                    url = GetUrl.getOrderFollwerUrl(page);
                    HttpUtils.load(url).callBack(TalentFragment.this,2);
                }
                else if (mPosition==3){
                    url = GetUrl.getOrderActionUrl(page);
                    HttpUtils.load(url).callBack(TalentFragment.this,2);
                }
                else if (mPosition==4){
                    url = GetUrl.getOrderTimeUrl(page);
                    HttpUtils.load(url).callBack(TalentFragment.this,2);
                }

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                page++;
                if (mPosition==0){
                    url = GetUrl.getOrderDefaultUrl(page);
                    HttpUtils.load(url).callBack(TalentFragment.this,3);
                }
                else if (mPosition==1){
                    url = GetUrl.getOrderSumUrl(page);
                    HttpUtils.load(url).callBack(TalentFragment.this,3);
                }
                else if (mPosition==2){
                    url = GetUrl.getOrderFollwerUrl(page);
                    HttpUtils.load(url).callBack(TalentFragment.this,3);
                }
                else if (mPosition==3){
                    url = GetUrl.getOrderActionUrl(page);
                    HttpUtils.load(url).callBack(TalentFragment.this,3);
                }
                else if (mPosition==4){
                    url = GetUrl.getOrderTimeUrl(page);
                    HttpUtils.load(url).callBack(TalentFragment.this,3);
                }


            }
        });
    }
    class MyadapterTalent extends BaseAdapter{

        @Override
        public int getCount() {
            return items==null?0:items.size();
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
            if (view==null){
                view = LayoutInflater.from(mContext).inflate(R.layout.userinfofragment_follow_item,parent,false);
                viewholderTalent = new ViewholderTalent(view);
            }else {
                viewholderTalent = (ViewholderTalent) view.getTag();
            }
            final Talent.DataBean.ItemsBean itemsBean = items.get(position);
            viewholderTalent.name.setText(itemsBean.getUsername());
            viewholderTalent.job.setText(itemsBean.getDuty());
            Picasso.with(mContext).load(itemsBean.getUser_images().getOrig()).into(viewholderTalent.imageView);

            viewholderTalent.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uid = itemsBean.getUid();
                    Intent intent = new Intent(mContext, UserInformationActivity.class);
                    intent.putExtra("id",uid);
                    startActivity(intent);
                }
            });
            return view;
        }
    }
    class ViewholderTalent{
        public ImageView imageView;
        public TextView name,job;
        public ViewholderTalent(View view){
            view.setTag(this );
            imageView = (ImageView) view.findViewById(R.id.use_fragment_item_follow_img);
            name = (TextView) view.findViewById(R.id.use_fragment_item_follow_name);
            job = (TextView) view.findViewById(R.id.use_fragment_item_follow_job);
        }
    }
    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode == 1){
            Gson gson = new Gson();
            Talent talent = gson.fromJson(result, Talent.class);
            items = talent.getData().getItems();
            myadapterTalent.notifyDataSetChanged();

        }
        if (requestCode == 2){
            Gson gson = new Gson();
            Talent talent = gson.fromJson(result, Talent.class);

            List<Talent.DataBean.ItemsBean> items1 = talent.getData().getItems();
            items = items1;
            refreshGridView.onRefreshComplete();
            myadapterTalent.notifyDataSetChanged();
        }
        if (requestCode == 3){
            Gson gson = new Gson();
            Talent talent = gson.fromJson(result, Talent.class);
            List<Talent.DataBean.ItemsBean> items3 = talent.getData().getItems();
            items.addAll(items3);
            refreshGridView.onRefreshComplete();
            myadapterTalent.notifyDataSetChanged();
        }
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.talent_search:
                break;
            case R.id.talent_set:
                menu.setBackgroundResource(R.drawable.close);
                listView = (ListView) view_menu.findViewById(R.id.talent_menu_list);
                myMenuAdapter = new MyMenuAdapter();
                listView.setAdapter(myMenuAdapter);
                popupWindow.setTouchable(true);
                popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
                popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.colorTalent));
                popupWindow.showAsDropDown(view,0,10);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        menu.setBackgroundResource(R.drawable.actionbar_navigation_menu);
                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mPosition = position;
                        page =1;
                        if (position==0){
                            url = GetUrl.getOrderDefaultUrl(page);
                            HttpUtils.load(url).callBack(TalentFragment.this,2);
                        }
                       else if (position==1){
                            url = GetUrl.getOrderSumUrl(page);
                            HttpUtils.load(url).callBack(TalentFragment.this,2);
                        }
                       else if (position==2){
                            url = GetUrl.getOrderFollwerUrl(page);
                            HttpUtils.load(url).callBack(TalentFragment.this,2);
                        }
                       else if (position==3){
                            url = GetUrl.getOrderActionUrl(page);
                            HttpUtils.load(url).callBack(TalentFragment.this,2);
                        }
                       else if (position==4){
                            url = GetUrl.getOrderTimeUrl(page);
                            HttpUtils.load(url).callBack(TalentFragment.this,2);
                        }
                    }
                });
                break;
        }
    }


    class MyMenuAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return menuList.size();
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
            View view1 = convertView;
            Viewhold viewhold;
            if (view1==null){
                view1 = LayoutInflater.from(mContext).inflate(R.layout.talent_menu_item,parent,false);
                viewhold = new Viewhold(view1);
            }else {
                viewhold = (Viewhold) view1.getTag();
            }
            viewhold.textView.setText(menuList.get(position));
            return view1;
        }
    }
    class Viewhold{
        TextView textView;
        public Viewhold(View view){
            view.setTag(this);
            textView = (TextView) view.findViewById(R.id.talent_menu_item_tv);
        }
    }
}
