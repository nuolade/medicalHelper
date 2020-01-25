package com.example.wenda01.beans.jq;

import java.util.List;

public class JqAllResult {  //为节气引导中所有的节气（现为本地读取，可改为服务器获取）
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
        private List<Jq> listF24;  //24节气列表

        public int getLosetime_tz() {
            return losetime_tz;
        }

        public void setLosetime_tz(int losetime_tz) {
            this.losetime_tz = losetime_tz;
        }

        public List<Jq> getListF24() {
            return listF24;
        }

        public void setListF24(List<Jq> listF24) {
            this.listF24 = listF24;
        }

        public static class Jq{  //节气
            private int id;  //节气id
            private String name;  //名称

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
        }
    }



}
