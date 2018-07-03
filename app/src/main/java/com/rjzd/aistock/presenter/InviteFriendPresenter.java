package com.rjzd.aistock.presenter;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.model.imp.UserModel;
import com.rjzd.aistock.view.IFillDataView;

/**
 * Created by Administrator on 2017/8/9.
 */

public class InviteFriendPresenter extends BasePresenter{
    private IFillDataView mView;
    private UserModel model;

    public InviteFriendPresenter(IFillDataView mView){
        this.mView = mView;
        this.model = new UserModel(this);
    }

    public InviteFriendPresenter(){
        this.model = new UserModel(this);
    }

    /**
     * 发送动态密码
     */
    public void getMyInvite(final int uId, final int pageNo, final int numPerPage){
        addSubscription(model.getMyInvite(uId,pageNo,numPerPage));
    }

    @Override
    public void onSuccess(Object data) {
        super.onSuccess(data);
      //  if ()
        mView.fillData(data, Constants.DS_TAG_DEFAULT);

    }

    @Override
    public void onFailed(Throwable e) {
        super.onFailed(e);
    }
}
