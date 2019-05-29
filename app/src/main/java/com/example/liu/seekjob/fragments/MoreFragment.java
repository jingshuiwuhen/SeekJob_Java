package com.example.liu.seekjob.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liu.seekjob.R;
import com.example.liu.seekjob.activities.LoginActivity;
import com.example.liu.seekjob.activities.MainActivity;
import com.example.liu.seekjob.beans.UserBean;
import com.example.liu.seekjob.utils.Constants;
import com.example.liu.seekjob.utils.Error;
import com.example.liu.seekjob.utils.Util;

public class MoreFragment extends Fragment {
    private View mRootView;
    private ImageView mHeadIconImageView;
    private Group mUserIdAndSignGroup;
    private Group mItemLogOutGroup;
    private TextView mLogInTextView;
    private TextView mNickNameTextView;
    private TextView mUserNameTextView;
    private ImageView mLogoutBackgroundImageView;

    public static MoreFragment getInstance() {
        MoreFragment fragment = new MoreFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.more_fragment, container, false);
        initViews();

        initLogState();

        initListeners();

        return mRootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data == null)
                return;

            UserBean bean = data.getParcelableExtra(Constants.EXTRA_KEY_USER_BEAN);
            setLogInState(bean, "", "");
        }
    }

    private void initViews() {
        mHeadIconImageView = mRootView.findViewById(R.id.iv_header_icon);
        mUserIdAndSignGroup = mRootView.findViewById(R.id.group_user_nick_name_and_user_name);
        mItemLogOutGroup = mRootView.findViewById(R.id.group_item_log_out);
        mLogInTextView = mRootView.findViewById(R.id.tv_login);
        mNickNameTextView = mRootView.findViewById(R.id.tv_user_nick_name);
        mUserNameTextView = mRootView.findViewById(R.id.tv_user_name);
        mLogoutBackgroundImageView = mRootView.findViewById(R.id.iv_item_log_out_background);
    }

    private void initLogState() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SP_NAME_USER, Context.MODE_PRIVATE);
        String userNameStr = sharedPreferences.getString(Constants.SP_KEY_USER_NAME, "");
        String passwordStr = sharedPreferences.getString(Constants.SP_KEY_PASSWORD, "");
        if (("".equals(userNameStr) || "".equals(passwordStr))
                || Error.OK != ((MainActivity) getActivity()).getDataManagerService().checkUserAccount(userNameStr, passwordStr)) {
            setLogOutState();
            return;
        }
        setLogInState(null, userNameStr, passwordStr);
    }

    private void initListeners() {
        mLogInTextView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginActivity.class);
            MoreFragment.this.startActivityForResult(intent, Constants.INTENT_REQUEST_CODE);
        });

        mLogoutBackgroundImageView.setOnClickListener(v -> {
            setLogOutState();
        });
    }

    private void setLogOutState() {
        mHeadIconImageView.setImageResource(R.drawable.icon_user_unlogin);
        mUserIdAndSignGroup.setVisibility(View.GONE);
        mItemLogOutGroup.setVisibility(View.GONE);
        mLogInTextView.setVisibility(View.VISIBLE);
        Util.setBooleanValueToSharedPreferences(getActivity(), Constants.SP_NAME_USER, Constants.SP_KEY_IS_LOG_IN, false);
        Util.setStringValueToSharedPreferences(getActivity(), Constants.SP_NAME_USER, Constants.SP_KEY_USER_NAME, "");
        Util.setStringValueToSharedPreferences(getActivity(), Constants.SP_NAME_USER, Constants.SP_KEY_PASSWORD, "");
    }

    private void setLogInState(UserBean user, String userName, String password) {
        if (user == null) {
            user = ((MainActivity)getActivity()).getDataManagerService().getUserInfo(userName, password);
        }

        mHeadIconImageView.setImageResource(R.drawable.icon_user_login);

        mUserIdAndSignGroup.setVisibility(View.VISIBLE);
        mNickNameTextView.setText(user.getNickName());
        mUserNameTextView.setText(user.getUserName());

        mItemLogOutGroup.setVisibility(View.VISIBLE);
        mLogInTextView.setVisibility(View.GONE);
        Util.setBooleanValueToSharedPreferences(getActivity(), Constants.SP_NAME_USER, Constants.SP_KEY_IS_LOG_IN, true);
        Util.setStringValueToSharedPreferences(getActivity(), Constants.SP_NAME_USER, Constants.SP_KEY_USER_NAME, user.getUserName());
        Util.setStringValueToSharedPreferences(getActivity(), Constants.SP_NAME_USER, Constants.SP_KEY_PASSWORD, user.getPassword());
    }
}
