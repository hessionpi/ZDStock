package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rjzd.aistock.R;
import com.rjzd.aistock.api.AIWeekly;
import com.rjzd.aistock.api.AIWeeklyData;
import com.rjzd.aistock.presenter.NotificationPresenter;
import com.rjzd.aistock.ui.adapters.AIWeeklyAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.ui.views.MyItemDecoration;
import com.rjzd.aistock.ui.views.pull2refresh.PullToRefreshView;
import com.rjzd.aistock.view.IFillDataView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * AI 周报
 */
public class AIWeeklyActivity extends BaseActivity implements IFillDataView {

    @Bind(R.id.rv_ai_weekly)
    RecyclerView mAIWeekly;
    @Bind(R.id.refrsh_view)
    PullToRefreshView refrshView;

    private AIWeeklyAdapter adapter;
    NotificationPresenter presenter;
    List<AIWeekly> aiWeekly;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AIWeeklyActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_weekly);
        ButterKnife.bind(this);
        presenter = new NotificationPresenter(this);
        initView();
    }

    private void initView() {

        presenter.getAIWeekly();
        refrshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getAIWeekly();
            }
        });
        MyItemDecoration decoration = new MyItemDecoration(this, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#e6e6e6"));
        mAIWeekly.addItemDecoration(decoration);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAIWeekly.setLayoutManager(layoutmanager);
        adapter = new AIWeeklyAdapter(this);
        mAIWeekly.setAdapter(adapter);
        adapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 周报详情
                String weeklyUrl = aiWeekly.get(position).get_detailUrl();
                WebActivity.startActivity(AIWeeklyActivity.this, weeklyUrl);

            }
        });
    }

    @Override
    public void fillData(Object data, int dsTag) {
        AIWeeklyData aiWeeklyData = (AIWeeklyData) data;
        if (refrshView.isRefreshing()) {
            refrshView.setRefreshing(false);
        }
        if (aiWeeklyData != null) {
            aiWeekly = aiWeeklyData.get_data();
            adapter.setData(aiWeekly);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showFailedView() {
        if (refrshView.isRefreshing()) {
            refrshView.setRefreshing(false);
        }
    }
}
