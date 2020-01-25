package com.example.wenda01.activities.yaowu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.beans.yw.MedicineIndex;
import com.example.wenda01.fragments.yw.YwContentFragment;


public class YwContentActivity extends BaseSpeakActivity {  //手机模式下调用的活动，用来加载YwContentFragment
    protected MedicineIndex medicineIndex; //药物索引


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {   //打开碎片
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yw_content);
        Intent intent=getIntent();
        medicineIndex=(MedicineIndex) intent.getSerializableExtra("mIndex");
        YwContentFragment ywContentFragment=new YwContentFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("mIndex",medicineIndex);
        bundle.putSerializable("YWGF",null);
        ywContentFragment.setArguments(bundle);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.yw_content_layout,ywContentFragment);
        transaction.commit();
    }

    public static void actionStart(Context context, MedicineIndex medicineIndex){  //获得参数，启动活动
        Intent intent=new Intent(context,YwContentActivity.class);
        intent.putExtra("mIndex",medicineIndex);
        context.startActivity(intent);
    }

}
