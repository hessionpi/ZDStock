package com.rjzd.aistock.bean;

import java.io.Serializable;

/**
 * Created by Hition on 2017/6/5.
 */

public class FundamentalItem implements Serializable {

    private String rank;
    private String name;
    private int process;
    private String value;
    private int type;


    public FundamentalItem(String rank,String name, int process, String value,int type) {
        this.rank = rank;
        this.name = name;
        this.process = process;
        this.value = value;
        this.type = type;
    }

    public String getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }
}
