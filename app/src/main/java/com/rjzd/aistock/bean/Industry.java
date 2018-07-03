package com.rjzd.aistock.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 行业
 *
 * Created by Hition on 2017/2/14.
 */

public class Industry implements Serializable {

    private int code;

    private List<IndustryData> data;

    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<IndustryData> getData() {
        return data;
    }

    public void setData(List<IndustryData> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class IndustryData{
        private int id;

        private String industry;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }


    }

}
