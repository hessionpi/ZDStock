package com.rjzd.aistock.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.BindType;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.LoginPresenter;
import com.rjzd.aistock.ui.views.ClearEditText;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.RegularUtils;
import com.rjzd.commonlib.utils.ToastUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**绑定手机号
 *
 * Created by Hition on 2017/4/27.
 */

public class BindingMobileActivity extends BaseActivity implements View.OnClickListener,IFillDataView{

    @Bind(R.id.et_mobile)
    ClearEditText mobileView;
    @Bind(R.id.et_send_code)
    EditText mCodeView;
    @Bind(R.id.btn_send_code)
    Button mSendView;
    @Bind(R.id.btn_submit)
    Button mSubmit;

    private LoginPresenter presenter;
    private String phoneNumber;

    public static void startActivityForResult(Activity context) {
        Intent intent = new Intent(context, BindingMobileActivity.class);
        context.startActivityForResult(intent,100);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_mobile);
        ButterKnife.bind(this);
        init();
        presenter = new LoginPresenter(this);
    }

    private void init() {
        mobileView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    // 正则表达式校验所输入的手机号是否合法
                    boolean isMobile = checkMobile(mobileView.getText().toString().trim());
                    if(!isMobile){
                        showToast("手机号码不正确，请确认后重新输入");
                    }
                }
            }
        });
        mSendView.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
    }

    public boolean checkMobile(String mobile){
       return RegularUtils.isMobileExact(mobile);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_code:
                String mobile = mobileView.getText().toString().trim();
                boolean isMobile = checkMobile(mobile);
                if(!isMobile){
                    showToast("手机号码不正确，请确认后重新输入");
                    return ;
                }
                // 发送动态验证码
                presenter.sendDynamicPwd(mobile);
                mSendView.setBackgroundResource(R.drawable.login_code_timer);
                mSendView.setTextColor(ContextCompat.getColor(this,R.color.cl_888888));
                MyTimer timer = new MyTimer(Constants.MAX_TIME_LENGTH,Constants.TICK_TIME_LENGTH);
                timer.start();
                mSendView.setEnabled(false);
                break;

            case R.id.btn_submit:
                phoneNumber = mobileView.getText().toString().trim();
                String sendCode = mCodeView.getText().toString().trim();
                if(!TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(sendCode)){
                    presenter.bindAccount(UserInfoCenter.getInstance().getUserId(),phoneNumber+";"+sendCode, BindType.MOBILE);
                }
                break;
        }
    }

    @Override
    public void fillData(Object data, int dsTag) {
        switch (dsTag){
            case Constants.DS_TAG_BINDING_ACCOUNT:
                IsSuccess sucData = (IsSuccess) data;
                if(StatusCode.OK.getValue() == sucData.get_status().getValue()){
                    Intent intent = new Intent();
                    intent.putExtra("binding_mobile", phoneNumber);
                    setResult(200, intent);
                    finishActivity(this);
                }else if(StatusCode.BINDED.getValue() == sucData.get_status().getValue()){
                    showToast(sucData.get_msg());
                }
                break;
            default:

                break;
        }
    }

    @Override
    public void showFailedView() {

    }

    private class MyTimer extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mSendView.setText(millisUntilFinished/1000 + " S");
        }

        @Override
        public void onFinish() {
            mSendView.setEnabled(true);
            mSendView.setBackgroundResource(R.drawable.login_code);
            mSendView.setTextColor(ContextCompat.getColor(BindingMobileActivity.this,R.color.white));
            mSendView.setText(R.string.send_code);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }
}
