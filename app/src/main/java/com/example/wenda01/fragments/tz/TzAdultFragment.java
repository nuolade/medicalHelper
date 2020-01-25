package com.example.wenda01.fragments.tz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wenda01.R;
import com.example.wenda01.beans.tz.TzAdultOlQuesResult;
import com.example.wenda01.beans.tz.TzAdultQuestion;
import com.example.wenda01.beans.tz.TzIndexQuestion;
import com.example.wenda01.fragments.base.TzFabFragment;
import com.example.wenda01.utils.Ks;
import com.example.wenda01.utils.MySimHash;
import com.example.wenda01.utils.StringUtils;
import com.example.wenda01.utils.TTSUtility;
import com.example.wenda01.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class TzAdultFragment extends TzFabFragment implements View.OnClickListener {   //体质成人常规检测碎片


    private int[] sums=new int [9];     //每组中所有问题的累计分数
    private int[] nums=new int [9];     //每组中问题总数
//    private int[] scores=new int[9];  //每组中所有问题的转换分数
    private int[] res=new int[9]; //体质结果标识
    private String[] tz={"阳虚质","阴虚质","气虚质","痰湿质","血瘀质","湿热质","气郁质","特禀质","平和质"};
    private int[] index4Json={8,2,0,1,3,5,4,6,7};//用于将json中体质排序转化成老板排序
    private String [] arr2={"8.1","8.2","8.3","8.4"}; //访问服务器的模块号

    private String [] ansButton;

    List<TzAdultQuestion> list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tz_adult_frag,container,false);
        preWork();
        setQaFab(view,this);
        showIntor("tzAdultIntor");
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
        viewList.add(buttonMY);
        viewList.add(buttonHS);
        viewList.add(buttonYS);
        viewList.add(buttonJC);
        viewList.add(buttonZS);
        viewList.add(buttonPre);
        viewList.add(buttonShow);
        viewList.add(buttonAnalyze);
        setViewEnabled(viewList,false);

        recyclerView=view.findViewById(R.id.q_recycler);
        progressBar=view.findViewById(R.id.q_progress);
        textContent=view.findViewById(R.id.q_content);
        textTitle=view.findViewById(R.id.q_title);
        textType=view.findViewById(R.id.q_type);
        layoutNum=view.findViewById(R.id.q_layout_r);
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

        buttonAnalyze.setVisibility(View.INVISIBLE);
        buttonPre.setVisibility(View.INVISIBLE);
        textType.setVisibility(View.GONE);
        buttonMY.setOnClickListener(this);
        buttonHS.setOnClickListener(this);
        buttonYS.setOnClickListener(this);
        buttonJC.setOnClickListener(this);
        buttonZS.setOnClickListener(this);
        buttonAnalyze.setOnClickListener(this);
        buttonShow.setOnClickListener(this);
        buttonPre.setOnClickListener(this);

        textTitle.setText("成人体质检测");
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

    protected void getData(){  //数据初始化
        tzIndexQuestionList =new ArrayList<>();
        s1=new ArrayList<>();
        s2=new ArrayList<>();
        s3=new ArrayList<>();
        s4=new ArrayList<>();
        s5=new ArrayList<>();
        s6=new ArrayList<>();
        s7=new ArrayList<>();
        s8=new ArrayList<>();
        s9=new ArrayList<>();
        answers=new ArrayList<>();
        current=0;
        cntQues=0;
        for(int i=0;i<sums.length;i++){
            sums[i]=0;
            res[i]=0;
//            scores[i]=0;
        }

        key=getArguments().getInt("key");
        if(key== Ks.TZ_AD_W){
            list= DataSupport.findAll(TzAdultQuestion.class);
            for(int j=0;j<list.size();j++){
                TzIndexQuestion tzIndexQuestion =new TzIndexQuestion(list.get(j).getQ(),j,0,false);
                if(j==0){
                    tzIndexQuestion.setOn(true);
                }
                tzIndexQuestionList.add(tzIndexQuestion);
            }
            nums=new int[]{4,4,4,4,4,4,4,4,5};
            totalQues=33;
            for(int i=0;i<totalQues;i++){
                s1.add(0);
                s2.add(0);
                s3.add(0);
                s4.add(0);
                s5.add(0);
                s6.add(0);
                s7.add(0);
                s8.add(0);
                s9.add(0);
                answers.add(0);
            }
            showQuestion();
            setViewEnabled(viewList,true);
            setAdapter();
        }else{
            if(key>=Ks.TZ_OM_W&& key<=Ks.TZ_YW_W){
                sendRequest4TzQues(arr2[key-1]);
            }
        }
    }

    private String getTap(TzAdultOlQuesResult.Answer a){  //获得问题的答案选项字符串
        String s="";
        s+=a.getOpt1()+"/"+a.getOpt2()+"/"+a.getOpt3()+"/"+a.getOpt4()+"/"+a.getOpt5();
        return s;
    }

    private void setQuesIndex(TzAdultQuestion question, int newId, int bW){  //设置问题属于那些体质组
        switch (newId){
            case 0:
                question.setIndex1(1+bW);
                break;
            case 1:
                question.setIndex2(1+bW);
                break;
            case 2:
                question.setIndex3(1+bW);
                break;
            case 3:
                question.setIndex4(1+bW);
                break;
            case 4:
                question.setIndex5(1+bW);
                break;
            case 5:
                question.setIndex6(1+bW);
                break;
            case 6:
                question.setIndex7(1+bW);
                break;
            case 7:
                question.setIndex8(1+bW);
                break;
            case 8:
                question.setIndex9(1+bW);
                break;
        }
    }

    protected void parseJson4Ques(String jsonData){  //解析服务其返回的问题内容
        Gson gson=new Gson();
        if(jsonData.equals("")){
            ToastUtils.showSWihtSound(getResources().getString(R.string.text_request_error));
            return;
        }
        List<TzAdultOlQuesResult> tzAdultOlQuesResults =gson.fromJson(jsonData,new TypeToken<List<TzAdultOlQuesResult>>(){}.getType());
        list= new ArrayList<>();
        for(int i = 0; i< tzAdultOlQuesResults.size(); i++){
            TzAdultOlQuesResult tzAdultOlQuesResult = tzAdultOlQuesResults.get(i);
            TzAdultQuestion question=new TzAdultQuestion(tzAdultOlQuesResult.getQuestion(), tzAdultOlQuesResult.getAuto_id());
            String tap=getTap(tzAdultOlQuesResult.getAnswer());
            question.setTap(tap);
            if(tzAdultOlQuesResult.getRepeat_question()==null){
                int bW= tzAdultOlQuesResult.getBackward();
                int bId= tzAdultOlQuesResult.getBody_id();
                int newId=index4Json[bId-1];
                nums[newId]++;
                setQuesIndex(question,newId,bW);
            }else{
                List<TzAdultOlQuesResult.OneRepeat> repeatList= tzAdultOlQuesResult.getRepeat_question();
                for(int j=0;j<repeatList.size();j++){
                    TzAdultOlQuesResult.OneRepeat oneRepeat=repeatList.get(j);
                    int bW=oneRepeat.getBackward();
                    int bId=oneRepeat.getBody_id();
                    int newId=index4Json[bId-1];
                    nums[newId]++;
                    setQuesIndex(question,newId,bW);
                }
            }
            list.add(question);
            TzIndexQuestion tzIndexQuestion =new TzIndexQuestion(list.get(i).getQ(),i,0,false);
            if(i==0){
                tzIndexQuestion.setOn(true);
            }
            tzIndexQuestionList.add(tzIndexQuestion);

        }

        totalQues= tzAdultOlQuesResults.size();
        for(int i=0;i<totalQues;i++){
            s1.add(0);
            s2.add(0);
            s3.add(0);
            s4.add(0);
            s5.add(0);
            s6.add(0);
            s7.add(0);
            s8.add(0);
            s9.add(0);
            answers.add(0);
        }
        showQuestion();
        setViewEnabled(viewList,true);
        setAdapter();
    }

    protected void showQuestion(){  //显示当前问题
        TTSUtility.getInstance(getContext()).stopSpeaking();
        String spre="问题"+(current+1)+":";
        TzAdultQuestion question=list.get(current);
        textContent.setText(spre+question.getQ());
        reportString="请问"+question.getQ();
        doFabReport(reportString,true,true);

        showScore();
        showButtonStyle();
        showProgress();
    }

    private void showProgress(){   //更新问题进度
        progressBar.setProgress(100*cntQues/totalQues);
    }

    private void showScore(){   //更新问题进度
        textPingh.setText(""+sums[8]);
        textYangx.setText(""+sums[0]);
        textYinx.setText(""+sums[1]);
        textQix.setText(""+sums[2]);
        textTans.setText(""+sums[3]);
        textXuey.setText(""+sums[4]);
        textShir.setText(""+sums[5]);
        textQiy.setText(""+sums[6]);
        textTeb.setText(""+sums[7]);
    }

    private void showButtonStyle(){   //更新答案按钮样式
        String buttonContent=list.get(current).getTap().trim();
        String [] arr;
        if(buttonContent.equals("")){
            arr=new String []{"没有","很少","有时","经常","总是"};
        }else {
            arr=buttonContent.split("/");
        }
        ansButton=arr;
        buttonMY.setText(arr[0]);
        buttonHS.setText(arr[1]);
        buttonYS.setText(arr[2]);
        buttonJC.setText(arr[3]);
        buttonZS.setText(arr[4]);

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

    protected void doChoose(int i){   //更新答案按钮样式
        TzAdultQuestion question=list.get(current);
        int i1=question.getIndex1();
        int i2=question.getIndex2();
        int i3=question.getIndex3();
        int i4=question.getIndex4();
        int i5=question.getIndex5();
        int i6=question.getIndex6();
        int i7=question.getIndex7();
        int i8=question.getIndex8();
        int i9=question.getIndex9();

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
        //8
        if(i8==1){
            sums[7]=sums[7]-s8.get(current)+(i);
            s8.set(current,i);
        }else if(i8==2){
            sums[7]=sums[7]-s8.get(current)+(6-i);
            s8.set(current,6-i);
        }
        //9
        if(i9==1){
            sums[8]=sums[8]-s9.get(current)+(i);
            s9.set(current,i);
        }else if(i9==2){
            sums[8]=sums[8]-s9.get(current)+(6-i);
            s9.set(current,6-i);
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
        else if (StringUtils.myContainPingHe(s)) {
            layoutPinghe.performClick();
        }
        else if (StringUtils.myContainYangXu(s)) {
            layoutYangxu.performClick();
        }
        else if (StringUtils.myContainYinXu(s)) {
            layoutYinxu.performClick();
        }
        else if (StringUtils.myContainQiXu(s)) {
            layoutQixu.performClick();
        }
        else if (StringUtils.myContainTanShi(s)) {
            layoutTanshi.performClick();
        }
        else if (StringUtils.myContainShiRe(s)) {
            layoutShire.performClick();
        }
        else if (StringUtils.myContainXueYu(s)) {
            layoutXueyu.performClick();
        }
        else if (StringUtils.myContainQiYu(s)) {
            layoutQiyu.performClick();
        }
        else if (StringUtils.myContainTeBing(s)) {
            layoutTebing.performClick();
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
        else {
            if(ansButton==null){
                return;
            }
            int maxD=Integer.MAX_VALUE;
            int index=-1;
            for(int i=0;i<ansButton.length;i++){
                int d= MySimHash.getD(s,ansButton[i]);
                if(d<maxD){
                    maxD=d;
                    index=i;
                }
            }
            switch (index){
                case 0:
                    buttonMY.performClick();
                    break;
                case 1:
                    buttonHS.performClick();
                    break;
                case 2:
                    buttonYS.performClick();
                    break;
                case 3:
                    buttonJC.performClick();
                    break;
                case 4:
                    buttonZS.performClick();
                    break;
            }
        }

    }
}
