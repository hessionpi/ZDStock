package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.LoginType;
import com.rjzd.aistock.api.OAuthInfo;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.api.UserData;
import com.rjzd.aistock.api.UserInfo;
import com.rjzd.aistock.bean.LoginBean;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.LoginPresenter;
import com.rjzd.aistock.ui.views.ClearEditText;
import com.rjzd.aistock.utils.OSHelper;
import com.rjzd.aistock.utils.SPUtils;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.NetWorkUtil;
import com.rjzd.commonlib.utils.RegularUtils;
import com.rjzd.commonlib.utils.ToastUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import java.util.Map;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import static com.rjzd.aistock.Constants.DS_TAG_LOGIN;
import static com.rjzd.aistock.R.id.send_code;

public class LoginActivity extends BaseActivity implements IFillDataView, View.OnClickListener {

    @Bind(R.id.iv_close_login)
    ImageView mClose;
    @Bind(R.id.login_qq)
    ImageView loginQq;
    @Bind(R.id.login_weixin)
    ImageView loginWeixin;
    @Bind(R.id.login_weibo)
    ImageView loginWeibo;
    @Bind(R.id.cet_number)
    ClearEditText cetNumber;
    @Bind(R.id.et_auth_code)
    EditText etAuthCode;
    @Bind(send_code)
    TextView sendCode;
    @Bind(R.id.login_bt)
    Button loginBt;
    @Bind(R.id.cb_master_swatch)
    CheckBox cbMasterSwatch;
    @Bind(R.id.tv_user_agreement)
    TextView mUserAgreement;


