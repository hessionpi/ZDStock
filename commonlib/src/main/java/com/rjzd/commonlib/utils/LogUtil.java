package com.rjzd.commonlib.utils;

import android.util.Log;

/**
 * Created by Hition on 2016/12/8.
 *
 * 当项目上线时，改为 isDebug = false 关闭所有打印信息
 *
 */

public class LogUtil {

    private LogUtil(){
        throw new UnsupportedOperationException("cannot be instantiated ...");
    }

    private static final String TAG = "RJZD";
    private static boolean isDebug = true;

    public static void v(String tag, String msg) {
        if (isDebug){
            Log.v(tag, msg);
        }
    }

    public static void v(String msg) {
        if (isDebug){
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (isDebug){
            Log.d(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug){
            Log.d(tag, msg);
        }
    }

    public static void i(String msg) {
        if (isDebug){
            Log.i(TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug){
            Log.i(tag, msg);
        }
    }

    public static void w(String msg) {
        if (isDebug){
            Log.w(TAG, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug){
            Log.w(tag, msg);
        }
    }

    public static void e(String msg) {
        if (isDebug){
            Log.e(TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug){
            Log.e(tag, msg);
        }
    }

}
