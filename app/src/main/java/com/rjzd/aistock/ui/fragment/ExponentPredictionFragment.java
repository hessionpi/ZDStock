package com.rjzd.aistock.ui.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.PredictionTrend;
import com.rjzd.aistock.api.PredictionTrendData;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.commonlib.utils.NetWorkUtil;

import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/6/15.
 */

public class ExponentPredictionFragment extends LazyF10Fragment {

    @Bind(R.id.tv_trend_summary)
    TextView tvTrendSummary;
    @Bind(R.id.hiddenline)
    LineChart hiddenline;
    @Bind(R.id.hidden_title)
    TextView hiddenTitle;

    @Override
    public void setStatistical() {
        super.statistical = "指数详情";
    }

    @Override
    protected int createView() {
        return R.layout.fragment_exponent_predition;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }

        if(NetWorkUtil.isNetworkConnected(activity)){
            presenter.getExponentPredict(stockCode);
            showLoadingDialog();
        }else{
            showFailedView();
        }
    }

    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data, dsTag);
        PredictionTrendData predictionTrendData = (PredictionTrendData) data;
        if (StatusCode.OK.getValue() == predictionTrendData.get_status().getValue()) {
            PredictionTrend trend = predictionTrendData.get_data();
            setPredictionTrendData(trend);
            tvTrendSummary.setText("* "+trend.get_summary());
        }
    }

    /**
     * 预测股价图
     *
     * @param predictionTrend
     */
    private void setPredictionTrendData(final PredictionTrend predictionTrend) {
        hiddenline.setDrawBorders(false);
        hiddenline.getDescription().setEnabled(false);
        hiddenline.setDrawGridBackground(false);
        hiddenline.setTouchEnabled(true);
        hiddenline.setDragEnabled(false);
        hiddenline.setScaleEnabled(false);
        hiddenline.setPinchZoom(false);
        XAxis xAxis = hiddenline.getXAxis();   //x轴
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
       // final List<String> labels=new ArrayList<>();
         xAxis.setLabelCount( predictionTrend.get_actualData().get_priceList().size() + predictionTrend.get_predictData().get_priceList().size() - 1, false);
     /*   for (int i=0;i<predictionTrend.get_actualData().get_priceList().size() + predictionTrend.get_predictData().get_priceList().size() - 1;i++){
            if (i == 0)
                labels.add(predictionTrend.get_actualData().get_startTiem());
            else if (i == predictionTrend.get_actualData().get_priceList().size() - 1)
                labels.add(predictionTrend.get_predictData().get_startTiem());
            else if (i == predictionTrend.get_actualData().get_priceList().size() + predictionTrend.get_predictData().get_priceList().size() - 1)
                labels.add(predictionTrend.get_predictData().get_endTime());

            else labels.add("");
        }*/
        xAxis.setTextSize(9);
        xAxis.setTextColor(ContextCompat.getColor(activity, R.color.cl_888888));
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int)value == 0)
                    return predictionTrend.get_actualData().get_startTiem();
                else if (Math.round(value) == predictionTrend.get_actualData().get_priceList().size() - 1)
                    return predictionTrend.get_predictData().get_startTiem();
                else if ((int)value == predictionTrend.get_actualData().get_priceList().size() + predictionTrend.get_predictData().get_priceList().size() - 1)
                    return predictionTrend.get_predictData().get_endTime();

                else return "";

               // return  labels.get((int)value);
            }
        });
        YAxis centerAxis = hiddenline.getAxisCenter();
        centerAxis.setEnabled(false);
        YAxis leftAxis = hiddenline.getAxisLeft();//y轴
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setLabelCount(4, false);
        leftAxis.setTextColor(ContextCompat.getColor(activity,R.color.cl_999999));
        leftAxis.setTextSize(9);
        YAxis rightAxis = hiddenline.getAxisRight();
        rightAxis.setEnabled(false);
        Legend mlegend = hiddenline.getLegend();
        mlegend.setEnabled(false);
        ArrayList<Entry> entries1 = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < predictionTrend.get_actualData().get_priceList().size(); i++) {
            entries1.add(new Entry(i, predictionTrend.get_actualData().get_priceList().get(i).floatValue()));
            if (i == predictionTrend.get_actualData().get_priceList().size() - 1) {
                entries2.add(new Entry(i, predictionTrend.get_actualData().get_priceList().get(i).floatValue()));
            } else {
                entries2.add(new Entry(i, Float.NaN));
            }
        }
        for (int i = 0; i < predictionTrend.get_predictData().get_priceList().size(); i++) {
            entries2.add(new Entry(predictionTrend.get_actualData().get_priceList().size() + i, predictionTrend.get_predictData().get_priceList().get(i).floatValue()));
        }
        LineDataSet set = new LineDataSet(entries1, "");
        set.setLineWidth(1.0f);
        set.setDrawCircles(true);
        set.setCircleColor(ContextCompat.getColor(activity, R.color.cl_d63535));
        set.setDrawCircleHole(false);
        set.setDrawValues(false);
        set.setColor(ContextCompat.getColor(activity, R.color.cl_d63535));

        LineDataSet set1 = new LineDataSet(entries2, "");
        set1.setLineWidth(1.0f);
        set1.setDrawCircles(true);
        set1.setDrawCircleHole(false);
        set1.setCircleColor(ContextCompat.getColor(activity, R.color.cl_0d74cb));
        set1.setDrawValues(false);
        set1.enableDashedLine(10, 10, 0);
        set1.setColor(ContextCompat.getColor(activity, R.color.cl_0d74cb));
        LineData lineData = new LineData();
        lineData.addDataSet(set1);
        lineData.addDataSet(set);

        xAxis.setAxisMinimum(lineData.getXMin() - 0.6f);
        xAxis.setAxisMaximum(lineData.getXMax() + 0.6f);
        hiddenline.setData(lineData);
        hiddenline.invalidate();
    }

}
