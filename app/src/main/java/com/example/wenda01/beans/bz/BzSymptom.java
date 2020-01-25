package com.example.wenda01.beans.bz;

import org.litepal.crud.DataSupport;

public class BzSymptom extends DataSupport {  //为本地存储的病症名称
    private String name;  //名称
    private int idd;  //病症id
    private int maxPath;  //病症中最大问题询问数
    private String intor;  //病症简介

    public BzSymptom(String name, int idd, int maxPath, String intor) {
        this.name = name;
        this.idd = idd;
        this.maxPath = maxPath;
        this.intor = intor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int id) {
        this.idd = id;
    }


    public int getMaxPath() {
        return maxPath;
    }

    public void setMaxPath(int maxPath) {
        this.maxPath = maxPath;
    }

    public String getIntor() {
        return intor;
    }

    public void setIntor(String intor) {
        this.intor = intor;
    }
}
