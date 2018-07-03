package com.rjzd.aistock.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.BasicStock;
import com.rjzd.aistock.api.BasicStockList;
import com.rjzd.aistock.api.RangeFlag;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.ui.activity.StockDetailsActivity;
import com.rjzd.aistock.ui.adapters.ExponentCrunchiesAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.commonlib.utils.NetWorkUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;


/**
 * Created by Administrator on 2017/1/12.
 * 涨幅榜
 */

public class RiseCrunchiesFragment extends LazyF10Fragment implements XMBaseAdapter.OnItemClickListener {

    @Bind(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView recycleCrunchies;
    @Bind(R.id.tv_empty)
    TextView tvEmpty;

    private Timer riseTimer;
    private TimerTask refreshRiseTask;
    private ExponentCrunchiesAdapter adapter;

    protected RangeFlag rf;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rf = RangeFlag.RANGE_RISE;
    }

    @Override
    protected int createView() {
        return R.layout.fragment_exponentdetails_crunchies;
    }

    @Override
    protected void initView() {
        //设置布局管理器
        LinearLayoutManager searchStockManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycleCrunchies.setLayoutManager(searchStockManager);
        HeaderView headerVeiw = new HeaderView();
        adapter = new ExponentCrunchiesAdapter(activity);
        adapter.addHeader(headerVeiw);
        adapter.setOnItemClickListener(this);
        recycleCrunchies.setAdapter(adapter);

        // 解决调用Recycleview进行局部刷新时候出现闪烁，其实解决办法就是去掉动画啦
        ((DefaultItemAnimator) recycleCrunchies.getItemAnimator()).setSupportsChangeAnimations(false);

    /*    if (stockCode.equals(Constants.EXPONENT_SH_CODE) || stockCode.equals(Constants.EXPONENT_SZ_CODE) || stockCode.equals(Constants.EXPONENT_CYB_CODE)) {
            recycleCrunchies.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        } else {
            recycleCrunchies.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }

        // 获取数据
        if(NetWorkUtil.isNetworkConnected(activity)){
            startTimer();
            showLoadingDialog();
        }else{
            showFailedView();
        }

    }

    /**
     * 启动计时器  由于Timer的schedule和cancel 方法都只能够被调用一次，所以每次启动都需要新建
     */
    private void startTimer() {
        riseTimer = new Timer();
        refreshRiseTask = new TimerTask() {
            @Override
            public void run() {
                presenter.getComponentStock(stockCode, rf);
            }
        };
        //  启动定时器
        riseTimer.schedule(refreshRiseTask, 0, Constants.PERIOD_REFRESH);
    }

    private void cancelTimer(){
        if(null != riseTimer){
            riseTimer.cancel();
        }
        if(null != refreshRiseTask){
            refreshRiseTask.cancel();
        }
    }

    @Override
    public void setStatistical() {
        statistical = "rise_list";
    }

    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data, dsTag);
        if(dsTag == Constants.DS_TAG_DEFAULT){
            BasicStockList listData = (BasicStockList) data;
            if(StatusCode.OK.getValue() != listData.get_status().getValue()){
                cancelTimer();
            }
            mHasLoadedOnce = true;
            adapter.setData(listData.get_data());
            adapter.notifyItemRangeChanged(0, 10);
        }
    }

    @Override
    public void onItemClick(int position) {
        BasicStock stock = (BasicStock) adapter.getItem(position);
        StockDetailsActivity.startActivity(activity,stock.get_code(),stock.get_name());
    }

    private class HeaderView implements XMBaseAdapter.ItemView {
        @Override
        public View onCreateView(ViewGroup parent) {
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_exponent_crunchies_header,null);
            return headerView;
        }

        @Override
        public void onBindView(View headerView) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
        cancelTimer();
    }
}
