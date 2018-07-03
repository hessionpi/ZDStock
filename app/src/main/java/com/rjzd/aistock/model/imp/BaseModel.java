package com.rjzd.aistock.model.imp;

import android.text.TextUtils;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.api.service.UserServiceApi;
import com.rjzd.aistock.bean.StockBasic;
import com.rjzd.aistock.db.DBHelper;
import com.rjzd.aistock.presenter.listener.BaseListener;
import com.rjzd.aistock.utils.SPUtils;
import com.rjzd.aistock.utils.StockUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Hition on 2017/3/15.
 */

public class BaseModel {

    private BaseListener listener;

    public BaseModel(BaseListener listener){
        this.listener = listener;
    }

    /**
     * 获取我的自选股（本地）
     */
    public List<StockBasic> getMyStocks(){
        return  DBHelper.getInstance().getPortfolio();
    }

    public List<String> getLocalCodes(){
        List<String> localCodeList = new ArrayList<>();
        List<StockBasic> localList = getMyStocks();
        if(null != localList && !localList.isEmpty()){
            for(StockBasic stock : localList){
                localCodeList.add(stock.getCode());
            }
        }
        return localCodeList;
    }


    /**
     * 添加自选股（本地）
     */
    public void addStock(StockBasic data){
        DBHelper.getInstance().insert2Stock(data);
        Constants.isNeedNotify = true;
    }

    /**
     * 向服务器添加自选股
     * @param uId                           用户id
     * @param code                          股票代码
     * @return Subscription
     */
    public Subscription addPortfolio2Server(final int uId, final String code){
        Observable.OnSubscribe<IsSuccess> subscribe = new Observable.OnSubscribe<IsSuccess>(){
            @Override
            public void call(Subscriber<? super IsSuccess> subscriber) {
                IsSuccess codeList = UserServiceApi.getInstance().addPortfolio(uId,code);
                subscriber.onNext(codeList);
                subscriber.onCompleted();
            }
        };
        return this.getSubscription(subscribe);
    }


    /**
     * 根据股票代码删除自选股（本地）
     *
     * @param code                      股票代码
     */
    public void deleteStockBycode(String code){
        DBHelper.getInstance().delete(code);
        Constants.isNeedNotify = true;
    }

    /**
     * 处理多条删除本地
     *
     * @param codes
     */
    public void mutilDelete(String[] codes){
        DBHelper.getInstance().mutilDelete(codes);
    }

    /**
     * 删除服务器上自选股
     *
     * @param uId           用户id
     * @param code          股票代码
     */
    public Subscription deletePortfolio(final int uId, final String code){
        Observable.OnSubscribe<IsSuccess> subscribe = new Observable.OnSubscribe<IsSuccess>(){
            @Override
            public void call(Subscriber<? super IsSuccess> subscriber) {
                IsSuccess codeList = UserServiceApi.getInstance().deletePortfolio(uId,code);
                subscriber.onNext(codeList);
                subscriber.onCompleted();
            }
        };
        return this.getSubscription(subscribe);
    }

    /**
     * 将本地自选股向服务器同步
     * @param uId                           用户id
     * @return Subscription
     */
    public Subscription synchronizePortfolio(final int uId){
        Observable.OnSubscribe<Boolean> subscribe = new Observable.OnSubscribe<Boolean>(){
            @Override
            public void call(Subscriber<? super Boolean > subscriber) {
                boolean isSuccess = false;
                List<String> localCodes = new ArrayList<>();
                List<StockBasic> localPortfolios = getMyStocks();
                if(null!=localPortfolios && !localPortfolios.isEmpty()){
                    for(int i=localPortfolios.size()-1;i>=0;i--){
                        localCodes.add(localPortfolios.get(i).getCode());
                    }
                }

                // 服务器获取合并后股票列表
                List<String>  mergeCodes = UserServiceApi.getInstance().synchronizePortfolio(uId,localCodes);

                if(null != mergeCodes && !mergeCodes.isEmpty()){
                    // 将服务器上有的本地没有的股票添加到本地自选股中
                    StockBasic stock ;
                    for(String code : mergeCodes){
                        if(isZixuan(code)){
                            continue ;
                        }

                        String name = StockUtils.getStockNameByCode(code);
                        if(!TextUtils.isEmpty(name)){
                            stock = new StockBasic(code,name);
                            stock.setHasAdd(1);
                            addStock(stock);
                        }
                    }
                    isSuccess = true;
                    SPUtils.put("has_synchronize",true);
                }
                subscriber.onNext(isSuccess);
                subscriber.onCompleted();
            }
        };
        return this.getSubscription(subscribe);
    }

    /**
     * 查询是否加入自选股
     */
    public boolean isZixuan(String code){
        return !TextUtils.isEmpty(code) && DBHelper.getInstance().isZixuan(code);
    }

    protected Subscription getSubscription(Observable.OnSubscribe<?> onsub){
        return Observable.create(onsub).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(e);
                    }

                    @Override
                    public void onNext(Object kData) {
                        listener.onSuccess(kData);
                    }
                });
    }

}
