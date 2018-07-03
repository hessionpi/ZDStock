package com.rjzd.aistock.bean;

import org.xclcharts.chart.BarData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hition on 2017/3/9.
 */

public class MarketBarData implements Serializable {

    private BarData barData;

    private List<String> barLabels;

    private List<String> codes;

    public MarketBarData(){}

    public MarketBarData(BarData barData, List<String> barLabels) {
        this.barData = barData;
        this.barLabels = barLabels;
    }

    public MarketBarData(BarData barData, List<String> barLabels, List<String> codes) {
        this.barData = barData;
        this.barLabels = barLabels;
        this.codes = codes;
    }

    public BarData getBarData() {
        return barData;
    }

    public void setBarData(BarData barData) {
        this.barData = barData;
    }

    public List<String> getBarLabels() {
        return barLabels;
    }

    public void setBarLabels(List<String> barLabels) {
        this.barLabels = barLabels;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }
}
