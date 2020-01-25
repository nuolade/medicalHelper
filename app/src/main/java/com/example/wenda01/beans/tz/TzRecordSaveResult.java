package com.example.wenda01.beans.tz;

public class TzRecordSaveResult {  //为服务器获得体质记录保存返回结果
    private String res;  //服务器返回结果标识
    private String msg;  //消息简介
    private C rec;  //内容

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

    public C getRec() {
        return rec;
    }

    public void setRec(C rec) {
        this.rec = rec;
    }

    public static class C{  //内容
        private int losetime_tz;  //保存数据
        private int id;  //记录id

        public int getLosetime_tz() {
            return losetime_tz;
        }

        public void setLosetime_tz(int losetime_tz) {
            this.losetime_tz = losetime_tz;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
