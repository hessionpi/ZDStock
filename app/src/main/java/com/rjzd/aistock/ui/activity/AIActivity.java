package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.AIAttention;
import com.rjzd.aistock.api.AIIncome;
import com.rjzd.aistock.api.AIIncomeInfo;
import com.rjzd.aistock.api.AIIncomeType;
import com.rjzd.aistock.api.AIOperation;
import com.rjzd.aistock.api.AIOperationList;
import com.rjzd.aistock.api.AITransfer;
import com.rjzd.aistock.api.AttentionFlag;
import com.rjzd.aistock.api.DateTransfer;
import com.rjzd.aistock.api.DateTransferList;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.presenter.AIPresenter;
import com.rjzd.aistock.ui.adapters.HoldPositionAdapter;
import com.rjzd.aistock.ui.adapters.TransferAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.ui.views.MyItemDecoration;
import com.rjzd.aistock.ui.views.ToolbarView;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.utils.DateUtil;
import com.rjzd.aistock.utils.AILineChartUtil;
import com.rjzd.aistock.utils.view.DialogManager;
import com.rjzd.commonlib.imageloader.ZDImgloader;
import com.rjzd.commonlib.utils.NetWorkUtil;
import com.rjzd.commonlib.utils.ToastUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * AI 详情
 *
 * Created by Hition on 2017/6/7.
 */

public class AIActivity extends ShareActivity implements View.OnClickListener {

    public static final String TAG = "AIActivity";

    @Bind(R.id.toolbar)
    ToolbarView mToolbar;
    @Bind(R.id.iv_ai_avatar)
    ImageView mAvatar;
    @Bind(R.id.online_date)
    TextView mOnlineDate;
    @Bind(R.id.tv_chara_label)
    TextView mCharactor;
    @Bind(R.id.tv_introduce)
    TextView mIntroduce;

    @Bind(R.id.tv_ai_week)
    TextView tvAiWeek;
    @Bind(R.id.tv_ai_month)
    TextView tvAiMonth;
    @Bind(R.id.tv_ai_always)
    TextView tvAiAlways;
    @Bind(R.id._week_idc)
    View WeekIdc;
    @Bind(R.id.month_idc)
    View monthIdc;
    @Bind(R.id.always_idc)
    View alwaysIdc;

    @Bind(R.id.tv_ai_income)
    TextView tvAiIncome;
    @Bind(R.id.tv_beyond_market)
    TextView tvBeyondMarket;
    @Bind(R.id.ai_linechart)
    LineChart aiLinechart;
    @Bind(R.id.tv_income_profile)
    TextView tvIncomeProfile;

    @Bind(R.id.tv_latest_transfer_time)
    TextView mLatestTransferTime;
    @Bind(R.id.tv_transfer_more)
    TextView mTransferMore;
    @Bind(R.id.tv_hold_stock_more)
    TextView mHoldStockMore;
    @Bind(R.id.tv_profit_and_loss_more)
    TextView mProfitAndLossMore;
    @Bind(R.id.rv_transfer)
    RecyclerView mTransfer;
    @Bind(R.id.rv_hold_stock)
    RecyclerView mHoldStock;
    @Bind(R.id.tv_stock_name)
    TextView mStockName;
    @Bind(R.id.tv_stock_code)
    TextView mStockCode;
    @Bind(R.id.tv_profit_and_loss_percentage)
    TextView mProfitAndloss;
    @Bind(R.id.tv_hold_days)
    TextView mHoldDays;
    @Bind(R.id.tv_buy_time)
    TextView mBuyTime;
    @Bind(R.id.income_symbol)
    TextView incomeSymbol;
    @Bind(R.id.percent)
    TextView percent;
    @Bind(R.id.tv_ai_income_color)
    TextView tvAiIncomeColor;

    @Bind(R.id.tv_share_for_points)
    TextView mShare;
    @Bind(R.id.attention)
    TextView mAttention;

    private AIPresenter aiPresenter;

    private boolean isNeedLoadData = true;
    private TransferAdapter aiTransferAdapter;
    protected HoldPositionAdapter holdAdapter;

    private String robotId;
    private String alias;
    private String robotName;
    private String photoImg;
    private String onlineTime;
    private String skill;
    private String introduce;

    private int action;

