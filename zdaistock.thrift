namespace java com.rjzd.aistock.api   // java
namespace js com.rjzd.aistock.api     //  javascript
namespace cocoa ai

/**
* 预测结果
**/
enum RiseOrFallPrediction{
    RISE = 1,                      //  看涨
    FALL = -1,                    //  看跌
    REMAIN = 0,                   //  看平
    NONE = -2
}

/**
* 错误码定义
**/
enum StatusCode{
    OK = 200 ,                 // 成功
    ERROR = 500,              // 失败
    NULL = 404,               // 字段为空，非法字段
    WAITING_FOR_PRICE = 410,  // 等待定价          8：45-9:15
    WAITING_FOR_OPEN = 412,   // 等待开盘           9.15-9:30
    NON_TRADE_TIME = 417,     // 非交易时间
    MISMATCH = 420,           // 当前手机号和动态密码不匹配
    BINDED = 430,             // 已绑定
    NEWEST = 100,             // 已经是最新版本
    PREDICT_NONE = 600       // 没有AI分析结果
}


/**
* K线类型  日K   周k   月k
**/
enum KType{
    REALTIME,   // 实时行情
    M5,         // 5-分钟线
    M15,        // 15-分钟线
    M30,        // 30-分钟线
    M60,        // 60-分钟线
    DAY,        // 日k
    WEEK,       // 周k
    MONTH       // 月k
}

/**
* 涨跌幅标识
**/
enum RangeFlag{
    RANGE_RISE = 100,                          // 涨幅
    RANGE_FALL = 200,                          // 跌幅
    RANGE_ALL =300                            // 所有
}

/**
* 板块标识
**/
enum PlateFlag{
    INDUSTRY,                          // 行业
    CONCEPT,                          // 概念
    REGION                            // 地区
}

/**
* 新闻类型    新闻/公告/研报
**/
enum NewsType{
    NEWS = 10,                            // 新闻
    ANNOUNCEMENT = 20,                   // 公告
    RESEARCH_REPORT = 30                // 研报
}

/**
* 代码类型   股    指    基    债
**/
enum CodeType{
    STOCK,
    EXPONENT,
    FUND,
    BONDS
}

/**
* 自选股
**/
struct Stock {
    1: required string code,            // 股票代码 必填
    2: required string name,            // 股票名称 必填
    3: required string abbreviation     // 股票名首字母缩写
}

struct StockList {
    1: required StatusCode status,     // 错误码 必填
    2: required list<Stock> data,    // 数据体 必填
    3: required string msg           // 错误信息 必填
}


/**
*  五档
**/
struct FivePosition{
    1: required string action,  // 例：卖1  卖2  买1 买2
    2: required double price,   // 价格
    3: required i64 volume      // 成交量
}

/**
* 股票Tick数据：当前价、最高价、最低价、昨开盘价、今收盘价、成交额、市盈率 ……
**/
struct StockTickData{
    1: required string code,                            // 股票代码
    2: required string name,                            // 股票名
    3: required string time,                            // 时间
    4: required double latestPrice,                     // 最新价
    5: required double range,                           // 涨跌幅
    6: required double high,                            // 最高
    7: required double low,                             // 最低
    8: required double open,                            // 开盘
    9: required double prevClose,                       // 昨日收盘价
    10: required double turnover,                       // 换手率
    11: required double amount,                         // 成交额（元）
    12: required i64 volume,                             // 成交量(股)
    13: optional double per,                            // 市盈率
    14: optional double nmc,                            // 流通市值
    15: optional double mktCap,                         // 总市值
    16: optional i32 riseNum,                           // 股票上涨家数
    17: optional i32 fairNum,							 // 股票平盘家数
    18: optional i32 fallNum,                          // 股票下跌家数
    19: optional list<FivePosition> fivePositionList,   // 五档数据
    20: optional RiseOrFallPrediction prediction,       // 看涨、看跌、看平
    21: optional string predictDate,                    // 预测日期
    22: optional double totalStockNum,                  // 总股本(股数：亿)
    23: optional double tradableStockNum                // 流通股(股数：亿)
}

struct StockData{
    1: required StatusCode status,
    2: required list<StockTickData> data,
    3: required string msg
}

/**
* 绘制K线图所需要数据结构  包括K线 分时  日k 月k
**/
struct Point{
    1: required double price,                   // 当前价
    2: required double close,                   // 收盘
    3: required double open,                    // 开盘
    4: required i64 volume,                     // 成交量(股)
    5: required double high,                    // 最高价
    6: required double low,                     // 最低价
    7: required double average,                 // 均价
    8: required double range,                   // 涨跌幅
    9: required i64 time                        // 时间(毫秒值)
}

struct KData{
    1: required StatusCode status,
    2: required list<Point> data,
    3: required string startTime,              // 起始时间   例：2017-01-01 13:00:00
    4: required string endTime,                // 终止时间   例：2017-01-05 15:00:00
    5: required string msg
}

/**
* 变化区间
**/
struct Range{
    1:required double min,                     // 最小值
    2:required double max,                     // 最大值
    3:required bool isMinValid,                // 是否有最小值
    4:required bool isMaxValid                 // 是否有最大值
}

/**
*  自定义选股-筛选条件
**/
struct Condition{
    1:optional Range close,                      // 收盘价
    2:optional Range amount,                     // 当日成交额
    3:optional Range amountOf5d,                 // 5日成交额
    4:optional Range amountOf20d,                // 20日成交额
    5:optional Range amountOf60d,                // 60日成交额
    6:optional Range amountOf120d,               // 120日成交额
    7:optional Range amountOf250d,               // 250日成交额

    8:optional Range range,                      // 当日涨幅
    9:optional Range rangeOf5d,                  // 5日涨幅
    10:optional Range rangeOf20d,                // 20日涨幅
    11:optional Range rangeOf60d,                // 60日涨幅
    12:optional Range rangeOf120d,               // 120日涨幅
    13:optional Range rangeOf250d,               // 250日涨幅

