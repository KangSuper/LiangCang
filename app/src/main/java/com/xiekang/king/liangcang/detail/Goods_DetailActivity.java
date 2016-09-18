package com.xiekang.king.liangcang.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.activity.UserInformationActivity;
import com.xiekang.king.liangcang.activity.WebActivity;
import com.xiekang.king.liangcang.bean.detail.Good_DetailsBean;
import com.xiekang.king.liangcang.bean.detail.GoodsComments;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


public class Goods_DetailActivity extends AppCompatActivity implements JsonCallBack, View.OnClickListener {
    @BindView(R.id.share_detail_link)
    Button link_bt;

    @BindView(R.id.share_detail_img)
    ImageView img;
    @BindView(R.id.share_detail_goods_name)
    TextView goods_name;
    @BindView(R.id.share_detail_price)
    TextView goods_price;
    @BindView(R.id.share_detail_like_count)
    TextView like_count;
    @BindView(R.id.share_detail_owner)
    ImageView owner_img;
    @BindView(R.id.share_detail_owner_name)
    TextView owner_name;
    @BindView(R.id.share_detail_listview)
    ListView listView;
    @BindView(R.id.share_detail_loadcomment)
    Button loadcomment;
    private String id;
    private String owner_id;
    private Context context = this;
    private List<GoodsComments.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();
    private Good_DetailsBean.DataBean.ItemsBean itemsBean ;
    private Myadapter1 myadapter1;
    private int page = 1;
    private String name;
    private String goods_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods__detail);
        myadapter1 = new Myadapter1();
        initView();
        loaddata();
        listView.setAdapter(myadapter1);
        owner_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserInformationActivity.class);
                intent.putExtra("id", owner_id);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        ButterKnife.bind(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        ImageView backImg = (ImageView) findViewById(R.id.goods_header_back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView shareImg = (ImageView) findViewById(R.id.goods_header_share);
        if (shareImg != null) {
            shareImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareSDK.initSDK(Goods_DetailActivity.this);
                    OnekeyShare oks = new OnekeyShare();
                    //关闭sso授权
                    oks.disableSSOWhenAuthorize();
                    // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                    //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//                    oks.setTitle("良仓");
                    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//                    oks.setTitleUrl("http://sharesdk.cn");
                    // text是分享文本，所有平台都需要这个字段
                    oks.setText(itemsBean.getGoods_name());
                    //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
                    oks.setImageUrl(itemsBean.getGoods_image());
                    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                    //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                    // url仅在微信（包括好友和朋友圈）中使用
                    oks.setUrl(itemsBean.getGoods_url());
                    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//                    oks.setComment("");
                    // site是分享此内容的网站名称，仅在QQ空间使用
//                    oks.setSite("良仓");
                    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//                    oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
                    oks.show(Goods_DetailActivity.this);
                }
            });
        }

        link_bt.setOnClickListener(this);

    }

    private void loaddata() {
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        HttpUtils.load(GetUrl.getGoodsDetail(id)).callBack(this, 1);
        HttpUtils.load(GetUrl.getGoodsComments(id, page)).callBack(this, 2);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("url", goods_url);
        startActivity(intent);
    }

    class Myadapter1 extends BaseAdapter {

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
            ViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.goods_comment_lsit_item, parent, false);
                viewHolder = new ViewHolder(view);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            final GoodsComments.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);
            String user_image = itemsBean.getUser_image();
            Picasso.with(context).load(user_image).into(viewHolder.imageView);
            viewHolder.content.setText(itemsBean.getUser_name() + ":" + itemsBean.getMsg());
            viewHolder.time.setText(itemsBean.getCreate_time());

            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user_id = itemsBean.getUser_id() + "";
                    Intent intent = new Intent(context, UserInformationActivity.class);
                    intent.putExtra("id", user_id);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    class ViewHolder {
        ImageView imageView;
        TextView content, time;

        public ViewHolder(View view) {
            view.setTag(this);
            imageView = (ImageView) view.findViewById(R.id.goods_comment_head);
            content = (TextView) view.findViewById(R.id.goods_comment_content);
            time = (TextView) view.findViewById(R.id.goods_comment_time);
        }
    }


    @Override
    public void successJson(String result, int requestCode) {
        //返回商品详情
        if (requestCode == 1) {
            Gson gson = new Gson();
            Good_DetailsBean good_detailsBean = gson.fromJson(result, Good_DetailsBean.class);
            itemsBean = good_detailsBean.getData().getItems();
            Picasso.with(this).load(itemsBean.getGoods_image()).into(img);
            name = itemsBean.getGoods_name();
            goods_url = itemsBean.getGoods_url();
            goods_name.setText(itemsBean.getGoods_name());
            goods_price.setText("¥ " + itemsBean.getPrice());
            Picasso.with(this).load(itemsBean.getHeadimg()).into(owner_img);
            like_count.setText(itemsBean.getLike_count());
            owner_id = itemsBean.getOwner_id();
            owner_name.setText(itemsBean.getOwner_name());
        }
        //加载评论
        if (requestCode == 2) {
            Gson gson2 = new Gson();
            GoodsComments goodsComments = gson2.fromJson(result, GoodsComments.class);
            itemsBeanList = goodsComments.getData().getItems();
            myadapter1.notifyDataSetChanged();
        }
    }
}
