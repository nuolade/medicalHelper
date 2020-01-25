package com.example.wenda01.fragments.xj;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wenda01.R;
import com.example.wenda01.fragments.base.FabFragment;
import com.example.wenda01.utils.TTSUtility;

public class XjContentFragment extends FabFragment implements View.OnClickListener {  //宣教详细内容碎片
    private View view;
    private TextView textTitle;
    private TextView textContent;
    private TextView textClass;

    private String title; //题目
    private String content; //内容
    private String type; //类别

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.xj_content_frag,container,false);
        setSDFab(view,this);
        preWork();
        setInitData();
        showContent();
        return view;
    }

    private void setInitData(){   //获得初始化数据
        title       =getArguments().getString("title");
        content     =getArguments().getString("content");
        type=getArguments().getString("class");
    }

    private void preWork(){
        textContent=view.findViewById(R.id.q_content);
        textTitle=view.findViewById(R.id.q_title);
        textClass=view.findViewById(R.id.q_class);
        textContent.setMovementMethod(new ScrollingMovementMethod());
        textClass.setVisibility(View.VISIBLE);
        textContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TTSUtility.doReport(textContent.getText().toString().trim());
            }
        });
    }

    private void showContent(){   //获得初始化数据
        String [] arr=title.split("/");
        textTitle.setText(arr[0]);
        textContent.setText(content);
        if(type.trim().equals("")){
            type="其他宣教";
        }
        textClass.setText(type);
    }

    @Override
    public void onClick(View v) {
        if (onClickFab(v) ){
            return;
        }
    }
}
