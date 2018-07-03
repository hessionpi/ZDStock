package com.rjzd.aistock.presenter;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.AIAttention;
import com.rjzd.aistock.api.AIIncome;
import com.rjzd.aistock.api.AIIncomeType;
import com.rjzd.aistock.api.AIList;
import com.rjzd.aistock.api.AIOperationList;
import com.rjzd.aistock.api.AttentionFlag;
import com.rjzd.aistock.api.DateTransferList;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.model.imp.AIModel;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.LogUtil;

import org.apache.thrift.TException;

import java.util.List;

/**
 * AI 相关
 * <p>
 * Created by Hition on 2017/4/12.
 */

public class AIPresenter extends BasePresenter {

    private IFillDataView mView;
    private AIModel model;
    private boolean isHold = false;

    public AIPresenter(IFillDataView view) {
        this.mView = view;
        model = new AIModel(this);
    }

    public AIModel getModel() {
        return model;
    }

    /**
     * 获取所有AI机器人信息
     */
    public void getAllRobots() {
        addSubscription(model.getAllRobots());
    }

    /**
     * 获取AI收益情况
     *
     * @param id         机器人id
     * @param incomeType 收益类别   近一周收益   近一月收益   全部收益
     */
    public void getAIIncome(String id, AIIncomeType incomeType) {
        addSubscription(model.getIncomes(id, incomeType));
    }


    /**
     * 获取机器人调仓动态信息
     *
     * @param id         机器人id
     * @param pageNo     待请求页码
     * @param numPerPage 每页请求多少条
     */
    public void getAIActions(String id, int pageNo, int numPerPage) {
        addSubscription(model.getAIActions(id, pageNo, numPerPage));
    }

    /**
     * 获取机器人持仓股票
     *
     * @param id 机器人id
     */
    public void getHoldStocks(String id, int topN) {
        isHold = true;
        addSubscription(model.getHoldStocks(id, topN));
    }

    /**
     * 获取历史盈亏
     *
     * @param id       机器人id
     * @param isAscend true 收益升序，false 收益降序
     */
    public void getHistoryPAL(String id, int topN, boolean isAscend) {
        addSubscription(model.getHistoryPAL(id, topN, isAscend));
    }

    /**
     * 获取历史盈亏 分页
     *
     * @param id     机器人id
     * @param pageNo 待请求页码
     */
    public void getHistoryPALPaging(String id, int pageNo) {
        isHold = false;
        addSubscription(model.getHistoryPALPaging(id, pageNo));
    }

    /**
     * 添加或取消关注
     */
    public void addOrCancelAttention(String aiId, int userId, AttentionFlag addOrCancel) {
        addSubscription(model.addOrCancelAttention(aiId, userId, addOrCancel));
    }

    public void getAttentionAIs(int userId) {
        addSubscription(model.getAttentionAIs(userId));
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof AIList) {
            mView.fillData(data, Constants.DS_TAG_AI_INFO);
        } else if (data instanceof AIIncome) {
            mView.fillData(data, Constants.DS_TAG_AI_INCOME);
        } else if (data instanceof DateTransferList) {
            mView.fillData(data, Constants.DS_TAG_AI_ACTION);
        } else if (data instanceof AIOperationList) {
            if (isHold) {
                mView.fillData(data, Constants.DS_TAG_AI_HOLD_STOCK);
            } else {
                mView.fillData(data, Constants.DS_TAG_AI_HISTORY_PAGING);
            }
        } else if (data instanceof List) {
            mView.fillData(data, Constants.DS_TAG_AI_HISTORY_PAL);
        } else if (data instanceof AIAttention) {
            mView.fillData(data, Constants.DS_TAG_AI_ATTENTION_GET);
        } else if (data instanceof IsSuccess) {
            mView.fillData(data, Constants.DS_TAG_AI_ATTENTION_ADD_OR_CANCEL);
        } else {
            mView.fillData(data, Constants.DS_TAG_DEFAULT);
        }
    }

    @Override
    public void onFailed(Throwable e) {
        if (e instanceof TException) {
            mView.showFailedView();
        } else {
            LogUtil.e("hition==AI", e.getMessage());
        }
    }
}
