package com.example.wenda01.beans.xy;

import java.util.List;

public class XyResult {  //
    private String msg;  //
    private String res;  //
    private Content rec;  //

    public static class Content{  //
        private List<Bx> listBX;  //
        public static class Bx{  //
            private String bianzheng_code;  //病症编码
            private String zhenghou;        //证候
            private String min_age;         //最小年龄
            private String max_age;         //最大年龄
            private String min_sp;          //最小收缩压
            private String max_sp;          //最大收缩压
            private String min_dp;          //最小舒张压
            private String max_dp;          //最大舒张压
            private String min_hr;          //最小心率
            private String max_hr;          //最大心率
            private String ib_md;           //失衡经络
            private String co_smp_man;      //常见症状男
            private String getCo_smp_woman; //常见症状女
            private String pathogenesis;    //病机
            private String treatment;       //治疗方法
            private List<Fanyao> bpFanyao;  //对应方药

            public static class Fanyao{  //
                private String name;  //
                private String id;  //
                private String zucheng_sy;  //

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getZucheng_sy() {
                    return zucheng_sy;
                }

                public void setZucheng_sy(String zucheng_sy) {
                    this.zucheng_sy = zucheng_sy;
                }
            }

            public String getBianzheng_code() {
                return bianzheng_code;
            }

            public void setBianzheng_code(String bianzheng_code) {
                this.bianzheng_code = bianzheng_code;
            }

            public String getZhenghou() {
                return zhenghou;
            }

            public void setZhenghou(String zhenghou) {
                this.zhenghou = zhenghou;
            }

            public String getMin_age() {
                return min_age;
            }

            public void setMin_age(String min_age) {
                this.min_age = min_age;
            }

            public String getMax_age() {
                return max_age;
            }

            public void setMax_age(String max_age) {
                this.max_age = max_age;
            }

            public String getMin_sp() {
                return min_sp;
            }

            public void setMin_sp(String min_sp) {
                this.min_sp = min_sp;
            }

            public String getMax_sp() {
                return max_sp;
            }

            public void setMax_sp(String max_sp) {
                this.max_sp = max_sp;
            }

            public String getMin_dp() {
                return min_dp;
            }

            public void setMin_dp(String min_dp) {
                this.min_dp = min_dp;
            }

            public String getMax_dp() {
                return max_dp;
            }

            public void setMax_dp(String max_dp) {
                this.max_dp = max_dp;
            }

            public String getMin_hr() {
                return min_hr;
            }

            public void setMin_hr(String min_hr) {
                this.min_hr = min_hr;
            }

            public String getMax_hr() {
                return max_hr;
            }

            public void setMax_hr(String max_hr) {
                this.max_hr = max_hr;
            }

            public String getIb_md() {
                return ib_md;
            }

            public void setIb_md(String ib_md) {
                this.ib_md = ib_md;
            }

            public String getCo_smp_man() {
                return co_smp_man;
            }

            public void setCo_smp_man(String co_smp_man) {
                this.co_smp_man = co_smp_man;
            }

            public String getGetCo_smp_woman() {
                return getCo_smp_woman;
            }

            public void setGetCo_smp_woman(String getCo_smp_woman) {
                this.getCo_smp_woman = getCo_smp_woman;
            }

            public String getPathogenesis() {
                return pathogenesis;
            }

            public void setPathogenesis(String pathogenesis) {
                this.pathogenesis = pathogenesis;
            }

            public String getTreatment() {
                return treatment;
            }

            public void setTreatment(String treatment) {
                this.treatment = treatment;
            }

            public List<Fanyao> getBpFanyao() {
                return bpFanyao;
            }

            public void setBpFanyao(List<Fanyao> bpFanyao) {
                this.bpFanyao = bpFanyao;
            }
        }

        public List<Bx> getListBX() {
            return listBX;
        }

        public void setListBX(List<Bx> listBX) {
            this.listBX = listBX;
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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
}
