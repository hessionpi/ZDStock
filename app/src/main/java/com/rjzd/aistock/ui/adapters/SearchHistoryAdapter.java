package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.rjzd.aistock.R;
import com.rjzd.aistock.bean.SearchHistory;
import com.rjzd.aistock.bean.StockBasic;
import com.rjzd.aistock.model.AddPortfolioListener;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;


/**
 * by phs at 2017-1-13
 */
public class SearchHistoryAdapter extends XMBaseAdapter<SearchHistory> {

    private AddPortfolioListener listener;

    public SearchHistoryAdapter(Context context) {
        super(context);
    }

    public void setOnAddPortfolioListener(AddPortfolioListener listener){
        this.listener = listener;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(parent, R.layout.item_recycleview_search_history);
    }

    private class SearchViewHolder extends BaseViewHolder<SearchHistory>{

        TextView item_stockname;
        TextView item_stockcode;
        ImageView item_addzixuan_img;
        TextView item_addzixuan_tv;

        SearchViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            item_stockname = $(R.id.item_stockname);
            item_stockcode = $(R.id.item_stockcode);
            item_addzixuan_img = $(R.id.iv_add);
            item_addzixuan_tv = $(R.id.tv_has_add);
        }

        @Override
        public void setData(final SearchHistory data) {
            if (TextUtils.isEmpty(data.getStockname())) {
                return;
            }
            if (TextUtils.isEmpty(data.getCode())) {
                return;
            }
            item_stockname.setText(data.getStockname());
            item_stockcode.setText(data.getCode());

            if(1 == data.getHasAdd()){
                item_addzixuan_img.setVisibility(View.GONE);
                item_addzixuan_tv.setVisibility(View.VISIBLE);
            }else if(0 == data.getHasAdd()){
                item_addzixuan_img.setVisibility(View.VISIBLE);
                item_addzixuan_tv.setVisibility(View.GONE);
            }

            item_addzixuan_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setVisibility(View.GONE);
                    item_addzixuan_tv.setVisibility(View.VISIBLE);
                    StockBasic stock = new StockBasic(data.getCode(),data.getStockname());
                    stock.setHasAdd(1);
                    listener.onAddClick(stock);
                }
            });
        }

    }

}
