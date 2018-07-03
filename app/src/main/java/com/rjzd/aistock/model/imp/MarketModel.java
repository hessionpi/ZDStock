package com.rjzd.aistock.model.imp;

import com.rjzd.aistock.api.MarketData;
import com.rjzd.aistock.api.PlateFlag;
import com.rjzd.aistock.api.RangeFlag;
import com.rjzd.aistock.api.service.StockServiceApi;
import com.rjzd.aistock.presenter.listener.BaseListener;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Hition on 2017/3/9.
 */

public class MarketModel extends BaseModel{

    public MarketModel(BaseListener listener) {
        super(listener);
    }

    /**
     * 获取大盘数据
     * @param rf  rise or fall
     * @param pf  industry concept area
     * @param pageNo 待请求页码
     * @param pageSize 每页显示数据条数
     * @return      Subscription
     */
    public Subscription getMarketData(final RangeFlag rf, final PlateFlag pf,final int pageNo,final int pageSize){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<MarketData>(){
            @Override
            public void call(Subscriber<? super MarketData> subscriber) {
                MarketData mData = StockServiceApi.getInstance().getMarketData(rf,pf,pageNo,pageSize);
                subscriber.onNext(mData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     *
     * @param rf          rise or fall
     * @param pf          industry or concept or area
     * @param lableName   the name of industry or concept or area
     * @param pageNo 待请求页码
     * @param pageSize 每页显示数据条数
     * @return            Subscription
     */
    public Subscription getLeaderStocks(final RangeFlag rf, final PlateFlag pf, final String lableName, final int pageNo,final int pageSize){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<MarketData>(){
            @Override
            public void call(Subscriber<? super MarketData> subscriber) {
                MarketData mData = StockServiceApi.getInstance().getLeaders(rf,pf,lableName,pageNo,pageSize);
                subscriber.onNext(mData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

}
