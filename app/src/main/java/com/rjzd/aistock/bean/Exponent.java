package com.rjzd.aistock.bean;

/** 股票指数实体
 * Created by Hition on 2016/12/29.
 */

public class Exponent {

    /**
     * 指数名称
     */
    private String name;

    /**
     * 0 下跌   1 上涨
     */
    private int flag;

    /**
     * 涨跌幅 波动
     */
    private String fluctuation;

    /**
     * 收盘价格
     * @return
     */
    private String closePrice;

    public Exponent(String name, String closePrice,String fluctuation,int flag) {
        this.name = name;
        this.fluctuation = fluctuation;
        this.closePrice = closePrice;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFluctuation() {
        return fluctuation;
    }

    public void setFluctuation(String fluctuation) {
        this.fluctuation = fluctuation;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
