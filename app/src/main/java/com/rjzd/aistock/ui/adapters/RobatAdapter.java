package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.AIInfo;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.commonlib.imageloader.ZDImgloader;

/**
 * Created by Administrator on 2017/2/27.
 */

public class RobatAdapter extends XMBaseAdapter {

    public RobatAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsHolder(parent, R.layout.item_personal_center_robot);
    }

    private class NewsHolder extends BaseViewHolder<AIInfo> {
        private TextView name;
        private ImageView avatar;

        NewsHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            name = $(R.id.robat_name);
            avatar = $(R.id.robat_im);
        }

        @Override
        public void setData(AIInfo data) {
            name.setText(data.get_name());
            ZDImgloader.loadTransformImage(getContext(),data.get_avatar(),R.drawable.ai_avatar_default,R.drawable.ai_avatar_default,avatar,0);
        }

    }
}
