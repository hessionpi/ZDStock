package com.rjzd.aistock.presenter;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.StockData;
import com.rjzd.aistock.bean.StockBasic;
import com.rjzd.aistock.model.imp.StockModel;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.LogUtil;

import org.apache.thrift.TException;

import java.util.List;

/**
 * 股票/指数详情presenter
 *
 * Created by Hition on 2017/1/17.
 */

public class StockDetailPresenter extends BasePresenter{

    private IFillDataView mView;
    private StockModel model;

    public StockDetailPresenter(IFillDataView mView) {
        this.mView = mView;
        model = new StockModel(this);
    }

    /**
     * 删除自选股
     */
    public void deleteStock(String code) {
        model.deleteStockBycode(code);
    }

    /**
     * 加为自选股
     */
    public void addStock(String stockCode,String stockName) {
        StockBasic stock = new StockBasic(stockCode,stockName);
        stock.setHasAdd(1);
        model.addStock(stock);
    }

    /**
     * 向服务器添加自选股
     * @param uId                   用户id
     * @param code                  股票代码
     */
    public void addPortfolio2Server(int uId,String code){
        model.addPortfolio2Server(uId,code);
    }

    /**
     * 从服务器删除自选股
     * @param uId                   用户id
     * @param code                  股票代码
     */
    public void deletePortfolioFromServer(int uId,String code){
        addSubscription(model.deletePortfolio(uId,code));
    }

    /**
     * 是否已经添加为自选股
     * @param code                          股票代码
     * @return boolean
     */
    public boolean isZixuan(String code){
        return model.isZixuan(code);
    }

    /**
     * 获取股票详情
     */
    public void refreshDataDetail(List<String> codelist) {
        addSubscription(model.refresh(codelist));
    }
    /**
     * 是否是交易日
     */
    public void isTradeDate() {
        addSubscription(model.isTradeDate());
    }
    @Override
    public void onSuccess(Object data) {
        if (data instanceof StockData){
            mView.fillData(data,Constants.DS_TAG_REFRESH_HANDICAP);}
        else{
            mView.fillData(data, Constants.DS_TAG_IS_TRADE_DATE);
        }
    }

    @Override
    public void onFailed(Throwable e) {
        if(e instanceof TException){
            mView.showFailedView();
        }else{
            LogUtil.e("hition===StockDetail",e.getMessage());
        }
    }
}
