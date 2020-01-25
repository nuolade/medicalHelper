package com.example.wenda01.beans.yw;

public class MedInter4S {  //为药物详细内容里相互作用中的每一项
    private String name;  //名称
    private String result;  //结果
    private String action;  //采取措施

    public MedInter4S(String name, String result, String action) {
        this.name = name;
        this.result = result;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
