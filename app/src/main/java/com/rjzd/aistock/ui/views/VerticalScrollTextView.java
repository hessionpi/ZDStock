package com.rjzd.aistock.ui.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.rjzd.aistock.R;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by phs on 2017/04/18.
 *
 * 垂直滚动文字显示
 */

public class VerticalScrollTextView extends TextSwitcher implements ViewSwitcher.ViewFactory, View.OnClickListener {

    private final static int DEFAULT_DELAY = 1000;
    private final static int DEFAULT_PERIOD = 5000;

    /**
     * 点击事件监听
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private Context context;
    /**
     * 从下到上进入屏幕动画
     */
    private TranslateAnimation in;
    /**
     * 从下到上移出屏幕动画
     */
    private TranslateAnimation out;

    /**
     * 需要显示的数据列表
     */
    private List<String> list;

    /**
     * 滚动时间
     */
    private Timer timer;
    private TimerTask timerTask;

    private int currentPosition = 0;

    /**
     * 文字显示控件和属性
     */
    private TextView textView;
    ColorStateList textColor = null;
    int textSize = 0;

    private OnItemClickListener onItemClickListener;

    private TimeHandler timeHandler;

    private int period;

    private class TimeHandler extends Handler {
        private WeakReference<Context> contextWeakReference;

        TimeHandler(Context context) {
            contextWeakReference = new WeakReference<>(context);
        }

        @Override
        public void dispatchMessage(Message msg) {
            Context context = contextWeakReference.get();
            if (null != context) {
                next();
            }
            super.dispatchMessage(msg);
        }
    }

    public VerticalScrollTextView(Context context) {
        super(context, null);
        if (!isInEditMode()) {
            init(context);
        }
    }

    public VerticalScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.VerticalScrollTextView);
        textColor = array.getColorStateList(R.styleable.VerticalScrollTextView_verticalScrollTextColor);
        textSize = array.getDimensionPixelSize(R.styleable.VerticalScrollTextView_verticalScrollTextSize, 0);
        period = array.getInt(R.styleable.VerticalScrollTextView_verticalScrollPeriod, 0);
        array.recycle();
        if (!isInEditMode()) {
            init(context);
        }
    }

    private void init(Context context) {
        this.context = context;
        setFactory(this);
        in = createAnimation(1.0f, 0f);
        out = createAnimation(0, -1.0f);
        setInAnimation(in);
        setOutAnimation(out);
        timeHandler = new TimeHandler(context);
        if (0 == period) {
            period = DEFAULT_PERIOD;
        }
    }

    public void start() {
        stop();

        if (timer == null) {
            timer = new Timer();
        }
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    sendMessage();
                }
            };
        }

        if (timer != null) {
            timer.schedule(timerTask, DEFAULT_DELAY, period);
        }
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    private void next() {
        if (textView != null && list != null && list.size() > 0) {
            if (list.size() > 1) {
                if (getAnimation() != in) {
                    setInAnimation(in);
                }

                if (getAnimation() != out) {
                    setOutAnimation(out);
                }
            }

            if (currentPosition >= list.size()) {
                currentPosition = 0;
            }
            setText(list.get(currentPosition));
            currentPosition++;
        }
    }

    private void sendMessage() {
        if (timeHandler != null) {
            Message message = Message.obtain(timeHandler);
            timeHandler.sendMessage(message);
        }
    }

    private TranslateAnimation createAnimation(float fromY, float toY) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, fromY, Animation.RELATIVE_TO_SELF, toY);
        translateAnimation.setDuration(400);
        translateAnimation.setFillAfter(false);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        return translateAnimation;
    }

    @Override
    public void onClick(View v) {
        if (this.onItemClickListener != null) {
            onItemClickListener.onItemClick(currentPosition - 1);
        }
    }

    @Override
    public View makeView() {
        textView = new TextView(context);
        textView.setTextColor(textColor != null ? textColor : ColorStateList.valueOf(0xFF000000));
        if (0 != textSize) {
            float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            textView.setTextSize((int) (textSize / fontScale + 0.5f));
        }
        textView.setOnClickListener(this);

        return textView;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置等待时长
     *
     * @param period 等待时长，单位ms
     */
    public void setPeriod(int period) {
        this.period = period;
    }
}