    14:optional Range turnover,                  // 当日换手率
    15:optional Range turnoverOf5d,              // 5日换手率
    16:optional Range turnoverOf20d,             // 20日换手率
    17:optional Range turnoverOf60d,             // 60日换手率
    18:optional Range turnoverOf120d,            // 120日换手率
    19:optional Range turnoverOf250d,            // 250日换手率

    20:optional Range riseStay,                  // 涨停天数
    21:optional Range fallStay,                  // 跌停天数

    22:optional Range valuation,                 // 总市值
    23:optional Range circulationValue,          // 流通市值

    24:optional Range amplitude,                 // 当日振幅
    25:optional Range amplitudeOf5d,             // 5日振幅
    26:optional Range amplitudeOf20d,            // 20日振幅
    27:optional Range amplitudeOf60d,            // 60日振幅
    28:optional Range amplitudeOf120d,           // 120日振幅
    29:optional Range amplitudeOf250d,           // 250日振幅

    30:optional Range listedDays,           	   // 上市天数
    31:optional string industry,               	// 行业
    32:optional string concept,                 	// 概念
    33:required i32 pageNum,                      // 请求页码
    34:required i32 stockCountPerPage             // 每页请求股票个数
}

/**
*  自定义选股-满足条件的股票
**/
struct FilterStockResult{
    1:required string name,                     // 股票名
    2:required string code,                     // 股票代码
    3:required double close,                    // 收盘价

    4:optional double amount,                   // 当日成交额
    5:optional double amountOf5d,               // 5日成交额
    6:optional double amountOf20d,              // 20日成交额
    7:optional double amountOf60d,              // 60日成交额
    8:optional double amountOf120d,             // 120日成交额
    9:optional double amountOf250d,             // 250日成交额

    10:required double range,                   // 当日涨幅
    11:optional double rangeOf5d,              	// 5日涨幅
    12:optional double rangeOf20d,             	// 20日涨幅
    13:optional double rangeOf60d,             	// 60日涨幅
    14:optional double rangeOf120d,            	// 120日涨幅
    15:optional double rangeOf250d,            	// 250日涨幅

    16:optional double turnover,                // 当日换手率
    17:optional double turnoverOf5d,            // 5日换手率
    18:optional double turnoverOf20d,           // 20日换手率
    19:optional double turnoverOf60d,           // 60日换手率
    20:optional double turnoverOf120d,          // 120日换手率
    21:optional double turnoverOf250d,          // 250日换手率

    22:optional i16 riseStay,                   // 涨停天数
    23:optional i16 fallStay,                   // 跌停天数

    24:optional double valuation,               // 总市值
    25:optional double circulationValue,        // 流通市值

    26:optional double amplitude,               // 当日振幅
    27:optional double amplitudeOf5d,           // 5日振幅
    28:optional double amplitudeOf20d,          // 20日振幅
    29:optional double amplitudeOf60d,          // 60日振幅
    30:optional double amplitudeOf120d,         // 120日振幅
    31:optional double amplitudeOf250d,         // 250日振幅

    32:optional i32 listedDays           		  // 上市天数
    33:optional string industry,                // 行业
    34:optional string concept,                 // 概念
    35:required i32 pageNum                     // 当前页码
}

/**
* 满足条件股票列表
**/
struct FilterStockList{
    1: required StatusCode status,
    2: required list<FilterStockResult> data,
    3: required string msg,
    4:required i32 total,                       // 返回数据总条数
    5:required i32 totalPage                   // 返回数据总页数
}

/**
* 新闻公告
**/
struct News{
    1: required string title,                // 新闻标题
    2: required string newsDetail,           // 新闻内容详情
    3: required string publicDate,           // 新闻发布时间
    4: required string origin,               // 新闻来源
	5: required bool isHeadline,			 // 新闻是否头条
    6: optional string url,                  // 新闻详情地址
    7: optional string summary,              // 新闻摘要
	8: optional string imageUrl,             // 新闻图片地址
	9: optional string type,                 // 新闻分类
    10: required string newsId               // 新闻id 
}

struct NewsList{
     1: required StatusCode status,
     2: required list<News> data,
     3: required string msg,
     4: optional i32 pageNo,                   // 当前页码
     5: required i32 totalPage                 // 总共多少页
}

/**
* 大盘数据 用于绘制3D柱状图 和 网格表
**/
struct Market{
    1:required string stockName,                          // 股票名称
    2:optional string plateName,                          // 板块名称
    3:required double rangePercentage,                    // 涨跌幅  例: 5.36
    4:optional string stockCode,                             // 股票代码
    5:optional double latestPrice                        // 当前价  例：15.36
}

struct MarketData{
    1: required StatusCode status,
    2: required list<Market> data,
    3: optional i32 totalPage,                // 总页数
    4: required string msg
}

/**
*  定义name value  unit 供财务分析、基本资料等用，避免重复定义
**/
struct NameValuePairs{
    1: required string name,                                     // 显示名称
    2: required string value,                                    // 显示值
    3: optional string unit                                      // 计量单位
}
/**
*  基础类别
**/
struct BasicCategory{
    1:required string category,                                 // 指标类别    例：关键指标、利润表、资产负债表、现金流表、成长能力指标、盈利能力指标等
    2:required list<NameValuePairs> analysisOptions             // 指标项列表
}

/**
* 公司高管
**/
struct Executive{
    1: required string name,                                  // 姓名
    2: required string position,                              // 职位
    3: required string startDate,                             // 开始日期
    4: required string endDate                                // 结束日期
}

/**
* 分红送配
**/
struct Dividends{
    1: required string year,
    2: required string plan,
    3: required string xrDate
}

