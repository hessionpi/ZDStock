package com.rjzd.aistock.ui.views.stockcharts.stockchart.bean;

/**
 * Created by Administrator on 2017/1/9.
 */

public class Deal {
    private String deal;
    private String price;
    private String num;

    public Deal(String deal, String price, String num) {
        this.deal = deal;
        this.price = price;
        this.num = num;
    }

    public String getDeal() {
        return deal;
    }

    public String getPrice() {
        return price;
    }

    public String getNum() {
        return num;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
