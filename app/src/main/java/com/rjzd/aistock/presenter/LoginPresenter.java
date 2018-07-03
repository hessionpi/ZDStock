package com.rjzd.aistock.presenter;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.BindType;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.api.OAuthInfo;
import com.rjzd.aistock.api.PushStatus;
import com.rjzd.aistock.api.UserData;
import com.rjzd.aistock.api.UserPoints;
import com.rjzd.aistock.model.imp.UserModel;
import com.rjzd.aistock.view.IFillDataView;
import org.apache.thrift.TException;

/**
 * Created by Hition on 2017/3/3.
 */

public class LoginPresenter extends BasePresenter {

    private IFillDataView mView;
    private UserModel model;
    private int resultType;

    public LoginPresenter(IFillDataView mView){
        this.mView = mView;
        this.model = new UserModel(this);
    }

    public LoginPresenter(){
        this.model = new UserModel(this);
    }

    /**
     * 发送动态密码
     */
    public void sendDynamicPwd(String mobile){
        addSubscription(model.sendSMSCode(mobile));
    }

    /**
     *
     * @param mobile                                     手机号
     * @param dynamicPwd                                 动态密码
     */
    public void loginByDynamicPwd(String mobile,String dynamicPwd){
        addSubscription(model.dynamicLogin(mobile,dynamicPwd));
    }

    /**
     * 三方认证登录
     * @param oInfo    从第三方获取的用户信息
     */
    public void oauthLogin(OAuthInfo oInfo){
        addSubscription(model.oauthLogin(oInfo));
    }

    /**
     * 绑定手机号
     * @param uid                               用户id
     * @param bindValue                         绑定值  手机号 微信、qq、sina id等
     * @param type                              绑定类型
     */
    public void bindAccount(int uid,String bindValue,BindType type){
        resultType = Constants.DS_TAG_BINDING_ACCOUNT;
        addSubscription(model.bindAccount(uid,bindValue,type));
    }

    /**
     * 获取用户信息
     * @param uid                 用户id
     */
    public void getUserInfo(int uid){
        addSubscription(model.getUserInfo(uid));
    }

    /**
     * 获取服务器自选列表
     * @param uId                 用户id
     */
    public void getPortfolioFromServer(int uId){
        resultType = Constants.DS_TAG_GET_PORTFOLIOS_SERVER;
        addSubscription(model.getPortfolioFromServer(uId));
    }

    /**
     * 向服务器同步自选股
     * @param uId                                   用户id
     */
    public void synchronizePortfolio(int uId){
        resultType = Constants.DS_TAG_SYNCHRONIZE_PORTFOLIO;
        addSubscription(model.synchronizePortfolio(uId));
    }

    public void earnPointsBydailyActive(int userId){
        resultType = Constants.DS_TAG_EARNPOINTS_BY_DAILYACTIVE;
        addSubscription(model.earnPointsBydailyActive(userId));
    }

    public void getRecentGainPoints(int userId){
        addSubscription(model.getRecentGainPoints(userId));
    }

    public void getPushStatus(int uId){
        addSubscription(model.getPushStatus(uId));
    }

    public void pushSettings(int uId,String pushType,boolean isNeedRemind){
        resultType = Constants.DS_TAG_PUSH_SETTINGS;
        addSubscription(model.pushSettings(uId,pushType,isNeedRemind));
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof UserData){
            mView.fillData(data, Constants.DS_TAG_LOGIN);
        }else if(data instanceof IsSuccess){
            mView.fillData(data,resultType);
        }else if(data instanceof PushStatus){
            mView.fillData(data,Constants.DS_TAG_PUSH_STATUS_GET);
        }else if(data instanceof Boolean){
            boolean isSuccess = (boolean) data;
            switch(resultType){
                case Constants.DS_TAG_GET_PORTFOLIOS_SERVER:
                    if(!isSuccess){
                        mView.showToast("获取自选股失败了……");
                    }
                    break;

                case Constants.DS_TAG_SYNCHRONIZE_PORTFOLIO: // 同步：先将本地自选股给server，由server将合并后列表返回
                    if(!isSuccess){
                        mView.showToast("同步自选股失败了……");
                    }
                    break;

                case Constants.DS_TAG_EARNPOINTS_BY_DAILYACTIVE:
                    if(!isSuccess){
                        mView.showToast("每日活跃加积分成功");
                    }
                    break;

                default:
                    mView.fillData(data,resultType);
                    break;
            }
        }else if(data instanceof UserPoints){
            mView.fillData(data,Constants.DS_TAG_RECENTGAINPOINTS);
        }else{
            mView.fillData(data,Constants.DS_TAG_DEFAULT);
        }
    }

    @Override
    public void onFailed(Throwable e) {
        if(e instanceof TException){
            mView.showFailedView();
        }
    }
}
