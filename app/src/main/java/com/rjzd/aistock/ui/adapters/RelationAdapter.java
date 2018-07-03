package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.RelatedStock;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;

/**
 * Created by Administrator on 2017/5/27.
 */

public class RelationAdapter  extends XMBaseAdapter<RelatedStock>{

    public RelationAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RelationHolder(parent, R.layout.item_recycler_relation);
    }

    class RelationHolder extends BaseViewHolder<RelatedStock>{
        private ImageView mTrend;
        private TextView mStock;
        private TextView mCode;
        private View line;
        private LinearLayout relation;


        public RelationHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            mTrend = $(R.id.iv_trend);
            mStock = $(R.id.tv_stock);
            mCode = $(R.id.tv_code);
            relation = $(R.id.relation);
            line=$(R.id.line);

        }

        @Override
        public void setData(RelatedStock data) {
            if(null == data){
                return ;
            }

            mStock.setText(data.get_name());
            mCode.setText(data.get_code());
            if(data.get_range()>=0){
                mTrend.setImageResource(R.drawable.relation_raise);
                line.setBackgroundColor(ContextCompat.getColor(mContext,R.color.cl_d63535));
                relation.setBackgroundResource(R.drawable.shape_relation_red_bg);
            }else{
                mTrend.setImageResource(R.drawable.relation_fall);
                line.setBackgroundColor(ContextCompat.getColor(mContext,R.color.cl_119d3e));
                relation.setBackgroundResource(R.drawable.shape_relation_green_bg);
            }
        }

    }
}
