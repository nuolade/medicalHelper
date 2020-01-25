package com.example.wenda01.beans.bz;

import org.litepal.crud.DataSupport;

public class BzRecord extends DataSupport {  //为本地存储的病症记录
    private String phone;  //手机号
    private String symptom;  //症状名
    private String time;  //保存时间
    private String sId;  //由询问问题的id组成的字符串
    private String sAns;  //由回答问题的答案组成的字符串
    private String suggestion;  //最终给出的建议

    public BzRecord(String phone, String symptom, String time, String sId, String sAns, String suggestion) {
        this.phone = phone;
        this.symptom = symptom;
        this.time = time;
        this.sId = sId;
        this.sAns = sAns;
        this.suggestion = suggestion;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
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

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsAns() {
        return sAns;
    }

    public void setsAns(String sAns) {
        this.sAns = sAns;
    }
}
