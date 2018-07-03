package com.rjzd.aistock.model.imp;

import com.rjzd.aistock.api.AppUpdate;
import com.rjzd.aistock.api.service.SystemServiceApi;
import com.rjzd.aistock.presenter.listener.BaseListener;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Hition on 2017/5/8.
 */

public class AppUpdateModel extends BaseModel {

    public AppUpdateModel(BaseListener listener) {
        super(listener);
    }

    /**
     * 版本检查与更新
     * @param packageName                    包名
     * @param versionCode                    版本号
     * @return Subscription
     */
    public Subscription checkVersion(final String packageName, final int versionCode){
        Observable.OnSubscribe subscribe =  new Observable.OnSubscribe<AppUpdate>(){
            @Override
            public void call(Subscriber<? super AppUpdate> subscriber) {
                AppUpdate preData = SystemServiceApi.getInstance().checkUpdate(packageName,versionCode);
                subscriber.onNext(preData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

}