/**
*  公司概况
**/
struct CompanyProfile{
    1: required StatusCode status,
    2: required list<NameValuePairs> basicInfo,                    // 基本资料  例： 公司名    公司股票代码   上市日期   发行价格   发行量等
    3: required list<Executive> executiveList,                     // 公司高管
    4: required list<Dividends> dividendsList,                     // 分红送配
    5: required string msg
}

/**
* 十大流通股 和 机构持股
**/
struct Shareholder{
    1: required string name,                                      // 股东名 (公司或机构)
    2: required string proportion,                                // 持股占比
    3: optional string change,                                    // 变动情况   例：增持450.5
    4: optional string quantity                                   // 持仓量 (万股)
}

/**
* 股东信息
**/
struct ShareholderInfo{
    1: required StatusCode status,
    2: required list<NameValuePairs> capitalStructure,              // 股本结构
    3: required list<Shareholder> circulationShareholderOf10        // 十大流通股
    4: required string circulationDate,                             // 十大流通股上报时间
    5: required list<Shareholder> organizationShareholders,         // 机构持股
    6: required string organizationDate,                            // 机构持股上报时间
    7: required string msg
}

/**
* 资金流向
**/
struct Funds{
    1: required double zhuliInRate,                               // 主力流入百分率
    2: required double zhuliOutRate,                              // 主力流出百分率
    3: required string zhuliIn,                                   // 主力流入
    4: required string zhuliOut,                                  // 主力流出
    5: required string zhuliNetIn,                                // 主力净流入
    6: required double sanhuInRate,                               // 散户流入百分率
    7: required double sanhuOutRate,                              // 散户流出百分率
    8: optional string sanhuNetIn,                               // 散户净流出
    9: optional string inTotal,                                  // 流入总和
    10: optional string outTotal                                 // 流出总和
}

struct FundsData{
    1: required StatusCode status,
    2: required Funds funds,
    3: required string msg
}

/**
* 融资融券差额
**/
struct MarginTrading{
    1: required StatusCode status,
    2: required string startTime,                                 // 起始时间
    3: required string endTime,                                   // 终止时间
    4: required list<double> points,                              // 点对应的值 列表
    5: string msg
}

/**
* 财务分析数据
**/
struct FinanceAnalysisData{
    1:required StatusCode status,
    2:optional string reportDate,                                    // 报告日期    例：2016年三季度报
    3:required list<BasicCategory> data,
    4:string msg
}

/**
* 财务业绩数据
**/
//   营业收入
struct BusinessIncome{
    1:required double businessIncomeQ1,                        // 第一季度营收
    2:required double businessIncomeQ2,                        // 第二季度营收
    3:required double businessIncomeQ3,                        // 第三季度营收
    4:required double businessIncomeQ4,                        // 第四季度营收
    5:required double incomeYoy,                               // 年度营收同比增长   例: 25.36
    6:required string unit                                     // 每季度营收计量单位
}
//   利润
struct Profits{
    1: required double netProfits,                             // 净利润（年度）
    2: required double profitsYoy,                             // 净利润同比增长    例： 20.25
    3:required string unit                                     // 净利润计量单位    例：
}
//   业绩
struct Performance{
    1: required list<string> years,
    2: optional string latestYear,                            // 最新财报显示年份
    3: optional string latestQuarterly,                       // 最新财报显示季度
    4: required list<BusinessIncome> businessIncomes,         // 营业收入
    5: required list<Profits> profits,                        // 利润
    6: required list<double> eps                             // 年度每股收益
}

struct FinancePerformance{
    1: required StatusCode status,
    2: required Performance data,
    3: required string msg
}

//  ---------------------------------------prediction domain start -----------------------------------------------------

/**
*  股票预测
**/
struct PredictionSummary{
    1:required RiseOrFallPrediction pResult,                //预测结果  （涨 跌 平）
    2:required double pClose,                              //预测收盘价
    3:required double pRange                               //预测涨跌幅
}

/**
* 股票预测影响因子
**/
struct PredictFactor{
    1:required string factorName,                           //因子名称
    2:required double factorValue                           //因子的值
}

/**
* 多因子预测结果
**/
struct MultiDimensionPrediction{
    1:required StatusCode status,
    2:required list<PredictFactor> data,                //影响的因子
    3:optional string summary,                         // 多因子预测概述
    4:required RiseOrFallPrediction predictResult,      // 预测结果
    5:required string msg
}


struct PredictionLine{
    1:required string startTiem,
    2:required string endTime,
    3:required list<double> priceList
}
/**
* 预测走势
**/
struct PredictionTrend{
    1:required PredictionLine actualData,
    2:required PredictionLine predictData,
    3:required string summary
}

struct PredictionTrendData{
    1:StatusCode status,
    2:PredictionTrend data,
    3:string msg
}

/**
* 机构持仓
**/
struct FundHolding{
    1:required i32 year,                                // 年份  例：4
    2:required i32 quarter,                             // 季度  例：2016
    3:required i32 holdTotal,                           // 持有机构家数   例：128
    4:required double count,                           // 机构持股数（万股） 例：487596.36
    5:required double holdPercentage,                  // 机构持股占有流通盘比率 例3.65
    6:required double price                            // 股票价格 例：5.63
}

struct FundHoldingData{
    1:required StatusCode status,
    2:required list<FundHolding> data,
    3:required string msg
}

/**
* 基本面数据
**/
struct Fundamentals{
    1:required double pb,                                // 市净率             例：13.65
    2:required double pe,                                //市盈率              例：13.65
    3:required double eps,                                //每股收益（元）       例：1.68
    4:required double fixedAssets,                       // 固定资产（万元）    例：12586.68
    5:required double mktcap,                            // 总市值（万元）      例：12586.68
    6:required string stockName
}

