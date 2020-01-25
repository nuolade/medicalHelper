package com.example.wenda01.fragments.tz;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wenda01.R;
import com.example.wenda01.beans.tz.TzAdultKeyQuestion;
import com.example.wenda01.fragments.base.TzFabFragment;
import com.example.wenda01.utils.DialogUtils;
import com.example.wenda01.utils.TTSUtility;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TzKeyFragment extends TzFabFragment implements View.OnClickListener{   //体质成人快捷检测碎片

    private Stack<Integer> s1; //1体质分数栈
    private Stack<Integer> s2; //2体质分数栈
    private Stack<Integer> s3; //3体质分数栈
    private Stack<Integer> s4; //4体质分数栈
    private Stack<Integer> s5; //5体质分数栈
    private Stack<Integer> s6; //6体质分数栈
    private Stack<Integer> s7; //7体质分数栈
    private Stack<Integer> s8; //8体质分数栈
    private Stack<Integer> s9; //9体质分数栈
    private Stack<Integer> answers;//答案栈
    private Stack<Integer> qNum; //显示问题栈
    private int[] sums=new int [9];     //每组中所有问题的累计分数
    private int[] nums=new int [9];     //每组中问题总数 7 8 8 8 7 6 7 7 8
    private int[] checkSums=new int[9]; //每组中关键问题的累计分数
    private boolean[] checkEnd=new boolean[9];  //每组问题是否会全部询问

    private List<TzAdultKeyQuestion> list;
    private List<Stack<Integer>> stackList;
    private int [] firstRead; //第一次访问问题，用来计数nums
//    private int ageType=0;


    protected Handler handlerAgeChoose=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            key=msg.what;
            getData();
        }
    };

    private String [] ages={"小于60岁","大于等于60岁"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tz_key_frag,container,false);
        preWork();
        setQaFab(view ,this);
        DialogUtils.showSingleChoiceDialog(getActivity(),"请问您选择您的年龄",ages,handlerAgeChoose);
        return view;
    }

    private void preWork(){
        buttonMY=view.findViewById(R.id.button_meiyou);
        buttonHS=view.findViewById(R.id.button_henshao);
        buttonYS=view.findViewById(R.id.button_youshi);
        buttonJC=view.findViewById(R.id.button_jingchang);
        buttonZS=view.findViewById(R.id.button_zongshi);
        buttonAnalyze=view.findViewById(R.id.button_analyze);
        buttonShow=view.findViewById(R.id.q_button_show);
        buttonPre=view.findViewById(R.id.button_pre);
        progressBar=view.findViewById(R.id.q_progress);
        textContent=view.findViewById(R.id.q_content);
        layoutFive=view.findViewById(R.id.layout_tz_five_choose);
        layoutNine=view.findViewById(R.id.layout_tz_nine);
        textPingh=view.findViewById(R.id.text_tz_pingh);
        textYangx=view.findViewById(R.id.text_tz_yangx);
        textYinx=view.findViewById(R.id.text_tz_yinx);
        textQix=view.findViewById(R.id.text_tz_qix);
        textTans=view.findViewById(R.id.text_tz_tans);
        textShir=view.findViewById(R.id.text_tz_shir);
        textXuey=view.findViewById(R.id.text_tz_xuey);
        textTeb=view.findViewById(R.id.text_tz_teb);
        textQiy=view.findViewById(R.id.text_tz_qiy);
        layoutNum=view.findViewById(R.id.q_layout_r);
        layoutNum.setVisibility(View.GONE);
        buttonAnalyze.setVisibility(View.INVISIBLE);
        buttonShow.setVisibility(View.GONE);
        buttonMY.setOnClickListener(this);
        buttonHS.setOnClickListener(this);
        buttonYS.setOnClickListener(this);
        buttonJC.setOnClickListener(this);
        buttonZS.setOnClickListener(this);
        buttonAnalyze.setOnClickListener(this);
        buttonPre.setOnClickListener(this);
        viewList.add(buttonMY);
        viewList.add(buttonHS);
        viewList.add(buttonYS);
        viewList.add(buttonJC);
        viewList.add(buttonZS);


        layoutPinghe=view.findViewById  (R.id.layout_nine_pinghe);
        layoutYangxu=view.findViewById  (R.id.layout_nine_yangxu);
        layoutYinxu=view.findViewById   (R.id.layout_nine_yinxu);
        layoutQixu=view.findViewById    (R.id.layout_nine_qixu);
        layoutTanshi=view.findViewById  (R.id.layout_nine_tanshi);
        layoutShire=view.findViewById   (R.id.layout_nine_shire);
        layoutXueyu=view.findViewById   (R.id.layout_nine_xueyu);
        layoutTebing=view.findViewById  (R.id.layout_nine_tebing);
        layoutQiyu=view.findViewById    (R.id.layout_nine_qiyu);
        layoutPinghe.setOnClickListener(this::onClick);
        layoutYangxu.setOnClickListener(this::onClick);
        layoutYinxu.setOnClickListener(this::onClick);
        layoutQixu.setOnClickListener(this::onClick);
        layoutTanshi.setOnClickListener(this::onClick);
        layoutShire.setOnClickListener(this::onClick);
        layoutXueyu.setOnClickListener(this::onClick);
        layoutTebing.setOnClickListener(this::onClick);
        layoutQiyu.setOnClickListener(this::onClick);

        progressBar.setMax(100);
        if(getActivity().findViewById(R.id.tz_guide_fragment)!=null){
            isTwoPane=true;
        }else{
            isTwoPane=false;
        }

    }

    protected void getData(){
        list= DataSupport.findAll(TzAdultKeyQuestion.class);
        current=-1;
        cntQues=0;
        totalQues=list.size();
        firstRead=new int[totalQues];

        for(int i=0;i<sums.length;i++){
            sums[i]=0;
            nums[i]=0;
//            scores[i]=0;
//            steps[i]=0;
            checkSums[i]=0;
            checkEnd[i]=false;
        }
        s1=new Stack<>();
        s2=new Stack<>();
        s3=new Stack<>();
        s4=new Stack<>();
        s5=new Stack<>();
        s6=new Stack<>();
        s7=new Stack<>();
        s8=new Stack<>();
        s9=new Stack<>();
        stackList=new ArrayList<>();
        stackList.add(s1);
        stackList.add(s2);
        stackList.add(s3);
        stackList.add(s4);
        stackList.add(s5);
        stackList.add(s6);
        stackList.add(s7);
        stackList.add(s8);
        stackList.add(s9);
        answers=new Stack<>();
        qNum=new Stack<>();

        showNextQuestion();
    }

    protected void showQuestion(){
        TTSUtility.getInstance(getContext()).stopSpeaking();
        qNum.push(current);
        String spre="问题"+(cntQues)+":";
        TzAdultKeyQuestion question=list.get(current);
        textContent.setText(spre+question.getQ());
        reportString="请问"+question.getQ();
        doFabReport(reportString,true,true);
        showScore();

        showProgress();
    }

    private void showProgress(){
        progressBar.setProgress(100*current/totalQues);
    }

    private void showScore(){
        textPingh.setText(""+sums[8]+" "+checkSums[8]);
        textYangx.setText(""+sums[0]+" "+checkSums[0]);
        textYinx.setText(""+sums[1]+" "+checkSums[1]);
        textQix.setText(""+sums[2]+" "+checkSums[2]);
        textTans.setText(""+sums[3]+" "+checkSums[3]);
        textXuey.setText(""+sums[4]+" "+checkSums[4]);
        textShir.setText(""+sums[5]+" "+checkSums[5]);
        textQiy.setText(""+sums[6]+" "+checkSums[6]);
        textTeb.setText(""+sums[7]+" "+checkSums[7]);
    }

    protected List<Integer> getIndexList(TzAdultKeyQuestion question){  //得到该问题的各个体质的标记
        if(question==null){
            return null;
        }
        List<Integer> integerList=new ArrayList<>();
        int i1=question.getIndex1();
        int i2=question.getIndex2();
        int i3=question.getIndex3();
        int i4=question.getIndex4();
        int i5=question.getIndex5();
        int i6=question.getIndex6();
        int i7=question.getIndex7();
        int i8=question.getIndex8();
        int i9=question.getIndex9();
        integerList.add(i1);
        integerList.add(i2);
        integerList.add(i3);
        integerList.add(i4);
        integerList.add(i5);
        integerList.add(i6);
        integerList.add(i7);
        integerList.add(i8);
        integerList.add(i9);
        return integerList;
    }

    protected void doChoose(int i){
        TzAdultKeyQuestion question=list.get(current);
        List<Integer> integerList=getIndexList(question);
        answers.push(i);
        for(int j=0;j<integerList.size();j++){
            int index=integerList.get(j);

            if(index!=0){
                if(index>0){ //为关键问题
                    if(index==1){ //正序
                        stackList.get(j).push(i);
                        sums[j]+=i;
                        checkSums[j]+=i;
                        if(i>=5 || checkSums[j]>=7){
                            checkEnd[j]=true;
                        }
                    }else{  //逆序
                        stackList.get(j).push(6-i);
                        sums[j]+=(6-i);
                        checkSums[j]+=(6-i);
                        if(i<=1 || checkSums[j]>=7){
                            checkEnd[j]=true;
                        }
                    }
                }else{  //非关键问题
                    if(index==-1){ //正序
                        sums[j]+=i;
                        stackList.get(j).push(i);
                    }else{  //逆序
                        sums[j]+=(6-i);
                        stackList.get(j).push(6-i);
                    }
                }
            }else{
                stackList.get(j).push(0);
            }

        }
        if(current<totalQues-1){
            showNextQuestion();
        }else{
            doQuestionOver();
        }
        showScore();
        buttonPre.setClickable(true);
        buttonPre.setEnabled(true);
        buttonPre.setVisibility(View.VISIBLE);
    }

    private void showNextQuestion(){   //显示下一个问题，因存在关键问题，在满足一定条件会跳过部分问题
        boolean f=false;
        cntQues++;
        while(!f && current<totalQues){
            current++;
            if(current==totalQues){
                break;
            }
            f=false;
            TzAdultKeyQuestion question=list.get(current);
            List<Integer> indexs=getIndexList(question);
            for(int i=0;i<indexs.size();i++){
                int index=indexs.get(i);
                if(index!=0){
                    if(firstRead[current]==0){
                        nums[i]++;
                    }
                }
                if(index>0 || (index!=0&&checkEnd[i])){
                    f=true;
                }
            }
            firstRead[current]=1;
        }
        if(current==totalQues){
            doQuestionOver();
        }else{
            showQuestion();
        }
    }

    private void doQuestionOver(){   //当完成所有问题更新外观
        progressBar.setProgress(100);
        qNum.push(totalQues);
        showButtonOverStyle();
    }

    private void showButtonOverStyle(){   //更新按钮当未完成所有问题
        setViewEnabled(viewList,false);
        setViewVisible(viewList,View.GONE);
        buttonAnalyze.setVisibility(View.VISIBLE);
        buttonAnalyze.setEnabled(true);
        buttonAnalyze.setClickable(true);
    }

    private void showButtonUnOverStyle(){   //更新按钮当完成所有问题
        setViewEnabled(viewList,true);
        setViewVisible(viewList,View.VISIBLE);
        buttonAnalyze.setVisibility(View.INVISIBLE);
        buttonAnalyze.setEnabled(false);
        buttonAnalyze.setClickable(false);
    }

    protected void doPre(){
        if(qNum.size()<=1){
            return;
        }
        cntQues--;
        qNum.pop();
        current=qNum.pop();
        if(qNum.size()<=0){
            buttonPre.setClickable(false);
            buttonPre.setEnabled(false);
            buttonPre.setVisibility(View.INVISIBLE);
        }
        answers.pop();
        List<Integer> integerList=getIndexList(list.get(current));
        if(current<totalQues){
            List<Integer> lastScores=new ArrayList<>();
            for(int i=0;i<stackList.size();i++){
                int score=stackList.get(i).pop();
                lastScores.add(score);
                sums[i]-=score;
                if(integerList.get(i)>0){
                    checkSums[i]-=score;
                    if(checkSums[i]<5){
                        checkEnd[i]=false;
                    }
                }
            }
        }
        showQuestion();
        showButtonUnOverStyle();
    }

    @Override
    public void onClick(View v) {
        if (onClickFab(v)){
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
                doAnalyze();
                break;
            case R.id.button_pre :
                doPre();
                break;

        }
    }

    protected void doAnalyze(){   //分析体质
        for(int i=0;i<sums.length;i++){
            sums[i]=Math.max(sums[i],nums[i]+1);
        }
        doAnalyze(sums,nums,key);
    }
}
