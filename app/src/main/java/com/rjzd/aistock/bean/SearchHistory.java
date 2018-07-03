package com.rjzd.aistock.bean;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 2016/12/30.
 *
 * update by phs at 2017-01-13
 */

public class SearchHistory extends RealmObject implements Serializable {

    /**
     *   股票名称
     */

    private String stockname;
    /**
     *   股票代码
     */
    @PrimaryKey
    private String code;

    /**
     *    是否加入自选   0 否  1 是
     */
    private int hasAdd;

    // 操作时间  排序用
    private long createTime;

    public SearchHistory(){}

    public SearchHistory(String code,String name){
        this.code = code;
        this.stockname = name;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname;
    }

    public String getStockname() {
        return stockname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
