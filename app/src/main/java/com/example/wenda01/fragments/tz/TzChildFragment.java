package com.example.wenda01.fragments.tz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wenda01.R;
import com.example.wenda01.beans.tz.TzChildQuestion;
import com.example.wenda01.beans.tz.TzIndexQuestion;
import com.example.wenda01.fragments.base.TzFabFragment;
import com.example.wenda01.utils.Ks;
import com.example.wenda01.utils.StringUtils;
import com.example.wenda01.utils.TTSUtility;
import com.example.wenda01.utils.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class TzChildFragment extends TzFabFragment implements View.OnClickListener { //体质儿童检测碎片

    private int[] sums=new int [7];     //每组中所有问题的累计分数
    private int[] nums=new int [7];     //每组中问题总数
    private int[] scores=new int[7];  //每组中所有问题的转换分数

    List<TzChildQuestion> list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tz_child_frag,container,false);
        preWork();
        key= Ks.TZ_CH_W;
        setQaFab(view,this);
        showIntor("tzChildIntor");
        return view;
    }

    private void preWork(){
        buttonMY=view.findViewById(R.id.button_meiyou);
        buttonHS=view.findViewById(R.id.button_henshao);
        buttonYS=view.findViewById(R.id.button_youshi);
        buttonJC=view.findViewById(R.id.button_jingchang);
        buttonZS=view.findViewById(R.id.button_zongshi);
        buttonPre=view.findViewById(R.id.button_pre);
        buttonShow=view.findViewById(R.id.q_button_show);
        buttonAnalyze=view.findViewById(R.id.button_analyze);

        recyclerView=view.findViewById(R.id.q_recycler);
        progressBar=view.findViewById(R.id.q_progress);
        textContent=view.findViewById(R.id.q_content);
        textTitle=view.findViewById(R.id.q_title);
        textType=view.findViewById(R.id.q_type);
        layoutFive=view.findViewById(R.id.layout_tz_five_choose);
        layoutSeven=view.findViewById(R.id.layout_tz_child_seven);
        layoutNum=view.findViewById(R.id.q_layout_r);
        textSjws   =view.findViewById(R.id.text_tz_sjws);
        textPx   =view.findViewById(R.id.text_tz_px);
        textJz   =view.findViewById(R.id.text_tz_jz);
        textRz   =view.findViewById(R.id.text_tz_rz);
        textSz   =view.findViewById(R.id.text_tz_sz);
        textXhw   =view.findViewById(R.id.text_tz_xhw);
        textYb   =view.findViewById(R.id.text_tz_yb);

        layoutSjws=view.findViewById(R.id.layout_seven_shengji);
        layoutPx=view.findViewById  (R.id.layout_seven_pixu);
        layoutJz=view.findViewById  (R.id.layout_seven_jizhi);
        layoutRz=view.findViewById  (R.id.layout_seven_rezhi);
        layoutSz=view.findViewById  (R.id.layout_seven_shizhi);
        layoutXhw=view.findViewById (R.id.layout_seven_xinhuowang);
        layoutYb=view.findViewById  (R.id.layout_seven_yibing);
        layoutSjws.setOnClickListener(this);
        layoutPx.setOnClickListener(this);
        layoutJz.setOnClickListener(this);
        layoutRz.setOnClickListener(this);
        layoutSz.setOnClickListener(this);
        layoutXhw.setOnClickListener(this);
        layoutYb.setOnClickListener(this);

        buttonAnalyze.setVisibility(View.INVISIBLE);
        buttonPre.setVisibility(View.INVISIBLE);
        buttonPre.setOnClickListener(this);
        buttonMY.setOnClickListener(this);
        buttonHS.setOnClickListener(this);
        buttonYS.setOnClickListener(this);
        buttonJC.setOnClickListener(this);
        buttonZS.setOnClickListener(this);
        buttonAnalyze.setOnClickListener(this);
        buttonAnalyze.setClickable(false);
        buttonShow.setOnClickListener(this);
        textType.setVisibility(View.VISIBLE);
        textTitle.setText("儿童体质检测");
        progressBar.setMax(100);

        if(getActivity().findViewById(R.id.tz_guide_fragment)==null){
            boxNum=5;
        }else{
            boxNum=10;
        }
        if(getActivity().findViewById(R.id.tz_guide_fragment)!=null){
            isTwoPane=true;
        }else{
            isTwoPane=false;
        }
    }

    protected void getData(){   //数据初始化
        tzIndexQuestionList =new ArrayList<>();
        list= DataSupport.findAll(TzChildQuestion.class);
        for(int j=0;j<list.size();j++){
            TzIndexQuestion tzIndexQuestion =new TzIndexQuestion(list.get(j).getQ(),j,0,false);
            if(j==0){
                tzIndexQuestion.setOn(true);
            }
            tzIndexQuestionList.add(tzIndexQuestion);
        }
        current=0;
        totalQues=43;
        cntQues=0;
        for(int i=0;i<sums.length;i++){
            sums[i]=0;
            scores[i]=0;

        }
        nums=new int []{8,8,8,8,8,7,8};
        s1=new ArrayList<>();
        s2=new ArrayList<>();
        s3=new ArrayList<>();
        s4=new ArrayList<>();
        s5=new ArrayList<>();
        s6=new ArrayList<>();
        s7=new ArrayList<>();
        answers=new ArrayList<>();
        for(int i=0;i<totalQues;i++){
            s1.add(0);
            s2.add(0);
            s3.add(0);
            s4.add(0);
            s5.add(0);
            s6.add(0);
            s7.add(0);
            answers.add(0);
        }
        setAdapter();
        showQuestion();
    }

    protected void showQuestion(){  //显示当前问题
        TTSUtility.getInstance(getContext()).stopSpeaking();
        String spre="问题"+(current+1)+":";
        TzChildQuestion question=list.get(current);
        textContent.setText(spre+question.getQ());
        textType.setText(question.getType());
        reportString="请问您"+question.getQ();
        doFabReport(reportString,true,true);
        showScore();
        showButtonStyle();
        showProgress();
    }

    private void showProgress(){  //更新问题进度
        progressBar.setProgress(100*cntQues/totalQues);
    }

    private void showButtonStyle(){   //更新答案按钮样式
        if(current>0){
            buttonPre.setVisibility(View.VISIBLE);
            buttonPre.setClickable(true);
        }else{
            buttonPre.setVisibility(View.INVISIBLE);
            buttonPre.setClickable(false);
        }
        if(cntQues==totalQues){
            buttonAnalyze.setClickable(true);
            buttonAnalyze.setVisibility(View.VISIBLE);
        }else{
            buttonAnalyze.setVisibility(View.INVISIBLE);
            buttonAnalyze.setClickable(false);
        }
        int i=answers.get(current);
        buttonMY.setBackgroundResource(R.drawable.shape_button_pre);
        buttonHS.setBackgroundResource(R.drawable.shape_button_pre);
        buttonYS.setBackgroundResource(R.drawable.shape_button_pre);
        buttonJC.setBackgroundResource(R.drawable.shape_button_pre);
        buttonZS.setBackgroundResource(R.drawable.shape_button_pre);
        switch (i){
            case 1:
                buttonMY.setBackgroundResource(R.drawable.shape_button_pre_press);
                break;
            case 2:
                buttonHS.setBackgroundResource(R.drawable.shape_button_pre_press);
                break;
            case 3:
                buttonYS.setBackgroundResource(R.drawable.shape_button_pre_press);
                break;
            case 4:
                buttonJC.setBackgroundResource(R.drawable.shape_button_pre_press);
                break;
            case 5:
                buttonZS.setBackgroundResource(R.drawable.shape_button_pre_press);
                break;

        }
    }

    private void showScore(){   //显示每种体质分数
        textSjws.setText(""+sums[6]);
        textPx.setText  (""+sums[0]);
        textJz.setText  (""+sums[1]);
        textSz.setText  (""+sums[2]);
        textXhw.setText (""+sums[3]);
        textRz.setText  (""+sums[4]);
        textYb.setText  (""+sums[5]);
    }

    @Override
    public void onClick(View v) {
        if (onClickFab(v) ){
            return;
        }
        if (onClickAnswer(v)){
            return;
        }
        if(onClickText(v)){
            return;
        }
        switch (v.getId()){
            case R.id.button_analyze:
                doAnalyze(sums,nums,key);
                break;
            case R.id.button_pre :
                doPre();
                break;
            case R.id.q_button_show:
                changeShow();
                break;
        }
    }



    protected void doChoose(int i){   //选择问题的答案
        TzChildQuestion question=list.get(current);
        int i1=question.getIndex1();
        int i2=question.getIndex2();
        int i3=question.getIndex3();
        int i4=question.getIndex4();
        int i5=question.getIndex5();
        int i6=question.getIndex6();
        int i7=question.getIndex7();
//        answers.add(i);
        if(answers.get(current)==0){
            cntQues++;
        }
        answers.set(current,i);

        //1
        if(i1==1){
            sums[0]=sums[0]-s1.get(current)+(i);
            s1.set(current,i);
        }else if(i1==2){
            sums[0]=sums[0]-s1.get(current)+(6-i);
            s1.set(current,6-i);
        }
        //2
        if(i2==1){
            sums[1]=sums[1]-s2.get(current)+(i);
            s2.set(current,i);
        }else if(i2==2){
            sums[1]=sums[1]-s2.get(current)+(6-i);
            s2.set(current,6-i);
        }
        //3
        if(i3==1){
            sums[2]=sums[2]-s3.get(current)+(i);
            s3.set(current,i);
        }else if(i3==2){
            sums[2]=sums[2]-s3.get(current)+(6-i);
            s3.set(current,6-i);
        }
        //4
        if(i4==1){
            sums[3]=sums[3]-s4.get(current)+(i);
            s4.set(current,i);
        }else if(i4==2){
            sums[3]=sums[3]-s4.get(current)+(6-i);
            s4.set(current,6-i);
        }
        //5
        if(i5==1){
            sums[4]=sums[4]-s5.get(current)+(i);
            s5.set(current,i);
        }else if(i5==2){
            sums[4]=sums[4]-s5.get(current)+(6-i);
            s5.set(current,6-i);
        }
        //6
        if(i6==1){
            sums[5]=sums[5]-s6.get(current)+(i);
            s6.set(current,i);
        }else if(i6==2){
            sums[5]=sums[5]-s6.get(current)+(6-i);
            s6.set(current,6-i);
        }
        //7
        if(i7==1){
            sums[6]=sums[6]-s7.get(current)+(i);
            s7.set(current,i);
        }else if(i7==2){
            sums[6]=sums[6]-s7.get(current)+(6-i);
            s7.set(current,6-i);
        }

        if(current<totalQues-1){
            tzIndexQuestionList.get(current).setOn(false);
            tzIndexQuestionList.get(current).setState(1);
            current++;
            tzIndexQuestionList.get(current).setOn(true);
            adapter.notifyDataSetChanged();
            showQuestion();
        }else{
            showScore();
            showButtonStyle();
            showProgress();
        }
        if(totalQues==cntQues){
            ToastUtils.showSWihtSound("您已完成所有的题目，可以进行分析诊断。");
        }

    }

    protected void getSayingWords(String s){   //语音输入后调用
        if(StringUtils.myContainMeiYou(s)){
            buttonMY.performClick();
        }
        else if(StringUtils.myContainHenShao(s)){
            buttonHS.performClick();
        }
        else if(StringUtils.myContainYouShi(s)){
            buttonYS.performClick();
        }
        else if(StringUtils.myContainJingChang(s)){
            buttonJC.performClick();
        }
        else if(StringUtils.myContainZongShi(s)){
            buttonZS.performClick();
        }
        else if(StringUtils.myContainPre(s)){
            buttonPre.performClick();
        }
        else if(StringUtils.myContainAnalyze(s)){
            if(cntQues==totalQues){
                buttonAnalyze.performClick();
                ToastUtils.showSWihtSound("您已完成所有的题目，正为您进行分析诊断。");
            }else{
                ToastUtils.showSWihtSound("您未完成所有的题目，无法进行分析诊断。");
            }
        }
        else if (StringUtils.myContainShengJi(s)) {
            layoutSjws.performClick();
        }
        else if (StringUtils.myContainPiXu(s)) {
            layoutPx.performClick();
        }
        else if (StringUtils.myContainJiZhi(s)) {
            layoutJz.performClick();
        }
        else if (StringUtils.myContainReZhi(s)) {
            layoutRz.performClick();
        }
        else if (StringUtils.myContainShiZhi(s)) {
            layoutSz.performClick();
        }
        else if (StringUtils.myContainXinHuo(s)) {
            layoutXhw.performClick();
        }
        else if (StringUtils.myContainYiBing(s)) {
            layoutYb.performClick();
        }
        else if(StringUtils.myContainNum(s) && StringUtils.myContainNumPre(s)){
            String num=StringUtils.findNum(s).trim();
            Integer integer=Integer.parseInt(num);

            if(integer<totalQues && integer>=0){
                tzIndexQuestionList.get(current).setOn(false);
                current=integer-1;
                tzIndexQuestionList.get(current).setOn(true);
                adapter.notifyDataSetChanged();
                showQuestion();
            }else{
                ToastUtils.showSWihtSound(getResources().getString(R.string.text_ques_num_over_index));
            }
        }
    }
}
