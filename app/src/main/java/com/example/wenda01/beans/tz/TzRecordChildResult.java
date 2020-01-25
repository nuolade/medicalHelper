package com.example.wenda01.beans.tz;

import java.util.List;

public class TzRecordChildResult {  //为服务器获得儿童体质记录
    private String res;  //服务器返回结果标识
    private String msg;  //消息简介
    private C rec;  //内容

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public C getRec() {
        return rec;
    }

    public void setRec(C rec) {
        this.rec = rec;
    }

    public static class C{  //内容
        private List<Body> listConstConclusionChild;  //所有儿童体质记录列表

        public List<Body> getListConstConclusionChild() {
            return listConstConclusionChild;
        }

        public void setListConstConclusionChild(List<Body> listConstConclusionChild) {
            this.listConstConclusionChild = listConstConclusionChild;
        }

        public static class Body{  //每个体质记录
            private int id;  //记录id
            private int member_id;  //现无用
            private String name;  //姓名
            private String phone;  //手机号
            private int gender;  //性别
            private int age;  //年龄
            private int pixu;  //脾虚分数
            private int jizhi;  //积滞分数
            private int rezhi;  //热滞分数
            private int shizhi;  //湿滞分数
            private int xinhuo;  //心火旺分数
            private int yibing;  //异禀分数
            private int shengji;  //生机旺盛分数
            private String record_time;  //记录时间

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMember_id() {
                return member_id;
            }

            public void setMember_id(int member_id) {
                this.member_id = member_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getPixu() {
                return pixu;
            }

            public void setPixu(int pixu) {
                this.pixu = pixu;
            }

            public int getJizhi() {
                return jizhi;
            }

            public void setJizhi(int jizhi) {
                this.jizhi = jizhi;
            }

            public int getRezhi() {
                return rezhi;
            }

            public void setRezhi(int rezhi) {
                this.rezhi = rezhi;
            }

            public int getShizhi() {
                return shizhi;
            }

            public void setShizhi(int shizhi) {
                this.shizhi = shizhi;
            }

            public int getXinhuo() {
                return xinhuo;
            }

            public void setXinhuo(int xinhuo) {
                this.xinhuo = xinhuo;
            }

            public int getYibing() {
                return yibing;
            }

            public void setYibing(int yibing) {
                this.yibing = yibing;
            }

            public int getShengji() {
                return shengji;
            }

            public void setShengji(int shengji) {
                this.shengji = shengji;
            }

            public String getRecord_time() {
                return record_time;
            }

            public void setRecord_time(String record_time) {
                this.record_time = record_time;
            }
        }


    }
}
