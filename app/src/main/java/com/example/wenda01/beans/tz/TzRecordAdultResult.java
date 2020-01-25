package com.example.wenda01.beans.tz;

import java.util.List;

public class TzRecordAdultResult {  //
    private String res;  //
    private C rec;  //
    private String msg;  //


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

    public static class C{  //
        List<Body> listBodyConclusion;  //

        public List<Body> getListBodyConclusion() {
            return listBodyConclusion;
        }

        public void setListBodyConclusion(List<Body> listBodyConclusion) {
            this.listBodyConclusion = listBodyConclusion;
        }

        public static class Body{  //
            private int id;  //
            private int member_id;  //
            private String name;  //
            private String phone;  //
            private int gender;  //
            private int age;  //
            private int pinghe;  //
            private int qixu;  //
            private int yangxu;  //
            private int yinxu;  //
            private int tanshi;  //
            private int shire;  //
            private int xueyu;  //
            private int qiyu;  //
            private int tebing;  //
            private String record_time;  //

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

            public int getPinghe() {
                return pinghe;
            }

            public void setPinghe(int pinghe) {
                this.pinghe = pinghe;
            }

            public int getQixu() {
                return qixu;
            }

            public void setQixu(int qixu) {
                this.qixu = qixu;
            }

            public int getYangxu() {
                return yangxu;
            }

            public void setYangxu(int yangxu) {
                this.yangxu = yangxu;
            }

            public int getYinxu() {
                return yinxu;
            }

            public void setYinxu(int yinxu) {
                this.yinxu = yinxu;
            }

            public int getTanshi() {
                return tanshi;
            }

            public void setTanshi(int tanshi) {
                this.tanshi = tanshi;
            }

            public int getShire() {
                return shire;
            }

            public void setShire(int shire) {
                this.shire = shire;
            }

            public int getXueyu() {
                return xueyu;
            }

            public void setXueyu(int xueyu) {
                this.xueyu = xueyu;
            }

            public int getQiyu() {
                return qiyu;
            }

            public void setQiyu(int qiyu) {
                this.qiyu = qiyu;
            }

            public int getTebing() {
                return tebing;
            }

            public void setTebing(int tebing) {
                this.tebing = tebing;
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
