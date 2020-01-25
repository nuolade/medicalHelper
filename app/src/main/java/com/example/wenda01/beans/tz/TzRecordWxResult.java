package com.example.wenda01.beans.tz;

import java.util.List;

public class TzRecordWxResult {  //为服务器获得五型体质记录
    private String res;  //服务器返回结果标识
    private String msg;  //消息简介
    private C rec;  //内容

    public static class C{  //内容
        private List<Body> listBodyConclusionWuxing;  //所有五型体质记录列表



        public static class Body{  //每个体质记录
            private int id;  //记录id
            private int member_id;  //现无用
            private String name;  //姓名
            private String phone;  //手机号
            private int gender;  //性别
            private int age;  //年龄
            private int jin;  //金分数
            private int mu;  //木分数
            private int shui;  //水分数
            private int huo;  //火分数
            private int tu;  //土分数
            private String record_time;  //保存时间

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



            public String getRecord_time() {
                return record_time;
            }

            public void setRecord_time(String record_time) {
                this.record_time = record_time;
            }

            public int getJin() {
                return jin;
            }

            public void setJin(int jin) {
                this.jin = jin;
            }

            public int getMu() {
                return mu;
            }

            public void setMu(int mu) {
                this.mu = mu;
            }

            public int getShui() {
                return shui;
            }

            public void setShui(int shui) {
                this.shui = shui;
            }

            public int getHuo() {
                return huo;
            }

            public void setHuo(int huo) {
                this.huo = huo;
            }

            public int getTu() {
                return tu;
            }

            public void setTu(int tu) {
                this.tu = tu;
            }
        }

        public List<Body> getListBodyConclusionWuxing() {
            return listBodyConclusionWuxing;
        }

        public void setListBodyConclusionWuxing(List<Body> listBodyConclusionWuxing) {
            this.listBodyConclusionWuxing = listBodyConclusionWuxing;
        }
    }

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
}
