package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.Invite;
import com.rjzd.aistock.api.InviteData;
import com.rjzd.aistock.bean.LoginBean;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.InviteFriendPresenter;
import com.rjzd.aistock.ui.adapters.InivteFriendAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.ui.views.MyItemDecoration;
import com.rjzd.commonlib.utils.LogUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class InviteFriendsActivity extends ShareActivity implements View.OnClickListener, XMBaseAdapter.OnLoadMoreListener {
    @Bind(R.id.rl_invite_friend)
    RecyclerView rlInviteFriend;

    private InivteFriendAdapter inviteFriendAdapter;
    private InviteFriendPresenter presenter;
    int page;
    int count = 10;
    int totalPage;
    @Bind(R.id.tv_empty)
    TextView tvEmpty;
    @Bind(R.id.bt_invite)
    Button btInvite;
    LoginBean loginBean;
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, InviteFriendsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        ButterKnife.bind(this);
        presenter = new InviteFriendPresenter(this);
       loginBean= UserInfoCenter.getInstance().getLoginModel();
        initView();
        isNeedAddPoints = false;
    }

    private void initView() {
        page=0;
        getData(page);
        btInvite.setOnClickListener(this);
        MyItemDecoration decoration = new MyItemDecoration(this, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#e6e6e6"));
        LinearLayoutManager layoutmanager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rlInviteFriend.setLayoutManager(layoutmanager1);
        rlInviteFriend.addItemDecoration(decoration);
        inviteFriendAdapter = new InivteFriendAdapter(this);
        rlInviteFriend.setAdapter(inviteFriendAdapter);
        inviteFriendAdapter.setMore(R.layout.view_recyclerview_more, this);
        inviteFriendAdapter.setNoMore(R.layout.view_recyclerview_nomore);
        inviteFriendAdapter.setError(R.layout.view_recyclerview_error);

    }


    void getData(int page) {
        presenter.getMyInvite(loginBean.getUserid(), page, count);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_invite:
                openShareBoard(Constants.URL_INVITE_FRIEND+loginBean.getInviteCode(),"邀请你领养最顶级的AI炒股机器人，每日免费AI预测自选股走势!",null);
                break;
        }
    }

    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data,dsTag);
        InviteData invite = (InviteData) data;
        List<Invite> invites = invite.get_data();

        if (invite.get_totalInvite()==0) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            totalPage= invite.get_totalPage();
            if (totalPage<page+1){
                invites=null;
            }
            inviteFriendAdapter.addAll(invites);
            LogUtil.e(invites.toString());
            inviteFriendAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onLoadMore() {
        page++;
        getData(page);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }
}
