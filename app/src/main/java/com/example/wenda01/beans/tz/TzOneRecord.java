package com.example.wenda01.beans.tz;

import org.litepal.crud.DataSupport;

public class TzOneRecord extends DataSupport {  //为本地存储的单选体质查询记录
    private int group;  //所属类别
    private int score;  //分数
    private String phone;  //手机号
    private String time;  //保存时间

    public TzOneRecord(int group, int score, String phone, String time) {
        this.group = group;
        this.score = score;
        this.phone = phone;
        this.time = time;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
