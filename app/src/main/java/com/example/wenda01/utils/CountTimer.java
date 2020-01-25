package com.example.wenda01.utils;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;

public class CountTimer extends CountDownTimer {   //时间计数器
    private Context context;
    private Activity activity;

    /**
     *
     * @param millisInFuture 倒计时总时间
     * @param countDownInterval 渐变时间
     * @param activity
     */
    public CountTimer(long millisInFuture, long countDownInterval, Activity activity) {
        super(millisInFuture, countDownInterval);
        this.activity = activity;
    }

    //计时完毕
    @Override
    public void onFinish() {   //时间计数器
//        activity.finish();
        ActivityCollector.finishAll();
        AccountKeeper.exit();
    }

    //计时过程显示
    @Override
    public void onTick(long l) {  //计时过程中显示

    }
}
