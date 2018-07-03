package com.rjzd.aistock.model.imp;

import com.rjzd.aistock.api.KData;
import com.rjzd.aistock.api.KType;
import com.rjzd.aistock.api.service.StockServiceApi;
import com.rjzd.aistock.presenter.listener.BaseListener;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * 图标数据Model  包含分时图 K线图（日K   周K  月K）
 *
 * Created by Hition on 2017/2/7.
 */

public class ChartModel extends BaseModel{

    public ChartModel(BaseListener listener) {
        super(listener);
    }

    /**
     * 获取分时图数据
     * @param code  股票代码
     * @param type  图表类型   KType.REALTIME-分时    KType.DAY-日K    KType.WEEK-周K   KType.MONTH-月K
     */
    public Subscription getData2Chart(final String code, final KType type,final String startMinute){
        Observable.OnSubscribe onSubscribe = new Observable.OnSubscribe<KData>(){
            @Override
            public void call(Subscriber<? super KData> subscriber) {
                KData kData = StockServiceApi.getInstance().getKData(code, type,startMinute);
                subscriber.onNext(kData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(onSubscribe);
    }
    /**
     * 判断是否是交易日
     * @return
     */
    public Subscription isTradeDate() {
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean data = StockServiceApi.getInstance().isTradeDate();
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

}
