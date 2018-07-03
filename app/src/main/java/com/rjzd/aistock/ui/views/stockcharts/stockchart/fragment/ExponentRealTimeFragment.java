package com.rjzd.aistock.ui.views.stockcharts.stockchart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.CodeType;
import com.rjzd.aistock.cache.ACache;
import com.rjzd.aistock.presenter.RealTimePresenter;
import com.rjzd.aistock.ui.activity.BaseActivity;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.bean.FenshiDataResponse;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.view.BaseRealTimeView;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.view.CrossView;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.view.FenshiView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/24.
 */


/**
 * Created by Administrator on 2016/10/25.
 */
public class ExponentRealTimeFragment extends LineBaseFragment implements BaseRealTimeView {
    public static final int REFRUSH_TIME = 5000;
    @Bind(R.id.cff_msg)
    TextView msgText;
    @Bind(R.id.cff_fenshiview)
    FenshiView fenshiView;
    @Bind(R.id.cff_cross)
    CrossView crossView;
    @Bind(R.id.cfb_index_tab)
    TabLayout cfbIndexTab;

    private Timer timer;
    private TimerTask refreshFenshiTask;
    BaseActivity activity;
    //是否全屏
    // private boolean isPause;
    public ACache mCache;
    private RealTimePresenter presenter;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chart_fenshi_frag, null);
        ButterKnife.bind(this, view);
        presenter = new RealTimePresenter(this);
        mCache=ACache.get(getActivity(),"realtime");
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fenshiView.setUsedViews(crossView, msgText);
        indexTab.setOnTabSelectedListener(this);
        for (String s : INDEX_FENSHI_TAB) {
            indexTab.addTab(indexTab.newTab().setText(s));
        }
        activity= (BaseActivity)getActivity();
        //  获取缓存数据
        FenshiDataResponse response = (FenshiDataResponse) mCache.getAsObject("realtime");
        if(null != response ){
            fenshiView.setDataAndInvalidate(response,false);
        }
        startTimer();


    }

    @Override
    public void onResume() {
        super.onResume();
        //isPause = false;

    }

    @Override
    public void onPause() {
        super.onPause();
        // isPause = true;
    }

    @Override
    public void setStatistical() {
        statistical = "real_time_exponent";
    }

    private void addListener() {
        fenshiView.setOnDoubleTapListener(this);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    /**
     * 启动计时器
     */
    private void startTimer() {
        timer = new Timer();
        //  刷新自选股定时器
        refreshFenshiTask = new TimerTask() {
            @Override
            public void run() {
//                new FenshiLoadKData().execute(Constants.stackcode);
                presenter.getRealmTime(stockCode,"9:30");
            }
        };


        //  启动定时器
        timer.schedule(refreshFenshiTask, 0, Constants.REALTIME_PERIOD_REFRESH);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case INDEX_VOL:
                fenshiView.showVOL();
                break;
       /*     case INDEX_ZJ:
                //TODO 显示指标资金
                fenshiView.showZJ();
                break;*/
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void fillDataAndInvalidate(FenshiDataResponse d) {
        fenshiView.setDataAndInvalidate(d,false);
        mCache.put(stockCode, d);
    }

    @Override
    public void cancelStockViewTimer() {
        if (timer != null) {
            timer.cancel();
        }
        if (refreshFenshiTask != null) {
            refreshFenshiTask.cancel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
        cancelStockViewTimer();
    }
}
