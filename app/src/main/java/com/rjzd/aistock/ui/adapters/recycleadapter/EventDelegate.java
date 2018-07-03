package com.rjzd.aistock.ui.adapters.recycleadapter;

import android.view.View;

/**
 * Created by Hition on 2016/12/9.
 */

public interface EventDelegate {
    void addData(int length);
    void clear();

    void stopLoadMore();
    void pauseLoadMore();
    void resumeLoadMore();

    void setMore(View view, XMBaseAdapter.OnLoadMoreListener listener);
    void setNoMore(View view);
    void setErrorMore(View view);
}