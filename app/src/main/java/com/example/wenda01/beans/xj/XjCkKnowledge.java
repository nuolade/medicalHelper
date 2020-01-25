package com.example.wenda01.beans.xj;

import org.litepal.crud.DataSupport;

public class XjCkKnowledge extends DataSupport {  //为本地存储的宣教知识
    private String title;  //知识名称
    private String content;  //知识内容
    private String label;  //知识标签（现无用，为标记内容的表现形式）
    private String type;  //知识类别

    public XjCkKnowledge() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public XjCkKnowledge(String title, String content, String label, String type) {
        this.title = title;
        this.content = content;
        this.label = label;
        this.type = type;
    }
}
