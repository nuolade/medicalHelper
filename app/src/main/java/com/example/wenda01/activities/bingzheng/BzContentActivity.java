package com.example.wenda01.activities.bingzheng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.fragments.bz.BzContentFragment;

public class BzContentActivity extends BaseSpeakActivity {  //手机模式下调用的活动，用来加载BzContentFragment
    private int staId;  //首个问题id
    private int maxPath;    //最长问题路径数
    private String symptomName; //症状名称
    private String symIntor;    //症状简介

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {  //打开碎片
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bz_content );

        Intent intent=getIntent();
        staId=intent.getIntExtra("symptomId",0);
        maxPath=intent.getIntExtra("maxPath",1);
        symptomName=intent.getStringExtra("symptomName");
        symIntor=intent.getStringExtra("intor");

        BzContentFragment bzContentFragment=new BzContentFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("id",staId);
        bundle.putInt("path",maxPath);
        bundle.putString("name",symptomName);
        bundle.putString("intor",symIntor);
        bzContentFragment.setArguments(bundle);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.bz_content_layout,bzContentFragment);
        transaction.commit();
    }

    public static void actionStart(Context context, int id, int path, String name,String intor){  //获得参数，启动活动
        Intent intent=new Intent(context,BzContentActivity.class);
        intent.putExtra("symptomId",id);
        intent.putExtra("maxPath",path);
        intent.putExtra("symptomName",name);
        intent.putExtra("intor",intor);
        context.startActivity(intent);
    }
}