    public static void startActivity(Context context, String robotId, String robotImg, String alias,String name, String onlineTime, String skill, String introduce) {
        Intent intent = new Intent(context, AIActivity.class);
        intent.putExtra("ai_robot_id", robotId);
        intent.putExtra("ai_robot_alias_name", alias);
        intent.putExtra("ai_robot_name", name);
        intent.putExtra("ai_robot_img", robotImg);
        intent.putExtra("ai_online_time", onlineTime);
        intent.putExtra("ai_robot_skill", skill);
        intent.putExtra("ai_robot_introduce", introduce);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai);
        ButterKnife.bind(this);
        aiPresenter = new AIPresenter(this);
        parseIntent();
        init();
        if (NetWorkUtil.isNetworkConnected(this)) {
            aiPresenter.getAIIncome(robotId, AIIncomeType.INCOME_TOTAL);
            showLoadingView();
        }else{
            ToastUtils.show(this,R.string.network_error);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(NetWorkUtil.isNetworkConnected(this)){
            if(userId>0){
                aiPresenter.getAttentionAIs(userId);
            }
        }else{
            ToastUtils.show(this,R.string.network_error);
        }

    }

    private void parseIntent() {
        robotId = getIntent().getStringExtra("ai_robot_id");
        alias = getIntent().getStringExtra("ai_robot_alias_name");
        robotName = getIntent().getStringExtra("ai_robot_name");
        photoImg = getIntent().getStringExtra("ai_robot_img");
        onlineTime = getIntent().getStringExtra("ai_online_time");
        skill = getIntent().getStringExtra("ai_robot_skill");
        introduce = getIntent().getStringExtra("ai_robot_introduce");
    }

