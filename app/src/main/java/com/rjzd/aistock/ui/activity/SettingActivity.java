package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rjzd.aistock.R;
import com.rjzd.aistock.bean.LoginBean;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.utils.AppVersionUtil;
import com.rjzd.aistock.utils.CheckVersionUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;


public class SettingActivity extends BaseActivity implements View.OnClickListener {
    /*   @Bind(R.id.person_share)
       TextView personShare;*/
    @Bind(R.id.idea_tickling)
    TextView ideaTickling;
    @Bind(R.id.scoring)
    TextView scoring;
    @Bind(R.id.tv_account_binding)
    TextView tvAccountBinding;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.versions)
    LinearLayout versions;
    @Bind(R.id.logout)
    Button logout;
    LoginBean login;
    @Bind(R.id.remind_settings)
    TextView remindSettings;

    public static void startActivity(Context context, LoginBean login) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.putExtra("login", login);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        login = (LoginBean) getIntent().getSerializableExtra("login");
        // mRemind.setOnClickListener(this);
        tvAccountBinding.setOnClickListener(this);
        logout.setOnClickListener(this);
        ideaTickling.setOnClickListener(this);
        versions.setOnClickListener(this);
        remindSettings.setOnClickListener(this);
        //   personShare.setOnClickListener(this);
        scoring.setOnClickListener(this);
        tvVersion.setText("当前版本 " + AppVersionUtil.getVersionName(this));
        if (null != login) {
            logout.setVisibility(View.VISIBLE);
        } else {
            logout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.person_share:
                ShareFriendActivity.startActivity(this);
                break;*/
            case R.id.remind_settings:
                if (null != login) {
                    RemindSettingsActivity.startActivity(this,login.getUserid());
                } else {
                    RemindSettingsActivity.startActivity(this,-1);
                }

                break;
            case R.id.scoring:
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("market://details?id=" + getPackageName()));
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tv_account_binding:
                if (null != login) {
                    AccountBindingActivity.startActivity(this);
                } else {
                    LoginActivity.startActivity(this);
                }

                break;
            case R.id.logout:
                //注册JPush别名,未登录用户用unregister来作为别名
                JPushInterface.setAlias(this, 0, "unregister");
                UserInfoCenter.getInstance().reset();
                UserInfoCenter.getInstance().clearUserInfo();
//                SPUtils.clear();
                finishActivity(this);
                break;
            case R.id.idea_tickling:
                FeedbackActivity.startActivity(this);
                break;
            case R.id.versions:
                CheckVersionUtils.getInstance().checkUpdate(this, true);
                break;


        }
    }
}
