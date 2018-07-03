package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rjzd.aistock.R;
import com.rjzd.aistock.api.AITransfer;
import com.rjzd.aistock.api.TransferType;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.utils.DateUtil;
import com.rjzd.aistock.utils.view.DialogManager;
import com.rjzd.commonlib.utils.LogUtil;

/**
 * 调仓 adapter
 *
 * Created by Hition on 2017/5/19.
 */

public class TransferAdapter extends XMBaseAdapter<AITransfer> {

    public TransferAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new TransferHolder(parent, R.layout.item_ai_transfer);
    }

    private class TransferHolder extends BaseViewHolder<AITransfer>{

        private ImageView mAction;
        private TextView mTime;
        private TextView mName;
        private TextView mCode;
        private TextView mDealPrice;
        private TextView mPositionChange;
        private TextView mProfitLoss;
        private TextView mTransferStrategy;

        TransferHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            mAction = $(R.id.iv_action);
            mName = $(R.id.tv_name);
            mCode = $(R.id.tv_code);
            mDealPrice = $(R.id.tv_deal_price);
            mPositionChange = $(R.id.tv_position_percentage_change);
            mProfitLoss = $(R.id.tv_profit_loss);
            mTransferStrategy = $(R.id.tv_transfer_strategy);
            mTime=$(R.id.tv_time);
        }

        @Override
        public void setData(final AITransfer data) {
            if(null == data){
                return ;
            }

            mName.setText(data.get_stockName());
            mCode.setText(data.get_stockCode());
          long time=  DateUtil.parser24(data.get_actionTime()).getTime();
            LogUtil.e("+++++++++++++++++++++++++++++++"+time);
            mTime.setText(DateUtil.getShortDateJustHour(time/1000));
            mProfitLoss.setText(data.get_profitPercentage()+"%");

            if (data.get_type() == TransferType.SELL) {
                mAction.setBackgroundResource(R.drawable.ai_sell);
                mDealPrice.setText(data.get_sellPrice()+"成交");
                mPositionChange.setText(data.get_positionPercentage()+"%"+" -> "+"0.00%");
            }else{
                mAction.setBackgroundResource(R.drawable.ai_buy);
                mDealPrice.setText(data.get_buyPrice()+"成交");
                mPositionChange.setText("0.00% -> " + data.get_positionPercentage()+"%");
            }

            if (data.get_profitPercentage()<0){
                mProfitLoss.setTextColor(ContextCompat.getColor(mContext,R.color.cl_119d3e));
            }else{
                mProfitLoss.setTextColor(ContextCompat.getColor(mContext,R.color.cl_f95555));
            }
            mTransferStrategy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogManager.showAiStrategyDialog(mContext,"       "+data.get_actionReason(),true);
                }
            });
        }
    }


}
