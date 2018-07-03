package com.rjzd.aistock.ui.views.stockcharts.stockchart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import com.rjzd.aistock.R;
import com.rjzd.aistock.ui.fragment.BaseFragment;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.view.ChartConstant;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.view.ChartView;


/**
 * Created by Arvin on 2016/10/26.
 * 分时frag和k线frag的父布局
 *  TabLayout.OnTabSelectedListener,
 */
public abstract class LineBaseFragment extends BaseFragment implements ChartConstant, ChartView.OnDoubleTapListener,TabLayout.OnTabSelectedListener {

    protected boolean isShow;
    //K线类型：取值为ChartConstant的TYPE_RIK等,日k月k周k等,默认为0分时图
    protected int type;
    protected String stockCode;
    protected String stockName;
    protected TabLayout indexTab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if (null != bundle) {
            stockCode = bundle.getString("stock_code");
            stockName = bundle.getString("stock_name");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        indexTab = (TabLayout)getView().findViewById(R.id.cfb_index_tab);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isShow = isVisibleToUser;
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onDoubleTap() {

    }
}
