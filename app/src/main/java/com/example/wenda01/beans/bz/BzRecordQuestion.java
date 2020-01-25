package com.example.wenda01.beans.bz;

public class BzRecordQuestion {  //为病症记录中的问答对
    String q;  //问题
    String a;  //答案

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public BzRecordQuestion(String q, String a) {
        this.q = q;
        this.a = a;
    }
}
