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
 * Created by Administrator on 2017/3/16.
 */

public class AIPositionstockAdapter extends XMBaseAdapter<AIOperation> {
    public AIPositionstockAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new AIPositionstockHolder(parent, R.layout.item_recycle_ai_positionstock);
    }

    class AIPositionstockHolder extends BaseViewHolder<AIOperation> {
        TextView ai_stock_name;
        TextView ai_stock_code;
        TextView tv_ai_range;
        TextView tv_ai_position_range;
        TextView tv_ai_price;
        public AIPositionstockHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            ai_stock_name=$(R.id.ai_stock_name);
            ai_stock_code=$(R.id.ai_stock_code);
            tv_ai_range=$(R.id.tv_ai_range);
            tv_ai_position_range=$(R.id.tv_ai_position_range);
            tv_ai_price=$(R.id.tv_ai_price);
        }
        @Override
        public void setData(AIOperation data) {
            ai_stock_name.setText(data.get_stockName());
            ai_stock_code.setText(data.get_stockCode());
            tv_ai_range.setText(String.format("%.2f", data.get_profitPercentage())+"%");
           tv_ai_position_range.setText(String.format("%.1f", data.get_positionPercentage())+"%");
            tv_ai_price.setText(data.get_sellPrice()+"");
            if (data.get_profitPercentage()>0){
                tv_ai_range.setTextColor(ContextCompat.getColor(mContext, R.color.cl_raise));
            }else if(data.get_profitPercentage()<0){
                tv_ai_range.setTextColor(ContextCompat.getColor(mContext, R.color.cl_119d3e));
            }else{
                tv_ai_range.setTextColor(ContextCompat.getColor(mContext, R.color.cl_333333));
            }
        }

    }
}

