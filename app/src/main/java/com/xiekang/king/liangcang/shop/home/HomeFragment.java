package com.xiekang.king.liangcang.shop.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.activity.WebActivity;
import com.xiekang.king.liangcang.bean.home.HomeBean;
import com.xiekang.king.liangcang.bean.home.RecyclerBean;
import com.xiekang.king.liangcang.urlString.GetUrl;
import com.xiekang.king.liangcang.utils.HttpUtils;
import com.xiekang.king.liangcang.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private Context mContext;
    private List<RecyclerBean> recyclerBeanList = new ArrayList<>();
    private List<HomeBean.DataBean.ItemsBean.SlideBean> slideBeenList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private HomeAdapter homeAdapter;
    private HeaderImageAdapter imageAdapter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        HttpUtils.load(GetUrl.SHOP_HOME_URL).callBack(new JsonCallBack() {
            @Override
            public void successJson(String result, int requestCode) {
                if (requestCode == 8) {
                    Gson gson = new Gson();
                    HomeBean homeBean = gson.fromJson(result, HomeBean.class);
                    List<HomeBean.DataBean.ItemsBean.SlideBean> slide = homeBean.getData().getItems().getSlide();
                    slideBeenList.addAll(slide);

                    List<HomeBean.DataBean.ItemsBean.ListBean> list = homeBean.getData().getItems().getList();
                    HomeBean.DataBean.ItemsBean.ListBean.OneBean one = list.get(0).getOne();
                    recyclerBeanList.add(new RecyclerBean(one.getHome_id(), one.getContent_type(), one.getContent_id(), one.getPic_url(), one.getPos(), one.getTopic_name(), one.getTopic_url()));

                    HomeBean.DataBean.ItemsBean.ListBean.ThreeBean three = list.get(0).getThree();
                    recyclerBeanList.add(new RecyclerBean(three.getHome_id(), three.getContent_type(), three.getContent_id(), three.getPic_url(), three.getPos(), three.getTopic_name(), three.getTopic_url()));

                    HomeBean.DataBean.ItemsBean.ListBean.TwoBean two = list.get(0).getTwo();
                    recyclerBeanList.add(new RecyclerBean(two.getHome_id(), two.getContent_type(), two.getContent_id(), two.getPic_url(), two.getPos(), two.getTopic_name(), two.getTopic_url()));

                    HomeBean.DataBean.ItemsBean.ListBean.FourBean four = list.get(0).getFour();
                    recyclerBeanList.add(new RecyclerBean(four.getHome_id(), four.getContent_type(), four.getContent_id(), four.getPic_url(), four.getPos(), four.getTopic_name(), four.getTopic_url()));

                    HomeBean.DataBean.ItemsBean.ListBean.OneBean one1 = list.get(2).getOne();
                    recyclerBeanList.add(new RecyclerBean(one1.getHome_id(), one1.getContent_type(), one1.getContent_id(), one1.getPic_url(), one1.getPos(), one1.getTopic_name(), one1.getTopic_url()));
                    HomeBean.DataBean.ItemsBean.ListBean.TwoBean two1 = list.get(2).getTwo();
                    recyclerBeanList.add(new RecyclerBean(two1.getHome_id(), two1.getContent_type(), two1.getContent_id(), two1.getPic_url(), two1.getPos(), two1.getTopic_name(), two1.getTopic_url()));
                    homeAdapter.notifyDataSetChanged();
                }
            }
        }, 8);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });

        mRecyclerView.setLayoutManager(gridLayoutManager);
        homeAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(homeAdapter);
    }

    /**
     * 新闻列表的适配器
     * 01-14 头部是 ViewPager，下面是列表新闻
     * Created by tomchen on 1/11/16.
     */
    public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;

        private ViewPager vpHottest;
        private int childCount;
        private LinearLayout llHottestIndicator;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //理论上应该把最可能返回的 TYPE 放在前面
            View view;

            if (viewType == TYPE_ITEM) {
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.home_item_view, parent, false);
                return new ItemArticleViewHolder(view);
            }
            //头部返回 ViewPager 实现的轮播图片
            if (viewType == TYPE_HEADER) {
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.home_header_view, parent, false);
                return new HeaderArticleViewHolder(view);
            }

            return null;
//        //可以抛出异常，没有对应的View类型
//        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemArticleViewHolder) {
                //转型
                ItemArticleViewHolder newHolder = (ItemArticleViewHolder) holder;
                final RecyclerBean recyclerBean = recyclerBeanList.get(position - 1);
                newHolder.imageView.setImageResource(R.drawable.brand_logo_empty);
                newHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, WebActivity.class);
                        intent.putExtra("name",recyclerBean.getTopic_name());
                        intent.putExtra("url",recyclerBean.getTopic_url());
                        startActivity(intent);
                    }
                });
                newHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(mContext).load(recyclerBean.getPic_url()).into(newHolder.imageView);
            } else if (holder instanceof HeaderArticleViewHolder) {
                HeaderArticleViewHolder newHolder = (HeaderArticleViewHolder) holder;
                vpHottest = newHolder.vpHottest;
                llHottestIndicator = newHolder.llHottestIndicator;
                imageAdapter = new HeaderImageAdapter();
                vpHottest.setAdapter(imageAdapter);

                //为ViewPager添加滚动监听，实现指示器的同步
                vpHottest.addOnPageChangeListener(pageChangeListener);
                childCount = llHottestIndicator.getChildCount();

                controlIndicator(0);
            }
        }

        private Handler mHandler = new Handler() {
            int currentIndex = 1;
            public void handleMessage(Message msg) {
                vpHottest.setCurrentItem(currentIndex%5);
                mHandler.sendEmptyMessageDelayed(1, 3000);
                currentIndex++;
            }
        };

        /**
         * 对Viewpager的监听，来使指示器同步
         */
        private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                controlIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        /**
         * 初始化指示器
         *
         * @param index
         */
        private void controlIndicator(int index) {
            ImageView view = (ImageView) llHottestIndicator.getChildAt(index);
            //初始化所有的ImageView的enable属性为false
            for (int i = 0; i < childCount; i++) {
                ImageView childView = (ImageView) llHottestIndicator.getChildAt(i);
                childView.setEnabled(false);
            }
            view.setEnabled(true);
        }

        @Override
        public int getItemCount() {
            return recyclerBeanList == null ? 0 : recyclerBeanList.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0)
                return TYPE_HEADER;
            else
                return TYPE_ITEM;
        }


        class HeaderArticleViewHolder extends RecyclerView.ViewHolder {

            ViewPager vpHottest;
            //轮播图片下面的小圆点

            LinearLayout llHottestIndicator;


            public HeaderArticleViewHolder(View itemView) {
                super(itemView);
                vpHottest = (ViewPager) itemView.findViewById(R.id.home_view_pager);
                llHottestIndicator = (LinearLayout) itemView.findViewById(R.id.home_ll_indicator);
                mHandler.sendEmptyMessageDelayed(1,3000);
            }
        }

        class ItemArticleViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;

            public ItemArticleViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.home_item_view);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }


    }


    private class HeaderImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return slideBeenList == null ? 0 : slideBeenList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return object == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(R.drawable.brand_logo_empty);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            final HomeBean.DataBean.ItemsBean.SlideBean slideBean = slideBeenList.get(position);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra("name",slideBean.getTopic_name());
                    intent.putExtra("url",slideBean.getTopic_url());
                    startActivity(intent);
                }
            });
            Glide.with(mContext).load(slideBean.getPic_url()).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
