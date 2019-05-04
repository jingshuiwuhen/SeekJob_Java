package com.example.liu.seekjob.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.liu.seekjob.R;
import com.example.liu.seekjob.db.beans.UserBean;
import com.example.liu.seekjob.services.UserDataManagerService;
import com.example.liu.seekjob.utils.Constants;
import com.example.liu.seekjob.utils.Error;
import com.example.liu.seekjob.utils.Util;

public class CreateAccountActivity extends Activity {
    private ImageView mTitleBarBackImageView;
    private TextView mTitleBarRegisterTextView;
    private EditText mUserNameInputEditText;
    private ConstraintLayout mUserNameErrorMsgLayout;
    private TextView mUserNameErrorMsgTextView;
    private EditText mPasswordInputEditText;
    private ConstraintLayout mPasswordErrorMsgLayout;
    private TextView mPasswordErrorMsgTextView;
    private EditText mComfirmPasswordInputEditText;
    private ConstraintLayout mComfirmPasswordErrorMsgLayout;
    private TextView mComfirmPasswordErrorMsgTextView;
    private EditText mNickNameInputEditText;
    private ConstraintLayout mNickNameErrorMsgLayout;
    private TextView mNickNameErrorMsgTextView;
    private EditText mMailInputEditText;
    private EditText mPhoneNumberInputEditText;
    private RadioGroup mUserLevelRadioGroup;

