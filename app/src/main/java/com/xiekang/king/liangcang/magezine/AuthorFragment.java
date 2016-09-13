package com.xiekang.king.liangcang.magezine;


import android.content.Context;
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
import com.xiekang.king.liangcang.bean.brand.BrandBean;
import com.xiekang.king.liangcang.bean.magazine.MgzAuthor;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.BitmapUtils;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;
import com.xiekang.king.liangcang.utils.MgzSeCallBack;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AuthorFragment extends Fragment implements JsonCallBack{
    private Context mContext;
    private List<MgzAuthor.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();
    private PullToRefreshListView mRefresh;
    private ListView mListView;
    private MyAdapter myAdapter;
    private MgzSeCallBack mgzSeCallBack;
    public static AuthorFragment newInstance() {
        AuthorFragment fragment = new AuthorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        if (mContext instanceof MgzSeCallBack){
            mgzSeCallBack = (MgzSeCallBack) mContext;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        HttpUtils.load(GetUrl.MGZ_AUTHOR_URL).callBack(this,46);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mgz_author, container, false);
//        mRefresh = (PullToRefreshListView) view.findViewById(R.id.mgz_author_list_view);
//        mListView = mRefresh.getRefreshableView();
        mListView = (ListView) view.findViewById(R.id.mgz_author_list_view);
        myAdapter = new MyAdapter();
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(itemClickListener);
        return view;
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MgzAuthor.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);
            String author_id = itemsBean.getAuthor_id();
            mgzSeCallBack.msDataCall("author_id",author_id,itemsBean.getAuthor_name());
        }
    };

    @Override
    public void successJson(String result, int requestCode) {
        if (requestCode == 46){
            Gson gson = new Gson();
            List<MgzAuthor.DataBean.ItemsBean> items = gson.fromJson(result, MgzAuthor.class).getData().getItems();
            itemsBeanList.addAll(items);
            myAdapter.notifyDataSetChanged();
        }
    }

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
            View view = convertView;
            ViewHodler viewHodler;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.mgz_author_item, parent, false);
                viewHodler = new ViewHodler(view);
            } else {
                viewHodler = (ViewHodler) view.getTag();
            }
            MgzAuthor.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);
            viewHodler.nameTxt.setText(itemsBean.getAuthor_name());
            viewHodler.noteTxt.setText(itemsBean.getNote());
            Glide.with(mContext).load(itemsBean.getThumb()).into(viewHodler.imageView);
            return view;
        }


        class ViewHodler {
            public CircleImageView imageView;
            public TextView nameTxt;
            public TextView noteTxt;
            public ViewHodler(View view) {
                view.setTag(this);
                imageView = (CircleImageView) view.findViewById(R.id.mgz_author_item_circle_image);
                nameTxt = (TextView) view.findViewById(R.id.mgz_author_item_name_txt);
                noteTxt = (TextView) view.findViewById(R.id.mgz_author_item_note_txt);
            }
        }
    }
}
