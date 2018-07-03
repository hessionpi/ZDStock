package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.bean.FundamentalItem;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import java.util.List;


public class FundamentalAdapter extends XMBaseAdapter<FundamentalItem>{

    public FundamentalAdapter(Context context, List<FundamentalItem> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FundamentalViewHolder(parent,R.layout.item_fundamental);
    }

    private class FundamentalViewHolder extends BaseViewHolder<FundamentalItem> {

        private TextView mRank;
        private TextView mName;
        private ProgressBar mPB;
        private TextView mValue;

        public FundamentalViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            mRank = $(R.id.tv_rank);
            mName = $(R.id.tv_fundamental_name);
            mPB = $(R.id.pb_fundamental);
            mValue = $(R.id.tv_fundamental_value);
        }

        @Override
        public void setData(FundamentalItem data) {
            mRank.setText(data.getRank());
            mName.setText(data.getName());
            mPB.setProgress(data.getProcess());
            mValue.setText(data.getValue());
            if(TextUtils.isEmpty(data.getRank().trim())){
                mRank.setBackgroundResource(R.color.white);
            }else{
                mRank.setBackgroundResource(R.drawable.ic_rank);
            }

            Drawable dw = null;
            switch(data.getType()){
                case Constants.FUNDAMENTALS_AVG:
                    dw = ContextCompat.getDrawable(mContext,R.drawable.progressbar_fundamentals_avg_color);
                    break;

                case Constants.FUNDAMENTALS_SELF:
                    dw = ContextCompat.getDrawable(mContext,R.drawable.progressbar_fundamentals_color);
                    break;

                case Constants.FUNDAMENTALS_FIRST:
                    dw = ContextCompat.getDrawable(mContext,R.drawable.progressbar_fundamentals_first_color);
                    break;

                case Constants.FUNDAMENTALS_LAST:
                    dw = ContextCompat.getDrawable(mContext,R.drawable.progressbar_fundamentals_last_color);
                    break;
            }
            mPB.setProgressDrawable(dw);
        }
    }


}
