package com.rjzd.aistock.api.service;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.AppUpdate;
import com.rjzd.aistock.api.SystemService;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * 所有系统操作接口
 * Created by Hition on 2017/1/11.
 */

public class SystemServiceApi {

    private static SystemServiceApi instance;

    private SystemServiceApi(){ }

    public static SystemServiceApi getInstance(){
        if(null == instance){
            synchronized (SystemServiceApi.class){
                if (null == instance){
                    instance = new SystemServiceApi();
                }
            }
        }
        return instance;
    }

    /**
     * 检查更新
     * @param packageName              应用包名
     * @param versionCode              版本号
     * @return AppUpdate
     */
    public AppUpdate checkUpdate(String packageName,int versionCode){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "SystemService");
        SystemService.Client client = new SystemService.Client(mp);
        AppUpdate update = null;
        try {
            transport.open();
            update = client.checkUpdate(packageName,versionCode);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return update;

    }









}
