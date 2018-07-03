package com.rjzd.aistock.model.imp;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.Stock;
import com.rjzd.aistock.api.StockList;
import com.rjzd.aistock.bean.SearchHistory;
import com.rjzd.aistock.bean.StockBasic;
import com.rjzd.aistock.db.DBHelper;
import com.rjzd.aistock.presenter.listener.BaseListener;
import com.rjzd.aistock.utils.FileUtil;
import com.rjzd.commonlib.utils.MathDataUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/** 搜索股票
 *
 * Created by Hition on 2017/1/13.
 */

public class SearchStockModel extends BaseModel{

    private List<Stock> all;

    {
        all = loadAllStocks();
    }

    public SearchStockModel(BaseListener listener) {
        super(listener);
    }

    /**
     * 获取所有股票
     */
    private List<Stock> loadAllStocks(){
        String json =  FileUtil.readFile(Constants.STOCK_LOCAL_FILE);
        if(!TextUtils.isEmpty(json)){
            Gson gson = new Gson ();
            StockList data =  gson.fromJson(json,StockList.class);
            return data.get_data();
        }
        return null;
    }

    /**
     * 添加到历史记录
     *
     * @param stock 搜索历史
     */
    public void insertHistory(SearchHistory stock){
        DBHelper.getInstance().insert2History(stock);
    }

    /**
     * 获取搜索历史
     *
     * @return Subscription
     */
    public Subscription getSearchHistory(){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<List<SearchHistory>>() {
            @Override
            public void call(Subscriber<? super List<SearchHistory>> subscriber) {
                List<SearchHistory> historyList = DBHelper.getInstance().getStockHistory();
                List<SearchHistory> newHistory = new ArrayList<>();
                for(SearchHistory history : historyList){
                    if(DBHelper.getInstance().isZixuan(history.getCode())){
                        history.setHasAdd(1);
                        newHistory.add(history);
                    }
                }
                subscriber.onNext(historyList);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 根据输入搜索条件匹配搜索结果
     * @param require         搜索条件
     * @return Subscription
     */
    public Subscription getSearchStocks(final String require) {
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<List<StockBasic>>() {
            @Override
            public void call(Subscriber<? super List<StockBasic>> subscriber) {
                Map<String,StockBasic> matchMap = new HashMap<>();
                List<StockBasic> matchStockList = null;
                if (null != all && !all.isEmpty() && !TextUtils.isEmpty(require.trim())) {
                    StockBasic sto;
                    matchStockList= new ArrayList<>();
                    for (Stock s : all) {
                        if(MathDataUtil.isInteger(require)){
                            if(s.get_code().endsWith(require.trim())){
                                sto = new StockBasic(s.get_code(),s.get_name());
                                sto.setAbbreviation(s.get_abbreviation());
                                if(DBHelper.getInstance().isZixuan(s.get_code())){
                                    sto.setHasAdd(1);
                                }else{
                                    sto.setHasAdd(0);
                                }

                                matchStockList.add(sto);
                                matchMap.put(sto.getCode(),sto);
                                if(require.length() < 6 && matchStockList.size()==10){
                                    break;
                                }

                            }
                        }else{
                            if(s.get_abbreviation().toLowerCase().replace(" ","").startsWith(require.toLowerCase().trim())||
                                    s.get_name().replace(" ","").contains(require.trim())){
                                sto = new StockBasic(s.get_code(),s.get_name());
                                sto.setAbbreviation(s.get_abbreviation());
                                if(DBHelper.getInstance().isZixuan(s.get_code())){
                                    sto.setHasAdd(1);
                                }else{
                                    sto.setHasAdd(0);
                                }

                                matchStockList.add(sto);
                                matchMap.put(sto.getCode(),sto);
                                if(require.length()<4 && matchStockList.size()==10){
                                    break;
                                }
                            }
                        }
                    }

                    if(matchStockList.size() < 10 ){
                        for(Stock targetStock : all){
                            if(matchMap.containsKey(targetStock.get_code())){
                                continue ;
                            }
                            if (targetStock.get_code().contains(require.trim()) || targetStock.get_abbreviation().toLowerCase().replace(" ","").contains(require.toLowerCase().trim())) {
                                sto = new StockBasic(targetStock.get_code(),targetStock.get_name());
                                sto.setAbbreviation(targetStock.get_abbreviation());
                                if(DBHelper.getInstance().isZixuan(targetStock.get_code())){
                                    sto.setHasAdd(1);
                                }else{
                                    sto.setHasAdd(0);
                                }

                                matchStockList.add(sto);
                                if(MathDataUtil.isInteger(require)){
                                    if(require.length() < 6 && matchStockList.size()==10){
                                        break;
                                    }
                                }else{
                                    if(require.length() < 4 && matchStockList.size()==10){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                subscriber.onNext(matchStockList);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }


    /**
     * 清空历史
     */
    public boolean clearHistory(){
        return DBHelper.getInstance().clearHistory();
    }

}
