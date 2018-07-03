package com.rjzd.aistock.model;

/**
* 一天的起始毫秒值，结束毫秒值
* @author wuhai
* create at 2016/4/18 11:03
*/
public class TimeInfo {
    private long startTime;
    private long endTime;


    public long getStartTime() {
        return this.startTime;
    }


    public void setStartTime(long paramLong) {
        this.startTime = paramLong;
    }


    public long getEndTime() {
        return this.endTime;
    }


    public void setEndTime(long paramLong) {
        this.endTime = paramLong;
    }
}
