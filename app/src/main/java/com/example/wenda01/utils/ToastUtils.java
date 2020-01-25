package com.example.wenda01.utils;

import android.widget.Toast;

import com.example.wenda01.MyApplication;

public class ToastUtils {   //Toast工具

    private static Toast mToast;

    public static void showS(String s){   //纯文字显示
        if(mToast==null){
            mToast=Toast.makeText(MyApplication.getContext(),"",Toast.LENGTH_SHORT);
        }
        mToast.setText(s);
        mToast.show();
    }

    public static void showSWihtSound(String s){   //文字与语音显示
        if(mToast==null){
            mToast=Toast.makeText(MyApplication.getContext(),"",Toast.LENGTH_SHORT);
        }
        mToast.setText(s);
        mToast.show();
        if(MyApplication.isIsSoundOpen()){
            TTSUtility.getInstance(MyApplication.getContext()).speaking(s);
        }
    }
}
