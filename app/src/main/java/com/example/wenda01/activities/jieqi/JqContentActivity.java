package com.example.wenda01.activities.jieqi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.fragments.jq.JqContentFragment;

public class JqContentActivity extends BaseSpeakActivity {  //手机模式下调用的活动，用来加载JqContentFragment
    private int id; //节气id
    private boolean isTwoPane;  //是否为平板

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {   //打开碎片
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jq_content);
        Intent intent=getIntent();
        id=intent.getIntExtra("id",1);
        isTwoPane=intent.getBooleanExtra("isTwoPane",true);
        JqContentFragment jqContentFragment=new JqContentFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        bundle.putBoolean("isTwoPane",isTwoPane);
        jqContentFragment.setArguments(bundle);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.jq_content_layout,jqContentFragment);
        transaction.commit();
//        preWork();
    }
    public static void actionStart(Context context, int id,boolean isTwoPane){  //获得参数，启动活动
        Intent intent=new Intent(context,JqContentActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("isTwoPane",isTwoPane);
        context.startActivity(intent);
    }

}
