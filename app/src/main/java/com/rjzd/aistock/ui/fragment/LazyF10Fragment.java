package com.rjzd.aistock.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.rjzd.aistock.api.CodeType;
import com.rjzd.aistock.presenter.F10Presenter;

/**
 * Created by Hition on 2017/3/27.
 */

public abstract class LazyF10Fragment extends LazyFragment{

    protected String stockCode;
    protected String stockName;
    protected CodeType ctype;
    protected F10Presenter presenter;
    protected Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new F10Presenter(this);
        bundle = getArguments();
        if (null != bundle) {
            stockCode = bundle.getString("stock_code");
            stockName = bundle.getString("stock_name");
        }
    }
    @Override
    protected int createViewById() {
        return createView();
    }

    @Override
    protected void lazyLoad() {}

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }

    /**
     *
     * @return layoutId
     */
    protected abstract int createView();
}
