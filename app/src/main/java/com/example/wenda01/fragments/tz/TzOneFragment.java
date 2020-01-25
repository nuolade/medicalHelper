package com.example.wenda01.fragments.tz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wenda01.R;
import com.example.wenda01.beans.tz.TzIndexQuestion;
import com.example.wenda01.beans.tz.TzOneQuestion;
import com.example.wenda01.beans.tz.TzOneIntorduction;
import com.example.wenda01.beans.tz.TzOneRecord;
import com.example.wenda01.fragments.base.TzFabFragment;
import com.example.wenda01.utils.DialogUtils;
import com.example.wenda01.utils.TTSUtility;
import com.example.wenda01.utils.TimeUtils;
import com.example.wenda01.utils.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class TzOneFragment extends TzFabFragment implements View.OnClickListener {   //体质单项检测碎片
    private List<TzIndexQuestion> tzIndexQuestionList =new ArrayList<>();
    private String[] tizhi={"阳虚","阴虚","气虚","痰湿","湿热","血瘀","气郁","血虚"};
    private int countNo; //回答否数量
    private int countYes; //回答是数量
    private int countNothing; //未回答数量
    private int key; //模块key
    private int score; //分数

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tz_one_frag,container,false);
        preWork();
        setQaFab(view,this);
        this.key=getArguments().getInt("key");
        textTitle.setText(tizhi[key]+"质检测");
        initQuestion();
        showIntor();
        showQuestion();
        return view;
    }


    private void preWork(){
        textTitle=view.findViewById(R.id.q_title);
        textContent=view.findViewById(R.id.q_content);
        progressBar=view.findViewById(R.id.q_progress);
        buttonShow=view.findViewById(R.id.q_button_show);
        buttonPre=view.findViewById( R.id.button_pre);
        buttonNo=view.findViewById(  R.id.button_no);
        buttonYes=view.findViewById( R.id.button_yes);
        buttonAnalyze=view.findViewById(R.id.button_analyze);
        recyclerView=view.findViewById(R.id.q_recycler);
        layoutNum=view.findViewById(R.id.q_layout_r);
        buttonShow.setOnClickListener(this);
        buttonPre.setOnClickListener(this);
        buttonNo.setOnClickListener(this);
        buttonYes.setOnClickListener(this);
        buttonAnalyze.setOnClickListener(this);
        textContent.setOnClickListener(this);
        showProgress();
        buttonAnalyze.setVisibility(View.INVISIBLE);
        if(getActivity().findViewById(R.id.tz_guide_fragment)==null){
            boxNum=5;
        }else{
            boxNum=10;
        }
        setAdapter();

        if(getActivity().findViewById(R.id.tz_guide_fragment)!=null){
            isTwoPane=true;
        }else{
            isTwoPane=false;
        }
    }

    private void initQuestion(){   //初始化问题
        List<TzOneQuestion> tzOneQuestions = DataSupport.where("groupId = ?",String.valueOf(key+1)).find(TzOneQuestion.class);
        for(int i = 0; i< tzOneQuestions.size(); i++){
            TzIndexQuestion tzIndexQuestion =new TzIndexQuestion(tzOneQuestions.get(i).getText(),i,0,false);
            if(i==0){
                tzIndexQuestion.setOn(true);
            }
            tzIndexQuestionList.add(tzIndexQuestion);
        }
        totalQues= tzOneQuestions.size();
        countNo=0;
        countYes=0;
        current=0;
        countNothing=totalQues;
    }

    private void showIntor(){
        List<TzOneIntorduction> tzOneIntorductions = DataSupport.where("groupId = ?",String.valueOf(key+1)).find(TzOneIntorduction.class);
        String s= tzOneIntorductions.get(0).getText();
        DialogUtils.showTapDialog(getActivity(),s);
    }

    @Override
    public void onClick(View view) {
        if (onClickFab(view) ){
            return;
        }
        switch (view.getId()){
            case R.id.q_button_show:
                changeShow();
                break;
            case R.id.button_pre:
                doBack();
                break;
            case R.id.button_no:
                doNo();
                break;
            case R.id.button_yes:
                doYes();
                break;
            case R.id.button_analyze:
                doAnalyze();
                break;
        }
    }

    private void doAnalyze(){
        score=100*countYes/totalQues;
        String res="";
        if(score>=60){
            res="您是"+tizhi[key]+"质。";
        }else if(score>=40){
            res="您倾向于是"+tizhi[key]+"质。";
        }else {
            res="您不是"+tizhi[key]+"质。";
        }
        res+="您可以输入手机号进行保存，以在体质记录中查看变化趋势。";
        DialogUtils.showPhoneInputDialog(getActivity(),handlerStart,res);
//        DialogUtils.showTapDialog(getActivity(),res);
    }

    protected void doPhoneSave(String p){   //绑定手机号保存检测记录
        TzOneRecord record=new TzOneRecord(key,score,p, TimeUtils.getTime());
        record.save();
        ToastUtils.showSWihtSound("成功保存");
    }

    private void doForward(){  //选择下一个问题
        if(current<totalQues-1){
            tzIndexQuestionList.get(current).setOn(false);
            current++;
            tzIndexQuestionList.get(current).setOn(true);
            adapter.notifyDataSetChanged();
            showQuestion();
        }
    }

    private void doBack(){   //选择上一个问题
        if(current>0){
            tzIndexQuestionList.get(current).setOn(false);
            current--;
            tzIndexQuestionList.get(current).setOn(true);
            adapter.notifyDataSetChanged();
            showQuestion();
        }
    }

    private void doYes(){   //选择
        int oldState= tzIndexQuestionList.get(current).getState();
        if(oldState==0){
            countNothing--;
            countYes++;
            showProgress();
            if(countNothing==0){
                buttonAnalyze.setVisibility(View.VISIBLE);
//                finishAll();
            }
        }else if(oldState==2){
            countYes++;
            countNo--;
        }
        tzIndexQuestionList.get(current).setState(1);
        adapter.notifyDataSetChanged();
        doForward();
    }

    private void doNo(){  //选择否
        int oldState= tzIndexQuestionList.get(current).getState();
        if(oldState==0){
            countNothing--;
            countNo++;
            showProgress();
            if(countNothing==0){
                buttonAnalyze.setVisibility(View.VISIBLE);
//                finishAll();
            }
        }else if(oldState==1){
            countYes--;
            countNo++;
        }
        tzIndexQuestionList.get(current).setState(2);
        adapter.notifyDataSetChanged();
        doForward();
    }

    private void showProgress(){
        progressBar.setProgress((int)(100*(totalQues-countNothing)/(float)totalQues));
    }

    protected void showQuestion(){  //播报当前问题
        TTSUtility.getInstance(getContext()).stopSpeaking();
        String text= tzIndexQuestionList.get(current).getText();
        String spre="问题"+(current+1)+":";
        textContent.setText(spre+text);
        reportString="请问您"+text;
        doFabReport(reportString,true,true);
    }

}
