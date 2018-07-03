package com.rjzd.aistock.ui.views.pull2refresh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import com.rjzd.aistock.R;
import com.rjzd.commonlib.utils.PixelUtil;

/**
 * 自定义刷新图标和文字显示
 *
 * Created by Hition on 2017/2/10.
 */

public class CustomRefreshView extends Drawable implements RefreshView {

    private static final String KPullRefresh = "下拉刷新";
    private static final String KReleaseRefresh = "松开刷新数据";
    private String KRefreshing = "正在为您刷新中..." ;
//    private static final String KRefreshing = "正在为您刷新中...";
//    private static final String KRefreshTime = "上次刷新时间: ";

    private static final float ARROW_SHRESHOLD = 0.9f;
    private static final int ANIMATION_ARROW_ROTATION = 180;
    private static final int ANIMATION_PROGRESS_RUN = 650;
    private static final Interpolator DECELERATE_INTERPOLATOR = new LinearInterpolator();

    private final PullToRefreshView mParent;
    private final Matrix mMatrix;
    private Context mContext;

    private float mScreenWidth;
    private float mScreenHeight;

    private float mTotalDragDistance;

    private float mArrowBottomOffset;
    private float mArrowLeftOffset;
    private float mArrowWidth;
    private float mArrowHeight;
    private float mProgressWidth;
    private float mProgressHeight;
    private float mRefreshTextSize;
    private float mRefreshTextLeftOffset;
//    private float mRefreshTimeOffsetOfRefreshText;

    private Bitmap mArrow;
    private Bitmap mProgress;
//    private String mLastRefreshTime;

    private Animation mArrowAnimation;
    private Animation mProgressAnimation;

    private float mPercent = 0.0f;
    private float mLastPercent = 0.0f;
    private float mProgressDegree = 0.0f;
    private float mArrowRotateDegree = 0.0f;
    private boolean mArrowAnimationCo = true;//是否正旋转

    private boolean isRefreshing = false;

    public CustomRefreshView(final PullToRefreshView layout) {
        mParent = layout;
        mMatrix = new Matrix();
        mContext = layout.getContext();
        setupAnimations();
        initiateDimens();

//        mLastRefreshTime = DateUtils.getDateToString(System.currentTimeMillis());
    }

    @Override
    public void setPercent(float percent, boolean invalidate) {
        mLastPercent = mPercent;
        mPercent = percent;
        if (invalidate) {
            setVariable(percent);
        }
    }

    public void setKRefreshing(String KRefreshing) {
        this.KRefreshing = KRefreshing;
    }

    private float setVariable(float value) {
        invalidateSelf();
        return value;
    }

    private void resetOriginals() {
        setPercent(0, true);
        mProgressDegree = setVariable(0);
        mArrowRotateDegree = setVariable(0);
    }

    @Override
    public void stop() {
        mParent.clearAnimation();
        isRefreshing = false;
        resetOriginals();
//        mLastRefreshTime = DateUtils.getDateToString(System.currentTimeMillis());
    }

    @Override
    public void start() {
        isRefreshing = true;
        final AnimationSet animatorSet = new AnimationSet(false);
        animatorSet.addAnimation(mProgressAnimation);
        mParent.startAnimation(mProgressAnimation);
    }

    @Override
    public void offsetTopAndBottom(int offset) {
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        if (mPercent <= 0) {
            return;
        }

        final int saveCount = canvas.save();

        canvas.translate(0, 0);
        canvas.clipRect(0, -mTotalDragDistance, mScreenWidth, mTotalDragDistance);

        if(!isRefreshing) {
            drawArrow(canvas);
        }
        drawRefreshingText(canvas);
//        drawRefreshTimeText(canvas);
        drawProgress(canvas);

        canvas.restoreToCount(saveCount);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    private void setupAnimations() {
        mProgressAnimation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                mProgressDegree = setVariable(interpolatedTime);
            }
        };
        mProgressAnimation.setInterpolator(DECELERATE_INTERPOLATOR);
        mProgressAnimation.setDuration(ANIMATION_PROGRESS_RUN);
        mProgressAnimation.setStartOffset(0);
        mProgressAnimation.setRepeatMode(Animation.RESTART);
        mProgressAnimation.setRepeatCount(Animation.INFINITE);

