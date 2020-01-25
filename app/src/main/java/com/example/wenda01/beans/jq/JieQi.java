package com.example.wenda01.beans.jq;

import org.litepal.crud.DataSupport;

public class JieQi extends DataSupport {  //
    private int idd;  //
    private String name;  //
    private String jieShao;  //
    private String qiHou;  //
    private String shengHuo;  //
    private String jingShen;  //
    private String fangBing;  //
    private String yinShi;  //
    private String yaoShan;  //

    public JieQi(int idd, String name) {
        this.idd = idd;
        this.name = name;
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJieShao() {
        return jieShao;
    }

    public void setJieShao(String jieShao) {
        this.jieShao = jieShao;
    }

    public String getQiHou() {
        return qiHou;
    }

    public void setQiHou(String qiHou) {
        this.qiHou = qiHou;
    }

    public String getShengHuo() {
        return shengHuo;
    }

    public void setShengHuo(String shengHuo) {
        this.shengHuo = shengHuo;
    }

    public String getJingShen() {
        return jingShen;
    }

    public void setJingShen(String jingShen) {
        this.jingShen = jingShen;
    }

    public String getFangBing() {
        return fangBing;
    }

    public void setFangBing(String fangBing) {
        this.fangBing = fangBing;
    }

    public String getYinShi() {
        return yinShi;
    }

    public void setYinShi(String yinShi) {
        this.yinShi = yinShi;
    }

    public String getYaoShan() {
        return yaoShan;
    }

    public void setYaoShan(String yaoShan) {
        this.yaoShan = yaoShan;
    }
}
