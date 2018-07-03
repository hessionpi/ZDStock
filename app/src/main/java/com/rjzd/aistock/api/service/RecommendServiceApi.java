package com.rjzd.aistock.api.service;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.PlateRecommendationData;
import com.rjzd.aistock.api.RecommendService;
import com.rjzd.aistock.api.StockRecommendationData;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

/**
 * 推荐类服务-主要提供每日金股、每日牛版推荐等
 *
 * Created by Hition on 2017/8/9.
 */

public class RecommendServiceApi {

    private static RecommendServiceApi instance;

    private RecommendServiceApi(){

    }

    public static RecommendServiceApi getInstance(){
        if(null == instance){
            synchronized (RecommendServiceApi.class){
                if (null == instance){
                    instance = new RecommendServiceApi();
                }
            }
        }
        return instance;
    }

    /**
     * 获取板块推荐结果
     *
     * @param pageIndex                                     待请求页码
     * @param perPage                                       每页请求条数
     * @return PlateRecommendationData
     */
    public PlateRecommendationData getPlateRecommendation(int pageIndex,int perPage){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "RecommendService");
        RecommendService.Client client = new RecommendService.Client(mp);
        PlateRecommendationData data = null;
        try {
            transport.open();
            data = client.getPlateRecommendation(pageIndex,perPage);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }


    /**
     * 获取股票推荐结果
     *
     * @param pageIndex                                     待请求页码
     * @param perPage                                       每页请求条数
     * @return StockRecommendationData
     */
    public StockRecommendationData getStockRecommendation(int pageIndex,int perPage){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "RecommendService");
        RecommendService.Client client = new RecommendService.Client(mp);
        StockRecommendationData data = null;
        try {
            transport.open();
            data = client.getStockRecommendation(pageIndex,perPage);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

}
