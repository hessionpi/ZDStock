package com.rjzd.aistock.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.bean.StockBasic;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.ZiXuanPresenter;
import com.rjzd.aistock.ui.activity.AIWeeklyActivity;
import com.rjzd.aistock.ui.activity.DailyBestPlateActivity;
import com.rjzd.aistock.ui.activity.DailyBestStockActivity;
import com.rjzd.aistock.ui.activity.SearchActivity;
import com.rjzd.aistock.ui.activity.StockDetailsActivity;
import com.rjzd.aistock.ui.adapters.ExponentPagerAdapter;
import com.rjzd.aistock.ui.adapters.StocksAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter.OnItemLongClickListener;
import com.rjzd.aistock.ui.views.pull2refresh.PullToRefreshView;
import com.rjzd.aistock.utils.view.DialogManager;
import com.rjzd.commonlib.utils.NetWorkUtil;
import com.rjzd.commonlib.utils.PixelUtil;
import com.rjzd.commonlib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hition on 2016/12/21.
 * <p>
 * 自选fragemnt
 */

public class ZiXuanFragment extends BaseFragment implements OnItemLongClickListener, ViewPager.OnPageChangeListener, View.OnClickListener {

    public static final String TAG = "ZiXuanFragment";
    @Bind(R.id.stocks_zixuan)
    RecyclerView mStocksView;

    @Bind(R.id.tv_add_zixuan)
    TextView mAddZixuan;
    @Bind(R.id.exponent_view)
    ViewPager exponentView;
    @Bind(R.id.ll_add_zixuan)
    LinearLayout llAddZixuan;
    @Bind(R.id.rl)
    RelativeLayout rl;
    @Bind(R.id.stocks_zixuan_header)
    LinearLayout stocksZixuanHeader;
    @Bind(R.id.ll_advanced_function)
    LinearLayout llAdvancedFunction;
    @Bind(R.id.fold)
    ImageView fold;
    @Bind(R.id.refresh_view)
    PullToRefreshView refreshView;

    @Bind(R.id.golden_stock)
    TextView mBestStock;
    @Bind(R.id.best_plate)
    TextView mBestPlate;
    @Bind(R.id.ai_weekly)
    TextView mAIWeekly;
    @Bind(R.id.divider)
    View divider;


    private StocksAdapter stocksAdapter;
    private LinearLayoutManager verticalLayoutManager;
    private ExponentPagerAdapter exponentAdapter;
    private ZiXuanPresenter presenter;

    // 待删除自选股代码
    private String code;
    private Timer refreshTimer;
    private TimerTask refreshTimerTask;


    //  第一个可见的itemview
    private int fPosition;
    //  最后一个可见的itemview
    private int lPosition;
    int index;
    private List<String> exponentCodeList = new ArrayList<>();
    private int userId;
    PopupWindow pw;