struct FundamentalsData{
    1:required StatusCode status,
    2:required Fundamentals selfFundamental,                      // 当前股票基本面数据
    3:required Fundamentals industryAvg,                          // 平均值
    4:required Fundamentals first,                               // 行业top one
    5:required string industryName,                             // 所属行业
    6:required i32 total,                                        // 本行业总共多少支股票
    7:required i32 rank,                                         //行业排名
    8:required string msg
}

/**
* 宏观因子数据
**/
struct Macro{
    1:required list<double> exponent1,                        // 第一关联指数值
    2:required list<double> price,                            // 股价
    3:required string exponentName1,                          // 第一关联指数名
    4:required string exponentName2,                          // 第二关联指数名指数名
    5:required list<double> exponent2,                        // 第二关联指数值
    6:required list<string> dateList                          // 日期   例:2010-07
}

struct MacroData{
    1:required StatusCode status,
    2:required Macro data,
    3:required string msg
}


/**
*股票预测因子详情
**/
struct PredictFactorLineData{
    1:required StatusCode status,
    3:required list<string> periods,                         //时间轴
    4:required list<string> elements,                       // 构成因子的要素
    5:required list<list<double>> labelMapValues,           // 曲线对应的值
    6:required string msg
}

/**
*股票预测-周期性分析
**/
struct Periodicity{
    1:required string date ,                         //日期
    2:required double close,                         //收盘价
    3:optional double lpf,                           //低频波动，长期趋势
    4:required i32 segmentPoint                      //主升主跌浪分割点  1主升，2主跌，3浪型的分割点
}

/**
*股票预测-周期性分析
**/
struct PeriodDataList{
    1: required StatusCode status,
    2: required list<Periodicity> data,                      //日期
    3: optional string summary,                             // 周期性预测概述
    4: required RiseOrFallPrediction predictResult,         // 预测结果
    5: required string msg
}

/**
* 日本云
**/
struct IchimokuData{
    1:required string date,                         //日期
    2:required double close,                        //收盘价, 黑色
    3:required double Tenkan,                       //转折线, 红色
    4:required double Kijun,                        //基准线, 蓝色
    5:required double SenkouA,                      //先行上线(A线), 云 黄色
    6:required double SenkouB,                      //先行下线(B线), 云 黄色
    7:required double Chikou                        //延迟线, 绿色的
}
/**
* 日本云指标特性
**/
struct CloudFeature{
    1:required list<string> goodList,               // 利好的指标
    2:required list<string> badList,                // 利空的指标
    3:required list<string> neutralList             // 中性指标
}
struct IchimokuDataList{
    1: required StatusCode status,
    2: required list<IchimokuData> data,                      //日期
    3: optional string summary,                              //预测概述
    4: required CloudFeature feature,                         // 指标特征
    5: required RiseOrFallPrediction predictResult,           // 预测结果
    6: required string msg
}

/**
* 关联股
**/
struct RelatedStock{
    1: required string name,                       //  关联股票名称
    2: required string code,                       //  关联股票代码
    3: required double range                       //  所关联的股票涨跌幅
    4: optional double relationValue,              //  关联系数
    5: optional string industryName               // 所属行业名
}
struct RelatedList{
    1: required StatusCode status,
    2: required list<RelatedStock> data,
    3: optional string summary,                               // 关联股概述
    4: required RiseOrFallPrediction predictResult,           // 预测结果
    5: required string msg
}

// --------------------------------------------- end of prediction domain-------------------------------

struct BasicStock{
    1: required string name,                                  // 股票名
    2: required string code,                                  // 股票代码
    3: required double range,                                 // 涨跌幅      例： 6.35
    4: required double price                                  // 股价        例： 17.85
}

struct BasicStockList{
    1: required StatusCode status,
    2: required list<BasicStock> data,
    3: required string msg
}


/**
* -----------------------------------------about AI domain  start -----------------------------------
* AI 机器人策略
**/

// AI 收益标识
enum AIIncomeType{
    LATEST_INCOME_WEEK = 7,                             // 近一周
    LATEST_INCOME_MONTH = 30,                           // 近一月
    INCOME_TOTAL = 10                                   // 总收益
}


/**
* 调仓动作  买入或者卖出
**/
enum TransferType{
    BUY = 10,
    SELL = 20
}

// 机器人收益情况
struct AIIncomeInfo{
    1: required double startFund,                   // 启动资金
    2: required double achievement,                 // 机器人成就（总收益百分比）
    3: required double compareResult,               // 大盘收益百分比
    4: required list<double> incomePoints,          // ai收益曲线所有点(比率)
    5: required list<double> hs300IncomePoints      // 沪深300曲线所有点(比率)
    6: required string startTime,                   // 开始时间
    7: required string endTime,                     // 结束时间
    8: required double totalCapital,                // 总资产
    9: required double stockCapital,                // 股票资产
    10: optional list<string> tradeDates,           // 交易日
	11: optional string strategyName                // 策略名称
}

// 机器人基本资料
struct AIInfo{
    1: required string id,                           // AI机器人id，身份标识
    2: required string name,                         // 机器人名称
    3: required string avatar,                       // 机器人头像
    4: required i32 totalAttention,                  // 总关注人数
    5: required string onlineTime,                   // 上线时间
    6: required list<string> characteristics,        // 机器人擅长特点
    7: required string introduce,                    // 机器人介绍
    8: optional AIIncomeInfo totalIncome,            // 当前机器人总收益情况
	9: optional string aliasName                     // 机器人别名，用于分享
}

struct AIInfoData{
    1: required StatusCode status,
    2: required AIInfo data,
    3: required string msg
}

// AI操作收入
struct AIIncome{
    1: required StatusCode status,
    2: required AIIncomeInfo data,
    3: required string msg
}

// 机器人列表
struct AIList{
    1: required StatusCode status,
    2: required list<AIInfo> data,
    3: required string msg
}

/**
* 关注机器人列表
* 2:机器人id或接口机器人别名
**/
struct AIAttention{
    1: required StatusCode status,
    2: required list<string> data,
    3: required string msg
}



