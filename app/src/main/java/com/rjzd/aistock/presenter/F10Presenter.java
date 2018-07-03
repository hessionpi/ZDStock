package com.rjzd.aistock.presenter;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.CodeType;
import com.rjzd.aistock.api.CompanyProfile;
import com.rjzd.aistock.api.FinanceAnalysisData;
import com.rjzd.aistock.api.FinancePerformance;
import com.rjzd.aistock.api.FundHoldingData;
import com.rjzd.aistock.api.FundamentalsData;
import com.rjzd.aistock.api.FundsData;
import com.rjzd.aistock.api.IchimokuDataList;
import com.rjzd.aistock.api.MacroData;
import com.rjzd.aistock.api.MarginTrading;
import com.rjzd.aistock.api.MultiDimensionPrediction;
import com.rjzd.aistock.api.NewsType;
import com.rjzd.aistock.api.PeriodDataList;
import com.rjzd.aistock.api.PredictFactorLineData;
import com.rjzd.aistock.api.Prediction;
import com.rjzd.aistock.api.PredictionKPS;
import com.rjzd.aistock.api.PredictionTrendData;
import com.rjzd.aistock.api.RangeFlag;
import com.rjzd.aistock.api.RelatedList;
import com.rjzd.aistock.api.ShareholderInfo;
import com.rjzd.aistock.model.imp.F10Model;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.LogUtil;

import org.apache.thrift.TException;

import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_COMPREHENSIVE;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_COMPREHENSIVE_FACTOR;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_KPS;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_MACRO;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_PERIODICITY;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_RADAR;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_RELATED_STOCKS;

/**
 * Created by Hition on 2017/3/16.
 */

public class F10Presenter extends BasePresenter{

    private IFillDataView mView;
    private F10Model model;

    public F10Presenter(IFillDataView mView){
        this.mView = mView;
        this.model = new F10Model(this);
    }

    /**
     * 获取综合预测结果
     *
     * @param code                              股票代码
     * @return Subscription
     */
    public void getComplexPrediction(String code){
        addSubscription(model.getComplexPrediction(code));
    }

    /**
     * 获取综和评分
     * @param code                              股票代码
     */
    public void getKPS(String code){
        addSubscription(model.getKPS(code));
    }

    /**
     * 获得多因子预测结果
     * @param code                              股票代码
     */
    public void getPrediction(String code){
        addSubscription(model.getPrediction(code));
    }

    /**
     * 获取机构持仓因子数据
     * @param code                              股票代码
     */
    public void getFundHolding(String code){
        addSubscription(model.getFundHolding(code));
    }

    /**
     * 获取基本面因子数据
     * @param code                              股票代码
     */
    public void getFundamentals(String code){
        addSubscription(model.getFundamentals(code));
    }

    /**
     * 获取宏观因子数据
     * @param code                              股票代码
     */
    public void getMacro(String code){
        addSubscription(model.getMacro(code));
    }

    /**
     * 获得股票预测因子的信息
     *
     * @param code                  股票代码
     * @param factorname            因子名
     */
    public void getPredictionFactor(String code,String factorname){
        addSubscription(model.getPredictionFactor(code,factorname));
    }

    /**
     * 获取利用某一因子预测结果
     * @param code                              股票代码
     * @param factorname                        因子名称
     */
    public void getPredictionTrend(String code,String factorname){
        addSubscription(model.getPredictionTrend(code,factorname));
    }
    /**
     * 指数获取利用某一因子预测结果
     * @param code                              股票代码
     */
    public void getExponentPredict(String code){
        addSubscription(model.getExponentPredict(code));
    }
    /**
     * 获取周期性预测结果
     * @param code
     */
    public void getPeriodPredict(String code){
        addSubscription(model.getPeriodPredict(code));
    }

    /**
     * 获取云图预测结果
     * @param code
     */
    public void getIchimoku(String code){
        addSubscription(model.getIchimoku(code));
    }

    /**
     * 获取关联矩阵预测股票列表
     * @param code                               股票代码
     */
    public void getRelationStocks(String code){
        addSubscription(model.getRelationStocks(code));
    }


