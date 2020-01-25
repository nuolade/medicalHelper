package com.example.wenda01.beans.tz;

import org.litepal.crud.DataSupport;

public class TzAdultKeyQuestion extends DataSupport {  //为本地存储的成人体质快捷问题
    private String q;  //问题内容
    private int idd;  //问题id
    private int index1;  //阳虚标记
    private int index2;  //阴虚标记
    private int index3;  //气虚标记
    private int index4;  //痰湿标记
    private int index5;  //血瘀标记
    private int index6;  //湿热标记
    private int index7;  //湿热标记
    private int index8;  //特禀标记
    private int index9;  //平和标记

    public TzAdultKeyQuestion(String q, int idd) {
        this.q = q;
        this.idd = idd;
    }

    public TzAdultKeyQuestion(String q, int idd, int index1, int index2, int index3, int index4, int index5, int index6, int index7, int index8, int index9) {
        this.q = q;
        this.idd = idd;
        this.index1 = index1;
        this.index2 = index2;
        this.index3 = index3;
        this.index4 = index4;
        this.index5 = index5;
        this.index6 = index6;
        this.index7 = index7;
        this.index8 = index8;
        this.index9 = index9;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
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

    public int getIndex8() {
        return index8;
    }

    public void setIndex8(int index8) {
        this.index8 = index8;
    }

    public int getIndex9() {
        return index9;
    }

    public void setIndex9(int index9) {
        this.index9 = index9;
    }
}
