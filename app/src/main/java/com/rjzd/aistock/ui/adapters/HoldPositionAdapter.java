package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjzd.aistock.R;
import com.rjzd.aistock.api.AIOperation;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;

/**
 * 持仓股票 adapter
 * Created by Hition on 2017/5/19.
 */

public class HoldPositionAdapter extends XMBaseAdapter<AIOperation> {

    public HoldPositionAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new HoldPositionHolder(parent,R.layout.item_hold_position_stock);
    }

    private class HoldPositionHolder extends BaseViewHolder<AIOperation>{

        private TextView mName;
        private TextView mCode;
        private TextView mPosition;
        private TextView mLatestPrice;
        private TextView mProfitAndLoss;

        HoldPositionHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            mName = $(R.id.ai_stock_name);
            mCode = $(R.id.ai_stock_code);
            mPosition = $(R.id.tv_position_percentage);
            mLatestPrice = $(R.id.tv_latest_price);
            mProfitAndLoss = $(R.id.tv_profit_and_loss);
        }

        @Override
        public void setData(AIOperation data) {
            if(null == data){
                return ;
            }

            mName.setText(data.get_stockName());
            mCode.setText(data.get_stockCode());
            mPosition.setText(data.get_positionPercentage()+"%");
            mLatestPrice.setText(data.get_sellPrice()+"");
            if(data.get_profit() > 0){
                mProfitAndLoss.setTextColor(ContextCompat.getColor(mContext, R.color.cl_f95555));
            }else{
                mProfitAndLoss.setTextColor(ContextCompat.getColor(mContext, R.color.cl_119d3e));
            }
            mProfitAndLoss.setText(data.get_profitPercentage()+"%");
        }
    }
}
