package com.rjzd.aistock.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.api.TaskStatuData;
import com.rjzd.aistock.api.TaskStatus;
import com.rjzd.aistock.presenter.PointsPresenter;
import com.rjzd.aistock.ui.activity.AccountBindingActivity;
import com.rjzd.aistock.ui.activity.InviteFriendsActivity;
import com.rjzd.aistock.ui.activity.SearchActivity;
import com.rjzd.aistock.utils.view.DialogManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的任务
 *
 * Created by Hition on 2017/7/20.
 */

public class TaskFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.points_invite_get)
    TextView mInviteGetPoints;
    @Bind(R.id.points_daily_use_get)
    TextView mDailyUseGetPoints;
    @Bind(R.id.points_share_get)
    TextView mShareGetPoints;
    @Bind(R.id.points_add_portfolio)
    TextView mAddPortfolioGetPoints;
    @Bind(R.id.points_attention_ai_get)
    TextView mAttentionAIGetPoints;
    @Bind(R.id.points_account_binding_get)
    TextView mBindingaccountPoints;

    @Bind(R.id.tv_go_invite)
    TextView mInvite;
    @Bind(R.id.tv_share_app)
    TextView mShare;
    @Bind(R.id.tv_add_portfolio)
    TextView mAddportfolio;
    @Bind(R.id.tv_go_attention)
    TextView mAttention;
    @Bind(R.id.tv_go_binding)
    TextView mGoBinding;

    private int userId;
    private PointsPresenter presenter;
    private List<String> taskIdList = new ArrayList<>();

    private boolean hasInvite = false;
    private boolean hasAdd = false;
    private boolean hasAttentionAI = false;
    private boolean hasBind = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_task,container,false);
        ButterKnife.bind(this,mView);
        initView();
        presenter = new PointsPresenter(this);
        return mView;
    }

    private void initView() {
        Bundle bundle = getArguments();
        if(null != bundle){
            userId = bundle.getInt("user_id");
        }
        taskIdList.add(Constants.TASK_INVITE);
        taskIdList.add(Constants.TASK_LOGIN);
        taskIdList.add(Constants.TASK_SHARE);
        taskIdList.add(Constants.TASK_ATTENTIONSTOCK);
        taskIdList.add(Constants.TASK_ATTENTIONAI);
        taskIdList.add(Constants.TASK_BIND);

        mInvite.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mAddportfolio.setOnClickListener(this);
        mAttention.setOnClickListener(this);
        mGoBinding.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getMyTaskStatus(userId,taskIdList);
    }

    @Override
    public void setStatistical() {
        statistical = "我的任务";
    }

    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data, dsTag);
        switch(dsTag){
            case Constants.DS_TAG_MYTASKSTATUS:
                TaskStatuData taskData = (TaskStatuData) data;
                if(StatusCode.OK.getValue() == taskData.get_status().getValue()){
                    List<TaskStatus> tasks = taskData.get_data();
                    if(null == tasks || tasks.isEmpty()){
                        return ;
                    }

                    mInviteGetPoints.setText(String.valueOf(tasks.get(0).get_gainPoints()));
                    mDailyUseGetPoints.setText(String.valueOf(tasks.get(1).get_gainPoints()));
                    mShareGetPoints.setText(String.valueOf(tasks.get(2).get_gainPoints()));
                    mAddPortfolioGetPoints.setText(String.valueOf(tasks.get(3).get_gainPoints()));
                    if(1 == tasks.get(3).get_finishFlag()){
                        hasAdd = true;
                        mAddportfolio.setText(R.string.finish_today);
                        mAddportfolio.setBackgroundResource(R.drawable.points_unlock_shape);
                    }else if(0 == tasks.get(3).get_finishFlag()){
                        mAddportfolio.setBackgroundResource(R.drawable.points_lock_shape);
                    }

                    mAttentionAIGetPoints.setText(String.valueOf(tasks.get(4).get_gainPoints()));
                    if(1 == tasks.get(4).get_finishFlag()){
                        hasAttentionAI = true;
                        mAttention.setText(R.string.finish_today);
                        mAttention.setBackgroundResource(R.drawable.points_unlock_shape);
                    }else if(0 == tasks.get(4).get_finishFlag()){
                        mAttention.setBackgroundResource(R.drawable.points_lock_shape);
                    }

                    mBindingaccountPoints.setText(String.valueOf(tasks.get(5).get_gainPoints()));
                    if(1 == tasks.get(5).get_finishFlag()){
                        hasBind = true;
                        mGoBinding.setText(R.string.finish_today);
                        mGoBinding.setBackgroundResource(R.drawable.points_unlock_shape);
                    }else if(0 == tasks.get(5).get_finishFlag()){
                        mGoBinding.setBackgroundResource(R.drawable.points_lock_shape);
                    }
                }
                break;
        }

    }

    @Override
    public void showFailedView() {
        super.showFailedView();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_go_invite:
                if(!hasInvite){
                    InviteFriendsActivity.startActivity(activity);
                }
                break;

            case R.id.tv_share_app:
                // 弹框提示去分享的地方
                DialogManager.showSingleButtonWithTitle(activity,R.string.tips,R.string.share_explanation,R.string.got_it,R.string.warning_earn_points_by_share,true);
                break;

            case R.id.tv_add_portfolio:
                if(!hasAdd){
                    SearchActivity.startActivity(activity);
                }
                break;

            case R.id.tv_go_attention:
                if(!hasAttentionAI){
                    // 去关注AI机器人
                    DialogManager.showSingleButtonWithTitle(activity,R.string.tips,R.string.ai_attention_explanation,R.string.got_it,0,true);
                }
                break;

            case R.id.tv_go_binding:
                if(!hasBind){
                    AccountBindingActivity.startActivity(activity);
                }
                break;

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }
}
