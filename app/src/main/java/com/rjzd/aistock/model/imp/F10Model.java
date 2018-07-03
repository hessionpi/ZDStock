package com.rjzd.aistock.model.imp;

import com.rjzd.aistock.api.BasicStockList;
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
import com.rjzd.aistock.api.NewsList;
import com.rjzd.aistock.api.NewsType;
import com.rjzd.aistock.api.PeriodDataList;
import com.rjzd.aistock.api.PredictFactorLineData;
import com.rjzd.aistock.api.Prediction;
import com.rjzd.aistock.api.PredictionKPS;
import com.rjzd.aistock.api.PredictionTrendData;
import com.rjzd.aistock.api.RangeFlag;
import com.rjzd.aistock.api.RelatedList;
import com.rjzd.aistock.api.ShareholderInfo;
import com.rjzd.aistock.api.service.PredictServiceApi;
import com.rjzd.aistock.api.service.StockServiceApi;
import com.rjzd.aistock.presenter.listener.BaseListener;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * F10档案  包括新闻 动态  研报 资金 财务  公司概况等信息
 * Created by Hition on 2017/3/16.
 */

public class F10Model extends BaseModel {

    public F10Model(BaseListener listener) {
        super(listener);
    }

    /**
     * 获取综合预测结果
     *
     * @param code                              股票代码
     * @return Subscription
     */
    public Subscription getComplexPrediction(final String code){
        Observable.OnSubscribe subscribe =  new Observable.OnSubscribe<Prediction>(){
            @Override
            public void call(Subscriber<? super Prediction> subscriber) {
                Prediction preData = PredictServiceApi.getInstance().getComplexPrediction(code);
                subscriber.onNext(preData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取综和评分
     * @param code                              股票代码
     * @return Subscription
     */
    public Subscription getKPS(final String code){
        Observable.OnSubscribe subscribe =  new Observable.OnSubscribe<PredictionKPS>(){
            @Override
            public void call(Subscriber<? super PredictionKPS> subscriber) {
                PredictionKPS kpsData = PredictServiceApi.getInstance().getKPS(code);
                subscriber.onNext(kpsData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获得多因子预测结果
     * @param code                              股票代码
     * @return Subscription
     */
    public Subscription getPrediction(final String code){
        Observable.OnSubscribe subscribe =  new Observable.OnSubscribe<MultiDimensionPrediction>(){
            @Override
            public void call(Subscriber<? super MultiDimensionPrediction> subscriber) {
                MultiDimensionPrediction  multidimension = PredictServiceApi.getInstance().getPrediction(code);
                subscriber.onNext(multidimension);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取机构持仓因子数据
     * @param code                              股票代码
     * @return Subscription
     */
    public Subscription getFundHolding(final String code){
        Observable.OnSubscribe subscribe =  new Observable.OnSubscribe<FundHoldingData>(){
            @Override
            public void call(Subscriber<? super FundHoldingData> subscriber) {
                FundHoldingData  fundHoldData = PredictServiceApi.getInstance().getFundHolding(code);
                subscriber.onNext(fundHoldData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取基本面因子数据
     * @param code                              股票代码
     * @return Subscription
     */
    public Subscription getFundamentals(final String code){
        Observable.OnSubscribe subscribe =  new Observable.OnSubscribe<FundamentalsData>(){
            @Override
            public void call(Subscriber<? super FundamentalsData> subscriber) {
                FundamentalsData  fundamentalsData = PredictServiceApi.getInstance().getFundamentals(code);
                subscriber.onNext(fundamentalsData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取宏观因子数据
     * @param code                              股票代码
     * @return Subscription
     */
    public Subscription getMacro(final String code){
        Observable.OnSubscribe subscribe =  new Observable.OnSubscribe<MacroData>(){
            @Override
            public void call(Subscriber<? super MacroData> subscriber) {
                MacroData  macroData = PredictServiceApi.getInstance().getMacro(code);
                subscriber.onNext(macroData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }


    /**
     * 获得股票预测因子的信息
     *
     * @param code                               股票代码
     * @param factorName                         因子名
     * @return Subscription
     */
    public Subscription getPredictionFactor(final String code, final String factorName){
        Observable.OnSubscribe subscribe =  new Observable.OnSubscribe<PredictFactorLineData>(){
            @Override
            public void call(Subscriber<? super PredictFactorLineData> subscriber) {
                PredictFactorLineData preflData = PredictServiceApi.getInstance().getPredictionFactor(code,factorName);
                subscriber.onNext(preflData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取利用某一因子预测结果
     * @param code                              股票代码
     * @param factorName                        因子名称
     * @return Subscription
     */
    public Subscription getPredictionTrend(final String code, final String factorName){
        Observable.OnSubscribe subscribe =  new Observable.OnSubscribe<PredictionTrendData>(){
            @Override
            public void call(Subscriber<? super PredictionTrendData> subscriber) {
                PredictionTrendData trendData = PredictServiceApi.getInstance().getPredictionTrend(code,factorName);
                subscriber.onNext(trendData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }
    /**
     * 指数获取利用某一因子预测结果
     * @param code                              股票代码
     * @return Subscription
     */
    public Subscription getExponentPredict(final String code){
        Observable.OnSubscribe subscribe =  new Observable.OnSubscribe<PredictionTrendData>(){
            @Override
            public void call(Subscriber<? super PredictionTrendData> subscriber) {
                PredictionTrendData trendData = PredictServiceApi.getInstance().getExponentPredict(code);
                subscriber.onNext(trendData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }
    /**
     * 周期性预测
     * @param code                                股票代码
     * @return Subscription
     */
    public Subscription getPeriodPredict(final String code){
        Observable.OnSubscribe subscribe =  new Observable.OnSubscribe<PeriodDataList>(){
            @Override
            public void call(Subscriber<? super PeriodDataList> subscriber) {
                PeriodDataList periodDataList = PredictServiceApi.getInstance().getPeriodPredict(code);
                subscriber.onNext(periodDataList);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }
    /**
     * 云图预测
     * @param code                                 股票代码
     * @return Subscription
     */
    public Subscription getIchimoku(final String code){
        Observable.OnSubscribe subscribe =  new Observable.OnSubscribe<IchimokuDataList>(){
            @Override
            public void call(Subscriber<? super IchimokuDataList> subscriber) {
                IchimokuDataList ichimokuDataList = PredictServiceApi.getInstance().getIchimoku(code);
                subscriber.onNext(ichimokuDataList);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }
    /**
     * 获取关联矩阵股票列表
     * @param code                                 股票代码
     * @return Subscription
     */
    public Subscription getRelationStocks(final String code){
        Observable.OnSubscribe subscribe =  new Observable.OnSubscribe<RelatedList>(){
            @Override
            public void call(Subscriber<? super RelatedList> subscriber) {
                RelatedList relatedStocks = PredictServiceApi.getInstance().getRelatedStocks(code);
                subscriber.onNext(relatedStocks);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }


    /**
     * 获取新闻公告
     * @param code               股票代码
     * @param pageindex          待请求页码
     * @param tp                 新闻类型
     * @return Subscription
     */
    public Subscription getNews(final String code, final int pageindex, final NewsType tp, final CodeType ctype){
        Observable.OnSubscribe subscribe =  new Observable.OnSubscribe<NewsList>(){
            @Override
            public void call(Subscriber<? super NewsList> subscriber) {
                NewsList listData = StockServiceApi.getInstance().getNews(code,pageindex,tp,ctype);
                subscriber.onNext(listData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 公司概况
     * @param code       股票代码
     * @return Subscription
     */
    public Subscription getCompanyProfile(final String code){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<CompanyProfile>() {
            @Override
            public void call(Subscriber<? super CompanyProfile> subscriber) {
                CompanyProfile profile = StockServiceApi.getInstance().getCompanyProfile(code);
                subscriber.onNext(profile);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 股东信息
     *
     * @param code           股票代码
     * @return Subscription
     */
    public Subscription getShareholder(final String code){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<ShareholderInfo>() {
            @Override
            public void call(Subscriber<? super ShareholderInfo> subscriber) {
                ShareholderInfo shInfo = StockServiceApi.getInstance().getShareholder(code);
                subscriber.onNext(shInfo);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取资金流向
     * @param code      股票代码
     * @return Subscription
     */
    public Subscription getFunds(final String code){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<FundsData>() {
            @Override
            public void call(Subscriber<? super FundsData> subscriber) {
                FundsData fdata = StockServiceApi.getInstance().getFunds(code);
                subscriber.onNext(fdata);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取融资融券差额
     *
     * @param code  股票代码
     * @return Subscription
     */
    public Subscription getMarginTrading(final String code){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<MarginTrading>() {
            @Override
            public void call(Subscriber<? super MarginTrading> subscriber) {
                MarginTrading mtData = StockServiceApi.getInstance().getMarginTrading(code);
                subscriber.onNext(mtData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取财务业绩数据
     *
     * @param code  股票代码
     * @return Subscription
     */
    public Subscription getFinancePerformance(final String code){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<FinancePerformance>() {
            @Override
            public void call(Subscriber<? super FinancePerformance> subscriber) {
                FinancePerformance fpData = StockServiceApi.getInstance().getFinancePerformance(code);
                subscriber.onNext(fpData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取财务分析数据
     *
     * @param code      股票代码
     * @return Subscription
     */
    public Subscription getFinanceAnalysis(final String code){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<FinanceAnalysisData>() {
            @Override
            public void call(Subscriber<? super FinanceAnalysisData> subscriber) {
                FinanceAnalysisData fpData = StockServiceApi.getInstance().getFinanceAnalysis(code,0,0);
                subscriber.onNext(fpData);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

    /**
     * 获取指数成份股
     *
     * @param code                        指数代码
     * @param rf                          涨跌幅标识
     * @param count                       总数
     * @return Subscription
     */
    public Subscription getComponentStock(final String code, final RangeFlag rf, final int count){
        Observable.OnSubscribe subscribe = new Observable.OnSubscribe<BasicStockList>() {
            @Override
            public void call(Subscriber<? super BasicStockList> subscriber) {
                BasicStockList data = StockServiceApi.getInstance().getComponentStock(code,rf,count);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        };
        return super.getSubscription(subscribe);
    }

}