    private UserDataManagerService mUserDataManagerService;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof UserDataManagerService.DataManagerBinder) {
                mUserDataManagerService = ((UserDataManagerService.DataManagerBinder) service).getService();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mUserDataManagerService = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        initViews();

        initListeners();

        initServices();

        mUserLevelRadioGroup.check(R.id.rb_private);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConn);
    }

    private void initServices() {
        bindService(new Intent(CreateAccountActivity.this, UserDataManagerService.class), mConn, BIND_AUTO_CREATE);
    }

    private void initViews() {
        mTitleBarBackImageView = findViewById(R.id.iv_back);
        mTitleBarRegisterTextView = findViewById(R.id.tv_register);
        mUserNameInputEditText = findViewById(R.id.et_user_name_input);
        mUserNameErrorMsgLayout = findViewById(R.id.cl_user_name_error_msg);
        mUserNameErrorMsgTextView = findViewById(R.id.tv_user_name_error_msg);
        mPasswordInputEditText = findViewById(R.id.et_password_input);
        mPasswordErrorMsgLayout = findViewById(R.id.cl_password_error_msg);
        mPasswordErrorMsgTextView = findViewById(R.id.tv_password_error_msg);
        mComfirmPasswordInputEditText = findViewById(R.id.et_comfirm_password_input);
        mComfirmPasswordErrorMsgLayout = findViewById(R.id.cl_comfirm_password_error_msg);
        mComfirmPasswordErrorMsgTextView = findViewById(R.id.tv_comfirm_password_error_msg);
        mNickNameInputEditText = findViewById(R.id.et_nick_name_input);
        mNickNameErrorMsgLayout = findViewById(R.id.cl_nick_name_error_msg);
        mNickNameErrorMsgTextView = findViewById(R.id.tv_nick_name_error_msg);
        mMailInputEditText = findViewById(R.id.et_mail_input);
        mPhoneNumberInputEditText = findViewById(R.id.et_phone_number_input);
        mUserLevelRadioGroup = findViewById(R.id.rg_user_level);
    }

    private void initListeners() {
        mTitleBarBackImageView.setOnClickListener(v -> finish());

        mTitleBarRegisterTextView.setOnClickListener(v -> {
            boolean isError = false;
            String userNameStr = mUserNameInputEditText.getText().toString();
            if ("".equals(userNameStr)) {
                setEditTextErrorState(mUserNameErrorMsgLayout, mUserNameErrorMsgTextView,
                        R.string.create_account_activity_hint_user_name_cannot_be_null, mUserNameInputEditText);
                isError = true;
            } else if (userNameStr.length() < 8) {
                setEditTextErrorState(mUserNameErrorMsgLayout, mUserNameErrorMsgTextView,
                        R.string.create_account_activity_hint_user_name_needs_at_least_8_digits, mUserNameInputEditText);
                isError = true;
            } else if (!userNameStr.matches("^[a-zA-Z0-9]+$")) {
                setEditTextErrorState(mUserNameErrorMsgLayout, mUserNameErrorMsgTextView,
                        R.string.create_account_activity_hint_user_name_only_has_number_and_letters, mUserNameInputEditText);
                isError = true;
            } else {
                mUserNameErrorMsgLayout.setVisibility(View.GONE);
                Util.setEditTextBottomLine(mUserNameInputEditText, getResources().getDrawable(R.drawable.edit_text_bottom_line, null));
            }

            String passwordStr = mPasswordInputEditText.getText().toString();
            if ("".equals(passwordStr)) {
                setEditTextErrorState(mPasswordErrorMsgLayout, mPasswordErrorMsgTextView,
                        R.string.create_account_activity_hint_password_cannot_be_null, mPasswordInputEditText);
                isError = true;
            } else if (passwordStr.length() < 8) {
                setEditTextErrorState(mPasswordErrorMsgLayout, mPasswordErrorMsgTextView,
                        R.string.create_account_activity_hint_password_needs_at_least_8_digits, mPasswordInputEditText);
                isError = true;
            } else {
                mPasswordErrorMsgLayout.setVisibility(View.GONE);
                Util.setEditTextBottomLine(mPasswordInputEditText, getResources().getDrawable(R.drawable.edit_text_bottom_line, null));
            }

            String comfirmPasswordStr = mComfirmPasswordInputEditText.getText().toString();
            if ("".equals(comfirmPasswordStr)) {
                setEditTextErrorState(mComfirmPasswordErrorMsgLayout, mComfirmPasswordErrorMsgTextView,
                        R.string.create_account_activity_hint_comfirm_password_cannot_be_null, mComfirmPasswordInputEditText);
                isError = true;
            } else if (!passwordStr.equals(comfirmPasswordStr)) {
                setEditTextErrorState(mComfirmPasswordErrorMsgLayout, mComfirmPasswordErrorMsgTextView,
                        R.string.create_account_activity_hint_comfirm_password_does_not_equal_to_password, mComfirmPasswordInputEditText);
                isError = true;
            } else {
                mComfirmPasswordErrorMsgLayout.setVisibility(View.GONE);
                Util.setEditTextBottomLine(mComfirmPasswordInputEditText, getResources().getDrawable(R.drawable.edit_text_bottom_line, null));
            }

            String nickNameStr = mNickNameInputEditText.getText().toString();
            if ("".equals(nickNameStr)) {
                setEditTextErrorState(mNickNameErrorMsgLayout, mNickNameErrorMsgTextView,
                        R.string.create_account_activity_hint_nick_name_cannot_be_null, mNickNameInputEditText);
                isError = true;
            } else {
                mNickNameErrorMsgLayout.setVisibility(View.GONE);
                Util.setEditTextBottomLine(mNickNameInputEditText, getResources().getDrawable(R.drawable.edit_text_bottom_line, null));
            }

            if (isError)
                return;

            UserBean userBean = new UserBean();
            userBean.setUserName(userNameStr);
            userBean.setPassword(passwordStr);
            userBean.setNickName(nickNameStr);
            userBean.setMail_address(mMailInputEditText.getText().toString());
            userBean.setPhoneNumber(mPhoneNumberInputEditText.getText().toString());
            switch (mUserLevelRadioGroup.getCheckedRadioButtonId()) {
                case R.id.rb_private:
                    userBean.setUserLevel(Constants.USER_LEVEL_PRIVATE);
                    break;
                case R.id.rb_company:
                    userBean.setUserLevel(Constants.USER_LEVEL_COMPANY);
                    break;
            }

            int result = mUserDataManagerService.register(userBean);
            if (result == Error.ERROR_USER_NAME_HAS_BEEN_REGISTERED) {
                setEditTextErrorState(mUserNameErrorMsgLayout, mUserNameErrorMsgTextView,
                        R.string.create_account_activity_hint_user_name_has_been_registered, mUserNameInputEditText);
                return;
            }

            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            intent.putExtra(Constants.EXTRA_KEY_USER_BEAN, userBean);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void setEditTextErrorState(ConstraintLayout errorMsgLayout, TextView errorMsgTextView, int errorMsgResId, EditText editText) {
        errorMsgLayout.setVisibility(View.VISIBLE);
        errorMsgTextView.setText(errorMsgResId);
        Util.setEditTextBottomLine(editText, getResources().getDrawable(R.drawable.edit_text_bottom_line_error, null));
    }
}
