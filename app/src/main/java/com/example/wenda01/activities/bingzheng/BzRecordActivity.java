package com.example.wenda01.activities.bingzheng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.fragments.bz.BzRecordFragment;

public class BzRecordActivity extends BaseSpeakActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {  //打开碎片
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bz_record);

        BzRecordFragment bzRecordFragment=new BzRecordFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.bz_content_layout,bzRecordFragment);
        transaction.commit();

    }

    public static void actionStart(Context context){  //启动活动
        Intent intent=new Intent(context,BzRecordActivity.class);
//        intent.putExtra("key",key);
        context.startActivity(intent);
    }
}
