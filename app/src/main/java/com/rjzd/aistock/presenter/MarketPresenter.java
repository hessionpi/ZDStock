package com.rjzd.aistock.presenter;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.PlateFlag;
import com.rjzd.aistock.api.RangeFlag;
import com.rjzd.aistock.model.imp.MarketModel;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.LogUtil;

import org.apache.thrift.TException;

/**
 * Created by Hition on 2017/3/9.
 */

public class MarketPresenter extends BasePresenter {

    private IFillDataView mView;
    private MarketModel model;
    private int dataSourceType = -1;

    public MarketPresenter(IFillDataView mView){
        this.mView = mView;
        this.model = new MarketModel(this);
    }

    /**
     * 获取大盘数据
     */
    public void getMarketData(RangeFlag rf, PlateFlag pf,int pageNo,int pageSize){
        dataSourceType = Constants.DS_TAG_MARKET;
        addSubscription(model.getMarketData(rf,pf,pageNo,pageSize));
    }

    /**
     * 获取龙头股数据
     */
    public void getLeaderStocks(RangeFlag rf, PlateFlag pf,String labelName,int pageNo,int pageSize){
        dataSourceType = Constants.DS_TAG_LEADERSTOCK;
        addSubscription(model.getLeaderStocks(rf,pf,labelName,pageNo,pageSize));
    }

    @Override
    public void onSuccess(Object data) {
        mView.fillData(data,dataSourceType);
    }

    @Override
    public void onFailed(Throwable e) {
        if(e instanceof TException){
            mView.showFailedView();
        }else{
            LogUtil.e("hition===markets",e.getMessage());
        }
    }
}
