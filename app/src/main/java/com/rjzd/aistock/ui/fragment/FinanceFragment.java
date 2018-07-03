package com.rjzd.aistock.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.BusinessIncome;
import com.rjzd.aistock.api.FinanceAnalysisData;
import com.rjzd.aistock.api.FinancePerformance;
import com.rjzd.aistock.api.Performance;
import com.rjzd.aistock.api.Profits;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.ui.adapters.AnalyzeAdapter;
import com.rjzd.aistock.ui.views.MyItemDecoration;
import com.rjzd.commonlib.utils.NetWorkUtil;
import com.rjzd.commonlib.utils.ToastUtils;
import java.util.ArrayList;
import butterknife.Bind;

/**
 * 财务
 * <p>
 * Created by Administrator on 2017/1/11.
 */

public class FinanceFragment extends LazyF10Fragment implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.rg_range)
    RadioGroup rgRange;
    @Bind(R.id.performance)
    LinearLayout performance;
    @Bind(R.id.analyze)
    RecyclerView analyze;
    @Bind(R.id.taking_combinedchart)
    CombinedChart takingCombinedchart;
    @Bind(R.id.profit_combinedchart)
    CombinedChart profitCombinedchart;
    @Bind(R.id.level_linechart)
    LineChart levelLinechart;
    @Bind(R.id.tv_taking)
    TextView tvTaking;
    @Bind(R.id.tv_profit)
    TextView tvProfit;
    @Bind(R.id.tv_level)
    TextView tvLevel;
    @Bind(R.id.id_stickynavlayout_innerscrollview)
    ScrollView idStickynavlayoutInnerscrollview;

    @Bind(R.id.income_no_data)
    TextView mIncomeNodata;
    @Bind(R.id.profits_no_data)
    TextView mProfitsNodata;
    @Bind(R.id.profitability_no_data)
    TextView mProfitabilityNodata;


    private AnalyzeAdapter adapter;
    ArrayList<String> years;

    @Override
    protected int createView() {
        return R.layout.fragment_finance;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }

        if(NetWorkUtil.isNetworkConnected(activity)){
            getFinance();
        }else{
            activity.showToast(R.string.no_network);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        performance.setVisibility(View.VISIBLE);
        analyze.setVisibility(View.GONE);
        MyItemDecoration decoration = new MyItemDecoration(activity, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#e6e6e6"));
        analyze.addItemDecoration(decoration);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        analyze.setLayoutManager(layoutmanager);
        if (null == adapter) {
            adapter = new AnalyzeAdapter(activity);
        }
        analyze.setAdapter(adapter);
        rgRange.setOnCheckedChangeListener(this);
    }

    FinancePerformance fpData;
    FinanceAnalysisData faData;

    @Override
    public void setStatistical() {
        statistical = "finance";
    }

    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data, dsTag);
        switch (dsTag) {
            case Constants.DS_TAG_FINANCE_PERFORMANCE:
                fpData = (FinancePerformance) data;
                if (StatusCode.OK.getValue() == fpData.get_status().getValue()) {
                    mHasLoadedOnce = true;
                    Performance performance = fpData.get_data();
                    years = (ArrayList<String>) performance.get_years();
                    if(!performance.get_businessIncomes().isEmpty()){
                        mIncomeNodata.setVisibility(View.GONE);
                        tvTaking.setText("营业收益（" + performance.get_businessIncomes().get(0).get_unit() + "）");
                        showTakingData(performance);
                    }else{
                        mIncomeNodata.setVisibility(View.VISIBLE);
                    }

                    if(!performance.get_profits().isEmpty()){
                        mProfitsNodata.setVisibility(View.GONE);
                        tvProfit.setText("净利润（" + performance.get_profits().get(0).get_unit() + "）");
                        showProfitData(performance);
                    }else{
                        mProfitsNodata.setVisibility(View.VISIBLE);
                    }

                    if(!performance.get_eps().isEmpty()){
                        mProfitabilityNodata.setVisibility(View.GONE);
                        showLevelData(performance);
                    }else{
                        mProfitabilityNodata.setVisibility(View.VISIBLE);
                    }

                }
                break;

            case Constants.DS_TAG_FINANCE_ANALYSIS:
                faData = (FinanceAnalysisData) data;
                if (StatusCode.OK.getValue() == faData.get_status().getValue()) {
                    mHasLoadedOnce = true;
                    adapter.addAll(faData.get_data());
                    adapter.setReportDate(faData.get_reportDate());

                }

                break;

            default:
                if (null == data) {
                    showFailedView();
                }
                break;
        }
    }

    private void getFinance() {
        presenter.getFinancePerformance(stockCode);
        presenter.getFinanceAnalysis(stockCode);
        showLoadingDialog();
    }


    //营业收入
    private void showTakingData(Performance performance) {
        ArrayList<BusinessIncome> businessIncomes = (ArrayList<BusinessIncome>) performance.get_businessIncomes();
        if ( (years != null && !years.isEmpty()) && (null != businessIncomes && !businessIncomes.isEmpty())) {
            showTakingCombinedChart(businessIncomes);
        }
    }

    //利润
    private void showProfitData(Performance performance) {
        ArrayList<Profits> profits = (ArrayList<Profits>) performance.get_profits();
        if ((null != years && !years.isEmpty()) && (null != profits && !profits.isEmpty())){
            showProfitCombinedChart(profits);
        }
    }

    //盈利水平
    private void showLevelData(Performance performance) {
        ArrayList<Double> eps = (ArrayList<Double>) performance.get_eps();
        if ((null != years && !years.isEmpty()) && (null != eps && !eps.isEmpty())){
            showLevelLinechart(eps);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_performance:
                if (fpData == null) {
                    ToastUtils.show(activity, "暂无数据", Toast.LENGTH_LONG);
                }
                performance.setVisibility(View.VISIBLE);
                analyze.setVisibility(View.GONE);
                break;

            case R.id.rb_analyze:
                if (faData == null) {
                    ToastUtils.show(activity, "暂无数据", Toast.LENGTH_LONG);
                }
                performance.setVisibility(View.GONE);
                analyze.setVisibility(View.VISIBLE);
                break;
        }
    }

    //营业收入
    private void showTakingCombinedChart(final ArrayList<BusinessIncome> businessIncomes) {
        takingCombinedchart.setDrawBorders(false);
        takingCombinedchart.getDescription().setEnabled(false);
        takingCombinedchart.setDrawGridBackground(false);
        takingCombinedchart.setTouchEnabled(false);
        takingCombinedchart.setDragEnabled(false);
        takingCombinedchart.setScaleEnabled(false);
        takingCombinedchart.setPinchZoom(false);
        //takingCombinedchart.setDrawValueAboveBar(true);
        XAxis xAxis = takingCombinedchart.getXAxis();//x轴
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(businessIncomes.size());
        xAxis.setTextColor(ContextCompat.getColor(activity, R.color.cl_888888));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return years.get((int) value);
            }
        });
        YAxis centerAxis = takingCombinedchart.getAxisCenter();
        centerAxis.setEnabled(false);
        YAxis leftAxis = takingCombinedchart.getAxisLeft();//y轴
        leftAxis.setDrawZeroLine(true); // draw a zero line
        leftAxis.setZeroLineColor(ContextCompat.getColor(activity, R.color.cl_e6e6e6));
        leftAxis.setZeroLineWidth(0.6f);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = takingCombinedchart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(4, false);

        rightAxis.setTextColor(ContextCompat.getColor(activity, R.color.cl_10174b));
        rightAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "%";
            }
        });
        Legend mlegend = takingCombinedchart.getLegend();
        mlegend.setEnabled(false);
        //柱图数据
        final ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < businessIncomes.size(); i++) {
            entries.add(new BarEntry(i, new float[]{Math.abs((float) (businessIncomes.get(i).get_businessIncomeQ1())), Math.abs((float) (businessIncomes.get(i).get_businessIncomeQ2())), Math.abs((float) (businessIncomes.get(i).get_businessIncomeQ3())),Math.abs( (float) (businessIncomes.get(i).get_businessIncomeQ4()))}));
            //entries.add(new BarEntry(i, new float[]{(float) (Math.abs(businessIncomes.get(i).get_businessIncomeQ1())),  (float) (Math.abs(businessIncomes.get(i).get_businessIncomeQ2())),  (float) (Math.abs(businessIncomes.get(i).get_businessIncomeQ3())), (float) (Math.abs(businessIncomes.get(i).get_businessIncomeQ4()))}));
        }
        BarDataSet barDataSet = new BarDataSet(entries, "");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(activity, R.color.cl_e64225));
        //  colors.add(Color.BLACK);
        colors.add(Color.parseColor("#fb5e5e"));
        // colors.add(Color.BLACK);
        colors.add(Color.parseColor("#fc7e7e"));
        // colors.add(Color.BLACK);
        colors.add(Color.parseColor("#fc9d9d"));
        barDataSet.setColors(colors);
        barDataSet.setDrawValues(true);
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setValueTextSize(9);
        barDataSet.setValueTextColor(ContextCompat.getColor(activity, R.color.cl_e64225));
        barDataSet.setValueFormatter(new DefaultValueFormatter(2) {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                // LogUtil.e("ZSJ", value + "---------------------------" + businessIncomes.get((int) entry.getX()).get_businessIncomeQ4());
                if (businessIncomes.get((int) entry.getX()).get_businessIncomeQ4() != 0) {
                    //if (value*100== businessIncomes.get((int) entry.getX()).get_businessIncomeQ4()) {
                    if (Math.abs(value - Math.abs(businessIncomes.get((int) entry.getX()).get_businessIncomeQ4())) <= 1e-3) {
                        //  LogUtil.e("ZSJ", "---------------------------" + entry.getX());
                        return String.format("%.2f", entry.getY()) + "";
                    } else {
                        return "";
                    }
                } else if (businessIncomes.get((int) entry.getX()).get_businessIncomeQ3() != 0) {
                    if ((Math.abs(value - Math.abs(businessIncomes.get((int) entry.getX()).get_businessIncomeQ3()) )<= 1e-3)) {
                        //  LogUtil.e("ZSJ", "---------------------------" + entry.getX());
                        return String.format("%.2f", entry.getY()) + "";
                    } else {
                        return "";
                    }
                } else if (businessIncomes.get((int) entry.getX()).get_businessIncomeQ2() != 0) {
                    if ((Math.abs(value -Math.abs( businessIncomes.get((int) entry.getX()).get_businessIncomeQ2())) <= 1e-3)) {
                        // LogUtil.e("ZSJ", "---------------------------" + entry.getX());
                        return String.format("%.2f", entry.getY()) + "";
                    } else {
                        return "";
                    }
                } else if (businessIncomes.get((int) entry.getX()).get_businessIncomeQ1() != 0) {
                    if ((Math.abs(value -Math.abs( businessIncomes.get((int) entry.getX()).get_businessIncomeQ1())) <= 1e-3)) {
                        //  LogUtil.e("ZSJ", "---------------------------" + entry.getX());
                        return String.format("%.2f", entry.getY()) + "";
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            }
        });
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        barData.setBarWidth(0.6f);
        //线图数据
        ArrayList<Entry> entries1 = new ArrayList<>();
        for (int i = 0; i < businessIncomes.size(); i++) {
            entries1.add(new Entry(i, (float) (businessIncomes.get(i).get_incomeYoy())));
        }
        LineDataSet lineDataSet = new LineDataSet(entries1, "");
        //设置颜色
        lineDataSet.setColor(ContextCompat.getColor(activity, R.color.cl_10174b));
        //线宽
        lineDataSet.setLineWidth(1.8f);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineDataSet.setCircleColorHole(Color.WHITE);
        lineDataSet.setCircleColor(ContextCompat.getColor(activity, R.color.cl_10174b));
        lineDataSet.setCircleRadius(4f);
        lineDataSet.setCircleHoleRadius(2f);
        //绘制数据
        lineDataSet.setDrawValues(false);
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedData.setData(barData);
        //图显示不全解决办法
        xAxis.setAxisMinimum(combinedData.getXMin() - 0.6f);
        xAxis.setAxisMaximum(combinedData.getXMax() + 0.6f);
        takingCombinedchart.setData(combinedData);
        takingCombinedchart.animateY(500);
    }

    //利润
    private void showProfitCombinedChart(final ArrayList<Profits> profits) {
        profitCombinedchart.setDrawBorders(false);
        profitCombinedchart.getDescription().setEnabled(false);
        profitCombinedchart.setDrawGridBackground(false);
        profitCombinedchart.setTouchEnabled(false);
        profitCombinedchart.setDragEnabled(false);
        profitCombinedchart.setScaleEnabled(false);
        profitCombinedchart.setPinchZoom(false);
        XAxis xAxis = profitCombinedchart.getXAxis();//x轴
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(profits.size());
        xAxis.setTextColor(ContextCompat.getColor(activity, R.color.cl_888888));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return years.get((int) value);
            }
        });
        YAxis centerAxis = profitCombinedchart.getAxisCenter();
        centerAxis.setEnabled(false);
        YAxis leftAxis = profitCombinedchart.getAxisLeft();//y轴
        leftAxis.setDrawZeroLine(true); // draw a zero line
        leftAxis.setZeroLineColor(ContextCompat.getColor(activity, R.color.cl_e6e6e6));
        leftAxis.setZeroLineWidth(0.6f);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        YAxis rightAxis = profitCombinedchart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(4, false);
        rightAxis.setTextColor(ContextCompat.getColor(activity, R.color.cl_10174b));
        rightAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "%";
            }
        });
        Legend mlegend = profitCombinedchart.getLegend();
        mlegend.setEnabled(false);
        int green = ContextCompat.getColor(activity, R.color.cl_119d3e);
        int red = ContextCompat.getColor(activity, R.color.cl_e64225);
        ArrayList<Integer> colors = new ArrayList<>();
        //柱图数据
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < profits.size(); i++) {
            entries.add(new BarEntry(i, (float) (profits.get(i).get_netProfits())));
            if (entries.get(i).getY() >= 0) {
                colors.add(red);
            } else {
                colors.add(green);
            }
        }
        BarDataSet barDataSet = new BarDataSet(entries, "");
        barDataSet.setColors(colors);
        barDataSet.setDrawValues(true);
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setValueTextSize(10);
        barDataSet.setValueTextColors(colors);
        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (int) value + "";
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
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        barData.setBarWidth(0.6f);
        //线图数据
        ArrayList<Entry> entries1 = new ArrayList<>();
        for (int i = 0; i < profits.size(); i++) {
            entries1.add(new Entry(i, (float) (profits.get(i).get_profitsYoy())));
        }

        LineDataSet lineDataSet = new LineDataSet(entries1, "");
        lineDataSet.setColor(ContextCompat.getColor(activity, R.color.cl_10174b));
        lineDataSet.setLineWidth(1.8f);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineDataSet.setCircleColorHole(Color.WHITE);
        lineDataSet.setCircleColor(ContextCompat.getColor(activity, R.color.cl_10174b));
        lineDataSet.setCircleRadius(4f);
        lineDataSet.setCircleHoleRadius(2f);
        lineDataSet.setDrawValues(false);
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedData.setData(barData);
        //图显示不全解决办法
        xAxis.setAxisMinimum(combinedData.getXMin() - 0.6f);
        xAxis.setAxisMaximum(combinedData.getXMax() + 0.6f);
        profitCombinedchart.setData(combinedData);
        profitCombinedchart.animateY(500);
    }

    //盈利水平
    private void showLevelLinechart(final ArrayList<Double> eps) {
        levelLinechart.setDrawBorders(false);
        levelLinechart.getDescription().setEnabled(false);
        levelLinechart.setDrawGridBackground(false);
        levelLinechart.setTouchEnabled(true);
        levelLinechart.setDragEnabled(false);
        levelLinechart.setScaleEnabled(false);
        levelLinechart.setPinchZoom(false);
        XAxis xAxis = levelLinechart.getXAxis();   //x轴
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelCount(eps.size());
        xAxis.setTextColor(ContextCompat.getColor(activity, R.color.cl_888888));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return years.get((int) value);
            }
        });
        YAxis centerAxis = levelLinechart.getAxisCenter();
        centerAxis.setEnabled(false);
        YAxis leftAxis = levelLinechart.getAxisLeft();//y轴
        leftAxis.setEnabled(false);
        YAxis rightAxis = levelLinechart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(4, false);
        //rightAxis.setXOffset(20);
        rightAxis.setTextColor(ContextCompat.getColor(activity, R.color.cl_10174b));
        Legend mlegend = levelLinechart.getLegend();
        mlegend.setEnabled(false);
        ArrayList<Entry> entries1 = new ArrayList<>();
        for (int i = 0; i < eps.size(); i++) {
            entries1.add(new Entry(i, eps.get(i).floatValue()));
        }
        LineDataSet set = new LineDataSet(entries1, "");
        set.setColor(ContextCompat.getColor(activity, R.color.cl_10174b));
        set.setLineWidth(1.8f);
        set.setDrawCircles(true);
        set.setCircleColorHole(Color.WHITE);
        set.setCircleColor(ContextCompat.getColor(activity, R.color.cl_10174b));
        set.setCircleRadius(4f);
        set.setCircleHoleRadius(2f);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(ContextCompat.getColor(activity, R.color.cl_10174b));

        LineData lineData = new LineData();
        lineData.addDataSet(set);
        xAxis.setAxisMinimum(lineData.getXMin() - 0.6f);
        xAxis.setAxisMaximum(lineData.getXMax() + 0.6f);
        levelLinechart.setData(lineData);
        levelLinechart.animateX(500);
    }

}
