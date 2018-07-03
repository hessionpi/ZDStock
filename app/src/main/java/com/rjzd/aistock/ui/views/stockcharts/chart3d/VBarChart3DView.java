
package com.rjzd.aistock.ui.views.stockcharts.chart3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.rjzd.aistock.ui.views.stockcharts.DemoView;
import com.rjzd.commonlib.utils.LogUtil;
import com.rjzd.commonlib.utils.PixelUtil;
import org.xclcharts.chart.BarChart3D;
import org.xclcharts.chart.BarData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.event.click.BarPosition;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Bar3DChart01View
 * @Description 3D柱形图例子
 */
public class VBarChart3DView extends DemoView {

    private String TAG = "VBarChart3DView";
    private BarChart3D chart = new BarChart3D();

    //标签轴
//    private List<String> chartLabels = new LinkedList<>();
    //数据轴
//    private List<BarData> BarDataset = new LinkedList<>();
//    Paint mPaintToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);

    private OnBarItemClickListener itemClickListener;

    public VBarChart3DView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    public VBarChart3DView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VBarChart3DView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        chartRender();

        //綁定手势滑动事件
//        this.bindTouch(this, chart);
    }

    /**
     * 设置数据源
     */
    public void setDataSource(List<BarData> barDataset,List<String> chartLabels){
        chart.setDataSource(barDataset);
        chart.setCategories(chartLabels);
    }

    public void setDataSource(BarData bData,List<String> chartLabels){
        // 设置Y坐标系，相对最大值、最小值、刻度
        List<BarData> barDataList = new ArrayList<>();
        barDataList.add(bData);
        setDataSource(barDataList,chartLabels);
    }

    public void setOnItemClickListener(OnBarItemClickListener listener){
        this.itemClickListener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        chart.setChartRange(w, h);
    }

    private void chartRender() {
        try {
            chart.setPadding(DensityUtil.dip2px(5), 0,DensityUtil.dip2px(5), DensityUtil.dip2px(30));
            //隐藏边框
            chart.hideBorder();

            //数据源
            /*chart.setDataSource(BarDataset);
            chart.setCategories(chartLabels);*/

            //坐标系最大值、最小值、刻度绝对值
            chart.getDataAxis().setAxisMax(2);
            chart.getDataAxis().setAxisMin(0.5);
            chart.getDataAxis().setAxisSteps(0.01);

            //隐藏轴线和tick
            chart.getDataAxis().hideAxisLine();
            //chart.getDataAxis().setTickMarksVisible(false);

            //标题
            /*chart.setTitle("本周水果销售情况");
            chart.addSubtitle("(XCL-Charts Demo)");
            chart.setTitleAlign(XEnum.HorizontalAlign.RIGHT);*/

            //背景网格
            /*chart.getPlotGrid().showHorizontalLines();
            chart.getPlotGrid().showVerticalLines();
            chart.getPlotGrid().showEvenRowBgColor();
            chart.getPlotGrid().showOddRowBgColor();*/

            //定义数据轴标签显示格式
            /*chart.getDataAxis().setTickLabelRotateAngle(-45);
            chart.getDataAxis().getTickMarksPaint().
                    setColor(Color.rgb(186, 20, 26));
            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack() {

                @Override
                public String textFormatter(String value) {
                    // TODO Auto-generated method stub
                    Double tmp = Double.parseDouble(value);
                    DecimalFormat df = new DecimalFormat("#0.00");
                    String label = df.format(tmp).toString();
                    return (label + "%");
                }
            });*/

            //设置标签轴标签交错换行显示
//            chart.getCategoryAxis().setLabelLineFeed(XEnum.LabelLineFeed.EVEN_ODD);

            //定义标签轴标签显示格式
            /*chart.getCategoryAxis().setLabelFormatter(new IFormatterTextCallBack() {

                @Override
                public String textFormatter(String value) {
                    // TODO Auto-generated method stub
                    return value;
                }

            });*/

            //定义柱形上标签显示格式
            chart.getBar().setItemLabelVisible(true);
            chart.getBar().setBarInnerMargin(0.1f);
            chart.getBar().setBarTickSpacePercent(0.85f);
            chart.getBar().getItemLabelPaint().setColor(Color.parseColor("#333333"));
            chart.getBar().getItemLabelPaint().setTextSize(PixelUtil.sp2px(6));
            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                @Override
                public String doubleFormatter(Double value) {
                    // TODO Auto-generated method stub
                    DecimalFormat df = new DecimalFormat("#0.00");
                    String label = df.format(value).toString();
                    return label;
                }
            });

            //激活点击监听
            chart.ActiveListenItemClick();

            //仅能横向移动
//            chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);

            //扩展横向显示范围
            //	chart.getPlotArea().extWidth(200f);

            // 设置X轴 标签文字显示大小
            chart.getCategoryAxis().getTickLabelPaint().setTextSize(PixelUtil.sp2px(10));

            //标签文字与轴间距
            chart.getCategoryAxis().setTickLabelMargin(5);

            //不使用精确计算，忽略Java计算误差
            chart.disableHighPrecision();

            // 设置轴标签字体大小
//            chart.getDataAxis().getAxisPaint().setTextSize(PixelUtil.dp2px(15));

            // 隐藏轴刻度线
            chart.getDataAxis().hideTickMarks();
            chart.getDataAxis().hideAxisLabels();

            // 设置底座厚度
            chart.setAxis3DBaseThickness(10);
            // 设置底座颜色
            chart.setAxis3DBaseColor(Color.parseColor("#3a3a3a"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void render(Canvas canvas) {
        try {
            chart.render(canvas);
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            triggerClick(event.getX(), event.getY());
        }
        return true;
    }


    //触发监听
    private void triggerClick(float x, float y) {
        if (!chart.getListenItemClickStatus()) return;

        BarPosition record = chart.getPositionRecord(x, y);
        if (null == record) {
            return;
        }

        if(null != itemClickListener){
            itemClickListener.onItemClick(record.getDataChildID());
        }


        /*BarData bData = chart.getDataSource().get(record.getDataID());
        if(null == bData){
            return ;
        }

        Double bValue = bData.getDataSet().get(record.getDataChildID());
        //在点击处显示tooltip
        mPaintToolTip.setColor(Color.WHITE);
        chart.getToolTip().getBackgroundPaint().setColor(Color.rgb(75, 202, 255));
        chart.getToolTip().getBorderPaint().setColor(Color.RED);
        chart.getToolTip().setCurrentXY(x, y);
        chart.getToolTip().addToolTip(
                " Current Value:" + Double.toString(bValue), mPaintToolTip);
        chart.getToolTip().getBackgroundPaint().setAlpha(100);
        this.invalidate();*/
    }

    public interface OnBarItemClickListener {
        void onItemClick(int position);
    }

}
