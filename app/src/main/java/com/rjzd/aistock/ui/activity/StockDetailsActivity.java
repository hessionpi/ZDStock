package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
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
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.CodeType;
import com.rjzd.aistock.api.FivePosition;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.api.StockData;
import com.rjzd.aistock.api.StockTickData;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.StockDetailPresenter;
import com.rjzd.aistock.ui.adapters.FragmentsViewPagerAdapter;
import com.rjzd.aistock.ui.fragment.BaseFragment;
import com.rjzd.aistock.ui.fragment.FinanceFragment;
import com.rjzd.aistock.ui.fragment.FundFragment;
import com.rjzd.aistock.ui.fragment.PredictionFragment;
import com.rjzd.aistock.ui.fragment.ProfileFragment;
import com.rjzd.aistock.ui.views.BackgroundDarkPopupWindow;
import com.rjzd.aistock.ui.views.ForbiddenScrollPager;
import com.rjzd.aistock.ui.views.StickyNavLayout;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.fragment.DayKLineFragment;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.fragment.MonthKLineFragment;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.fragment.RealTimeFragment;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.fragment.WeekKLineFragment;
import com.rjzd.aistock.utils.FontUtil;
import com.rjzd.aistock.utils.view.DialogManager;
import com.rjzd.commonlib.utils.StockTimeUtils;
import com.rjzd.commonlib.utils.ToastUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.Bind;
import butterknife.ButterKnife;
import static com.rjzd.aistock.Constants.DS_TAG_REFRESH_HANDICAP;
import static com.rjzd.aistock.api.StatusCode.WAITING_FOR_PRICE;

public class StockDetailsActivity extends ShareActivity implements View.OnClickListener{

