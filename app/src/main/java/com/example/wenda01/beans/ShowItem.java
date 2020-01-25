package com.example.wenda01.beans;

public class ShowItem {  //为显示的每一项
    private String name;  //名称
    private String content;  //内容

    public ShowItem(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
