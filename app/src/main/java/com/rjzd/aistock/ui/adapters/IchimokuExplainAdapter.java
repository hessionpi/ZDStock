package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rjzd.aistock.R;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;

import java.util.List;

import static com.rjzd.aistock.Constants.ICHIMOKU_EXPLAIN_BAD;
import static com.rjzd.aistock.Constants.ICHIMOKU_EXPLAIN_GOOD;

/**
 * Created by Administrator on 2017/6/1.
 */

public class IchimokuExplainAdapter extends XMBaseAdapter {
int dsTag;
    public IchimokuExplainAdapter(Context context) {
        super(context);
    }

    public IchimokuExplainAdapter(Context context, List objects,int dsTag) {
        super(context, objects);
        this.dsTag=dsTag;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new IchimokuGoodHolder(parent, R.layout.item_recycler_ichimoku);
    }

    class IchimokuGoodHolder extends BaseViewHolder<String> {
        private TextView tv_explain;
        private LinearLayout ll_explain;

        public IchimokuGoodHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            tv_explain=$(R.id.tv_explain);
            ll_explain=$(R.id.ll_explain);
        }

        @Override
        public void setData(String data) {
            if (dsTag==ICHIMOKU_EXPLAIN_GOOD){
                ll_explain.setBackgroundResource(R.drawable.shape_red_bg);
                tv_explain.setTextColor(ContextCompat.getColor(mContext,R.color.cl_d63535));
            }else if(dsTag==ICHIMOKU_EXPLAIN_BAD){
                ll_explain.setBackgroundResource(R.drawable.shape_green_bg);
                tv_explain.setTextColor(ContextCompat.getColor(mContext,R.color.cl_119d3e));
            }else {
                ll_explain.setBackgroundResource(R.drawable.shape_ichimoku_gray_bg);
                tv_explain.setTextColor(ContextCompat.getColor(mContext,R.color.cl_555555));
            }
            tv_explain.setText(data);

        }

    }
}
