package com.rjzd.aistock.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 概念
 *
 * Created by Hition on 2017/2/14.
 */

public class Concept implements Serializable {

    private int code;

    private List<ConceptData> data;

    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ConceptData> getData() {
        return data;
    }

    public void setData(List<ConceptData> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class ConceptData{
        private int id;

        private String concept;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getConcept() {
            return concept;
        }

        public void setConcept(String concept) {
            this.concept = concept;
        }

    }


}
