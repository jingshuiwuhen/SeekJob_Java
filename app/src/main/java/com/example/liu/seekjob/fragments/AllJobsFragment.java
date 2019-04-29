package com.example.liu.seekjob.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liu.seekjob.R;

public class AllJobsFragment extends Fragment {
    private View mRootView;

    public static AllJobsFragment getInstance() {
        AllJobsFragment fragment = new AllJobsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.all_jobs_fragment, container, false);
        return mRootView;
    }
}
