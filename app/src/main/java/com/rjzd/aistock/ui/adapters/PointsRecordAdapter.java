package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.PointsRecord;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;

/**
 * Created by Hition on 2017/7/31.
 */

public class PointsRecordAdapter extends XMBaseAdapter<PointsRecord> {

    public PointsRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PointsRecordHolder(parent, R.layout.layout_item_points_record);
    }

    private class PointsRecordHolder extends BaseViewHolder<PointsRecord>{

        private TextView mEvent;
        private TextView mPoints;
        private TextView mDate;


        PointsRecordHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            mEvent = $(R.id.event_title);
            mPoints = $(R.id.points);
            mDate = $(R.id.date);
        }

        @Override
        public void setData(PointsRecord data) {
            mEvent.setText(data.get_event());
            if(data.get_points()>0){
                mPoints.setText("+" + data.get_points());
            }else{
                mPoints.setText(String.valueOf(data.get_points()));
            }
            mDate.setText(data.get_date());
        }
    }

}
