package com.rjzd.aistock.ui.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.FundHolding;
import com.rjzd.aistock.api.FundHoldingData;
import com.rjzd.aistock.api.Fundamentals;
import com.rjzd.aistock.api.FundamentalsData;
import com.rjzd.aistock.api.IchimokuData;
import com.rjzd.aistock.api.IchimokuDataList;
import com.rjzd.aistock.api.Macro;
import com.rjzd.aistock.api.MacroData;
import com.rjzd.aistock.api.MultiDimensionPrediction;
import com.rjzd.aistock.api.PeriodDataList;
import com.rjzd.aistock.api.Periodicity;
import com.rjzd.aistock.api.PredictFactor;
import com.rjzd.aistock.api.PredictFactorLineData;
import com.rjzd.aistock.api.Prediction;
import com.rjzd.aistock.api.PredictionKPS;
import com.rjzd.aistock.api.PredictionTrend;
import com.rjzd.aistock.api.PredictionTrendData;
import com.rjzd.aistock.api.RelatedList;
import com.rjzd.aistock.api.RelatedStock;
import com.rjzd.aistock.api.RiseOrFallPrediction;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.bean.FundamentalItem;
import com.rjzd.aistock.ui.activity.StockDetailsActivity;
import com.rjzd.aistock.ui.adapters.FundamentalAdapter;
import com.rjzd.aistock.ui.adapters.RelationAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.ui.views.MyScrollView;
import com.rjzd.aistock.ui.views.stockcharts.radarview.RadarChartView;
import com.rjzd.aistock.utils.FontUtil;
import com.rjzd.aistock.utils.view.DialogLineUtil;
import com.rjzd.aistock.utils.view.DialogManager;
import com.rjzd.aistock.utils.view.StockPredictionUtil;
import com.rjzd.commonlib.utils.NetWorkUtil;
import com.rjzd.commonlib.utils.PixelUtil;
import org.xclcharts.chart.RadarData;
import org.xclcharts.renderer.XEnum;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_COMPREHENSIVE;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_COMPREHENSIVE_FACTOR;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_ICHIMOKU;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_KPS;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_MACRO;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_PERIODICITY;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_RADAR;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_RELATED_STOCKS;
import static com.rjzd.aistock.Constants.DS_TAG_PREDCTION_STOCK_RESULT;
import static com.rjzd.aistock.R.id.hiddenline;


/**
 * 智能预测
 * Created by Administrator on 2017/1/6.
 */

public class PredictionFragment extends LazyF10Fragment implements MyScrollView.ISmartScrollChangedListener, XMBaseAdapter.OnItemClickListener {

    @Bind(R.id.id_stickynavlayout_innerscrollview)
    MyScrollView mScroll;
    @Bind(R.id.radarchart)
    RadarChartView radarchart;
    @Bind(R.id.rv_relevance)
    RecyclerView rvRelation;

    @Bind(R.id.tv_predition_range)
    TextView tvPreditionRange;

    @Bind(R.id.tv_grade)
    TextView tvGrade;
    @Bind(R.id.stock_name_right)
    TextView stockNameRight;
    @Bind(R.id.linechart)
    LineChart lineChart;
    @Bind(R.id.ichimokuchart)
    LineChart ichimokuchart;
    @Bind(R.id.tv_symbol)
    TextView tvSymbol;
    @Bind(R.id.tv_percent)
    TextView tvPercent;
    @Bind(R.id.predictionkps_summary)
    TextView predictionkpsSummary;
  /*  @Bind(R.id.analysis_time)
    TextView mAnalysisTime;*/

    @Bind(R.id.iv_cloud_map_tips)
    ImageView ichimokuExplain;
    @Bind(R.id.iv_prediction_relevance_trend)
    ImageView mRelevanceTrend;
    @Bind(R.id.iv_radar_summary)
    ImageView ivRadarSummary;
    @Bind(R.id.iv_perdition_summary)
    ImageView ivPerditionSummary;
    @Bind(R.id.iv_cloud_trend)
    ImageView ivCloudTrend;
    @Bind(R.id.day)
    TextView day;
    @Bind(R.id.pre)
    TextView pre;

    @Bind(R.id.iv_multi_dimension_tips)
    ImageView mMutiDimensionTips;
    @Bind(R.id.iv_cyclical_tips)
    ImageView mCyclicalTips;
    @Bind(R.id.iv_relevance_tips)
    ImageView mRelevance;

    @Bind(R.id.predict_none_layout)
    LinearLayout mNoneLayout;
    @Bind(R.id.tv_analysis_time)
    TextView mNoneAnalysisTime;
    @Bind(R.id.predict_ok_layout)
    LinearLayout mOKLayout;

    @Bind(R.id.cyclical_layout)
    LinearLayout mCyclicalLayout;
    @Bind(R.id.cloud_layout)
    LinearLayout mCloudLayout;
    @Bind(R.id.relevance_layout)
    LinearLayout mRelevanceLayout;
    @Bind(R.id.iv_pre_arrows)
    ImageView ivPreArrows;


    private String label;
    private RelationAdapter relatedadapter;
    private boolean isFirst2Bottom = true;

    private static final int FUNDAMENTAL_PB = 10;
    private static final int FUNDAMENTAL_PE = 11;
    private static final int FUNDAMENTAL_EPS = 12;
    private static final int FUNDAMENTAL_ASSETS = 13;
    private static final int FUNDAMENTAL_MKTCAP = 14;
    private int rank;

    private LineChart trendChart;
    private TextView mTrendSummary;

    @Override
    protected int createView() {
        return R.layout.fragment_predition;
    }

