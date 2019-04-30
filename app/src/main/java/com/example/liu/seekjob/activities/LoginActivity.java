package com.example.liu.seekjob.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liu.seekjob.R;
import com.example.liu.seekjob.db.beans.UserBean;
import com.example.liu.seekjob.services.DataManagerService;
import com.example.liu.seekjob.utils.Constants;
import com.example.liu.seekjob.utils.Error;
import com.example.liu.seekjob.utils.Util;

public class LoginActivity extends Activity {

    private TextView mCreateAccountTextView;
    private Button mLoginBtn;
    private EditText mUserNameEditText;
    private EditText mPasswordEditText;
    private TextView mUserNameErrorMsgTextView;
    private TextView mPasswordErrorMsgTextView;
    private DataManagerService mDataManagerService;

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof DataManagerService.DataManagerBinder) {
                mDataManagerService = ((DataManagerService.DataManagerBinder) service).getService();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mDataManagerService = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initViews();

        initListeners();

        initServices();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConn);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initServices() {
        bindService(new Intent(LoginActivity.this, DataManagerService.class), mConn, BIND_AUTO_CREATE);
    }

    private void initViews() {
        mCreateAccountTextView = findViewById(R.id.tv_create_account);
        mLoginBtn = findViewById(R.id.btn_login);
        mUserNameEditText = findViewById(R.id.et_user_name_input);
        mPasswordEditText = findViewById(R.id.et_password_input);
        mUserNameErrorMsgTextView = findViewById(R.id.tv_user_name_error_msg);
        mPasswordErrorMsgTextView = findViewById(R.id.tv_password_error_msg);
    }

    private void initListeners() {
        setCreateAccountText();
        setLogInBtnListener();
    }

    private void setCreateAccountText() {
        SpannableStringBuilder style = new SpannableStringBuilder();
        style.append(getString(R.string.login_activity_have_not_account_create));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivityForResult(intent, Constants.INTENT_REQUEST_CODE);
            }
        };
        style.setSpan(clickableSpan, getString(R.string.login_activity_have_not_account_create).indexOf("Create account"), getString(R.string.login_activity_have_not_account_create).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mCreateAccountTextView.setText(style);

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0000FF"));
        style.setSpan(foregroundColorSpan, getString(R.string.login_activity_have_not_account_create).indexOf("Create account"), getString(R.string.login_activity_have_not_account_create).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mCreateAccountTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mCreateAccountTextView.setText(style);
    }

    private void setLogInBtnListener() {
        mLoginBtn.setOnClickListener(v -> {
            String userNameStr = mUserNameEditText.getText().toString();
            String passwordStr = mPasswordEditText.getText().toString();
            if ("".equals(userNameStr) || "".equals(passwordStr)) {
                setUserNameErrorState("".equals(userNameStr), R.string.login_activity_hint_user_name_cannot_be_null);
                setPasswordErrorState("".equals(passwordStr), R.string.login_activity_hint_password_cannot_be_null);
                return;
            }

            switch (mDataManagerService.checkUserAccount(userNameStr, passwordStr)) {
                case Error.ERROR_ACCOUNT_NOT_EXIST:
                    setUserNameErrorState(true, R.string.login_activity_hint_user_name_is_not_exist);
                    setPasswordErrorState(false, 0);
                    break;
                case Error.ERROR_PASSWORD_INCORRECT:
                    setUserNameErrorState(false, 0);
                    setPasswordErrorState(true, R.string.login_activity_hint_password_incorrect);
                    break;
                case Error.OK:
                    setPasswordErrorState(false, 0);
                    setUserNameErrorState(false, 0);

                    UserBean bean = mDataManagerService.getUserInfo(userNameStr, passwordStr);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_USER_BEAN, bean);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
        });
    }

    private void setUserNameErrorState(boolean isError, int msgResId) {
        if (isError) {
            mUserNameErrorMsgTextView.setVisibility(View.VISIBLE);
            mUserNameErrorMsgTextView.setText(msgResId);
            Util.setEditTextBottomLine(mUserNameEditText, getResources().getDrawable(R.drawable.edit_text_bottom_line_error, null));
        } else {
            mUserNameErrorMsgTextView.setVisibility(View.GONE);
            Util.setEditTextBottomLine(mUserNameEditText, getResources().getDrawable(R.drawable.edit_text_bottom_line, null));
        }
    }

    private void setPasswordErrorState(boolean isError, int msgResId) {
        if (isError) {
            mPasswordErrorMsgTextView.setVisibility(View.VISIBLE);
            mPasswordErrorMsgTextView.setText(msgResId);
            Util.setEditTextBottomLine(mPasswordEditText, getResources().getDrawable(R.drawable.edit_text_bottom_line_error, null));
        } else {
            mPasswordErrorMsgTextView.setVisibility(View.GONE);
            Util.setEditTextBottomLine(mPasswordEditText, getResources().getDrawable(R.drawable.edit_text_bottom_line, null));
        }
    }
}
