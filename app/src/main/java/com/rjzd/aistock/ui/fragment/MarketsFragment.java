package com.rjzd.aistock.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.ZdStockApp;
import com.rjzd.aistock.api.KData;
import com.rjzd.aistock.api.Market;
import com.rjzd.aistock.api.MarketData;
import com.rjzd.aistock.api.PlateFlag;
import com.rjzd.aistock.api.Point;
import com.rjzd.aistock.api.RangeFlag;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.presenter.MarketPresenter;
import com.rjzd.aistock.presenter.RealTimePresenter;
import com.rjzd.aistock.ui.activity.PlateListActivity;
import com.rjzd.aistock.ui.activity.PlateStockActivity;
import com.rjzd.aistock.ui.activity.StockDetailsActivity;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.bean.CMinute;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.bean.FenshiDataResponse;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.bean.FenshiParam;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.view.BaseRealTimeView;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.view.RealtimeChartView;
import com.rjzd.commonlib.utils.LogUtil;
import com.rjzd.commonlib.utils.NetWorkUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hition on 2017/5/12.
 */

public class MarketsFragment extends BaseFragment implements BaseRealTimeView,View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.rg_plate)
    RadioGroup mPlateRG;

    @Bind(R.id.tv_title_industry)
    TextView mTitleIndustry;
    @Bind(R.id.tv_title_top5)
    TextView mTitleTop5;
    @Bind(R.id.tv_title_realtime)
    TextView mTitleRealtime;

    @Bind(R.id.bar_chart_markets)
    BarChart marketsBarChart;
    @Bind(R.id.bar_chart_leaders)
    BarChart leaderBarChart;
    @Bind(R.id.realmtime)
    RealtimeChartView mRealtimeView;
    @Bind(R.id.ll_title_industry)
    LinearLayout llTitleIndustry;
    @Bind(R.id.ll_title_top5)
    LinearLayout llTitleTop5;
    @Bind(R.id.ll_title_realtime)
    LinearLayout llTitleRealtime;

    @Bind(R.id.fl_top_stock)
    FrameLayout mTopStockLayout;
    @Bind(R.id.fl_realtime)
    FrameLayout mRealtimeLayout;
    @Bind(R.id.tv_no_data)
    TextView mNoDataView;

    private MarketPresenter presenter;
    private static final int TOP5 = 5;
    private RangeFlag rf;
    private PlateFlag pf;
    private String labelName;
    private String stockName;
    private String code;
    private RealTimePresenter realTimePresenter;

    private int barColor = Color.parseColor("#fca2a2");
    private int clickColor = Color.parseColor("#d63535");
    private XAxis xAxis;
    private XAxis leadersXAxis;
    private Handler handler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        ButterKnife.bind(this, view);
        initView();

        presenter = new MarketPresenter(this);
        realTimePresenter = new RealTimePresenter(this);
        handler = new Handler();
        getMarketsData();
        return view;
    }

    private void initView() {
        rf = RangeFlag.RANGE_RISE;
        pf = PlateFlag.INDUSTRY;

        mPlateRG.setOnCheckedChangeListener(this);
        llTitleIndustry.setOnClickListener(this);
        llTitleTop5.setOnClickListener(this);
        llTitleRealtime.setOnClickListener(this);
        //  获取缓存数据
        FenshiDataResponse response = (FenshiDataResponse) mCache.getAsObject("realtime");
        if (null != response) {
            mRealtimeView.setDataAndInvalidate(response);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            getMarketsData();
        }
    }

    private void initMarketsBarChart() {
        marketsBarChart.setDrawBarShadow(false);
        marketsBarChart.setDrawValueAboveBar(true);
        marketsBarChart.getDescription().setEnabled(false);

        // if more than 10 entries are displayed in the chart, no values will be
        // drawn
        marketsBarChart.setMaxVisibleValueCount(10);

        // scaling can now only be done on x- and y-axis separately
        marketsBarChart.setPinchZoom(false);
        marketsBarChart.setScaleEnabled(false);

        marketsBarChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        xAxis = marketsBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineColor(Color.parseColor("#e6e6e6"));
        xAxis.setAxisLineWidth(1);

        YAxis leftAxis = marketsBarChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setAxisMinimum(0.01f);

        YAxis rightAxis = marketsBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false);

        marketsBarChart.getAxisCenter().setDrawAxisLine(false);
        // add a nice and smooth animation
        marketsBarChart.animateY(500);
        marketsBarChart.getLegend().setEnabled(false);

        marketsBarChart.setOnChartValueSelectedListener(new OnMarketBarValueSelectListener());
    }

    private void initLeadersBarChart() {
        leaderBarChart.setDrawBarShadow(false);
        leaderBarChart.setDrawValueAboveBar(true);

        leaderBarChart.getDescription().setEnabled(false);

        // if more than 10 entries are displayed in the chart, no values will be
        // drawn
        leaderBarChart.setMaxVisibleValueCount(10);

        // scaling can now only be done on x- and y-axis separately
        leaderBarChart.setPinchZoom(false);
        leaderBarChart.setScaleEnabled(false);

        leaderBarChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        leadersXAxis = leaderBarChart.getXAxis();
        leadersXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        leadersXAxis.setDrawGridLines(false);
        leadersXAxis.setAxisLineColor(Color.parseColor("#e6e6e6"));
        leadersXAxis.setAxisLineWidth(1);

        YAxis leftAxis = leaderBarChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setAxisMinimum(0.01f);

        YAxis rightAxis = leaderBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false);

        leaderBarChart.getAxisCenter().setDrawAxisLine(false);
        // add a nice and smooth animation
        leaderBarChart.animateY(500);
        leaderBarChart.getLegend().setEnabled(false);
    }

    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data, dsTag);
        if (data instanceof MarketData) {
            final MarketData mData = (MarketData) data;
            if (StatusCode.OK.getValue() == mData.get_status().getValue()) {
                List<Market> markdatas = mData.get_data();
                if (!markdatas.isEmpty()) {
                    if (Constants.DS_TAG_MARKET == dsTag) {
                        marketsBarChart.clear();
                        fillMarketBarView(markdatas);
                    } else if (Constants.DS_TAG_LEADERSTOCK == dsTag) {
                        leaderBarChart.clear();
                        fillLeadersBarView(markdatas);
                    }

                }
            }
        } else if (Constants.DS_TAG_DEFAULT == dsTag) {
            KData kData = (KData) data;
            List<Point> points = ((KData) data).get_data();
            if (kData != null) {
                FenshiDataResponse fdata = new FenshiDataResponse();

                if (kData.get_status().getValue() == StatusCode.WAITING_FOR_PRICE.getValue() | kData.get_status().getValue() == StatusCode.WAITING_FOR_OPEN.getValue()) {
                    mRealtimeView.setDataAndInvalidate(null);
                    cancelStockViewTimer();
                    return ;
                }

                String start =  kData.get_startTime();
                String end = kData.get_endTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                     Date startDate = sdf.parse(start);
                    Date endDate = sdf.parse(end);
                    long startTime =startDate.getTime()/1000;
                    long endTime = endDate.getTime() / 1000;
                    long openTime = startTime;
                    List<CMinute> cMinuteList = new ArrayList<>();
                    long leadTime = 60;
                    if (null != points && !points.isEmpty()) {
                       // long leadTime = timeQuantum / points.size();
                        for (Point p : points) {
                            CMinute cMinute = new CMinute();
                            cMinute.setPrice(p.get_price());
                            cMinute.setRate(p.get_range());
                            cMinute.setAverage(p.get_average());
                            cMinute.setCount(p.get_volume());
                            cMinute.setTime(openTime);
                            cMinute.setMoney(p.get_price() * p.get_volume());
                            openTime += leadTime;
                            cMinuteList.add(cMinute);
                        }
                        FenshiParam param = new FenshiParam();
                        param.setDuration("9:30-11:30|13:00-15:00");
                        param.setUntil(endTime);
                      //  double count = ((14400.0 / timeQuantum) * points.size());
                        param.setLength(242);
                        param.setLast(points.get(0).get_close());
                        fdata.setParam(param);
                        fdata.setData(cMinuteList);
                        fdata.setMsg(kData.get_msg());
                        mRealtimeView.setDataAndInvalidate(fdata);
                        if (kData.get_status().getValue() == StatusCode.NON_TRADE_TIME.getValue()) {
                            cancelStockViewTimer();
                        }
                    }
                    LogUtil.e(kData.get_data().size() + "+++++++++++++++++++++++++++");
                } catch (ParseException e) {
                    LogUtil.e(e.getMessage());
                }
            }
        } else {
            if (null == data) {
                showFailedView();
            }
        }

    }

    private void fillLeadersBarView(final List<Market> leaderStocks) {
        // init the mpchart of leaders barchart
        initLeadersBarChart();
        final List<String> chartLabels = new LinkedList<>();
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < leaderStocks.size(); i++) {
            double range = leaderStocks.get(i).get_rangePercentage();
            if (rf.getValue() == RangeFlag.RANGE_RISE.getValue() && range >= 0 || rf.getValue() == RangeFlag.RANGE_FALL.getValue() && range < 0) {
                chartLabels.add(leaderStocks.get(i).get_stockName());
                yVals1.add(new BarEntry(i, (float) Math.abs(range)));
            }
        }

        leadersXAxis.setLabelCount(chartLabels.size());
        leadersXAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value > chartLabels.size() - 1) {
                    return "";
                }
                return chartLabels.get((int) value);
            }
        });

        BarDataSet set1;
        if (leaderBarChart.getData() != null &&
                leaderBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) leaderBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            leaderBarChart.animateY(1000);
            leaderBarChart.invalidate();
        } else {
            set1 = new BarDataSet(yVals1, "markets");
            set1.setHighLightColor(clickColor);
            set1.setHighLightAlpha(255);
            set1.setColors(barColor);
            set1.setValueTextColor(barColor);
            set1.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    if (rf.getValue() == RangeFlag.RANGE_FALL.getValue()) {
                        return "-" + String.format("%.2f", value).toString() + "%";
                    }
                    return String.format("%.2f", value).toString() + "%";
                }

                @Override
                public Bitmap getFormattedBitmap(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return null;
                }

                @Override
                public Bitmap getFormattedLine(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return null;
                }
            });
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData barData = new BarData(dataSets);
            barData.setValueTextSize(12f);
            barData.setBarWidth(0.5f);

            leaderBarChart.setData(barData);
        }

        leaderBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int position = (int) e.getX();
                Market mData = leaderStocks.get(position);

                stockName = mData.get_stockName();
                mTitleRealtime.setText(stockName + "分时图");
                code = mData.get_stockCode();
                getRealTimeData(code);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        leaderBarChart.highlightValue(0, 0);
    }

    private void fillMarketBarView(List<Market> markdatas) {
        // init the mpchart of markets barchart
        initMarketsBarChart();
        final List<String> chartLabels = new LinkedList<>();
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < markdatas.size(); i++) {
            double range = markdatas.get(i).get_rangePercentage();
            if (rf.getValue() == RangeFlag.RANGE_RISE.getValue() && range >= 0 || rf.getValue() == RangeFlag.RANGE_FALL.getValue() && range < 0) {
                chartLabels.add(markdatas.get(i).get_plateName());
                yVals1.add(new BarEntry(i, (float) Math.abs(range)));
            }
        }

        xAxis.setLabelCount(chartLabels.size());
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value > chartLabels.size() - 1) {
                    return "";
                }
                return chartLabels.get((int) value);
            }
        });

        BarDataSet set1;
        if (marketsBarChart.getData() != null &&
                marketsBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) marketsBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            marketsBarChart.animateY(1000);
            marketsBarChart.invalidate();
        } else {
            set1 = new BarDataSet(yVals1, "markets");
            set1.setHighLightColor(clickColor);
            set1.setHighLightAlpha(255);
            set1.setColors(barColor);
            set1.setValueTextColor(barColor);
            set1.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    if (rf.getValue() == RangeFlag.RANGE_FALL.getValue()) {
                        return "-" + String.format("%.2f", value).toString() + "%";
                    }
                    return String.format("%.2f", value).toString() + "%";
                }

                @Override
                public Bitmap getFormattedBitmap(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return null;
                }

                @Override
                public Bitmap getFormattedLine(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return null;
                }
            });
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData barData = new BarData(dataSets);
            barData.setValueTextSize(12f);
            barData.setBarWidth(0.5f);

            marketsBarChart.setData(barData);
        }

        // 设置标题文字显示
        String plate = "";
        if (pf.getValue() == PlateFlag.INDUSTRY.getValue()) {
            plate = activity.getString(R.string.industry_plate);
        } else if (pf.getValue() == PlateFlag.CONCEPT.getValue()) {
            plate = activity.getString(R.string.concept_plate);
        } else if (pf.getValue() == PlateFlag.REGION.getValue()) {
            plate = activity.getString(R.string.region);
        }

        String mktTitle = "";
        if (rf.getValue() == RangeFlag.RANGE_RISE.getValue()) {
            mktTitle = labelName + "涨幅榜";
            plate = plate + "涨幅榜";
        } else if (rf.getValue() == RangeFlag.RANGE_FALL.getValue()) {
            mktTitle = labelName + "跌幅榜";
            plate = plate + "跌幅榜";
        }
        mTitleIndustry.setText(plate);
        mTitleTop5.setText(mktTitle);

        if(!chartLabels.isEmpty()){
            mTopStockLayout.setVisibility(View.VISIBLE);
            mRealtimeLayout.setVisibility(View.VISIBLE);
            mNoDataView.setVisibility(View.GONE);

            marketsBarChart.highlightValue(0, 0);
        }else{
            leaderBarChart.clear();
            fillLeadersBarView(markdatas);
            // 隐藏大盘top5 和 分时图
            mTopStockLayout.setVisibility(View.GONE);
            mRealtimeLayout.setVisibility(View.GONE);
            mNoDataView.setVisibility(View.VISIBLE);
            if(rf.getValue() == RangeFlag.RANGE_RISE.getValue()){
                mNoDataView.setText(R.string.no_rise);
            }else {
                mNoDataView.setText(R.string.no_fall);
            }
            mNoDataView.setTextColor(barColor);
        }


    }

    @Override
    public void showFailedView() {
        super.showFailedView();
    }

    @Override
    public void setStatistical() {
        statistical = "行情";
    }

    /**
     * 请求数据源
     */
    private void getMarketsData() {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            showLoadingDialog();
            presenter.getMarketData(rf, pf, 0, TOP5);
        }else{
            activity.showToast(R.string.no_network);
        }
    }

    private void getTop5Stocks() {
        handler.postDelayed(new Runnable() {
            public void run() {
                //execute the task
                presenter.getLeaderStocks(rf, pf, labelName, 0, TOP5);
            }
        }, 500);
    }

    private void getRealTimeData(final String code) {
        handler.postDelayed(new Runnable() {
            public void run() {
                realTimePresenter.getRealmTime(code, "9:30");
            }
        }, 500);
    }

    @Override
    public void fillDataAndInvalidate(FenshiDataResponse d) {
        mRealtimeView.setDataAndInvalidate(d);
        mCache.put(code, d);
    }

    @Override
    public void cancelStockViewTimer() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_industry:
                PlateListActivity.startActivity(activity, rf, pf);
                break;

            case R.id.ll_title_top5:
                PlateStockActivity.startActivity(activity, rf, pf, labelName);
                break;

            case R.id.ll_title_realtime:
                // 跳转到个股详情页面
                if (!TextUtils.isEmpty(code) && !TextUtils.isEmpty(stockName)) {
                    StockDetailsActivity.startActivity(activity, code, stockName);
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_industry:
                pf = PlateFlag.INDUSTRY;
                break;

            case R.id.rb_concept:
                pf = PlateFlag.CONCEPT;
                break;

            case R.id.rb_area:
                pf = PlateFlag.REGION;
                break;
        }
        getMarketsData();
    }

    /**
     * 涨幅/跌幅
     *
     * @param barcolor 柱子颜色
     * @param highlightColor 高亮颜色
     * @param flag  涨跌标识
     */
    public void switchRiseOrFall(int barcolor, int highlightColor, RangeFlag flag) {
        this.barColor = barcolor;
        this.rf = flag;
        this.clickColor = highlightColor;
        getMarketsData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 行情柱子点击响应事件
     */
    private class OnMarketBarValueSelectListener implements OnChartValueSelectedListener {

        @Override
        public void onValueSelected(Entry e, Highlight h) {
            labelName = marketsBarChart.getXAxis().getFormattedLabel((int) e.getX());
            mTitleTop5.setText(labelName + "龙头股");
            getTop5Stocks();
        }

        @Override
        public void onNothingSelected() {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
        cancelStockViewTimer();
    }

    private String getFileDir() {
        return ZdStockApp.getAppContext().getFilesDir().toString() + code;
    }
}
