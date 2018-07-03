package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.RiseOrFallPrediction;
import com.rjzd.aistock.bean.StockBasic;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import java.util.List;
import java.util.Locale;


/**
 * 自选股 adapter
 * 股票代码   当前价格    涨跌幅
 * Created by Hition on 2016/12/30.
 */

public class StocksAdapter extends XMBaseAdapter<StockBasic> {

    public StocksAdapter(Context context) {
        super(context);
    }

    public List<StockBasic> getItems() {
        return super.mObjects;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExponentViewHolder(parent, R.layout.item_recycleview_zixuan);
    }

    private class ExponentViewHolder extends BaseViewHolder<StockBasic> {

        TextView stock_name;
        TextView stock_code;
        TextView date;
        TextView tv_price;
        TextView tv_range;
        ImageView ai;
        LinearLayout llAi;

        ExponentViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            stock_name = $(R.id.stock_name);
            stock_code = $(R.id.stock_code);
            date = $(R.id.date);
            tv_price = $(R.id.tv_price);
            tv_range = $(R.id.tv_range);
            ai = $(R.id.ai);
            llAi = $(R.id.ll_ai);
        }

        @Override
        public void setData(StockBasic data) {
            if (TextUtils.isEmpty(data.getName())) {
                return;
            }
            if (TextUtils.isEmpty(data.getCode())) {
                return;
            }

            stock_name.setText(data.getName());
            stock_code.setText(data.getCode());
            int preResult = data.getPredictionResult();
            if (RiseOrFallPrediction.RISE.getValue() == preResult) {
                llAi.setBackground(ContextCompat.getDrawable(mContext,R.drawable.zixuan_stock_raise_bg));
                ai.setImageResource(R.drawable.zixuan_ai_rise);
            } else if (RiseOrFallPrediction.FALL.getValue() == preResult) {
                llAi.setBackground(ContextCompat.getDrawable(mContext,R.drawable.zixuan_stock_fall_bg));
                ai.setImageResource(R.drawable.zixuan_ai_fall);
            } else if (RiseOrFallPrediction.REMAIN.getValue() == preResult) {
                llAi.setBackground(ContextCompat.getDrawable(mContext,R.drawable.zixuan_stock_flat_bg));
                ai.setImageResource(R.drawable.zixuan_ai_flat);
            } else {
                ai.setImageResource(0);
            }

            if ("".equals(data.getPredictDate())) {
                ai.setVisibility(View.GONE);
                date.setText("————");
                date.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                llAi.setBackground(ContextCompat.getDrawable(mContext,R.drawable.zixuan_stock_flat_bg));
            } else {
                ai.setVisibility(View.VISIBLE);
                date.setText(data.getPredictDate());
            }

            String cPrice = String.format(Locale.CHINA, "%.2f", data.getLatestPrice());
            Double open = Double.valueOf(data.getOpen());
            int openForInt = open.intValue();
            if(-2 == openForInt){
                tv_price.setTextColor(ContextCompat.getColor(mContext, R.color.cl_888888));
                tv_price.setText(cPrice);
                tv_range.setTextColor(ContextCompat.getColor(mContext, R.color.cl_888888));
                tv_range.setText(R.string.suspend);
            }else if(-1 == openForInt){
                tv_price.setText("");
                tv_range.setText("");
            }else if (0 == openForInt) {
                tv_price.setTextColor(ContextCompat.getColor(mContext, R.color.cl_888888));
                tv_price.setText("——");
                tv_range.setTextColor(ContextCompat.getColor(mContext, R.color.cl_888888));
                tv_range.setText("——");
            }else {
                tv_price.setText(cPrice);
                double rate = data.getRange() * 100;
                String rateValue = String.format(Locale.CHINA, "%.2f", rate) + "%";
                tv_range.setText(rateValue);

                if (rate > 0) {
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color.cl_d63535));
                    tv_range.setTextColor(ContextCompat.getColor(mContext, R.color.cl_d63535));
                } else if (rate < 0) {
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color.cl_119d3e));
                    tv_range.setTextColor(ContextCompat.getColor(mContext, R.color.cl_119d3e));
                } else {
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color.cl_333333));
                    tv_range.setTextColor(ContextCompat.getColor(mContext, R.color.cl_333333));
                }
            }

        }
    }


}
