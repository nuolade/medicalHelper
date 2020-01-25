package com.example.wenda01.beans.tz;

import org.litepal.crud.DataSupport;

public class TzOneQuestion extends DataSupport {  //为本地存储的单项体质问题
    private String text;  //问题内容
    private int idd;  //问题id
    private int groupId;  //所属类别

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

    public TzOneQuestion(String text, int idd, int groupId) {
        this.text = text;
        this.idd = idd;
        this.groupId = groupId;

    }
}