// 调仓动作
struct AITransfer{
    1: required TransferType type,                    // 调仓类型， 买/卖
    2: required string stockCode,					  // 股票代码
    3: required string stockName,                     // 股票名字
    4: required double buyPrice,                      // 买入价
	5: required double sellPrice,                     // 卖出价或者最新价
	6: required double profit,                        // 盈亏绝对值
	7: required double profitPercentage,              // 盈亏百分比
	8: required i32 quantity,                         // 买卖数量
	9: required double positionPercentage,           // 买入时持仓百分比
    10:required string actionTime,                    // 操作时间
    11: required string actionReason,                // 操作理由 例：板块轮动规则[上海本地，自贸区]被自贸区上涨触发，从而在上海本地板块中选择华鑫股份（600193）...
	12: optional i64 transferId,                     // 调仓Id
	13: optional string strategyName                 // 策略名称
}

// 按照时间分组后的调仓动作
struct DateTransfer{
    1: required string actionTime,                    // 操作时间
    2: required list<AITransfer> transfers            // 调仓动作
}

//  历史盈亏
struct AIOperation {
    1: required string stockCode,                     // 股票代码
    2: required string stockName,                     // 股票名
    3: required double buyPrice,                      // 买入价
	4: required double sellPrice,                     // 卖出价或者最新价
	5: required double profit,                        // 盈亏绝对值
	6: required double profitPercentage,              // 盈亏百分比
    7: required i32 quantity,                         // 买卖数量
    8: required i32 pos,                              // 现在的持仓数
    9: required double positionPercentage,            // 持仓百分比
    10: required string buyTime,                      // 买入时间
    11: required string sellTime,                     // 卖出时间
	12: required string buyReason,                    // 买入理由
    13: required string sellReason                    // 卖出理由
    14: optional list<double> hs300ProfitPoints,      // ai该持仓收益曲线所有点(比率)
    15: optional list<double> aiProfitPoints          // hs300该持仓收益曲线所有点(比率)
	16: optional i64 operationId,                     // 操作Id
	17: optional string strategyName                  // 策略名称
}

struct AIOperationList{
    1: required StatusCode status,
    2: required list<AIOperation> data,
    3: required string msg,
    4: optional i32 totalPage
}


// AI机器人调仓动态
struct TransferActionList{
    1: required StatusCode status,
    2: required list<AITransfer> data,
    3: required i32 totalPage,
    4: required string msg
}

struct DateTransferList{
    1: required StatusCode status,
    2: required list<DateTransfer> data,
    3: required i32 totalPage,
    4: required string msg
}

//  ----------------------------------- ai domain end -----------------------------------


// ----------------------------------- user start --------------------------------
/**
* 动态验证码
**/
struct SendCode{
    1: required StatusCode status,
    2: required string msg
}

/**
*  用户信息
**/
struct UserInfo{
    1: required i32 userId,                    // 用户id
    2: required string phoneNumber,           // 手机号
    3: required string nickname,              // 昵称
    4: optional string avatar,                // 头像
    5: optional i32 sex,                      // 0 保密  1男   2女
    6: optional string area,                  // 地区
    7: optional bool isBindWX,                // 是否绑定微信
    8: optional bool isBindQQ,                // 是否绑定QQ
    9: optional bool isBindSina,              // 是否绑定微博
    10:optional list<AIInfo> aiList,          // 关注机器人列表Id
    11:optional i32 level,                    // 用户等级
    12:optional i32 points,                   // 积分
    13:optional string inviteCode            // 邀请码
}

struct UserData{
    1: required StatusCode status,
    2: required UserInfo data,
    3: required string msg
}

/**
* 用户积分
**/
struct UserPoints{
    1: required StatusCode status,
    2: required i32 points,                      // 积分
    3: required i32 level,                      // 用户等级
    4: required string msg
}

/**
*  邀请好友
**/
struct Invite{
    1:required i32 userId;                              // 好友id
    2:optional string avatar,                          // 好友头像
    3:required string nickName,                        // 好友昵称
    4:required string registerTime,                    // 注册时间
    5:required i32 gainPoints                          // 获得积分
}

struct InviteData{
    1: required StatusCode status,
    2: required list<Invite> data,
    3: required string msg,
    4: required i32 totalInvite,                        // 邀请好友总数
    5: required i32 totalPage                           // 总页数
}

/**
* 积分特权
**/
struct Privilege{
    1: required string privilegeId,                               // 特权id
    2: required i32 costPoints,                                   // 解锁特权所需要积分
    3: required i32 unlock                                        // 是否解锁     0 未解锁     1已解锁
}

struct PrivilegeData{
    1: required StatusCode status,
    2: required list<Privilege> data,
    3: required string msg
}

/**
*  积分任务
**/
struct TaskStatus{
    1: required string taskTypeId,                                // 任务类型id
    2: required i32 gainPoints,                                   // 完成任务所获得积分
    3: required i32 finishFlag                                    // 任务完成标识     0 未完成     1完成
}

struct TaskStatuData{
    1: required StatusCode status,
    2: required list<TaskStatus> data,
    3: required string msg
}

/**
*  积分记录
**/
struct PointsRecord{
    1:required string event,                                // 获取或花费积分事件（解锁每日金股、邀请好友等）
    2:required i32 points,                                  // 获取或花费积分
    3:required string date                                 // 获取或花费时间
}

struct PointsRecordData{
    1: required StatusCode status,
    2: required list<PointsRecord> data,
    3: required string msg,
    4: required i32 totalPage
}

enum LoginType{
    QQ = 1,
    WEIXIN = 2,
    SINA = 3
}

enum BindType{
    QQ = 1,
    WEIXIN = 2,
    SINA = 3,
    MOBILE = 4
}

enum AttentionFlag{
    ADD =1,
    CANCEL = 2
}

