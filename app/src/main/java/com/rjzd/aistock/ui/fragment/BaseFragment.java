package com.rjzd.aistock.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.rjzd.aistock.R;
import com.rjzd.aistock.cache.ACache;
import com.rjzd.aistock.ui.activity.BaseActivity;
import com.rjzd.aistock.utils.StatisticalTools;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.ToastUtils;


/**
 * Created by Hition on 2016/12/21.
 */

public abstract class BaseFragment extends Fragment implements IFillDataView {

    protected BaseActivity activity;
    public ACache mCache;
    protected String statistical = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
        mCache = ACache.get(activity,"zixuan");
        setStatistical();
    }

    @Override
    public void onResume() {
        super.onResume();
        StatisticalTools.fragmentOnResume(statistical);
    }

    @Override
    public void onPause() {
        super.onPause();
        StatisticalTools.fragmentOnPause(statistical);
    }

    /**
     * 给统计赋值
     */
    public abstract void setStatistical();

    @Override
    public void fillData(Object data,int dsTag) {
        dismissLoading();
    }

    @Override
    public void showFailedView() {
        // 获取网络数据失败  显示加载失败view
        dismissLoading();
    }

    protected void showLoadingDialog() {
        activity.showLoadingView();
    }

    protected void dismissLoading() {
        activity.dismissLoading();
    }

    @Override
    public void showToast(String msg) {
        activity.showToast(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
