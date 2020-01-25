package com.example.wenda01.fragments.tz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wenda01.R;
import com.example.wenda01.activities.tizhi.TzAdultActivity;
import com.example.wenda01.activities.tizhi.TzChildActivity;
import com.example.wenda01.activities.tizhi.TzKeyActivity;
import com.example.wenda01.activities.tizhi.TzOneActivity;
import com.example.wenda01.activities.tizhi.TzRecordActivity;
import com.example.wenda01.activities.tizhi.TzWxActivity;
import com.example.wenda01.utils.DialogUtils;
import com.example.wenda01.utils.Ks;

public class TzGuideFragment extends Fragment implements View.OnClickListener {   //体质引导碎片
    private View view;
    private boolean isTwoPane;

    private FrameLayout frameOne;
    private FrameLayout frameAll;
    private FrameLayout frameWx;
    private FrameLayout frameAdult;
    private ImageView imageOne;
    private ImageView imageAll;
    private ImageView imageWx;
    private ImageView imageAdult;
    private LinearLayout linearOne;
    private LinearLayout linearAll;
    private LinearLayout linearWx;
    private LinearLayout linearAdult;

    private Button buttonKey;
    private Button buttonAdult;
    private Button buttonChild;

    private Button buttonYangXu;
    private Button buttonYinXu;
    private Button buttonQiXu;
    private Button buttonTanShi;
    private Button buttonShiRe;
    private Button buttonXueYu;
    private Button buttonQiYu;
    private Button buttonXueXu;

    private Button buttonOM;
    private Button buttonOW;
    private Button buttonYM;
    private Button buttonYW;
    private Button buttonWx;

