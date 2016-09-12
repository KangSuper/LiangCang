package com.xiekang.king.liangcang.talent;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
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
import com.xiekang.king.liangcang.UserInfo_Fragment.UserInfo;
import com.xiekang.king.liangcang.activity.UserInformationActivity;
import com.xiekang.king.liangcang.bean.talent.Talent;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.List;


public class TalentFragment extends Fragment implements JsonCallBack{
    private Context mContext;
    private ImageView search,menu;
    private PullToRefreshGridView refreshGridView;
    private GridView gridview;
    private String url;
    private int page = 1;
    private List<Talent.DataBean.ItemsBean> items = new ArrayList<>();
    private MyadapterTalent myadapterTalent;
    private String server_time;

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
        gridview.setAdapter(myadapterTalent);
        return view;
    }

    private void initdata() {
        url= GetUrl.getOrderDefaultUrl(page);
        HttpUtils.load(url).callBack(this,1);
    }

    private void initview(View view) {
        search = (ImageView) view.findViewById(R.id.talent_search);
        menu = (ImageView) view.findViewById(R.id.talent_set);
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
                url = GetUrl.getOrderDefaultUrl(page);
                HttpUtils.load(url).callBack(TalentFragment.this,2);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                page++;
                url = GetUrl.getOrderDefaultUrl(page);
                HttpUtils.load(url).callBack(TalentFragment.this,3);

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
            myadapterTalent.notifyDataSetChanged();
        }
        if (requestCode == 3){
            Gson gson = new Gson();
            Talent talent = gson.fromJson(result, Talent.class);
            List<Talent.DataBean.ItemsBean> items3 = talent.getData().getItems();
            items.addAll(items3);
            myadapterTalent.notifyDataSetChanged();
        }
    }
}
