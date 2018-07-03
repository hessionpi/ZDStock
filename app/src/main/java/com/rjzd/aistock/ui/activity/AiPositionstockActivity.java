package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.AIOperation;
import com.rjzd.aistock.api.AIOperationList;
import com.rjzd.aistock.presenter.AIPresenter;
import com.rjzd.aistock.ui.adapters.AIPositionstockAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.ui.views.MyItemDecoration;
import com.rjzd.aistock.view.IFillDataView;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

public class AiPositionstockActivity extends BaseActivity implements IFillDataView {

    @Bind(R.id.rv_positionstock)
    RecyclerView rvPositionstock;

    private AIPresenter aiPresenter;
    private AIPositionstockAdapter aiPositionstockAdapter;

    public static void startActivity(Context context,String aiId) {
        Intent intent = new Intent(context, AiPositionstockActivity.class);
        intent.putExtra("ai_id",aiId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_positionstock);
        ButterKnife.bind(this);
        aiPresenter = new AIPresenter(this);
        init();
    }

    private void init() {
        String aiId = getIntent().getStringExtra("ai_id");
        MyItemDecoration decoration = new MyItemDecoration(this, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#e6e6e6"));
        LinearLayoutManager layoutmanager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPositionstock.setNestedScrollingEnabled(false);
        rvPositionstock.setLayoutManager(layoutmanager1);
        rvPositionstock.addItemDecoration(decoration);
        aiPositionstockAdapter = new AIPositionstockAdapter(this);
        rvPositionstock.setAdapter(aiPositionstockAdapter);
        aiPresenter.getHoldStocks(aiId,20);

        aiPositionstockAdapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AIOperation mOperation = aiPositionstockAdapter.getItem(position);
                StockDetailsActivity.startActivity(AiPositionstockActivity.this,mOperation.get_stockCode(),mOperation.get_stockName());
            }
        });
    }

    @Override
    public void fillData(Object data, int dsTag) {
        AIOperationList aiOperationList = (AIOperationList) data;
        List<AIOperation> stockPositions = aiOperationList.get_data();
       // tvCount.setText("持仓股票（" + stockPositions.size() + "）");
        aiPositionstockAdapter.setData(stockPositions);
        aiPositionstockAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFailedView() {

    }
}
