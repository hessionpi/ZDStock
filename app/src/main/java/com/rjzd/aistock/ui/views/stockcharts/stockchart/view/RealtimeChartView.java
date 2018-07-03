package com.rjzd.aistock.ui.views.stockcharts.stockchart.view;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.rjzd.aistock.ui.views.stockcharts.stockchart.bean.CMinute;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.bean.FenshiDataResponse;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.utils.ColorUtil;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.utils.DrawUtils;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.utils.GridUtils;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.utils.LineUtil;
import com.rjzd.commonlib.utils.LogUtil;
import com.rjzd.commonlib.utils.PixelUtil;

import java.util.ArrayList;

/**
 * 分时图封装
 * <p>
 * Created by Administrator on 2017/3/7.
 */

public class RealtimeChartView extends ChartView {
    //分时数据
    private FenshiDataResponse data;
    //补全后的所有点
    private ArrayList<CMinute> minutes;
    //所有价格
    private float[] price;
    //所有均线数据
    private float[] average;
    //分时线昨收
    private double yd;

    //是否白盘夜盘一起显示
    private boolean hasNight;

    private double yMax;
    private double yMin;
    private float xUnit;
    /**
     * the phase that is animated and influences the drawn values on the x-axis
     */
    protected float mPhaseX = 1f;

