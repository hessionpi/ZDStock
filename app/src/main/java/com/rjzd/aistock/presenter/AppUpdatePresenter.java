package com.rjzd.aistock.presenter;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.AppUpdate;
import com.rjzd.aistock.model.imp.AppUpdateModel;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.LogUtil;

import org.apache.thrift.TException;

/**
 * Created by Hition on 2017/5/8.
 */

public class AppUpdatePresenter extends BasePresenter {

    private IFillDataView mView;
    private AppUpdateModel model;

    public AppUpdatePresenter(IFillDataView mView){
        this.mView = mView;
        model = new AppUpdateModel(this);
    }

    /**
     * 版本检查与更新
     * @param packageName                 应用包名
     * @param versionCode                 应用版本号
     */
    public void checkUpdate(String packageName,int versionCode){
        addSubscription(model.checkVersion(packageName,versionCode));
    }

    @Override
    public void onSuccess(Object data) {
        super.onSuccess(data);
        if(data instanceof AppUpdate){
            mView.fillData(data, Constants.DS_TAG_CHECK_VERSION);
        }
    }

    @Override
    public void onFailed(Throwable e) {
        if(e instanceof TException){
            mView.showFailedView();
        }else{
            LogUtil.e("hition==update",e.getMessage());
        }

    }
}
