package com.example.wenda01.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {  //时间工具

    public static String getTime(){  //得到当前时间字符串
        Date date=new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

        String str = format.format(date);
        return str;
    }

    public static boolean before(String t1,String t2){   //比较两时间前后关系
        String [] arr1=t1.split("-");
        String [] arr2=t2.split("-");
        int len=Math.min(arr1.length,arr2.length);
        boolean f=true;
        if(t1.equals("") || t2.equals("")){
            return f;
        }
        for(int i=0;i<len;i++){
            int n1=Integer.parseInt(arr1[i]);
            int n2=Integer.parseInt(arr2[i]);
            if(n1>n2){
                f=false;
                break;
            }
        }
        return f;
    }
}
