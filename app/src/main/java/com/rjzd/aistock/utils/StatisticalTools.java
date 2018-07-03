package com.rjzd.aistock.utils;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * 壹眼富 统计工具
 */
public final class StatisticalTools {

    private StatisticalTools() {
    }

    public static void eventCount(Context context, String event_id) {
        MobclickAgent.onEvent(context, event_id);
    }

    /**
     * 友盟统计 统计activity
     */
    public static void onResume(Context context) {
        MobclickAgent.onResume(context);
    }

    public static void onPause(Context context) {
        MobclickAgent.onPause(context);
    }

    /**
     * 统计 Fragment 页面：
     */

    public static void fragmentOnResume(String name) {
        MobclickAgent.onPageStart(name);
    }

    public static void fragmentOnPause(String name) {
        MobclickAgent.onPageEnd(name);
    }

    /**
     * 统计 用户
     * @param provider     当使用公司自有账号登录时""，三方登录时来源  WB  WX QQ
     * @param userId
     */
    public static void userInfo(String provider, String userId) {
        if(TextUtils.isEmpty(provider)){
            MobclickAgent.onProfileSignIn(userId);
        }else{
            MobclickAgent.onProfileSignIn(provider,userId);
        }
    }

    /**
     * 统计 用户clear
     */
    public static void clearUserInfo() {
        MobclickAgent.onProfileSignOff();
    }
}
