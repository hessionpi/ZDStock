package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.github.mikephil.charting.charts.LineChart;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.AIOperation;
import com.rjzd.aistock.api.AIOperationList;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.presenter.AIPresenter;
import com.rjzd.aistock.ui.adapters.AIEarningsAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.ui.views.MyItemDecoration;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.utils.DateUtil;
import com.rjzd.aistock.utils.AILineChartUtil;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.LogUtil;
import com.rjzd.commonlib.utils.NetWorkUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

public class AiEarningsActivity extends BaseActivity implements IFillDataView,XMBaseAdapter.OnLoadMoreListener {

    @Bind(R.id.ai_history_linechart)
    LineChart aiHistoryLinechart;
    @Bind(R.id.rv_ai_stock)
    RecyclerView rvAiStock;
    String aiId;
    private AIPresenter aiPresenter;
    private AIEarningsAdapter aiEarningsAdapter;
    private int pageIndex = 0;
    private boolean isLoadFirst = true;

    public static void startActivity(Context context,String aiId) {
        Intent intent = new Intent(context, AiEarningsActivity.class);
        intent.putExtra("ai_id",aiId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_learnings);
        ButterKnife.bind(this);
        aiPresenter = new AIPresenter(this);
        init();
    }

    private void init() {
        aiId = getIntent().getStringExtra("ai_id");
        MyItemDecoration decoration = new MyItemDecoration(this, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#e6e6e6"));
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAiStock.setLayoutManager(layoutmanager);
        rvAiStock.addItemDecoration(decoration);
        aiEarningsAdapter = new AIEarningsAdapter(this);
        rvAiStock.setAdapter(aiEarningsAdapter);

        aiEarningsAdapter.setMore(R.layout.view_recyclerview_more, this);
        aiEarningsAdapter.setNoMore(R.layout.view_recyclerview_nomore);
        aiEarningsAdapter.setError(R.layout.view_recyclerview_error);

        aiEarningsAdapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AIOperation aiOperation = aiEarningsAdapter.getItem(position);
                setLineData(aiOperation);
            }
        });

        loadPAL();
    }

    public void loadPAL(){
        if(NetWorkUtil.isNetworkConnected(this)){
            showLoadingView();
            aiPresenter.getHistoryPALPaging(aiId, pageIndex);
        }else{
            super.showToast(R.string.network_error);
        }
    }

    @Override
    public void fillData(Object data, int dsTag) {
        dismissLoading();
        if(Constants.DS_TAG_AI_HISTORY_PAGING == dsTag){
            AIOperationList paldata = (AIOperationList) data;
            int totalPage = paldata.get_totalPage();
            if(pageIndex < totalPage && StatusCode.OK.getValue() == paldata.get_status().getValue()){
                List<AIOperation> palHistorys = paldata.get_data();
                if ( null != palHistorys && !palHistorys.isEmpty()) {
                    aiEarningsAdapter.addAll(palHistorys);
                    if(isLoadFirst){
                        AIOperation aiOperation = palHistorys.get(0);
                        setLineData(aiOperation);
                        isLoadFirst = false;
                    }
                }
            }else{
                aiEarningsAdapter.stopMore();
            }
        }

    }

    void setLineData(AIOperation aiOperation) {
        List<String> labels = new ArrayList<>();
        Date buyDate = DateUtil.parser(aiOperation.get_buyTime());
        String buyTime = DateUtil.formatDate(buyDate,"yyyy-MM-dd");
        Date sellDate = DateUtil.parser(aiOperation.get_sellTime());
        String sellTime = DateUtil.formatDate(sellDate,"yyyy-MM-dd");
        labels.add(buyTime);
        labels.add(sellTime);

        AILineChartUtil.showLine2(aiHistoryLinechart,aiOperation.get_hs300ProfitPoints(), aiOperation.get_aiProfitPoints(),labels,aiOperation.get_stockName());
        LogUtil.e(labels.toString());
    }

    @Override
    public void showFailedView() {
        dismissLoading();
    }

    @Override
    public void onLoadMore() {
        pageIndex++;
        loadPAL();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aiPresenter.onUnsubscribe();
    }
}