// 三方登录信息
struct OAuthInfo{
    1: required string openId,                // QQ、微信、sina Id
    2: required LoginType type,                // QQ、微信、sina
    3: required string nickname,              // 昵称
    4: optional string avatar,                // 头像
    5: optional string sex,                   // 0 保密  1男   2女
    6: optional string area,                  // 地区
    7: optional string wxOfficialAccountsOpenId // 微信公众账号openId
}

struct IsSuccess{
    1: required StatusCode status,
    2: required bool data,
    3: required string msg
}

/**
* 消息推送状态
**/
struct PushStatus{
    1: required StatusCode status,
    2: required map<string,bool> data,
    3: required string msg
}

// --------------------  USer end ------------------------------------------

// --------------------  Notification domain start -------------------------

/**
* 公告
**/
struct Announcement{
    1: required string newsId,               // 新闻id
    2: required string title,                // 新闻标题
    3: required string url,                  // 新闻详情地址
	4: required string imageUrl,             // 新闻图片地址
	5: required i32 hasRead,                 // 是否已读  0-未读   1-已读
    6: optional string publishTime           // 公告发布时间
}

struct AnnouncementData{
    1: required StatusCode status,
    2: required list<Announcement> data,
    3: required string msg,
    4: required i32 totalPage                  // 总共多少页
}

struct Count{
    1: required StatusCode status,
    2: required i32 data,
    3: required string msg
}

/**
* AI周报
**/
struct AIWeekly{
    1: required string weeklyTitle,
    2: required string detailUrl,
    3: optional bool read
}

struct AIWeeklyData{
    1: required StatusCode status,
    2: required list<AIWeekly> data,
    3: required string msg
}

// --------------------  Notification end ------------------------------------------



//  版本更新信息
struct AppUpdateInfo{
    1: required string appSname,                           // 应用名称
    2: required string appVersionName,                     // 应用版本名称
    3: required string appPackage,                         // 应用包标识符
    4: required i32 appVersionCode,                        // 应用版本号
    5: required string appUrl,                            // 应用全量更新包下载地址
    6: optional i64 appSize,                              // 应用全量更新包大小
    7: required string appUpdateLog,                      // 新版本更新说明
    8: required bool force,                              // 是否需要强制升级
    9: optional string updateTime                        // 更新时间
}

struct AppUpdate{
    1: required StatusCode status,
    2: optional AppUpdateInfo data,
    3: required string msg
}

struct HistoryVersion{
    1: required StatusCode status,
    2: required list<AppUpdateInfo> data,
    3: required i32 totalPage,
    4: required string msg
}



/**
* 股票信息类
*/
service StockService {
	// 判断是否交易日
	bool isTradeDate(),

	// 根据输入的搜索条件获取股票   selection = "" 表示获取全部股票，selection (股票代码 股票名称 股票所写 股票拼音)
    StockList getStock(1: string selection),

	// 刷新自选股当前价格和涨跌幅    1 股票或指数代码
	StockData refreshStocks(1: list<string> codeList),

	// 获取K线数据  用于绘制K线图   1 股票或指数代码    2  分时、日K、周K、月K      3 开始分时图时间 例：9:36
    KData getStockKData(1: string code, 2: KType type,3:string startMinute),

    // 自定义选股-根据筛选条件获取符合条件的股票列表
    FilterStockList getOptionalStocks(1:Condition conditions),

    // 获取个股新闻、公告、研报   1 股票代码或指数代码   2 页码    3  每页请求条数   4 新闻类型（新闻 公告 研报） 5  代码类型（股票  指数）   [已弃用]
    NewsList getNews(1: string code,2: i32 pageNum,3: i32 newsCountPerpage,4: NewsType tp,5:CodeType codeType),
	
	// 存储新闻 [for web ]
	bool storeNews(1: string title, 2: string newsDetail, 3: string publicDate, 4: string origin, 5: bool isHeadline, 6: string url, 7: string summary, 8: string imageUrl, 9: string type )
	
	// 获取新闻列表 [for web ]
    NewsList getNewsList(1: string startDate, 2: string newsType, 3: i32 pageNo, 4: i32 pageSize),

    // 获取一眼财经头条新闻 [for web ]
    NewsList getHeaderLine(),
	
	// 获取某一个新闻 [for web ]
	NewsList getNewsById(1: string newsId),

    // 获取大盘数据  rf：涨幅/跌幅   1：行业/概念/区域     2:版块类型       3：待请求页码     4：每页请求条数
    MarketData getMarket(1: RangeFlag rf,2: PlateFlag pf,3: i32 pageNo,4: i32 pageSize),

    // 获取某一行业领涨或领跌   1：行业/概念/区域     2:版块类型    3：板块名称
    MarketData getLeaders(1: RangeFlag rf,2: PlateFlag pf,3: string label, 4: i32 pageNo,5: i32 pageSize),

    // 获取关联股相关数据 [ 已弃用]
    MarketData getRelationStocks(1: RangeFlag rf,2: PlateFlag pf,3: string code,4: i64 total),

    // 获取公司概况       1：股票代码
    CompanyProfile getCompanyProfile(1:required string code),

    // 获取公司股东信息   1：股票代码
    ShareholderInfo getShareholder(1:required string code),

    // 获取资金流向      1：股票代码
    FundsData getFunds(1:required string code),

    // 获取融资融券差额（资金） 1：股票代码
    MarginTrading getMarginTrading(1:required string code),

    // 获取财务业绩数据       1：股票代码
    FinancePerformance getFinancePerformance(1:required string code),

    // 获取财务分析数据   1  股票代码   2  年份   3 季度     注：year=0 && quarterly=0 表示获取最新的财务分析数据
    FinanceAnalysisData getFinanceAnalysis(1:required string code,2: i32 year,3: i32 quarterly),

    // 获取大盘指数成份股的涨幅榜或跌幅榜        1  指数代码    2 涨幅榜/跌幅榜    3  总数 (默认传10代表top10，Integer.MAX_VALUE表示全部)
    BasicStockList getComponentStock(1: string code,2: RangeFlag rf,3: i32 count)

}

