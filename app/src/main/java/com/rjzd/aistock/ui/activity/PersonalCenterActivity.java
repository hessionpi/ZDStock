package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.api.UserData;
import com.rjzd.aistock.api.UserInfo;
import com.rjzd.aistock.api.UserPoints;
import com.rjzd.aistock.bean.LoginBean;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.LoginPresenter;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.imageloader.ZDImgloader;
import com.rjzd.commonlib.utils.NetWorkUtil;
import com.rjzd.commonlib.utils.ToastUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener, IFillDataView {

    @Bind(R.id.iv_mine_back)
    ImageView mBack;
    @Bind(R.id.iv_head_vip)
    ImageView mVip;
    @Bind(R.id.iv_head_portrait)
    ImageView mAvatar;
    @Bind(R.id.tv_nickname)
    TextView mNickName;
    @Bind(R.id.tv_integral)
    TextView mPoints;
    @Bind(R.id.about_us)
    TextView mAboutUs;
    @Bind(R.id.setting)
    TextView setting;
    @Bind(R.id.ll_mypoints)
    LinearLayout mPointsLayout;
    @Bind(R.id.ll_mytasks)
    LinearLayout mTasksLayout;

    @Bind(R.id.tv_daily_stock)
    TextView mBestStock;
    @Bind(R.id.tv_daily_plate)
    TextView mBestPlate;
    @Bind(R.id.tv_week_new)
    TextView mWeekNew;

    @Bind(R.id.invite_friends)
    TextView inviteFriends;
    @Bind(R.id.newer_get_points)
    TextView mNewerPoints;

    @Bind(R.id.iv_plate_remind)
    ImageView mPlateRemind;
    @Bind(R.id.iv_stock_remind)
    ImageView mStockRemind;
    @Bind(R.id.iv_weekly_remind)
    ImageView mWeeklyRemind;

    private LoginBean login;
    private LoginPresenter presenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PersonalCenterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(R.color.cl_f95555);
        setContentView(R.layout.activity_personal_center);
        ButterKnife.bind(this);
        init();

        presenter = new LoginPresenter(this);
    }

    private void init() {
        mAvatar.setOnClickListener(this);
        mNickName.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mBestStock.setOnClickListener(this);
        mBestPlate.setOnClickListener(this);
        mWeekNew.setOnClickListener(this);
        mPointsLayout.setOnClickListener(this);
        mTasksLayout.setOnClickListener(this);
        mAboutUs.setOnClickListener(this);
        setting.setOnClickListener(this);
        inviteFriends.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        login = UserInfoCenter.getInstance().getLoginModel();
        if (null != login) {
            if (NetWorkUtil.isNetworkConnected(this)) {
                presenter.getUserInfo(login.getUserid());
                showLoadingView();
            } else {
                showFailedView();
            }

            if(login.getLevel()>0){
                mVip.setImageResource(R.drawable.ic_header_vip);
            }
            mNickName.setBackground(null);
            ZDImgloader.loadTransformImage(this, login.getIconurl(), R.drawable.ic_avatar_login, R.drawable.ic_avatar_login, mAvatar, 0);
        } else {
            mPoints.setText("积分：");
            mNewerPoints.setText("");
            mNickName.setText(R.string.login_regist);
            mNickName.setBackground(ContextCompat.getDrawable(PersonalCenterActivity.this, R.drawable.shape_person_white_bg));
            mAvatar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_avatar_default));
        }

        if(Constants.recommendPlateNew){
            mPlateRemind.setVisibility(View.VISIBLE);
        }else{
            mPlateRemind.setVisibility(View.GONE);
        }

        if(Constants.recommendStockNew){
            mStockRemind.setVisibility(View.VISIBLE);
        }else{
            mStockRemind.setVisibility(View.GONE);
        }

        if(Constants.recommendWeeklyNew){
            mWeeklyRemind.setVisibility(View.VISIBLE);
        }else{
            mWeeklyRemind.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_mine_back:
                finishActivity(this);
                break;

            case R.id.iv_head_portrait:
            case R.id.tv_nickname:
                if (null == login) {
                    LoginActivity.startActivity(this);
                } else {
                    UserinfoDetailActivity.startActivity(this);
                }
                break;

            case R.id.tv_daily_stock:
                DailyBestStockActivity.startActivity(this);
                Constants.recommendStockNew = false;
                break;

            case R.id.tv_daily_plate:
                DailyBestPlateActivity.startActivity(this);
                Constants.recommendPlateNew = false;
                break;
            case R.id.tv_week_new:
                AIWeeklyActivity.startActivity(this);
                Constants.recommendWeeklyNew = false;
                break;
            case R.id.ll_mypoints:
                if (null == login) {
                    LoginActivity.startActivity(this);
                } else {
                    MyPointsActivity.startActivity(this, MyPointsActivity.POINTS_PRIVILEGE);
                }
                break;

            case R.id.ll_mytasks:
                if (null == login) {
                    LoginActivity.startActivity(this);
                } else {
                    MyPointsActivity.startActivity(this, MyPointsActivity.MY_TASK);
                }
                break;

            /*case R.id.message_notification:
                MessageNotificationActivity.startActivity(this);
                break;*/

            case R.id.invite_friends:
                if (null == login) {
                    LoginActivity.startActivity(this);
                } else {
                    InviteFriendsActivity.startActivity(this);
                }
                break;
            case R.id.about_us:
                WebActivity.startActivity(this, Constants.URL_ABOUT_US, getString(R.string.about_us));
                break;
            case R.id.setting:
                SettingActivity.startActivity(this, login);
                break;

        }
    }

    @Override
    public void fillData(Object data, int dsTag) {
        dismissLoading();
        switch (dsTag) {
            case Constants.DS_TAG_LOGIN:
                UserData uData = (UserData) data;
                if (StatusCode.OK.getValue() == uData.get_status().getValue()) {
                    UserInfo uInfo = uData.get_data();
                    ZDImgloader.loadTransformImage(this, uInfo.get_avatar(), R.drawable.ic_avatar_login, R.drawable.ic_avatar_login, mAvatar, 0);
                    mNickName.setText(uInfo.get_nickname());
                    mPoints.setText("积分：" + uInfo.get_points());

                    // 更新User信息
                    login.setUserid(uInfo.get_userId());
                    login.setCellphone(uInfo.get_phoneNumber());
                    String sex = "保密";
                    if (uInfo.get_sex() == 0) {
                        sex = "保密";
                    } else if (uInfo.get_sex() == 1) {
                        sex = "男";
                    } else if (uInfo.get_sex() == 2) {
                        sex = "女";
                    }
                    login.setGender(sex);
                    login.setNickname(uInfo.get_nickname());
                    login.setIconurl(uInfo.get_avatar());
                    login.setLocation(uInfo.get_area());
                    login.setBindWX(uInfo.is_isBindWX());
                    login.setBindQQ(uInfo.is_isBindQQ());
                    login.setBindSina(uInfo.is_isBindSina());
                    login.setLevel(uInfo.get_level());
                    login.setPoints(uInfo.get_points());
                    login.setInviteCode(uInfo.get_inviteCode());

                    UserInfoCenter.getInstance().setLoginBean(login);
                    presenter.getRecentGainPoints(uInfo.get_userId());
                } else {
                    showToast(uData.get_msg());
                }
                break;

            case Constants.DS_TAG_RECENTGAINPOINTS:
                UserPoints points = (UserPoints) data;
                String newGerPoints = getString(R.string.newer_points);
                if (StatusCode.OK.getValue() == points.get_status().getValue()) {
                    int newerP = points.get_points();
                    if (newerP > 0) {
                        mNewerPoints.setText(String.format(newGerPoints, newerP));
                    } else {
                        mNewerPoints.setText("");
                    }
                }
                break;
        }

    }

    @Override
    public void showFailedView() {
        dismissLoading();
        ToastUtils.show(this, "网络异常");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }
}
