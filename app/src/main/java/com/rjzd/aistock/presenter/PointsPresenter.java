package com.rjzd.aistock.presenter;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.api.PointsRecordData;
import com.rjzd.aistock.api.PrivilegeData;
import com.rjzd.aistock.api.TaskStatuData;
import com.rjzd.aistock.api.UserPoints;
import com.rjzd.aistock.model.imp.UserModel;
import com.rjzd.aistock.view.IFillDataView;
import java.util.List;

/**
 * 积分presenter
 *
 * Created by Hition on 2017/7/27.
 */

public class PointsPresenter extends BasePresenter {

    private IFillDataView mView;
    private UserModel model;
    private int dataType;

    public PointsPresenter(IFillDataView mView){
        this.mView = mView;
        model = new UserModel(this);
    }

    public void getMyPoints(int userId){
        addSubscription(model.getMyPoints(userId));
    }

    public void getPrivilegeStatus(int uId, List<String> privilegeIds){
        addSubscription(model.getPrivilegeStatus(uId,privilegeIds));
    }

    public void getMyTaskStatus(int uId, List<String> taskTypeIds){
        addSubscription(model.getMyTaskStatus(uId,taskTypeIds));
    }

    public void unlockPrivilege(int uId, String privilegeId){
        dataType = Constants.DS_TAG_UNLOCKPRIVILEGE;
        addSubscription(model.unlockPrivilege(uId,privilegeId));
    }

    public void getPointsRecord(int userId, int pageNo,int numPerPage){
        addSubscription(model.getPointsRecord(userId,pageNo,numPerPage));
    }

    public void earnPointsByshare(int userId){
        dataType = Constants.DS_TAG_EARNPOINTS_BY_SHARE;
        addSubscription(model.earnPointsByshare(userId));
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof PrivilegeData){
            mView.fillData(data, Constants.DS_TAG_PRIVILEGESTATUS);
        }else if(data instanceof TaskStatuData){
            mView.fillData(data, Constants.DS_TAG_MYTASKSTATUS);
        }else if(data instanceof UserPoints){
            mView.fillData(data, Constants.DS_TAG_MY_POINTS);
        }else if(data instanceof IsSuccess){
            mView.fillData(data, dataType);
        }else if(data instanceof PointsRecordData){
            mView.fillData(data, Constants.DS_TAG_POINTS_RECORD);
        }else{
            mView.fillData(data, Constants.DS_TAG_DEFAULT);
        }
    }

    @Override
    public void onFailed(Throwable e) {
        super.onFailed(e);

    }
}
