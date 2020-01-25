package com.example.wenda01.beans.tz;

import org.litepal.crud.DataSupport;

public class TzChildQuestion extends DataSupport {  //为本地存储的儿童体质问题
    private int idd;  //问题id
    private String q;  //问题内容
    private int orderId;  //问题先后顺序编号
    private int index1;  //脾虚标记
    private int index2;  //积滞标记
    private int index3;  //热滞标记
    private int index4;  //湿滞标记
    private int index5;  //心火旺标记
    private int index6;  //异禀标记
    private int index7;  //生机旺盛标记
    private String type;  //问题分类

    public TzChildQuestion(int idd, String q, int orderId, int index1, int index2, int index3, int index4, int index5, int index6, int index7, String type) {
        this.idd = idd;
        this.q = q;
        this.orderId = orderId;
        this.index1 = index1;
        this.index2 = index2;
        this.index3 = index3;
        this.index4 = index4;
        this.index5 = index5;
        this.index6 = index6;
        this.index7 = index7;
        this.type = type;
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getIndex1() {
        return index1;
    }

    public void setIndex1(int index1) {
        this.index1 = index1;
    }

    public int getIndex2() {
        return index2;
    }

    public void setIndex2(int index2) {
        this.index2 = index2;
    }

    public int getIndex3() {
        return index3;
    }

    public void setIndex3(int index3) {
        this.index3 = index3;
    }

    public int getIndex4() {
        return index4;
    }

    public void setIndex4(int index4) {
        this.index4 = index4;
    }

    public int getIndex5() {
        return index5;
    }

    public void setIndex5(int index5) {
        this.index5 = index5;
    }

    public int getIndex6() {
        return index6;
    }

    public void setIndex6(int index6) {
        this.index6 = index6;
    }

    public int getIndex7() {
        return index7;
    }

    public void setIndex7(int index7) {
        this.index7 = index7;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