    @Bind(R.id.tv_title_name)
    TextView tvTitleName;
    @Bind(R.id.tv_title_code)
    TextView tvTitleCode;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_share)
    TextView mShareView;
    @Bind(R.id.current_price)
    TextView currentPrice;
    @Bind(R.id.floating_prices)
    TextView floatingPrices;
    @Bind(R.id.floating_range)
    TextView floatingRange;

    @Bind(R.id.tv_realmtime)
    TextView mRealtime;
    @Bind(R.id.k_day)
    TextView mDayK;
    @Bind(R.id.k_week)
    TextView mWeekK;
    @Bind(R.id.k_month)
    TextView mMonthK;

    @Bind(R.id.realmtime_idc)
    View realmtimeIdc;
    @Bind(R.id.day_idc)
    View dayIdc;
    @Bind(R.id.week_idc)
    View weekIdc;
    @Bind(R.id.month_idc)
    View monthIdc;

    @Bind(R.id.prediction)
    TextView mPredition;
    @Bind(R.id.finance)
    TextView mFinance;
    @Bind(R.id.fund)
    TextView mFund;
    @Bind(R.id.profile)
    TextView mProfile;

    @Bind(R.id.prediction_idc)
    View mPreditionIdc;
    @Bind(R.id.funds_idc)
    View mFundsIdc;
    @Bind(R.id.finance_idc)
    View mFinanceIdc;
    @Bind(R.id.profile_idc)
    View mProfileIdc;

    @Bind(R.id.lv_left)
    ImageView lvLeft;
    @Bind(R.id.add_or_delete)
    TextView mAddOrDelete;
    @Bind(R.id.details_opening_price)
    TextView detailsOpeningPrice;
    /*   @Bind(R.id.dateils_volume_transaction)
       TextView dateilsVolumeTransaction;*/
    @Bind(R.id.dateils_prec)
    TextView dateilsPrec;
    @Bind(R.id.dateils_count)
    TextView dateilsCount;

    @Bind(R.id.id_stickynavlayout_viewpager)
    ForbiddenScrollPager viewPager;
    /*@Bind(R.id.rl_details_title)
    RelativeLayout rlDetailsTitle;*/
    @Bind(R.id.stock_abstract)
    LinearLayout stockAbstract;
    /* @Bind(R.id.cb_expand)
     CheckBox mExpand;*/
    @Bind(R.id.stubView)
    ViewStub mStubView;
    @Bind(R.id.stickynav)
    StickyNavLayout stickynav;
    @Bind(R.id.ll_status)
    LinearLayout llStatus;
    @Bind(R.id.ll_price)
    LinearLayout llPrice;
    @Bind(R.id.id_stickynavlayout_topview)
    LinearLayout topview;
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
    @Bind(R.id.tv_stock_pe)
    TextView tvStockPe;


    private int userId;
    private String stockName;
    private String code;
    private StockDetailPresenter presenter;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    double prevClose;
    List<StockTickData> stocks;
    boolean isTrade;
    // PredictionFragment.StockReceiver stockReceiver;
    /**
     * Fragment类型
     */
    private static final int REALTIME = 10;
    private static final int K_DAY = 11;
    private static final int K_WEEK = 12;
    private static final int K_MONTH = 13;

    private static final int PREDICTION = 0;
    private static final int FUND = 1;
    private static final int FINANCE = 2;
    private static final int PROFILE = 3;

    private RealTimeFragment realtimeFragment;
    private DayKLineFragment dayKFragment;
    private WeekKLineFragment weekKFragment;
    private MonthKLineFragment monthKFragment;

    private Timer timer;
    private TimerTask refreshStocksTask;
    public static final String TAG = "StockDetailsActivity";
    public List<FivePosition> fivePositions;

    private Bundle bundle;
    final static int SUCCESS = 1;
    // private LinearLayout mStubLayout;
    BackgroundDarkPopupWindow pw;

    public static void startActivity(Context context, String code, String name) {
        Intent intent = new Intent(context, StockDetailsActivity.class);
        intent.putExtra("stock_code", code);
        intent.putExtra("stock_name", name);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);
        ButterKnife.bind(this);
        userId = UserInfoCenter.getInstance().getUserId();
        fragmentManager = getSupportFragmentManager();
        presenter = new StockDetailPresenter(this);
        presenter.isTradeDate();
        parseIntent();
        initView();
        initData();
    }

    private void parseIntent() {
        code = getIntent().getStringExtra("stock_code");
        stockName = getIntent().getStringExtra("stock_name");
    }

    void initView() {
        tvTitleName.setText(stockName);
        tvTitleCode.setText("(" + code + ")");

        boolean isExists = presenter.isZixuan(code);
        setZixuanBtn(isExists);
        initExpandView();

        mAddOrDelete.setOnClickListener(this);
        lvLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        mShareView.setOnClickListener(this);
        stockAbstract.setOnClickListener(this);

        bundle = new Bundle();
        bundle.putString("stock_code", code);
        bundle.putString("stock_name", stockName);
        bundle.putInt("code_type_value", CodeType.STOCK.getValue());
        initKView();
        initF10View();
        stickynav.setOnStickStateChangeListener(new StickyNavLayout.onStickStateChangeListener() {
            @Override
            public void isStick(boolean isStick) {

            }

            @Override
            public void scrollPercent(float percent) {
                float mPer = (float) ((stockAbstract.getHeight() * 1.0) / (topview.getHeight() * 1.0));
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

    public void initExpandView() {
        // mStubLayout = (LinearLayout) mStubView.inflate();
        // mStubLayout.setVisibility(View.GONE);
       /* mExpand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 展开View
                    mStubLayout.setVisibility(View.VISIBLE);
                } else {
                    mStubLayout.setVisibility(View.GONE);
                }
            }
        });*/
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
        mFinance.setOnClickListener(this);
        mFund.setOnClickListener(this);
        mProfile.setOnClickListener(this);

        ArrayList<BaseFragment> fragments = new ArrayList<>();
        FundFragment fundsFragment = new FundFragment();
        fundsFragment.setArguments(bundle);
        FinanceFragment financeFragment = new FinanceFragment();
        financeFragment.setArguments(bundle);
        ProfileFragment pFragment = new ProfileFragment();
        pFragment.setArguments(bundle);
        PredictionFragment predFragment = new PredictionFragment();
        predFragment.setArguments(bundle);

        fragments.add(predFragment);
        fragments.add(fundsFragment);
        fragments.add(financeFragment);
        fragments.add(pFragment);

        FragmentsViewPagerAdapter adapter = new FragmentsViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        //registerBoradcastReceiver(predFragment);
    }

    void initData() {
        currentPrice.setText("——");//现价
        floatingPrices.setText("——");//涨跌价
        floatingRange.setText("——");//涨跌幅
        dateilsPrec.setText("——");//昨收
        dateilsCount.setText("——");//换手率
        detailsOpeningPrice.setText("——");//今开
        tvStockHigh.setText("——");//最高
        tvStockLow.setText("——");//最低
        tvStockPe.setText("——");//市盈率
        //startTimer();
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
            case R.id.tv_share:       // 分享
                String url = Constants.BASE_SHARE_ADDRESS + code;
                String title = stockName + "(" + code + ")" + "股票AI预测涨跌幅新鲜出炉!-壹眼富_免费AI股票助手";
                openShareBoard(url,title,null);
                break;
            case R.id.add_or_delete:
                boolean isPortfolio = presenter.isZixuan(code);
                if (!isPortfolio) {
                    // 添加自选
                    presenter.addStock(code, stockName);
                    if (userId > 0) {
                        presenter.addPortfolio2Server(userId, code);
                    }
                    setZixuanBtn(true);
                    ToastUtils.show(StockDetailsActivity.this, "添加成功", Toast.LENGTH_LONG);
                } else {
                    // 弹框删除自选
                    DialogManager.showSelectDialog(StockDetailsActivity.this, R.string.delete_zixuan_warning,
                            R.string.delete, R.string.cancel, false, new DialogManager.DialogListener() {
                                @Override
                                public void onNegative() {

                                }

                                @Override
                                public void onPositive() {
                                    presenter.deleteStock(code);
                                    if (userId > 0) {
                                        presenter.deletePortfolioFromServer(userId, code);
                                    }
                                    setZixuanBtn(false);
                                    ToastUtils.show(StockDetailsActivity.this, "删除成功", Toast.LENGTH_LONG);
                                }
                            });
                }
                break;

            case R.id.stock_abstract://弹出
                showPopup();
                break;

            case R.id.prediction: // 智能预测
                switchTab(PREDICTION);
                break;

            case R.id.finance:    // 财务
                switchTab(FINANCE);
                break;

            case R.id.fund:    // 资金
                switchTab(FUND);
                break;
            case R.id.profile:    // 概况
                switchTab(PROFILE);
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

    int statusCode;

    private void showPopup() {
        View detailView = getLayoutInflater().inflate(R.layout.layout_stock_detail_extend, null);
        TextView volume = ButterKnife.findById(detailView, R.id.dateils_volume_transaction);//成交额
        TextView circulate = ButterKnife.findById(detailView, R.id.dateils_circulate);//流通股
        TextView total = ButterKnife.findById(detailView, R.id.dateils_total);//总股本
        TextView mktcap = ButterKnife.findById(detailView, R.id.tv_mktcap);//总市值
        TextView currentvalue = ButterKnife.findById(detailView, R.id.tv_currentvalue);//流通值
        if (statusCode == StatusCode.WAITING_FOR_PRICE.getValue()) {
            volume.setText("——");//成交额
        } else {
            if (null == stocks) {
                return;
            }

            if (stocks.get(0).get_amount() / 100000000 == 0) {
                volume.setText(String.format("%.2f", stocks.get(0).get_amount() / 10000) + "万");//成交额
            } else {
                volume.setText(String.format("%.2f", stocks.get(0).get_amount() / 100000000) + "亿");//成交额
            }
        }
        mktcap.setText(String.format("%.2f", stocks.get(0).get_mktCap()) + "亿");
        currentvalue.setText(String.format("%.2f", stocks.get(0).get_nmc()) + "亿");
        if (stocks.get(0).is_set_totalStockNum()) {
            total.setText(String.format("%.2f", stocks.get(0).get_totalStockNum()) + "亿");
        } else {
            total.setText("0亿");
        }
        if (stocks.get(0).is_set_tradableStockNum()) {
            circulate.setText(String.format("%.2f", stocks.get(0).get_tradableStockNum()) + "亿");
        } else {
            circulate.setText("0亿");
        }


        pw = new BackgroundDarkPopupWindow(detailView, LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        pw.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        pw.setDarkStyle(-1);
        pw.setDarkColor(Color.parseColor("#a0000000"));
        pw.resetDarkPosition();
        pw.darkBelow(stockAbstract);

        // pw.showAsDropDown(stockAbstract, stockAbstract.getRight() / 2, 0);
        pw.showAsDropDown(stockAbstract);
        pw.setTouchable(true); // 设置popupwindow可点击
        pw.setOutsideTouchable(true); // 设置popupwindow外部可点击
        pw.setFocusable(true); // 获取焦点
        //设置popupWindow消失时的监听
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        pw.update();
    }



    public void setZixuanBtn(boolean isZixuan) {
        if (isZixuan) {
            mAddOrDelete.setText(R.string.delete_zixuan);
            mAddOrDelete.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_888888));
            mAddOrDelete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.delete_zixuan, 0, 0, 0);
        } else if (mAddOrDelete.getText().toString().equals(getString(R.string.delete_zixuan))) {
            mAddOrDelete.setText(R.string.add_zixuan);
            mAddOrDelete.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_d63535));
            mAddOrDelete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_zixuan_icon, 0, 0, 0);
        }
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
                // rtLine.setVisibility(View.VISIBLE);
                if (null == realtimeFragment) {
                    realtimeFragment = new RealTimeFragment();
                    bundle.putBoolean("is_trade_date", true);
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

                // mDayK.setBackgroundResource(R.drawable.rect_press);
                // dayKView.setVisibility(View.VISIBLE);
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

                // mWeekK.setBackgroundResource(R.drawable.rect_press);
                // weekKLine.setVisibility(View.VISIBLE);
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
                // mMonthK.setBackgroundResource(R.drawable.rect_press);
                //  monthKLine.setVisibility(View.VISIBLE);
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

            case FUND:
                mFund.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                mFundsIdc.setVisibility(View.VISIBLE);
                break;

            case FINANCE:
                mFinance.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                mFinanceIdc.setVisibility(View.VISIBLE);
                break;

            case PROFILE:
                mProfile.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                mProfileIdc.setVisibility(View.VISIBLE);
                break;

            default:

                break;
        }
    }

    private void clearF10Chioce() {
        mPredition.setTextColor(ContextCompat.getColor(this, R.color.cl_333333));
        mFinance.setTextColor(ContextCompat.getColor(this, R.color.cl_333333));
        mFund.setTextColor(ContextCompat.getColor(this, R.color.cl_333333));
        mProfile.setTextColor(ContextCompat.getColor(this, R.color.cl_333333));

        mPreditionIdc.setVisibility(View.GONE);
        mFundsIdc.setVisibility(View.GONE);
        mFinanceIdc.setVisibility(View.GONE);
        mProfileIdc.setVisibility(View.GONE);
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
                    setStockBasicData(data);
                    break;

            }
        }
    };

    private void setStockBasicData(StockData data) {

        stocks = data.get_data();
        prevClose = stocks.get(0).get_prevClose(); //昨收
        double price = stocks.get(0).get_latestPrice();//现价
        double open = stocks.get(0).get_open(); // 今开
        double highPrice = stocks.get(0).get_high();
        double lowPrice = stocks.get(0).get_low();
        double pe = stocks.get(0).get_per();

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time.setText(dateFormat.format(now));
        Constants.last = prevClose;
        if (open > 0) {
            if (!isTrade) {
                status.setText("已收盘");
            } else {
                Long time = now.getTime() / 1000;
                if (time < StockTimeUtils.getnineMillis()) {
                    status.setText("已收盘");
                } else if (time >= StockTimeUtils.getnineMillis() && time < StockTimeUtils.getelevenMillis()) {
                    status.setText("交易中");
                } else if (time >= StockTimeUtils.getelevenMillis() && time < StockTimeUtils.getoneMillis()) {
                    status.setText("午间休市");
                } else if (time >= StockTimeUtils.getoneMillis() && time < StockTimeUtils.getThreeMillis()) {
                    status.setText("交易中");
                } else {
                    status.setText("已收盘");
                }
            }
            titlePrice.setText(String.format("%.2f", price));//现价
            titleFloatingPrices.setText(String.format("%.2f", stocks.get(0).get_latestPrice() - stocks.get(0).get_prevClose()));//涨跌价
            titleRange.setText(String.format("%.2f", stocks.get(0).get_range() * 100) + "%");//涨跌幅
            currentPrice.setText(String.format("%.2f", price));//现价
            floatingPrices.setText(String.format("%.2f", stocks.get(0).get_latestPrice() - stocks.get(0).get_prevClose()));//涨跌价
            floatingRange.setText(String.format("%.2f", stocks.get(0).get_range() * 100) + "%");//涨跌幅
            dateilsPrec.setText(String.format("%.2f", prevClose));//昨收
            dateilsCount.setText(String.format("%.2f", stocks.get(0).get_turnover()) + "%");//换手率
            detailsOpeningPrice.setText(String.format("%.2f", open));//今开
            tvStockHigh.setText(String.format("%.2f", highPrice));//最高
            tvStockLow.setText(String.format("%.2f", lowPrice));//最低
            tvStockPe.setText(String.format("%.2f", pe));//市盈率
            FontUtil.setTextTypeface(currentPrice);
            if (prevClose <= price) {
                currentPrice.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_d63535));
                floatingPrices.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_d63535));
                floatingRange.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_d63535));
            } else if (prevClose > price) {
                currentPrice.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_119d3e));
                floatingPrices.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_119d3e));
                floatingRange.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_119d3e));
            }
            if (open >= prevClose) {
                detailsOpeningPrice.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_d63535));
            } else {
                detailsOpeningPrice.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_119d3e));
            }
            if (highPrice <= prevClose) {
                tvStockHigh.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_119d3e));
            } else if (highPrice > prevClose) {
                tvStockHigh.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_d63535));
            }
            if (lowPrice < prevClose) {
                tvStockLow.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_119d3e));

            } else if (lowPrice >= prevClose) {
                tvStockLow.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_d63535));
            }
            fivePositions = stocks.get(0).get_fivePositionList();
            if (data.get_status().getValue() == WAITING_FOR_PRICE.getValue()) {
                currentPrice.setText("——");//现价
                floatingPrices.setText("——");//涨跌价
                floatingRange.setText("——");//涨跌幅
                dateilsCount.setText("——");//换手率
                detailsOpeningPrice.setText("——");//今开
                tvStockHigh.setText("——");//最高
                tvStockLow.setText("——");//最低
                currentPrice.setTypeface(null);
                currentPrice.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_333333));
                floatingPrices.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_333333));
                floatingRange.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_333333));
                tvStockHigh.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_333333));
                tvStockLow.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_333333));
                detailsOpeningPrice.setTextColor(ContextCompat.getColor(StockDetailsActivity.this, R.color.cl_333333));
            }
        } else {
            status.setText("停牌");
            titlePrice.setText(prevClose + "");
            titleFloatingPrices.setText("0.00");//涨跌价
            titleRange.setText("0.00%");//涨跌幅

            currentPrice.setText(stocks.get(0).get_prevClose() + "");//现价
            floatingPrices.setText("0.00");//涨跌价
            floatingRange.setText("0.00%");//涨跌幅
            dateilsPrec.setText(String.format("%.2f", prevClose));//昨收
            dateilsCount.setText("——");//换手率
            detailsOpeningPrice.setText("——");//今开
            tvStockHigh.setText("——");//最高
            tvStockLow.setText("——");//最低
            tvStockPe.setText(String.format("%.2f", pe));//市盈率
        }


        if (data.get_status().getValue() == StatusCode.WAITING_FOR_PRICE.getValue()) {//等待定价
            status.setText("等待定价");
        } else if (data.get_status().getValue() == StatusCode.WAITING_FOR_OPEN.getValue()) {//等待开盘
            //   if (open > 0) {
            status.setText("等待开盘");
         /*   } else {
                status.setText("停牌");
            }*/
        }
    }


    /**
     * 启动计时器
     */
    private void startTimer() {
        timer = new Timer();
        //  刷新自选股定时器
        refreshStocksTask = new TimerTask() {
            @Override
            public void run() {
                List<String> list = new ArrayList<>();
                list.add(code);
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
            case DS_TAG_REFRESH_HANDICAP:
                if (data instanceof StockData) {
                    StockData stockData = (StockData) data;
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
