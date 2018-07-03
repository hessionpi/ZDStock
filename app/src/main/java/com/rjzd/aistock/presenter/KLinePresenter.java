package com.rjzd.aistock.presenter;

import com.rjzd.aistock.api.KType;
import com.rjzd.aistock.model.imp.ChartModel;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.LogUtil;

import static com.rjzd.aistock.Constants.DS_TAG_DEFAULT;

/**
 * 绘制K线图 presenter
 *
 * Created by Hition on 2017/2/7.
 */

public class KLinePresenter extends BasePresenter{

    private IFillDataView mView;
    private ChartModel model;

    public KLinePresenter(IFillDataView mView){
        this.mView = mView;
        model = new ChartModel(this);
    }

    /**
     * 获取K线图数据
     * @param code  股票代码
     * @param tp    K线数据类型
     */
    public void getKData(String code, KType tp){
        addSubscription(model.getData2Chart(code,tp,""));
    }

    @Override
    public void onSuccess(Object kd) {
        mView.fillData(kd,DS_TAG_DEFAULT);
    }

    @Override
    public void onFailed(Throwable e) {
        LogUtil.e("hition===KLine",e.getMessage());
    }
}
