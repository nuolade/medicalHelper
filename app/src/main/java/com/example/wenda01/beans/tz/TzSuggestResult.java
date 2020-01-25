package com.example.wenda01.beans.tz;

import com.example.wenda01.beans.tz.Bisic;

import java.util.List;

public class TzSuggestResult {  //为从服务器获得的每个体质记录（成人和儿童）的建议
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
        private String name;  //姓名
        private int age;  //年龄
        private int gender;  //性别
        private String tizhi_end;  //体质状况
        List<Bisic> zyBisic;  //体质基础介绍
        List<Bisic> zyConditioning;  //体质中医调节建议
        List<Bisic> zySports;  //体质运动起居建议
        private String record_time;  //保存时间

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getTizhi_end() {
            return tizhi_end;
        }

        public void setTizhi_end(String tizhi_end) {
            this.tizhi_end = tizhi_end;
        }

        public List<Bisic> getZyBisic() {
            return zyBisic;
        }

        public void setZyBisic(List<Bisic> zyBisic) {
            this.zyBisic = zyBisic;
        }

        public List<Bisic> getZyConditioning() {
            return zyConditioning;
        }

        public void setZyConditioning(List<Bisic> zyConditioning) {
            this.zyConditioning = zyConditioning;
        }

        public List<Bisic> getZySports() {
            return zySports;
        }

        public void setZySports(List<Bisic> zySports) {
            this.zySports = zySports;
        }

        public String getRecord_time() {
            return record_time;
        }

        public void setRecord_time(String record_time) {
            this.record_time = record_time;
        }


    }
}
