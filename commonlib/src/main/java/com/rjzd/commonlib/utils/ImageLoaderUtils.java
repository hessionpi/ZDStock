package com.rjzd.commonlib.utils;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.rjzd.commonlib.imageloader.CircleTransform;
import com.rjzd.commonlib.imageloader.RoundTransform;


/**
 * Created by Hition on 2016/12/6.
 */
public class ImageLoaderUtils {

    /**
     * 加载原图
     * @param context
     * @param url
     * @param emptyResId
     * @param errorResId
     * @param view
     */
    public static void loadImage(Context context,String url,int emptyResId,int errorResId,ImageView view){
        Glide.with(context).load(url).placeholder(emptyResId).error(errorResId).into(view);
    }

    /**
     * 加载圆形图像或者圆角图像
     * @param context
     * @param url
     * @param emptyResId
     * @param errorResId
     * @param view
     * @param radius
     */
    public static void loadTransformImage(Context context,String url,int emptyResId,int errorResId,ImageView view,int radius){
        if(0 == radius){
            Glide.with(context).load(url).transform(new CircleTransform(context)).placeholder(emptyResId).error(errorResId).into(view);
        }else{
            Glide.with(context).load(url).transform(new RoundTransform(context)).placeholder(emptyResId).error(errorResId).into(view);
        }
    }

}
