package com.rjzd.aistock.view;

import com.rjzd.aistock.api.BasicStock;
import com.rjzd.aistock.api.StockTickData;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */

public interface IDetailView {
    /**
     * 重置按钮文字为 添加自选/删除自选
     *
     * @param txtRes
     */
     void resetTextOfButton(int txtRes);
    //更新股票详情
     void refreshStocksDetails(List<StockTickData> stocks);

     void cancelTimer();
}
