package com.rjzd.aistock.model.imp;

import android.text.TextUtils;

import com.rjzd.aistock.api.BindType;
import com.rjzd.aistock.api.InviteData;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.api.OAuthInfo;
import com.rjzd.aistock.api.PointsRecordData;
import com.rjzd.aistock.api.PrivilegeData;
import com.rjzd.aistock.api.PushStatus;
import com.rjzd.aistock.api.SendCode;
import com.rjzd.aistock.api.TaskStatuData;
import com.rjzd.aistock.api.UserData;
import com.rjzd.aistock.api.UserPoints;
import com.rjzd.aistock.api.service.UserServiceApi;
import com.rjzd.aistock.bean.StockBasic;
import com.rjzd.aistock.presenter.listener.BaseListener;
import com.rjzd.aistock.utils.StockUtils;
import com.rjzd.commonlib.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * 用户model
 *
 * Created by Hition on 2017/7/21.
 */

public class UserModel extends BaseModel {

    public UserModel(BaseListener listener) {
        super(listener);
    }

    /**
     * 发送动态密码
     * @param mobile                           手机号
     * @return Subscription
     */
    public Subscription sendSMSCode(final String mobile){
        Observable.OnSubscribe<SendCode> subscribe = new Observable.OnSubscribe<SendCode>(){
            @Override
            public void call(Subscriber<? super SendCode> subscriber) {
                SendCode sc = UserServiceApi.getInstance().getSendCode(mobile);
                subscriber.onNext(sc);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 动态密码登录
     * @param mobile                            手机号
     * @param dynamicPwd                        动态密码
     * @return Subscription
     */
    public Subscription dynamicLogin(final String mobile, final String dynamicPwd){
        Observable.OnSubscribe<UserData> subscribe = new Observable.OnSubscribe<UserData>(){
            @Override
            public void call(Subscriber<? super UserData> subscriber) {
                UserData ud = UserServiceApi.getInstance().dynamicCodeLogin(mobile,dynamicPwd);
                subscriber.onNext(ud);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 第三方认证登录
     * @param info                              三方认证同步信息
     * @return Subscription
     */
    public Subscription oauthLogin(final OAuthInfo info){
        Observable.OnSubscribe<UserData> subscribe = new Observable.OnSubscribe<UserData>(){
            @Override
            public void call(Subscriber<? super UserData> subscriber) {
                UserData ud = UserServiceApi.getInstance().oauthLogin(info);
                subscriber.onNext(ud);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取用户id
     * @param userId                            用户id
     * @return Subscription
     */
    public Subscription getUserInfo(final int userId){
        Observable.OnSubscribe<UserData> subscribe = new Observable.OnSubscribe<UserData>(){
            @Override
            public void call(Subscriber<? super UserData> subscriber) {
                UserData uData = UserServiceApi.getInstance().getUserInfo(userId);
                subscriber.onNext(uData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取我的积分
     * @param userId                    用户id
     * @return Subscription
     */
    public Subscription getMyPoints(final int userId){
        Observable.OnSubscribe<UserPoints> subscribe = new Observable.OnSubscribe<UserPoints>(){
            @Override
            public void call(Subscriber<? super UserPoints> subscriber) {
                UserPoints uData = UserServiceApi.getInstance().getMyPoints(userId);
                subscriber.onNext(uData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 最近一天用户新增积分
     *
     * @param userId                           用户id
     * @return Subscription
     */
    public Subscription getRecentGainPoints(final int userId){
        Observable.OnSubscribe<UserPoints> subscribe = new Observable.OnSubscribe<UserPoints>(){
            @Override
            public void call(Subscriber<? super UserPoints> subscriber) {
                UserPoints uData = UserServiceApi.getInstance().getRecentGainPoints(userId);
                subscriber.onNext(uData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 绑定账号
     * @param userId                                    用户id
     * @param bindValue                                 绑定id
     * @param btype                                     绑定类型
     * @return Subscription
     */
    public Subscription bindAccount(final int userId, final String bindValue, final BindType btype){
        Observable.OnSubscribe<IsSuccess> subscribe = new Observable.OnSubscribe<IsSuccess>(){
            @Override
            public void call(Subscriber<? super IsSuccess> subscriber) {
                IsSuccess isBinding = UserServiceApi.getInstance().bindAccount(userId,bindValue,btype);
                subscriber.onNext(isBinding);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取服务器自选股列表
     * @param uId                               用户id
     * @return Subscription
     */
    public Subscription getPortfolioFromServer(final int uId){
        Observable.OnSubscribe<Boolean> subscribe = new Observable.OnSubscribe<Boolean>(){
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean isSuccess = false;
                //  从服务器获取自选股列表
                List<String> codeList = UserServiceApi.getInstance().getPortfolioFromServer(uId);

                // 本地自选股
                List<String> localPortfolio = getLocalCodes();
                // copy 一份本地自选股
                List<String> copyLocal= new ArrayList<>(Arrays.asList(new String[localPortfolio.size()]));
                Collections.copy(copyLocal, localPortfolio);
                if(!localPortfolio.isEmpty()){
                    //交集
                    copyLocal.retainAll(codeList);

                    //需要删除的
                    localPortfolio.removeAll(copyLocal);
                    if(!localPortfolio.isEmpty()){
                        for(String code : localPortfolio){
                            LogUtil.e("delete",code);
                        }
                        int size = localPortfolio.size();
                        String[] codeArray = localPortfolio.toArray(new String[size]);
                        mutilDelete(codeArray);
                    }

                    //需要添加的
                    codeList.removeAll(copyLocal);
                    if(!codeList.isEmpty()){
                        StockBasic insertStock ;
                        for(int i=codeList.size()-1;i>=0;i--){
                            String code = codeList.get(i);
                            String name = StockUtils.getStockNameByCode(code);
                            LogUtil.e("add",name+code);
                            if(!TextUtils.isEmpty(name)){
                                insertStock = new StockBasic(code,name);
                                insertStock.setHasAdd(1);
                                addStock(insertStock);
                            }
                        }
                    }
                    isSuccess = true;
                }
                subscriber.onNext(isSuccess);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取我的邀请好友
     *
     * @param uId                                   用户id
     * @param pageNo                                待请求页码
     * @param numPerPage                            每页条数
     * @return Subscription
     */
    public Subscription getMyInvite(final int uId, final int pageNo, final int numPerPage){
        Observable.OnSubscribe<InviteData> subscribe = new Observable.OnSubscribe<InviteData>(){
            @Override
            public void call(Subscriber<? super InviteData> subscriber) {
                InviteData invite = UserServiceApi.getInstance().getMyInvite(uId,pageNo,numPerPage);
                subscriber.onNext(invite);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取特权状态
     *
     * @param uId                               用户id
     * @param privilegeIds                      特权id列表
     * @return Subscription
     */
    public Subscription getPrivilegeStatus(final int uId, final List<String> privilegeIds){
        Observable.OnSubscribe<PrivilegeData> subscribe = new Observable.OnSubscribe<PrivilegeData>(){
            @Override
            public void call(Subscriber<? super PrivilegeData> subscriber) {
                PrivilegeData privilege = UserServiceApi.getInstance().getPrivilegeStatus(uId,privilegeIds);
                subscriber.onNext(privilege);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 解锁某一特权
     *
     * @param uId                               用户id
     * @param privilegeId                       特权id
     * @return Subscription
     */
    public Subscription unlockPrivilege(final int uId, final String privilegeId){
        Observable.OnSubscribe<IsSuccess> subscribe = new Observable.OnSubscribe<IsSuccess>(){
            @Override
            public void call(Subscriber<? super IsSuccess> subscriber) {
                IsSuccess isSuccess = UserServiceApi.getInstance().unlockPrivilege(uId,privilegeId);
                subscriber.onNext(isSuccess);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取任务状态
     *
     * @param uId                                   用户id
     * @param taskTypeIds                           任务id
     * @return Subscription
     */
    public Subscription getMyTaskStatus(final int uId, final List<String> taskTypeIds){
        Observable.OnSubscribe<TaskStatuData> subscribe = new Observable.OnSubscribe<TaskStatuData>(){
            @Override
            public void call(Subscriber<? super TaskStatuData> subscriber) {
                TaskStatuData taskStatus = UserServiceApi.getInstance().getMyTaskStatus(uId,taskTypeIds);
                subscriber.onNext(taskStatus);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取积分记录
     *
     * @param uId                                   用户id
     * @param pageNo                                待请求页码
     * @param numPerPage                               每页条数
     * @return Subscription
     */
    public Subscription getPointsRecord(final int uId, final int pageNo, final int numPerPage){
        Observable.OnSubscribe<PointsRecordData> subscribe = new Observable.OnSubscribe<PointsRecordData>(){
            @Override
            public void call(Subscriber<? super PointsRecordData> subscriber) {
                PointsRecordData data = UserServiceApi.getInstance().getPointsRecord(uId,pageNo,numPerPage);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }


    /**
     * 每日活跃赚积分
     *
     * @param uId                               用户id
     * @return Subscription
     */
    public Subscription earnPointsBydailyActive(final int uId){
        Observable.OnSubscribe<IsSuccess> subscribe = new Observable.OnSubscribe<IsSuccess>(){
            @Override
            public void call(Subscriber<? super IsSuccess> subscriber) {
                IsSuccess data = UserServiceApi.getInstance().earnPointsBydailyActive(uId);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 分享app给好友赚积分
     *
     * @param uId                       用户id
     * @return IsSuccess
     */
    public Subscription earnPointsByshare(final int uId){
        Observable.OnSubscribe<IsSuccess> subscribe = new Observable.OnSubscribe<IsSuccess>(){
            @Override
            public void call(Subscriber<? super IsSuccess> subscriber) {
                IsSuccess data = UserServiceApi.getInstance().earnPointsByshare(uId);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取消息推送状态
     * @param uId                             用户id
     * @return Subscription
     */
    public Subscription getPushStatus(final int uId){
        Observable.OnSubscribe<PushStatus> subscribe = new Observable.OnSubscribe<PushStatus>(){
            @Override
            public void call(Subscriber<? super PushStatus> subscriber) {
                PushStatus data = UserServiceApi.getInstance().getPushStatus(uId);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 通知推送设置
     *
     * @param uId                                        用户id
     * @param pushType                                      通知类型：PushNotification  PushAITransfer PushPredict PushInvite PushDailyBestPlate PushDailyBestStock PushAIWeekly
     * @param isNeedRemind                                  true 打开推送，false关闭推送
     * @return IsSuccess
     */
    public Subscription pushSettings(final int uId, final String pushType, final boolean isNeedRemind){
        Observable.OnSubscribe<IsSuccess> subscribe = new Observable.OnSubscribe<IsSuccess>(){
            @Override
            public void call(Subscriber<? super IsSuccess> subscriber) {
                IsSuccess data = UserServiceApi.getInstance().pushSettings(uId,pushType,isNeedRemind);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }


}
