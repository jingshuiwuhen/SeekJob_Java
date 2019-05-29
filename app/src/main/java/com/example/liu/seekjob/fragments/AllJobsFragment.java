package com.example.liu.seekjob.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.liu.seekjob.R;
import com.example.liu.seekjob.adapters.AllJobsListViewAdapter;
import com.example.liu.seekjob.beans.JobBean;

import java.util.List;

public class AllJobsFragment extends Fragment {
    private View mRootView;
    private ListView mAllJobsListView;
    private List<JobBean> mList;

    public static AllJobsFragment getInstance() {
        AllJobsFragment fragment = new AllJobsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.all_jobs_fragment, container, false);
        getAllJobsDataFromServer();
        initViews();
        initData();
        return mRootView;
    }

    private void getAllJobsDataFromServer() {

    }

    private void initViews() {
        mAllJobsListView = mRootView.findViewById(R.id.lv_all_jobs_list);
    }

    private void initData() {
        AllJobsListViewAdapter adapter = new AllJobsListViewAdapter(getActivity(), mList);
        mAllJobsListView.setAdapter(adapter);
    }
}