    private void init() {
        mToolbar.setTitle(robotName);
        ZDImgloader.load(this, photoImg, R.drawable.ai_avatar_default, R.drawable.ai_avatar_default, mAvatar);
        mOnlineDate.setText("上线时间：" + onlineTime);
        mCharactor.setText(skill);
        mIntroduce.setText(introduce);

        tvAiAlways.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
        alwaysIdc.setBackgroundColor(ContextCompat.getColor(this, R.color.cl_d63535));

        MyItemDecoration decoration = new MyItemDecoration(this, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#e6e6e6"));
        // init the transfer recycleview
        mTransfer.addItemDecoration(decoration);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTransfer.setLayoutManager(layoutmanager);
        aiTransferAdapter = new TransferAdapter(this);
        mTransfer.setAdapter(aiTransferAdapter);

        // init the hold stocks recycleview
        mHoldStock.addItemDecoration(decoration);
        LinearLayoutManager holdLayoutmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mHoldStock.setLayoutManager(holdLayoutmanager);
        holdAdapter = new HoldPositionAdapter(this);
        mHoldStock.setAdapter(holdAdapter);

        tvAiWeek.setOnClickListener(this);
        tvAiMonth.setOnClickListener(this);
        tvAiAlways.setOnClickListener(this);
        mTransferMore.setOnClickListener(this);
        mHoldStockMore.setOnClickListener(this);
        mProfitAndLossMore.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mAttention.setOnClickListener(this);

        aiTransferAdapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AITransfer transfer = aiTransferAdapter.getItem(position);
                StockDetailsActivity.startActivity(AIActivity.this, transfer.get_stockCode(), transfer.get_stockName());
            }
        });

        holdAdapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AIOperation operation = holdAdapter.getItem(position);
                StockDetailsActivity.startActivity(AIActivity.this, operation.get_stockCode(), operation.get_stockName());
            }
        });
    }

    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data,dsTag);
        dismissLoading();
        switch (dsTag) {
            case Constants.DS_TAG_AI_INCOME:
                AIIncome income = (AIIncome) data;
                setAIIncomeData(income.get_data());
                if (isNeedLoadData) {
                    aiPresenter.getAIActions(robotId, 0, 2);
                    aiPresenter.getHoldStocks(robotId, 2);
                    aiPresenter.getHistoryPAL(robotId, 1, false);
                }
                break;

            case Constants.DS_TAG_AI_ACTION:
                DateTransferList actionList = (DateTransferList) data;
                List<DateTransfer> transferList = actionList.get_data();
                if (null == transferList || transferList.isEmpty()) {
                    return;
                }
                mLatestTransferTime.setText(transferList.get(0).get_actionTime());
                aiTransferAdapter.clear();
                aiTransferAdapter.addAll(transferList.get(0).get_transfers());
                break;

            case Constants.DS_TAG_AI_HOLD_STOCK:
                AIOperationList holdStockData = (AIOperationList) data;
                holdAdapter.clear();
                holdAdapter.addAll(holdStockData.get_data());
                break;

            case Constants.DS_TAG_AI_HISTORY_PAL:
                List<AIOperation> incomeAndLoss = (List<AIOperation>) data;
                if (null == incomeAndLoss || incomeAndLoss.isEmpty()) {
                    return;
                }
                AIOperation operation = incomeAndLoss.get(0);
                mStockName.setText(operation.get_stockName());
                mStockCode.setText(operation.get_stockCode());
                mProfitAndloss.setText(operation.get_profitPercentage() + "%");
                if (operation.get_profitPercentage() < 0) {
                    mProfitAndloss.setTextColor(ContextCompat.getColor(this, R.color.cl_119d3e));
                } else {
                    mProfitAndloss.setTextColor(ContextCompat.getColor(this, R.color.cl_f95555));
                }
                mHoldDays.setText(operation.get_aiProfitPoints_size() + "");
                Date buyTime = DateUtil.parser(operation.get_buyTime());
                String formatBuyTime = DateUtil.formatDate(buyTime, "yyyy-MM-dd");
                mBuyTime.setText(formatBuyTime);
                break;

            case Constants.DS_TAG_AI_ATTENTION_ADD_OR_CANCEL:
                IsSuccess isSuccess = (IsSuccess) data;
                if(StatusCode.OK.getValue() == isSuccess.get_status().getValue()){
                    if(isSuccess.is_data()){
                        switch (action){
                            case 1:       // cancel成功
                                ToastUtils.show(this, "取消成功");
                                mAttention.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                                mAttention.setText(R.string.attention_add);
                                mAttention.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_zixuan_icon,0,0,0);
                                break;

                            case 0:    // 关注成功
                                ToastUtils.show(this, "关注成功");
                                mAttention.setText(R.string.attention_cancel);
                                mAttention.setTextColor(ContextCompat.getColor(this, R.color.cl_333333));
                                mAttention.setCompoundDrawablesWithIntrinsicBounds(R.drawable.delete_zixuan,0,0,0);
                                break;
                        }
                    }
                }
                break;

            case Constants.DS_TAG_AI_ATTENTION_GET:
                AIAttention aiAttention = (AIAttention) data;
                if(StatusCode.OK.getValue() == aiAttention.get_status().getValue()){
                    List<String> attentionAIs = aiAttention.get_data();
                    if(!attentionAIs.isEmpty() && attentionAIs.contains(robotId)){
                        mAttention.setTag(true);
                        mAttention.setText(R.string.attention_cancel);
                        mAttention.setTextColor(ContextCompat.getColor(this, R.color.cl_333333));
                        mAttention.setCompoundDrawablesWithIntrinsicBounds(R.drawable.delete_zixuan,0,0,0);
                    }else{
                        mAttention.setTag(false);
                        mAttention.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                        mAttention.setText(R.string.attention_add);
                        mAttention.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_zixuan_icon,0,0,0);
                    }
                }

                break;
        }
    }

    @Override
    public void showFailedView() {
        dismissLoading();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.tv_ai_week:  // 近一周
                isNeedLoadData = false;
                clearEarningStatus();
                tvAiWeek.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                WeekIdc.setBackgroundColor(ContextCompat.getColor(this, R.color.cl_d63535));
                tvIncomeProfile.setText("周收益概况");
                aiPresenter.getAIIncome(robotId, AIIncomeType.LATEST_INCOME_WEEK);

                break;

            case R.id.tv_ai_month:      // 近一月
                isNeedLoadData = false;
                clearEarningStatus();
                tvAiMonth.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                monthIdc.setBackgroundColor(ContextCompat.getColor(this, R.color.cl_d63535));
                tvIncomeProfile.setText("月收益概况");
                aiPresenter.getAIIncome(robotId, AIIncomeType.LATEST_INCOME_MONTH);

                break;

            case R.id.tv_ai_always:  // 总收益
                isNeedLoadData = false;
                clearEarningStatus();
                tvAiAlways.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
                alwaysIdc.setBackgroundColor(ContextCompat.getColor(this, R.color.cl_d63535));
                tvIncomeProfile.setText("总收益概况");
                aiPresenter.getAIIncome(robotId, AIIncomeType.INCOME_TOTAL);

                break;

            case R.id.tv_transfer_more://所有调仓
                AiAdjustdynamicActivity.startActivity(this, robotId);
                break;

            case R.id.tv_hold_stock_more://所有持仓股票
                AiPositionstockActivity.startActivity(this, robotId);
                break;

            case R.id.tv_profit_and_loss_more://所有历史盈亏
                AiEarningsActivity.startActivity(this, robotId);
                break;

            case R.id.tv_share_for_points:
                // 弹出分享面板分享、分享成功后调用加积分接口
                String url = Constants.AI_SHARE_ADDRESS + alias;
                String title = robotName + "壹眼富_免费AI股票助手";
                openShareBoard(url,title,photoImg);
                break;

            case R.id.attention:
                // 添加关注取消关注
                if(0 == userId){
                    LoginActivity.startActivity(AIActivity.this);
                    return ;
                }

                boolean hasAttention = (boolean) v.getTag();

                if(hasAttention){
                    //v.setTag(false);
                    // 弹框删除自选
                    DialogManager.showSelectDialog(AIActivity.this, R.string.attention_warning,
                            R.string.sure, R.string.cancel, false, new DialogManager.DialogListener() {
                                @Override
                                public void onNegative() {

                                }

                                @Override
                                public void onPositive() {
                                    v.setTag(false);
                                    aiPresenter.addOrCancelAttention(robotId,userId, AttentionFlag.CANCEL);
                                    action = 1;
                                }
                            });
                }else{
                    v.setTag(true);
                    aiPresenter.addOrCancelAttention(robotId,userId, AttentionFlag.ADD);
                    action = 0;
                }
                break;
        }
    }

    void clearEarningStatus() {
        tvAiWeek.setTextColor(ContextCompat.getColor(this, R.color.cl_666666));
        tvAiMonth.setTextColor(ContextCompat.getColor(this, R.color.cl_666666));
        tvAiAlways.setTextColor(ContextCompat.getColor(this, R.color.cl_666666));
        WeekIdc.setBackgroundColor(ContextCompat.getColor(this, R.color.cl_e6e6e6));
        monthIdc.setBackgroundColor(ContextCompat.getColor(this, R.color.cl_e6e6e6));
        alwaysIdc.setBackgroundColor(ContextCompat.getColor(this, R.color.cl_e6e6e6));
    }

    //收益
    void setAIIncomeData(AIIncomeInfo aiIncomeInfo) {
        List<String> labels = new ArrayList<>();
        Date startDate = DateUtil.parser(aiIncomeInfo.get_startTime());
        String startTime = DateUtil.formatDate(startDate, "yyyy-MM-dd");
        Date endDate = DateUtil.parser(aiIncomeInfo.get_endTime());
        String endTime = DateUtil.formatDate(endDate, "yyyy-MM-dd");
        labels.add(startTime);
        labels.add(endTime);
         tvAiIncomeColor.setText(robotName+"收益");
        tvAiIncome.setText(String.format("%.2f", aiIncomeInfo.get_achievement()));
        if (aiIncomeInfo.get_achievement() >= 0) {
            tvAiIncome.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
            incomeSymbol.setText("+");
            incomeSymbol.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
            percent.setTextColor(ContextCompat.getColor(this, R.color.cl_d63535));
        } else if (aiIncomeInfo.get_achievement() < 0) {
            tvAiIncome.setTextColor(ContextCompat.getColor(this, R.color.cl_119d3e));
            incomeSymbol.setText("-");
            incomeSymbol.setTextColor(ContextCompat.getColor(this, R.color.cl_119d3e));
            percent.setTextColor(ContextCompat.getColor(this, R.color.cl_119d3e));
        }
        tvBeyondMarket.setText("超越沪深300  " + String.format("%.2f", aiIncomeInfo.get_achievement() - aiIncomeInfo.get_compareResult()) + "%");
        AILineChartUtil.showLine(aiLinechart, aiIncomeInfo.get_hs300IncomePoints(), aiIncomeInfo.get_incomePoints(), labels);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        aiPresenter.onUnsubscribe();
    }

}
