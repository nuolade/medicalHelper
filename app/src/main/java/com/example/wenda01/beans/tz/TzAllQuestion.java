package com.example.wenda01.beans.tz;

import org.litepal.crud.DataSupport;

public class TzAllQuestion extends DataSupport {  //为本地存储的综合体质问题（因模块停用）
    private String text;  ///问题内容
    private int idd;  //问题id
    private int groupId;  //所属类别
    private int sameId;  //相同的问题编号
    private int isK;  //是否为关键问题
    private int isR;  //是否为反向问题

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSameId() {
        return sameId;
    }

    public void setSameId(int sameId) {
        this.sameId = sameId;
    }

    public int getIsR() {
        return isR;
    }

    public void setIsR(int isR) {
        this.isR = isR;
    }

    public int getIsK() {
        return isK;
    }

    public void setIsK(int isK) {
        this.isK = isK;
    }

    public TzAllQuestion(String text, int idd, int groupId, int sameId, int isK, int isR) {
        this.text = text;
        this.idd = idd;
        this.groupId = groupId;
        this.sameId = sameId;
        this.isK=isK;
        this.isR = isR;
    }
}
