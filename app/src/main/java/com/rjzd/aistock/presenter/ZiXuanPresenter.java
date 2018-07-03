package com.rjzd.aistock.presenter;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.api.StockData;
import com.rjzd.aistock.api.StockTickData;
import com.rjzd.aistock.bean.StockBasic;
import com.rjzd.aistock.model.imp.StockModel;
import com.rjzd.aistock.ui.fragment.ZiXuanFragment;
import com.rjzd.commonlib.utils.LogUtil;

import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;

import static com.rjzd.aistock.Constants.DS_TAG_DEFAULT;
import static com.rjzd.aistock.Constants.DS_TAG_TICK_REFRESH;

/**
 * Created by Hition on 2016/12/21.
 */

public class ZiXuanPresenter extends BasePresenter {
    private ZiXuanFragment mView;
    private StockModel model;

    public ZiXuanPresenter(ZiXuanFragment view) {
        this.mView = view;
        model = new StockModel(this);
    }

    /**
     * 获取我的自选股列表
     */
    public void fillMyStocklist(){
        List<StockBasic> myStocks = model.getMyStocks();
        if(null == myStocks || myStocks.isEmpty()){
            mView.showDataEmptyView();
        }else{
            mView.hideEmptyView();
        }

        mView.fillData2View(myStocks);
    }

    /**
     * 删除自选股
     */
    public void deleteMyStock(String code,int position){
        model.deleteStockBycode(code);
        mView.deleteItem(position);
    }
    /**
     * 添加自选股
     */
    public void addMyStock(StockBasic stock){
      //  listener.onAddClick(stock);

            model.addStock(stock);

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
     * 刷新自选股
     */
    public void refreshStockData(List<String> codelist) {
        addSubscription(model.refresh(codelist));
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof StockData){
            StockData stockDt = (StockData) data;
            List<StockTickData> stocks = stockDt.get_data();
            ArrayList<StockBasic> stockBasics = new ArrayList<>();

            StockBasic stock ;
            for(StockTickData tick : stocks){
                stock = new StockBasic(tick.get_code(),tick.get_name());
                stock.setLatestPrice(tick.get_latestPrice());
                stock.setRange(tick.get_range());
                stock.setPredictionResult(tick.get_prediction().getValue());
                stock.setPredictDate(tick.get_predictDate());

                if(stockDt.get_status().getValue() == StatusCode.WAITING_FOR_PRICE.getValue()){
                    stock.setOpen(0);
                    stock.setLatestPrice(0);
                }else if(stockDt.get_status().getValue() == StatusCode.WAITING_FOR_OPEN.getValue()){
                    stock.setOpen(tick.get_open());
                }else{
                    double openPrice = tick.get_open();
                    if(openPrice > 0){
                        stock.setOpen(openPrice);
                    }else{
                        stock.setOpen(-2);
                    }
                }
                stockBasics.add(stock);
            }
            mView.fillData(stockBasics,DS_TAG_TICK_REFRESH);

            if(stockDt.get_status().getValue() == StatusCode.NON_TRADE_TIME.getValue()){ // 非交易时间和定价时间不需要刷新
                mView.cancelTimer();
            }
        }else if(data instanceof IsSuccess){
            mView.fillData(data, Constants.DS_TAG_DELETE_PORTFOLIO_SERVER);
        }else{
            mView.cancelTimer();
            mView.fillData(data,DS_TAG_DEFAULT);
        }
    }

    @Override
    public void onFailed(Throwable e) {
        if(e instanceof TException){
            mView.showFailedView();
        }else{
            LogUtil.e("hition==Zixuan",e.getMessage());
        }
    }
}
