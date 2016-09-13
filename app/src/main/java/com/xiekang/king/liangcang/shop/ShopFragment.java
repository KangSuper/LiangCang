package com.xiekang.king.liangcang.shop;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.activity.ProductSearchActivity;
import com.xiekang.king.liangcang.shop.brand.BrandFragment;
import com.xiekang.king.liangcang.shop.category.CategoryFragment;
import com.xiekang.king.liangcang.shop.gift.GiftFragment;
import com.xiekang.king.liangcang.shop.home.HomeFragment;
import com.xiekang.king.liangcang.shop.topic.TopicFragment;

import java.util.ArrayList;
import java.util.List;


public class ShopFragment extends Fragment {

    private Context mContext;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private MyFragmentPagerAdapter mFragmentPagerAdapter;
    private ImageView mCartImg;
    private ImageView mSearchImg;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public static ShopFragment newInstance() {
        ShopFragment fragment = new ShopFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        loadFragment();
    }

    private void loadFragment() {
        fragmentList.add(CategoryFragment.newInstance());
        fragmentList.add(BrandFragment.newInstance());
        fragmentList.add(HomeFragment.newInstance());
        fragmentList.add(TopicFragment.newInstance());
        fragmentList.add(GiftFragment.newInstance());
        titles.add("分类");
        titles.add("品牌");
        titles.add("首页");
        titles.add("专题");
        titles.add("礼物");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        mCartImg = (ImageView) view.findViewById(R.id.shop_cart_img);

        mSearchImg = (ImageView) view.findViewById(R.id.shop_search_img);
        mSearchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductSearchActivity.class);
                startActivity(intent);
            }
        });
        mTabLayout = (TabLayout) view.findViewById(R.id.shop_tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.shop_view_pager);



        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mFragmentPagerAdapter = new MyFragmentPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
