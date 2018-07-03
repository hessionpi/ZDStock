package com.rjzd.aistock.ui.views.stockcharts.stockchart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjzd.aistock.R;
import com.rjzd.aistock.api.KType;
import com.rjzd.aistock.presenter.KLinePresenter;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.view.CrossView;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.view.KLineView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/7.
 */

public class FifteenMinuteKLineFragment extends LineBaseFragment {

    @Bind(R.id.ckf_klineview)
    KLineView kLineView;
    @Bind(R.id.cff_cross)
    CrossView crossView;
    @Bind(R.id.cff_msg)
    TextView msgText;
    private KLinePresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chart_kline_frag, null);
        ButterKnife.bind(this, view);
        presenter = new KLinePresenter(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        kLineView.setUsedViews(crossView, msgText);
        kLineView.setType(1);
        kLineView.setOnDoubleTapListener(this);
        indexTab.setOnTabSelectedListener(this);
        for(String s : INDEX_KLINE_TAB) {
            indexTab.addTab(indexTab.newTab().setText(s));
        }
        presenter.getKData(stockCode, KType.M15);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case INDEX_VOL:
                kLineView.showVOL();
                break;
         /*   case INDEX_ZJ:
                kLineView.showZJ();
                break;*/
            case INDEX_MACD:
                kLineView.showMACD();
                break;
            case INDEX_KDJ:
                kLineView.showKDJ();
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }

    @Override
    public void setStatistical() {
        statistical = "k_line_fifteen_min";
    }
}

