package com.example.wenda01.activities.tizhi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;

public class TzKeyActivity extends BaseSpeakActivity {  //手机模式下调用的活动，用来加载TzKeyFragment
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {   //打开碎片
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tz_key);
//        preWork();
    }

    public static void actionStart(Context context){   //获得参数，启动活动
        Intent intent=new Intent(context,TzKeyActivity.class);

        context.startActivity(intent);
    }
}
