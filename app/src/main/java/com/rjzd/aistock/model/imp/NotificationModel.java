package com.rjzd.aistock.model.imp;

import com.rjzd.aistock.api.AIWeeklyData;
import com.rjzd.aistock.api.AnnouncementData;
import com.rjzd.aistock.api.Count;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.api.service.NotificationServiceApi;
import com.rjzd.aistock.presenter.listener.BaseListener;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Hition on 2017/8/7.
 *
 * @author Hition
 *
 */
public class NotificationModel extends BaseModel {

    public NotificationModel(BaseListener listener) {
        super(listener);
    }

    /**
     * 获取未读消息总条数
     *
     * @param userId                                  用户id
     * @return Subscription
     */
    public Subscription getTotalUnread(final int userId){
        Observable.OnSubscribe<Count> subscribe = new Observable.OnSubscribe<Count>() {
            @Override
            public void call(Subscriber<? super Count> subscriber) {
                Count data = NotificationServiceApi.getInstance().getTotalUnread(userId);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取系统公告通知
     *
     * @param userId                                用户id
     * @param pageNo                                待请求页码
     * @param perPage                               每页请求条数
     * @return Subscription
     */
    public Subscription getAnnouncement(final int userId,final int pageNo,final int perPage){
        Observable.OnSubscribe<AnnouncementData> subscribe = new Observable.OnSubscribe<AnnouncementData>() {
            @Override
            public void call(Subscriber<? super AnnouncementData> subscriber) {
                AnnouncementData data = NotificationServiceApi.getInstance().getAnnouncement(userId,pageNo,perPage);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 设置消息为已读状态
     *
     * @param userId                                    用户id
     * @param newsId                                    消息id
     * @return Subscription
     */
    public Subscription setRead(final int userId,final String newsId){
        Observable.OnSubscribe<IsSuccess> subscribe = new Observable.OnSubscribe<IsSuccess>() {
            @Override
            public void call(Subscriber<? super IsSuccess> subscriber) {
                IsSuccess isSuccess = NotificationServiceApi.getInstance().setRead(userId,newsId);
                subscriber.onNext(isSuccess);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取AI周报
     *
     * @return Subscription
     */
    public Subscription getAIWeekly(){
        Observable.OnSubscribe<AIWeeklyData> subscribe = new Observable.OnSubscribe<AIWeeklyData>() {
            @Override
            public void call(Subscriber<? super AIWeeklyData> subscriber) {
                AIWeeklyData data = NotificationServiceApi.getInstance().getAIWeekly();
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

}
