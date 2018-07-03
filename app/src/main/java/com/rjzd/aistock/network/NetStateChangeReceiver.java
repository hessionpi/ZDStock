package com.rjzd.aistock.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import com.rjzd.commonlib.utils.NetWorkUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * 监听网络状态变化的BroadcastReceiver
 *
 * @author Tianma at 2016/12/28
 */
public class NetStateChangeReceiver extends BroadcastReceiver {

    private static boolean networkAvailable = true;
    private static List<NetChangeObserver> mObservers = new ArrayList<>();
    private static NetStateChangeReceiver instance;

    private NetStateChangeReceiver() {

    }

    public static NetStateChangeReceiver getInstance(){
        if(null == instance){
            synchronized (NetStateChangeReceiver.class){
                if (null == instance){
                    instance = new NetStateChangeReceiver();
                }
            }
        }
        return instance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            networkAvailable = NetWorkUtil.isNetworkConnected(context);
            // 通知所有注册了的网络状态观察者
            notifyObservers();
        }
    }

    /**
     * 注册网络监听
     */
    public void registerReceiver(@NonNull Context context) {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(instance, intentFilter);
    }

    /**
     * 取消网络监听
     */
    public void unregisterReceiver(@NonNull Context context) {
        context.unregisterReceiver(instance);
    }

    /**
     * 注册网络变化Observer
     */
    public void registerObserver(NetChangeObserver observer) {
        if (observer == null){
            return;
        }

        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    /**
     * 取消网络变化Observer的注册
     */
    public void unregisterObserver(NetChangeObserver observer) {
        if(null!=observer && mObservers.isEmpty()){
            mObservers.remove(observer);
        }
    }

    /**
     * 通知所有的Observer网络状态变化
     */
    private void notifyObservers() {
        if(null != mObservers  && mObservers.size() >0){
            for(NetChangeObserver observer : mObservers){
                if(observer != null){
                    if(networkAvailable){
                        observer.onReconnect();
                    }else{
                        observer.onDisconnect();
                    }
                }
            }
        }
    }

}
