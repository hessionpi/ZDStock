package com.rjzd.aistock.api.service;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.AIWeeklyData;
import com.rjzd.aistock.api.AnnouncementData;
import com.rjzd.aistock.api.Count;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.api.NotificationService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

/**
 * Created by Hition on 2017/8/7.
 *
 * @author Hition
 */

public class NotificationServiceApi {

    private static NotificationServiceApi instance;

    private NotificationServiceApi(){

    }

    public static NotificationServiceApi getInstance(){
        if(null == instance){
            synchronized (AIServiceApi.class){
                if (null == instance){
                    instance = new NotificationServiceApi();
                }
            }
        }
        return instance;
    }

    /**
     * 获取未读消息条数
     *
     * @param userId                            用户id
     * @return Count                            未读消息总条数
     */
    public Count getTotalUnread(int userId){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "NotificationService");
        NotificationService.Client client = new NotificationService.Client(mp);
        Count count = null;
        try {
            transport.open();
            count = client.getTotalUnread(userId);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return count;
    }

    /**
     * 设置已读消息状态
     *
     * @param userId                        用户id
     * @param newsId                        消息id
     * @return IsSuccess                    是否设置成功
     */
    public IsSuccess setRead(int userId,String newsId){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "NotificationService");
        NotificationService.Client client = new NotificationService.Client(mp);
        IsSuccess isSuccess = null;
        try {
            transport.open();
            isSuccess = client.setRead(userId,newsId);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return isSuccess;
    }


    /**
     * 获取系统同通知
     * @param userId                        用户id
     * @param pageNo                        待请求页
     * @param numPerPage                    每页请求页数
     * @return AnnouncementData
     */
    public AnnouncementData getAnnouncement(int userId,int pageNo, int numPerPage){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "NotificationService");
        NotificationService.Client client = new NotificationService.Client(mp);
        AnnouncementData announcementData = null;
        try {
            transport.open();
            announcementData = client.getAnnouncement(userId,pageNo,numPerPage);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return announcementData;
    }

    /**
     * 获取AI周报
     *
     * @return AIWeeklyData
     */
    public AIWeeklyData getAIWeekly(){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "NotificationService");
        NotificationService.Client client = new NotificationService.Client(mp);
        AIWeeklyData data = null;
        try {
            transport.open();
            data = client.getAIWeekly();
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

}
