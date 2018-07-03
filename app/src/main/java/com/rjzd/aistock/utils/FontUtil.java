package com.rjzd.aistock.utils;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.rjzd.aistock.ZdStockApp;

/**
 * Created by Hition on 2016/12/29.
 */

public class FontUtil {

    public static Typeface getTypeface(String fontfile){
        //将字体文件保存在assets/fonts/目录下，创建Typeface对象
        Typeface typeFace = Typeface.createFromAsset(ZdStockApp.getAppContext().getAssets(),fontfile);
        return typeFace;
    }

    public static void setTextTypeface(TextView mView){
        Typeface mTypeface =  getTypeface("font/DIN_1451_Std.ttf");
        mView.setTypeface(mTypeface);
    }
}
