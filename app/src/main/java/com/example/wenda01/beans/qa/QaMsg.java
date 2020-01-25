package com.example.wenda01.beans.qa;

import org.litepal.crud.DataSupport;

public class QaMsg extends DataSupport {  //为本地存储的知识问答消息
    private String time;  //消息时间
    private String text;  //内容
    private String phone;  //绑定电话
    private int session;  //绑定会话编号
    private int type;  //消息类别（区分接收和发送）
    private int no;  //消息的标号（用于接收和发送关联）

    public QaMsg(String time, String text, String phone, int session, int type, int no) {
        this.time = time;
        this.text = text;
        this.phone = phone;
        this.session = session;
        this.type = type;
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
