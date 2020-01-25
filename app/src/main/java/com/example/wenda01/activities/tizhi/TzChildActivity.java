package com.example.wenda01.activities.tizhi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.fragments.tz.TzChildFragment;

public class TzChildActivity extends BaseSpeakActivity {  //手机模式下调用的活动，用来加载TzChildFragment
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {   //打开碎片
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tz_child);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.tz_content_layout,new TzChildFragment());
        transaction.commit();
    }

    public static void actionStart(Context context){   //获得参数，启动活动
        Intent intent=new Intent(context,TzChildActivity.class);

        context.startActivity(intent);
    }
}
