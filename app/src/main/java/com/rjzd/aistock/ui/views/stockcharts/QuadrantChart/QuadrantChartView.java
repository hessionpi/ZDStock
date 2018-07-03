/**
 * Copyright 2014  XCL-Charts
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
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
 * @version 2.1
 */
package com.rjzd.aistock.ui.views.stockcharts.QuadrantChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import com.rjzd.aistock.ui.views.stockcharts.DemoView;

import org.xclcharts.chart.BubbleChart;
import org.xclcharts.chart.BubbleData;
import org.xclcharts.chart.SplineChart;
import org.xclcharts.chart.SplineData;
import org.xclcharts.common.DensityUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QuadrantChartView extends DemoView {
	
	private static final String TAG = "QuadrantChart01View";
	
	
	private BubbleChart chart = new BubbleChart();
	//分类轴标签集合
	private LinkedList<String> labels = new LinkedList<String>();
	private List<BubbleData> chartData = new LinkedList<BubbleData>();
	private SplineChart chartSp = new SplineChart();
	private LinkedList<SplineData> chartDataSp = new LinkedList<SplineData>();
	
	
	public QuadrantChartView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}	 
	
	public QuadrantChartView(Context context, AttributeSet attrs){
        super(context, attrs);   
        initView();
	 }
	 
	 public QuadrantChartView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		// chartLabels();

		 chartRender();
		 chartRenderSp();
		 

	 }
	public void setDataSource(SplineData splineData, List<String> chartLabels) {
		List<SplineData> splineDataList = new ArrayList<>();
		splineDataList.add(splineData);
	//	chart.setDataSource(splineDataList);
		chart.setCategories(chartLabels);
		chartDataSp.add(splineData);
		Double yMax=0d;
		Double yMin=0d;
		Double xMax=0d;
		Double xMin=0d;
		for (int i=0;i<splineData.getLineDataSet().size();i++){
			if (yMax<splineData.getLineDataSet().get(i).y){
				yMax=splineData.getLineDataSet().get(i).y;
			}if (yMin>splineData.getLineDataSet().get(i).y){
				yMin=splineData.getLineDataSet().get(i).y;
			}if (xMax<splineData.getLineDataSet().get(i).x){
				xMax=splineData.getLineDataSet().get(i).x;
			}if (xMin>splineData.getLineDataSet().get(i).x){
				xMin=splineData.getLineDataSet().get(i).x;
			}

		}
		//数据轴最大值
		chartSp.getDataAxis().setAxisMax(yMax+(yMax-yMin)/10);
		chartSp.getDataAxis().setAxisMin(yMin-(yMax-yMin)/10);
		//chart.getDataAxis().setAxisMin(0);
		//数据轴刻度间隔
		chartSp.getDataAxis().setAxisSteps((yMax-yMin)/10);
		//标签轴最大值
		chartSp.setCategoryAxisMax(xMax+(xMax-xMin)/10);
		//标签轴最小值
		chartSp.setCategoryAxisMin(xMin-(xMax-xMin)/10);
	}
	 
	 @Override
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
	        super.onSizeChanged(w, h, oldw, oldh);  
	       //图所占范围大小
	        chart.setChartRange(w,h);
	        chartSp.setChartRange(w,h);
	    }
	private void chartRender()
	{
		try {
			chart.setDataSource(chartData);
			//坐标系
			//数据轴最大值
			chart.getDataAxis().setAxisMax(100);
			//数据轴刻度间隔
			chart.getDataAxis().setAxisSteps(10);
			//标签轴最大值
			chart.setCategoryAxisMax(100);
			//标签轴最小值
			chart.setCategoryAxisMin(0);
			chart.getCategoryAxis().hideTickMarks();
			chart.getDataAxis().getAxisPaint().setStrokeWidth(3);
			chart.getCategoryAxis().getAxisPaint().setStrokeWidth(3);
			//象限设置
			chart.getPlotQuadrant().show(50d, 50d);
			chart.getPlotQuadrant().setBgColor(Color.rgb(32, 32, 32),
					Color.rgb(32, 32, 32),
					Color.rgb(32, 32, 32),
					Color.rgb(32, 32, 32));
			chart.getPlotQuadrant().getHorizontalLinePaint().setColor(Color.rgb(50, 50, 50));
			chart.getPlotQuadrant().getVerticalLinePaint().setColor(Color.rgb(50, 50, 50));
			chart.getPlotQuadrant().getHorizontalLinePaint().setStrokeWidth( DensityUtil.dip2px(1));
			chart.getPlotQuadrant().getVerticalLinePaint().setStrokeWidth( DensityUtil.dip2px(1));
			chart.getPlotLegend().hide();
			chart.getDataAxis().hide();
			chart.getCategoryAxis().hide();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}
	}

	private void chartRenderSp()
	{
		try {
		//	chartSp.setPadding(DensityUtil.dip2px(25), DensityUtil.dip2px(40),DensityUtil.dip2px(25), DensityUtil.dip2px(20));
			//数据源
			chartSp.setCategories(labels);
			chartSp.setDataSource(chartDataSp);
			chartSp.getPlotLegend().hide();
			chartSp.getDataAxis().hide();
			chartSp.getCategoryAxis().hide();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}
	}


	@Override
	public void render(Canvas canvas) {
		try{
			chart.render(canvas);
			chartSp.render(canvas);
		} catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}
}
