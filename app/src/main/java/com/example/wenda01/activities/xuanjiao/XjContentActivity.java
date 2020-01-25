package com.example.wenda01.activities.xuanjiao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.fragments.xj.XjContentFragment;

public class XjContentActivity extends BaseSpeakActivity {  //手机模式下调用的活动，用来加载XjContentFragment

    private String title; //宣教知识题目
    private String content; //宣教知识内容
    private String type;    //宣教知识类别
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {   //打开碎片
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xj_content);
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        content=intent.getStringExtra("content");
        type=intent.getStringExtra("class");

        XjContentFragment xjContentFragment=new XjContentFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title",title);
        bundle.putString("content",content);
        bundle.putString("class",type);
        xjContentFragment.setArguments(bundle);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.xj_content_layout,xjContentFragment);
        transaction.commit();
    }

    public static void actionStart(Context context, String title,String content,String type){   //获得参数，启动活动
        Intent intent=new Intent(context,XjContentActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.putExtra("class",type);
        context.startActivity(intent);
    }

}