    public RealtimeChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean onViewScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector != null)
            gestureDetector.onTouchEvent(event);
        // return true;
        return false;
    }


    @Override
    protected void init() {
        if (data == null) return;
        yd = data.getParam().getLast();
        hasNight = LineUtil.hasNight(data.getParam().getDuration());
        // xUnit = mWidth / LineUtil.getShowCount(data.getParam().getDuration());
        //if (data.getParam().getLength()>240){
        xUnit = mWidth / data.getParam().getLength();
      /*  }else{
            xUnit = mWidth/240;
        }*/
        //xUnit = mWidth / data.getParam().getLength();
        // xUnit = mWidth / minutes.size();
        //计算最大最小值
        boolean first = true;
        for (CMinute c : data.getData()) {
            if (first) {
                first = false;
                yMax = c.getPrice();
                yMin = c.getPrice();
            }
            yMax = c.getPrice() > yMax ? c.getPrice() : yMax;
            yMax = c.getAverage() > yMax ? c.getAverage() : yMax;
            if (c.getPrice() != 0)
                yMin = c.getPrice() < yMin ? c.getPrice() : yMin;
            if (c.getAverage() != 0 && c.getAverage() != 0.01)
                yMin = c.getAverage() < yMin ? c.getAverage() : yMin;
        }
    }

    @Override
    protected void drawGrid(Canvas canvas) {
        //1,画网格
        if (data != null && data.getParam() != null && LineUtil.isIrregular(data.getParam().getDuration())) {
            //如果是不规则网格画不规则网格
            GridUtils.drawIrregularGrid(canvas, mWidth, mHeight, data.getParam().getDuration());
            // GridUtils.drawIrregularIndexGrid(canvas, indexStartY, mWidth, indexH, data.getParam().getDuration());
        } else {
            if (hasNight) {
                GridUtils.drawNightGrid(canvas, mWidth, mHeight);
                //  GridUtils.drawNightIndexGrid(canvas, indexStartY, mWidth, indexH);
            } else {
                GridUtils.drawGrid(canvas, mWidth, mHeight);
                //  GridUtils.drawIndexGrid(canvas, indexStartY, mWidth, indexH);
            }
        }

    }

    ObjectAnimator animatorX;

    public void animateX(ValueAnimator.AnimatorUpdateListener mListener) {

        if (android.os.Build.VERSION.SDK_INT < 11)
            return;
        animatorX = ObjectAnimator.ofFloat(this, "phaseX", 0f, 1f);
        animatorX.setDuration(500);
        animatorX.addUpdateListener(mListener);
        animatorX.start();
    }

    /**
     * 重新画分时图
     *
     * @param data
     */
    public void setDataAndInvalidate(FenshiDataResponse data) {

        // minutes = LineUtil.getAllFenshiData(data);
        if(null == data){
            minutes = new ArrayList<>();
        }else {
            this.data = data;
            minutes = (ArrayList<CMinute>) data.getData();
        }
        MyAnimatorUpdateListener listener = new MyAnimatorUpdateListener();
        animateX(listener);
    }

    class MyAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            postInvalidate();
        }
    }

    @Override
    protected void drawText(Canvas canvas) {
        if (data == null) return;
        DrawUtils.drawYPercentAndPrice(canvas, yMax, yMin, yd, mWidth, mHeight);
    }

    @Override
    protected void drawLines(final Canvas canvas) {
        drawPrevClose(canvas);
        if (data == null) {

            return;

        } else {
            setPoint();
            drawAverageLine(canvas);
            drawPriceLine(canvas);
        }

    }

    void drawPrevClose(Canvas canvas) {
        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.GRAY);
        p.setStrokeWidth(PixelUtil.dp2px(1));
        p.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));
        p.setAntiAlias(true);
        //中间实线(横)
        canvas.drawLine(0, mHeight * 2 / 4, mWidth, mHeight * 2 / 4, p);
    }

    public void drawLines(Canvas canvas, float[] prices, float xUnit, float height, int color, float max, float min, boolean fromZero) {
        drawLine(canvas, prices, xUnit, height, color, max, min, fromZero, 0, 0);
    }

    public void drawLine(Canvas canvas, float[] prices, float xUnit, float height, int color, float max, float min, boolean fromZero, float y, float xOffset) {
        if (canvas == null) {
            LogUtil.w("DrawUtils", "canvas为空");
            return;
        }
        float tmax = 0f;
        float tmin = 0f;
        for (float f : prices) {
            tmax = tmax > f ? tmax : f;
            tmin = tmin < f ? tmin : f;

        }
        //如果数组中值全为0，则不画该线
        if (tmax == 0 && tmin == 0)
            return;
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(color);
        p.setStrokeWidth(PixelUtil.dp2px(1.2f));
        canvas.drawLines(getLines(prices, xUnit, height, max, min, fromZero, y, xOffset), p);
        p.reset();
    }

    public float[] getLines(float[] prices, float xUnit, float height, float max, float min, boolean fromZero, float y, float xOffset) {
        float[] result = new float[prices.length * 4];
        float yUnit = (max - min) / height;
        int length = (int) ((float) prices.length * (float) animatorX.getAnimatedValue());
        for (int i = 0; i < length - 1; i++) {
            //排除起点为0的点
            if (!fromZero && prices[i] == 0) continue;
            result[i * 4 + 0] = (xOffset + i * xUnit);
            result[i * 4 + 1] = y + height - (prices[i] - min) / yUnit;
            result[i * 4 + 2] = xOffset + (i + 1) * xUnit;
            result[i * 4 + 3] = y + height - (prices[i + 1] - min) / yUnit;
        }

        return result;
    }

    @Override
    protected void drawVOL(Canvas canvas) {
    }

    /**
     * 价格线
     *
     * @param canvas
     */
    private void drawPriceLine(Canvas canvas) {

        //乘以1.001是为了让上下分别空一点出来
        double[] maxAndMin = LineUtil.getMaxAndMinByYd(yMax, yMin, yd);
        drawLines(canvas, price, xUnit, mHeight, ColorUtil.COLOR_PRICE_LINE, (float) maxAndMin[0], (float) maxAndMin[1], false);
        // DrawUtils.drawLines(canvas, price,xUnit , mHeight, ColorUtil.COLOR_PRICE_LINE, (float) maxAndMin[0], (float) maxAndMin[1], false);
    }

    void setPoint() {
        price = new float[minutes.size()];
        average = new float[minutes.size()];
        for (int i = 0; i < minutes.size(); i++) {
            // setPhaseX((float) minutes.get(i).getPrice());
            price[i] = (float) minutes.get(i).getPrice();
            average[i] = (float) minutes.get(i).getAverage();

        }
    }

    private void drawAverageLine(Canvas canvas) {

        float[] maxAndMin1 = LineUtil.getMaxAndMin(average);
        //如果均线值全为0.01则不画改线，否则会影响价格线展示
        if (maxAndMin1[0] == 0.01 && maxAndMin1[1] == 0.01)
            return;
        //乘以1.001是为了让上下分别空一点出来
        double[] maxAndMin = LineUtil.getMaxAndMinByYd(yMax, yMin, yd);
        //  DrawUtils.drawPriceShader(canvas, price, xUnit, mHeight, (float) maxAndMin[0], (float) maxAndMin[1]);
        drawLines(canvas, average, xUnit, mHeight, ColorUtil.COLOR_SMA_LINE, (float) maxAndMin[0], (float) maxAndMin[1], false);
    }

    /**
     * 当十字线移动到某点时，回调到此处，用此处的数据判断是否显示十字线
     *
     * @param x x轴坐标
     * @param y y轴坐标
     */
    @Override
    public void onCrossMove(float x, float y) {
        super.onCrossMove(x, y);
    }

    @Override
    public void onDismiss() {

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    /**
     * 价格信息
     *
     * @param entity
     * @return
     */
    public String getCurPriceInfo(CMinute entity) {
        return ColorUtil.getCurPriceInfo(entity, yd);
    }

    /**
     * This gets the x-phase that is used to animate the values.
     *
     * @return
     */
    public float getPhaseX() {
        return mPhaseX;
    }

    /**
     * This modifys the x-phase that is used to animate the values.
     *
     * @param phase
     */
    public void setPhaseX(float phase) {
        mPhaseX = phase;
    }
}


