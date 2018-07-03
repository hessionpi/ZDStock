package com.rjzd.commonlib.imageloader;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

/**
 * Created by Hition on 2016/12/21.
 */

public class ZDImgloader {

    /**
     * 加载原图
     * @param context
     * @param url   图片地址
     * @param emptyResId  未加载时图片资源id
     * @param errorResId  加载失败图片资源id
     * @param view    imageView
     */
    public static void load(Context context, String url, int emptyResId, int errorResId, ImageView view){
        Glide.with(context).load(url).placeholder(emptyResId).error(errorResId).into(view);
    }

    /**
     * 加载圆形图像或者圆角图像
     * @param context
     * @param url   图片地址
     * @param emptyResId  未加载时图片资源id
     * @param errorResId  加载失败图片资源id
     * @param view     imageView
     * @param radius   圆角度数   0 圆形   非0代表磨圆弧角度数
     */
    public static void loadTransformImage(Context context,String url,int emptyResId,int errorResId,ImageView view,int radius){
        if(0 == radius){
            Glide.with(context).load(url).transform(new CircleTransform(context)).placeholder(emptyResId).error(errorResId).into(view);
        }else{
            Glide.with(context).load(url).transform(new RoundTransform(context)).placeholder(emptyResId).error(errorResId).into(view);
        }
    }




}
