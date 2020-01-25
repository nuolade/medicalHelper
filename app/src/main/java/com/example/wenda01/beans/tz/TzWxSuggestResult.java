package com.example.wenda01.beans.tz;

import com.example.wenda01.beans.tz.Bisic;

import java.util.List;

public class TzWxSuggestResult {  //五型体质建议返回结果
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
        private String tizhi_end; //体质状况
        private String res;  //服务器返回结果标识
        private int [] scores;  //分数
        List<Bisic> wxBisic;   //体质基础介绍
        List<Bisic> wxConditioning;  //体质中医调节建议
        List<Bisic> wxSports;  //体质运动起居建议
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

        public String getRes() {
            return res;
        }

        public void setRes(String res) {
            this.res = res;
        }

        public int[] getScores() {
            return scores;
        }

        public void setScores(int[] scores) {
            this.scores = scores;
        }

        public List<Bisic> getWxBisic() {
            return wxBisic;
        }

        public void setWxBisic(List<Bisic> wxBisic) {
            this.wxBisic = wxBisic;
        }

        public List<Bisic> getWxConditioning() {
            return wxConditioning;
        }

        public void setWxConditioning(List<Bisic> wxConditioning) {
            this.wxConditioning = wxConditioning;
        }

        public List<Bisic> getWxSports() {
            return wxSports;
        }

        public void setWxSports(List<Bisic> wxSports) {
            this.wxSports = wxSports;
        }

        public String getRecord_time() {
            return record_time;
        }

        public void setRecord_time(String record_time) {
            this.record_time = record_time;
        }


    }
}
