package com.rjzd.aistock;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import com.rjzd.aistock.db.DBHelper;
import com.rjzd.aistock.model.imp.StockModel;
import com.rjzd.aistock.presenter.listener.BaseListener;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.utils.DateUtil;
import com.rjzd.aistock.utils.FileUtil;
import com.rjzd.aistock.utils.SPUtils;
import com.rjzd.commonlib.utils.LogUtil;
import com.rjzd.commonlib.utils.NetWorkUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import cn.jpush.android.api.JPushInterface;
import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by Hition on 2016/12/20.
 */

public class ZdStockApp extends Application implements BaseListener {

    private static Context context;

    /**
     * 两次按下返回键退出的间隔
     */
    public static final Integer QUIT_INTERVAL = 2 * 1000;

    public static Context getAppContext() {
        return ZdStockApp.context;
    }

    {
        Config.DEBUG = false;
        PlatformConfig.setWeixin("wx460db727a0170c06", "3d87354394e1ed33a80cc453ed2de018");
        //http://sns.whalecloud.com  https://api.weibo.com/oauth2/default.html
        PlatformConfig.setSinaWeibo("1495166945", "924cd03cf2b8539c5b7c14a5cb846413","https://api.weibo.com/oauth2/default.html");
        PlatformConfig.setQQZone("1106011536", "7SJqaufX8QPcHZNu");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();

        //UMShareAPI.get(this);
        UMShareConfig config = new UMShareConfig();
        config.setSinaAuthType(UMShareConfig.AUTH_TYPE_WEBVIEW);
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(context).setShareConfig(config);
        initRealm();
        initJPush();

        //包含fragment的程序中 禁止默认的页面统计方式，这样将不会再自动统计Activity
        MobclickAgent.openActivityDurationTrack(false);

        // 每天只同步一次全部股票
        if((boolean)SPUtils.get("hasGetAll",false) ){
            if(DateUtil.getDay() <= (int)SPUtils.get("getTime",0)){
                return ;
            }
        }
        getAllStock();
    }

    /**
     * 主要解决dex突破65535时候，项目在android5.0 以下报错Java.lang.NoClassDefFoundError
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化极光推送
     */
    private void initJPush() {
        JPushInterface.setDebugMode(false); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    /**
     * 初始化Realm 数据库操作
     */
    private void initRealm(){
        Realm.init(this);
        RealmConfiguration configuration=new RealmConfiguration.Builder()
                .name(DBHelper.DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    /**
     * 获取所有3000多支股票到本地
     */
    private void getAllStock() {
        if(NetWorkUtil.isNetworkConnected(this)){
            StockModel model = new StockModel(this);
            model.getAllStocks2Local();
        }
    }

    @Override
    public void onSuccess(Object data) {
        SPUtils.put("hasGetAll",true);
        SPUtils.put("getTime", DateUtil.getDay());
        String jsonData = (String) data;
        if(!TextUtils.isEmpty(jsonData)){
            FileUtil.saveFile(Constants.STOCK_LOCAL_FILE,jsonData);
        }
    }

    @Override
    public void onFailed(Throwable e) {
        LogUtil.e("获取全部股票数据失败了……"+e.getMessage());
    }

}
