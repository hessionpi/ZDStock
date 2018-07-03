package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.BindType;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.bean.LoginBean;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.LoginPresenter;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.ToastUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import java.util.Map;
import butterknife.Bind;
import butterknife.ButterKnife;

/** 账号绑定
 * Created by Hition on 2017/4/26.
 */

public class AccountBindingActivity extends BaseActivity implements View.OnClickListener,IFillDataView{

    @Bind(R.id.rl_binding_mobile)
    RelativeLayout mBindingMobilelayout;
    @Bind(R.id.rl_binding_wx)
    RelativeLayout mBindingWXlayout;
    @Bind(R.id.rl_binding_qq)
    RelativeLayout mBindingQQlayout;
    @Bind(R.id.rl_binding_sina)
    RelativeLayout mBindingSinalayout;

    @Bind(R.id.tv_binding_mobile)
    TextView mTVBindingMobile;
    @Bind(R.id.tv_binding_wx)
    TextView mTVBindingWX;
    @Bind(R.id.tv_binding_qq)
    TextView mTVBindingQQ;
    @Bind(R.id.tv_binding_sina)
    TextView mTVBindingSina;

    private boolean hasBindMobile = false;
    private boolean hasBindWX = false;
    private boolean hasBindQQ = false;
    private boolean hasBindSina = false;

    private LoginPresenter presenter;
    private BindType btype;
    private LoginBean login;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AccountBindingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_binding);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        presenter = new LoginPresenter(this);
        login = UserInfoCenter.getInstance().getLoginModel();

        if(null != login){
            if(!TextUtils.isEmpty(login.getCellphone())){
                hasBindMobile = true;
                mTVBindingMobile.setTextColor(ContextCompat.getColor(this,R.color.cl_stock_code));
                mTVBindingMobile.setText(login.getCellphone());
            }else{
                mTVBindingMobile.setTextColor(ContextCompat.getColor(this,R.color.cl_unbind));
                mTVBindingMobile.setText("未绑定");
            }

            if(login.isBindWX()){
                hasBindWX = true;
                mTVBindingWX.setTextColor(ContextCompat.getColor(this,R.color.cl_stock_code));
                mTVBindingWX.setText("已绑定");
            }else {
                mTVBindingWX.setTextColor(ContextCompat.getColor(this,R.color.cl_unbind));
                mTVBindingWX.setText("未绑定");
            }

            if(login.isBindQQ()){
                hasBindQQ = true;
                mTVBindingQQ.setTextColor(ContextCompat.getColor(this,R.color.cl_stock_code));
                mTVBindingQQ.setText("已绑定");
            }else{
                mTVBindingQQ.setTextColor(ContextCompat.getColor(this,R.color.cl_unbind));
                mTVBindingQQ.setText("未绑定");
            }

            if(login.isBindSina()){
                hasBindSina = true;
                mTVBindingSina.setTextColor(ContextCompat.getColor(this,R.color.cl_stock_code));
                mTVBindingSina.setText("已绑定");
            }else{
                mTVBindingSina.setTextColor(ContextCompat.getColor(this,R.color.cl_unbind));
                mTVBindingSina.setText("未绑定");
            }
        }

        mBindingMobilelayout.setOnClickListener(this);
        mBindingWXlayout.setOnClickListener(this);
        mBindingQQlayout.setOnClickListener(this);
        mBindingSinalayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_binding_mobile:
                if(!hasBindMobile){
                    BindingMobileActivity.startActivityForResult(this);
                }
                break;

            case R.id.rl_binding_wx:
                if(!hasBindWX){
                    bindOAuth(SHARE_MEDIA.WEIXIN);
                }
                break;

            case R.id.rl_binding_qq:
                if(!hasBindQQ){
                    bindOAuth(SHARE_MEDIA.QQ);
                }
                break;

            case R.id.rl_binding_sina:
                if(!hasBindSina){
                    bindOAuth(SHARE_MEDIA.SINA);
                }
                break;
        }
    }

    /**
     * 绑定第三方账号
     * @param platform                      平台
     */
    private void bindOAuth(SHARE_MEDIA platform) {
        UMShareAPI.get(getApplicationContext()).getPlatformInfo(this, platform, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) { }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            //回调成功，即登陆成功后这里返回Map<String, String> map，map里面就是用户的信息，可以拿出来使用了
            if (map != null) {
                String oauthId = map.get("uid");
                if (share_media == SHARE_MEDIA.QQ) {
                    btype = BindType.QQ;
                } else if (share_media == SHARE_MEDIA.SINA) {
                    btype = BindType.SINA;
                } else if (share_media == SHARE_MEDIA.WEIXIN) {
                    btype = BindType.WEIXIN;
                }
                presenter.bindAccount(UserInfoCenter.getInstance().getUserId(),oauthId,btype);
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            String mess = throwable.getMessage();
            if (mess.equals(UmengErrorCode.NotInstall.getMessage())) {
                ToastUtils.show(getApplicationContext(), "授权失败:未安装应用", Toast.LENGTH_SHORT);
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            ToastUtils.show(getApplicationContext(), "取消授权", Toast.LENGTH_SHORT);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 200){
            String mobile = data.getStringExtra("binding_mobile");
            if(!TextUtils.isEmpty(mobile)){
                mTVBindingMobile.setTextColor(ContextCompat.getColor(this,R.color.cl_stock_code));
                mTVBindingMobile.setText(mobile);
            }
        }else{
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void fillData(Object data, int dsTag) {
        switch (dsTag){
            case Constants.DS_TAG_BINDING_ACCOUNT:
                IsSuccess sucData = (IsSuccess) data;
                if(StatusCode.OK.getValue() == sucData.get_status().getValue() && sucData.is_data()){
                    switch (btype){
                        case WEIXIN:
                            mTVBindingWX.setTextColor(ContextCompat.getColor(this,R.color.cl_stock_code));
                            mTVBindingWX.setText("已绑定");
                            login.setBindWX(true);
                            break;

                        case QQ:
                            mTVBindingQQ.setTextColor(ContextCompat.getColor(this,R.color.cl_stock_code));
                            mTVBindingQQ.setText("已绑定");
                            login.setBindQQ(true);
                            break;

                        case SINA:
                            mTVBindingSina.setTextColor(ContextCompat.getColor(this,R.color.cl_stock_code));
                            mTVBindingSina.setText("已绑定");
                            login.setBindSina(true);
                            break;
                    }
                    UserInfoCenter.getInstance().setLoginBean(login);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }
}
