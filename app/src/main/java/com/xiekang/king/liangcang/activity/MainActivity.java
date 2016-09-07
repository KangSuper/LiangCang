package com.xiekang.king.liangcang.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.magezine.MagezineFragment;
import com.xiekang.king.liangcang.self.SelfFragment;
import com.xiekang.king.liangcang.share.ShareFragment;
import com.xiekang.king.liangcang.shop.ShopFragment;
import com.xiekang.king.liangcang.talent.TalentFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private Fragment mCurrentShowFragment;
    private boolean isExit;
    private RadioGroup mRadioGroup;
    private ShopFragment shopFragment;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            isExit = false;
        }
    };
    private MagezineFragment magezineFragment;
    private TalentFragment talentFragment;
    private ShareFragment shareFragment;
    private SelfFragment selfFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        initView();
    }

    private void initView() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        mRadioGroup = (RadioGroup) findViewById(R.id.main_radio_group);
        mRadioGroup.setOnCheckedChangeListener(checkedChangeListener);
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        shopFragment = ShopFragment.newInstance();
        magezineFragment = MagezineFragment.newInstance();
        talentFragment = TalentFragment.newInstance();
        shareFragment = ShareFragment.newInstance();
        selfFragment = SelfFragment.newInstance();
        ctrlFragment(shopFragment);
    }


    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.main_shop_rb:
                    ctrlFragment(shopFragment);
                    break;
                case R.id.main_mgz_rb:
                    ctrlFragment(magezineFragment);
                    break;
                case R.id.main_talent_rb:
                    ctrlFragment(talentFragment);
                    break;
                case R.id.main_share_rb:
                    ctrlFragment(shareFragment);
                    break;
                case R.id.main_self_rb:
                    ctrlFragment(selfFragment);
                    break;
            }
        }
    };


    /**
     * @param fragment 当前要显示的Fragment
     */
    private void ctrlFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mCurrentShowFragment != null && mCurrentShowFragment.isAdded()) {
            fragmentTransaction.hide(mCurrentShowFragment);
        }
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.main_frame_layout, fragment);
        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.commit();

        mCurrentShowFragment = fragment;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
}
