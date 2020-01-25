package com.example.wenda01.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

public class TTSUtility {   //文字播报工具
    // 发音人
    public final static String[] COLOUD_VOICERS_VALUE = {"xiaoyan", "xiaoyu", "catherine", "henry",
            "vimary", "vixy", "xiaoqi", "vixf", "xiaomei","xiaolin", "xiaorong", "xiaoqian", "xiaokun",
            "xiaoqiang", "vixying", "xiaoxin", "nannan", "vils",};

    private static final String TAG = "TTSUtility";
    private static SpeechSynthesizer mTts;
    private static Context mContext;
    private volatile static TTSUtility instance;
    private static View mView;
    private static int state=0; //0表示正常结束，1表示输入语音结束
    private static Handler mHandler;
    private static boolean isShowing;

    private static SynthesizerListener mTtsListener=new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
            Log.d(TAG, "开始播放");

        }

        @Override
        public void onBufferProgress(int percent, int i1, int i2, String s) {
            // TODO 缓冲的进度
            Log.d(TAG, "缓冲 : " + percent);
        }

        @Override
        public void onSpeakPaused() {
            Log.d(TAG, "暂停播放");

        }

        @Override
        public void onSpeakResumed() {
            Log.d(TAG, "继续播放");
        }

        @Override
        public void onSpeakProgress(int percent, int i1, int i2) {
            // TODO 说话的进度
            Log.d(TAG, "合成 : " + percent);
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                Log.d(TAG, "播放完成");
                if(mHandler!=null && isShowing){
                    Message message=new Message();
                    message.what=Ks.SAYKEY;
                    mHandler.sendMessage(message);
                }
//                if(state==0){
//                    Toast.makeText(mContext,"do sth"+state,Toast.LENGTH_SHORT).show();
                    doSth();
//                }
//                setState(0);


            } else if (error != null) {
//                Toast.makeText(mContext,"error"+state,Toast.LENGTH_SHORT).show();
                Log.d(TAG, error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };
    private static void doSth(){
        mView.performClick();
    }

    private TTSUtility(Context context,View view){
        mContext = context;
        mView=view;
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(mContext, new InitListener() {
            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                    Log.d("fjj", "初始化失败,错误码：" + code);
                }
                Log.d("fjj", "初始化失败,q错误码：" + code);
            }
        });
    }
    private TTSUtility(Context context, Handler handler){
        mContext = context;
        mHandler=handler;
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(mContext, new InitListener() {
            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                    Log.d("fjj", "初始化失败,错误码：" + code);
                }
                Log.d("fjj", "初始化失败,q错误码：" + code);
            }
        });
    }
    private TTSUtility(Context context){
        mContext = context;
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(mContext, new InitListener() {
            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                    Log.d("fjj", "初始化失败,错误码：" + code);
                }
                Log.d("fjj", "初始化失败,q错误码：" + code);
            }
        });
    }
    public static TTSUtility getInstance(Context context,View view){   //获得单例
        if (instance == null) {
            synchronized (TTSUtility.class) {
                if (instance == null) {
                    instance = new TTSUtility(context,view);
                }
            }
        }
        return  instance;
    }
    public static TTSUtility getInstance(Context context,Handler handler){   //获得单例
        mHandler=handler;
        if (instance == null) {
            synchronized (TTSUtility.class) {
                if (instance == null) {
                    instance = new TTSUtility(context,handler);
                }
            }
        }
        return  instance;
    }
    public static TTSUtility getInstance(Context context){   //获得单例
        if (instance == null) {
            synchronized (TTSUtility.class) {
                if (instance == null) {
                    instance = new TTSUtility(context);
                }
            }
        }
        return  instance;
    }

    /**
     * 停止语音播报
     */
    public static void stopSpeaking() {  //停止语音播报
        // 对象非空并且正在说话
        if (null != mTts && mTts.isSpeaking()) {
            // 停止说话
            mTts.stopSpeaking();
        }
    }

    /**
     * 判断当前有没有说话
     *
     * @return
     */
    public  static boolean isSpeaking() {  //判断是否在播报
        if (null != mTts) {
            return mTts.isSpeaking();
        } else {
            return false;
        }
    }

    /**
     * 开始合成
     *
     * @param text
     */
    public void speaking(String text) {  //播报文字
        isShowing=false;
        state=0;
        if (TextUtils.isEmpty(text))
            return;
        int code = mTts.startSpeaking(text, mTtsListener);

        Log.d("fjj", "-----" + code + "++++++++++");

        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                Toast.makeText(mContext, "没有安装语音+ code = " + code, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "语音合成失败,错误码: " + code, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 开始合成
     *
     * @param text
     */
    public void speaking(String text,boolean f) {  //播报文字，并判断是否需要显示对话框提示
        isShowing=f;
        state=0;
        if (TextUtils.isEmpty(text))
            return;
        int code = mTts.startSpeaking(text, mTtsListener);

        Log.d("fjj", "-----" + code + "++++++++++");

        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                Toast.makeText(mContext, "没有安装语音+ code = " + code, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "语音合成失败,错误码: " + code, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {  //设置参数
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 引擎类型 网络
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, COLOUD_VOICERS_VALUE[0]);
        // 设置语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        // 设置音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        // 设置音量
        mTts.setParameter(SpeechConstant.VOLUME, "100");
        // 设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/KRobot/wavaudio.pcm");
        // 背景音乐  1有 0 无
        // mTts.setParameter("bgs", "1");
    }


    public static void doReport(String s){  //进行播报
        if(MyApplication.isIsSoundOpen()){
            TTSUtility ttsUtility=TTSUtility.getInstance(MyApplication.getContext());
            if(ttsUtility.isSpeaking()){
                TTSUtility.getInstance(MyApplication.getContext()).stopSpeaking();
            }else{
                ttsUtility.speaking(s,true);
            }
        }
        else{
//            ToastUtils.showS(MyApplication.getContext().getResources().getString(R.string.text_sound_intor));
        }
    }
}
