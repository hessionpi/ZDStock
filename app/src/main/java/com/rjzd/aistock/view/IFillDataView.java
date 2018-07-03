package com.rjzd.aistock.view;


/**
 * Created by Hition on 2016/12/9.
 *
 *  适用于获取数据填充页面的activity
 */

public interface IFillDataView extends IBaseView{

    /**
     *
     * @param data        返回数据源
     * @param dsTag       返回数据源标签
     */
    void fillData(Object data,int dsTag);

    /**
     * 显示数据获取失败view
     */
    void showFailedView();

}
