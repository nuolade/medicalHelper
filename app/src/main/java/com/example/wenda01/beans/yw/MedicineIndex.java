package com.example.wenda01.beans.yw;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class MedicineIndex extends DataSupport implements Serializable {  //为本地存储的药物基本信息（索引）
    private String name ;  //名称
    private String category;  //类别
    private String o1;  //与药物相互作用表关联的名称
    private String o2;  //与药物功效表关联的名称
    private String cf;  //药物处方类别
    private String mx;  //药物明细类别
    private int idd;  //药物id

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public String getO1() {
        return o1;
    }

    public void setO1(String o1) {
        this.o1 = o1;
    }

    public String getO2() {
        return o2;
    }

    public void setO2(String o2) {
        this.o2 = o2;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getMx() {
        return mx;
    }

    public void setMx(String mx) {
        this.mx = mx;
    }

    public MedicineIndex(String name, String category, String o1, String o2, String cf, String mx, int idd) {
        this.name = name;
        this.category = category;
        this.o1 = o1;
        this.o2 = o2;
        this.cf = cf;
        this.mx = mx;
        this.idd = idd;
    }
}
