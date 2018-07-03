package com.rjzd.aistock.ui.views.stockcharts.stockchart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjzd.aistock.R;
import com.rjzd.aistock.api.KData;
import com.rjzd.aistock.api.KType;
import com.rjzd.aistock.api.Point;
import com.rjzd.aistock.presenter.KLinePresenter;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.bean.StickData;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.view.CrossView;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.view.KLineView;
import com.rjzd.commonlib.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/7.
 */

public class MonthKLineFragment extends LineBaseFragment {
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
        showLoadingDialog();
        kLineView.setUsedViews(crossView, msgText);
        kLineView.setType(1);
        kLineView.setOnDoubleTapListener(this);
        indexTab.setOnTabSelectedListener(this);
        for (String s : INDEX_KLINE_TAB) {
            indexTab.addTab(indexTab.newTab().setText(s));
        }
        presenter.getKData(stockCode, KType.MONTH);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case INDEX_VOL:
                kLineView.showVOL();
                break;
     /*       case INDEX_ZJ:
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setStatistical() {
        statistical = "k_line_month";
    }

    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data, dsTag);

        KData kData = (KData) data;
        if (kData != null) {
            ArrayList<StickData> stickDatas = new ArrayList<>();
            List<Point> points = kData.get_data();
            if (null != points && !points.isEmpty()) {
                LogUtil.e("共有：" + points.size());
                for (Point p : points) {
                    StickData stickData=new StickData();
                    stickData.setOpen(p.get_open());
                    stickData.setClose(p.get_close());
                    stickData.setHigh(p.get_high());
                    stickData.setLow(p.get_low());
                    stickData.setCount(p.get_volume());
                    stickData.setRate(p.get_range()) ;
                    stickData.setTime(p.get_time());
                    stickDatas.add(stickData);
                }
                kLineView.setDataAndInvalidate(stickDatas);
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }
}
