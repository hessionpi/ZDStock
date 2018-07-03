package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.DateTransfer;
import com.rjzd.aistock.api.DateTransferList;
import com.rjzd.aistock.presenter.AIPresenter;
import com.rjzd.aistock.ui.adapters.AIAdjustdynamicAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.NetWorkUtil;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 调仓历史
 */
public class AiAdjustdynamicActivity extends BaseActivity implements IFillDataView ,XMBaseAdapter.OnLoadMoreListener{

    @Bind(R.id.rv_ai_adjustdynamic)
    RecyclerView rvAiAdjustdynamic;

    private int page = 0;
    private static final int COUNT_PER_PAGE = 10;
    private String aiId;
    private AIPresenter aiPresenter;
    private AIAdjustdynamicAdapter aiAdjustdynamicAdapter;


    public static void startActivity(Context context,String aiId) {
        Intent intent = new Intent(context, AiAdjustdynamicActivity.class);
        intent.putExtra("ai_id",aiId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_adjustdynamic);
        ButterKnife.bind(this);
        aiPresenter = new AIPresenter(this);
        init();
    }

    private void init() {
        aiId = getIntent().getStringExtra("ai_id");
        LinearLayoutManager layoutmanager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAiAdjustdynamic.setNestedScrollingEnabled(false);
        rvAiAdjustdynamic.setLayoutManager(layoutmanager2);
        aiAdjustdynamicAdapter = new AIAdjustdynamicAdapter(this);
        rvAiAdjustdynamic.setAdapter(aiAdjustdynamicAdapter);

        aiAdjustdynamicAdapter.setMore(R.layout.view_recyclerview_more, this);
        aiAdjustdynamicAdapter.setNoMore(R.layout.view_recyclerview_nomore);
        aiAdjustdynamicAdapter.setError(R.layout.view_recyclerview_error);
        loadData();
    }

    public void loadData(){
        if(NetWorkUtil.isNetworkConnected(this)){
            showLoadingView();
            aiPresenter.getAIActions(aiId, page, COUNT_PER_PAGE);
        }else{
            super.showToast(R.string.network_error);
        }
    }

    @Override
    public void fillData(Object data, int dsTag) {
        dismissLoading();
        if(Constants.DS_TAG_AI_ACTION == dsTag){
            DateTransferList dateTransferList= (DateTransferList) data;
            int totalPage = dateTransferList.get_totalPage();
            if(page < totalPage){
                List<DateTransfer> dateTransfers= dateTransferList.get_data();
                if(null != dateTransfers && !dateTransfers.isEmpty()){
                    aiAdjustdynamicAdapter.addAll(dateTransfers);
                }
            }else{
                aiAdjustdynamicAdapter.stopMore();
            }
        }
    }

    @Override
    public void onLoadMore() {
        page++;
        loadData();
    }

    @Override
    public void showFailedView() {
        dismissLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aiPresenter.onUnsubscribe();
    }

}
