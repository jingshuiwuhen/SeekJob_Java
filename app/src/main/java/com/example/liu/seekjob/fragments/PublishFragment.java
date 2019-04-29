package com.example.liu.seekjob.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liu.seekjob.R;

public class PublishFragment extends Fragment {
    private View mRootView;

    public static PublishFragment getInstance() {
        PublishFragment fragment = new PublishFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.publish_fragment, container, false);
        return mRootView;
    }
}
