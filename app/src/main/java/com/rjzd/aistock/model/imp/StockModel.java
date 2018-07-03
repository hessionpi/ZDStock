package com.rjzd.aistock.model.imp;

import com.rjzd.aistock.api.NewsList;
import com.rjzd.aistock.api.StockData;
import com.rjzd.aistock.api.service.StockServiceApi;
import com.rjzd.aistock.presenter.listener.BaseListener;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Hition on 2017/4/12.
 */

public class StockModel extends BaseModel {

    public StockModel(BaseListener listener) {
        super(listener);
    }

    /**
     * 调用后台接口api 获取数据
     */
    public Subscription getAllStocks2Local() {
        Observable.OnSubscribe<String> subscribe = new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String all2Json = StockServiceApi.getInstance().getAllStockAndCash();

                subscriber.onNext(all2Json);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }
    /**
     * 判断是否是交易日
     * @return Subscription
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
    /**
     * 刷新股票
     * @param codelist             代码列表
     * @return Subscription
     */
    public Subscription refresh(final List<String> codelist) {
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<StockData>() {
            @Override
            public void call(Subscriber<? super StockData> subscriber) {
                StockData data = StockServiceApi.getInstance().refreshStock(codelist);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取最新资讯
     *
     * @param pageSize                             新闻条数
     * @return Subscription
     */
    public Subscription getLatestNews(final int pageSize){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<NewsList>() {
            @Override
            public void call(Subscriber<? super NewsList> subscriber) {
                NewsList data = StockServiceApi.getInstance().getLatestNews(pageSize);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

}
