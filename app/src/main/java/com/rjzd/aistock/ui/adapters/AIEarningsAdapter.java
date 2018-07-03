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
import com.rjzd.aistock.ui.views.stockcharts.stockchart.utils.DateUtil;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/16.
 */

public class AIEarningsAdapter extends XMBaseAdapter<AIOperation> {


    public AIEarningsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new AIEarningsHistoryHolder(parent, R.layout.item_recycle_ai_learning);
    }

    private class AIEarningsHistoryHolder extends BaseViewHolder<AIOperation> {
        TextView name;
        TextView code;
        TextView range;
        TextView tv_time;
        TextView tv_data;

        AIEarningsHistoryHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            name=$(R.id.tv_name);
            code=$(R.id.tv_code);
            range=$(R.id.tv_range);
            tv_time=$(R.id.tv_time);
            tv_data=$(R.id.tv_data);
        }

        @Override
        public void setData(AIOperation data) {
            name.setText(data.get_stockName());
            code.setText(data.get_stockCode());
            range.setText(String.format("%.2f", data.get_profitPercentage())+"%");
            Date buyTime = DateUtil.parser(data.get_buyTime());
            String formatBuyTime = DateUtil.formatDate(buyTime,"yyyy-MM-dd");
            tv_data.setText(formatBuyTime);
            tv_time.setText(data.get_aiProfitPoints_size()+"天");

            if (data.get_profitPercentage()>0){
                range.setTextColor(ContextCompat.getColor(mContext, R.color.cl_f95555));
            }else if(data.get_profitPercentage()<0){
                range.setTextColor(ContextCompat.getColor(mContext, R.color.cl_119d3e));
            }else{
                range.setTextColor(ContextCompat.getColor(mContext, R.color.cl_333333));
            }
        }

    }
    }

