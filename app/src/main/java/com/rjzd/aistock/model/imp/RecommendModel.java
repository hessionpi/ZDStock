package com.rjzd.aistock.model.imp;

import com.rjzd.aistock.api.PlateRecommendationData;
import com.rjzd.aistock.api.StockRecommendationData;
import com.rjzd.aistock.api.service.RecommendServiceApi;
import com.rjzd.aistock.presenter.listener.BaseListener;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * 推荐model类
 *
 * Created by Hition on 2017/8/9.
 */

public class RecommendModel extends BaseModel {

    public RecommendModel(BaseListener listener) {
        super(listener);
    }

    /**
     * 获取板块推荐结果
     *
     * @param pageIndex                             待请求页码
     * @param perPage                               每页请求条数
     * @return
     */
    public Subscription getPlateRecommendation(final int pageIndex, final int perPage){
        Observable.OnSubscribe<PlateRecommendationData> subscribe = new Observable.OnSubscribe<PlateRecommendationData>() {
            @Override
            public void call(Subscriber<? super PlateRecommendationData> subscriber) {
                PlateRecommendationData data = RecommendServiceApi.getInstance().getPlateRecommendation(pageIndex,perPage);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取股票推荐结果
     *
     * @param pageIndex                             待请求页码
     * @param perPage                               每页请求条数
     * @return
     */
    public Subscription getStockRecommendation(final int pageIndex, final int perPage){
        Observable.OnSubscribe<StockRecommendationData> subscribe = new Observable.OnSubscribe<StockRecommendationData>() {
            @Override
            public void call(Subscriber<? super StockRecommendationData> subscriber) {
                StockRecommendationData data = RecommendServiceApi.getInstance().getStockRecommendation(pageIndex,perPage);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }


}
