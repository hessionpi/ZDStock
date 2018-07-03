package com.rjzd.commonlib.utils;

import java.math.BigDecimal;

/**
 * Created by Hition on 2016/12/8.
 *
 * 去除double类型数字尾巴的0
 */

public class MathDataUtil {

    public static BigDecimal stripTrailingZeros(double d) {//去除double尾巴的0
        return new BigDecimal(d).stripTrailingZeros();
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
