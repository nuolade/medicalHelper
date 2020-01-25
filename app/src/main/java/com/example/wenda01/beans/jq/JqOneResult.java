package com.example.wenda01.beans.jq;

public class JqOneResult {  //为服务器获得单个节气详细介绍
    private String res;  //服务器返回结果标识
    private Content rec;  //内容
    private String msg;  //消息简介

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public Content getRec() {
        return rec;
    }

    public void setRec(Content rec) {
        this.rec = rec;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class Content{  //内容
        private int losetime_tz;  //时间
        private int id;  //节气id
        private String name;  //名称
        private String jieshao;  //介绍
        private String qihou;  //气候
        private String shenghuo;  //生活
        private String jingshen;  //精神
        private String fangbing;  //防病
        private String yinshi;  //饮食
        private String yaoshan;  //药膳

        public int getLosetime_tz() {
            return losetime_tz;
        }

        public void setLosetime_tz(int losetime_tz) {
            this.losetime_tz = losetime_tz;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getJieshao() {
            return jieshao;
        }

        public void setJieshao(String jieshao) {
            this.jieshao = jieshao;
        }

        public String getQihou() {
            return qihou;
        }

        public void setQihou(String qihou) {
            this.qihou = qihou;
        }

        public String getShenghuo() {
            return shenghuo;
        }

        public void setShenghuo(String shenghuo) {
            this.shenghuo = shenghuo;
        }

        public String getJingshen() {
            return jingshen;
        }

        public void setJingshen(String jingshen) {
            this.jingshen = jingshen;
        }

        public String getFangbing() {
            return fangbing;
        }

        public void setFangbing(String fangbing) {
            this.fangbing = fangbing;
        }

        public String getYinshi() {
            return yinshi;
        }

        public void setYinshi(String yinshi) {
            this.yinshi = yinshi;
        }

        public String getYaoshan() {
            return yaoshan;
        }

        public void setYaoshan(String yaoshan) {
            this.yaoshan = yaoshan;
        }
    }
}
