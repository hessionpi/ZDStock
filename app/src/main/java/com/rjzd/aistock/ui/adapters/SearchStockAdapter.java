package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.rjzd.aistock.R;
import com.rjzd.aistock.bean.StockBasic;
import com.rjzd.aistock.model.AddPortfolioListener;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;


/**
 * Created by Administrator on 2017/1/3.
 */

public class SearchStockAdapter extends XMBaseAdapter<StockBasic> {

    private AddPortfolioListener listener;

    public SearchStockAdapter(Context context) {
        super(context);
    }

    public void setOnAddPortfolioListener(AddPortfolioListener listener){
        this.listener = listener;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchStockViewHolder(parent, R.layout.item_recycleview_match);
    }

    private class SearchStockViewHolder extends BaseViewHolder<StockBasic>{

        TextView item_stockname;
        TextView item_stockcode;
        ImageView addImg;
        TextView tvhasAdd;

        SearchStockViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            item_stockname = $(R.id.match_name);
            item_stockcode = $(R.id.match_code);
            addImg = $(R.id.match_add);
            tvhasAdd = $(R.id.match_has_add);
        }

        @Override
        public void setData(final StockBasic data) {
            if(TextUtils.isEmpty(data.getName())){
                return ;
            }
            if(TextUtils.isEmpty(data.getCode())){
                return ;
            }
            item_stockname.setText(data.getName());
            item_stockcode.setText(data.getCode());

            if(1 == data.getHasAdd()){
                addImg.setVisibility(View.GONE);
                tvhasAdd.setVisibility(View.VISIBLE);
            }else if(0 == data.getHasAdd()){
                addImg.setVisibility(View.VISIBLE);
                tvhasAdd.setVisibility(View.GONE);
            }

            addImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setVisibility(View.GONE);
                    tvhasAdd.setVisibility(View.VISIBLE);
                    data.setHasAdd(1);
                    listener.onAddClick(data);
                }
            });
        }
    }

}

