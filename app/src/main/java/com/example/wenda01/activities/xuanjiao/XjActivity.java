package com.example.wenda01.activities.xuanjiao;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.utils.ExcelReader;

public class XjActivity extends BaseSpeakActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xj);
        isF();
    }

    private void isF(){  //判断是否是首次打开该模块，从而加载宣教数据
        SharedPreferences sharedPreferences=getSharedPreferences("isFirst",MODE_PRIVATE);
        boolean isFirst=sharedPreferences.getBoolean("isFirstXJ",true);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(isFirst){
            loadData();
            editor.putBoolean("isFirstXJ",false);
            editor.commit();
        }
    }

    private void loadData(){  //加载宣教数据（孕产知识）
        ExcelReader.readExcelXjCk(this,"xjCk.xls");
    }

}
