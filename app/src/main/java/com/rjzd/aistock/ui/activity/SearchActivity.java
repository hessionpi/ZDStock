package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.bean.SearchHistory;
import com.rjzd.aistock.bean.StockBasic;
import com.rjzd.aistock.model.AddPortfolioListener;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.SearchPresenter;
import com.rjzd.aistock.ui.adapters.SearchHistoryAdapter;
import com.rjzd.aistock.ui.adapters.SearchStockAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.ui.views.ClearEditText;
import com.rjzd.aistock.ui.views.MyItemDecoration;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.PixelUtil;
import com.rjzd.commonlib.utils.ToastUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity implements TextWatcher, View.OnClickListener, IFillDataView {

    @Bind(R.id.cet_search)
    ClearEditText cetSearch;
    @Bind(R.id.tv_cancel)
    TextView mCancel;
    @Bind(R.id.rv_search_history)
    RecyclerView mHistoryView;
    @Bind(R.id.rv_search_match)
    RecyclerView mMatchView;
    @Bind(R.id.tv_no_retult)
    TextView tvNoRetult;

    private SearchStockAdapter matchStockAdapter;
    private SearchHistoryAdapter historyAdapter;
    private SearchPresenter presenter;
    private Handler handler;
    private int userId;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
        presenter = new SearchPresenter(this);
        userId = UserInfoCenter.getInstance().getUserId();
        // 获取历史记录
        presenter.showHistory();
        handler = new Handler();
    }

    private void initView() {

        MyItemDecoration decoration = new MyItemDecoration(this, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#e6e6e6"));
        mHistoryView.addItemDecoration(decoration);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mHistoryView.setLayoutManager(layoutmanager);
        historyAdapter = new SearchHistoryAdapter(this);
        mHistoryView.setAdapter(historyAdapter);

        mMatchView.addItemDecoration(decoration);
        LinearLayoutManager layoutmanager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMatchView.setLayoutManager(layoutmanager2);
        matchStockAdapter = new SearchStockAdapter(this);
        mMatchView.setAdapter(matchStockAdapter);

        historyAdapter.setOnItemClickListener(new HistoryItemClickListener());
        matchStockAdapter.setOnItemClickListener(new MatchItemClickListener());
        historyAdapter.setOnAddPortfolioListener(new PortfolioClickListener());
        matchStockAdapter.setOnAddPortfolioListener(new PortfolioClickListener());

        //设置输入框里面内容发生改变的监听
        cetSearch.addTextChangedListener(this);
        mCancel.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(final Editable editable) {
        handler.postDelayed(new Runnable() {
            public void run() {
                presenter.searchStock(editable.toString());
            }
        }, 500);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    @Override
    public void fillData(Object data, int dsTag) {
        switch (dsTag) {
            case Constants.DS_TAG_SEARCH_STOCK_HISTORY: // 获取搜索历史记录
                if (data instanceof List) {
                    List<SearchHistory> historyList = (List<SearchHistory>) data;
                    if (null == historyList || historyList.isEmpty()) {
                        mHistoryView.setVisibility(View.GONE);
                    } else {
                        historyAdapter.addFooter(new FooterView());
                        mHistoryView.setVisibility(View.VISIBLE);
                        historyAdapter.clear();
                        historyAdapter.addAll(historyList);
                    }
                }
                break;

            case Constants.DS_TAG_SEARCH_STOCKS:    // 搜索匹配股票
                if (null == data) {
                    mHistoryView.setVisibility(View.VISIBLE);
                    mMatchView.setVisibility(View.GONE);
                    return;
                }

                if (data instanceof List) {
                    List<StockBasic> matchList = (List<StockBasic>) data;
                    if (matchList.isEmpty()) {
                        tvNoRetult.setVisibility(View.VISIBLE);
                        mMatchView.setVisibility(View.GONE);
                    } else {
                        if (matchList.get(0) instanceof StockBasic) {
                            tvNoRetult.setVisibility(View.GONE);
                            mMatchView.setVisibility(View.VISIBLE);
                            matchStockAdapter.setData(matchList);
                            matchStockAdapter.notifyDataSetChanged();
                        }
                    }
                    mHistoryView.setVisibility(View.GONE);
                }
                break;
        }

    }

    @Override
    public void showFailedView() {

    }

    private class FooterView implements XMBaseAdapter.ItemView {
        @Override
        public View onCreateView(ViewGroup parent) {
            TextView footer = new TextView(SearchActivity.this);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, PixelUtil.dp2px(45));

            footer.setLayoutParams(params);
            footer.setGravity(Gravity.CENTER);
            footer.setBackgroundResource(R.color.white);
            // footer.setPadding(0,PixelUtil.dp2px(8),0,PixelUtil.dp2px(8));
            footer.setText(R.string.clear_history);
            footer.setTextSize(13);
            footer.setTextColor(Color.parseColor("#999999"));

            footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //清空历史记录
                    presenter.clearHistory();
                }
            });
            return footer;
        }

        @Override
        public void onBindView(View headerView) {

        }
    }

    private class MatchItemClickListener implements XMBaseAdapter.OnItemClickListener {
        @Override
        public void onItemClick(int position) {
            StockBasic matchStock = matchStockAdapter.getItem(position);
            presenter.insertHistory(matchStock);
            StockDetailsActivity.startActivity(SearchActivity.this, matchStock.getCode(), matchStock.getName());
            finish();
        }
    }

    private class HistoryItemClickListener implements XMBaseAdapter.OnItemClickListener {
        @Override
        public void onItemClick(int position) {
            // 加入到搜索历史
            SearchHistory history = historyAdapter.getItem(position);
            StockBasic stoc = new StockBasic(history.getCode(), history.getStockname());
            StockDetailsActivity.startActivity(SearchActivity.this, stoc.getCode(), stoc.getName());
            finish();
        }
    }

    private class PortfolioClickListener implements AddPortfolioListener {
        @Override
        public void onAddClick(StockBasic stock) {
            presenter.addPortfolio(stock);
            if (userId > 0) {
                presenter.addPortfolio2Server(userId, stock.getCode());
            }
            ToastUtils.show(SearchActivity.this, "添加成功", Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }
}
