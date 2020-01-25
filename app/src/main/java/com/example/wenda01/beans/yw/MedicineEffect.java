package com.example.wenda01.beans.yw;

import org.litepal.crud.DataSupport;

public class MedicineEffect extends DataSupport {  //为本地存储的药物功效
    private String name;  //名称
    private String effect;  //功效


    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MedicineEffect(String name, String effect) {
        this.name = name;
        this.effect = effect;
    }
}
