package com.rjzd.aistock.api.service;


import com.google.gson.Gson;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.BasicStockList;
import com.rjzd.aistock.api.CodeType;
import com.rjzd.aistock.api.CompanyProfile;
import com.rjzd.aistock.api.Condition;
import com.rjzd.aistock.api.FilterStockList;
import com.rjzd.aistock.api.FinanceAnalysisData;
import com.rjzd.aistock.api.FinancePerformance;
import com.rjzd.aistock.api.FundsData;
import com.rjzd.aistock.api.KData;
import com.rjzd.aistock.api.KType;
import com.rjzd.aistock.api.MarginTrading;
import com.rjzd.aistock.api.MarketData;
import com.rjzd.aistock.api.News;
import com.rjzd.aistock.api.NewsList;
import com.rjzd.aistock.api.NewsType;
import com.rjzd.aistock.api.PlateFlag;
import com.rjzd.aistock.api.RangeFlag;
import com.rjzd.aistock.api.ShareholderInfo;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.api.StockData;
import com.rjzd.aistock.api.StockList;
import com.rjzd.aistock.api.StockService;
import com.rjzd.commonlib.utils.LogUtil;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import java.util.List;

/**
 * 对所有网络请求做封装  使用Thrift方式
 * Created by Hition on 2017/1/4.
 * */

public class StockServiceApi {

    private static StockServiceApi instance;

    private StockServiceApi(){

    }

    public static StockServiceApi getInstance(){
        if(null == instance){
            synchronized (StockServiceApi.class){
                if (null == instance){
                    instance = new StockServiceApi();
                }
            }
        }
        return instance;
    }

    /**
     * 从服务器获取所有股票存本地文件中，搜索逻辑从本地操作
     * @return  listToJson    所有股票转json
     */
    public String getAllStockAndCash(){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);

        long start = System.currentTimeMillis();
        StockList data = null;
        try {
            transport.open();
            data = client.getStock("");
            long end = System.currentTimeMillis();
            LogUtil.e("获取所有股票数据总耗时："+(end-start));
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }

