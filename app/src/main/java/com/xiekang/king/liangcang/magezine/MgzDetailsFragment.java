package com.xiekang.king.liangcang.magezine;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.utils.MgzCallBack;

import java.util.ArrayList;
import java.util.List;


public class MgzDetailsFragment extends Fragment {
    private Context mContext;
    private MgzCallBack mgzCallBack;
    private RadioGroup mRadioGroup;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TextView mUpTxt;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    public static MgzDetailsFragment newInstance() {
        MgzDetailsFragment fragment = new MgzDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        if (mContext instanceof MgzCallBack){
            mgzCallBack = (MgzCallBack) mContext;
        }
        loadData();
    }

    private void loadData() {
        titles.add("分类");
        titles.add("作者");
        fragmentList.add(CateFragment.newInstance());
        fragmentList.add(AuthorFragment.newInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mgz_details, container, false);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.mgz_details_rg_all);
        mRadioGroup.setOnCheckedChangeListener(checkedChangeListener);


        mTabLayout = (TabLayout) view.findViewById(R.id.mgz_details_tab_layout);
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());

        mViewPager = (ViewPager) view.findViewById(R.id.mgz_details_view_pager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(myPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(pageChangeListener);

        mUpTxt = (TextView) view.findViewById(R.id.mgz_details_up_txt);
        mUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mgzCallBack.mBackCall();
            }
        });
        return view;
    }



    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.mgz_details_rb_category:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.mgz_details_rb_author:
                    mViewPager.setCurrentItem(1);
                    break;
            }
        }
    };

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton childAt = (RadioButton) mRadioGroup.getChildAt(position);
            childAt.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
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
