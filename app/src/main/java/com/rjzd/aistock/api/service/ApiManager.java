/*
package com.rjzd.aistock.api.service;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.ZdStockApp;
import com.rjzd.commonlib.utils.NetWorkUtil;
import java.io.File;
import java.io.IOException;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

*/
/**
 * Created by Hition on 2016/12/21.
 *//*


public class ApiManager {

    private static ApiManager instance ;
    private Retrofit retrofit;

    private ApiManager(){
        File cashFile = ZdStockApp.getAppContext().getExternalCacheDir();
        // 设置OkHttp请求数据时缓存
        File cacheFile = new File(cashFile,Constants.CASH_PATH);
        Cache cache = new Cache(cacheFile,1024*1024*50);
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                boolean isConnect = NetWorkUtil.isNetworkConnected(ZdStockApp.getAppContext());

                if (!isConnect) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                Response response = chain.proceed(request);
                if (isConnect) {
                    int maxAge = 0 ;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };

        OkHttpClient client = new OkHttpClient.Builder().cache(cache)
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("")
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

    }

    public static ApiManager getInstance(){
        if (instance==null){
            synchronized (ApiManager.class){
                if(instance==null){
                    instance = new ApiManager();
                }
            }
        }
        return instance;
    }


    */
/**
     * 获取主页数据
     *
     * @return
     *//*
*/
/*
    public Observable<BaseData> getHomePageData(){
        HomePageService home = retrofit.create(HomePageService.class);
        return home.getPageData();
    }*//*




}
*/
