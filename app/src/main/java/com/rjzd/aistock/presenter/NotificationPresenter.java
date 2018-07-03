package com.rjzd.aistock.presenter;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.AIWeekly;
import com.rjzd.aistock.api.AIWeeklyData;
import com.rjzd.aistock.api.AnnouncementData;
import com.rjzd.aistock.api.Count;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.model.imp.NotificationModel;
import com.rjzd.aistock.view.IFillDataView;

/**
 * Created by Hition on 2017/8/7.
 */

public class NotificationPresenter extends BasePresenter{

    private IFillDataView mView;
    private NotificationModel model;

    public NotificationPresenter(IFillDataView mView){
        this.mView = mView;
        this.model = new NotificationModel(this);
    }

    public void getTotalUnread(int userId){
        addSubscription(model.getTotalUnread(userId));
    }

    public void getAnnouncement(int userId,int pageNo,int perPage){
        addSubscription(model.getAnnouncement(userId,pageNo,perPage));
    }

    public void setRead(int userId,String newsId){
        addSubscription(model.setRead(userId,newsId));
    }

    public void getAIWeekly(){
        addSubscription(model.getAIWeekly());
    }

    @Override
    public void onSuccess(Object data) {
        super.onSuccess(data);
        if(data instanceof Count){
            mView.fillData(data, Constants.DS_TAG_NOTIFICATION_UNREAD);
        }else if(data instanceof AnnouncementData){
            mView.fillData(data, Constants.DS_TAG_NOTIFICATION_ANNOUNCEMENT);
        }else if(data instanceof AIWeeklyData){
            mView.fillData(data, Constants.DS_TAG_AI_WEEKLY);
        }else if(data instanceof IsSuccess){
            mView.fillData(data, Constants.DS_TAG_MESSAGE_SET_READ);
        }else{
            mView.fillData(data,Constants.DS_TAG_DEFAULT);
        }
    }

    @Override
    public void onFailed(Throwable e) {
        super.onFailed(e);
    }
}
