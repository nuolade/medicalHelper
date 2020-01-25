package com.example.wenda01.beans.qa;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class QaSession extends DataSupport implements Serializable {  //为本地存储的知识问答会话
    private String timeCreated;  //会话创建时间
    private String timeLast;  //会话中最后一条消息时间
    private String name;  //名称
    private String textLast;  //会话中最后一条消息内容
    private int no;  //会话编号
    private String phone;  //绑定手机号
    private int cnt;  //会话中已有消息问答对数目

    public QaSession() {
    }

    public QaSession(String timeCreated, String timeLast, String name, String textLast, int no, String phone, int cnt) {
        this.timeCreated = timeCreated;
        this.timeLast = timeLast;
        this.name = name;
        this.textLast = textLast;
        this.no = no;
        this.phone = phone;
        this.cnt = cnt;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getTimeLast() {
        return timeLast;
    }

    public void setTimeLast(String timeLast) {
        this.timeLast = timeLast;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextLast() {
        return textLast;
    }

    public void setTextLast(String textLast) {
        this.textLast = textLast;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}
