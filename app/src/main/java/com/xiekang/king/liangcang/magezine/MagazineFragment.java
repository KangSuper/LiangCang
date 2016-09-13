package com.xiekang.king.liangcang.magezine;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.activity.WebActivity;
import com.xiekang.king.liangcang.bean.magazine.MgzBean;
import com.xiekang.king.liangcang.bean.magazine.MgzInfoBean;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MagazineFragment extends Fragment implements JsonCallBack {
    private Context mContext;
    private TextView mTimeTxt;
    private TextView mTitleTxt;
    private ExpandableListView mExpandableList;
    private List<String> keyList = new ArrayList<>();
    private Map<String,List<MgzInfoBean>> dataMap = new LinkedHashMap<>();
    private MyAdapter mAdapter;

    public static MagazineFragment newInstance() {
        MagazineFragment fragment = new MagazineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public void onStart() {
        super.onStart();
        HttpUtils.load(GetUrl.MAGEZINE_URL).callBack(this,42);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_magazine, container, false);
        mTimeTxt = (TextView) view.findViewById(R.id.magazine_time_txt);
        mTitleTxt = (TextView) view.findViewById(R.id.magazine_title_txt);
        mExpandableList = (ExpandableListView) view.findViewById(R.id.magazine_expandable_list);
        mExpandableList.setGroupIndicator(null);
        mAdapter = new MyAdapter();
        mExpandableList.setAdapter(mAdapter);

        mExpandableList.setOnChildClickListener(childClickListener);
        mExpandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        mExpandableList.setOnScrollListener(scrollListener);

        return view;
    }

    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            Log.d("androidxx", "firstVisibleItem: "+firstVisibleItem + "<---->  visibleItemCount:"+visibleItemCount+"   <--->totalItemCount:"+totalItemCount);
            int num = 0;
            for (int i = 0; i < keyList.size(); i++) {
                String key = keyList.get(i);
                num = dataMap.get(key).size() + num ;
                if (num + i<= firstVisibleItem){
                    mTimeTxt.setText(getSubResult(key));
                }
            }
        }
    };


    private ExpandableListView.OnChildClickListener childClickListener = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            MgzInfoBean mgzInfoBean = dataMap.get(keyList.get(groupPosition)).get(childPosition);
            Intent intent = new Intent(mContext, WebActivity.class);
            intent.putExtra("url",mgzInfoBean.getTopic_url());
            intent.putExtra("name",mgzInfoBean.getTopic_name());
            startActivity(intent);
            return false;
        }
    };


    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode == 42){
            Gson gson = new Gson();
            List<String> keys = gson.fromJson(result, MgzBean.class).getData().getItems().getKeys();
            keyList.addAll(keys);
            try {
                JSONObject object = new JSONObject(result);
                JSONObject data = object.getJSONObject("data");
                JSONObject item = data.getJSONObject("items");
                JSONObject infos = item.getJSONObject("infos");
                for (int i = 0; i < keyList.size(); i++) {
                    List<MgzInfoBean> mgzInfoBeenList = new ArrayList<>();
                    String key = keyList.get(i);
                    JSONArray jsonArray = infos.getJSONArray(key);
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        String taid = jsonObject.getString("taid");
                        String topic_name = jsonObject.getString("topic_name");
                        String cat_id = jsonObject.getString("cat_id");
                        String author_id = jsonObject.getString("author_id");
                        String topic_url = jsonObject.getString("topic_url");
                        String access_url = jsonObject.getString("access_url");
                        String cover_img = jsonObject.getString("cover_img");
                        String cover_img_new = jsonObject.getString("cover_img_new");
                        String hit_number = jsonObject.getString("hit_number");
                        String addtime = jsonObject.getString("addtime");
                        String content = jsonObject.getString("content");
                        String nav_title = jsonObject.getString("nav_title");
                        String author_name = jsonObject.getString("author_name");
                        String cat_name = jsonObject.getString("cat_name");
                        MgzInfoBean mgzInfoBean = new MgzInfoBean(taid, topic_name, cat_id, author_id, topic_url, access_url, cover_img, cover_img_new, hit_number, addtime, content, nav_title, author_name, cat_name);
                        mgzInfoBeenList.add(mgzInfoBean);
                    }
                    dataMap.put(key,mgzInfoBeenList);
                }

                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }



    class MyAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return keyList == null ? 0:keyList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            String key = keyList.get(groupPosition);
            List<MgzInfoBean> mgzInfoBeen = dataMap.get(key);
            return mgzInfoBeen.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return keyList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            String key = keyList.get(groupPosition);
            List<MgzInfoBean> mgzInfoBeen = dataMap.get(key);
            return mgzInfoBeen.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            FatherViewHodler viewHodler;
            if (convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.magazine_item_father, parent, false);
                viewHodler = new FatherViewHodler(convertView);
            }else {
                viewHodler = (FatherViewHodler) convertView.getTag();
            }
            viewHodler.timeTxt.setText(getSubResult(keyList.get(groupPosition)));
            mExpandableList.expandGroup(groupPosition);
            return convertView;
        }

        class FatherViewHodler {
            public TextView timeTxt;

            public FatherViewHodler(View view) {
                view.setTag(this);
                timeTxt = (TextView) view.findViewById(R.id.mgz_item_text);
            }
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHodler viewHodler;
            if (convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.magazine_item_child,parent,false);
                viewHodler = new ChildViewHodler(convertView);
            }else {
                viewHodler = (ChildViewHodler) convertView.getTag();
            }
            MgzInfoBean mgzInfoBean = dataMap.get(keyList.get(groupPosition)).get(childPosition);
            viewHodler.categoryTxt.setText(mgzInfoBean.getCat_name());
            viewHodler.descTxt.setText(mgzInfoBean.getTopic_name());
            Glide.with(mContext).load(mgzInfoBean.getCover_img_new()).into(viewHodler.iconImg);
            return convertView;
        }

        class ChildViewHodler{
            public TextView descTxt;
            public ImageView iconImg;
            public TextView categoryTxt;

            public ChildViewHodler(View view) {
                view.setTag(this);
                descTxt = (TextView) view.findViewById(R.id.mgz_item_desc_txt);
                iconImg = (ImageView) view.findViewById(R.id.mgz_item_icon_img);
                categoryTxt = (TextView) view.findViewById(R.id.mgz_item_category_txt);
            }
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }


    private String getSubResult(String string){
        String substring = string.substring(5);
        return substring;
    }
}