/**
* 用户类
*/
service UserService{
    // 获取动态密码      1：手机号
   SendCode getSendCode(1: string phoneNumber),

   // 动态密码登录        1：手机号   2：验证码
   UserData dynamicLogin(1: string phoneNumber,2:string code),

   // 邀请好友注册时验证手机是否注册过     1：手机号
   IsSuccess inviteCheck(1: string phoneNumber),

   // 邀请好友注册     1：手机号   2：验证码     3：邀请码
   IsSuccess inviteRegister(1: string phoneNumber,2:string code,3:string inviteCode),

   // 利用QQ、Weixin、Sina等第三方认证登陆
   UserData oauthLogin(1: OAuthInfo params),

   // 获取用户数据        1：永用户id
   UserData getUserInfo(1:i32 userId),

   // 获取用户积分        1：永用户id
   UserPoints getMyPoints(1:i32 userId),

   // 最近一天用户获取的积分   1：永用户id
   UserPoints getRecentGainPoints(1:i32 userId),

   /**
   * 绑定账号或手机号       1  当前登录用户id      2  绑定值（QQ、微信、Sina id 或手机号）     3  绑定类型
   * 注：BindType.MOBILE,bindValue需要传入手机号;验证码，例  15236187285;7569
    **/
   IsSuccess binding(1: i32 uid,2:string bindValue,3:BindType type),

   // 添加自选股            1： 用户id     2：股票代码
   IsSuccess addPortfolio(1:i32 userId,2: string code),

   /**
   * 将本地的自选股列表同步到server      1：用户id       2：本地自选股列表
   *
   * return：同步后所有自选股票列表
   **/
   list<string> synchronizePortfolio(1:i32 userId,2:list<string> codeList),

   // 删除自选       1：用户id          2：股票代码
   IsSuccess deletePortfolio(1:i32 userId,2:string code),

   /**
   *    获取自选股     1：用户id
   *    return：用户所有自选股票列表
   **/
   list<string> getPortfolio(1:i32 userId),

   // 添加关注AI机器人  1：ai机器人id           2：用户id         3：添加/取消关注
   IsSuccess addOrCancelAttention(1: string aiId,2: i32 userId,3:AttentionFlag flag),

   // 获取我的邀请好友   1: 用户id        2：待请求页码        3：每页条数
   InviteData getMyInvite(1:i32 userId,2:i32 pageNo, 3: i32 numPerPage),

   // 获取特权
   PrivilegeData getPrivilegeStatus(1:i32 userId,2:list<string> privilegeIds),

   // 解锁特权                1:用户id          2：特权id
   IsSuccess unlockPrivilege(1:i32 userId,2:string privilegeId),

   // 获取我的任务            1:用户id         2：任务类型id列表
   TaskStatuData getMyTaskStatus(1:i32 userId,2:list<string> taskTypeIds),

   // 获取积分记录            1:用户id         2：待请求页码       3：每页请求条数
   PointsRecordData getPointsRecord(1:i32 userId,2:i32 pageNo,3: i32 numPerPage),

   // 每日活跃赚积分          1:用户id
   IsSuccess earnPointsBydailyActive(1:i32 userId),

   // 分享APP赚积分          1:用户id
   IsSuccess earnPointsByshare(1:i32 userId),

   // 获取消息推送状态       1:用户id
   PushStatus getPushStatus(1: i32 userId),

    /**通知推送设置
    * 1: 用户id
    * 2: 推送类型           PushAll（全部消息）；PushAITransfer（机器人调仓）；PushDailyBestPlate（每日牛版）；PushDailyBestStock（每日金股）；PushInvite（邀请好友）；PushAIWeekly（AI周报）
    * 3：是否接受消息提醒   true-是   false-否
    **/
   IsSuccess pushSettings(1: i32 userId,2: string pushType,3: bool isNeedRemind),

   // 自选股置顶、置底操作          1: 用户id      2: 股票列表      3: 0-置顶    1-置底
   list<string> stickTopOrBottom(1: i32 userId,2: list<string> stockCodes,3: i32 action),


  // 自选股移位操作                1: 用户id      2: 股票列表      3: 位移，大于0上移，小于0下移
  list<string> movePosition(1: i32 userId,2: list<string> stockCodes,3: i32 shift)


}


/**
* 系统类
*/
service SystemService{
    // 发布新版本信息
    IsSuccess releaseAppVersion(1: AppUpdateInfo appInfo),

    // 获取历史版本    1  平台（android/ios）    2  当前页码           3 每页多少条
    HistoryVersion getAppHistoryVersion(1: string plaform,2: i32 pageNo,3: i32 pageSize),

    // 应用检查更新           1  应用唯一标识         2  版本号
    AppUpdate checkUpdate(1: string appPackage,2: i32 versionCode)
}

struct Prediction{
    1: required StatusCode status,
    2: required double predictionRange,              // 预测涨跌幅   例: 12.36
    3: optional double predictionPrice,              // 预测股价     例: 5.68
    4: required string predictDate,                  // 预测时间     例：5月19日
    5: required string msg
}

struct PredictionKPS{
    1: required StatusCode status,
    2:required double score,                        // 评分
    3:required string summary,                      // 总结
    4:required string kpsDate,                      // 评分时间
    5: required string msg
}


/**
* 推荐理由
**/
struct RecommendReason {
	1: required string unit,						// 单位
	2: required list<double> data,					// 数据列表
	3: required string title,						// 标题
	4: required string summary                      // 总结
}


