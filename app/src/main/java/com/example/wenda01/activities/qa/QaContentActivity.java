package com.example.wenda01.activities.qa;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wenda01.R;
import com.example.wenda01.beans.qa.QaSession;
import com.example.wenda01.fragments.qa.QaContentFragment;

public class QaContentActivity extends AppCompatActivity {//手机模式下调用的活动，用来加载QaContentFragment

    private QaSession qaSession;    //知识问答会话

    @Override
    protected void onCreate(Bundle savedInstanceState) {   //打开碎片
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa_content);

        Intent intent=getIntent();
        qaSession=(QaSession) intent.getSerializableExtra("session");

        QaContentFragment qaContentFragment=new QaContentFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("session",qaSession);
        qaContentFragment.setArguments(bundle);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.qa_content_layout,qaContentFragment);
        transaction.commit();
    }

    public static void actionStart(Context context, QaSession qaSession){ //获得参数，启动活动
        Intent intent=new Intent(context,QaContentActivity.class);
        intent.putExtra("session",qaSession);
        context.startActivity(intent);
    }
}
