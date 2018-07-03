package com.rjzd.aistock.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hition on 2017/6/6.
 */

public class ConversionUtils {

    /**
     * 将某一数字转换位[0,1]内的数字
     */
    public static List<Double> transport(List<Double> inputData) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (Double e : inputData) {
            if (e != null && !Double.isNaN(e)) {
                if (e < min) {
                    min = e;
                }
                if (e > max) {
                    max = e;
                }
            }
        }

        List<Double> newData = new ArrayList<>();
        for (Double e : inputData) {
            if (e != null && !Double.isNaN(e)) {
                newData.add(doubleRound((e - min) / (max - min))+1);
            } else {
                newData.add(e);
            }
        }
        return newData;
    }

    /**
     *  把double数字四舍五入保留两位小数
     * @param inputNumber        输入数字
     * @return double
     */
    private static double doubleRound(Double inputNumber) {
        if (inputNumber == null) {
            return Double.NaN;
        }
        if (Double.isNaN(inputNumber)) {
            return Double.NaN;
        }
        BigDecimal bigDecimal = new BigDecimal(inputNumber);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


}