        mArrowAnimation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                mArrowRotateDegree = setVariable(interpolatedTime);
            }
        };
        mArrowAnimation.setInterpolator(DECELERATE_INTERPOLATOR);
        mArrowAnimation.setDuration(ANIMATION_ARROW_ROTATION);
        mArrowAnimation.setStartOffset(0);
        mArrowAnimation.setRepeatMode(Animation.ABSOLUTE);
        mArrowAnimation.setRepeatCount(0);
    }

    private void initiateDimens() {
        mTotalDragDistance = mParent.getTotalDragDistance();
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = mContext.getResources().getDisplayMetrics().heightPixels;

        createBitmaps();

        mArrowBottomOffset = PixelUtil.dp2px(40);
        mArrowLeftOffset = PixelUtil.dp2px(40);
        mRefreshTextLeftOffset = mArrowLeftOffset + mArrow.getWidth() + PixelUtil.dp2px(20);
        mRefreshTextSize = PixelUtil.sp2px(13);
//        mRefreshTimeOffsetOfRefreshText = PixelUtil.dp2px(0);
        mArrowWidth = mArrow.getWidth();
        mArrowHeight = mArrow.getHeight();
        mProgressWidth = mProgress.getWidth();
        mProgressHeight = mProgress.getHeight();
    }

    private void createBitmaps() {
        mArrow = CreateBitmapFactory.getBitmapFromImage(R.drawable.xlistview_arrow, mContext);
        mProgress = CreateBitmapFactory.getBitmapFromImage(R.drawable.default_ptr_rotate, mContext);
    }

    private void drawArrow(Canvas canvas) {
        Matrix matrix = mMatrix;
        matrix.reset();
        float dragPercent = Math.min(1f, Math.abs(mPercent));

        float offsetY;
        float offsetX = mArrowLeftOffset;
        offsetY = mTotalDragDistance * dragPercent - mArrowBottomOffset;

        checkArrowAnimation();
        if(mArrowAnimationCo && mArrowRotateDegree>0) {
            matrix.postRotate(mArrowRotateDegree*180, mArrowWidth/2, mArrowHeight/2);
        } else if(!mArrowAnimationCo && mArrowRotateDegree>0) {
            matrix.postRotate((1-mArrowRotateDegree)*180, mArrowWidth/2, mArrowHeight/2);
        }

        matrix.postTranslate(offsetX, offsetY);

        Paint paint = new Paint();
        canvas.drawBitmap(mArrow, matrix, paint);
    }

    private void drawRefreshingText(Canvas canvas) {
        Matrix matrix = mMatrix;
        matrix.reset();
        float dragPercent = Math.min(1f, Math.abs(mPercent));
        float offsetX = mRefreshTextLeftOffset;
        float offsetY = mTotalDragDistance * dragPercent - mArrowBottomOffset + mRefreshTextSize;
        matrix.postTranslate(offsetX, offsetY);

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#5a5a5a"));
        paint.setTextSize(mRefreshTextSize);
        paint.setAntiAlias(true);
        if(isRefreshing) {
            canvas.drawText(KRefreshing, offsetX, offsetY, paint);
        }else if(dragPercent <= ARROW_SHRESHOLD) {
            canvas.drawText(KPullRefresh, offsetX, offsetY, paint);
        } else {
            canvas.drawText(KReleaseRefresh, offsetX, offsetY, paint);
        }
    }

    /*private void drawRefreshTimeText(Canvas canvas) {
        Matrix matrix = mMatrix;
        matrix.reset();
        float dragPercent = Math.min(1f, Math.abs(mPercent));

        float offsetY;
        float offsetX = mRefreshTextLeftOffset;
        offsetY = mTotalDragDistance * dragPercent - mArrowBottomOffset
                + mRefreshTextSize + mRefreshTimeOffsetOfRefreshText;

        matrix.postTranslate(offsetX, offsetY);

        Paint paint = new Paint();
        paint.setTextSize(mRefreshTextSize);
        paint.setAntiAlias(true);
        canvas.drawText(KRefreshTime + mLastRefreshTime, offsetX, offsetY, paint);
    }*/

    private void drawProgress(Canvas canvas) {
        if(mProgressDegree > 0) {
            Matrix matrix = mMatrix;
            matrix.reset();
            float dragPercent = Math.min(1f, Math.abs(mPercent));
            float offsetY;
            float offsetX = mArrowLeftOffset;
            offsetY = mTotalDragDistance * dragPercent - mArrowBottomOffset;

            matrix.postRotate(mProgressDegree*360, mProgressWidth/2, mProgressHeight/2);
            matrix.postTranslate(offsetX, offsetY);

            Paint paint = new Paint();
            canvas.drawBitmap(mProgress, matrix, paint);
        }
    }

    private void checkArrowAnimation(){
        if(mPercent > ARROW_SHRESHOLD && mLastPercent <= ARROW_SHRESHOLD) {
            mParent.clearAnimation();
            mArrowRotateDegree = setVariable(0);
            mParent.startAnimation(mArrowAnimation);
            mArrowAnimationCo = true;
        } else if(mPercent < ARROW_SHRESHOLD && mLastPercent >= ARROW_SHRESHOLD) {
            mParent.clearAnimation();
            mArrowRotateDegree = setVariable(0);
            mParent.startAnimation(mArrowAnimation);
            mArrowAnimationCo = false;
        }
    }
}

