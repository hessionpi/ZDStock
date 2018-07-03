package com.rjzd.aistock.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.FivePosition;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.api.StockData;
import com.rjzd.aistock.api.StockTickData;
import com.rjzd.aistock.bean.StockBasic;
import com.rjzd.aistock.presenter.StockDetailPresenter;
import com.rjzd.aistock.ui.adapters.FragmentsViewPagerAdapter;
import com.rjzd.aistock.ui.fragment.BaseFragment;
import com.rjzd.aistock.ui.fragment.ExponentPredictionFragment;
import com.rjzd.aistock.ui.fragment.FallCrunchiesFragment;
import com.rjzd.aistock.ui.fragment.RiseCrunchiesFragment;
import com.rjzd.aistock.ui.views.BackgroundDarkPopupWindow;
import com.rjzd.aistock.ui.views.ForbiddenScrollPager;
import com.rjzd.aistock.ui.views.StickyNavLayout;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.fragment.DayKLineFragment;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.fragment.MonthKLineFragment;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.fragment.RealTimeFragment;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.fragment.WeekKLineFragment;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.MathDataUtil;
import com.rjzd.commonlib.utils.StockTimeUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.Bind;
import butterknife.ButterKnife;
import static com.rjzd.aistock.api.StatusCode.WAITING_FOR_PRICE;


public class ExponentDetailsActivity extends ShareActivity implements View.OnClickListener, IFillDataView {

    @Bind(R.id.lv_left)
    ImageView lvLeft;
    @Bind(R.id.tv_title_name)
    TextView tvTitleName;
    @Bind(R.id.tv_title_code)
    TextView tvTitleCode;
    @Bind(R.id.current_price)
    TextView currentPrice;
    @Bind(R.id.floating_prices)
    TextView floatingPrices;
    @Bind(R.id.floating_range)
    TextView floatingRange;
    @Bind(R.id.dateils_prec)
    TextView dateilsPrec;

    @Bind(R.id.dateils_volume_transaction)
    TextView dateilsVolumeTransaction;

    @Bind(R.id.tv_realmtime)
    TextView mRealtime;
    @Bind(R.id.k_day)
    TextView mDayK;
    @Bind(R.id.k_week)
    TextView mWeekK;
    @Bind(R.id.k_month)
    TextView mMonthK;
    @Bind(R.id.exponent_dateils_count)
    TextView exponentDateilsCount;

    @Bind(R.id.prediction)
    TextView mPredition;
    @Bind(R.id.tv_riseCrunchies)
    TextView tvRiseCrunchies;
    @Bind(R.id.tv_fallCrunchies)
    TextView tvFallCrunchies;
    @Bind(R.id.prediction_idc)
    View mPreditionIdc;
    @Bind(R.id.riseCrunchies_idc)
    View riseCrunchiesIdc;
    @Bind(R.id.fallCrunchies_idc)
    View fallCrunchiesIdc;

    @Bind(R.id.id_stickynavlayout_viewpager)
    ForbiddenScrollPager viewPager;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.ll_share)
    LinearLayout mShareView;
    @Bind(R.id.details_opening_price)
    TextView detailsOpeningPrice;
    @Bind(R.id.realmtime_idc)
    View realmtimeIdc;
    @Bind(R.id.day_idc)
    View dayIdc;
    @Bind(R.id.week_idc)
    View weekIdc;
    @Bind(R.id.month_idc)
    View monthIdc;
    @Bind(R.id.rl_details_title)
    RelativeLayout rlDetailsTitle;
    @Bind(R.id.exponent_abstract)
    LinearLayout ExponentAbstract;

