package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjzd.aistock.R;
import com.rjzd.aistock.api.AIWeekly;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;

/**
 * Created by Administrator on 2017/8/8.
 */

public class AIWeeklyAdapter extends XMBaseAdapter<AIWeekly> {

    public AIWeeklyAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new AIWeeklyHolder(parent, R.layout.item_recycler_week_new);
    }

    private class AIWeeklyHolder extends BaseViewHolder<AIWeekly> {

        private TextView mWeekly;

        AIWeeklyHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            mWeekly = $(R.id.tv_weekly);
        }

        @Override
        public void setData(AIWeekly data) {
            mWeekly.setText(data.get_weeklyTitle());
        }

    }
}
