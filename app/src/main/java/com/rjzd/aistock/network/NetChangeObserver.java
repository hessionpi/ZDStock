package com.rjzd.aistock.network;

/**
 * 监听网络状态改变的观察者
 *
 * Created by Hition on 2017/1/18.
 */

public interface NetChangeObserver {
    /**
     * 网络状态连接时调用
     */
    void onReconnect();

    /**
     * 网络状态断开时调用
     */
    void onDisconnect();


}
