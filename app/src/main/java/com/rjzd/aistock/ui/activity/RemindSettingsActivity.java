package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.PushStatus;
import com.rjzd.aistock.presenter.LoginPresenter;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.LogUtil;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 提醒设置
 * <p>
 * Created by Hition on 2017/2/28.
 */

public class RemindSettingsActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, IFillDataView {

    @Bind(R.id.rl_ai_trans_remind)
    RelativeLayout aitransLayout;
    @Bind(R.id.rl_prediction_remind)
    RelativeLayout mPredictionLayout;
    @Bind(R.id.rl_prediction_daily)
    RelativeLayout rlPredictionDaily;
    @Bind(R.id.rl_important_msg_remind)
    RelativeLayout mImportantLayout;
    @Bind(R.id.ll_ai_weeklynewspaper)
    RelativeLayout llAiWeeklynewspaper;

    @Bind(R.id.cb_ai_trans_remind)
    CheckBox aitransCheckbox;

    @Bind(R.id.cb_new_msg)
    CheckBox cbNewMsg;
    @Bind(R.id.cb_remind_share)
    CheckBox cbRemindShare;
    @Bind(R.id.remind_ai_weeklynewspaper)
    CheckBox remindAiWeeklynewspaper;
    @Bind(R.id.cb_remind)
    CheckBox cbRemind;
    @Bind(R.id.cb_daily)
    CheckBox cbDaily;
    LoginPresenter presenter;
    int userId;
    // PushAll   PushNotification  PushAITransfer PushPredict PushInvite PushDailyBestPlate PushDailyBestStock PushAIWeekly
    public static final String PUSH_ALL = "PushAll";
    public static final String PUSH_NOTIFICATION = "PushNotification";
    public static final String PUSH_AI_TRANSFER = "PushAITransfer";
    public static final String PUSH_DAILYBEST_PLALE = "PushDailyBestPlate";
    public static final String PUSH_DAILYBEST_STOCK = "PushDailyBestStock";
    public static final String PUSH_INVITE = "PushInvite";
    public static final String PUSH_AI_WEEKLY = "PushAIWeekly";


    public static void startActivity(Context context, int userId) {
        Intent intent = new Intent(context, RemindSettingsActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_settings);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this);
        initView();
    }

    private void initView() {
        userId = (int) getIntent().getSerializableExtra("userId");
        if (userId > 0) {
            presenter.getPushStatus(userId);
        }
        cbNewMsg.setOnCheckedChangeListener(this);
        aitransCheckbox.setOnCheckedChangeListener(this);
        cbRemind.setOnCheckedChangeListener(this);
        cbDaily.setOnCheckedChangeListener(this);
        cbRemindShare.setOnCheckedChangeListener(this);
        remindAiWeeklynewspaper.setOnCheckedChangeListener(this);

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_new_msg:

                if (userId > 0) {
                    pushStatus(PUSH_NOTIFICATION, isChecked);
                    if (isChecked) {
                        showAllSubSwitch();
                    } else {
                        hideAllSubSwitch();
                    }
                } else {
                    NotLogin(cbNewMsg, isChecked);
                }

                break;
            case R.id.cb_ai_trans_remind: //  AI调仓提醒

                if (userId > 0) {
                    pushStatus(PUSH_AI_TRANSFER, isChecked);
                } else {
                    NotLogin(aitransCheckbox, isChecked);
                }
                break;
            case R.id.cb_remind:

                if (userId > 0)
                    pushStatus(PUSH_DAILYBEST_PLALE, isChecked);
                else {
                    NotLogin(cbRemind, isChecked);
                }
                break;
            case R.id.cb_daily:

                if (userId > 0) {
                    pushStatus(PUSH_DAILYBEST_STOCK, isChecked);
                } else {
                    NotLogin(cbDaily, isChecked);
                }
                break;
            case R.id.cb_remind_share:
                if (userId > 0) {

                    pushStatus(PUSH_INVITE, isChecked);
                } else {

                    NotLogin(cbRemindShare, isChecked);
                }
                break;
            case R.id.remind_ai_weeklynewspaper:

                if (userId > 0) {
                    pushStatus(PUSH_AI_WEEKLY, isChecked);
                } else {
                    NotLogin(remindAiWeeklynewspaper, isChecked);
                }
                break;
        }
    }

    private void pushStatus(String pushType, boolean isChecked) {
        presenter.pushSettings(userId, pushType, isChecked);
    }

    void NotLogin(CheckBox checkBox, boolean isChecked) {
        LoginActivity.startActivity(this);
        checkBox.setChecked(!isChecked);
    }

    /**
     * 隐藏所有分支开关布局
     */
    private void hideAllSubSwitch() {
        aitransLayout.setVisibility(View.GONE);
        mPredictionLayout.setVisibility(View.GONE);
        mImportantLayout.setVisibility(View.GONE);
        llAiWeeklynewspaper.setVisibility(View.GONE);
        rlPredictionDaily.setVisibility(View.GONE);

        aitransCheckbox.setChecked(false);
        cbRemind.setChecked(false);
        cbDaily.setChecked(false);
        cbRemindShare.setChecked(false);
        remindAiWeeklynewspaper.setChecked(false);

        pushStatus(PUSH_ALL, false);


    }

    /**
     * 显示所有分支开关布局
     */
    private void showAllSubSwitch() {
        aitransLayout.setVisibility(View.VISIBLE);
        mPredictionLayout.setVisibility(View.VISIBLE);
        mImportantLayout.setVisibility(View.VISIBLE);
        llAiWeeklynewspaper.setVisibility(View.VISIBLE);
        rlPredictionDaily.setVisibility(View.VISIBLE);
        aitransCheckbox.setChecked(true);
        cbRemind.setChecked(true);
        cbDaily.setChecked(true);
        cbRemindShare.setChecked(true);
        remindAiWeeklynewspaper.setChecked(true);

        pushStatus(PUSH_ALL, true);


    }


    @Override
    public void fillData(Object data, int dsTag) {
        if (dsTag == Constants.DS_TAG_PUSH_STATUS_GET) {
            PushStatus pushStatus = (PushStatus) data;
            if (pushStatus != null) {
                Map<String, Boolean> pushData = pushStatus.get_data();
                LogUtil.e(pushData.toString());
                cbNewMsg.setChecked(pushData.get(PUSH_NOTIFICATION));
                aitransCheckbox.setChecked(pushData.get(PUSH_AI_TRANSFER));
                cbRemind.setChecked(pushData.get(PUSH_DAILYBEST_PLALE));
                cbDaily.setChecked(pushData.get(PUSH_DAILYBEST_STOCK));
                cbRemindShare.setChecked(pushData.get(PUSH_INVITE));
                remindAiWeeklynewspaper.setChecked(pushData.get(PUSH_AI_WEEKLY));

            }

        }
    }

    @Override
    public void showFailedView() {

    }


}
