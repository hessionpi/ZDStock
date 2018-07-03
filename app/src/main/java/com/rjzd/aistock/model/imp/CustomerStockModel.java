package com.rjzd.aistock.model.imp;

import com.rjzd.aistock.api.Condition;
import com.rjzd.aistock.api.FilterStockList;
import com.rjzd.aistock.api.service.StockServiceApi;
import com.rjzd.aistock.presenter.listener.BaseListener;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * 自定义选股model
 *
 * Created by Hition on 2017/2/10.
 */

public class CustomerStockModel extends BaseModel{

    public CustomerStockModel(BaseListener listener) {
        super(listener);
    }

    public Subscription getCustomerMatchStocks(final Condition cd){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<FilterStockList>(){
            @Override
            public void call(Subscriber<? super FilterStockList> subscriber) {
                FilterStockList stockResult = StockServiceApi.getInstance().getMatchStockResult(cd);
                subscriber.onNext(stockResult);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

}
