package com.example.wenda01.utils;

import android.app.Activity;

import com.example.wenda01.activities.WelcomeActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {   //用来管理所有的活动
    public static List<Activity> activities=new ArrayList<>();  //收集活动
    public static void addActivity(Activity activity){   //添加活动
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){   //移除活动
        activities.remove(activity);
    }
    public static void finishAll(){   //结束所有活动
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }
    public static void finishToHome(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                if(!activity.getClass().equals(WelcomeActivity.class)){
                    activity.finish();
                }

            }
        }
        activities.clear();
    }


}
