package com.example.wenda01.beans.tz;

public class TzIndexQuestion {  //为编号选择的体质问题
    private String text;  //问题内容
    private int id;  //问题id
    private int state;  //问题状态（选择的答案）
    private boolean isOn;  //问题是否选择

    public TzIndexQuestion(String text, int id, int state, boolean isOn) {
        this.text = text;
        this.id = id;
        this.state = state;
        this.isOn=isOn;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
