package com.example.wenda01.activities.yaowu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.utils.ExcelReader;

public class YwActivity extends BaseSpeakActivity {   //药物主活动


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yw);
        isF();
    }

    private void isF(){  //判断是否是首次打开该模块，从而加载药物数据
        SharedPreferences sharedPreferences=getSharedPreferences("isFirst",MODE_PRIVATE);
        boolean isFirst=sharedPreferences.getBoolean("isFirstYW",true);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(isFirst){
            loadData();
            editor.putBoolean("isFirstYW",false);
            editor.commit();
        }
    }

    private void loadData(){   //加载药物数据（所有药物索引，药物功效，药物间相互作用）
        ExcelReader.readExcelMIndex(this,"mIndex.xls");
        ExcelReader.readExcelMEffect(this,"mEffect.xls");
        ExcelReader.readExcelMInter(this,"mInter.xls");
    }
}
