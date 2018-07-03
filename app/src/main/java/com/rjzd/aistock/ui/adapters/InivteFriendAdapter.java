package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rjzd.aistock.R;
import com.rjzd.aistock.api.Invite;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.commonlib.imageloader.ZDImgloader;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class InivteFriendAdapter extends XMBaseAdapter<Invite> {
    public InivteFriendAdapter(Context context) {
        super(context);
    }

    public InivteFriendAdapter(Context context, List<Invite> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new InivteFriendHolder(parent, R.layout.item_recycler_invitefriend);
    }

    private class InivteFriendHolder extends BaseViewHolder<Invite> {
        ImageView iv_avatar;
        TextView tv_nickname;
        TextView tv_registertime;


        public InivteFriendHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            iv_avatar = $(R.id.iv_avatar);
            tv_nickname = $(R.id.tv_nickname);
            tv_registertime = $(R.id.tv_registertime);

        }

        @Override
        public void setData(Invite data) {
            ZDImgloader.loadTransformImage(mContext, data.get_avatar(), R.drawable.ic_avatar_login, R.drawable.ic_avatar_login, iv_avatar, 0);
            tv_nickname.setText(data.get_nickName());
            tv_registertime.setText(data.get_registerTime());
        }
    }
}