    private Button buttonSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tz_guide_frag,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.tz_content_layout)!=null){
            isTwoPane=true;
        }else {
            isTwoPane=false;
        }
        showIntor();
        preWork();
    }

    private void showIntor(){   //体质引导碎片
        DialogUtils.showIntorDialog(getActivity(),getResources().getString(R.string.text_tz_intor));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_tz_key:
                openKey();
                break;
            case R.id.button_tz_oM:
                openAdult(Ks.TZ_OM_W);
                break;
            case R.id.button_tz_oW:
                openAdult(Ks.TZ_OW_W);
                break;
            case R.id.button_tz_yM:
                openAdult(Ks.TZ_YM_W);
                break;
            case R.id.button_tz_yW:
                openAdult(Ks.TZ_YW_W);
                break;
            case R.id.button_tz_adult:
                openAdult(Ks.TZ_AD_W);
                break;
            case R.id.button_tz_child:
                openChild();
                break;
            case R.id.button_tz_wx:
                openWx();
                break;

            case R.id.button_tz_yangxu:
                openOne(0);
                break;
            case R.id.button_tz_yinxu:
                openOne(1);
                break;
            case R.id.button_tz_qixu:
                openOne(2);
                break;
            case R.id.button_tz_tanshi:
                openOne(3);
                break;
            case R.id.button_tz_shire:
                openOne(4);
                break;
            case R.id.button_tz_xueyu:
                openOne(5);
                break;
            case R.id.button_tz_qiyu:
                openOne(6);
                break;
            case R.id.button_tz_xuexu:
                openOne(7);
                break;

            case R.id.button_tz_record:
                doSearch();
                break;


            case R.id.layout_tz_one:
                changeVis(imageOne,linearOne);
                break;
            case R.id.layout_tz_all:
                changeVis(imageAll,linearAll);
                break;
            case R.id.layout_tz_wx:
                changeVis(imageWx,linearWx);
                break;
            case R.id.layout_tz_adult:
                changeVis(imageAdult,linearAdult);
                break;


        }
    }

    private void changeVis(ImageView imageView,LinearLayout layout){  //更改子布局可见性
        if(layout.getVisibility()==View.GONE){
            layout.setVisibility(View.VISIBLE);
            imageView.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.up));
        }else{
            layout.setVisibility(View.GONE);
            imageView.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.down));
        }
    }

    private void doSearch(){   //进行体质查询
        if(isTwoPane){
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
//            Fragment fragment=fragmentManager.findFragmentById()
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.tz_content_layout,new TzRecordFragment());
//            transaction.addToBackStack(null);
            transaction.commit();
        }else {
            TzRecordActivity.actionStart(getActivity());
        }
    }

    private void openKey(){  //打开成人快捷体质检测
        if(isTwoPane){
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
//            transaction.replace(R.id.tz_content_layout,new TzAllFragment());
            transaction.replace(R.id.tz_content_layout,new TzKeyFragment());
            transaction.commit();
        }else {
            TzKeyActivity.actionStart(getActivity());
        }
    }
    private void openAdult(int key){  //打开成人常规体质检测
        if(isTwoPane){
            TzAdultFragment tzAdultFragment=new TzAdultFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("key",key);
            tzAdultFragment.setArguments(bundle);

            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.tz_content_layout,tzAdultFragment);
//            transaction.addToBackStack(null);
            transaction.commit();
        }else {
            TzAdultActivity.actionStart(getActivity(),key);
        }
    }
    private void openChild(){   //打开儿童体质检测
        if(isTwoPane){
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.tz_content_layout,new TzChildFragment());
            transaction.commit();
        }else {
            TzChildActivity.actionStart(getActivity());
        }
    }

    private void openWx(){  //打开五型体质检测
        if(isTwoPane){
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.tz_content_layout,new TzWxFragment());
            transaction.commit();
        }else {
            TzWxActivity.actionStart(getActivity());
        }
    }

    private void openOne(int key){   //打开单项体质检测
        if(isTwoPane){
            TzOneFragment tzOneFragment=new TzOneFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("key",key);
            tzOneFragment.setArguments(bundle);

            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.tz_content_layout,tzOneFragment);
//            transaction.addToBackStack(null);
            transaction.commit();
//            tzOneFragment.refresh(key);
        }else {
            TzOneActivity.actionStart(getActivity(),key);
        }


    }

    private void preWork(){
//        layoutAll=view.findViewById(R.id.layout_tz_over);
//        layoutOnes=view.findViewById(R.id.layout_tz_ones);
        buttonKey=view.findViewById(     R.id.button_tz_key);
//        buttonOne=view.findViewById(     R.id.button_tz_one);
        buttonAdult=view.findViewById(     R.id.button_tz_adult);
        buttonChild=view.findViewById(     R.id.button_tz_child);
        buttonYangXu=view.findViewById(  R.id.button_tz_yangxu);
        buttonYinXu=view.findViewById(   R.id.button_tz_yinxu);
        buttonQiXu=view.findViewById(    R.id.button_tz_qixu);
        buttonTanShi=view.findViewById(  R.id.button_tz_tanshi);
        buttonShiRe=view.findViewById(   R.id.button_tz_shire);
        buttonXueYu=view.findViewById(   R.id.button_tz_xueyu);
        buttonQiYu=view.findViewById(    R.id.button_tz_qiyu);
        buttonXueXu=view.findViewById(   R.id.button_tz_xuexu);
//        buttonBack=view.findViewById(    R.id.button_tz_back);
        buttonSearch=view.findViewById(R.id.button_tz_record);
        buttonWx=view.findViewById(R.id.button_tz_wx);
        buttonOM=view.findViewById(R.id.button_tz_oM);
        buttonOW=view.findViewById(R.id.button_tz_oW);
        buttonYM=view.findViewById(R.id.button_tz_yM);
        buttonYW=view.findViewById(R.id.button_tz_yW);

        frameOne=view.findViewById(R.id.layout_tz_one);
        frameAll=view.findViewById(R.id.layout_tz_all);
        frameWx=view.findViewById(R.id.layout_tz_wx);
        frameAdult=view.findViewById(R.id.layout_tz_adult);
        imageOne=view.findViewById(R.id.image_one);
        imageAll=view.findViewById(R.id.image_all);
        imageWx=view.findViewById(R.id.image_wx);
        imageAdult=view.findViewById(R.id.image_adult);
        linearOne=view.findViewById(R.id.layout_tz_ones);
        linearAll=view.findViewById(R.id.layout_tz_alls);
        linearWx=view.findViewById(R.id.layout_tz_wxs);
        linearAdult=view.findViewById(R.id.layout_tz_adults);
        frameOne.setOnClickListener(this);
        frameAll.setOnClickListener(this);
        frameWx.setOnClickListener(this);
        frameAdult.setOnClickListener(this);


        buttonWx.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonKey.setOnClickListener(this);
//        buttonOne.setOnClickListener(this);
        buttonAdult.setOnClickListener(this);
        buttonChild.setOnClickListener(this);
        buttonYangXu.setOnClickListener(this);
        buttonYinXu.setOnClickListener(this);
        buttonQiXu.setOnClickListener(this);
        buttonTanShi.setOnClickListener(this);
        buttonShiRe.setOnClickListener(this);
        buttonXueYu.setOnClickListener(this);
        buttonQiYu.setOnClickListener(this);
        buttonXueXu.setOnClickListener(this);
//        buttonBack.setOnClickListener(this);
        buttonOM.setOnClickListener(this);
        buttonOW.setOnClickListener(this);
        buttonYM.setOnClickListener(this);
        buttonYW.setOnClickListener(this);
    }
}