    @Override
    protected void initView() {
        super.initView();
        mScroll.setScanScrollChangedListener(this);
        FontUtil.setTextTypeface(tvPreditionRange);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);
        rvRelation.setLayoutManager(gridLayoutManager);
        relatedadapter = new RelationAdapter(activity);
        rvRelation.setAdapter(relatedadapter);
        relatedadapter.setOnItemClickListener(this);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        if (NetWorkUtil.isNetworkConnected(activity)) {
            showLoadingDialog();
            // 综合评分
            presenter.getKPS(stockCode);
            // 综合预测结果
            presenter.getComplexPrediction(stockCode);
            // 多因子预测
            presenter.getPrediction(stockCode);
            // 周期性预测
            presenter.getPeriodPredict(stockCode);
            // 云图分析
            presenter.getIchimoku(stockCode);
        }else{
            activity.showToast(R.string.no_network);
        }

    }

    @Override
    public void setStatistical() {
        statistical = "smart_prediction";
    }

    @Override
    public void fillData(Object data, final int dsTag) {
        super.fillData(data, dsTag);
        switch (dsTag) {
            //综合预测结果
            case DS_TAG_PREDCTION_COMPREHENSIVE:
                Prediction preData = (Prediction) data;
                if (StatusCode.OK.getValue() == preData.get_status().getValue()) {
                    mHasLoadedOnce = true;
                    pre.setText("涨跌幅");
                    day.setText(preData.get_predictDate());
                    tvPreditionRange.setText(Math.abs(preData.get_predictionRange()) + "");
                    if (preData.get_predictionRange() < 0) {
                        tvPreditionRange.setTextColor(ContextCompat.getColor(activity, R.color.cl_119d3e));
                        tvSymbol.setTextColor(ContextCompat.getColor(activity, R.color.cl_119d3e));
                        tvSymbol.setText("-");
                        tvPercent.setTextColor(ContextCompat.getColor(activity, R.color.cl_119d3e));
                        ivPreArrows.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.pre_fall));
                    } else if (preData.get_predictionRange() > 0) {
                        tvPreditionRange.setTextColor(ContextCompat.getColor(activity, R.color.cl_d63535));
                        tvSymbol.setTextColor(ContextCompat.getColor(activity, R.color.cl_d63535));
                        tvSymbol.setText("+");
                        tvPercent.setTextColor(ContextCompat.getColor(activity, R.color.cl_d63535));
                        ivPreArrows.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.pre_raise));
                    } else {
                        tvPreditionRange.setTextColor(ContextCompat.getColor(activity, R.color.cl_999999));
                        tvSymbol.setTextColor(ContextCompat.getColor(activity, R.color.cl_999999));
                        tvSymbol.setText("+");
                        tvPercent.setTextColor(ContextCompat.getColor(activity, R.color.cl_999999));
                        ivPreArrows.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.pre_flat));
                    }
                }

                break;
            //综合评分
            case DS_TAG_PREDCTION_KPS:
                PredictionKPS predictionKPS = (PredictionKPS) data;
                if (StatusCode.OK.getValue() == predictionKPS.get_status().getValue()) {
                    setComplexScoreView(predictionKPS.get_summary(), predictionKPS.get_kpsDate(), predictionKPS.get_score());
                } else if (StatusCode.PREDICT_NONE.getValue() == predictionKPS.get_status().getValue()) {
                    mNoneLayout.setVisibility(View.VISIBLE);
                    mOKLayout.setVisibility(View.GONE);
                    mNoneAnalysisTime.setText(activity.getString(R.string.analysis_time) + predictionKPS.get_kpsDate());
                }
                break;
            //因子详情
            case DS_TAG_PREDCTION_COMPREHENSIVE_FACTOR:
                PredictFactorLineData predictFactorLineData = (PredictFactorLineData) data;
                if (StatusCode.OK.getValue() == predictFactorLineData.get_status().getValue()) {
                    showTechOrTrendFactor(predictFactorLineData);
                }
                break;
            //多因子预测结果
            case DS_TAG_PREDCTION_RADAR:
                final MultiDimensionPrediction prediction = (MultiDimensionPrediction) data;
                if (StatusCode.OK.getValue() == prediction.get_status().getValue()) {
                    if (RiseOrFallPrediction.RISE.getValue() == prediction.get_predictResult().getValue()) {
                        ivRadarSummary.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_predict_rise));
                    } else if (RiseOrFallPrediction.FALL.getValue() == prediction.get_predictResult().getValue()) {
                        ivRadarSummary.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_predict_fall));
                    } else {
                        ivRadarSummary.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_predict_flat));
                    }


                    List<PredictFactor> periodDataLists = prediction.get_data();
                    setFactorData(periodDataLists);
                    mMutiDimensionTips.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogManager.showNoButtonDialog(activity, activity.getString(R.string.prediction_multi_dimension), prediction.get_summary(), true, dsTag);
                        }
                    });
                }
                break;

            case Constants.DS_TAG_PREDCTION_FUNDHOLDING:     // 机构持仓因子
                FundHoldingData holdingData = (FundHoldingData) data;
                if (StatusCode.OK.getValue() == holdingData.get_status().getValue()) {
                    List<FundHolding> holdings = holdingData.get_data();
                    if (null != holdings && !holdings.isEmpty()) {
                        List<String> dateList = new ArrayList<>();
                        List<Double> priceList = new ArrayList<>();
                        List<Double> holdTotals = new ArrayList<>();
                        List<Double> holdCounts = new ArrayList<>();
                        List<Double> holdPercentages = new ArrayList<>();
                        for (FundHolding holding : holdings) {
                            dateList.add(holding.get_year() + "年" + holding.get_quarter() + "季度");
                            priceList.add(holding.get_price());
                            holdTotals.add((double) holding.get_holdTotal());
                            holdCounts.add(holding.get_count());
                            holdPercentages.add(holding.get_holdPercentage());
                        }
                        showFundholdingDialog(label, holdTotals, holdCounts, holdPercentages, priceList, dateList);
                    }

                }
                break;

            case Constants.DS_TAG_PREDCTION_FUNDAMENTALS:     // 基本面因子
                FundamentalsData fData = (FundamentalsData) data;
                if (StatusCode.OK.getValue() == fData.get_status().getValue()) {
                    showFundamentalDialog(fData, label);
                }
                break;

            case DS_TAG_PREDCTION_MACRO:                     //雷达宏观因子
                MacroData macroData = (MacroData) data;
                if (StatusCode.OK.getValue() == macroData.get_status().getValue()) {
                    Macro macro = macroData.get_data();
                    if (null != macro) {
                        showMacroFactor(macro);
                    }
                }
                break;

            case DS_TAG_PREDCTION_PERIODICITY:                      //周期性
                final PeriodDataList periodDataList = (PeriodDataList) data;
                if (StatusCode.OK.getValue() == periodDataList.get_status().getValue()) {
                    if (RiseOrFallPrediction.RISE.getValue() == periodDataList.get_predictResult().getValue()) {
                        ivPerditionSummary.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_predict_rise));
                    } else if (RiseOrFallPrediction.FALL.getValue() == periodDataList.get_predictResult().getValue()) {
                        ivPerditionSummary.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_predict_fall));
                    } else {
                        ivPerditionSummary.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_predict_flat));
                    }
                    List<Periodicity> periodDataLists = periodDataList.get_data();
                    if (periodDataLists.size() != 0) {
                        setLineChart(periodDataLists);
                    }
                    mCyclicalTips.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogManager.showNoButtonDialog(activity, activity.getString(R.string.prediction_cyclical), periodDataList.get_summary(), true, dsTag);
                        }
                    });
                } else if (StatusCode.PREDICT_NONE.getValue() == periodDataList.get_status().getValue()) {
                    mCyclicalLayout.setVisibility(View.GONE);
                }
                break;

            case DS_TAG_PREDCTION_ICHIMOKU:                            //云图
                final IchimokuDataList ichimokuDataList = (IchimokuDataList) data;
                if (ichimokuDataList.get_status().getValue() == StatusCode.OK.getValue()) {

                    if (RiseOrFallPrediction.RISE.getValue() == ichimokuDataList.get_predictResult().getValue()) {
                        ivCloudTrend.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_predict_rise));
                    } else if (RiseOrFallPrediction.FALL.getValue() == ichimokuDataList.get_predictResult().getValue()) {
                        ivCloudTrend.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_predict_fall));
                    } else {
                        ivCloudTrend.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_predict_flat));
                    }
                    List<IchimokuData> ichimokuDatas = ichimokuDataList.get_data();
                    setIchimokuChart(ichimokuDatas);
                    ichimokuExplain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogManager.showIchimokuChartDialog(activity, true, ichimokuDataList.get_summary(), ichimokuDataList.get_feature());
                        }
                    });
                } else if (StatusCode.PREDICT_NONE.getValue() == ichimokuDataList.get_status().getValue()) {
                    mCloudLayout.setVisibility(View.GONE);
                }
                break;

            case DS_TAG_PREDCTION_RELATED_STOCKS:                      //关联股
                final RelatedList relatedList = (RelatedList) data;
                if (StatusCode.OK.getValue() == relatedList.get_status().getValue()) {
                    if (RiseOrFallPrediction.RISE.getValue() == relatedList.get_predictResult().getValue()) {
                        mRelevanceTrend.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_predict_rise));
                    } else if (RiseOrFallPrediction.FALL.getValue() == relatedList.get_predictResult().getValue()) {
                        mRelevanceTrend.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_predict_fall));
                    } else {
                        mRelevanceTrend.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_predict_flat));
                    }
                    List<RelatedStock> relatedStocks = relatedList.get_data();
                    if (null != relatedStocks && !relatedStocks.isEmpty()) {
                        relatedadapter.addAll(relatedStocks);
                    }

                    mRelevance.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogManager.showNoButtonDialog(activity, activity.getString(R.string.prediction_relevance), relatedList.get_summary(), true, dsTag);
                        }
                    });
                } else if (StatusCode.PREDICT_NONE.getValue() == relatedList.get_status().getValue()) {
                    mRelevanceLayout.setVisibility(View.GONE);
                }
                break;
            //预测股价
            case DS_TAG_PREDCTION_STOCK_RESULT:
                PredictionTrendData predictionTrendData = (PredictionTrendData) data;
                if (StatusCode.OK.getValue() == predictionTrendData.get_status().getValue()) {
                    PredictionTrend redictionTrend = predictionTrendData.get_data();

                    mTrendSummary.setText("* " + redictionTrend.get_summary());
                    setTrendChart(redictionTrend);
                }
                break;
        }
    }

    /**
     * 预测股价图
     *
     * @param predictionTrend
     */
    private void setTrendChart(final PredictionTrend predictionTrend) {
        trendChart.setDrawBorders(false);
        trendChart.getDescription().setEnabled(false);
        trendChart.setDrawGridBackground(false);
        trendChart.setTouchEnabled(true);
        trendChart.setDragEnabled(false);
        trendChart.setScaleEnabled(false);
        trendChart.setPinchZoom(false);
        YAxis centerAxis = trendChart.getAxisCenter();
        centerAxis.setEnabled(false);
        YAxis leftAxis = trendChart.getAxisLeft();//y轴
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setLabelCount(4, false);
        leftAxis.setTextColor(ContextCompat.getColor(activity, R.color.cl_999999));
        leftAxis.setTextSize(9);
        YAxis rightAxis = trendChart.getAxisRight();
        rightAxis.setEnabled(false);
        Legend mlegend = trendChart.getLegend();
        mlegend.setEnabled(false);
        XAxis xAxis = trendChart.getXAxis();   //x轴
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelCount(predictionTrend.get_actualData().get_priceList().size() + predictionTrend.get_predictData().get_priceList().size() - 1, false);
        xAxis.setTextSize(9);
        xAxis.setTextColor(ContextCompat.getColor(activity, R.color.cl_888888));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int) value == 0)
                    return predictionTrend.get_actualData().get_startTiem();
                else if (Math.round(value) == predictionTrend.get_actualData().get_priceList().size() - 1)
                    return predictionTrend.get_predictData().get_startTiem();
                else if ((int) value == predictionTrend.get_actualData().get_priceList().size() + predictionTrend.get_predictData().get_priceList().size() - 1)
                    return predictionTrend.get_predictData().get_endTime();

                else return "";
            }
        });

        ArrayList<Entry> entries1 = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < predictionTrend.get_actualData().get_priceList().size(); i++) {
            entries1.add(new Entry(i, predictionTrend.get_actualData().get_priceList().get(i).floatValue()));
            if (i == predictionTrend.get_actualData().get_priceList().size() - 1) {
                entries2.add(new Entry(i, predictionTrend.get_actualData().get_priceList().get(i).floatValue()));
            } else {
                entries2.add(new Entry(i, Float.NaN));
            }
        }
        for (int i = 0; i < predictionTrend.get_predictData().get_priceList().size(); i++) {
            entries2.add(new Entry(predictionTrend.get_actualData().get_priceList().size() + i, predictionTrend.get_predictData().get_priceList().get(i).floatValue()));
        }
        LineDataSet set = new LineDataSet(entries1, "");
        set.setLineWidth(1.0f);
        set.setDrawCircles(true);
        set.setDrawCircleHole(false);
        set.setCircleColor(ContextCompat.getColor(activity, R.color.cl_e64225));
        set.setDrawValues(false);
        set.setColor(ContextCompat.getColor(activity, R.color.cl_e64225));

        LineDataSet set1 = new LineDataSet(entries2, "");
        set1.setLineWidth(1.0f);
        set1.setDrawCircles(true);
        set1.setDrawCircleHole(false);
        set1.setCircleColor(ContextCompat.getColor(activity, R.color.cl_0d74cb));
        set1.setDrawValues(false);
        set1.enableDashedLine(10, 10, 0);
        set1.setColor(ContextCompat.getColor(activity, R.color.cl_0d74cb));
        LineData lineData = new LineData();
        lineData.addDataSet(set1);
        lineData.addDataSet(set);

        xAxis.setAxisMinimum(lineData.getXMin() - 0.6f);
        xAxis.setAxisMaximum(lineData.getXMax() + 0.6f);
        trendChart.setData(lineData);
        trendChart.invalidate();


    }

    /**
     * 机构持股dialog
     *
     * @param title              标题
     * @param holdTotalList      持股家数
     * @param holdCountList      持股总数
     * @param holdPercentageList 占流通股比例
     * @param priceList          股价
     * @param dateList           日期
     * @return
     */
    private Dialog showFundholdingDialog(String title, List<Double> holdTotalList, List<Double> holdCountList, List<Double> holdPercentageList, List<Double> priceList, List<String> dateList) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View layout = inflater.inflate(R.layout.dialog_prediction_fund_holding_layout, null);
        final Dialog dialog = new Dialog(activity);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.show();

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setWindowAnimations(R.style.predict_factor_dialog_anim);
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.CENTER);//
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        TextView tvtitle = ButterKnife.findById(layout, R.id.predition_title);
        tvtitle.setText(title);
        TextView mLabel = (TextView) dialog.findViewById(R.id.tv_title);
        mLabel.setText("机构持仓预测");
        ImageView mClose = ButterKnife.findById(layout, R.id.iv_close);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mTrendSummary = ButterKnife.findById(layout, R.id.tv_trend_summary);
        trendChart = ButterKnife.findById(layout, R.id.hiddenline);
        initChartData(title);
        CombinedChart holdTotalChart = ButterKnife.findById(layout, R.id.holdtotal_combinedchart);
        CombinedChart holdCountChart = ButterKnife.findById(layout, R.id.hold_count_combinedchart);
        CombinedChart holdPercentageChart = ButterKnife.findById(layout, R.id.hold_percentage_combinedchart);

        StockPredictionUtil.showMarcoChart(activity, holdTotalChart, holdTotalList, priceList, dateList, 0, true);
        StockPredictionUtil.showMarcoChart(activity, holdCountChart, holdCountList, priceList, dateList, 1, true);
        StockPredictionUtil.showMarcoChart(activity, holdPercentageChart, holdPercentageList, priceList, dateList, 2, true);

        return dialog;
    }

    private void initChartData(String title) {
        presenter.getPredictionTrend(stockCode, title);
    }

    /**
     * 设置综合评分view
     */
    private void setComplexScoreView(String summary, String analysisDate, double score) {
        stockNameRight.setText(stockName + "AI综合评分");
        predictionkpsSummary.setText(summary);
        //mAnalysisTime.setText("分析时间：" + analysisDate);
        LinearLayout.LayoutParams mscoreLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(30), LinearLayout.LayoutParams.WRAP_CONTENT);
        float mar = 0;
        if (score <= 42.2) {
            mar = new Float(score / 42.2);
            if (score < 42.2 / 2.0) {
                tvGrade.setBackground(ContextCompat.getDrawable(activity, R.drawable.green_left));
            } else {
                tvGrade.setBackground(ContextCompat.getDrawable(activity, R.drawable.green_right));
            }
        } else if (score > 42.2 && score <= 49.5) {
            mar = new Float((score - 42.2) / (49.5 - 42.2) + 1);
            if (score - 42.2 < (49.5 - 42.2) / 2.0) {
                tvGrade.setBackground(ContextCompat.getDrawable(activity, R.drawable.blueness_left));
            } else {
                tvGrade.setBackground(ContextCompat.getDrawable(activity, R.drawable.blueness_right));
            }
        } else if (score > 49.5 && score <= 59) {
            mar = new Float((score - 49.5) / (59 - 49.5) + 2);
            if (score - 49.5 < (59 - 49.5) / 2.0) {
                tvGrade.setBackground(ContextCompat.getDrawable(activity, R.drawable.blue_left));
            } else {
                tvGrade.setBackground(ContextCompat.getDrawable(activity, R.drawable.blue_right));
            }
        } else if (score > 59 && score <= 67) {
            mar = new Float((score - 59) / (67 - 59) + 3);
            if (score - 59 < (67 - 59) / 2.0) {
                tvGrade.setBackground(ContextCompat.getDrawable(activity, R.drawable.yellow_left));
            } else {
                tvGrade.setBackground(ContextCompat.getDrawable(activity, R.drawable.yellow_right));
            }
        } else {
            mar = new Float((score - 67) / (100 - 67) + 4);
            if (score - 67 < (100 - 67) / 2.0) {
                tvGrade.setBackground(ContextCompat.getDrawable(activity, R.drawable.red_left));
            } else {
                tvGrade.setBackground(ContextCompat.getDrawable(activity, R.drawable.red_right));
            }
        }
        mscoreLP.setMargins((int) (mar * PixelUtil.dp2px(60)), 0, 0, 0);
        tvGrade.setLayoutParams(mscoreLP);
        tvGrade.setText(score + "");
    }

    /**
     * 显示基本面dialog
     */
    private Dialog showFundamentalDialog(FundamentalsData fData, String title) {
        rank = fData.get_rank();
        Fundamentals selfFund = fData.get_selfFundamental();
        Fundamentals avgFund = fData.get_industryAvg();
        Fundamentals top1Fund = fData.get_first();

        LayoutInflater inflater = LayoutInflater.from(activity);
        View layout = inflater.inflate(R.layout.dialog_fundamentals_layout, null);

        final Dialog dialog = new Dialog(activity);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.show();

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setWindowAnimations(R.style.predict_factor_dialog_anim);
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.CENTER);//
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        ImageView mClose = ButterKnife.findById(layout, R.id.iv_close);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView tvtitle = ButterKnife.findById(layout, R.id.predition_title);
        tvtitle.setText(title);
        TextView mLabel = ButterKnife.findById(layout, R.id.tv_title);
        mLabel.setText("基本面预测");
        mTrendSummary = ButterKnife.findById(layout, R.id.tv_trend_summary);
        trendChart = (LineChart) dialog.findViewById(hiddenline);
        initChartData(title);
        List<FundamentalItem> pbDataList = initFundamental(FUNDAMENTAL_PB, avgFund, top1Fund, selfFund);
        initPB(layout, pbDataList);

        List<FundamentalItem> peDataList = initFundamental(FUNDAMENTAL_PE, avgFund, top1Fund, selfFund);
        initPE(layout, peDataList);

        List<FundamentalItem> profitsDataList = initFundamental(FUNDAMENTAL_EPS, avgFund, top1Fund, selfFund);
        initProfits(layout, profitsDataList);

        List<FundamentalItem> assetsDataList = initFundamental(FUNDAMENTAL_ASSETS, avgFund, top1Fund, selfFund);
        initAssets(layout, assetsDataList);

        List<FundamentalItem> mkcapDataList = initFundamental(FUNDAMENTAL_MKTCAP, avgFund, top1Fund, selfFund);
        initMktcap(layout, mkcapDataList);

        return dialog;
    }

    public List<FundamentalItem> initFundamental(int type, Fundamentals... fundamentals) {
        List<FundamentalItem> pbDataList = new ArrayList<>();
        Fundamentals avgFund = fundamentals[0];
        Fundamentals top1Fund = fundamentals[1];
        Fundamentals selfFund = fundamentals[2];

        FundamentalItem avg = null;
        FundamentalItem first = null;
        FundamentalItem self = null;

        switch (type) {
            case FUNDAMENTAL_PB:
                double maxPB = Math.max(Math.max(selfFund.get_pb(), avgFund.get_pb()), top1Fund.get_pb());
                int avgProcess = getProgress(avgFund.get_pb(), maxPB);
                avg = new FundamentalItem("  ", avgFund.get_stockName(), avgProcess, avgFund.get_pb() + "", Constants.FUNDAMENTALS_AVG);
                int selfProcess = getProgress(selfFund.get_pb(), maxPB);
                self = new FundamentalItem(rank + "", selfFund.get_stockName(), selfProcess, selfFund.get_pb() + "", Constants.FUNDAMENTALS_SELF);
                if (!selfFund.get_stockName().equals(top1Fund.get_stockName())) {
                    int firstProcess = getProgress(top1Fund.get_pb(), maxPB);
                    first = new FundamentalItem(1 + " ", top1Fund.get_stockName(), firstProcess, top1Fund.get_pb() + "", Constants.FUNDAMENTALS_FIRST);
                }

                break;

            case FUNDAMENTAL_PE:
                double maxPE = Math.max(Math.max(selfFund.get_pe(), avgFund.get_pe()), top1Fund.get_pe());
                int avgPEProcess = getProgress(avgFund.get_pe(), maxPE);
                avg = new FundamentalItem("  ", avgFund.get_stockName(), avgPEProcess, avgFund.get_pe() + "", Constants.FUNDAMENTALS_AVG);
                int selfPEProcess = getProgress(selfFund.get_pe(), maxPE);
                self = new FundamentalItem(rank + "", selfFund.get_stockName(), selfPEProcess, selfFund.get_pe() + "", Constants.FUNDAMENTALS_SELF);
                if (!selfFund.get_stockName().equals(top1Fund.get_stockName())) {
                    int top1PEProcess = getProgress(top1Fund.get_pe(), maxPE);
                    first = new FundamentalItem(1 + " ", top1Fund.get_stockName(), top1PEProcess, top1Fund.get_pe() + "", Constants.FUNDAMENTALS_FIRST);
                }
                break;

            case FUNDAMENTAL_EPS:
                double maxProfits = Math.max(Math.max(selfFund.get_eps(), avgFund.get_eps()), top1Fund.get_eps());
                int avgProfitsProcess = getProgress(avgFund.get_eps(), maxProfits);
                avg = new FundamentalItem("  ", avgFund.get_stockName(), avgProfitsProcess, avgFund.get_eps() + "元", Constants.FUNDAMENTALS_AVG);
                int selfProfitsProcess = getProgress(selfFund.get_eps(), maxProfits);
                self = new FundamentalItem(rank + "", selfFund.get_stockName(), selfProfitsProcess, selfFund.get_eps() + "元", Constants.FUNDAMENTALS_SELF);

                if (!selfFund.get_stockName().equals(top1Fund.get_stockName())) {
                    int top1ProfitsProcess = getProgress(top1Fund.get_eps(), maxProfits);
                    first = new FundamentalItem(1 + " ", top1Fund.get_stockName(), top1ProfitsProcess, top1Fund.get_eps() + "元", Constants.FUNDAMENTALS_FIRST);
                }
                break;

            case FUNDAMENTAL_ASSETS:
                double maxAssets = Math.max(Math.max(selfFund.get_fixedAssets(), avgFund.get_fixedAssets()), top1Fund.get_fixedAssets());
                int avgAssetsProcess = getProgress(avgFund.get_fixedAssets(), maxAssets);
                avg = new FundamentalItem("  ", avgFund.get_stockName(), avgAssetsProcess, avgFund.get_fixedAssets() + "亿元", Constants.FUNDAMENTALS_AVG);
                int selfAssetsProcess = getProgress(selfFund.get_fixedAssets(), maxAssets);
                self = new FundamentalItem(rank + "", selfFund.get_stockName(), selfAssetsProcess, selfFund.get_fixedAssets() + "亿元", Constants.FUNDAMENTALS_SELF);

                if (!selfFund.get_stockName().equals(top1Fund.get_stockName())) {
                    int top1AssetsProcess = getProgress(top1Fund.get_fixedAssets(), maxAssets);
                    first = new FundamentalItem(1 + " ", top1Fund.get_stockName(), top1AssetsProcess, top1Fund.get_fixedAssets() + "亿元", Constants.FUNDAMENTALS_FIRST);
                }
                break;

            case FUNDAMENTAL_MKTCAP:
                double maxMktcap = Math.max(Math.max(selfFund.get_mktcap(), avgFund.get_mktcap()), top1Fund.get_mktcap());
                int avgMktcapProcess = getProgress(avgFund.get_mktcap(), maxMktcap);
                avg = new FundamentalItem("  ", avgFund.get_stockName(), avgMktcapProcess, avgFund.get_mktcap() + "亿元", Constants.FUNDAMENTALS_AVG);
                int selfMktcapProcess = getProgress(selfFund.get_mktcap(), maxMktcap);
                self = new FundamentalItem(rank + "", selfFund.get_stockName(), selfMktcapProcess, selfFund.get_mktcap() + "亿元", Constants.FUNDAMENTALS_SELF);

                if (!selfFund.get_stockName().equals(top1Fund.get_stockName())) {
                    int top1MktcapProcess = getProgress(top1Fund.get_mktcap(), maxMktcap);
                    first = new FundamentalItem(1 + " ", top1Fund.get_stockName(), top1MktcapProcess, top1Fund.get_mktcap() + "亿元", Constants.FUNDAMENTALS_FIRST);
                }
                break;
        }

        pbDataList.add(avg);
        if (null != first) {
            pbDataList.add(first);
        }
        pbDataList.add(self);
        return pbDataList;
    }

    private void initPB(View parent, List<FundamentalItem> pbData) {
        RecyclerView mPBView = ButterKnife.findById(parent, R.id.rv_pb);
        LinearLayoutManager layout = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        mPBView.setLayoutManager(layout);
        FundamentalAdapter pbAdapter = new FundamentalAdapter(activity, pbData);
        mPBView.setAdapter(pbAdapter);
    }

    private void initPE(View parent, List<FundamentalItem> peData) {
        RecyclerView mPEView = ButterKnife.findById(parent, R.id.rv_pe);
        LinearLayoutManager layout = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        mPEView.setLayoutManager(layout);
        FundamentalAdapter peAdapter = new FundamentalAdapter(activity, peData);
        mPEView.setAdapter(peAdapter);
    }

    private void initProfits(View parent, List<FundamentalItem> profitsDataList) {
        RecyclerView mProfitsView = ButterKnife.findById(parent, R.id.rv_profit);
        LinearLayoutManager layout = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        mProfitsView.setLayoutManager(layout);
        FundamentalAdapter profitAdapter = new FundamentalAdapter(activity, profitsDataList);
        mProfitsView.setAdapter(profitAdapter);
    }

    private void initAssets(View parent, List<FundamentalItem> assetsDataList) {
        RecyclerView mAssetView = ButterKnife.findById(parent, R.id.rv_fixed_assets);
        LinearLayoutManager layout = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        mAssetView.setLayoutManager(layout);
        FundamentalAdapter fixedAssetsAdapter = new FundamentalAdapter(activity, assetsDataList);
        mAssetView.setAdapter(fixedAssetsAdapter);
    }

    private void initMktcap(View parent, List<FundamentalItem> mktcapList) {
        RecyclerView mktcapView = ButterKnife.findById(parent, R.id.rv_mktcap);
        LinearLayoutManager layout = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        mktcapView.setLayoutManager(layout);
        FundamentalAdapter mktcapAdapter = new FundamentalAdapter(activity, mktcapList);
        mktcapView.setAdapter(mktcapAdapter);
    }

    /**
     * 获取进度
     */
    private int getProgress(double value, double max) {
        int avgProcess = Double.valueOf(100 * Math.abs(value) / max).intValue();
        if (avgProcess < 5) {
            avgProcess = 5;
        } else if (avgProcess > 100) {
            avgProcess = 100;
        }
        return avgProcess;
    }

    /**
     * 宏观因子dialog
     *
     * @param macro
     * @return
     */
    public Dialog showMacroFactor(Macro macro) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View layout = inflater.inflate(R.layout.dialog_prediction_macro_layout, null);
        final Dialog dialog = new Dialog(activity);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.show();

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setWindowAnimations(R.style.predict_factor_dialog_anim);
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.CENTER);//
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        //  dialog.setTitle(title);
        ImageView mClose = ButterKnife.findById(layout, R.id.iv_close);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView tvtitle = ButterKnife.findById(layout, R.id.predition_title);
        TextView mLabel = ButterKnife.findById(layout, R.id.tv_title);
        tvtitle.setText(label);
        mLabel.setText(label+"预测");
        TextView tv_exponent = ButterKnife.findById(layout, R.id.tv_exponent1);
        tv_exponent.setText(macro.get_exponentName1());
        TextView tv_exponent2 = ButterKnife.findById(layout, R.id.tv_exponent2);
        tv_exponent2.setText(macro.get_exponentName2());

        mTrendSummary = ButterKnife.findById(layout, R.id.tv_trend_summary);
        trendChart = ButterKnife.findById(layout, R.id.hiddenline);
        initChartData(label);
        CombinedChart combinedChart = ButterKnife.findById(layout, R.id.macro_combinedchart);
        CombinedChart combinedChart2 = ButterKnife.findById(layout, R.id.macro_combinedchart2);

        dialog.setCancelable(true);
        StockPredictionUtil.showMarcoChart(activity, combinedChart, macro.get_exponent1(), macro.get_price(), macro.get_dateList(), 3, false);
        StockPredictionUtil.showMarcoChart(activity, combinedChart2, macro.get_exponent2(), macro.get_price(), macro.get_dateList(), 3, false);
        dialog.show();
        return dialog;
    }

    /**
     * 技术或趋势因子Dialog
     *
     * @param predictFactorLineData
     * @return
     */
    public Dialog showTechOrTrendFactor(PredictFactorLineData predictFactorLineData) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View layout = inflater.inflate(R.layout.dialog_ai_linechart_layout, null);
        final Dialog dialog = new Dialog(activity);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.show();

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setWindowAnimations(R.style.predict_factor_dialog_anim);
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.CENTER);//
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        ImageView mClose = ButterKnife.findById(layout, R.id.iv_close);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView tvtitle = ButterKnife.findById(layout, R.id.predition_title);
        tvtitle.setText(label);
        TextView mLabel = (TextView) dialog.findViewById(R.id.tv_title);
        mLabel.setText(label + "预测");
        LineChart lineChart1 = ButterKnife.findById(layout, R.id.predition_linechart1);
        LineChart lineChart2 = ButterKnife.findById(layout, R.id.predition_linechart2);
        mTrendSummary = ButterKnife.findById(layout, R.id.tv_trend_summary);
        trendChart = ButterKnife.findById(layout, R.id.hiddenline);
        initChartData(label);

        TextView mFirstReleatedExponent = ButterKnife.findById(layout, R.id.first_releated_exponent);
        mFirstReleatedExponent.setText(predictFactorLineData.get_elements().get(0));
        TextView mSecondReleatedExponent = ButterKnife.findById(layout, R.id.second_releated_exponent);
        mSecondReleatedExponent.setText(predictFactorLineData.get_elements().get(1));
        dialog.setCancelable(true);
        DialogLineUtil.showDialogLinechart(lineChart1, predictFactorLineData.get_labelMapValues().get(2), predictFactorLineData.get_labelMapValues().get(0), predictFactorLineData.get_periods());
        DialogLineUtil.showDialogLinechart(lineChart2, predictFactorLineData.get_labelMapValues().get(2), predictFactorLineData.get_labelMapValues().get(1), predictFactorLineData.get_periods());
        dialog.show();
        return dialog;
    }

    private void setFactorData(List<PredictFactor> factors) {
        final List<String> labels = new LinkedList<>();
        final List<Double> chartData = new LinkedList<>();

        for (PredictFactor pf : factors) {
            labels.add(pf.get_factorName());

            chartData.add(pf.get_factorValue());
        }
        RadarData radarData = new RadarData("", chartData,
                Color.rgb(214, 53, 53), XEnum.DataAreaStyle.FILL);
        radarData.getPlotLine().setDotStyle(XEnum.DotStyle.DOT);
        radarData.setDotRadius(PixelUtil.dp2px(2));
        List<Bitmap> bitmaps = new ArrayList<>();
        Bitmap bmp1 = BitmapFactory.decodeResource(activity.getResources(), R.drawable.hongguan_img);
        Bitmap bmp2 = BitmapFactory.decodeResource(activity.getResources(), R.drawable.jigou_img);
        Bitmap bmp3 = BitmapFactory.decodeResource(activity.getResources(), R.drawable.jibenmian_img);
        Bitmap bmp4 = BitmapFactory.decodeResource(activity.getResources(), R.drawable.dongliang_img);
        Bitmap bmp5 = BitmapFactory.decodeResource(activity.getResources(), R.drawable.qushi_img);
        for (int i = 0; i < labels.size(); i++) {
            if ("基本面".equals(labels.get(i))) {
                bitmaps.add(bmp3);
            } else if ("动量指标".equals(labels.get(i))) {
                bitmaps.add(bmp4);
            } else if ("机构持仓".equals(labels.get(i))) {
                bitmaps.add(bmp2);
            } else if ("宏观指标".equals(labels.get(i))) {
                bitmaps.add(bmp1);
            } else {
                bitmaps.add(bmp5);
            }
        }
        radarchart.setDataSource(radarData, labels, bitmaps);
        radarchart.refreshChart();
        radarchart.setOnItemClickListener(new RadarChartView.OnRadarItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Double data = chartData.get(position);
                if (data > 0) {
                    if(NetWorkUtil.isNetworkConnected(activity)){
                        label = labels.get(position);
                        showLoadingDialog();
                        if ("宏观指标".equals(label)) {
                            presenter.getMacro(stockCode);
                        } else if ("机构持仓".equals(label)) {
                            presenter.getFundHolding(stockCode);
                        } else if ("基本面".equals(label)) {
                            presenter.getFundamentals(stockCode);
                        } else {
                            presenter.getPredictionFactor(stockCode, label);
                        }
                    }
                }else{
                    showToast(activity.getString(R.string.radar_factor_predict_none));
                }
            }
        });
    }

    //周期图
    private void setLineChart(final List<Periodicity> periodDataLists) {
        lineChart.setDrawBorders(false);   //折线图上添加边框
        lineChart.getDescription().setEnabled(false);  //去掉数据描述
        lineChart.setBackgroundColor(Color.parseColor("#ffffff"));
        lineChart.setTouchEnabled(false);  //是否可以触摸
        lineChart.setPinchZoom(false);//是否可以双手缩放
        lineChart.setMaxVisibleValueCount(200);
        // x轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴的位置
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineColor(Color.parseColor("#f5cf90"));

        xAxis.setDrawLabels(true);
        xAxis.setLabelCount(2, true);     //X轴描述文字个数
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(10);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int) value == 0) {
                    return periodDataLists.get(0).get_date();
                } else if ((int) value == periodDataLists.size() - 1) {
                    return periodDataLists.get(periodDataLists.size() - 1).get_date();
                } else {
                    return "";
                }
            }
        });

        //y轴
        YAxis centerAxis = lineChart.getAxisCenter();
        centerAxis.setEnabled(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.GRAY);
        leftAxis.setTextSize(10);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisLineColor(Color.parseColor("#f5cf90"));
        leftAxis.setLabelCount(4, true);
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(true);
        rightAxis.setTextColor(Color.parseColor("#00f5cf90"));
        rightAxis.setTextSize(13);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisLineColor(Color.parseColor("#00f5cf90"));

        Legend mlegend = lineChart.getLegend();
        LinkedList<LegendEntry> linkedList = new LinkedList<LegendEntry>();
        LegendEntry redentry = new LegendEntry();
        redentry.formColor = Color.parseColor("#d63535");
        redentry.label = "主升浪";
        LegendEntry greenentry = new LegendEntry();
        greenentry.formColor = Color.parseColor("#119d3e");
        greenentry.label = "主跌浪";
        linkedList.add(redentry);
        linkedList.add(greenentry);
        mlegend.setCustom(linkedList);
        mlegend.setFormSize(6f);// 字体
        mlegend.setTextColor(Color.GRAY);// 颜色
        ArrayList<Integer> colors = new ArrayList<>();
        //数据
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < periodDataLists.size(); i++) {
            entries.add(new Entry(i, (float) periodDataLists.get(i).get_lpf()));
            if (periodDataLists.get(i).get_segmentPoint() == 1)
                colors.add(Color.parseColor("#d63535"));
            else if (i != periodDataLists.size() - 1 && periodDataLists.get(i).get_segmentPoint() == 3 && periodDataLists.get(i + 1).get_segmentPoint() == 1)
                colors.add(Color.parseColor("#d63535"));
            else if (i != periodDataLists.size() - 1 && periodDataLists.get(i).get_segmentPoint() == 3 && periodDataLists.get(i + 1).get_segmentPoint() == 2)
                colors.add(Color.parseColor("#119d3e"));
            else colors.add(Color.parseColor("#119d3e"));
        }
        LineDataSet set = new LineDataSet(entries, "");
        set.setColors(colors);
        set.setDrawCircles(false);
        set.setDrawValues(true);
        set.setLineWidth(1.0f);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.BLACK);
        set.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                if (entry.getX() == periodDataLists.size() - 1) {
                    if (periodDataLists.get(periodDataLists.size() - 1).get_segmentPoint() == 1)
                        return "主升浪";
                    else if (periodDataLists.get(periodDataLists.size() - 1).get_segmentPoint() == 2)
                        return "主跌浪";
                    else
                        return "";
                } else
                    return "";
            }

            @Override
            public Bitmap getFormattedBitmap(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return null;

            }

            @Override
            public Bitmap getFormattedLine(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                Bitmap bmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.lines);
                if (periodDataLists.get((int) entry.getX()).get_segmentPoint() == 3)
                    return bmp;
                else
                    return null;
            }

        });

        LineData lineData = new LineData();
        lineData.addDataSet(set);
        lineChart.setData(lineData);   //设置数据
        lineChart.invalidate();
    }

    //云图
    private void setIchimokuChart(final List<IchimokuData> ichimokuDatas) {
        ichimokuchart.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
        ichimokuchart.setGridBackgroundColor(Color.YELLOW);
        ichimokuchart.setDrawGridBackground(true);
        ichimokuchart.setDrawBorders(false);
        ichimokuchart.getDescription().setEnabled(false);
        ichimokuchart.setPinchZoom(false);
        ichimokuchart.setTouchEnabled(false);  //是否可以触摸

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.MAGENTA);
        colors.add(Color.BLACK);
        colors.add(Color.RED);
        colors.add(Color.BLUE);

        Legend mlegend = ichimokuchart.getLegend();
        LinkedList<LegendEntry> linkedList = new LinkedList<LegendEntry>();

        LegendEntry SenkouA = new LegendEntry();
        SenkouA.formColor = colors.get(0);
        SenkouA.label = "先行上线";
        LegendEntry SenkouB = new LegendEntry();
        SenkouB.formColor = colors.get(1);
        SenkouB.label = "先行下线";
        LegendEntry close = new LegendEntry();
        close.formColor = colors.get(2);
        close.label = "收盘价";
        LegendEntry Tenkan = new LegendEntry();
        Tenkan.formColor = colors.get(3);
        Tenkan.label = "转折线";
        LegendEntry Kijun = new LegendEntry();
        Kijun.formColor = colors.get(4);
        Kijun.label = "基准线";

        linkedList.add(SenkouA);
        linkedList.add(SenkouB);
        linkedList.add(close);
        linkedList.add(Tenkan);
        linkedList.add(Kijun);

        mlegend.setCustom(linkedList);
        mlegend.setWordWrapEnabled(true);
        mlegend.setFormSize(6f);// 字体
        mlegend.setTextColor(Color.GRAY);// 颜色

        XAxis xAxis = ichimokuchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴的位置
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineColor(Color.parseColor("#f5cf90"));
        xAxis.setDrawLabels(true);
        xAxis.setLabelCount(2, true);     //X轴描述文字个数
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(10);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int) value == 0) {
                    return ichimokuDatas.get(0).get_date();
                } else if ((int) value == ichimokuDatas.size() - 1) {
                    return ichimokuDatas.get(ichimokuDatas.size() - 1).get_date();
                } else {
                    return "";
                }
            }
        });

        YAxis centerAxis = ichimokuchart.getAxisCenter();
        centerAxis.setEnabled(false);
        YAxis leftAxis = ichimokuchart.getAxisLeft();
        leftAxis.setTextColor(Color.GRAY);
        leftAxis.setTextSize(10);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisLineColor(Color.parseColor("#f5cf90"));
        leftAxis.setLabelCount(4, true);
        YAxis rightAxis = ichimokuchart.getAxisRight();
        rightAxis.setEnabled(true);
        rightAxis.setTextColor(Color.parseColor("#00f5cf90"));
        rightAxis.setTextSize(13);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisLineColor(Color.parseColor("#00f5cf90"));
        setIchimokuData(ichimokuDatas, colors);
        ichimokuchart.invalidate();
    }

    float a = 0;
    float b = 0;

    //设置云图数据
    void setIchimokuData(List<IchimokuData> ichimokuDatas, ArrayList<Integer> colors) {
        ArrayList<Entry> maxVals = new ArrayList<Entry>();
        ArrayList<Entry> minyVals = new ArrayList<Entry>();
        // 先行上线
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i = 0; i < ichimokuDatas.size(); i++) {
            if (Double.isNaN(ichimokuDatas.get(i).get_SenkouA())) {
                if (i < ichimokuDatas.size() - 1 && Double.isNaN(ichimokuDatas.get(i).get_SenkouA()) && !Double.isNaN(ichimokuDatas.get(i + 1).get_SenkouA())) {
                    a = i + 1;
                }

            }
            if (ichimokuDatas.get(i).get_SenkouA()<ichimokuDatas.get(i).get_SenkouB()){
                maxVals.add(new Entry(i, (float) ichimokuDatas.get(i).get_SenkouB()));
                minyVals.add(new Entry(i, (float) ichimokuDatas.get(i).get_SenkouA()));
            }else{
                maxVals.add(new Entry(i, (float) ichimokuDatas.get(i).get_SenkouA()));
                minyVals.add(new Entry(i, (float) ichimokuDatas.get(i).get_SenkouB()));
            }
            if(ichimokuDatas.get(i).get_SenkouA()!=Double.NaN){
                yVals1.add(new Entry(i, (float) ichimokuDatas.get(i).get_SenkouA()));
            }else{
                yVals1.add(new Entry(i, Float.NaN));
            }


        }
        // 先行下线
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        for (int i = 0; i < ichimokuDatas.size(); i++) {
            if (Double.isNaN(ichimokuDatas.get(i).get_SenkouB())) {

                if (i < ichimokuDatas.size() - 1 && Double.isNaN(ichimokuDatas.get(i).get_SenkouA()) && !Double.isNaN(ichimokuDatas.get(i + 1).get_SenkouB())) {
                    b = i + 1;
                }

            }
            yVals2.add(new Entry(i, (float) ichimokuDatas.get(i).get_SenkouB()));

        }
        //收盘价
        ArrayList<Entry> yVals3 = new ArrayList<>();
        for (int i = 0; i < ichimokuDatas.size(); i++) {
            yVals3.add(new Entry(i, (float) ichimokuDatas.get(i).get_close()));
        }
        //转折线
        ArrayList<Entry> yVals4 = new ArrayList<>();
        for (int i = 0; i < ichimokuDatas.size(); i++) {
            yVals4.add(new Entry(i, (float) ichimokuDatas.get(i).get_Tenkan()));
        }
        //基准线
        ArrayList<Entry> yVals5 = new ArrayList<>();
        for (int i = 0; i < ichimokuDatas.size(); i++) {
            yVals5.add(new Entry(i, (float) ichimokuDatas.get(i).get_Kijun()));
        }

        LineDataSet maxSet,minSet,set1, set2, set3, set4, set5;
        maxSet = new LineDataSet(maxVals, "");
        maxSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        maxSet.setColor(Color.TRANSPARENT);
        maxSet.setDrawCircles(false);
        maxSet.setLineWidth(1.0f);
        maxSet.setFillAlpha(255);
        maxSet.setDrawFilled(true);
        maxSet.setFillColor(ContextCompat.getColor(activity, R.color.white));
        maxSet.setDrawCircleHole(false);
        maxSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return ichimokuchart.getAxisLeft().getAxisMaximum();
            }

            @Override
            public float getFillStartIndex(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return a;
            }
        });
        minSet = new LineDataSet(minyVals, "");
        minSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        minSet.setColor(Color.TRANSPARENT);
        minSet.setDrawCircles(false);
        minSet.setLineWidth(2.0f);
        minSet.setDrawFilled(true);
        minSet.setFillAlpha(255);
        minSet.setFillColor(ContextCompat.getColor(activity, R.color.white));
        minSet.setDrawCircleHole(false);
        minSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return ichimokuchart.getAxisLeft().getAxisMinimum();
            }

            @Override
            public float getFillStartIndex(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return a;
            }
        });
        set1 = new LineDataSet(yVals1, "");
        set1.setColor(colors.get(0));
        set1.setDrawCircles(false);
        set1.setLineWidth(1.0f);
        set1.enableDashedLine(10, 0, 0);

        set2 = new LineDataSet(yVals2, "");
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(colors.get(1));
        set2.setDrawCircles(false);
        set2.setLineWidth(1.0f);
        set2.enableDashedLine(10, 0, 0);

        set3 = new LineDataSet(yVals3, "");
        set3.setAxisDependency(YAxis.AxisDependency.LEFT);
        set3.setColor(colors.get(2));
        set3.setDrawCircles(false);
        set3.setLineWidth(1.0f);
        set3.setDrawFilled(false);
        set3.enableDashedLine(10, 0, 0);


        set4 = new LineDataSet(yVals4, "");
        set4.setAxisDependency(YAxis.AxisDependency.LEFT);
        set4.setColor(colors.get(3));
        set4.setDrawCircles(false);
        set4.setLineWidth(1.0f);
        set4.setDrawFilled(false);
        set4.enableDashedLine(10, 0, 0);


        set5 = new LineDataSet(yVals5, "");
        set5.setAxisDependency(YAxis.AxisDependency.LEFT);
        set5.setColor(colors.get(4));
        set5.setDrawCircles(false);
        set5.setLineWidth(1.0f);
        set5.setDrawFilled(false);
        set5.enableDashedLine(10, 0, 0);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(maxSet);
        dataSets.add(minSet);
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);
        dataSets.add(set5);

        LineData data = new LineData(dataSets);
        data.setDrawValues(false);
        ichimokuchart.setData(data);
    }

    @Override
    public void onScrolledToBottom() {
        if (isFirst2Bottom) {
            presenter.getRelationStocks(stockCode);
        }
        isFirst2Bottom = false;
    }

    @Override
    public void onScrolledToTop() {

    }

    @Override
    public void onItemClick(int position) {
        RelatedStock relatedStock = relatedadapter.getItem(position);
        StockDetailsActivity.startActivity(activity, relatedStock.get_code(), relatedStock.get_name());
    }

}
