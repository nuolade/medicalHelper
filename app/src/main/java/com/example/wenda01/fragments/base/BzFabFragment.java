package com.example.wenda01.fragments.base;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.wenda01.R;
import com.example.wenda01.activities.bingzheng.BzContentActivity;
import com.example.wenda01.beans.bz.BzSymptom;
import com.example.wenda01.fragments.bz.BzContentFragment;
import com.example.wenda01.utils.MySimHash;
import com.example.wenda01.utils.StringUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

public class BzFabFragment extends FabFragment {  //具有辅助功能的病症父类碎片


    protected String [] words={"判断","选择","检测","测试"};

    protected void doOpenContentFragment(BzSymptom bzSymptom){  //打开病症详细信息
        if(isTwoPane){
            BzContentFragment bzContentFragment=new BzContentFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("id", bzSymptom.getIdd());
            bundle.putInt("path", bzSymptom.getMaxPath());
            bundle.putString("name", bzSymptom.getName());
            bundle.putString("intor", bzSymptom.getIntor());
            bzContentFragment.setArguments(bundle);

            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.bz_content_layout,bzContentFragment);
            transaction.commit();
        }else {
            BzContentActivity.actionStart(getActivity(), bzSymptom.getIdd(), bzSymptom.getMaxPath(), bzSymptom.getName(), bzSymptom.getIntor());
        }
    }

    protected void doFindSym(String s){  //查找病症
        List<BzSymptom> list= DataSupport.findAll(BzSymptom.class);
        s= StringUtils.myTrim(s,StringUtils.listSearch);
        s=StringUtils.myTrim(s,words);
        int maxD=Integer.MAX_VALUE;
        int index=-1;
        for(int i=0;i<list.size();i++){
            String name=list.get(i).getName();
            int d= MySimHash.getD(s,name);
            if(d<maxD){
                maxD=d;
                index=i;
            }
        }
        if(index==-1){
            return;
        }
        doOpenContentFragment(list.get(index));
    }
}
