package com.rjzd.aistock.presenter.listener;


/**
 * Created by Hition on 2016/12/9.
 */

public interface BaseListener {

    // 获取数据或提交成功
    void onSuccess(Object data);

    // 获取数据失败或提交失败
    void onFailed(Throwable e);

}
