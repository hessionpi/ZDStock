package com.rjzd.aistock.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.rjzd.aistock.presenter.StockDetailPresenter;
import com.rjzd.aistock.utils.SPUtils;
import com.rjzd.aistock.view.IFillDataView;

/**
 * Created by Hition on 2016/12/20.
 */

public class SplashActivity extends Activity implements IFillDataView {

    private StockDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StockDetailPresenter(this);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                init();
            }
        }, 1000);
    }

    private void init() {
        boolean is_first = (boolean)SPUtils.get("is_first",true);
        if(is_first){
            //第一次进入，进入导航页，is_first改成false
            GuideActivity.startActivity(this);
            SPUtils.put( "is_first", false);

            // 插入默认的三只股票
            presenter.addStock("002415","海康威视");
            presenter.addStock("000651","格力电器");
            presenter.addStock("601857","中国石油");
        }else{
            //第二次进入，进入主页
            MainActivity.startActivity(this);
        }
        finish();
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void fillData(Object data, int dsTag) {

    }

    @Override
    public void showFailedView() {

    }
}