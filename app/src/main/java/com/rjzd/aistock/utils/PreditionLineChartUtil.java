package com.rjzd.aistock.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.rjzd.aistock.R;
import com.rjzd.commonlib.utils.LogUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Administrator on 2017/3/15.
 */

public class PreditionLineChartUtil {
    static void setLine(LineChart lineChart, final List<String> labels,final int size) {
        lineChart.setDrawBorders(false);   //折线图上添加边框
        lineChart.getDescription().setEnabled(false);  //去掉数据描述
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setTouchEnabled(false);  //是否可以触摸
        lineChart.setPinchZoom(false);//是否可以双手缩放
        // x轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴的位置
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        xAxis.setLabelCount(2, true);     //X轴描述文字个数
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(10);
        xAxis.setAxisLineColor(Color.parseColor("#e6e6e6"));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                LogUtil.i("ZSJ", value + "");
                if (value == 0)
                    return labels.get(0);
                else if (value == size - 1) return labels.get(1);
                else return "";
            }
        });
        //y轴
        YAxis centerAxis = lineChart.getAxisCenter();
        centerAxis.setEnabled(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.TRANSPARENT);
        leftAxis.setTextSize(10);
        leftAxis.setLabelCount(2, true);
        leftAxis.setGridColor(Color.parseColor("#e6e6e6"));
        leftAxis.setAxisLineColor(Color.TRANSPARENT);
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setTextColor(Color.TRANSPARENT);
        rightAxis.setTextSize(10);
        rightAxis.setLabelCount(2, true);
        rightAxis.setGridColor(Color.parseColor("#e6e6e6"));
        rightAxis.setAxisLineColor(Color.TRANSPARENT);
    }

    public static void showLine(LineChart lineChart, final boolean isSell, final List<Double> points300, final List<Double> points, final Context context, final List<String> labels, String name){
        setLine(lineChart, labels,points.size());
        //设置比例图标示
        Legend mlegend = lineChart.getLegend();
        LinkedList<LegendEntry> linkedList = new LinkedList<LegendEntry>();
        LegendEntry redentry = new LegendEntry();
        redentry.formColor = Color.parseColor("#e64b4b");
        redentry.label = name;
        LegendEntry greenentry = new LegendEntry();
        greenentry.formColor = Color.parseColor("#3e5b7d");
        greenentry.label = "沪深300";
        linkedList.add(redentry);
        linkedList.add(greenentry);
        mlegend.setCustom(linkedList);
        mlegend.setFormSize(6f);// 字体
        mlegend.setTextColor(Color.BLACK);// 颜色
        mlegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        if (points.size()==0||points300.size()==0) return;
        //数据
        ArrayList<Entry> entries1 = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < points300.size(); i++) {
            entries1.add(new Entry(i, points300.get(i).floatValue()));
            entries2.add(new Entry(i, points.get(i).floatValue()));
        }
        LineDataSet set = new LineDataSet(entries1, "");
        LineDataSet set1 = new LineDataSet(entries2, "");
        set.setColor(Color.parseColor("#3e5b7d"));
        set.setLineWidth(1.0f);
        set.setDrawCircles(false);
        set.setDrawValues(false);
        set.setValueTextColor(Color.WHITE);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER); //设置曲线为圆滑的线
        set.setCubicIntensity(0.2f); //设置曲线圆滑的角度

        set1.setColor(Color.parseColor("#e64b4b"));
        set1.setLineWidth(1.0f);
        set1.setDrawCircles(false);
        set1.setDrawValues(true);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);
        set1.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "";
            }

            @Override
            public Bitmap getFormattedBitmap(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

                Bitmap bmp1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.buy);
                Bitmap bmp2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.sell);
                if ((int) entry.getX() == 0)
                    return bmp1;
                else if ((int) entry.getX() == points.size() - 1 && isSell)
                    return bmp2;
                else
                    return null;
            }

            @Override
            public Bitmap getFormattedLine(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return null;
            }
        });
        LineData lineData = new LineData();
        lineData.addDataSet(set);
        lineData.addDataSet(set1);
        lineChart.setData(lineData);   //设置数据
        lineChart.animateX(500);//立即执行的动画，x轴
        //lineChart.invalidate();
    }

    public static void showLine2(LineChart lineChart, final boolean isSell, final List<Double> points300, final List<Double> points, final Context context, final List<String> labels, String name) {
        setLine(lineChart, labels,points.size());
        //设置比例图标示
        Legend mlegend = lineChart.getLegend();
        LinkedList<LegendEntry> linkedList = new LinkedList<LegendEntry>();
        LegendEntry redentry = new LegendEntry();
        redentry.formColor = Color.parseColor("#e64b4b");
        redentry.label = name;
        LegendEntry greenentry = new LegendEntry();
        greenentry.formColor = Color.parseColor("#3e5b7d");
        greenentry.label = "沪深300";
        linkedList.add(redentry);
        linkedList.add(greenentry);
        mlegend.setCustom(linkedList);
        mlegend.setFormSize(6f);// 字体
        mlegend.setTextColor(Color.BLACK);// 颜色
        mlegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        if (points.size()==0||points300.size()==0) return;
        //数据
        ArrayList<Entry> entries1 = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < points300.size(); i++) {
            entries1.add(new Entry(i, points300.get(i).floatValue()));
            entries2.add(new Entry(i, points.get(i).floatValue()));
        }
        LineDataSet set = new LineDataSet(entries1, "");
        LineDataSet set1 = new LineDataSet(entries2, "");
        set.setColor(Color.parseColor("#3e5b7d"));
        set.setLineWidth(1.0f);
        set.setDrawCircles(false);
        set.setDrawValues(false);
        set.setValueTextColor(Color.WHITE);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER); //设置曲线为圆滑的线
        set.setCubicIntensity(0.2f); //设置曲线圆滑的角度

        set1.setColor(Color.parseColor("#e64b4b"));
        set1.setLineWidth(1.0f);
        set1.setDrawCircles(false);
        set1.setDrawValues(true);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);
        set1.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "";
            }

            @Override
            public Bitmap getFormattedBitmap(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

                Bitmap bmp1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.buy);
                Bitmap bmp2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.sell);
                if ((int) entry.getX() == 0)
                    return bmp1;
                else if ((int) entry.getX() == points.size() - 1 && isSell)
                    return bmp2;
                else
                    return null;
            }

            @Override
            public Bitmap getFormattedLine(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return null;
            }
        });
        LineData lineData = new LineData();
        lineData.addDataSet(set);
        lineData.addDataSet(set1);
        lineChart.setData(lineData);   //设置数据
        lineChart.animateX(500);//立即执行的动画，x轴
        //lineChart.invalidate();
    }


}
