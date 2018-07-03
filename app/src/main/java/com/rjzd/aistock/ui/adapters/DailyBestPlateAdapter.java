package com.rjzd.aistock.ui.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.BasicStock;
import com.rjzd.aistock.api.DailyPlateRecommendation;
import com.rjzd.aistock.api.PlateRecommendation;
import com.rjzd.aistock.api.RangeFlag;
import com.rjzd.aistock.api.RecommendReason;
import com.rjzd.aistock.ui.activity.PlateStockActivity;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.commonlib.utils.PixelUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;

/**
 * 每日牛版适配器
 *
 * Created by Hition on 2017/8/4.
 */

public class DailyBestPlateAdapter extends XMBaseAdapter<DailyPlateRecommendation> {

    public DailyBestPlateAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new DailyBestPlateHolder(parent, R.layout.item_daily_recommend);
    }

    private class DailyBestPlateHolder extends BaseViewHolder<DailyPlateRecommendation>{

        private TextView mDate;
        private LinearLayout mRecommendPlate;

        DailyBestPlateHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            mDate = $(R.id.recommend_date);
            mRecommendPlate = $(R.id.plate_container);
        }

        @Override
        public void setData(DailyPlateRecommendation data) {
            mDate.setText(data.get_predictDate());
            // 动态的生成推荐的板块
            List<PlateRecommendation> plateRecommendations = data.get_recommendList();
            mRecommendPlate.removeAllViews();
            if(!plateRecommendations.isEmpty()){
                for(final PlateRecommendation plate : plateRecommendations){
                    // 推荐强度
                    int recommendIndex = plate.get_recommendIndex();
                    // 预测当天涨跌幅
                    double theDayRange = plate.get_rangeOfRecommendDay();
                    // 板块名
                    final String plateName = plate.get_plateName();
                    // 涨跌幅
                    final List<Double> rangeList = plate.get_rangeList();
                    // 推荐理由
                    StringBuilder stringBuilder = new StringBuilder("推荐理由：");
                    final List<RecommendReason> reasonList = plate.get_reasonList();
                    int size = reasonList.size();
                    for(int i=0;i<size;i++){
                        stringBuilder.append(reasonList.get(i).get_summary());
                        if(i != size-1){
                            stringBuilder.append("；");
                        }
                    }
                    String reason = stringBuilder.toString();

                    // 板块关联股票
                    List<BasicStock> stockLeaders = plate.get_stockLeaders();
                    View mView = LayoutInflater.from(mContext).inflate(R.layout.item_recommend_plate,null);

                    RatingBar mIndex = ButterKnife.findById(mView,R.id.recommend_index);
                    mIndex.setRating(recommendIndex);
                    TextView mRange = ButterKnife.findById(mView,R.id.tv_the_day_range);
                    Double range = Double.valueOf(theDayRange);
                    int formatRange = range.intValue();
                    if(-100 == formatRange){
                        theDayRange = 0;
                    }
                    mRange.setText(theDayRange+"");
                    TextView mSymbol = ButterKnife.findById(mView,R.id.symbol);
                    if(theDayRange<0){
                        mRange.setTextColor(ContextCompat.getColor(mContext, R.color.cl_119d3e));
                        mSymbol.setTextColor(ContextCompat.getColor(mContext, R.color.cl_119d3e));
                    }else{
                        mRange.setTextColor(ContextCompat.getColor(mContext, R.color.cl_d63535));
                        mSymbol.setTextColor(ContextCompat.getColor(mContext, R.color.cl_d63535));
                    }
                    TextView mPlateName = ButterKnife.findById(mView,R.id.tv_plate_name);
                    mPlateName.setText(plateName);
                    mPlateName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PlateStockActivity.startActivity(mContext, RangeFlag.RANGE_RISE,plate.get_plateType(),plateName);
                        }
                    });
                    TextView mDetail = ButterKnife.findById(mView,R.id.tv_detail);
                    mDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 展示折线图dialog
