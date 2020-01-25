package com.example.wenda01.beans.tz;

public class TzRecordFirstResult {  //为从服务器获得记录时的初始检测记录
    private String res;  //服务器返回结果标识
    private String msg;  //服务器返回结果标识

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
