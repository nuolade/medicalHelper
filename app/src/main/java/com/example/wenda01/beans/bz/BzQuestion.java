package com.example.wenda01.beans.bz;

import org.litepal.crud.DataSupport;

public class BzQuestion extends DataSupport {  //为本地存储的病症问题
    private String text;  //问题内容
    private int idd;  //问题id
    private int idToYes;  //选择答案是到达的问题id
    private int idToNo;  //选择答案是否到达的问题id
    private int isEnd;  //判断是否是结束的问题
//    private int idFrom;


    public BzQuestion(String text, int idd, int idToYes, int idToNo, int isEnd) {
        this.text = text;
        this.idd = idd;
        this.idToYes = idToYes;
        this.idToNo = idToNo;
        this.isEnd = isEnd;
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public int getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(int isEnd) {
        this.isEnd = isEnd;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIdToYes() {
        return idToYes;
    }

    public void setIdToYes(int idToYes) {
        this.idToYes = idToYes;
    }

    public int getIdToNo() {
        return idToNo;
    }

    public void setIdToNo(int idToNo) {
        this.idToNo = idToNo;
    }

//    public int getIdFrom() {
//        return idFrom;
//    }
//
//    public void setIdFrom(int idFrom) {
//        this.idFrom = idFrom;
//    }

}