        if(null != data && (StatusCode.OK == data.get_status())){
            Gson gson = new Gson();
            return gson.toJson(data);
        }
        return null;
    }

    /**
     * 刷新自选股涨跌幅和当前股价
     * @param codeList    待刷新股票代码列表
     * @return  StockData
     */
    public StockData refreshStock(List<String> codeList){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);
        StockData data = null;
        try {
            transport.open();
            data = client.refreshStocks(codeList);
        } catch (TException x) {
            x.printStackTrace();
        }finally {
            transport.close();
        }

        return data;
    }
    /**
     * 判断是否是交易日
     * @return  boolean
     */
    public boolean isTradeDate(){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);
        boolean isTrade = true;
        try {
            transport.open();
            isTrade = client.isTradeDate();
        } catch (TException x) {
            x.printStackTrace();
        }finally {
            transport.close();
        }

        return isTrade;
    }

    /**
     * 获取K线数据
     * @param code            股票代码
     * @param type            分时图/日k/周k/月k
     * @param startMinute     开始时间   例 9:30
     * @return KData
     */
    public KData getKData(String code, KType type,String startMinute){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);

        KData data = null;
        try {
            transport.open();
            data = client.getStockKData(code,type,startMinute);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }

        return data;
    }

    /**
     * 根据筛选条件获取满足条件的自定义股票列表
     * @param condition       筛选条件
     * @return FilterStockList
     */
    public FilterStockList getMatchStockResult(Condition condition){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);

        FilterStockList data= null;
        try {
            transport.open();
            data = client.getOptionalStocks(condition);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

    /**
     * 获取大盘数据
     * @param rf                   涨幅/跌幅
     * @param pf                   行业/概念/区域
     * @param pageNo               待请求页码
     * @param pageSize             每页请求条数
     * @return List<Market>
     */
    public MarketData getMarketData(RangeFlag rf, PlateFlag pf, int pageNo,int pageSize){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);

        MarketData mData = null;
        try {
            transport.open();
            mData =  client.getMarket(rf,pf,pageNo,pageSize);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return mData;
    }

    /**
     * 获取领涨或领跌个股
     * @param rf                   涨幅/跌幅
     * @param pf                   行业/概念/区域
     * @param lable                标签名：行业名称/概念名称/省市名称
     * @param pageNo               待请求页
     * @param pageSize             每页请求数据条数
     * @return List<Market>
     */
    public MarketData getLeaders(RangeFlag rf, PlateFlag pf, String lable,int pageNo,int pageSize){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);

        MarketData mData = null;
        try {
            transport.open();
            mData =  client.getLeaders(rf,pf,lable,pageNo,pageSize);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return mData;
    }

    /**
     * 获取个股新闻 或 公告
     * @param code                股票代码
     * @param pageindex           待请求页码
     * @param nt                  新闻类型（新闻/公告）
     * @return List<News>
     */
    public NewsList getNews(String code, int pageindex, NewsType nt,CodeType ctype){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);
        NewsList newsData = null;
        try {
            transport.open();
            newsData = client.getNews(code,pageindex,Constants.DEFAULT_PER_PAGE,nt,ctype);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return newsData;
    }

    /**
     * 获取公司概况
     * @param code  股票代码
     * @return CompanyProfile
     */
    public CompanyProfile getCompanyProfile(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);
        CompanyProfile profile = null;
        try {
            transport.open();
            profile =  client.getCompanyProfile(code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return profile;
    }

    /**
     * 获取股东信息
     *
     * @param code                   股票代码
     * @return ShareholderInfo
     */
    public ShareholderInfo getShareholder(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);
        ShareholderInfo shInfo = null;
        try {
            transport.open();
            shInfo = client.getShareholder(code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return shInfo;
    }

    /**
     * 获取资金流向
     * @param code                股票代码
     * @return FundsData
     */
    public FundsData getFunds(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);
        FundsData fData = null;
        try {
            transport.open();
            fData = client.getFunds(code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return fData;
    }

    /**
     * 获取融资融券差额数据
     *
     * @param code                  股票代码
     * @return MarginTrading
     */
    public MarginTrading getMarginTrading(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);
        MarginTrading mTrading = null;
        try {
            transport.open();
            mTrading = client.getMarginTrading(code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return mTrading;
    }

    /**
     * 获取财务业绩数据
     * @param code             股票代码
     * @return FinancePerformance
     */
    public FinancePerformance getFinancePerformance(String code){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);
        FinancePerformance fPerformance = null;
        try {
            transport.open();
            fPerformance = client.getFinancePerformance(code);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return fPerformance;
    }


    /**
     * 获取财务分析数据
     * @param code                 股票代码
     * @param year                 年份
     * @param quarterly            季度
     * @return                     FinanceData
     */
    public FinanceAnalysisData getFinanceAnalysis(String code, int year, int quarterly){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);
        FinanceAnalysisData fData = null;
        try {
            transport.open();
            fData = client.getFinanceAnalysis(code,year,quarterly);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return fData;
    }

    /**
     * 获取成份股                        涨幅榜或跌幅榜
     * @param code                      指数代码
     * @param rf                        涨跌不标识
     * @param count                     总共条数
     * @return BasicStockList
     */
    public BasicStockList getComponentStock(String code,RangeFlag rf,int count){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);
        BasicStockList data = null;
        try {
            transport.open();
            data = client.getComponentStock(code,rf,count);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

    /**
     * 获取最新资讯
     *
     * @param pageSize                             新闻条数
     * @return
     */
    public NewsList getLatestNews(int pageSize){
        TSocket socket = new TSocket(Constants.SERVER_IP, Constants.SERVER_PORT, Constants.TIMEOUT);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "StockService");
        StockService.Client client = new StockService.Client(mp);
        NewsList data = null;
        try {
            transport.open();
            data = client.getNewsList("","壹眼财经",0,pageSize);
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
        return data;
    }

}
