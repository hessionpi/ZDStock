package com.rjzd.aistock.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rjzd.aistock.api.RangeFlag;

/**
 * Created by Administrator on 2017/1/12.
 * 跌幅榜
 */

public class FallCrunchiesFragment extends RiseCrunchiesFragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rf = RangeFlag.RANGE_FALL;
    }

    @Override
    public void setStatistical() {
        statistical = "fall_list";
    }
}
