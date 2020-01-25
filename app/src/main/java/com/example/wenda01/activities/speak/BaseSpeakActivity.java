package com.example.wenda01.activities.speak;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.example.wenda01.utils.ActivityCollector;
import com.example.wenda01.utils.CountTimer;

public class BaseSpeakActivity extends SpeakActivity {  //大多数活动的父类，用于检测活动的相应情况，从而做出动作
    private long waitTime=1000*60*5; //检测的相应时间
    private CountTimer countTimer;  //时间计算器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        doTimeCount();
    }

    private void doTimeCount(){  //初始化时间记录器
        countTimer=new CountTimer(waitTime,1000,BaseSpeakActivity.this);
    }

    private void timeStart(){  //初始化时间记录器
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                countTimer.start();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {  //判断是否有触碰动作
        switch (ev.getAction()){
            //获取触摸动作，开始计时
            case MotionEvent.ACTION_UP:
                countTimer.start();
                break;
            //其他动作取消计时
            default:
                countTimer.cancel();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        countTimer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timeStart();
    }
}
