package com.rjzd.aistock.bean;

/**
 * Created by Administrator on 2017/2/27.
 */

public class RobatBean {
    String name;
    String messagecount;

    public RobatBean(String name, String messagecount) {
        this.name = name;
        this.messagecount = messagecount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessagecount(String messagecount) {
        this.messagecount = messagecount;
    }

    public String getName() {
        return name;
    }

    public String getMessagecount() {
        return messagecount;
    }
}