    private SHARE_MEDIA platform = null;
    private LoginBean loginBean;
    private LoginPresenter presenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if(OSHelper.isMIUI()){
                setMiuiStatusBarDarkMode(this,true);
            }else if(OSHelper.isFlyme()){
                FlymeSetStatusBarLightMode(getWindow(),true);
            }
        }

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginBean = new LoginBean();
        presenter = new LoginPresenter(this);
        mClose.setOnClickListener(this);
        loginQq.setOnClickListener(this);
        loginWeixin.setOnClickListener(this);
        loginWeibo.setOnClickListener(this);
        sendCode.setOnClickListener(this);
        loginBt.setOnClickListener(this);
        mUserAgreement.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_user_agreement:
                WebActivity.startActivity(this,Constants.URL_USER_AGREEMENT,getString(R.string.user_agreement));
                break;
            case R.id.iv_close_login:
                finishActivity(this);
                break;
            case R.id.login_qq:
                platform = SHARE_MEDIA.QQ;
                oauthLogin();
                break;
            case R.id.login_weixin:
                platform = SHARE_MEDIA.WEIXIN;
                oauthLogin();
                break;
            case R.id.login_weibo:
                platform = SHARE_MEDIA.SINA;
                oauthLogin();
                break;
            case send_code:
                String number = cetNumber.getText().toString();
                Boolean isMobile = RegularUtils.isMobileExact(number);
                if(!isMobile){
                    ToastUtils.show(this, "手机号格式不正确", Toast.LENGTH_LONG);
                    return ;
                }
                if(NetWorkUtil.isNetworkConnected(this)){
                    presenter.sendDynamicPwd(number);
                    sendCode.setBackgroundResource(R.drawable.login_code_timer);
                    sendCode.setTextColor(ContextCompat.getColor(this, R.color.cl_888888));
                    MyCountTimer timer = new MyCountTimer(Constants.MAX_TIME_LENGTH, Constants.TICK_TIME_LENGTH);
                    timer.start();
                    sendCode.setEnabled(false);
                }
                break;
            case R.id.login_bt:
                String mobile = cetNumber.getText().toString();
                String pwd = etAuthCode.getText().toString();
                Boolean isPhone = RegularUtils.isMobileExact(mobile);
                if(!isPhone){
                    ToastUtils.show(this, "手机号格式不正确", Toast.LENGTH_LONG);
                    return ;
                }

                if(TextUtils.isEmpty(pwd) || pwd.trim().length()<4){
                    showToast("请输入四位验证码");
                    return ;
                }

                if (cbMasterSwatch.isChecked()) {
                    if(NetWorkUtil.isNetworkConnected(this)){
                        presenter.loginByDynamicPwd(mobile, pwd);
                        showLoadingView();
                    }
                } else {
                    ToastUtils.show(this, "请阅读并勾选《用户服务协议》", Toast.LENGTH_LONG);
                }
                break;
        }

    }

    /**
     * 第三方登录
     */
    void oauthLogin() {
        // UMShareAPI.get(getApplicationContext()).doOauthVerify(LoginActivity.this, platform, umAuthListener);
        UMShareAPI.get(getApplicationContext()).getPlatformInfo(LoginActivity.this, platform, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            ToastUtils.show(getApplicationContext(), "授权开始", Toast.LENGTH_SHORT);
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            //回调成功，即登陆成功后这里返回Map<String, String> map，map里面就是用户的信息，可以拿出来使用了
            if (map != null) {
                OAuthInfo oInfo = new OAuthInfo();
                if (share_media == SHARE_MEDIA.QQ) {
                    oInfo.set_openId(map.get("uid"));
                    oInfo.set_type(LoginType.QQ);
                    oInfo.set_nickname(map.get("name"));
                    oInfo.set_avatar(map.get("iconurl"));
                    oInfo.set_sex(map.get("gender"));
                    oInfo.set_area(map.get("province") + " " + map.get("city"));
                } else if (share_media == SHARE_MEDIA.SINA) {
                    oInfo.set_openId(map.get("uid"));
                    oInfo.set_type(LoginType.SINA);
                    oInfo.set_nickname(map.get("name"));
                    oInfo.set_avatar(map.get("iconurl"));
                    oInfo.set_sex(map.get("gender"));
                    oInfo.set_area(map.get("location"));
                } else if (share_media == SHARE_MEDIA.WEIXIN) {
                    oInfo.set_openId(map.get("uid"));
                    oInfo.set_type(LoginType.WEIXIN);
                    oInfo.set_nickname(map.get("name"));
                    oInfo.set_avatar(map.get("iconurl"));
                    oInfo.set_sex(map.get("gender"));
                    oInfo.set_area(map.get("country") + " " + map.get("prvinice") + " " + map.get("city"));
                }
                presenter.oauthLogin(oInfo);
            }

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            //  Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
            //  LogUtil.e("ZSJ", "授权失败" + throwable);
            String mess = throwable.getMessage();
            if (mess.equals(UmengErrorCode.NotInstall.getMessage())) {
                ToastUtils.show(getApplicationContext(), "授权失败:未安装应用", Toast.LENGTH_SHORT);
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            ToastUtils.show(getApplicationContext(), "授权取消", Toast.LENGTH_SHORT);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 动态密码登录成功
     */
    @Override
    public void fillData(Object data, int dsTag) {
        dismissLoading();
        switch (dsTag) {
            case DS_TAG_LOGIN:
                UserData udata = (UserData) data;
                if (udata.get_status().getValue() == StatusCode.OK.getValue()) {
                    UserInfo uInfo = udata.get_data();
                    // 注册JPush别名
                    JPushInterface.setAlias(this,0,String.valueOf(uInfo.get_userId()));

                    loginBean.setUserid(uInfo.get_userId());
                    loginBean.setCellphone(uInfo.get_phoneNumber());
                    String sex = "保密";
                    if (uInfo.get_sex() == 0) {
                        sex = "保密";
                    } else if (uInfo.get_sex() == 1) {
                        sex = "男";
                    } else if (uInfo.get_sex() == 2) {
                        sex = "女";
                    }
                    loginBean.setGender(sex);
                    loginBean.setNickname(uInfo.get_nickname());
                    loginBean.setIconurl(uInfo.get_avatar());
                    loginBean.setLocation(uInfo.get_area());
                    loginBean.setBindWX(uInfo.is_isBindWX());
                    loginBean.setBindQQ(uInfo.is_isBindQQ());
                    loginBean.setBindSina(uInfo.is_isBindSina());
                    loginBean.setLevel(uInfo.get_level());
                    loginBean.setPoints(uInfo.get_points());
                    loginBean.setInviteCode(uInfo.get_inviteCode());

                    UserInfoCenter.getInstance().setLoginBean(loginBean);
                    // 登陆成功后判断是否同步过自选股，如果没有就向服务后端同步本地加入的自选股
                    boolean hasSynchronize = (boolean) SPUtils.get("has_synchronize",false);
                    if(hasSynchronize){
                        finishActivity(this);
                        return ;
                    }
                    presenter.synchronizePortfolio(uInfo.get_userId());
                    finishActivity(this);
                } else{
                    showToast(udata.get_msg());
                }
                break;
        }
    }

    @Override
    public void showFailedView() {

    }

    private class MyCountTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            sendCode.setText(millisUntilFinished / 1000 + " S");
        }

        @Override
        public void onFinish() {
            sendCode.setEnabled(true);
            sendCode.setBackgroundResource(R.drawable.login_code);
            sendCode.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
            sendCode.setText(R.string.send_code);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }

}
