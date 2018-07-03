package com.rjzd.aistock.bean;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class ChartData {
    LinkedHashMap<String, Float> map;
    List<Integer> list;
List<String> num;


    public void setMap(LinkedHashMap<String, Float> map) {
        this.map = map;
    }

    public LinkedHashMap<String, Float> getMap() {
        return map;
    }

    public List<String> getNum() {
        return num;
    }

    public void setNum(List<String> num) {
        this.num = num;
    }



    public List<Integer> getList() {
        return list;
    }



    public void setList(List<Integer> list) {
        this.list = list;
    }


}
