package com.rjzd.aistock.ui.fragment;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.support.v4.content.ContextCompat;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.Funds;
import com.rjzd.aistock.api.FundsData;
import com.rjzd.aistock.api.MarginTrading;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.commonlib.utils.NetWorkUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 资金
 * <p>
 * Created by Administrator on 2017/1/11.
 */

public class FundFragment extends LazyF10Fragment {

    PieChart pieChart;
    TextView zhuliin;
    TextView zhuliout;
    TextView sanhuNetIn;
    LineChart lineChart;

    @Bind(R.id.no_data)
    ViewStub noData;
    @Bind(R.id.fund_flow_layout)
    ViewStub fundFlowLayout;
    @Bind(R.id.margin_layout)
    ViewStub marginLayout;

    private LinearLayout mMarginLayout;
    private LinearLayout mFundFlowLayout;

    private boolean isFlowEmpty;
    private boolean isMarginEmpty;

    @Override
    protected int createView() {
        return R.layout.fragment_fund;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }


        if (NetWorkUtil.isNetworkConnected(activity)) {
            // 获取数据
            presenter.getFunds(stockCode);
            //  获取融资融券差额数据
            presenter.getMarginTrading(stockCode);
            showLoadingDialog();
        } else {
            activity.showToast(R.string.no_network);
        }

    }


    @Override
    public void setStatistical() {
        statistical = "fund";
    }


    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data, dsTag);
        switch (dsTag) {
            case Constants.DS_TAG_CAPITAL_FLOWS:
                FundsData fd = (FundsData) data;
                if (StatusCode.OK.getValue() == fd.get_status().getValue()) {
                    mHasLoadedOnce = true;
                    if (fundFlowLayout.getParent() != null) {
                        mFundFlowLayout = (LinearLayout) fundFlowLayout.inflate();
                    }
                    if (null != fundFlowLayout) {
                        pieChart = ButterKnife.findById(mFundFlowLayout, R.id.pie_chart);
                        zhuliin = ButterKnife.findById(mFundFlowLayout, R.id.zhuliin);
                        zhuliout = ButterKnife.findById(mFundFlowLayout, R.id.zhuliout);
                        sanhuNetIn = ButterKnife.findById(mFundFlowLayout, R.id.sanhuNetIn);
                    }
                    Funds fdata = fd.get_funds();
                    setFundFlow(fdata);
                } else {
                    isFlowEmpty = true;
                }
                break;

            case Constants.DS_TAG_MARGINTRADING:
                MarginTrading mt = (MarginTrading) data;
                // LogUtil.i(mt.toString());
                if (StatusCode.OK.getValue() == mt.get_status().getValue()) {
                    if (marginLayout.getParent() != null) {
                        mMarginLayout = (LinearLayout) marginLayout.inflate();
                    }
                    if (null != mMarginLayout) {
                        lineChart = ButterKnife.findById(mMarginLayout, R.id.margin_linechart);
                    }
                    setMarginTrading(mt);
                } else {
                    isMarginEmpty = true;
                }
                break;

            default:
                if (null == data) {
                    showFailedView();
                }
                break;
        }

        if (isFlowEmpty && isMarginEmpty) {
            noData.inflate();
        }

    }

    private void setMarginTrading(MarginTrading mt) {
        List<Double> lists = mt.get_points();
        if (null != lists && !lists.isEmpty()) {
            final List<String> texts = new ArrayList<>();
            texts.add(mt.get_startTime());
            texts.add(mt.get_endTime());
            showMarginLinechart(texts, lists);
        }

    }

    @Override
    public void showFailedView() {
        super.showFailedView();
    }

    //融资融券
    private void showMarginLinechart(final List<String> texts, final List<Double> list) {
        lineChart.setDrawBorders(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        XAxis xAxis = lineChart.getXAxis();   //x轴
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setLabelCount(2, true);
        xAxis.setTextSize(9);
        xAxis.setYOffset(5);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setTextColor(ContextCompat.getColor(activity, R.color.cl_888888));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int) value == 0)
                    return texts.get(0);
                else if ((int) value == list.size() - 1) return texts.get(1);
                else return "";
            }
        });
        YAxis centerAxis = lineChart.getAxisCenter();
        centerAxis.setEnabled(false);
        YAxis leftAxis = lineChart.getAxisLeft();//y轴
        leftAxis.setEnabled(false);
        YAxis rightAxis = lineChart.getAxisRight();
        DashPathEffect effect = new DashPathEffect(new float[]{1, 2, 4, 8}, 1);
        rightAxis.setGridDashedLine(effect);
        rightAxis.setGridColor(ContextCompat.getColor(activity, R.color.cl_888888));
        rightAxis.setAxisMinimum(0);
        rightAxis.setLabelCount(3, false);
        rightAxis.setXOffset(10);
        rightAxis.setTextColor(ContextCompat.getColor(activity, R.color.cl_f1a83a));
        Legend mlegend = lineChart.getLegend();
        mlegend.setEnabled(false);
        ArrayList<Entry> entries1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entries1.add(new Entry(i, (float) (list.get(i).doubleValue())));
        }
        LineDataSet set = new LineDataSet(entries1, "");
        set.setColor(ContextCompat.getColor(activity, R.color.cl_f1a83a));
        set.setLineWidth(1.0f);
        set.setDrawCircles(false);
        set.setDrawValues(false);
        LineData lineData = new LineData();
        lineData.addDataSet(set);
        lineChart.setData(lineData);
        lineChart.animateX(500);

    }

    //流入流出资金
    private void showMoney(List<String> num) {
        zhuliin.setText(num.get(0));
        zhuliout.setText(num.get(1));
        sanhuNetIn.setText(num.get(2));
        if (num.get(2).contains("-"))
            sanhuNetIn.setTextColor(ContextCompat.getColor(activity, R.color.cl_119d3e));
        else sanhuNetIn.setTextColor(ContextCompat.getColor(activity, R.color.cl_d63535));

    }


    //圆环配置
    private void showPie(LinkedHashMap<String, Float> data, List<Integer> colors) {
        //显示百分比
        pieChart.setUsePercentValues(true);
        //去掉描述信息
        pieChart.getDescription().setEnabled(false);
        //设置偏移量
        pieChart.setExtraOffsets(5, 10, 5, 10);
        //设置滑动减速摩擦系数
        //  pieChart.setDragDecelerationFrictionCoef(0.95f);
        //中间文字
        // pieChart.setDrawCenterText(true);
        pieChart.setCenterText("今日资金");
        pieChart.setCenterTextSize(13);
        pieChart.setCenterTextColor(ContextCompat.getColor(activity, R.color.cl_888888));
        //设置饼图是否是空心的
        //  pieChart.setDrawHoleEnabled(true);
        //设置中心空心圆孔颜色
        pieChart.setHoleColor(ContextCompat.getColor(activity, R.color.white));
        //设置环形图和空心圆之间的圆环
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(52f);
        pieChart.setTransparentCircleRadius(50f);
        // 圆盘是否可转动
        pieChart.setRotationEnabled(false);
        //每个饼块描述信息
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setTouchEnabled(false);


        // 设置比例图
        Legend mLegend = pieChart.getLegend();
        mLegend.setEnabled(false);
        //数据
        ArrayList<PieEntry> yValues = new ArrayList<>();  //yVals用来表示封装每个饼块的实际数据
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            float value = (float) entry.getValue();
            yValues.add(new PieEntry(value, key));

        }
        PieDataSet dataSet = new PieDataSet(yValues, "");
        // 设置饼图区块之间的距离
        //  dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueTextColors(colors);
        dataSet.setColors(colors);
        // dataSet.setValueLineColor(Color.parseColor("#19AD0A"));
        dataSet.setValueLinePart1OffsetPercentage(110f);
        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(0.6f);

        dataSet.setValueLineColor(Color.BLACK);
        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(12f);
        pieChart.setData(pieData);
        // pieChart.invalidate();
        pieChart.animateXY(500, 500);
    }


    //显示资金流入流出数据
    private void setFundFlow(Funds fdata) {
        LinkedHashMap<String, Float> fundsData = new LinkedHashMap<>();
        //添加颜色
        ArrayList<Integer> colors = new ArrayList<>();
        if (fdata.get_zhuliInRate() > 0) {
            fundsData.put("主力流入", (float) fdata.get_zhuliInRate());
            colors.add(Color.parseColor("#DD3B19"));
        }
        if (fdata.get_sanhuInRate() > 0) {
            colors.add(Color.parseColor("#F26E14"));
            fundsData.put("散户流入", (float) fdata.get_sanhuInRate());
        }
        if (fdata.get_sanhuOutRate() > 0) {
            colors.add(Color.parseColor("#75CD6C"));
            fundsData.put("散户流出", (float) fdata.get_sanhuOutRate());
        }
        if (fdata.get_zhuliOutRate() > 0) {
            colors.add(Color.parseColor("#19AD0A"));
            fundsData.put("主力流出", (float) fdata.get_zhuliOutRate());
        }
        // if (fundsData.isEmpty()||fundsData)
        showPie(fundsData, colors);
        List<String> money = new ArrayList<>();
        money.add(fdata.get_zhuliIn());
        money.add(fdata.get_zhuliOut());
        money.add(fdata.get_zhuliNetIn());
        showMoney(money);
    }

}
