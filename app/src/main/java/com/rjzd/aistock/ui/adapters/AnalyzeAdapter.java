package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.BasicCategory;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;


/**
 * Created by Hition on 2017/3/30.
 */

public class AnalyzeAdapter extends XMBaseAdapter<BasicCategory> {

    private String reportDate ;
    private Context mContext;

    public AnalyzeAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new AnalyzeViewHolder(parent, R.layout.layout_item_analyze);
    }

    private class AnalyzeViewHolder extends BaseViewHolder<BasicCategory>{

        TextView mCategoryView;
        TextView reportDateView;
        RecyclerView mOptionsView;

        public AnalyzeViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            mCategoryView = $(R.id.category);
            reportDateView = $(R.id.reportDate);
            mOptionsView = $(R.id.rv_options);
        }

        @Override
        public void setData(BasicCategory data) {
            mCategoryView.setText(data.get_category());
            reportDateView.setText(reportDate);
            LinearLayoutManager layoutmanager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            mOptionsView.setLayoutManager(layoutmanager);
            NameValuePairsAdapter optionAdapter = new NameValuePairsAdapter(mContext,data.get_analysisOptions());
            mOptionsView.setAdapter(optionAdapter);
        }
    }
}
