package com.rjzd.aistock;


/**
 * Created by Hition on 2016/12/21.
 *
 * @author Hition
 */

public class Constants {

    // 股票数据存本地json文件名
    public static final String STOCK_LOCAL_FILE = "stocks.json";

  //    public static final String SERVER_IP = "172.16.0.156";
    public static final String SERVER_IP = "111.202.205.172";
    public static final int SERVER_PORT = 18090;
    public static final int TIMEOUT = 5 * 1000;


    /**
     * 大盘指数数据
     */
    public static final String EXPONENT_SH = "sh";  // 上证指数
    public static final String EXPONENT_SZ = "sz";  // 深证指数
    public static final String EXPONENT_CYB = "cyb"; // 创业版
    public static final String EXPONENT_ZXB = "zxb";  // 中小板
    public static final String EXPONENT_HS300 = "hs300"; //  沪深300
    public static final String EXPONENT_SZ50 = "sz50"; // 上证50

    /*public static final String EXPONENT_SH_CODE = "000001";  // 上证指数
    public static final String EXPONENT_SZ_CODE = "399001";  // 深证成指
    public static final String EXPONENT_CYB_CODE = "399006"; // 创业版指
    public static final String EXPONENT_ZXB_CODE = "399005";  // 中小板指
    public static final String EXPONENT_HS300_CODE = "000300"; //  沪深300
    public static final String EXPONENT_SZ50_CODE = "000016"; // 上证50*/


    /**
     * 刷新时间间隔 5s
     *
     */
    public static final long PERIOD_REFRESH = 5*1000;

    /**
     * 刷新时间间隔 1min
     *
     */
    public static final long REALTIME_PERIOD_REFRESH = 60*1000;

    /**
     * 发送短信验证码时长
     */
    public static int MAX_TIME_LENGTH = 60*1000;
    public static int TICK_TIME_LENGTH = 1000;


    public static double last;


    // 待删除股票代码和位置
    public static String delCode ;
    public static int delPosition = -1;

    public static boolean isNeedNotify = false;

    /**
     * 用于存放用户登录后用户信息
     */
    public static final String LoginModelFileName = "userinfo";

    /**
     * 默认向服务器每页请求个数
     */
    public static final int DEFAULT_PER_PAGE = 10;

    public static final int MARKET_PER_PAGE = 15;

    /**
     * 数据源标识
     */
    public static final int DS_TAG_DEFAULT = 0;                          // 默认
    public static final int DS_TAG_CAPITAL_FLOWS = 1;                    // 资金流向
    public static final int DS_TAG_MARGINTRADING = 2;                    // 融资融券余额差额
    public static final int DS_TAG_FINANCE_PERFORMANCE = 3;              // 财务业绩
    public static final int DS_TAG_FINANCE_ANALYSIS = 4;                 // 财务分析
    public static final int DS_TAG_COMPANY_PROFILE = 5;                  // 公司概况
    public static final int DS_TAG_SHARE_HOLDER = 6;                     // 股东信息

    public static final int DS_TAG_TICK_REFRESH = 10;                     //  刷新自选股
    public static final int DS_TAG_REFRESH_HANDICAP  = 11;               //  刷新盘口数据
    public static final int DS_TAG_SEARCH_STOCK_HISTORY =12;             // 获取搜索历史
    public static final int DS_TAG_SEARCH_STOCKS =13;                    // 获取满足条件搜索结果

    public static final int DS_TAG_MARKET = 20;                         // 所有大盘股票
    public static final int DS_TAG_LEADERSTOCK = 21;                    // 大盘领涨或领跌股

    public static final int DS_TAG_PREDCTION_COMPREHENSIVE = 30;         // 综合预测
    public static final int DS_TAG_PREDCTION_KPS = 31;// 综合评分
    public static final int DS_TAG_PREDCTION_PERIODICITY = 32;           // 周期性预测
    public static final int DS_TAG_PREDCTION_ICHIMOKU = 33;             // 云图预测
    public static final int DS_TAG_PREDCTION_RELATED_STOCKS = 34;        // 关联矩阵预测
    public static final int DS_TAG_PREDCTION_COMPREHENSIVE_FACTOR = 35;     // 技术、趋势因子
    public static final int DS_TAG_PREDCTION_RADAR = 36;                   //获取雷达所有因子
    public static final int DS_TAG_PREDCTION_FUNDAMENTALS = 37;            // 基本面因子
    public static final int DS_TAG_PREDCTION_MACRO = 38;                  // 宏观因子
    public static final int DS_TAG_PREDCTION_FUNDHOLDING = 39;            // 机构持仓
    public static final int DS_TAG_PREDCTION_STOCK_RESULT = 40;           // 获取利用某一因子预测结果
    public static final int DS_TAG_IS_TRADE_DATE = 41;                    // 是否是交易日

    public static final int DS_TAG_NOTIFICATION_UNREAD = 42;              // 获取未读消息总条数
    public static final int DS_TAG_NOTIFICATION_ANNOUNCEMENT = 43;        // 获取所有系统公告通知
    public static final int DS_TAG_MESSAGE_SET_READ= 44;                  // 设置消息为已读
    public static final int DS_TAG_PUSH_STATUS_GET= 45;                   // 获取消息推送状态
    public static final int DS_TAG_PUSH_SETTINGS= 46;                     // 设置推送

