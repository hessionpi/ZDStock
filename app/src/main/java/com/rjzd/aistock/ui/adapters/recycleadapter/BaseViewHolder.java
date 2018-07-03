package com.rjzd.aistock.ui.adapters.recycleadapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hition on 2016/12/9.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
    }

    abstract public void setData(T data);

    protected <V extends View> V $(@IdRes int id) {
        return (V) itemView.findViewById(id);
    }

    protected Context getContext(){
        return itemView.getContext();
    }
}