/**
* 板块推荐
**/
struct PlateRecommendation{
	1:required PlateFlag plateType,					      // 板块类型：industry/concept/area
	2:required string plateName,                          // 板块名字
	3:required i32 recommendIndex,                        // 推荐强度   1，2，3，4，5
	4:required list<double> rangeList,                    // 过去5天涨跌幅
	5:required list<RecommendReason> reasonList,          // 理由支撑数据， 例如 <过去5天资金流入量, list<0.1, 0.1, 0.1, 0.1, 0.1>>
	6:required double rangeOfRecommendDay,                // 推荐日涨幅，-100. 意味着未知
    7:required list<BasicStock> stockLeaders              // 推荐日领涨股列表
}


struct DailyPlateRecommendation {
	1:required string predictDate,							// 推荐日期  例:2017-08-03
	2:required list<PlateRecommendation> recommendList		// 推荐列表
}

/**
* 股票推荐
**/
struct StockRecommendation{
	1:required string stockCode,                          // 股票代码
	2:required string stockName,                          // 股票名称
	3:required i32 recommendIndex,                        // 推荐强度   1，2，3，4，5
	4:required list<double> rangeList,                    // 过去5天涨跌幅
	5:required list<RecommendReason> reasonList,          // 理由支撑数据， 例如 <过去5天资金流入量, list<0.1, 0.1, 0.1, 0.1, 0.1>>
	6:required double rangeOfRecommendDay                // 推荐日涨幅，-100. 意味着未知
}

/**
* 板块推荐Response
**/
struct PlateRecommendationData{
    1:required StatusCode status,
	2:required list<DailyPlateRecommendation> dailyRecommendList,   // 每日推荐板块， 例如<2017-07-30, 板块推荐列表>
	3:required i32 totalPage,
    4:required string msg
}

struct DailyStockRecommendation {
	1:required string predictDate,							// 推荐日期  例:2017-08-03
	2:required list<StockRecommendation> recommendList		// 推荐列表
}

/**
* 股票推荐Response
**/
struct StockRecommendationData{
    1:required StatusCode status,
	2:required list<DailyStockRecommendation> dailyRecommendList,   // 每日推荐股票， 例如<2017-07-30, 板块推荐列表>
	3:required i32 totalPage,
    4:required string msg
}

/**
* 推荐类服务
**/
service RecommendService{
    //  获取板块推荐结果          1：待请求页码           2：每页条数
    PlateRecommendationData getPlateRecommendation(1:i32 pageNo, 2:i32 pageSize),
	
	//  获取股票推荐结果         1：待请求页码           2：每页条数
    StockRecommendationData getStockRecommendation(1:i32 pageNo, 2:i32 pageSize)
}

/**
* 股票预测类
**/
service PredictService{
    //  获取综合预测结果        1: 股票代码
    Prediction getComplexPrediction(1: string code ),

    // 获取综和评分            1: 股票代码
    PredictionKPS getKPS(1: string code ),

    //获得多维预测结果          1:  股票代码
    MultiDimensionPrediction getPrediction(1:string code),

    // 获取雷达因子预测走势，绘制预测走势曲线      1： 代码             2：因子名称
    PredictionTrendData getPredictionTrend(1:string code,2:string factorName)

    //获取机构持仓因子数据       1:  股票代码
    FundHoldingData getFundHolding(1:string code),

    // 获取基本面因子数据        1:  股票代码
    FundamentalsData getFundamentals(1:string code),

    // 获取宏观因子数据          1:  股票代码
    MacroData getMacro(1:string code),

    //获得股票预测因子的信息     1： 股票代码             2：因子名称
    PredictFactorLineData getPredictionFactor(1:string code,2:string factorName),

    //获得股票的周期预测    1:股票代码
    PeriodDataList getPeriodPredict(1:string code),

    // 获取关联股          1: 股票代码，只有股票才会有关联股，指数没有
    RelatedList getRelatedStocks(1:string code),

    //获得日本云数据       1:股票代码
    IchimokuDataList getIchimoku(1:string code),

    // 获取指数预测趋势    1：指数代码
    PredictionTrendData getExponentPredict(1:string code)


}

/**
* AI相关，策略类
**/
service AIService{
    // 获取所有智能机器人相关数据
    AIList getAllTheAI(),

    // 获取某一机器人基本资料      1：机器人id
    AIInfoData getAIInfo(1: string id),

    // 获取所有关注的机器人       1：用户id          return: 关注机器人id列表
    AIAttention getAttentionAIs(1:i32 userId),


    // 获取所有关注的机器人       1：用户id          return: 关注机器人alias列表
    AIAttention getAttentionAIAlias(1:i32 userId),

    // 获取ai机器人收益收据  1：机器人id            2：收益类型（近一周/近一月/总收益）
    AIIncome getAIIncome(1: string id, 2: AIIncomeType incomeType),

    // 获取历史盈亏   1  机器人id      2 持仓中/所有  3  top5 or top10      4  是否按照收益 升序  true 升序， false 降序
    AIOperationList getOperations(1:string id, 2:bool isAll, 3:i32 topN, 4:bool isAscend),

    // 获取历史盈亏分页   1  机器人id       2  待请求页      3  每页请求数
    AIOperationList getOperationsByPage(1:string id, 2:i32 pageNo, 3:i32 numPerPage),
	
	// 获取ai机器人调仓动态 1：机器人id   2  分页-待请求页      3  每页请求数
    DateTransferList getAITransfers(1:string id, 2:i32 pageNo, 3: i32 numPerPage)
}

/**
*
**/
service NotificationService{
    // 获取系统公告通知         1: 用户id     2：分页-待请求页      3：每页请求数
    AnnouncementData getAnnouncement(1: i32 userId,2:i32 pageNo, 3: i32 numPerPage),

    // 设为已读                 1: 用户id     2:新闻id
    IsSuccess setRead(1: i32 userId,2: string newsId),

    // 获取未读消息总数         1: 用户id
    Count getTotalUnread(1: i32 userId),

    // 获取AI周报
    AIWeeklyData getAIWeekly()

}

