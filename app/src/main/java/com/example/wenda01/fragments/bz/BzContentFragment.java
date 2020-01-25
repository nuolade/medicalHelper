package com.example.wenda01.fragments.bz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wenda01.R;
import com.example.wenda01.beans.bz.BzRecord;
import com.example.wenda01.beans.bz.BzQuestion;
import com.example.wenda01.fragments.base.BzFabFragment;
import com.example.wenda01.utils.DialogUtils;
import com.example.wenda01.utils.StringUtils;
import com.example.wenda01.utils.TTSUtility;
import com.example.wenda01.utils.TimeUtils;
import com.example.wenda01.utils.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BzContentFragment extends BzFabFragment implements View.OnClickListener {  //病症问答交互的碎片
    private TextView textQuestionContent;
    private TextView textQuestionTitle;
    private TextView getTextQuestionSymptom;
    private Button buttonYes;
    private Button buttonNo;
    private Button buttonPre;
    private Button buttonSave;

    private ProgressBar progressBar;
    private ImageView buttonShow;
    private LinearLayout layoutNum;

    private int staId;  //首个问题id
    private int maxPath;    //最长问题路径数
    private String symptomName; //症状名称
    private String symIntor;    //症状简介
    private int progress; //问题回答进度
    private int staIdToYes; //该问题回答是所到问题编号
    private int staIdToNo; //该问题回答否所到问题编号
    private Stack<Integer> stackState; //回答路径问题编号
    private Stack<Integer> stackAns;    //回答问题答案
    private String suggestion; //最终给出建议
    private String phone; //手机号

    private List<View> chooseViewList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.bz_content_frag,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setQaFab(view,this);
        preWork();
        setInitData();
        showIntorOnlyString(symIntor);
    }

    protected void getData(){   //获得下一个问题相关数据
        showStatement();
    }

    private void preWork(){   //控件初始化
        textQuestionContent=view.findViewById(R.id.q_content);
        textQuestionTitle=view.findViewById(R.id.q_class);
        getTextQuestionSymptom=view.findViewById(R.id.q_title);
        buttonYes=view.findViewById(R.id.button_yes);
        buttonNo=view.findViewById(R.id.button_no);
        buttonPre=view.findViewById(R.id.button_pre);
        buttonSave=view.findViewById(R.id.button_analyze);
        buttonShow=view.findViewById(R.id.q_button_show);
        layoutNum =view.findViewById(R.id.q_layout_r);
        buttonPre.setOnClickListener(this);
        buttonYes.setOnClickListener(this);
        buttonNo.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        chooseViewList.add(buttonYes);
        chooseViewList.add(buttonNo);
        progressBar=view.findViewById(R.id.q_progress);
        textQuestionTitle.setVisibility(View.VISIBLE);
        buttonShow.setVisibility(View.GONE);
        layoutNum.setVisibility(View.GONE);
        buttonSave.setText("保存记录");

        if (canFindView(R.id.button_voice)) {
            isTwoPane=true;
        }else{
            isTwoPane=false;
        }
        updateFabSound();
    }


    private void setInitData(){   //获得从活动传递的参数
        staId       =getArguments().getInt("id");
        maxPath     =getArguments().getInt("path");
        symptomName =getArguments().getString("name");
        symIntor=getArguments().getString("intor");
//        ToastUtils.showS(symIntor);
        stackState=new Stack<>();
        stackAns=new Stack<>();
        buttonPre.setVisibility(View.INVISIBLE);
        buttonSave.setVisibility(View.INVISIBLE);
        textQuestionTitle.setText(this.getResources().getText(R.string.question_title_q));
        getTextQuestionSymptom.setText(symptomName);
        progress=1;

    }

    public void doSaveWithPhone(String phone){  //将本次病症判断的记录保存
        String sId=arrayToString(stackState);
        String sAns=arrayToString(stackAns);
        BzRecord bzRecord=new BzRecord(phone,symptomName ,TimeUtils.getTime(),sId,sAns,suggestion);
        bzRecord.save();
        ToastUtils.showSWihtSound("成功保存");
    }

    public String arrayToString(Stack<Integer> stack){   //将整形的栈转换成空格分隔的字符串
        String s="";
        for(int i=0;i<stack.size();i++){
            if(i==0){
                s+=stack.get(i);
            }else{
                s+=" "+stack.get(i);
            }
        }
        return s;
    }


    private void showProgress(){   //更新问题回答的进度
        if(staIdToNo==staId || staIdToYes==staId){
            progressBar.setProgress(100);
        }else{
            int ratio=(int)(progress*100/maxPath);
            progressBar.setProgress(ratio);
        }
    }

    public void doSave(){  //提示需要输入手机号的保存对话框
        DialogUtils.showPhoneInputDialog(getActivity(),handlerStart,"请输入您的电话");
    }

    protected void doPhoneSave(String p){   //通过手机号进行保存
        phone=p;
        doSaveWithPhone(phone);
    }

    private void showStatement(){   //更新新问题相关内容
        List<BzQuestion> bzQuestions = DataSupport.where("idd = ?",String.valueOf(staId)).find(BzQuestion.class);
        BzQuestion bzQuestion = bzQuestions.get(0);
        staIdToYes= bzQuestion.getIdToYes();
        staIdToNo= bzQuestion.getIdToNo();
        textQuestionContent.setText(bzQuestion.getText());
        if(staId==staIdToNo||staId==staIdToYes){
            String saveS="您已完成该症状的判断，可以点击“保存”将本次病症记录保存。";
            reportString= bzQuestion.getText()+saveS;
            doFabReport(reportString,false,false);
        }else{
            reportString="请问"+ bzQuestion.getText();
            doFabReport(reportString,true,false);
        }
        showProgress();
        setButtonStyle();
    }

    private void setButtonStyle(){  //更给交互按钮的状态
        if(staId==staIdToNo||staId==staIdToYes){
            buttonYes.setVisibility(View.INVISIBLE);
            buttonNo.setVisibility(View.INVISIBLE);
            buttonSave.setVisibility(View.VISIBLE);
            setViewEnabled(chooseViewList,false);
            buttonSave.setEnabled(true);

            suggestion=textQuestionContent.getText().toString();
            textQuestionTitle.setText(this.getResources().getText(R.string.question_title_s));
        }else{
            buttonYes.setVisibility(View.VISIBLE);
            buttonNo.setVisibility(View.VISIBLE);
            buttonSave.setVisibility(View.INVISIBLE);
            setViewEnabled(chooseViewList,true);
            buttonSave.setEnabled(false);
            textQuestionTitle.setText(this.getResources().getText(R.string.question_title_q));
        }
        if(stackState.empty()){
            buttonPre.setVisibility(View.INVISIBLE);
            buttonPre.setEnabled(false);
        }else{
            buttonPre.setVisibility(View.VISIBLE);
            buttonPre.setEnabled(true);
        }
    }

    protected void doChoose(int i){  //根据用户对问题的答复进行处理
        if(staId!=staIdToYes){
            stackState.push(staId);
            stackAns.push(2-i);
            if(i==1){
                staId=staIdToYes;
            }else if(i==0){
                staId=staIdToNo;
            }

            if(progress<maxPath){
                progress+=1;
            }
            showStatement();

        }
    }

    protected void doPre(){  //进行获得上一问题
        if(stackState.empty()){
            ToastUtils.showSWihtSound("现在已是第一道题");
        }else{
            staId=stackState.peek();
            stackState.pop();
            stackAns.pop();

            if(progress>1){
                progress-=1;
            }
            showStatement();
        }
    }

    @Override
    public void onClick(View view) {
        if (onClickFab(view) ){
            return;
        }
        if (onClickAnswer(view)){
            return;
        }
        switch (view.getId()){
            case R.id.button_pre:
                doPre();
                break;
            case R.id.button_analyze:
//                ToastUtils.showS("aa");
                TTSUtility.getInstance(getContext()).stopSpeaking();
                doSave();
//                DialogUtils.showPhoneInputDialog(getActivity(),handlerStart);
                break;
        }
    }

    protected void getSayingWords(String s){   //获得语音输入文字后的处理操作
        if(StringUtils.myContainYes(s)){
            buttonYes.performClick();
        }
        else if(StringUtils.myContainNo(s)){
            buttonNo.performClick();
        }
        else if(StringUtils.myContainPre(s)){
            buttonPre.performClick();
        }
        else if(canFindView(R.id.button_voice)){
            if(StringUtils.myContainSearch(s)){
                doFindSym(s);
            }
        }
    }


}
