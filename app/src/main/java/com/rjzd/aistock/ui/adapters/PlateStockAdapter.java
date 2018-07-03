package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.Market;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;

/**
 * Created by Hition on 2017/5/24.
 */

public class PlateStockAdapter extends XMBaseAdapter<Market> {

    public PlateStockAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlateHolder(parent, R.layout.item_plate_stock);
    }

    private class PlateHolder extends BaseViewHolder<Market>{
        private TextView mStockName;
        private TextView mStockCode;
        private TextView mRange;
        private TextView mPrice;

        PlateHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            mStockName = $(R.id.tv_stock_name);
            mStockCode = $(R.id.tv_stock_code);
            mRange = $(R.id.tv_range);
            mPrice = $(R.id.tv_price);
        }

        @Override
        public void setData(Market data) {
            mStockName.setText(data.get_stockName());
            mStockCode.setText(data.get_stockCode());
            if(data.get_latestPrice() > 0){
                mRange.setText(data.get_rangePercentage()+"%");
                mPrice.setText(data.get_latestPrice()+"");
                if(data.get_rangePercentage() < 0){
                    mRange.setTextColor(ContextCompat.getColor(mContext,R.color.cl_119d3e));
                }else{
                    mRange.setTextColor(ContextCompat.getColor(mContext,R.color.cl_d63535));
                }
            }else{
                mRange.setText("--");
                mPrice.setText("停牌");
            }
        }
    }
}
