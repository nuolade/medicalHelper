package com.example.wenda01.activities.tizhi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.fragments.tz.TzAdultFragment;

public class TzAdultActivity extends BaseSpeakActivity {   //手机模式下调用的活动，用来加载TzAdultFragment
    private int key; //体质子模块的key，为Ks中的值

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {   //打开碎片
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tz_adult);

        Intent intent=getIntent();
        key=intent.getIntExtra("key",0);

        TzAdultFragment tzAdultFragment=new TzAdultFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("key",key);
        tzAdultFragment.setArguments(bundle);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.tz_content_layout,tzAdultFragment);
        transaction.commit();
    }

    public static void actionStart(Context context, int key){   //获得参数，启动活动
        Intent intent=new Intent(context,TzAdultActivity.class);
        intent.putExtra("key",key);
        context.startActivity(intent);
    }
}
