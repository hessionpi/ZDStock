package com.rjzd.aistock.ui.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/** 进制滑动viewpager
 * Created by Hition on 2017/3/24.
 */

public class ForbiddenScrollPager extends ViewPager{

    public ForbiddenScrollPager(Context context) {
        super(context);
    }

    public ForbiddenScrollPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        //表示切换的时候，不需要切换时间。
        super.setCurrentItem(item, false);
    }
}
