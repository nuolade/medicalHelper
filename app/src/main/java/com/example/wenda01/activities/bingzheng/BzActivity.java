package com.example.wenda01.activities.bingzheng;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.utils.ExcelReader;

public class BzActivity extends BaseSpeakActivity {  //病症主活动

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bz);
        isF();
    }

    private void isF(){  //判断是否是首次打开该模块，从而加载病症数据
        SharedPreferences sharedPreferences=getSharedPreferences("isFirst",MODE_PRIVATE);
        boolean isFirst=sharedPreferences.getBoolean("isFirstBZ",true);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(isFirst){
            loadData();
            editor.putBoolean("isFirstBZ",false);
            editor.commit();
        }
    }

    private void loadData(){   //加载病症数据（所有病症，各个病症的相关问题）
        ExcelReader.readExcelBzSym(this,"s0.xls");
        ExcelReader.readExcelBzQues(this,"s70.xls");
    }
}