    private Handler mHandler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zixuan, container, false);
        ButterKnife.bind(this, view);
        presenter = new ZiXuanPresenter(this);
        initView();
        presenter.fillMyStocklist();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        exponentCodeList.add(Constants.EXPONENT_SH);
        exponentCodeList.add(Constants.EXPONENT_SZ);
        exponentCodeList.add(Constants.EXPONENT_CYB);
        exponentCodeList.add(Constants.EXPONENT_ZXB);
        exponentCodeList.add(Constants.EXPONENT_HS300);
        exponentCodeList.add(Constants.EXPONENT_SZ50);
    }

    @Override
    public void onResume() {
        super.onResume();

        userId = UserInfoCenter.getInstance().getUserId();

        if (!TextUtils.isEmpty(Constants.delCode) && -1 != Constants.delPosition) {
            presenter.deleteMyStock(Constants.delCode, Constants.delPosition);
            Constants.delCode = null;
            Constants.delPosition = -1;
        }

        if (Constants.isNeedNotify) {
            presenter.fillMyStocklist();
            Constants.isNeedNotify = false;
        }

        if (NetWorkUtil.isNetworkConnected(activity)) {
            startTimer();
        } else {
            activity.showToast(R.string.no_network);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            cancelTimer();
        } else {
            if (NetWorkUtil.isNetworkConnected(activity)) {
                startTimer();
            } else {
                activity.showToast(R.string.no_network);
            }
        }
    }

    @Override
    public void setStatistical() {
        statistical = "portfolio";
    }

    private void initView() {
        refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRefreshData();
                    }
                }, 1200);
            }
        });
        StockBasic sh = (StockBasic) mCache.getAsObject(Constants.EXPONENT_SH);
        StockBasic sz = (StockBasic) mCache.getAsObject(Constants.EXPONENT_SZ);
        StockBasic cyb = (StockBasic) mCache.getAsObject(Constants.EXPONENT_CYB);
        StockBasic zxb = (StockBasic) mCache.getAsObject(Constants.EXPONENT_ZXB);
        StockBasic hs300 = (StockBasic) mCache.getAsObject(Constants.EXPONENT_HS300);
        StockBasic sz50 = (StockBasic) mCache.getAsObject(Constants.EXPONENT_SZ50);
        ArrayList<StockBasic> cacheExponent = new ArrayList<>(6);
        if (null != sh) {
            cacheExponent.add(sh);
        }
        if (null != sz) {
            cacheExponent.add(sz);
        }
        if (null != cyb) {
            cacheExponent.add(cyb);
        }
        if (null != zxb) {
            cacheExponent.add(zxb);
        }
        if (null != hs300) {
            cacheExponent.add(hs300);
        }
        if (null != sz50) {
            cacheExponent.add(sz50);
        }
        if (!cacheExponent.isEmpty()) {
            exponentAdapter = new ExponentPagerAdapter(cacheExponent, activity);
            exponentView.setAdapter(exponentAdapter);
        }

        exponentView.addOnPageChangeListener(this);
        // 我的自选股Recycleview
        verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mStocksView.setLayoutManager(verticalLayoutManager);

        stocksAdapter = new StocksAdapter(activity);
        StockFooterView footerView = new StockFooterView();
        stocksAdapter.addFooter(footerView);
        mStocksView.setAdapter(stocksAdapter);
        stocksAdapter.setOnItemLongClickListener(this);


        stocksAdapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                StockBasic stoc = stocksAdapter.getItem(position);
                StockDetailsActivity.startActivity(activity, stoc.getCode(), stoc.getName());
            }
        });
        // 解决调用Recycleview进行局部刷新时候出现闪烁，其实解决办法就是去掉动画啦
        ((DefaultItemAnimator) mStocksView.getItemAnimator()).setSupportsChangeAnimations(false);

        mAddZixuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.startActivity(activity);
            }
        });
        mStocksView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                refreshView.setEnabled(verticalLayoutManager.findFirstVisibleItemPosition() == 0);
                /*&& null == refreshTimer*/
                if (pw != null) {
                    pw.dismiss();
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fPosition = verticalLayoutManager.findFirstVisibleItemPosition();
                    lPosition = verticalLayoutManager.findLastVisibleItemPosition();
                    getRefreshData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        fold.setOnClickListener(this);
        mBestStock.setOnClickListener(this);
        mBestPlate.setOnClickListener(this);
        mAIWeekly.setOnClickListener(this);
    }

    /**
     * 自选股 recycleview item 长按事件
     *
     * @param position item 所在位置
     * @return boolean
     */
    @Override
    public boolean onItemLongClick(final int position) {
        StockBasic s = stocksAdapter.getItem(position);
        if (null != s) {
            code = s.getCode();
        }

        showPopup(position);
        return true;
    }

    public void showPopup(final int position) {

        if (pw != null) {
            pw.dismiss();
        }
        View iteVmview = verticalLayoutManager.findViewByPosition(position);
        View view = LayoutInflater.from(activity).inflate(R.layout.zixuan_item_popup, null);
        LinearLayout ll_zixuan_popup = (LinearLayout) view.findViewById(R.id.ll_zixuan_popup);
        TextView delete_stocck = (TextView) view.findViewById(R.id.delete_stocck);
        TextView stick_stocck = (TextView) view.findViewById(R.id.stick_stocck);
        pw = new PopupWindow(activity);
        pw.setContentView(view);
        pw.setWidth(PixelUtil.dp2px(110));
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pw.setHeight(LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        if (position == 0) {
            pw.showAsDropDown(iteVmview, PixelUtil.dp2px(120), PixelUtil.dp2px(-10));
            ll_zixuan_popup.setBackground(ContextCompat.getDrawable(activity, R.drawable.zixuan_popup_bg_shang));
        } else {
            pw.showAsDropDown(iteVmview, PixelUtil.dp2px(120), PixelUtil.dp2px(-80));
            ll_zixuan_popup.setBackground(ContextCompat.getDrawable(activity, R.drawable.zixuan_popup_bg_xia));
        }
        // pw.setOutsideTouchable(true); // 设置popupwindow外部可点击
        delete_stocck.setOnClickListener(new View.OnClickListener() {//删除自选股
            @Override
            public void onClick(View v) {
                pw.dismiss();
                DialogManager.showSelectDialogWithTitle(getActivity(),
                        R.string.delete_zixuan, R.string.delete_zixuan_warning,
                        R.string.sure, R.string.cancel, false, new DialogManager.DialogListener() {
                            @Override
                            public void onNegative() {

                            }

                            @Override
                            public void onPositive() {
                                presenter.deleteMyStock(code, position);
                                if (userId > 0) {
                                    presenter.deletePortfolioFromServer(userId, code);
                                }
                                mCache.remove(code);
                                ToastUtils.show(activity, "删除成功", Toast.LENGTH_LONG);
                            }
                        });
            }
        });
        stick_stocck.setOnClickListener(new View.OnClickListener() {//置顶自选股
            @Override
            public void onClick(View v) {
                pw.dismiss();
                List<StockBasic> stockBasics = stocksAdapter.getAllData();
                StockBasic s = stocksAdapter.getItem(position);
                presenter.addMyStock(s);
                stockBasics.remove(position);
                stockBasics.add(0, s);
                stocksAdapter.setData(stockBasics);
                stocksAdapter.notifyItemRangeChanged(0, position + 1);


            }
        });
    }

    /**
     * 填充adapter数据
     */
    public void fillData2View(List<StockBasic> data) {

        for (StockBasic portfolio : data) {
            StockBasic cache = (StockBasic) mCache.getAsObject(portfolio.getCode());
            if (null != cache) {
                portfolio.setName(cache.getName());
                portfolio.setOpen(cache.getOpen());
                portfolio.setLatestPrice(cache.getLatestPrice());
                portfolio.setRange(cache.getRange());
                portfolio.setPredictDate(cache.getPredictDate());
                portfolio.setPredictionResult(cache.getPredictionResult());
            }
        }
        stocksAdapter.clear();
        stocksAdapter.addAll(data);
    }

    /**
     * 删除数据后更新
     */
    public void deleteItem(int position) {
        mStocksView.setItemAnimator(new DefaultItemAnimator());
        stocksAdapter.removeItem(position);
        ArrayList<StockBasic> newerList = (ArrayList<StockBasic>) stocksAdapter.getAllData();
        if (newerList.size() == 0) {
            showDataEmptyView();
        }
    }

    /**
     * 显示自选为空时view
     */
    public void showDataEmptyView() {
        llAddZixuan.setVisibility(View.VISIBLE);
        stocksZixuanHeader.setVisibility(View.GONE);
        divider.setVisibility(View.GONE);
        mStocksView.setPadding(0, PixelUtil.dp2px(230), 0, 0);
    }

    /**
     * 隐藏控view
     */
    public void hideEmptyView() {
        llAddZixuan.setVisibility(View.GONE);
        stocksZixuanHeader.setVisibility(View.VISIBLE);
        divider.setVisibility(View.VISIBLE);
        mStocksView.setPadding(0, 0, 0, 0);
    }

    /**
     * 刷新大盘指数或可见自选股视图
     */
    @Override
    public void fillData(Object data, int dsTag) {
        ((DefaultItemAnimator) mStocksView.getItemAnimator()).setSupportsChangeAnimations(false);
        if (refreshView.isRefreshing()) {
            refreshView.setRefreshing(false);
        }
        switch (dsTag) {
            case Constants.DS_TAG_TICK_REFRESH:
                ArrayList<StockBasic> refreshStockList = (ArrayList<StockBasic>) data;
                if (!refreshStockList.isEmpty()) {
                    for (StockBasic stock : refreshStockList) {
                        mCache.put(stock.getCode(), stock);
                    }
                }
                // 更新指数
                List<StockBasic> exponentList = refreshStockList.subList(0, 6);
                exponentAdapter = new ExponentPagerAdapter(exponentList, activity);
                exponentView.setAdapter(exponentAdapter);
                exponentView.setCurrentItem(index);

                if (refreshStockList.size() > 6) {
                    // 更新自选列表
                    List<StockBasic> portfolioList = refreshStockList.subList(6, refreshStockList.size());
                    List<StockBasic> zixuanStocks = stocksAdapter.getItems();
                    int index = fPosition;
                    for (StockBasic sc : portfolioList) {
                        if (index > lPosition) {
                            break;
                        }
                        sc.setHasAdd(1);
                        zixuanStocks.set(index, sc);
                        index++;
                    }
                    // 局部刷新RecycleView
                    stocksAdapter.setData(zixuanStocks);
                    stocksAdapter.notifyItemRangeChanged(fPosition, (lPosition - fPosition) + 1);
                }
                break;

            case Constants.DS_TAG_DELETE_PORTFOLIO_SERVER:
                showToast("同步删除成功!");

                break;

            default:
                if (null == data) {
                    showFailedView();
                }
                break;
        }
    }

    /**
     * 启动计时器  由于Timer的schedule和cancel 方法都只能够被调用一次，所以每次启动都需要新建
     */
    private void startTimer() {
        refreshTimer = new Timer();

        //  刷新大盘指数定时器
        refreshTimerTask = new TimerTask() {
            @Override
            public void run() {
                //  刷新自选
                if (stocksAdapter.getCount() > 0) {
                    try {
                        fPosition = verticalLayoutManager.findFirstVisibleItemPosition();
                        lPosition = verticalLayoutManager.findLastVisibleItemPosition();
                        if (fPosition == -1) {
                            fPosition = 0;
                        }
                    } catch (Exception e) {
                        if (lPosition < 10) {
                            if (stocksAdapter.getCount() >= 10) {
                                lPosition = 9;
                            } else {
                                lPosition = stocksAdapter.getCount() - 1;
                            }
                        }
                        e.printStackTrace();
                    }
                }
                getRefreshData();
            }
        };
        refreshTimer.schedule(refreshTimerTask, 300, Constants.PERIOD_REFRESH);
    }

    private void getRefreshData() {
        List<String> codeList = new ArrayList<>();
        codeList.addAll(exponentCodeList);
        if (stocksAdapter.getCount() > 0) {
            List<String> portfolioCodeList = new ArrayList<>();
            for (int i = fPosition; i <= lPosition - 1; i++) {
                portfolioCodeList.add(stocksAdapter.getItem(i).getCode());
            }

            if (!portfolioCodeList.isEmpty()) {
                codeList.addAll(6, portfolioCodeList);
            }
        }
        // 刷新我的自选股
        presenter.refreshStockData(codeList);
    }

    /**
     * 取消刷新股票指数
     */
    public void cancelTimer() {
        if (null != refreshTimer) {
            refreshTimer.cancel();
            refreshTimer = null;
        }
        if (null != refreshTimerTask) {
            refreshTimerTask.cancel();
            refreshTimerTask = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        cancelTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
        if (null != pw && pw.isShowing()) {
            pw.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        index = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class StockFooterView implements XMBaseAdapter.ItemView {

        @Override
        public View onCreateView(ViewGroup parent) {

            View footer = LayoutInflater.from(activity).inflate(R.layout.layout_zixuan_footer, null);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            footer.setLayoutParams(params);

            return footer;
        }

        @Override
        public void onBindView(View footerView) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fold:
                if (llAdvancedFunction.getVisibility() == View.VISIBLE) {
                    fold.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.zixuan_unfold));
                    llAdvancedFunction.setVisibility(View.GONE);
                } else {
                    fold.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.zixuan_packup));
                    llAdvancedFunction.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.golden_stock:
                DailyBestStockActivity.startActivity(activity);
                break;

            case R.id.best_plate:
                DailyBestPlateActivity.startActivity(activity);
                break;

            case R.id.ai_weekly:
                AIWeeklyActivity.startActivity(activity);
                break;
        }
    }

}