//                            showRecommendedCharts(reasonList);
                            showRecommendedCharts(rangeList,reasonList);
                        }
                    });
                    TextView mReason = ButterKnife.findById(mView,R.id.tv_reason);
                    mReason.setText(reason);

                    if(!stockLeaders.isEmpty()){
                        LinearLayout recommendStockLL = ButterKnife.findById(mView,R.id.recommend_stock_ll);
                        for(BasicStock stock : stockLeaders){
                            View recomView = createAndFillRecommendStock(stock);
                            recommendStockLL.addView(recomView);
                        }
                    }

                    mRecommendPlate.addView(mView);
                }
            }
        }
    }

    /**
     * 动态生成推荐的股票布局
     *
     * @param stock  BasicStock
     * @return LinearLayout
     */
    private LinearLayout createAndFillRecommendStock(BasicStock stock){
        LinearLayout mRecommendStockLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams recommendStockLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        recommendStockLP.setMargins(0,0,PixelUtil.dp2px(10),0);
        recommendStockLP.gravity = Gravity.BOTTOM;
        mRecommendStockLayout.setLayoutParams(recommendStockLP);

        TextView mStockName = new TextView(mContext);
        mStockName.setCompoundDrawablePadding(8);
        mStockName.setTextSize(10);
        mStockName.setText(stock.get_name());

        double range = stock.get_range();
        TextView mStockRange = new TextView(mContext);
        mStockRange.setPadding(6,0,0,0);
        mStockRange.setTextSize(10);
        mStockRange.setText(range+"%");

        if(range < 0){
            mStockName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_portfolio_fall,0,0,0);
            mStockName.setTextColor(ContextCompat.getColor(mContext, R.color.cl_119d3e));
            mStockRange.setTextColor(ContextCompat.getColor(mContext, R.color.cl_119d3e));
        }else{
            mStockName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_portfolio_rise,0,0,0);
            mStockName.setTextColor(ContextCompat.getColor(mContext, R.color.cl_d63535));
            mStockRange.setTextColor(ContextCompat.getColor(mContext, R.color.cl_d63535));
        }
        mRecommendStockLayout.addView(mStockName);
        mRecommendStockLayout.addView(mStockRange);

        return mRecommendStockLayout;
    }

    private void showRecommendedCharts(List<Double> rangeList,List<RecommendReason> data) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout = inflater.inflate(R.layout.dialog_recommend_layout, null);
        final Dialog dialog = new Dialog(mContext);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.show();

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setWindowAnimations(R.style.predict_factor_dialog_anim);
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.CENTER);//
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        ImageView mClose = ButterKnife.findById(layout, R.id.iv_recommend_close);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView mTitle = ButterKnife.findById(layout, R.id.recommend_title);
        mTitle.setText(R.string.learn_more);
        LinearLayout mAccordingLayout = ButterKnife.findById(layout,R.id.ll_according);

        int child = 0;
        for(RecommendReason reason : data){
            // 标题
            TextView mDescriptionTitle = new TextView(mContext);
            mDescriptionTitle.setTextSize(14);
            mDescriptionTitle.setTextColor(ContextCompat.getColor(mContext, R.color.cl_333333));
            mDescriptionTitle.setText(reason.get_title());
            mDescriptionTitle.setPadding(0,0,0,0);
            ViewGroup.LayoutParams descriptionLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            mDescriptionTitle.setLayoutParams(descriptionLP);
            mAccordingLayout.addView(mDescriptionTitle);

            LinearLayout labelLayout = new LinearLayout(mContext);
            LinearLayout.LayoutParams labelLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            labelLp.setMargins(0,PixelUtil.dp2px(10),0,0);
            labelLayout.setLayoutParams(labelLp);
            TextView mLabel1 = new TextView(mContext);
            mLabel1.setPadding(0,0,PixelUtil.dp2px(20),0);
            mLabel1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.taking,0,0,0);
            mLabel1.setCompoundDrawablePadding(8);
            mLabel1.setTextColor(ContextCompat.getColor(mContext,R.color.cl_e64225));
            mLabel1.setTextSize(11);
            mLabel1.setText(reason.get_title()+"( "+reason.get_unit()+" )");

            TextView mLabel2 = new TextView(mContext);
            mLabel2.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.rise,0,0,0);
            mLabel2.setCompoundDrawablePadding(8);
            mLabel2.setTextColor(ContextCompat.getColor(mContext,R.color.cl_10174b));
            mLabel2.setTextSize(11);
            mLabel2.setText(R.string.rate);

            labelLayout.addView(mLabel1);
            labelLayout.addView(mLabel2);
            mAccordingLayout.addView(labelLayout);

            List<Double> datas = reason.get_data();
            CombinedChart combinedChart = new CombinedChart(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, PixelUtil.dp2px(180));
            combinedChart.setLayoutParams(lp);
            showCombinedChart(combinedChart,rangeList,datas);
            mAccordingLayout.addView(combinedChart);

            // 概述
            TextView mSummary = new TextView(mContext);
            mSummary.setTextSize(12);
            mSummary.setTextColor(ContextCompat.getColor(mContext, R.color.cl_555555));
            if(TextUtils.isEmpty(reason.get_summary())){
                mSummary.setText("暂无");
            }else{
                mSummary.setText("* " + reason.get_summary());
            }
            mSummary.setPadding(0,10,0,0);
            ViewGroup.LayoutParams summaryLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            mSummary.setLayoutParams(summaryLP);
            mAccordingLayout.addView(mSummary);

            //分割线
            if(child == data.size()-1){
                return ;
            }
            View mDivider = new View(mContext);
            LinearLayout.LayoutParams dividerLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,PixelUtil.dp2px(2));
            dividerLP.setMargins(0,PixelUtil.dp2px(15),0,PixelUtil.dp2px(10));
            mDivider.setLayoutParams(dividerLP);
            mDivider.setLayerType(View.LAYER_TYPE_SOFTWARE,new Paint());
            mDivider.setBackgroundResource(R.drawable.shape_dash_line);
            mAccordingLayout.addView(mDivider);
            child ++ ;
        }
    }

    private void showCombinedChart(CombinedChart chart, final List<Double> rangeList, final List<Double> datas) {
        chart.setDrawBorders(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);
        XAxis xAxis = chart.getXAxis();//x轴
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(rangeList.size());
        xAxis.setTextColor(ContextCompat.getColor(mContext, R.color.cl_888888));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        });
        YAxis centerAxis = chart.getAxisCenter();
        centerAxis.setEnabled(false);

        YAxis leftAxis = chart.getAxisLeft();//y轴
        leftAxis.setDrawZeroLine(true); // draw a zero line
        leftAxis.setZeroLineColor(ContextCompat.getColor(mContext, R.color.cl_e6e6e6));
        leftAxis.setZeroLineWidth(0.6f);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(2, false);
        rightAxis.setTextSize(12);
        rightAxis.setTextColor(ContextCompat.getColor(mContext, R.color.cl_10174b));
        rightAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.format("%.2f", value) + "%";
            }
        });
        Legend mlegend = chart.getLegend();
        mlegend.setEnabled(false);
        int green = ContextCompat.getColor(mContext, R.color.cl_119d3e);
        int red = ContextCompat.getColor(mContext, R.color.cl_e64225);
        ArrayList<Integer> colors = new ArrayList<>();
        //柱图数据
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            entries.add(new BarEntry(i, datas.get(i).floatValue()));
            if (entries.get(i).getY() >= 0) {
                colors.add(red);
            } else {
                colors.add(green);
            }
        }
        BarDataSet barDataSet = new BarDataSet(entries, "");
        barDataSet.setColors(colors);
        barDataSet.setDrawValues(true);
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setValueTextSize(12);
        barDataSet.setValueTextColors(colors);
        barDataSet.setValueFormatter(new DefaultValueFormatter(2){
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.format("%.2f", value) + "";
            }
        });
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        barData.setBarWidth(0.6f);
        //线图数据
        ArrayList<Entry> entries1 = new ArrayList<>();
        for (int i = 0; i < rangeList.size(); i++) {
            entries1.add(new Entry(i, rangeList.get(i).floatValue()));
        }

        LineDataSet lineDataSet = new LineDataSet(entries1, "");
        lineDataSet.setColor(ContextCompat.getColor(mContext, R.color.cl_10174b));
        lineDataSet.setLineWidth(1.8f);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineDataSet.setCircleColorHole(Color.WHITE);
        lineDataSet.setCircleColor(ContextCompat.getColor(mContext, R.color.cl_10174b));
        lineDataSet.setCircleRadius(4f);
        lineDataSet.setCircleHoleRadius(2f);
        lineDataSet.setDrawValues(false);
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedData.setData(barData);
        //图显示不全解决办法
        xAxis.setAxisMinimum(combinedData.getXMin() - 0.6f);
        xAxis.setAxisMaximum(combinedData.getXMax() + 0.6f);
        chart.setData(combinedData);
        chart.animateY(500);
    }


}
