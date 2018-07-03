package com.rjzd.aistock.bean;

import java.io.Serializable;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Hition on 2016/12/30.
 */

@RealmClass
public class StockBasic extends RealmObject implements Serializable{

    // 股票代码
    @PrimaryKey
    private String code;

    // 股票名称 必填
    private String name;

    // 股票名首字母缩写
    private String abbreviation;

    // 当前股价
    private double latestPrice;

    // 开盘价
    private double open = -1;

    // 幅度
    private double range;

    // 是否已经添加自选股   0否  1是
    private int hasAdd;

    // 操作时间  排序用
    private long createTime;

    private int predictionResult = -3;

    private String predictDate;

    public StockBasic(){}

    public StockBasic(String code,String name){
        this.code = code;
        this.name = name;
    }

    public StockBasic(String name, String code, double latestPrice, double range) {
        this.name = name;
        this.code = code;
        this.latestPrice = latestPrice;
        this.range = range;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public int getHasAdd() {
        return hasAdd;
    }

    public void setHasAdd(int hasAdd) {
        this.hasAdd = hasAdd;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getPredictionResult() {
        return predictionResult;
    }

    public void setPredictionResult(int predictionResult) {
        this.predictionResult = predictionResult;
    }

    public String getPredictDate() {
        return predictDate;
    }

    public void setPredictDate(String predictDate) {
        this.predictDate = predictDate;
    }
}
