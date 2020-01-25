package com.example.wenda01.activities.jieqi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;

public class JqActivity extends AppCompatActivity {   //节气主活动

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jq);
    }

    @Override
    protected void onDestroy() {//清空wheelview的内容
        super.onDestroy();
        MyApplication.setWheelView(null);
    }
}

