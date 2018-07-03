package com.rjzd.commonlib.datarequest;

/**
 * Created by Hition on 2016/12/21.
 *
 *  数据返回的基类
 *
 *  code    响应码
 *  msg     响应消息
 *  data    数据体
 */

public class BaseData<T> {

    private int code=-1;

    private String msg;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