    /**
     * 获取新闻/公告
     * @param code             股票代码
     * @param pageindex        待请求页码
     * @param nt               新闻类型
     */
    public void getNews(String code, int pageindex, NewsType nt,CodeType ctype){
        addSubscription(model.getNews(code,pageindex,nt,ctype));
    }

    /**
     * 获取公司概况数据
     * @param code   股票代码
     */
    public void getCompanyProfile(String code){
        addSubscription(model.getCompanyProfile(code));
    }

    /**
     * 股东信息
     * @param code    股票代码
     */
    public void getShareholder(String code){
        addSubscription(model.getShareholder(code));
    }

    /**
     * 获取资金流向
     *
     * @param code    股票代码
     */
    public void getFunds(String code){
        addSubscription(model.getFunds(code));
    }

    /**
     * 获取融资融券数据
     * @param code
     */
    public void getMarginTrading(String code){
        addSubscription(model.getMarginTrading(code));
    }

    /**
     * 获取财务业绩数据
     * @param code              股票代码
     * @return
     */
    public void getFinancePerformance(String code){
        addSubscription(model.getFinancePerformance(code));
    }

    /**
     * 获取财务分析数据
     * @param code              股票代码
     */
    public void getFinanceAnalysis(String code){
        addSubscription(model.getFinanceAnalysis(code));
    }

    /**
     * 获取成份股涨跌幅榜  top10
     *
     * @param code                            指数代码
     * @param rf                              {@link RangeFlag}
     */
    public void getComponentStock(String code, RangeFlag rf){
        addSubscription(model.getComponentStock(code,rf,10));
    }
    public boolean isZixuan(String code){
        return model.isZixuan(code);
    }

    @Override
    public void onSuccess(Object data) {
        super.onSuccess(data);
        if(data instanceof Prediction){
            mView.fillData(data,DS_TAG_PREDCTION_COMPREHENSIVE);
        } else if(data instanceof PredictionKPS){
            mView.fillData(data,DS_TAG_PREDCTION_KPS);
        }else if(data instanceof MultiDimensionPrediction){
            mView.fillData(data,DS_TAG_PREDCTION_RADAR);
        }else if(data instanceof PeriodDataList){
            mView.fillData(data,DS_TAG_PREDCTION_PERIODICITY);
        }else if(data instanceof FundamentalsData){
            mView.fillData(data,Constants.DS_TAG_PREDCTION_FUNDAMENTALS);
        }else if(data instanceof FundHoldingData){
            mView.fillData(data,Constants.DS_TAG_PREDCTION_FUNDHOLDING);
        }else if(data instanceof MacroData){
            mView.fillData(data,DS_TAG_PREDCTION_MACRO);
        }else if(data instanceof PredictFactorLineData){
            mView.fillData(data,DS_TAG_PREDCTION_COMPREHENSIVE_FACTOR);
        }else if(data instanceof IchimokuDataList){
            mView.fillData(data,Constants.DS_TAG_PREDCTION_ICHIMOKU);
        }else if(data instanceof RelatedList){
            mView.fillData(data,DS_TAG_PREDCTION_RELATED_STOCKS);
        }else if(data instanceof FundsData){
            mView.fillData(data,Constants.DS_TAG_CAPITAL_FLOWS);
        }else if(data instanceof MarginTrading){
            mView.fillData(data,Constants.DS_TAG_MARGINTRADING);
        }else if(data instanceof FinancePerformance){
            mView.fillData(data,Constants.DS_TAG_FINANCE_PERFORMANCE);
        }else if(data instanceof FinanceAnalysisData){
            mView.fillData(data,Constants.DS_TAG_FINANCE_ANALYSIS);
        }else if(data instanceof CompanyProfile){
            mView.fillData(data,Constants.DS_TAG_COMPANY_PROFILE);
        }else if(data instanceof ShareholderInfo){
            mView.fillData(data,Constants.DS_TAG_SHARE_HOLDER);
        }else if(data instanceof PredictionTrendData) {
            mView.fillData(data, Constants.DS_TAG_PREDCTION_STOCK_RESULT);
        }else{
            mView.fillData(data, Constants.DS_TAG_DEFAULT);
        }

    }

    @Override
    public void onFailed(Throwable e) {
        if(e instanceof TException){
            mView.showFailedView();
        }else{
            LogUtil.e("hition==F10",e.getMessage());
        }
    }
}
