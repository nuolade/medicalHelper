package com.example.wenda01.fragments.tz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wenda01.R;
import com.example.wenda01.beans.tz.TzIndexQuestion;
import com.example.wenda01.beans.tz.TzWxOlQuesResult;
import com.example.wenda01.fragments.base.TzFabFragment;
import com.example.wenda01.utils.Ks;
import com.example.wenda01.utils.StringUtils;
import com.example.wenda01.utils.TTSUtility;
import com.example.wenda01.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TzWxFragment extends TzFabFragment implements View.OnClickListener {  //体质五型检测碎片

    private int[] sums=new int [5];     //每组中所有问题的累计分数
    private int[] nums=new int [5];     //每组中问题总数 13 14 14 11 15
    private int[] res=new int[5]; //问题结果标记

    List<TzWxOlQuesResult.C.Wxtz> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tz_wx_frag,container,false);
        key= Ks.TZ_WX_W;
        preWork();
        setQaFab(view,this);
        getData();
        return view;
    }

    private void preWork(){
        buttonYes=view.findViewById(R.id.button_yes);
        buttonNo=view.findViewById(R.id.button_no);
        buttonPre=view.findViewById(R.id.button_pre);
        buttonShow=view.findViewById(R.id.q_button_show);
        buttonAnalyze=view.findViewById(R.id.button_analyze);

        setAllButtonClickable(false);
        recyclerView=view.findViewById(R.id.q_recycler);
        progressBar=view.findViewById(R.id.q_progress);
        textContent=view.findViewById(R.id.q_content);
        textTitle=view.findViewById(R.id.q_title);
        textType=view.findViewById(R.id.q_type);
        layoutNum=view.findViewById(R.id.q_layout_r);
        layoutFour=view.findViewById(R.id.layout_tz_four_choose);
        layoutFive=view.findViewById(R.id.layout_tz_five);
        textJin=view.findViewById(R.id.text_tz_jin);
        textMu=view.findViewById(R.id.text_tz_mu);
        textShui=view.findViewById(R.id.text_tz_shui);
        textHuo=view.findViewById(R.id.text_tz_huo);
        textTu=view.findViewById(R.id.text_tz_tu);

        layoutJin=view.findViewById  (R.id.layout_five_jin);
        layoutMu=view.findViewById  (R.id.layout_five_mu);
        layoutShui=view.findViewById   (R.id.layout_five_shui);
        layoutHuo=view.findViewById    (R.id.layout_five_huo);
        layoutTu=view.findViewById  (R.id.layout_five_tu);

        buttonAnalyze.setVisibility(View.INVISIBLE);
        buttonPre.setVisibility(View.INVISIBLE);
        textType.setVisibility(View.GONE);
        buttonYes.setOnClickListener(this);
        buttonNo.setOnClickListener(this);
        buttonAnalyze.setOnClickListener(this);
        buttonShow.setOnClickListener(this);
        buttonPre.setOnClickListener(this);

        textTitle.setText("五型体质检测");
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

    private void setAllButtonClickable(boolean b){   //设置所有按钮可用性
        buttonYes.setEnabled(b);
        buttonNo.setEnabled(b);
        buttonPre.setEnabled(b);
        buttonShow.setEnabled(b);
        buttonAnalyze.setEnabled(b);
    }

    protected void getData(){
        tzIndexQuestionList =new ArrayList<>();
        s1=new ArrayList<>();
        s2=new ArrayList<>();
        s3=new ArrayList<>();
        s4=new ArrayList<>();
        s5=new ArrayList<>();
        answers=new ArrayList<>();
        current=0;
        cntQues=0;
        for(int i=0;i<sums.length;i++){
            sums[i]=0;
            res[i]=0;
        }
        sendRequest4TzQues("1");
    }

    protected void parseJson4Ques(String jsonData){  //解析五型体质
        Gson gson=new Gson();
        TzWxOlQuesResult tzResults=gson.fromJson(jsonData, TzWxOlQuesResult.class);
        if(!tzResults.getRes().equals("1001")){
            return;
        }
//        Toast.makeText(getContext(),jsonData,Toast.LENGTH_SHORT).show();
        list= new ArrayList<>();
        List<TzWxOlQuesResult.C.Wxtz> newList=tzResults.getRec().getListWxtz();
        for(int i=0;i<newList.size();i++){
            TzWxOlQuesResult.C.Wxtz wxtz=newList.get(i);
            nums[wxtz.getBody_id()-19]++;
            list.add(wxtz);
            TzIndexQuestion tzIndexQuestion =new TzIndexQuestion(newList.get(i).getQuestion(),i,0,false);
            if(i==0){
                tzIndexQuestion.setOn(true);
            }
            tzIndexQuestionList.add(tzIndexQuestion);

        }

        totalQues=newList.size();
        for(int i=0;i<totalQues;i++){
            s1.add(0);
            s2.add(0);
            s3.add(0);
            s4.add(0);
            s5.add(0);
            answers.add(-1);
        }
        showQuestion();
        setAllButtonClickable(true);
        setAdapter();
    }

    protected void showQuestion(){
        TTSUtility.getInstance(getContext()).stopSpeaking();
        String spre="问题"+(current+1)+":";
        TzWxOlQuesResult.C.Wxtz question=list.get(current);
        textContent.setText(spre+question.getQuestion()+"？");
        reportString="请问您"+question.getQuestion();
        doFabReport(reportString,true,true);
        showScore();
        showButtonStyle();
        showProgress();
    }

    private void showButtonStyle(){
        if(current>0){
            buttonPre.setVisibility(View.VISIBLE);
            buttonPre.setEnabled(true);
        }else{
            buttonPre.setVisibility(View.INVISIBLE);
            buttonPre.setEnabled(false);
        }
        if(cntQues==totalQues){
            buttonAnalyze.setEnabled(true);
            buttonAnalyze.setVisibility(View.VISIBLE);
        }else{
            buttonAnalyze.setVisibility(View.INVISIBLE);
            buttonAnalyze.setEnabled(false);
        }
    }

    private void showScore(){
        textJin.setText(""+sums[0]);
        textMu.setText(""+sums[1]);
        textShui.setText(""+sums[2]);
        textHuo.setText(""+sums[3]);
        textTu.setText(""+sums[4]);
    }

    private void showProgress(){
        progressBar.setProgress(100*cntQues/totalQues);
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




    protected void doChoose(int i){
        TzWxOlQuesResult.C.Wxtz question=list.get(current);
        int i1=question.getBody_id()-19;

//        answers.add(i);
        if(answers.get(current)==-1){
            cntQues++;
        }
        answers.set(current,i);

        //1
        if(i1==0){
            sums[0]=sums[0]-s1.get(current)+(i);
            s1.set(current,i);
        }
        //2
        if(i1==1){
            sums[1]=sums[1]-s2.get(current)+(i);
            s2.set(current,i);
        }
        //3
        if(i1==2){
            sums[2]=sums[2]-s3.get(current)+(i);
            s3.set(current,i);
        }
        //4
        if(i1==3){
            sums[3]=sums[3]-s4.get(current)+(i);
            s4.set(current,i);
        }
        //5
        if(i1==4){
            sums[4]=sums[4]-s5.get(current)+(i);
            s5.set(current,i);
        }


        if(current<totalQues-1){
            tzIndexQuestionList.get(current).setOn(false);
            tzIndexQuestionList.get(current).setState(2-i);
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

    protected void getSayingWords(String s){
        if(StringUtils.myContainYes(s)){
            buttonYes.performClick();
        }
        else if(StringUtils.myContainNo(s)){
            buttonNo.performClick();
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
