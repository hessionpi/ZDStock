package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.DailyStockRecommendation;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.api.StockRecommendationData;
import com.rjzd.aistock.ui.adapters.DailyBestStockAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.ui.views.MyItemDecoration;
import com.rjzd.aistock.ui.views.pull2refresh.PullToRefreshView;
import com.rjzd.commonlib.utils.LogUtil;
import com.rjzd.commonlib.utils.NetWorkUtil;
import java.util.List;

/**
 * 每日金股
 *
 * Created by Hition on 2017/8/4.
 */

public class DailyBestStockActivity extends BlurActivity implements XMBaseAdapter.OnLoadMoreListener{

    private DailyBestStockAdapter mAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DailyBestStockActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recommends.add(Constants.PRIVILEGE_NIUGU);
        recommendDialogTitle = "解锁每日金股";
    }

    @Override
    protected void initView() {
        mToolbar.setTitle(R.string.daily_best_stock);

        MyItemDecoration decoration = new MyItemDecoration(this, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#e6e6e6"));
        mRecommendView.addItemDecoration(decoration);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecommendView.setLayoutManager(layoutmanager);
        mAdapter = new DailyBestStockAdapter(this);
        mAdapter.setMore(R.layout.view_recyclerview_more, this);
        mAdapter.setNoMore(R.layout.view_recyclerview_nomore);
        mAdapter.setError(R.layout.view_recyclerview_error);
        mRecommendView.setAdapter(mAdapter);

        mRefresh.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isUnlock){
                    pageIndext = 0;
                    getStockRecommendation(pageIndext);
                }
            }
        });

        mUnlockBtn.setOnClickListener(this);
        mBlurLayout.setOnClickListener(this);
    }

    @Override
    protected void delayGetRecommends() {
        getStockRecommendation(pageIndext);
    }

    /**
     * 获取推荐金股
     */
    private void getStockRecommendation(int page) {
        if(NetWorkUtil.isNetworkConnected(this)){
            mRefresh.setVisibility(View.VISIBLE);
            showLoadingView();
            presenter.getStockRecommendation(page,PAGE_SIZE);
        }else{
            mRefresh.setVisibility(View.GONE);
            mBlurLayout.setVisibility(View.GONE);
            super.showToast(R.string.network_error);
        }
    }

    @Override
    public void onLoadMore() {
        pageIndext++;
        getStockRecommendation(pageIndext);
    }

    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data, dsTag);
        switch(dsTag){
            case Constants.DS_TAG_RECOMMEND_STOCK:
                StockRecommendationData recomStock = (StockRecommendationData) data;
                int totalPage = recomStock.get_totalPage();
                if(pageIndext<totalPage && StatusCode.OK.getValue() == recomStock.get_status().getValue()){
                    List<DailyStockRecommendation> dailyStocks = recomStock.get_dailyRecommendList();
                    if(0 == pageIndext){
                        mAdapter.clear();
                    }
                    mAdapter.addAll(dailyStocks);
                }else{
                    mAdapter.stopMore();
                }
                if(isUnlock){
                    mBlurLayout.setVisibility(View.GONE);
                    return ;
                }
                mBlurLayout.setVisibility(View.VISIBLE);

                // 未解锁时候需要解锁
                if(!hasLoad){
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            applyBlur(mBlurLayout);
                        }
                    },500);
                    hasLoad = true;
                }
                break;
        }
    }

}
