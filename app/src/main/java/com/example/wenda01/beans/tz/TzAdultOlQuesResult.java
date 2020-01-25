package com.example.wenda01.beans.tz;

import java.util.List;

public class TzAdultOlQuesResult {  //为服务器获得成人在线体质检测问题
    private int auto_id;  //问题排序
    private int id;  //问题id
    private int body_id;  //体质对应id
    private String body_name;  //体质名称
    private String question;  //体质问题
    private int backward;  //逆序积分：0为否，1为是
    private int repeat;  //重复问题id
    private Answer answer;  //答案选项
    private List<OneRepeat> repeat_question;  //重复问题

    public static class OneRepeat{  //重复问题
        private int id;  //问题id
        private int body_id;  //对应体质id
        private String body_name;  //体质名称
        private int backward;  //逆序积分
        private int repeat;  //重复问题id

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBody_id() {
            return body_id;
        }

        public void setBody_id(int body_id) {
            this.body_id = body_id;
        }

        public String getBody_name() {
            return body_name;
        }

        public void setBody_name(String body_name) {
            this.body_name = body_name;
        }

        public int getBackward() {
            return backward;
        }

        public void setBackward(int backward) {
            this.backward = backward;
        }

        public int getRepeat() {
            return repeat;
        }

        public void setRepeat(int repeat) {
            this.repeat = repeat;
        }
    }

    public List<OneRepeat> getRepeat_question() {
        return repeat_question;
    }

    public void setRepeat_question(List<OneRepeat> repeat_question) {
        this.repeat_question = repeat_question;
    }

    public static class Answer{  //答案
        private String opt1;  //答案1
        private String opt2;  //答案2
        private String opt3;  //答案3
        private String opt4;  //答案4
        private String opt5;  //答案5

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

        public String getOpt3() {
            return opt3;
        }

        public void setOpt3(String opt3) {
            this.opt3 = opt3;
        }

        public String getOpt4() {
            return opt4;
        }

        public void setOpt4(String opt4) {
            this.opt4 = opt4;
        }

        public String getOpt5() {
            return opt5;
        }

        public void setOpt5(String opt5) {
            this.opt5 = opt5;
        }
    }

    public int getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(int auto_id) {
        this.auto_id = auto_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBody_id() {
        return body_id;
    }

    public void setBody_id(int body_id) {
        this.body_id = body_id;
    }

    public String getBody_name() {
        return body_name;
    }

    public void setBody_name(String body_name) {
        this.body_name = body_name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getBackward() {
        return backward;
    }

    public void setBackward(int backward) {
        this.backward = backward;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
