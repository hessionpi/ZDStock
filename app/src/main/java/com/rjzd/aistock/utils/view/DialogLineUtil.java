package com.rjzd.aistock.utils.view;

import android.graphics.Color;
import android.graphics.DashPathEffect;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class DialogLineUtil {
    /*  public static void showDialogLinechart(LineChart lineChart, final PredictFactorLineData predictFactorLineData) {
          lineChart.setDrawBorders(false);
          lineChart.getDescription().setEnabled(false);
          lineChart.setDrawGridBackground(false);
          lineChart.setTouchEnabled(true);
          lineChart.setDragEnabled(false);
          lineChart.setScaleEnabled(false);
          lineChart.setPinchZoom(false);
          ArrayList<Integer> colors = new ArrayList<>();
          colors.add(Color.RED);
          colors.add(Color.BLACK);
          colors.add(Color.GREEN);
          colors.add(Color.YELLOW);
          colors.add(Color.BLUE);
          colors.add(Color.MAGENTA);
          colors.add(Color.CYAN);
          colors.add(Color.LTGRAY);
          colors.add(Color.GRAY);
          colors.add(Color.DKGRAY);
          XAxis xAxis = lineChart.getXAxis();   //x轴
          xAxis.setEnabled(true);
          xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
          xAxis.setDrawGridLines(false);
          xAxis.setDrawAxisLine(true);
          xAxis.setLabelCount(2, true);
          xAxis.setTextSize(9);
          xAxis.setYOffset(5);
          xAxis.setAvoidFirstLastClipping(true);
          xAxis.setTextColor(Color.parseColor("#888888"));
          xAxis.setValueFormatter(new IAxisValueFormatter() {
              @Override
              public String getFormattedValue(float value, AxisBase axis) {
                  if ((int) value == 0)
                      return predictFactorLineData.get_periods().get(0);
                  else if ((int) value == predictFactorLineData.get_periods().size() - 1)
                      return predictFactorLineData.get_periods().get(predictFactorLineData.get_periods().size() - 1);
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
          rightAxis.setGridColor(Color.parseColor("#888888"));
          rightAxis.setLabelCount(3, false);
          rightAxis.setXOffset(10);
          rightAxis.setTextColor(Color.parseColor("#f1a83a"));
          Legend mlegend = lineChart.getLegend();
          // mlegend.setForm(Legend.LegendForm.CIRCLE);// 样式
          LinkedList<LegendEntry> linkedList = new LinkedList<LegendEntry>();
          for (int i = 0; i < predictFactorLineData.get_elements().size(); i++) {
              LegendEntry entry = new LegendEntry();
              entry.formColor = colors.get(i);
              entry.label = predictFactorLineData.get_elements().get(i);
              linkedList.add(entry);
          }
          mlegend.setWordWrapEnabled(true);
          mlegend.setCustom(linkedList);
          mlegend.setFormSize(5f);// 字体
          mlegend.setTextColor(Color.parseColor("#999999"));// 颜色
          LineData lineData = new LineData();
          for (int i = 0; i < predictFactorLineData.get_labelMapValues().size(); i++) {
              ArrayList<Entry> entries = new ArrayList<>();
              for (int j = 0; j < predictFactorLineData.get_labelMapValues().get(i).size(); j++) {
                  if (!Double.isNaN(predictFactorLineData.get_labelMapValues().get(i).get(j))) {
                      entries.add(new Entry(j, predictFactorLineData.get_labelMapValues().get(i).get(j).floatValue()));
                  }
              }
              LineDataSet set = new LineDataSet(entries, "");
              set.setColor(colors.get(i));
              set.setLineWidth(1.0f);
              set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
              set.setCubicIntensity(0.2f);
              set.setDrawCircles(false);
              set.setDrawValues(false);
              lineData.addDataSet(set);
          }
          lineChart.setData(lineData);
          lineChart.invalidate();

      }*/
    public static void showDialogLinechart(LineChart lineChart, final List<Double> baseData, final List<Double> data, final List<String> labels) {
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
        xAxis.setTextColor(Color.parseColor("#888888"));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int) value == 0)
                    return labels.get(0);
                else if ((int) value == labels.size() - 1)
                    return labels.get(labels.size() - 1);
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
        rightAxis.setGridColor(Color.parseColor("#888888"));
        rightAxis.setLabelCount(3, false);
        rightAxis.setXOffset(10);
        rightAxis.setTextColor(Color.parseColor("#f1a83a"));
        Legend mlegend = lineChart.getLegend();
        mlegend.setEnabled(false);
        /*mlegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        LinkedList<LegendEntry> linkedList = new LinkedList<LegendEntry>();
        LegendEntry entry1 = new LegendEntry();
        entry1.formColor = Color.RED;
        entry1.label = baseLegend;
        LegendEntry entry2 = new LegendEntry();
        entry2.formColor = Color.BLUE;
        entry2.label = legend;
        linkedList.add(entry1);
        linkedList.add(entry2);

        mlegend.setWordWrapEnabled(true);
        mlegend.setCustom(linkedList);
        mlegend.setFormSize(5f);// 字体
        mlegend.setTextColor(Color.parseColor("#333333"));*/

        ArrayList<Entry> entries1 = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            entries1.add(new Entry(i, data.get(i).floatValue()));
        }
        LineDataSet set1 = new LineDataSet(entries1, "");
        set1.setColor(Color.parseColor("#d63535"));
        set1.setLineWidth(1.0f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);

        ArrayList<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < baseData.size(); i++) {
            entries2.add(new Entry(i, baseData.get(i).floatValue()));
        }
        LineDataSet set2 = new LineDataSet(entries2, "");
        set2.setColor(Color.parseColor("#0d74cb"));
        set2.setLineWidth(1.0f);
        set2.setDrawCircles(false);
        set2.setDrawValues(false);

        LineData lineData = new LineData();
        lineData.addDataSet(set1);
        lineData.addDataSet(set2);

        lineChart.setData(lineData);
        lineChart.invalidate();

    }
}
