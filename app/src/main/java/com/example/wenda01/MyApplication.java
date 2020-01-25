package com.example.wenda01;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.wenda01.utils.Ks;
import com.example.wenda01.utils.TTSUtility;
import com.example.wenda01.utils.ToastUtils;
import com.example.wenda01.views.Wheel.WheelView;

import org.litepal.LitePal;

public class MyApplication extends Application {  //启动的应用
    private static Context context;  //全局上下文
    private static boolean isSoundOpen; //是否开启播报辅助功能
    private static boolean isDialogOpen; //是否开启对话框辅助功能

    private static int sayLock=0; //声音播报锁
    private static String reportString; //播报内容
    private static WheelView wheelView=null;
    public static int HSIZE=6; //历史记录存储大小
    public static boolean bzRDel=true; //病症记录删除提示
    public static int qaMsgDel= Ks.QA_MSG_UNDEL; //知识问答消息删除标记
    public static int qaSesDel= Ks.QA_MSG_UNDEL; //知识问答会话删除标记

    public static int getQaMsgDel() {
        return qaMsgDel;
    }

    public static void setQaMsgDel(int qaMsgDel) {
        MyApplication.qaMsgDel = qaMsgDel;
    }

    public static int getQaSesDel() {
        return qaSesDel;
    }

    public static void setQaSesDel(int qaSesDel) {
        MyApplication.qaSesDel = qaSesDel;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        LitePal.initialize(context);
        initSound();
        initDialog();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        saveSound();
        saveDialog();
    }

    private void initSound(){
        SharedPreferences sharedPreferences=getSharedPreferences("init",MODE_PRIVATE);
        isSoundOpen=sharedPreferences.getBoolean("isSoundOpen",true);
    }

    private void saveSound(){
        SharedPreferences sharedPreferences=getSharedPreferences("init",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("isSoundOpen",isSoundOpen);
        editor.commit();
    }

    public static boolean isIsSoundOpen() {
        return isSoundOpen;
    }

    public static void setIsSoundOpen(boolean isSoundOpen) {
        MyApplication.isSoundOpen = isSoundOpen;
    }

    public static void changeSoundOpen(){
        if(isIsSoundOpen()){
            setIsSoundOpen(false);
            ToastUtils.showS("辅助语音功能关闭");
            TTSUtility.getInstance(getContext()).stopSpeaking();
        }else{
            setIsSoundOpen(true);
            ToastUtils.showS("辅助语音功能开启");
        }
    }

    private void initDialog(){
        SharedPreferences sharedPreferences=getSharedPreferences("init",MODE_PRIVATE);
        isDialogOpen=sharedPreferences.getBoolean("isDialogOpen",true);
    }

    private void saveDialog(){
        SharedPreferences sharedPreferences=getSharedPreferences("init",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("isDialogOpen",isDialogOpen);
        editor.commit();
    }

    public static boolean isIsDialogOpen() {
        return isDialogOpen;
    }

    public static void setIsDialogOpen(boolean isDialogOpen) {
        MyApplication.isDialogOpen = isDialogOpen;
    }

    public static void changeDialogOpen(){
        if(isIsDialogOpen()){
            setIsDialogOpen(false);
            ToastUtils.showS("对话介绍功能关闭");
            TTSUtility.getInstance(getContext()).stopSpeaking();
        }else{
            setIsDialogOpen(true);
            ToastUtils.showS("对话介绍功能开启");
        }
    }

    public static Context getContext(){
        return context;
    }

    public static int getSayLock() {
        return sayLock;
    }

    public static void setSayLock(int sayLock) {
        MyApplication.sayLock = sayLock;
    }

    public static boolean isSayLocked(){
        return sayLock==0;
    }

    public static void addSayLock(){
        sayLock++;
    }

    public static void subSayLock(){
        sayLock--;
    }

    public static String getReportString() {
        return reportString;
    }

    public static void setReportString(String reportString) {
        MyApplication.reportString = reportString;
    }

    public static WheelView getWheelView() {
        return wheelView;
    }

    public static void setWheelView(WheelView wheelView) {
        MyApplication.wheelView = wheelView;
    }

    public static boolean hasWheelView(){
        return wheelView==null;
    }

    public static boolean isBzRDel() {
        return bzRDel;
    }

    public static void setBzRDel(boolean bzRDel) {
        MyApplication.bzRDel = bzRDel;
    }
}
