package com.example.wenda01.beans.tz;

import org.litepal.crud.DataSupport;

public class TzOneIntorduction extends DataSupport {  //为本地存储的单选体质介绍
    private int groupId;  //体质编号
    private String text;  //介绍内容

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TzOneIntorduction(int groupId, String text) {
        this.groupId = groupId;

        this.text = text;
    }
}
