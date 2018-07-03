package com.rjzd.aistock.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

/** 防止fragemt预加载
 * Created by Hition on 2017/3/27.
 */

public abstract class LazyFragment extends BaseFragment {

    /** Fragment当前状态是否可见 */
    protected boolean isVisible;

    /**
     * 标志位，标志已经初始化完成
     */
    protected boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    protected boolean mHasLoadedOnce;

    protected LayoutInflater mInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        View mView = inflater.inflate(createViewById(),container,false);
        ButterKnife.bind(this,mView);
        initView();
        isPrepared = true;
        lazyLoad();
        return mView;
    }

    protected  void initView(){}

    //setUserVisibleHint  adapter中的每个fragment切换的时候都会被调用，如果是切换到当前页，那么isVisibleToUser==true，否则为false
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible(){}

    /**
     *
     * @return layoutId
     */
    protected abstract int createViewById();

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
