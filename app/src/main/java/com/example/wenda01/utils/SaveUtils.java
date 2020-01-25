package com.example.wenda01.utils;

import android.content.SharedPreferences;

import com.example.wenda01.MyApplication;

import static android.content.Context.MODE_PRIVATE;

public class SaveUtils {   //知识问答模块，存储工具
    public static int getNewSessionNo(){   //获得累计会话数
        SharedPreferences sharedPreferences= MyApplication.getContext().getSharedPreferences("session",MODE_PRIVATE);
        int sNo=sharedPreferences.getInt("sNo",1);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("sNo",(sNo+1));
        editor.commit();
        return sNo;
    }

}
