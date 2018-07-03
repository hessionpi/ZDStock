package com.rjzd.aistock.api.service;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.AIAttention;
import com.rjzd.aistock.api.AIIncome;
import com.rjzd.aistock.api.AIIncomeType;
import com.rjzd.aistock.api.AIList;
import com.rjzd.aistock.api.AIOperation;
import com.rjzd.aistock.api.AIOperationList;
import com.rjzd.aistock.api.AIService;
import com.rjzd.aistock.api.DateTransferList;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import java.util.List;

/**
 * 所有系统操作接口
 * Created by Hition on 2017/1/11.
 */

public class AIServiceApi {

    private static AIServiceApi instance;

    private AIServiceApi(){

    }

    public static AIServiceApi getInstance(){
        if(null == instance){
            synchronized (AIServiceApi.class){
                if (null == instance){
                    instance = new AIServiceApi();
                }
            }
        }
        return instance;
    }

    /**
     * 获取所有AI机器人
     *
     * @return AIList
     */
    public AIList getAllRobots(){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "AIService");
        AIService.Client client = new AIService.Client(mp);
        AIList aiDatas = null;
        try {
            transport.open();
            aiDatas = client.getAllTheAI();
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return aiDatas;
    }

    /**
     * 获取所有关注的机器人
     *
     * @param userId                                用户id
     * @return List<String>
     */
    public AIAttention getAttentionAIs(int userId){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "AIService");
        AIService.Client client = new AIService.Client(mp);
        AIAttention data = null;
        try {
            transport.open();
            data = client.getAttentionAIs(userId);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

    /**
     *
     * @param id                         机器人id
     * @param incomeType                 收益类型   近一周  近一月   总收益
     * @return AIIncome
     */
    public AIIncome getAIIncome(String id, AIIncomeType incomeType){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "AIService");
        AIService.Client client = new AIService.Client(mp);
        AIIncome incomeData = null;
        try {
            transport.open();
            incomeData = client.getAIIncome(id,incomeType);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return incomeData;
    }

    /**
     * 获取机器人调仓动态
     *
     * @param id                机器人id
     * @return AIActionList
     */
    public DateTransferList getAIActions(String id,int pageNo,int numPerPage){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "AIService");
        AIService.Client client = new AIService.Client(mp);
        DateTransferList actionList = null;
        try {
            transport.open();
            actionList = client.getAITransfers(id,pageNo,numPerPage);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return actionList;
    }

    /**
     * 获取持仓股票情况                      持仓股票和离职盈亏公用一个api   isAll = false代表持仓，topN参数忽略
     * @param id                            机器人id
     * @return AIOperationList
     */
    public AIOperationList getHoldStocks(String id,int topN){
        return getOperations(id,false,topN,false);
    }

    /**
     * 获取历史盈亏                               get history of profit and loss
     * @param id                                 机器人id
     * @param isAscend                           是否按照收益升序排序  true 升序，false降序
     */
    public List<AIOperation> getHistoryPAL(String id,int topN,boolean isAscend){
        return getOperations(id,true,topN,isAscend).get_data();
    }

    /**
     * 获取持仓股票、历史盈亏等调用接口
     * @param id                        机器人id
     * @param isAll                     持仓中/所有
     * @param topN                      获取数据总条数
     * @param isAscend                  是否按照收益 升序  true 升序， false 降序
     * @return List<AIOperation>
     */
    private AIOperationList getOperations(String id,boolean isAll,int topN,boolean isAscend){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "AIService");
        AIService.Client client = new AIService.Client(mp);
        AIOperationList positionList = null;
        try {
            transport.open();
            positionList = client.getOperations(id,isAll,topN,isAscend);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return positionList;
    }

    /**
     * 获取历史盈亏 分页                               get history of profit and loss by paging
     * @param id                                      机器人id
     * @param pageNo                                  待请求页码
     * @return AIOperationList
     */
    public AIOperationList getHistoryPALPaging(String id,int pageNo){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "AIService");
        AIService.Client client = new AIService.Client(mp);
        AIOperationList positionList = null;
        try {
            transport.open();
            positionList = client.getOperationsByPage(id,pageNo,Constants.DEFAULT_PER_PAGE);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return positionList;
    }


}
