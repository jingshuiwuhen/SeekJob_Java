package com.example.liu.seekjob.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.liu.seekjob.R;
import com.example.liu.seekjob.fragments.AllJobsFragment;
import com.example.liu.seekjob.fragments.MoreFragment;
import com.example.liu.seekjob.fragments.PublishFragment;
import com.example.liu.seekjob.services.UserDataManagerService;

public class MainActivity extends FragmentActivity {

    private RelativeLayout mAllJobsRelativeLayout;
    private RelativeLayout mPublishRelativeLayout;
    private RelativeLayout mMoreRelativeLayout;
    private Fragment mCurrentFragment;
    private AllJobsFragment mAllJobsFragment;
    private PublishFragment mPublishFragment;
    private MoreFragment mMoreFragment;
    private UserDataManagerService mUserDataManagerService;

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof UserDataManagerService.DataManagerBinder) {
                mUserDataManagerService = ((UserDataManagerService.DataManagerBinder) service).getService();
            }

            if (mUserDataManagerService != null) {
                initState();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mUserDataManagerService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initServices();

        initViews();

        setListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConn);
    }

    private View.OnClickListener mFooterTabItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_footer_tab_item_all_jobs:
                    mAllJobsRelativeLayout.setSelected(true);
                    mPublishRelativeLayout.setSelected(false);
                    mMoreRelativeLayout.setSelected(false);
                    switchFragment(mAllJobsFragment);
                    break;
                case R.id.rl_footer_tab_item_publish:
                    mAllJobsRelativeLayout.setSelected(false);
                    mPublishRelativeLayout.setSelected(true);
                    mMoreRelativeLayout.setSelected(false);
                    switchFragment(mPublishFragment);
                    break;
                case R.id.rl_footer_tab_item_more:
                    mAllJobsRelativeLayout.setSelected(false);
                    mPublishRelativeLayout.setSelected(false);
                    mMoreRelativeLayout.setSelected(true);
                    switchFragment(mMoreFragment);
                    break;
            }
        }
    };

    private void initViews() {
        mAllJobsRelativeLayout = findViewById(R.id.rl_footer_tab_item_all_jobs);
        mPublishRelativeLayout = findViewById(R.id.rl_footer_tab_item_publish);
        mMoreRelativeLayout = findViewById(R.id.rl_footer_tab_item_more);
        mAllJobsFragment = AllJobsFragment.getInstance();
        mPublishFragment = PublishFragment.getInstance();
        mMoreFragment = MoreFragment.getInstance();
    }

    private void setListeners() {
        mAllJobsRelativeLayout.setOnClickListener(mFooterTabItemClickListener);
        mPublishRelativeLayout.setOnClickListener(mFooterTabItemClickListener);
        mMoreRelativeLayout.setOnClickListener(mFooterTabItemClickListener);
    }

    private void initState() {
        mAllJobsRelativeLayout.setSelected(true);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fl_content, mPublishFragment);
        ft.add(R.id.fl_content, mMoreFragment);
        ft.hide(mPublishFragment);
        ft.hide(mMoreFragment);
        ft.add(R.id.fl_content, mAllJobsFragment);
        ft.commit();
        mCurrentFragment = mAllJobsFragment;
    }

    private void initServices() {
        bindService(new Intent(MainActivity.this, UserDataManagerService.class), mConn, BIND_AUTO_CREATE);
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.hide(mCurrentFragment)
        .show(fragment)
        .commit();
        mCurrentFragment = fragment;
    }

    public UserDataManagerService getDataManagerService() {
        return mUserDataManagerService;
    }
}
