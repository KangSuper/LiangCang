package com.xiekang.king.liangcang.self;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.activity.BeforeLoadActivity;


public class SelfFragment extends Fragment {
    private Context mContext;
    private TextView loadBtn;
    private ImageView settingImg;

    public static SelfFragment newInstance() {
        SelfFragment fragment = new SelfFragment();
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
        View view = inflater.inflate(R.layout.fragment_self, container, false);
        loadBtn = (TextView) view.findViewById(R.id.personal_load);
        settingImg = (ImageView) view.findViewById(R.id.personal_setting);
        loadBtn.setOnClickListener(clickListener);
        settingImg.setOnClickListener(clickListener);
        return view;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, BeforeLoadActivity.class);
            startActivity(intent);
        }
    };
}
