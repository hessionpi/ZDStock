package com.rjzd.aistock;

import com.rjzd.aistock.bean.StockBasic;

import java.util.Comparator;

/**
 * 排序 按照code升序排
 *
 * Created by Hition on 2017/6/30.
 */

public class CompareCode implements Comparator<StockBasic> {

    boolean is_Ascend ;

    public CompareCode(boolean b) {
        // TODO Auto-generated constructor stub
        is_Ascend = b;
    }

    @Override
    public int compare(StockBasic o1, StockBasic o2) {
        // TODO Auto-generated method stub
        if (is_Ascend){
            return o1.getCode().compareTo(o2.getCode());
        }else{
            return o2.getCode().compareTo(o1.getCode());
        }
    }
}
