package com.example.wenda01.fragments.base;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.wenda01.R;
import com.example.wenda01.activities.xuanjiao.XjContentActivity;
import com.example.wenda01.beans.xj.XjCkKnowledge;
import com.example.wenda01.fragments.xj.XjContentFragment;

public class XjFabFragment extends FabFragment {  //具有辅助功能的宣教父类碎片
    protected void openKnow(XjCkKnowledge xjCkKnowledge){  //打开宣教详细信息
        if(isTwoPane){
            XjContentFragment xjContentFragment=new XjContentFragment();
            Bundle bundle=new Bundle();
            bundle.putString("title", xjCkKnowledge.getTitle());
            bundle.putString("content", xjCkKnowledge.getContent());
            bundle.putString("class", xjCkKnowledge.getLabel());
            xjContentFragment.setArguments(bundle);

            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.xj_content_layout,xjContentFragment);
            transaction.commit();
        }else{
            XjContentActivity.actionStart(getActivity(), xjCkKnowledge.getTitle(), xjCkKnowledge.getContent(), xjCkKnowledge.getLabel());
        }
    }
}