    public static final int DS_TAG_AI_INFO = 50;                         // AI机器人基本信息
    public static final int DS_TAG_AI_INCOME = 51;                       // 机器人收益状况
    public static final int DS_TAG_AI_ACTION = 52;                       // 机器人调仓动态
    public static final int DS_TAG_AI_HOLD_STOCK = 53;                   // 机器人持仓股票
    public static final int DS_TAG_AI_HISTORY_PAL = 54;                   // 机器人历史盈亏
    public static final int DS_TAG_AI_ATTENTION_ADD_OR_CANCEL = 55;       // 添加或取消关注机器人
    public static final int DS_TAG_AI_HISTORY_PAGING = 56;                // 机器人历史盈亏分页
    public static final int DS_TAG_AI_ATTENTION_GET = 57;                // 获取关注的机器人

    public static final int DS_TAG_LOGIN = 60;                            // 用户登录
    public static final int DS_TAG_BINDING_ACCOUNT = 61;                  // 绑定账号

    public static final int DS_TAG_GET_PORTFOLIOS_SERVER = 62;            // 获取服务器自选股
    public static final int DS_TAG_DELETE_PORTFOLIO_SERVER = 63;          // 删除服务器自选股
    public static final int DS_TAG_ADD_PORTFOLIO_SERVER = 63;             // 向服务器添加自选股
    public static final int DS_TAG_SYNCHRONIZE_PORTFOLIO = 64;             // 向服务器同步本地自选股
    public static final int DS_TAG_CHECK_VERSION = 65;

    public static final int DS_TAG_MY_POINTS = 66;                         // 我的积分
    public static final int DS_TAG_RECENTGAINPOINTS = 67;                  // 最近新增积分
    public static final int DS_TAG_PRIVILEGESTATUS = 68;                   // 积分特权状态
    public static final int DS_TAG_MYTASKSTATUS = 69;                      // 我的任务状态
    public static final int DS_TAG_UNLOCKPRIVILEGE = 70;                   // 解锁特权
    public static final int DS_TAG_POINTS_RECORD = 71;                     // 积分记录
    public static final int DS_TAG_EARNPOINTS_BY_DAILYACTIVE = 72;         // 每日活跃赚积分
    public static final int DS_TAG_EARNPOINTS_BY_SHARE = 73;               // 分享APP赚积分

    public static final int DS_TAG_RECOMMEND_PLATE = 74;                    // 每日牛版推荐
    public static final int DS_TAG_RECOMMEND_STOCK = 75;                    // 每日金股推荐
    public static final int DS_TAG_AI_WEEKLY = 76;                          // 每日金股推荐






    // 版本检查与更新
    public static final int ICHIMOKU_EXPLAIN_GOOD = 101;                     // 云图利好
    public static final int ICHIMOKU_EXPLAIN_BAD = 102;                     // 云图利空
    public static final int ICHIMOKU_EXPLAIN_NEUTRAL = 103;                     // 云图中性

    /**
     * 默认分享页面
     */
    public static final String BASE_SHARE_ADDRESS = "http://www.yiyanfu.com/stock/getStock.htmls?code=";
    public static final String APP_SHARE_ADDRESS = "http://www.yiyanfu.com/m/share.jsp";
    public static final String AI_SHARE_ADDRESS = "http://www.yiyanfu.com/ai/detail.htmls?id=";
    public static final String SHARE_CONTENT="壹眼富是股民的免费AI股票助手，每日AI预测个股涨跌幅，多个AI炒股机器人免费关注领养，拥有AI，人人是股神！";
    
    // 基本面因子
    public static final int FUNDAMENTALS_AVG = 0;                           // 行业平均
    public static final int FUNDAMENTALS_SELF = 1;                          // 自身
    public static final int FUNDAMENTALS_FIRST = 2;                         // 行业第一名
    public static final int FUNDAMENTALS_LAST = 3;                          // 行业最后一名

    // 关于我们链接
    public static final String URL_ABOUT_US = "http://www.yiyanfu.com/m/about_us.jsp";

    // 用户协议
    public static final String URL_USER_AGREEMENT = "http://www.yiyanfu.com/m/protocol.jsp";
  //邀请朋友
    public static final String URL_INVITE_FRIEND = "http://www.yiyanfu.com/m/pages/my/go_request.jsp?inviteCode=";

    // 特权id
    public static final String PRIVILEGE_NIUBAN = "niuban";//牛版
    public static final String PRIVILEGE_NIUGU = "niugu";//牛股
    public static final String PRIVILEGE_ZHOUBAO = "zhoubao";//周报

    // 任务id
    public static final String TASK_LOGIN = "login";//每日登陆
    public static final String TASK_INVITE = "invite";//邀请好友
    public static final String TASK_SHARE = "share";//分享页面
    public static final String TASK_ATTENTIONSTOCK = "attentionStock";//添加自选股
    public static final String TASK_ATTENTIONAI = "attentionAI";//关注机器人
    public static final String TASK_BIND = "bind";//绑定手机号

    public static final String ACTION_ANNOUNCEMENT = "PushNotification"; // 消息通知
    public static final String ACTION_AITRANSFER = "PushAITransfer"; // AI调仓
    public static final String ACTION_PREDICT = "PushPredict"; // AI预测
    public static final String ACTION_INVITE = "PushInvite"; // 邀请好友注册成功了
    public static final String ACTION_DAILYBESTPLATE = "PushDailyBestPlate"; // 每日金版
    public static final String ACTION_DAILYBESTSTOCK = "PushDailyBestStock"; // 每日牛股
    public static final String ACTION_AI_WEEKLY = "PushAIWeekly";		      // AI周报

    public static boolean recommendPlateNew = false;
    public static boolean recommendStockNew = false;
    public static boolean recommendWeeklyNew = false;
}
