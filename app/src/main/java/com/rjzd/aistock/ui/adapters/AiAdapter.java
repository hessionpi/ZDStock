package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rjzd.aistock.R;
import com.rjzd.aistock.api.AIIncomeInfo;
import com.rjzd.aistock.api.AIInfo;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.commonlib.imageloader.ZDImgloader;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */

public class AiAdapter extends XMBaseAdapter<AIInfo> {
    int currentItem = 0;
    List<String> idList;

    public AiAdapter(Context context) {
        super(context);
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new AiHolder(parent, R.layout.item_recycler_ai);
    }

    private class AiHolder extends BaseViewHolder<AIInfo> {
        LinearLayout ai_unfold;
        ImageView unfold_img_ai;
        TextView unfold_ai_name;
        TextView unfold_mIncome;
        TextView unfold_mSymbol;
        TextView unfold_percent;
        TextView unfold_followed;
        TextView online_date;
        TextView tv_chara_label;
        TextView tv_introduce;


        AiHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);

            ai_unfold = $(R.id.ai_unfold);
            unfold_img_ai = $(R.id.unfold_img_ai);
            unfold_ai_name = $(R.id.unfold_ai_name);
            unfold_mIncome = $(R.id.unfold_income_total);
            unfold_mSymbol = $(R.id.unfold_income_symbol);
            unfold_percent = $(R.id.unfold_income_percent);
            unfold_followed = $(R.id.unfold_followed);
            online_date = $(R.id.online_date);
            tv_chara_label = $(R.id.tv_chara_label);
            tv_introduce = $(R.id.tv_introduce);
        }

        @Override
        public void setData(final AIInfo data) {
            if (null == data) {
                return;
            }
            ZDImgloader.load(mContext, data.get_avatar(), R.drawable.ai_avatar_default, R.drawable.ai_avatar_default, unfold_img_ai);
            unfold_ai_name.setText(data.get_name());
            AIIncomeInfo income = data.get_totalIncome();
            unfold_mIncome.setText(Math.abs(income.get_achievement()) + "");
            if (income.get_achievement() < 0) {
                unfold_mSymbol.setText("-");
                unfold_mSymbol.setTextColor(ContextCompat.getColor(mContext, R.color.cl_119d3e));
                unfold_mIncome.setTextColor(ContextCompat.getColor(mContext, R.color.cl_119d3e));
                unfold_percent.setTextColor(ContextCompat.getColor(mContext, R.color.cl_119d3e));
            } else {
                unfold_mSymbol.setText("+");
                unfold_mSymbol.setTextColor(ContextCompat.getColor(mContext, R.color.cl_d63535));
                unfold_mIncome.setTextColor(ContextCompat.getColor(mContext, R.color.cl_d63535));
                unfold_percent.setTextColor(ContextCompat.getColor(mContext, R.color.cl_d63535));
            }
            online_date.setText("生日:  " + data.get_onlineTime());
            StringBuilder characters = new StringBuilder();
            if (!data.get_characteristics().isEmpty()) {
                for (String cha : data.get_characteristics()) {
                    characters.append(cha + "     ");
                }
            }
            tv_chara_label.setText(characters.toString());
            tv_introduce.setText(data.get_introduce());
            if (idList != null) {
                String aiId = data.get_id();
                if (idList.contains(aiId)) {
                  //  unfold_followed.setVisibility(View.VISIBLE);
                    unfold_followed.setBackground(ContextCompat.getDrawable(mContext,R.drawable.icon_yiguanzhu));
                    unfold_followed.setText("已关注");
                } else {
                    //unfold_followed.setVisibility(View.GONE);
                    unfold_followed.setBackground(ContextCompat.getDrawable(mContext,R.drawable.icon_weiguanzhu));
                    unfold_followed.setText("未关注");
                }
            }

            }



    }
}

