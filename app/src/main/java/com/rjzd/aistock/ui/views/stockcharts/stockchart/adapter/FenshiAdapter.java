package com.rjzd.aistock.ui.views.stockcharts.stockchart.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.FivePosition;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;

/**
 * Created by Administrator on 2017/1/9.
 */

public class FenshiAdapter extends XMBaseAdapter {
    public FenshiAdapter(Context context) {
        super(context);
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FenshiHolder(parent, R.layout.item_recycle_deal);
    }
    class FenshiHolder extends BaseViewHolder<FivePosition> {
        TextView item_deal;
        TextView item_price;
        TextView item_num;

        public FenshiHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            item_deal = $(R.id.item_deal);
            item_price = $(R.id.item_price);
            item_num = $(R.id.item_num);
        }

        @Override
        public void setData(FivePosition data) {
            item_deal.setText(data.get_action());
           double price= data.get_price();
            item_price.setText(String.format("%.2f",price ));
            item_num.setText(data.get_volume()+"");
           if (Constants.last>price){
               item_price.setTextColor(ContextCompat.getColor(mContext,R.color.cl_fall));
           }else if (Constants.last<price){
               item_price.setTextColor(ContextCompat.getColor(mContext,R.color.cl_raise));
           }else{

           }

        }
    }
}
