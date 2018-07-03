package com.rjzd.aistock.api.service;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.AttentionFlag;
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
import com.rjzd.aistock.api.UserService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

import java.util.List;

/**
 * 所有用户相关操作接口
 *
 * Created by Hition on 2017/1/11.
 */

public class UserServiceApi {

    private static UserServiceApi instance;

    private UserServiceApi(){ }

    public static UserServiceApi getInstance(){
        if(null == instance){
            synchronized (UserServiceApi.class){
                if (null == instance){
                    instance = new UserServiceApi();
                }
            }
        }
        return instance;
    }

    /**
     * 获取动态密码
     */
    public SendCode getSendCode(String mobile){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        SendCode cData = null;
        try {
            transport.open();
            cData = client.getSendCode(mobile);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return cData;
    }

    /**
     * 手机号 + 动态密码 登录
     */
    public UserData dynamicCodeLogin(String mobile,String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        UserData udata = null;
        try {
            transport.open();
            udata = client.dynamicLogin(mobile,code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return udata;
    }

    /**
     * 第三方认证登录
     * @param params                                 授权成功后登录，同步的参数
     * @return UserData
     */
    public UserData oauthLogin(OAuthInfo params){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        UserData udata = null;
        try {
            transport.open();
            udata = client.oauthLogin(params);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return udata;
    }

    /**
     * 绑定账号
     * @param userId                             用户id
     * @param bindValue                          绑定值（微信id、QQid、Sinaid、手机号）
     * @param bindType                           微信、QQ、Sin、手机
     * @return IsSuccess                         成功与否标识
     */
    public IsSuccess bindAccount(int userId,String bindValue,BindType bindType){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        IsSuccess isBinding = null;
        try {
            transport.open();
            isBinding = client.binding(userId,bindValue,bindType);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return isBinding;
    }


    /**
     * 添加或取消关注AI机器人
     * @param aiId                                     机器人id
     * @param userId                                   用户id
     * @param addOrCancel                              添加或取消关注
     * @return IsSuccess
     */
    public IsSuccess addOrCancelAttention(String aiId,int userId,AttentionFlag addOrCancel){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        IsSuccess isBinding = null;
        try {
            transport.open();
            isBinding = client.addOrCancelAttention(aiId,userId,addOrCancel);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return isBinding;
    }

    /**
     * 获取用户信息
     * @param uId                   用户id
     * @return UserData             用户基本信息
     */
    public UserData getUserInfo(int uId){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        UserData uData = null;
        try {
            transport.open();
            uData = client.getUserInfo(uId);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return uData;
    }

    /**
     * 获取我的积分
     *
     * @param uId                   用户id
     * @return UserPoints
     */
    public UserPoints getMyPoints(int uId){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        UserPoints uPoints = null;
        try {
            transport.open();
            uPoints = client.getMyPoints(uId);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return uPoints;
    }

    /**
     * 最近一天用户新增积分
     *
     * @param uId                           用户id
     * @return UserPoints
     */
    public UserPoints getRecentGainPoints(int uId){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        UserPoints uPoints = null;
        try {
            transport.open();
            uPoints = client.getRecentGainPoints(uId);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return uPoints;
    }

    /**
     * 获取服务器自选股列表
     *
     * @param uId                       用户id
     * @return List<String>             自选股列表
     */
    public List<String> getPortfolioFromServer(int uId){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        List<String> codeList = null;
        try {
            transport.open();
            codeList = client.getPortfolio(uId);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return codeList;
    }

    /**
     * 删除服务器上自选股
     *
     * @param uId           用户id
     * @param code          股票代码
     */
    public IsSuccess deletePortfolio(int uId,String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        IsSuccess result = null;
        try {
            transport.open();
            result = client.deletePortfolio(uId,code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return result;
    }

    /**
     * 向服务器添加自选股
     *
     * @param uId                       用户id
     * @param code                      股票代码
     * @return IsSuccess                是否成功标识
     */
    public IsSuccess addPortfolio(int uId,String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        IsSuccess result = null;
        try {
            transport.open();
            result = client.addPortfolio(uId,code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return result;
    }

    /**
     * 向服务器同步自选股列表
     *
     * @param uId                           用户id
     * @param codeList                      股票代码列表
     * @return List<String>                 合并后自选股代码列表
     */
    public List<String> synchronizePortfolio(int uId,List<String> codeList){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        List<String> mergeCodes = null;
        try {
            transport.open();
            mergeCodes = client.synchronizePortfolio(uId,codeList);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return mergeCodes;
    }

    /**
     * 获取我的邀请好友
     *
     * @param uId                                   用户id
     * @param pageNo                                待请求页码
     * @param numPerPage                            每页条数
     * @return InviteData
     */
    public InviteData getMyInvite(int uId,int pageNo,int numPerPage){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        InviteData data = null;
        try {
            transport.open();
            data = client.getMyInvite(uId,pageNo,numPerPage);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

    /**
     * 获取特权状态
     *
     * @param uId                               用户id
     * @param privilegeIds                      特权id列表
     * @return PrivilegeData
     */
    public PrivilegeData getPrivilegeStatus(int uId,List<String> privilegeIds){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        PrivilegeData data = null;
        try {
            transport.open();
            data = client.getPrivilegeStatus(uId,privilegeIds);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

    /**
     * 解锁某一特权
     *
     * @param uId                               用户id
     * @param privilegeId                       特权id
     * @return IsSuccess
     */
    public IsSuccess unlockPrivilege(int uId,String privilegeId){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        IsSuccess data = null;
        try {
            transport.open();
            data = client.unlockPrivilege(uId,privilegeId);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

    /**
     * 获取任务状态
     *
     * @param uId                                   用户id
     * @param taskTypeIds                           任务id
     * @return TaskStatuData
     */
    public TaskStatuData getMyTaskStatus(int uId,List<String> taskTypeIds){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        TaskStatuData data = null;
        try {
            transport.open();
            data = client.getMyTaskStatus(uId,taskTypeIds);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

    /**
     * 获取积分记录
     *
     * @param uId                                   用户id
     * @param pageNo                                待请求页码
     * @param numPerPage                               每页条数
     * @return PointsRecordData
     */
    public PointsRecordData getPointsRecord(int uId,int pageNo,int numPerPage){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        PointsRecordData data = null;
        try {
            transport.open();
            data = client.getPointsRecord(uId,pageNo,numPerPage);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

    /**
     * 每日活跃赚积分
     *
     * @param uId                               用户id
     * @return IsSuccess
     */
    public IsSuccess earnPointsBydailyActive(int uId){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        IsSuccess data = null;
        try {
            transport.open();
            data = client.earnPointsBydailyActive(uId);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

    /**
     * 分享app给好友赚积分
     *
     * @param uId                       用户id
     * @return IsSuccess
     */
    public IsSuccess earnPointsByshare(int uId){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        IsSuccess data = null;
        try {
            transport.open();
            data = client.earnPointsByshare(uId);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

    /**
     * 获取消息推送状态
     * @param userId                             用户id
     * @return PushStatus
     */
    public PushStatus getPushStatus(int userId){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        PushStatus data = null;
        try {
            transport.open();
            data = client.getPushStatus(userId);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

    /**
     * 通知推送设置
     *
     * @param userId                                        用户id
     * @param pushType                                      通知类型：PushNotification  PushAITransfer PushPredict PushInvite PushDailyBestPlate PushDailyBestStock PushAIWeekly
     * @param isNeedRemind                                  true 打开推送，false关闭推送
     * @return IsSuccess
     */
    public IsSuccess pushSettings(int userId,String pushType,boolean isNeedRemind){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
        UserService.Client client = new UserService.Client(mp);
        IsSuccess data = null;
        try {
            transport.open();
            data = client.pushSettings(userId,pushType,isNeedRemind);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

}
