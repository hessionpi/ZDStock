package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rjzd.aistock.api.BasicStock;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.R;




/**
 * Created by Administrator on 2017/1/16.
 */


public class ExponentCrunchiesAdapter extends XMBaseAdapter {

    public ExponentCrunchiesAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExponentCrunchiesHolder(parent, R.layout.item_recycle_exponent);
    }

    class ExponentCrunchiesHolder extends BaseViewHolder<BasicStock> {
        TextView stock_name;
        TextView stock_code;
        TextView price;
        TextView fluctuation_value;

        public ExponentCrunchiesHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            stock_name = $(R.id.stock_name);
            stock_code = $(R.id.stock_code);
            price = $(R.id.price);
            fluctuation_value = $(R.id.fluctuation_value);
        }

        @Override
        public void setData(BasicStock data) {
            stock_name.setText(data.get_name());
            stock_code.setText(data.get_code());
            price.setText(String.format("%.2f",data.get_price()));
            fluctuation_value.setText(String.format("%.2f",data.get_range())+"%");
            if (data.get_range()<0){
                fluctuation_value.setTextColor(ContextCompat.getColor(mContext,R.color.cl_119d3e));
            }else if(data.get_range()>0){
                fluctuation_value.setTextColor(ContextCompat.getColor(mContext,R.color.cl_d63535));
            }
        }
    }

}
