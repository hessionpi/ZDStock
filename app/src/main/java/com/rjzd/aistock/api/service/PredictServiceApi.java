package com.rjzd.aistock.api.service;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.FundHoldingData;
import com.rjzd.aistock.api.FundamentalsData;
import com.rjzd.aistock.api.IchimokuDataList;
import com.rjzd.aistock.api.MacroData;
import com.rjzd.aistock.api.MultiDimensionPrediction;
import com.rjzd.aistock.api.PeriodDataList;
import com.rjzd.aistock.api.PredictFactorLineData;
import com.rjzd.aistock.api.PredictService;
import com.rjzd.aistock.api.Prediction;
import com.rjzd.aistock.api.PredictionKPS;
import com.rjzd.aistock.api.PredictionTrendData;
import com.rjzd.aistock.api.RelatedList;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

/**
 * Created by Hition on 2017/3/28.
 */

public class PredictServiceApi {

    private static PredictServiceApi instance;

    private PredictServiceApi(){ }

    public static PredictServiceApi getInstance(){
        if(null == instance){
            synchronized (PredictServiceApi.class){
                if (null == instance){
                    instance = new PredictServiceApi();
                }
            }
        }
        return instance;
    }


    /**
     * 获取综合预测结果
     * @param code              股票代码
     * @return Prediction       综合预测结果
     */
    public Prediction getComplexPrediction(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "PredictService");
        PredictService.Client client = new PredictService.Client(mp);
        Prediction preData = null;
        try {
            transport.open();
            preData = client.getComplexPrediction(code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return preData;
    }

    /**
     * 获取综和评分
     * @param code                 股票代码
     * @return PredictionKPS       评分结果
     */
    public PredictionKPS getKPS(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "PredictService");
        PredictService.Client client = new PredictService.Client(mp);
        PredictionKPS kps = null;
        try {
            transport.open();
            kps = client.getKPS(code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return kps;
    }

    /**
     * 获得多因子预测结果
     * @param code                              股票代码
     * @return MultiDimensionPrediction         多因子雷达总结果
     */
    public MultiDimensionPrediction getPrediction(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "PredictService");
        PredictService.Client client = new PredictService.Client(mp);
        MultiDimensionPrediction multiDimension = null;
        try {
            transport.open();
            multiDimension = client.getPrediction(code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return multiDimension;
    }

    /**
     * 获取机构持仓因子数据
     * @param code                   股票代码
     * @return FundHoldingData       机构持仓数据
     */
    public FundHoldingData getFundHolding(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "PredictService");
        PredictService.Client client = new PredictService.Client(mp);
        FundHoldingData fundHoldingData = null;
        try {
            transport.open();
            fundHoldingData = client.getFundHolding(code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return fundHoldingData;
    }

    /**
     * 获取基本面因子数据
     * @param code                          股票代码
     * @return FundamentalsData             基本面数据
     */
    public FundamentalsData getFundamentals(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "PredictService");
        PredictService.Client client = new PredictService.Client(mp);
        FundamentalsData fundamentalsData = null;
        try {
            transport.open();
            fundamentalsData = client.getFundamentals(code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return fundamentalsData;
    }

    /**
     * 获取宏观因子数据
     * @param code                          股票代码
     * @return MacroData                    宏观因子数据
     */
    public MacroData getMacro(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "PredictService");
        PredictService.Client client = new PredictService.Client(mp);
        MacroData macroData = null;
        try {
            transport.open();
            macroData = client.getMacro(code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return macroData;
    }

    /**获得股票预测因子的信息
     *
     * @param code                          股票代码
     * @param factorName                     因子名
     * @return PredictFactorLineData
     */
    public PredictFactorLineData getPredictionFactor(String code,String factorName){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp  = new TMultiplexedProtocol(protocol, "PredictService");
        PredictService.Client client = new PredictService.Client(mp);
        PredictFactorLineData prefData = null;
        try {
            transport.open();
            prefData = client.getPredictionFactor(code,factorName);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return prefData;
    }

    /**
     * 获取预测趋势曲线相关数据
     * @param code                              股票代码
     * @param factorName                        因子名称
     * @return PredictionTrendData              趋势预测结果
     */
    public PredictionTrendData getPredictionTrend(String code,String factorName){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "PredictService");
        PredictService.Client client = new PredictService.Client(mp);
        PredictionTrendData trendData = null;
        try {
            transport.open();
            trendData = client.getPredictionTrend(code,factorName);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return trendData;
    }

    /**
     * 指数获取预测趋势曲线相关数据
     * @param code                              股票代码
     * @return PredictionTrendData              趋势预测结果
     */
    public PredictionTrendData getExponentPredict(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "PredictService");
        PredictService.Client client = new PredictService.Client(mp);
        PredictionTrendData trendData = null;
        try {
            transport.open();
            trendData = client.getExponentPredict(code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return trendData;
    }

    /**
     * 获取周期性分析预测
     * @param code                                 代码（股票代码或指数代码）
     * @return PeriodDataList                       周期性数据
     */
    public PeriodDataList getPeriodPredict(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp  = new TMultiplexedProtocol(protocol, "PredictService");
        PredictService.Client client = new PredictService.Client(mp);
        PeriodDataList periodDataList = null;
        try {
            transport.open();
            periodDataList = client.getPeriodPredict(code);
        } catch (TException e) {
            e.printStackTrace();
        }
        return periodDataList;
    }

    /**
     * 获取云图预测
     * @param code                                 代码（股票代码或指数代码）
     * @return IchimokuDataList                    云图数据
     */
    public IchimokuDataList getIchimoku(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp  = new TMultiplexedProtocol(protocol, "PredictService");
        PredictService.Client client = new PredictService.Client(mp);
        IchimokuDataList ichimokuDataList = null;
        try {
            transport.open();
            ichimokuDataList = client.getIchimoku(code);
        } catch (TException e) {
            e.printStackTrace();
        }
        return ichimokuDataList;
    }
    /**
     * 获取关联矩阵预测股票
     * @param code                              股票代码
     * @return RelatedList                      关联股数据
     */
    public RelatedList getRelatedStocks(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp  = new TMultiplexedProtocol(protocol, "PredictService");
        PredictService.Client client = new PredictService.Client(mp);
        RelatedList mRelatedStocks = null;
        try {
            transport.open();
            mRelatedStocks = client.getRelatedStocks(code);
        } catch (TException e) {
            e.printStackTrace();
        }
        return mRelatedStocks;
    }

}
