package com.rjzd.aistock.presenter;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.KData;
import com.rjzd.aistock.api.KType;
import com.rjzd.aistock.model.imp.ChartModel;
import com.rjzd.aistock.view.IFillDataView;

import static com.rjzd.aistock.Constants.DS_TAG_DEFAULT;

/**
 * Created by Hition on 2017/2/7.
 */

public class RealTimePresenter extends BasePresenter{

    private IFillDataView mView;
    private ChartModel model;
    public RealTimePresenter(IFillDataView mView){
        this.mView = mView;
        model = new ChartModel(this);

    }

    /**
     * 获取分时图数据
     *
     * @param code
     */
    public void getRealmTime(String code, String startMinute){
        addSubscription(model.getData2Chart(code, KType.REALTIME,startMinute));
    }
    /**
     * 是否是交易日
     */
    public void isTradeDate() {
        addSubscription(model.isTradeDate());
    }
    @Override
    public void onSuccess(Object kd) {
        if (kd instanceof KData){
        mView.fillData(kd,DS_TAG_DEFAULT);}
        else{
            mView.fillData(kd, Constants.DS_TAG_IS_TRADE_DATE);
        }


    }

    @Override
    public void onFailed(Throwable e) {
        super.onFailed(e);
    }
}