/*    @Bind(R.id.cb_expand)
    CheckBox mExpand;
    @Bind(R.id.stubView)
    ViewStub mStubView;*/

    @Bind(R.id.stickynav)
    StickyNavLayout stickynav;
    @Bind(R.id.id_stickynavlayout_topview)
    LinearLayout topview;
    @Bind(R.id.ll_status)
    LinearLayout llStatus;
    @Bind(R.id.ll_price)
    LinearLayout llPrice;

    @Bind(R.id.status)
    TextView status;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.title_price)
    TextView titlePrice;
    @Bind(R.id.title_floatingPrices)
    TextView titleFloatingPrices;
    @Bind(R.id.title_range)
    TextView titleRange;
    @Bind(R.id.tv_stock_high)
    TextView tvStockHigh;
    @Bind(R.id.tv_stock_low)
    TextView tvStockLow;

    private StockBasic stock;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    //  double prevClose;

    /**
     * Fragment类型
     */
    private static final int REALTIME = 10;
    private static final int K_DAY = 11;
    private static final int K_WEEK = 12;
    private static final int K_MONTH = 13;

    private static final int PREDICTION = 0;
    private static final int RISECRUNCHIES = 1;
    private static final int FALLCRUNCHIES = 2;


    private RealTimeFragment realtimeFragment;
    private DayKLineFragment dayKFragment;
    private WeekKLineFragment weekKFragment;
    private MonthKLineFragment monthKFragment;
    private Timer timer;

    private TimerTask refreshStocksTask;
    public static final String TAG = "StockDetailsActivity";
    public List<FivePosition> fivePositions;
    StockDetailPresenter presenter;
    private Bundle bundle;
    final static int SUCCESS = 1;
    int statusCode;
    double prevClose;
    List<StockTickData> stocks;
    BackgroundDarkPopupWindow pw;
    boolean isTrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exponent_details);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        presenter = new StockDetailPresenter(this);
        presenter.isTradeDate();
        initView();
        initData();
    }

    void initView() {
        stock = (StockBasic) getIntent().getSerializableExtra("stock");
        if (null == stock) {
            return;
        }
        tvTitleName.setText(stock.getName());
        if (MathDataUtil.isInteger(stock.getCode().trim())) {
            tvTitleCode.setText("(" + stock.getCode() + ")");
        } else {
            tvTitleCode.setVisibility(View.GONE);
        }
        lvLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        mShareView.setOnClickListener(this);
        ExponentAbstract.setOnClickListener(this);

        bundle = new Bundle();
        bundle.putString("stock_code", stock.getCode());
        initKView();
        initF10View();

        stickynav.setOnStickStateChangeListener(new StickyNavLayout.onStickStateChangeListener() {
            @Override
            public void isStick(boolean isStick) {

            }

            @Override
            public void scrollPercent(float percent) {
                float mPer = (float) ((ExponentAbstract.getHeight() * 1.0) / (topview.getHeight() * 1.0));
                if (percent < mPer) {
                    llStatus.setVisibility(View.VISIBLE);
                    llPrice.setVisibility(View.GONE);
                } else {
                    llStatus.setVisibility(View.GONE);
                    llPrice.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 初始化K线相关的Fragment
     */
    private void initKView() {
        setKViewTab(REALTIME);
        mRealtime.setOnClickListener(this);
        mDayK.setOnClickListener(this);
        mWeekK.setOnClickListener(this);
        mMonthK.setOnClickListener(this);
    }

    /**
     * 初始化F10相关的fragment
     */
    private void initF10View() {
        mPredition.setOnClickListener(this);
        tvRiseCrunchies.setOnClickListener(this);
        tvFallCrunchies.setOnClickListener(this);

        ArrayList<BaseFragment> fragments = new ArrayList<>();
        RiseCrunchiesFragment riseCrunchiesFragment = new RiseCrunchiesFragment();
        riseCrunchiesFragment.setArguments(bundle);
        FallCrunchiesFragment fallCrunchiesFragment = new FallCrunchiesFragment();
        fallCrunchiesFragment.setArguments(bundle);
        ExponentPredictionFragment predFragment = new ExponentPredictionFragment();
        predFragment.setArguments(bundle);

        fragments.add(predFragment);
        fragments.add(riseCrunchiesFragment);
        fragments.add(fallCrunchiesFragment);
        FragmentsViewPagerAdapter adapter = new FragmentsViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
    }

    void initData() {
        currentPrice.setText("——");//现价
        floatingPrices.setText("——");//涨跌价
        floatingRange.setText("——");//涨跌幅
        dateilsPrec.setText("——");//昨收
        tvStockHigh.setText("——");//最高
        detailsOpeningPrice.setText("——");//今开
        tvStockLow.setText("——");//最低
        dateilsVolumeTransaction.setText("——");//成交额
        exponentDateilsCount.setText("——");//成交量
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lv_left:
                finish();
                break;
            case R.id.iv_right:
                SearchActivity.startActivity(this);
                break;
            case R.id.ll_share:       // 分享
                String url = Constants.BASE_SHARE_ADDRESS + stock.getCode();
                String title = "来自壹眼富";
                openShareBoard(url,title,null);
                break;
            case R.id.exponent_abstract://弹出
                showPopup();
                break;
            case R.id.prediction: // 智能预测
                switchTab(PREDICTION);
                break;
            case R.id.tv_riseCrunchies: // 涨幅榜
                switchTab(RISECRUNCHIES);
                break;
            case R.id.tv_fallCrunchies: // 跌幅榜
                switchTab(FALLCRUNCHIES);
                break;
            case R.id.tv_realmtime:  // 分时
                setKViewTab(REALTIME);
                break;

            case R.id.k_day:      // 日K
                setKViewTab(K_DAY);
                break;

            case R.id.k_week:    // 周K
                setKViewTab(K_WEEK);
                break;

            case R.id.k_month:  // 月K
                setKViewTab(K_MONTH);
                break;
        }
    }

    private void showPopup() {
        View detailView = getLayoutInflater().inflate(R.layout.layout_exponent_detail_extend, null);
        TextView expontentRise = ButterKnife.findById(detailView, R.id.expontent_dateils_rise);
        TextView expontentFall = ButterKnife.findById(detailView, R.id.expontent_dateils_fall);
        if (statusCode == StatusCode.WAITING_FOR_PRICE.getValue()) {
            expontentRise.setText("——");//上涨
            expontentFall.setText("——");//下跌
        } else {
            expontentRise.setText(stocks.get(0).get_riseNum() + "");//上涨
            expontentFall.setText(stocks.get(0).get_fallNum() + "");//下跌
        }
        pw = new BackgroundDarkPopupWindow(detailView, LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        pw.setContentView(detailView);
        pw.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        pw.setDarkStyle(-1);
        pw.setDarkColor(Color.parseColor("#a0000000"));
        pw.resetDarkPosition();
        pw.darkBelow(ExponentAbstract);
        pw.showAsDropDown(ExponentAbstract);
        pw.showAsDropDown(ExponentAbstract);
        pw.setTouchable(true); // 设置popupwindow可点击
        pw.setOutsideTouchable(true); // 设置popupwindow外部可点击
        pw.setFocusable(true); // 获取焦点
        pw.update();
    }

    /**
     * 切换K线图曲线类型
     */
    private void setKViewTab(int index) {
        fragmentTransaction = fragmentManager.beginTransaction();
        clearKStatus(); // 清空, 重置选项, 隐藏所有Fragment
        hideKFragments();
        switch (index) {
            case REALTIME:
                mRealtime.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                realmtimeIdc.setVisibility(View.VISIBLE);
                if (null == realtimeFragment) {
                    realtimeFragment = new RealTimeFragment();
                    realtimeFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.kline_container, realtimeFragment);
                } else {
                    // 如果不为空，则直接将它显示出来
                    fragmentTransaction.show(realtimeFragment);
                }

                break;

            case K_DAY:
                mDayK.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                dayIdc.setVisibility(View.VISIBLE);
                if (null == dayKFragment) {
                    dayKFragment = new DayKLineFragment();
                    dayKFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.kline_container, dayKFragment);
                } else {
                    fragmentTransaction.show(dayKFragment);
                }

                break;

            case K_WEEK:
                mWeekK.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                weekIdc.setVisibility(View.VISIBLE);
                if (null == weekKFragment) {
                    weekKFragment = new WeekKLineFragment();
                    weekKFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.kline_container, weekKFragment);
                } else {
                    fragmentTransaction.show(weekKFragment);
                }
                break;

            case K_MONTH:
                mMonthK.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                monthIdc.setVisibility(View.VISIBLE);
                if (null == monthKFragment) {
                    monthKFragment = new MonthKLineFragment();
                    monthKFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.kline_container, monthKFragment);
                } else {
                    fragmentTransaction.show(monthKFragment);
                }
                break;
        }
        fragmentTransaction.commit();   // 提交
    }

    public void switchTab(int index) {
        viewPager.setCurrentItem(index);
        clearF10Chioce(); // 清空, 重置选项, 隐藏所有Fragment
        switch (index) {
            case PREDICTION:
                mPredition.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                mPreditionIdc.setVisibility(View.VISIBLE);
                break;
            case RISECRUNCHIES:
                tvRiseCrunchies.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                riseCrunchiesIdc.setVisibility(View.VISIBLE);
                break;
            case FALLCRUNCHIES:
                tvFallCrunchies.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                fallCrunchiesIdc.setVisibility(View.VISIBLE);
                break;
            default:

                break;
        }
    }

    private void clearF10Chioce() {
        mPredition.setTextColor(ContextCompat.getColor(this, R.color.cl_333333));
        tvRiseCrunchies.setTextColor(ContextCompat.getColor(this, R.color.cl_333333));
        tvFallCrunchies.setTextColor(ContextCompat.getColor(this, R.color.cl_333333));
        mPreditionIdc.setVisibility(View.GONE);
        riseCrunchiesIdc.setVisibility(View.GONE);
        fallCrunchiesIdc.setVisibility(View.GONE);
    }

    private void clearKStatus() {
        mRealtime.setTextColor(ContextCompat.getColor(this, R.color.cl_333333));
        mDayK.setTextColor(ContextCompat.getColor(this, R.color.cl_333333));
        mWeekK.setTextColor(ContextCompat.getColor(this, R.color.cl_333333));
        mMonthK.setTextColor(ContextCompat.getColor(this, R.color.cl_333333));
        realmtimeIdc.setVisibility(View.GONE);
        dayIdc.setVisibility(View.GONE);
        weekIdc.setVisibility(View.GONE);
        monthIdc.setVisibility(View.GONE);
    }

    /**
     * 隐藏K线图  相关的所有 Fragment
     */
    private void hideKFragments() {
        if (realtimeFragment != null) {
            fragmentTransaction.hide(realtimeFragment);
        }
        if (dayKFragment != null) {
            fragmentTransaction.hide(dayKFragment);
        }
        if (weekKFragment != null) {
            fragmentTransaction.hide(weekKFragment);
        }
        if (monthKFragment != null) {
            fragmentTransaction.hide(monthKFragment);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    StockData data = (StockData) msg.obj;
                    stocks = data.get_data();
                    prevClose = stocks.get(0).get_prevClose(); //昨收
                    double price = stocks.get(0).get_latestPrice();//现价
                    double todayPrice = stocks.get(0).get_open(); //今开
                    double highPrice = stocks.get(0).get_high();
                    double lowPrice = stocks.get(0).get_low();
                    Constants.last = prevClose;
                    Date now = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    time.setText(dateFormat.format(now));

                    if (!isTrade) {
                        status.setText("已收盘");
                    } else {
                        Long time = now.getTime() / 1000;
                        if (time < StockTimeUtils.getnineMillis()) {
                            status.setText("已收盘");
                        } else if (time > StockTimeUtils.getnineMillis() && time < StockTimeUtils.getelevenMillis()) {
                            status.setText("交易中");
                        } else if (time > StockTimeUtils.getelevenMillis() && time < StockTimeUtils.getoneMillis()) {
                            status.setText("午间休市");
                        } else if (time > StockTimeUtils.getoneMillis() && time < StockTimeUtils.getThreeMillis()) {
                            status.setText("交易中");
                        } else {
                            status.setText("已收盘");
                        }
                    }
                    titlePrice.setText(String.format("%.2f", price));//现价
                    if (todayPrice > 0) {
                        titleFloatingPrices.setText(String.format("%.2f", stocks.get(0).get_latestPrice() - stocks.get(0).get_prevClose()));//涨跌价
                        floatingPrices.setText(String.format("%.2f", stocks.get(0).get_latestPrice() - stocks.get(0).get_prevClose()));//涨跌价
                    } else {
                        titleFloatingPrices.setText("0.00");//涨跌价
                        floatingPrices.setText("0.00");//涨跌价
                    }
                    titleRange.setText(String.format("%.2f", stocks.get(0).get_range() * 100) + "%");//涨跌幅


                    currentPrice.setText(String.format("%.2f", price));//现价

                    floatingRange.setText(String.format("%.2f", stocks.get(0).get_range() * 100) + "%");//涨跌幅
                    dateilsPrec.setText(String.format("%.2f", prevClose));//昨收
                    tvStockHigh.setText(String.format("%.2f", highPrice));//最高
                    detailsOpeningPrice.setText(String.format("%.2f", todayPrice));//今开
                    tvStockLow.setText(String.format("%.2f", lowPrice));//最低

                    if (stocks.get(0).get_amount() / 100000000 == 0) {
                        dateilsVolumeTransaction.setText(String.format("%.2f", stocks.get(0).get_amount() / 10000) + "万");//成交额
                    } else {
                        dateilsVolumeTransaction.setText(String.format("%.2f", stocks.get(0).get_amount() / 100000000) + "亿");//成交额
                    }
                    if (stocks.get(0).get_volume() / 100000000 == 0) {
                        exponentDateilsCount.setText(String.format("%.2f", stocks.get(0).get_volume() / 10000.0) + "万");//成交量
                    } else {
                        exponentDateilsCount.setText(String.format("%.2f", stocks.get(0).get_volume() / 100000000.0) + "亿");//成交量
                    }

                    if (prevClose < price) {
                        currentPrice.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_d63535));
                        floatingPrices.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_d63535));
                        floatingRange.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_d63535));
                    } else if (prevClose > price) {
                        currentPrice.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_119d3e));
                        floatingPrices.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_119d3e));
                        floatingRange.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_119d3e));
                    } else {
                        currentPrice.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_333333));
                        floatingPrices.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_333333));
                        floatingRange.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_333333));
                    }
                    if (todayPrice < prevClose) {
                        detailsOpeningPrice.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_119d3e));
                    } else if (todayPrice >= prevClose) {
                        detailsOpeningPrice.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_d63535));
                    }
                    if (highPrice >= prevClose) {
                        tvStockHigh.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_d63535));
                    } else if (highPrice < prevClose) {
                        tvStockHigh.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_119d3e));
                    }
                    if (lowPrice >= prevClose) {
                        tvStockLow.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_d63535));
                    } else if (lowPrice < prevClose) {
                        tvStockLow.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_119d3e));
                    }
                    fivePositions = stocks.get(0).get_fivePositionList();

                    if (data.get_status().getValue() == WAITING_FOR_PRICE.getValue()) {
                        currentPrice.setText("——");//现价
                        floatingPrices.setText("——");//涨跌价
                        floatingRange.setText("——");//涨跌幅
                        detailsOpeningPrice.setText("——");//今开
                        tvStockHigh.setText("——");//最高
                        tvStockLow.setText("——");//最低
                        exponentDateilsCount.setText("——");//成交量
                        dateilsVolumeTransaction.setText("——");//成交额
                        currentPrice.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_333333));
                        floatingPrices.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_333333));
                        floatingRange.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_333333));
                        tvStockHigh.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_333333));
                        tvStockLow.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_333333));
                        detailsOpeningPrice.setTextColor(ContextCompat.getColor(ExponentDetailsActivity.this, R.color.cl_333333));
                    }
                    if (data.get_status().getValue() == StatusCode.WAITING_FOR_PRICE.getValue()) {//等待定价
                        status.setText("等待定价");
                    } else if (data.get_status().getValue() == StatusCode.WAITING_FOR_OPEN.getValue()) {//等待开盘
                        status.setText("等待开盘");
                    }
                    break;
            }
        }
    };

    /**
     * 启动计时器
     */
    private void startTimer() {
        timer = new Timer();
        //  刷新定时器
        refreshStocksTask = new TimerTask() {
            @Override
            public void run() {
                List<String> list = new ArrayList<>();
                list.add(stock.getCode());
                // 刷新
                presenter.refreshDataDetail(list);
            }
        };

        //  启动定时器
        timer.schedule(refreshStocksTask, 0, Constants.PERIOD_REFRESH);
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }

        if (refreshStocksTask != null) {
            refreshStocksTask.cancel();
        }
    }

    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data,dsTag);
        switch (dsTag) {
            case Constants.DS_TAG_IS_TRADE_DATE:
                if (data instanceof Boolean) {
                    isTrade = (boolean) data;
                    startTimer();
                }
                break;
            case Constants.DS_TAG_REFRESH_HANDICAP:

                if (data instanceof StockData) {

                    StockData stockData = (StockData) data;
                    if (stockData.get_status().getValue() != StatusCode.ERROR.getValue() || stockData.get_status().getValue() != StatusCode.NULL.getValue()) {
                        if (null != stockData) {
                            if (null != stockData.get_data()) {
                                List<StockTickData> stocks = stockData.get_data();
                                statusCode = stockData.get_status().getValue();
                                if (null != stocks && !stocks.isEmpty()) {
                                    Message msg = Message.obtain();
                                    msg.what = SUCCESS;
                                    msg.obj = stockData;
                                    handler.sendMessage(msg);
                                }
                            }

                            if (stockData.get_status().getValue() == StatusCode.NON_TRADE_TIME.getValue()) {
                                cancelTimer();
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void showFailedView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
        cancelTimer();
    }
}
