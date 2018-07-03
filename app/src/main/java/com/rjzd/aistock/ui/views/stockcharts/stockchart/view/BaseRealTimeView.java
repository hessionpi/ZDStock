package com.rjzd.aistock.ui.views.stockcharts.stockchart.view;

import com.rjzd.aistock.ui.views.stockcharts.stockchart.bean.FenshiDataResponse;

/**
 * Created by Administrator on 2017/3/13.
 */

public interface BaseRealTimeView {

    /**
     * 获取数据重绘图表
     *
     * @param d 分时绘制所需要数据
     */
     void fillDataAndInvalidate(FenshiDataResponse d);
     void cancelStockViewTimer();
}
