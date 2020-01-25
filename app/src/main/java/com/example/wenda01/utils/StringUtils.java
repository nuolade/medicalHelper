package com.example.wenda01.utils;

import com.example.wenda01.R;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {   //字符串工具类

    public static String [] listHome={"主"};
    public static String [] listBack={"返回"};
    public static String [] listFinish={"退出账号","注销","退出"};
    public static String [] listNo={"否","没有","不是","不对"};
    public static String [] listYes={"是","对","有","嗯"};
    public static String [] listPre={"上一步","上一问","上"};
    public static String [] listSave={"保存","存储","记录"};
    public static String [] listMeiYou      ={"没有","根本不","从来没有"};
    public static String [] listHenShao     ={"很少","有一点","偶尔"};
    public static String [] listYouShi      ={"有时","有些","少数时间"};
    public static String [] listJingChang   ={"经常","相当","多数时间"};
    public static String [] listZongShi     ={"总是","非常","每天"};
    public static String [] listAnalyze ={"分析","诊断","评估"};
    public static String [] listYangX={"阳虚"};
    public static String [] listYinX ={"阴虚"};
    public static String [] listQiX  ={"气虚"};
    public static String [] listTanS ={"痰湿"};
    public static String [] listShiR ={"湿热"};
    public static String [] listXueY ={"血瘀"};
    public static String [] listQiY  ={"气郁"};
    public static String [] listXueX ={"血虚"};
    public static String [] listPingH={"平和"};
    public static String [] listTeB={"特禀"};
    public static String [] listShengJ={"生机"};
    public static String [] listPiX ={"脾虚"};
    public static String [] listJiZ  ={"积滞"};
    public static String [] listReZ ={"热滞"};
    public static String [] listShiZ ={"湿滞"};
    public static String [] listXinHW ={"心火"};
    public static String [] listYiB  ={"异禀"};
    public static String [] listNumPre={"第","题"};
    public static String [] listSound={"语音","辅助"};
    public static String [] listRepeat={"重复","重播","重"};

    public static String [] listSearch={"搜索","查找","搜","查","寻"};
    public static String [] listChange={"转换","改变","模式","外观"};
    public static String [] listJq={"立春","雨水","惊蛰","春分","清明","谷雨"
            ,"立夏","小满","芒种","夏至","小暑","大暑"
            ,"立秋","处暑","白露","秋分","寒露","霜降"
            ,"立冬","小雪","大雪","冬至","小寒","大寒"};


    public static boolean myContainInList(String s, List<String> list){   //判断字符串是否在列表中
        boolean f=false;
        for(int i=0;i<list.size();i++){
            if(s.equals(list.get(i))){
                f=true;
                break;
            }
        }
        return f;
    }

    public static String myTrim(String s,String [] list){   //去除列表中出现文字
        for(int i=0;i<list.length;i++){
            s.replace(list[i],"");
        }
        return s;
    }

//    private void doContainCheck(String s,String [] list)

    public static boolean myContain(String s,String [] list){   //判断字符串中是否包含列表中内容
        boolean flag=false;
        for(int i=0;i<list.length;i++){
            if(s.indexOf(list[i])!=-1){
                flag=true;
                return flag;
            }
        }
        return flag;
    }

    public static boolean myContainSound(String s){   //判断是否包含“声音”的字样
        return myContain(s,listSound);
    }

    public static boolean myContainRepeat(String s){   //判断是否包含“重播”的字样
        return myContain(s,listRepeat);
    }

    public static int myContainJq(String s){   //判断是否包含节气并返回索引值
        int index=-1;
        for(int i=0;i<listJq.length;i++){
            if(s.indexOf(listJq[i])!=-1){
                index=i;
                break;
            }
        }
        return index;
    }

    public static boolean myContain(String s,String s2){   //判断是否包含s2
        return s.indexOf(s2)!=-1;
    }

    public static boolean myContainChange(String s){   //判断是否包含“改变样式”的字样
        return myContain(s,listChange);
    }

    public static boolean myContainSearch(String s){   //判断是否包含“搜索”的字样
        return myContain(s,listSearch);
    }

    public static boolean myContainPingHe(String s){   //判断是否包含“平和质”的字样
        return myContain(s,listPingH);
    }
    public static boolean myContainYangXu(String s){  //判断是否包含“阳虚质”的字样
        return myContain(s,listYangX);
    }
    public static boolean myContainYinXu(String s){   //判断是否包含“阴虚质”的字样
        return myContain(s ,listYinX);
    }
    public static boolean myContainQiXu(String s){   //判断是否包含“气虚质”的字样
        return myContain(s  ,listQiX);
    }
    public static boolean myContainTanShi(String s){   //判断是否包含“痰湿质”的字样
        return myContain(s,listTanS);
    }
    public static boolean myContainShiRe(String s){   //判断是否包含“湿热质”的字样
        return myContain(s ,listShiR);
    }
    public static boolean myContainXueYu(String s){   //判断是否包含“血瘀质”的字样
        return myContain(s ,listXueY);
    }
    public static boolean myContainQiYu(String s){   //判断是否包含“气郁质”的字样
        return myContain(s  ,listQiY);
    }
    public static boolean myContainXueXu(String s){   //判断是否包含“血虚质”的字样
        return myContain(s ,listXueX);
    }
    public static boolean myContainTeBing(String s){   //判断是否包含“特禀质”的字样
        return myContain(s ,listTeB);
    }
    public static boolean myContainShengJi(String s){   //判断是否包含“生机旺盛质”的字样
        return myContain(s,listShengJ);
    }
    public static boolean myContainPiXu(String s){   //判断是否包含“脾虚质”的字样
        return myContain(s,listPiX);
    }
    public static boolean myContainJiZhi(String s){   //判断是否包含“积滞质”的字样
        return myContain(s,listJiZ);
    }
    public static boolean myContainReZhi(String s){   //判断是否包含“热滞质”的字样
        return myContain(s,listReZ);
    }
    public static boolean myContainShiZhi(String s){   //判断是否包含“湿滞质”的字样
        return myContain(s,listShiZ);
    }
    public static boolean myContainXinHuo(String s){   //判断是否包含“心火旺质”的字样
        return myContain(s,listXinHW);
    }
    public static boolean myContainYiBing(String s){   //判断是否包含“异禀质”的字样
        return myContain(s,listYiB);
    }


    public static boolean myContainAnalyze(String s){   //判断是否包含“分析”的字样
        return myContain(s,listAnalyze);
    }

    public static boolean myContainMeiYou   (String s){   //判断是否包含“没有”的字样
        return myContain(s,listMeiYou);
    }
    public static boolean myContainHenShao  (String s){  //判断是否包含“很少”的字样
        return myContain(s,listHenShao);
    }
    public static boolean myContainYouShi   (String s){   //判断是否包含“有时”的字样
        return myContain(s,listYouShi);
    }
    public static boolean myContainJingChang(String s){   //判断是否包含“经常”的字样
        return myContain(s,listJingChang);
    }
    public static boolean myContainZongShi  (String s){  //判断是否包含“总是”的字样
        return myContain(s,listZongShi);
    }

    public static boolean myContainNo(String s){   //判断是否包含“否”的字样
        return myContain(s,listNo);
    }
    public static boolean myContainYes(String s){   //判断是否包含“是”的字样

        return myContain(s,listYes);
    }

    public static boolean myContainPre(String s){   //判断是否包含“上一问”的字样
        return myContain(s,listPre);
    }

    public static boolean myContainSave(String s){   //判断是否包含“保存”的字样
        return myContain(s,listSave);
    }

    public static boolean myContainBack(String s){   //判断是否包含“回退”的字样
        return myContain(s,listBack);
    }

    public static boolean myContainHome(String s){   //判断是否包含“回到主界面”的字样
        return myContain(s,listHome);
    }

    public static boolean myContainFinish(String s){   //判断是否包含“结束”的字样
        return myContain(s,listFinish);
    }

    public static boolean myContainNumPre(String s){  //判断是否包含“题目选择”的字样
        return myContain(s,listNumPre);
    }

    public static boolean myContainNum(String s){   //判断是否包含阿拉伯数字
        String num=StringUtils.findNum(s).trim();
        if(num.equals("")){
            return false;
        }
        return true;
    }

    public static String findNum(String s){   //找到字符串中的阿拉伯数字
        Pattern p=Pattern.compile("\\d+");
        Matcher m=p.matcher(s);
        m.find();
        try{
            return m.group();
        }catch (Exception e){
            return "";
        }

    }

    public static String doUnnumTrim(String s){  //找到字符串中的中文数字
        String[] cnArr =  {"一","二","三","四","五","六","七","八","九","十"};
        String s2="";


        for(int i=0;i<s.length();i++){
            String s0=s.substring(i,i+1);
            if(Arrays.asList(cnArr).contains(s0)){
                s2+=s0;
            }
        }
        return s2;
    }


    public static void main(String [] args){

        System.out.println(doUnnumTrim("244我二十九你好"));
        System.out.println(2);
    }

    public static boolean myMhContian(String s,String key){   //字符串的模糊查询
        boolean f=false;
        for(int i=0;i<key.length();i++){
            char c=key.charAt(i);
            if(s.contains(""+c)){
                f=true;
                break;
            }
        }
        return f;
    }

    public static String getPhoneHide(String phone ){  //返回隐藏电话号
        if(phone.length()<11){
            return "";
        }else{
            return phone.substring(0,3)+"*****"+phone.substring(8,11);
        }
    }
}
