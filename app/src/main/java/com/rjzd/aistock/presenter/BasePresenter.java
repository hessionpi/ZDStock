package com.rjzd.aistock.presenter;

import com.rjzd.aistock.presenter.listener.BaseListener;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Hition on 2016/12/21.
 */

public class BasePresenter implements IPresenter,BaseListener {

    protected CompositeSubscription mCompositeSubscription;

    /**
     * 防止由于没有及时取消，Activity/Fragment无法销毁导致的内存泄露
     */
    @Override
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void addSubscription(Subscription subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscriber);
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onFailed(Throwable e) {
//        LogUtil.e(e.getMessage());
    }

}
