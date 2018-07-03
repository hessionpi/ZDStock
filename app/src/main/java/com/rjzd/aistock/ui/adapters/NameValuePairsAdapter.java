package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.NameValuePairs;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import java.util.List;

/**
 * Created by Hition on 2017/3/30.
 */

public class NameValuePairsAdapter extends XMBaseAdapter<NameValuePairs> {

    public NameValuePairsAdapter(Context context) {
        super(context);
    }

    public NameValuePairsAdapter(Context context, List<NameValuePairs> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new AnalyzeOptionHolder(parent, R.layout.layout_analyze_option_item);
    }

    private class AnalyzeOptionHolder extends BaseViewHolder<NameValuePairs>{

        TextView mOptionView;
        TextView mOptionValueView;

        public AnalyzeOptionHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            mOptionView = $(R.id.option_name);
            mOptionValueView = $(R.id.option_value);
        }

        @Override
        public void setData(NameValuePairs data) {
            mOptionView.setText(data.get_name());
            mOptionValueView.setText(data.get_value()+data.get_unit());
        }
    }
}
