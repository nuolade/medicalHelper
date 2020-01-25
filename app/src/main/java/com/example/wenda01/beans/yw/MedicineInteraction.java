package com.example.wenda01.beans.yw;

import org.litepal.crud.DataSupport;

public class MedicineInteraction extends DataSupport {  //为本地存储的药物相互作用信息
    private String name;  //名称
    private String interactionName;  //相互作用药物名称
    private String result;  //结果
    private String action;  //采取措施

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInteractionName() {
        return interactionName;
    }

    public void setInteractionName(String interactionName) {
        this.interactionName = interactionName;
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

    public MedicineInteraction(String name, String interactionName, String result, String action) {
        this.name = name;
        this.interactionName = interactionName;
        this.result = result;
        this.action = action;
    }
}
