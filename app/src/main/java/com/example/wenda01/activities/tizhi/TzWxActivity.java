package com.example.wenda01.activities.tizhi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.fragments.tz.TzWxFragment;

public class TzWxActivity extends BaseSpeakActivity {  //手机模式下调用的活动，用来加载TzWxFragment

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {   //打开碎片
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tz_wx);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.tz_content_layout,new TzWxFragment());
        transaction.commit();
    }

    public static void actionStart(Context context){   //启动活动
        Intent intent=new Intent(context,TzWxActivity.class);
        context.startActivity(intent);
    }
}
