package com.example.wenda01.beans.tz;

import java.util.List;

public class TzWxOlQuesResult {  //为服务器获得五型在线体质检测问题
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
        private int losetime_tz;  //时间
        private List<Wxtz> listWxtz;  //五行体质问题列表

        public static class Wxtz{  //体质问题
            private int age_group;  //年龄组别 0年轻1老年2通用
            private Answer answer;  //答案
            private int backward;  //反向计分
            private int body_id;  //五型体质id（19-23金木水火土
            private int class_id;  //无用
            private int gender;  //性别
            private int id;  //问题id
            private String question;  //问题内容
            private int repeat;  //重复问题id
            private int sort;  //手动排序（无用

            public static class Answer{  //答案
                private String opt1;  //答案1
                private String opt2;  //答案2

                public String getOpt1() {
                    return opt1;
                }

                public void setOpt1(String opt1) {
                    this.opt1 = opt1;
                }

                public String getOpt2() {
                    return opt2;
                }

                public void setOpt2(String opt2) {
                    this.opt2 = opt2;
                }
            }

            public int getAge_group() {
                return age_group;
            }

            public void setAge_group(int age_group) {
                this.age_group = age_group;
            }

            public Answer getAnswer() {
                return answer;
            }

            public void setAnswer(Answer answer) {
                this.answer = answer;
            }

            public int getBackward() {
                return backward;
            }

            public void setBackward(int backward) {
                this.backward = backward;
            }

            public int getBody_id() {
                return body_id;
            }

            public void setBody_id(int body_id) {
                this.body_id = body_id;
            }

            public int getClass_id() {
                return class_id;
            }

            public void setClass_id(int class_id) {
                this.class_id = class_id;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public int getRepeat() {
                return repeat;
            }

            public void setRepeat(int repeat) {
                this.repeat = repeat;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }
        }

        public int getLosetime_tz() {
            return losetime_tz;
        }

        public void setLosetime_tz(int losetime_tz) {
            this.losetime_tz = losetime_tz;
        }

        public List<Wxtz> getListWxtz() {
            return listWxtz;
        }

        public void setListWxtz(List<Wxtz> listWxtz) {
            this.listWxtz = listWxtz;
        }
    }



}
