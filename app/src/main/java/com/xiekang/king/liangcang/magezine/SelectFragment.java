package com.xiekang.king.liangcang.magezine;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.activity.WebActivity;
import com.xiekang.king.liangcang.bean.magazine.MgzBean;
import com.xiekang.king.liangcang.bean.magazine.MgzInfoBean;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;
import com.xiekang.king.liangcang.utils.MgzCallBack;
import com.xiekang.king.liangcang.utils.MgzSeCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SelectFragment extends Fragment implements JsonCallBack{
    private Context mContext;
    private ImageView mBackImg;
    private TextView mTitleTxt;
    private PullToRefreshListView mRefresh;
    private List<String> keyList = new ArrayList<>();
    private List<MgzInfoBean> mgzInfoBeenList = new ArrayList<>();
    private ListView mListView;
    private MyAdapter myAdapter;
    private MgzSeCallBack mgzSeCallBack;
    private MgzCallBack mgzCallBack;
    public static SelectFragment newInstance(String category,String gid,String title) {
        SelectFragment fragment = new SelectFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category",category);
        bundle.putString("gid",gid);
        bundle.putString("title",title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        if (mContext instanceof MgzSeCallBack){
            mgzSeCallBack = (MgzSeCallBack) mContext;
        }

        if (mContext instanceof MgzCallBack){
            mgzCallBack = (MgzCallBack) mContext;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle arguments = getArguments();
        String category = arguments.getString("category");
        String gid = arguments.getString("gid");
        String title = arguments.getString("title");
        mTitleTxt.setText("杂志*"+title);
        HttpUtils.load(GetUrl.getMgzSelectUrl(category,gid)).callBack(this,50);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mgz_select, container, false);
        mBackImg = (ImageView) view.findViewById(R.id.select_back_image);
        mTitleTxt = (TextView) view.findViewById(R.id.select_title_txt);
        mRefresh = (PullToRefreshListView) view.findViewById(R.id.select_refresh_list_view);
        mListView = mRefresh.getRefreshableView();
        mListView.setOnItemClickListener(itemClickListener);
        myAdapter = new MyAdapter();
        mListView.setAdapter(myAdapter);

        mTitleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mgzCallBack.mDataCall();
            }
        });

        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mgzSeCallBack.msBackCall();
            }
        });

        return view;
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MgzInfoBean mgzInfoBean = mgzInfoBeenList.get(position);
            Intent intent = new Intent(mContext, WebActivity.class);
            intent.putExtra("url",mgzInfoBean.getTopic_url());
            intent.putExtra("name",mgzInfoBean.getTopic_name());
            startActivity(intent);
        }
    };


    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode == 50){
            Gson gson = new Gson();
            List<String> keys = gson.fromJson(result, MgzBean.class).getData().getItems().getKeys();
            keyList.addAll(keys);
            try {
                JSONObject object = new JSONObject(result);
                JSONObject data = object.getJSONObject("data");
                JSONObject item = data.getJSONObject("items");
                JSONObject infos = item.getJSONObject("infos");
                for (int i = 0; i < keyList.size(); i++) {
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
                }

                myAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mgzInfoBeenList == null ? 0:mgzInfoBeenList.size();
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
            ChildViewHodler viewHodler;
            if (convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.magazine_item_child,parent,false);
                viewHodler = new ChildViewHodler(convertView);
            }else {
                viewHodler = (ChildViewHodler) convertView.getTag();
            }
            MgzInfoBean mgzInfoBean = mgzInfoBeenList.get(position);
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
    }
}
