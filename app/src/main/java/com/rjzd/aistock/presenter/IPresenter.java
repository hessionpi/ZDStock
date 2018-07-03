package com.rjzd.aistock.presenter;

import rx.Subscription;

/**
 * Created by Hition on 2016/12/21.
 */

public interface IPresenter {

    // 取消订阅，防止出现内存泄漏
    void onUnsubscribe();

    // 订阅事件
    void addSubscription(Subscription subscriber);
}
