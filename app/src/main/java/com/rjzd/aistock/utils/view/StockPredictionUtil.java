package com.rjzd.aistock.utils.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.mikephil.charting.charts.CombinedChart;
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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.CloudFeature;
import com.rjzd.aistock.ui.adapters.IchimokuExplainAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.rjzd.aistock.Constants.ICHIMOKU_EXPLAIN_BAD;
import static com.rjzd.aistock.Constants.ICHIMOKU_EXPLAIN_GOOD;
import static com.rjzd.aistock.Constants.ICHIMOKU_EXPLAIN_NEUTRAL;


/**
 * Created by Administrator on 2017/6/1.
 */

public class StockPredictionUtil {
    //宏观因子
    public static void showMarcoChart(Context context, CombinedChart chart, final List<Double> barDataList, List<Double> priceList, final List<String> dateList,final int style,final  boolean flag){
        chart.setDrawBorders(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);
        XAxis xAxis = chart.getXAxis();//x轴
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        if (!flag){
            xAxis.setLabelCount(2,true);
        }else{
            xAxis.setLabelCount(4,false);
        }
        xAxis.setTextColor(ContextCompat.getColor(context,R.color.cl_888888));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (!flag) {
                    if ((int) value == 0) {
                        return dateList.get(0);
                    } else if ((int) value == dateList.size() - 1) {
                        return dateList.get((int) value);
                    }
                    return "";
                }else{
                    return dateList.get((int) value);
                }
            }
        });
        YAxis centerAxis=chart.getAxisCenter();
        centerAxis.setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();//y轴
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
//
        leftAxis.setTextColor(ContextCompat.getColor(context,R.color.cl_e64225));
        leftAxis.setLabelCount(4, false);
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.format("%.0f",value).toString();
            }
        });

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
//
        rightAxis.setTextColor(ContextCompat.getColor(context,R.color.cl_10174b));
        rightAxis.setLabelCount(4, false);
        rightAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return Math.round(value)+"";
            }
        });
        Legend mlegend = chart.getLegend();
        mlegend.setEnabled(false);
       // int green = ContextCompat.getColor(context,R.color.cl_fall);
       // int red = ContextCompat.getColor(context,R.color.cl_raise);
        //ArrayList<Integer> colors = new ArrayList<>();
        //柱图数据
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < barDataList.size(); i++) {
            entries.add(new BarEntry(i, barDataList.get(i).floatValue()));
           /* if (entries.get(i).getY() >= 0) {
                colors.add(red);
            } else {
                colors.add(green);
            }*/
        }
        BarDataSet barDataSet = new BarDataSet(entries, "");
        if (!flag) {
            barDataSet.setDrawValues(false);
        }else{
            barDataSet.setDrawValues(true);
            barDataSet.setValueTextSize(8);
        }
        barDataSet.setColor(ContextCompat.getColor(context,R.color.cl_e64225));
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                String format ="";
                switch (style){
                    case 0:
                        format = Math.round(value)+"";
                        break;
                    case 1:
                        if(value/10000 >= 1){
                            format = String.format("%.2f", value/10000) + "亿";
                        }else{
                            format = String.format("%.2f", value) + "万";
                        }
                        break;

                    case 2:
                        format = String.format("%.2f",value)+"%";
                        break;

                    case 3:
                        format = String.format("%.1f",value);
                        break;
                }
                return format;
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
        for (int i = 0; i < priceList.size(); i++) {
            entries1.add(new Entry(i, priceList.get(i).floatValue()));
        }

        LineDataSet lineDataSet = new LineDataSet(entries1, "");
        lineDataSet.setColor(ContextCompat.getColor(context, R.color.cl_10174b));
        lineDataSet.setLineWidth(1.8f);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineDataSet.setCircleColorHole(Color.WHITE);
        lineDataSet.setCircleColor(ContextCompat.getColor(context,R.color.cl_10174b));
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
        chart.setData(combinedData);

        chart.invalidate();
    }
    //云图
    public static void showIchimokuExplain(Context context,CloudFeature feature, RecyclerView rv_raise, RecyclerView rv_fall, RecyclerView rv_flat){
        List<String> goodList=feature.get_goodList();
        List<String> badList=feature.get_badList();
        List<String> neutralList=feature.get_neutralList();
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(context, 2);
        rv_raise.setLayoutManager(gridLayoutManager1);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(context, 2);
        rv_fall.setLayoutManager(gridLayoutManager2);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(context, 2);
        rv_flat.setLayoutManager(gridLayoutManager3);
        IchimokuExplainAdapter ichimokuGoodAdapter = new IchimokuExplainAdapter(context,goodList,ICHIMOKU_EXPLAIN_GOOD);
        rv_raise.setAdapter(ichimokuGoodAdapter);
        IchimokuExplainAdapter ichimokuBadAdapter = new IchimokuExplainAdapter(context,badList,ICHIMOKU_EXPLAIN_BAD);
        rv_fall.setAdapter(ichimokuBadAdapter);
        IchimokuExplainAdapter ichimokuNeutralAdapter = new IchimokuExplainAdapter(context,neutralList,ICHIMOKU_EXPLAIN_NEUTRAL);
        rv_flat.setAdapter(ichimokuNeutralAdapter);

    }
}
