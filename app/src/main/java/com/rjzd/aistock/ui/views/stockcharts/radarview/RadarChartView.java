package com.rjzd.aistock.ui.views.stockcharts.radarview;
/**
 * Copyright 2014  XCL-Charts
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @Project XCL-Charts
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @Copyright Copyright (c) 2014 XCL-Charts (www.xclcharts.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.0
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.rjzd.aistock.ui.views.stockcharts.DemoView;
import com.rjzd.commonlib.utils.LogUtil;

import org.xclcharts.chart.RadarChart;
import org.xclcharts.chart.RadarData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.event.click.PointPosition;

import java.util.ArrayList;
import java.util.List;


/**
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @ClassName RadarChart01View
 * @Description 蜘蛛雷达图例子
 */
public class RadarChartView extends DemoView {

    private String TAG = "RadarChartView";
    private RadarChart chart = new RadarChart();

    private OnRadarItemClickListener itemClickListener;



    public RadarChartView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    public RadarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RadarChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
      /*  chartLabels();
        chartDataSet();*/
        chartRender();

        //綁定手势滑动事件
        //  this.bindTouch(this,chart);
    }

    public void setOnItemClickListener(OnRadarItemClickListener listener) {
        this.itemClickListener = listener;
    }

    /**
     * 设置数据源
     */
    public void setDataSource(List<RadarData> radarDataset, List<String> chartLabels) {
        chart.setDataSource(radarDataset);
        chart.setCategories(chartLabels);
    }
    public void setDataSource(RadarData radarData, List<String> chartLabels,List<Bitmap> bitmaps) {
        List<RadarData> radarDataList = new ArrayList<>();
        radarDataList.add(radarData);
        chart.setDataSource(radarDataList);
        chart.setCategories(chartLabels);
        chart.setCategoriesBitmap(bitmaps);
        Double max=0d;
        for (int i=0;i<radarData.getLinePoint().size();i++){
            if (max<radarData.getLinePoint().get(i).doubleValue()){
                max=radarData.getLinePoint().get(i).doubleValue();
            }
        }
        //数据轴最大值
        chart.getDataAxis().setAxisMax(max+max/4);
        //数据轴刻度间隔
        chart.getDataAxis().setAxisSteps(max/2);
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        chart.setChartRange(w, h);
    }

    private void chartRender() {
        try {

            //设置绘图区默认缩进px值
            chart.setPadding(DensityUtil.dip2px(25), DensityUtil.dip2px(40),DensityUtil.dip2px(25), DensityUtil.dip2px(20));
            chart.getBackgroundPaint();
            chart.getDyLegend().hideBorder();
            //点击事件处理
            chart.ActiveListenItemClick();
            chart.extPointClickRange(DensityUtil.dip2px(20));
            chart.setlabelOffset(DensityUtil.dip2px(5));

            chart.getLinePaint().setColor(Color.rgb(214, 53, 53)); //Color.parseColor("#BFE154"));
            chart.getLabelPaint().setColor(Color.WHITE);
            chart.getLabelPaint().setTextSize(DensityUtil.dip2px(13));
            chart.getCategoryAxis().getAxisPaint().setStrokeWidth(0.2f);
            chart.getLabelPaint().setFakeBoldText(false);
            chart.getDataAxis().hide();
            //主轴标签偏移50，以便留出空间用于显示点和标签
            chart.getDataAxis().setTickLabelMargin(DensityUtil.dip2px(50));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LogUtil.e(TAG, e.toString());
        }

    }
    @Override
    public void render(Canvas canvas) {
        try {
            chart.render(canvas);
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            triggerClick(event.getX(), event.getY());
        }
        return true;
    }

    //触发监听
    private void triggerClick(float x, float y) {
        PointPosition record = chart.getPositionRecord(x, y);
        if (null == record) return;
        if (null != itemClickListener) {
            itemClickListener.onItemClick(record.getDataChildID());
        }
    }
  public   interface OnRadarItemClickListener {
        void onItemClick(int position);
    }
}



