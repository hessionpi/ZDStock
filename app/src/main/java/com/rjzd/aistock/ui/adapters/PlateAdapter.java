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

public class PlateAdapter extends XMBaseAdapter<Market> {

    public PlateAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlateHolder(parent, R.layout.item_market_plate);
    }

    private class PlateHolder extends BaseViewHolder<Market>{
        private TextView mPlateName;
        private TextView mPlateRange;
        private TextView mTop1;

        PlateHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            mPlateName = $(R.id.tv_plate_name);
            mPlateRange = $(R.id.tv_plate_range);
            mTop1 = $(R.id.tv_plate_top1);
        }

        @Override
        public void setData(Market data) {
            mPlateName.setText(data.get_plateName());
            mPlateRange.setText(data.get_rangePercentage() + "%");
            mTop1.setText(data.get_stockName());

            if(data.get_rangePercentage()>0){
                mPlateRange.setTextColor(ContextCompat.getColor(mContext,R.color.cl_d63535));
            }else{
                mPlateRange.setTextColor(ContextCompat.getColor(mContext,R.color.cl_119d3e));
            }
        }
    }
}
