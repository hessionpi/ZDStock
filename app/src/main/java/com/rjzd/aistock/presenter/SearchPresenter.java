package com.rjzd.aistock.presenter;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.bean.SearchHistory;
import com.rjzd.aistock.bean.StockBasic;
import com.rjzd.aistock.model.imp.SearchStockModel;
import com.rjzd.aistock.view.IFillDataView;
import io.realm.RealmObject;

/**
 * Created by Hition on 2017/1/13.
 */

public class SearchPresenter extends BasePresenter {

    private IFillDataView mView;
    private SearchStockModel model;
    private int requestType;

    public SearchPresenter(IFillDataView mView){
        this.mView = mView;
        model = new SearchStockModel(this);
    }

    /**
     * 获取所有股票信息
     */
    /*public void getAllTheStock() {
        requestType = Constants.DS_TAG_ALL_STOCKS_CACHE;
        addSubscription(model.getAllStocks());
    }*/

    /**
     * 添加自选股
     */
    public void addPortfolio(StockBasic data) {
        model.addStock(data);
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
     * 添加浏览历史
     */
    public void insertHistory(RealmObject object){
        SearchHistory history = null;
        if(object instanceof StockBasic){
            StockBasic stock = (StockBasic) object;
            history = new SearchHistory(stock.getCode(),stock.getName());
        }else if(object instanceof SearchHistory){
            history = (SearchHistory) object;
        }
        model.insertHistory(history);
    }


    /**
     *  显示搜索历史数据
     */
    public void showHistory(){
        requestType = Constants.DS_TAG_SEARCH_STOCK_HISTORY;
        addSubscription(model.getSearchHistory());
    }

    /**
     *  搜索股票
     * @param selection 搜索条件
     */
    public void searchStock(String selection){
        requestType = Constants.DS_TAG_SEARCH_STOCKS;
        addSubscription(model.getSearchStocks(selection));

        // 如果输入为空则显示 历史记录
        /*if(TextUtils.isEmpty(selection)){
            showHistory();
            mView.hideSearchResultView();
            return ;
        }

        mView.hideHistoryView();
        mView.showSearchResultView();

        List<StockBasic> matchStockList = model.getSearchStocks(selection);
        if(matchStockList.isEmpty()){
            mView.showEmptyResult();
        }else{
            mView.hideEmptyResult();
        }

        mView.fillSearchResultData(matchStockList,true);*/
    }


    /**
     * 清空历史数据
     */
    public void clearHistory(){
        boolean isDelete = model.clearHistory();
        if(isDelete){
            showHistory();
        }
    }

    @Override
    public void onSuccess(Object data) {
        mView.fillData(data,requestType);
    }

    @Override
    public void onFailed(Throwable e) {
        mView.showFailedView();
    }

}
