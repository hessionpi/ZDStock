package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.Announcement;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.commonlib.imageloader.ZDImgloader;

/**
 * Created by Administrator on 2017/7/24.
 */

public class MessageNotificationAdapter extends XMBaseAdapter<Announcement> {

    public MessageNotificationAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageNotificationHolder(parent,R.layout.item_recycler_messagenotification);
    }

    private class MessageNotificationHolder extends BaseViewHolder<Announcement> {

        private ImageView mNewsImg;
        private TextView mNewsTitle;

        public MessageNotificationHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            mNewsImg = $(R.id.news_img);
            mNewsTitle = $(R.id.news_title);
        }

        @Override
        public void setData(Announcement data) {
            ZDImgloader.load(mContext,data.get_imageUrl(),0,0,mNewsImg);
//            ZDImgloader.loadTransformImage(mContext,data.get_imageUrl(),0,0,mNewsImg,5);
            mNewsTitle.setText(data.get_title());
            if(0 == data.get_hasRead()){
                mNewsTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shape_bg_circle,0,0,0);
            }else{
                mNewsTitle.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            }
        }
    }

}

