package com.rjzd.aistock.model.imp;

import com.rjzd.aistock.api.AIAttention;
import com.rjzd.aistock.api.AIIncome;
import com.rjzd.aistock.api.AIIncomeType;
import com.rjzd.aistock.api.AIList;
import com.rjzd.aistock.api.AIOperation;
import com.rjzd.aistock.api.AIOperationList;
import com.rjzd.aistock.api.AttentionFlag;
import com.rjzd.aistock.api.DateTransferList;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.api.TransferActionList;
import com.rjzd.aistock.api.service.AIServiceApi;
import com.rjzd.aistock.api.service.UserServiceApi;
import com.rjzd.aistock.presenter.listener.BaseListener;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Hition on 2017/4/12.
 */

public class AIModel extends BaseModel {

    public AIModel(BaseListener listener) {
        super(listener);
    }

    /**
     * 获取所有的AI机器人信息
     *
     * @return Subscription
     */
    public Subscription getAllRobots(){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<AIList>() {
            @Override
            public void call(Subscriber<? super AIList> subscriber) {
                AIList data = AIServiceApi.getInstance().getAllRobots();
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取所有关注的机器人
     *
     * @param userId                                用户id
     * @return List<String>
     */
    public Subscription getAttentionAIs(final int userId){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<AIAttention>() {
            @Override
            public void call(Subscriber<? super AIAttention> subscriber) {
                AIAttention data = AIServiceApi.getInstance().getAttentionAIs(userId);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取机器人收益状况
     * @param id                            机器人id
     * @param incomeType                    收益类别    近一周   近一月  总收益
     * @return Subscription
     */
    public Subscription getIncomes(final String id, final AIIncomeType incomeType){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<AIIncome>() {
            @Override
            public void call(Subscriber<? super AIIncome> subscriber) {
                AIIncome data = AIServiceApi.getInstance().getAIIncome(id,incomeType);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取AI机器人调仓历史
     * @param id                            机器人id
     * @param pageNo                        待请求页码
     * @param numPerPage                    每页请求多少条
     * @return Subscription
     */
    public Subscription getAIActions(final String id, final int pageNo, final int numPerPage){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<DateTransferList>() {
            @Override
            public void call(Subscriber<? super DateTransferList> subscriber) {
                DateTransferList data = AIServiceApi.getInstance().getAIActions(id,pageNo,numPerPage);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取持仓股票列表
     * @param id                              机器人id
     * @return Subscription
     */
    public Subscription getHoldStocks(final String id,final int topN){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<AIOperationList>() {
            @Override
            public void call(Subscriber<? super AIOperationList> subscriber) {
                AIOperationList data = AIServiceApi.getInstance().getHoldStocks(id,topN);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取历史收益
     * @param id                                       机器人id
     * @param isAscend                                 true 收益升序，false收益降序
     * @return Subscription
     */
    public Subscription getHistoryPAL(final String id, final int topN,final boolean isAscend){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<List<AIOperation>>() {
            @Override
            public void call(Subscriber<? super List<AIOperation>> subscriber) {
                List<AIOperation> data = AIServiceApi.getInstance().getHistoryPAL(id,topN,isAscend);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取历史收益 分页
     * @param id                                       机器人id
     * @param pageNo                                   待请求页码
     * @return Subscription
     */
    public Subscription getHistoryPALPaging(final String id, final int pageNo){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<AIOperationList>() {
            @Override
            public void call(Subscriber<? super AIOperationList> subscriber) {
                AIOperationList data = AIServiceApi.getInstance().getHistoryPALPaging(id,pageNo);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }


    /**
     * 添加或取消关注机器人
     * @param aiId                          机器人id
     * @param userId                        用户id
     * @param addOrCancel                   添加或取消关注标志
     * @return Subscription
     */
    public Subscription addOrCancelAttention(final String aiId, final int userId, final AttentionFlag addOrCancel){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<IsSuccess>() {
            @Override
            public void call(Subscriber<? super IsSuccess> subscriber) {
                IsSuccess data = UserServiceApi.getInstance().addOrCancelAttention(aiId,userId,addOrCancel);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

}
