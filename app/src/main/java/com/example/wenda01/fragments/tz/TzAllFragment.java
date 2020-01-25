package com.example.wenda01.fragments.tz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wenda01.R;
import com.example.wenda01.activities.tizhi.TzResultActivity;
import com.example.wenda01.beans.tz.TzAllQuestion;
import com.example.wenda01.fragments.base.TzFabFragment;

import org.litepal.crud.DataSupport;

import java.util.List;

public class TzAllFragment extends TzFabFragment implements View.OnClickListener { //无用

    private int currentGroup;   //初始化为1
    private int currentNo; //当前问题编号
    private TzAllQuestion currentQuestion;
    private List<TzAllQuestion> currentQuesList;
    private int[] sums=new int [9];     //每组中所有问题的累计分数
    private int[] nums=new int [9];     //每组中问题总数
    private int[] checkSums=new int[9]; //每组中关键问题的累计分数
    private int[] steps=new int[9];     //每组询问到第几个
    private int[] scores=new int[9];  //每组中所有问题的转换分数
    private boolean[] checkEnd=new boolean[9];  //每组问题是否会全部询问
    private int cnt=0; //累计显示问题数

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tz_key_frag,container,false);
        preWork();
        setFab(view ,this);
        updateGroupQues();
        getNextQuestion();
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

        progressBar.setMax(72);

        for(int i=0;i<sums.length;i++){
            sums[i]=0;
            nums[i]=0;
            scores[i]=0;
            steps[i]=0;
            checkSums[i]=0;
            checkEnd[i]=false;
        }
        currentGroup=1;
        if(getActivity().findViewById(R.id.tz_guide_fragment)!=null){
            isTwoPane=true;
        }else{
            isTwoPane=false;
        }
    }

    private void updateScore(int newS){
        // 1 为正序 12345,key
        // 0 为逆序 54321,not key
        int score;
        if(currentQuestion.getIsR()==1){
            score=newS;
        }else{
            score=6-newS;
        }

        currentNo++;
        steps[currentGroup-1]=currentNo;
        int sameId=currentQuestion.getSameId();
        if(currentQuestion.getIsK()==1){
            if(checkEnd[currentGroup-1]==false){
                checkSums[currentGroup-1]+=score;
                boolean keepShowing=checkToShow(score,checkSums[currentGroup-1]);
                if(keepShowing){
                    checkEnd[currentGroup-1]=true;
                }
            }
        }
        sums[currentGroup-1]+=score;
        if(sameId!=0){
            List<TzAllQuestion> newList=DataSupport.where("idd = ?",String.valueOf(sameId)).order("idd asc").find(TzAllQuestion.class);
            TzAllQuestion question=newList.get(0);
            if(question.getIsR()==currentQuestion.getIsR()){
                if(question.getIsK()==1){
                    if(checkEnd[sameId/10-1]==false){
                        checkSums[sameId/10-1]+=score;
                        boolean keepShowing=checkToShow(score,checkSums[sameId/10-1]);
                        if(keepShowing){
                            checkEnd[sameId/10-1]=true;
                        }
                    }
                }
                sums[sameId/10-1]+=score;
            }else{
                if(question.getIsK()==1){
                    if(checkEnd[sameId/10-1]==false){
                        checkSums[sameId/10-1]+=6-score;
                        boolean keepShowing=checkToShow(6-score,checkSums[sameId/10-1]);
                        if(keepShowing){
                            checkEnd[sameId/10-1]=true;
                        }
                    }
                }
                sums[sameId/10-1]+=6-score;
            }

        }
        updateGroupScore();
    }

    private void updateGroupQues(){
        if(currentGroup>=10){
            return;
        }
        currentQuesList= DataSupport.where("groupId = ?",String.valueOf(currentGroup)).order("idd asc").find(TzAllQuestion.class);
        nums[currentGroup-1]=currentQuesList.size();
        Log.d("tzbs",""+nums[currentGroup-1]);

        currentNo=0;
    }

    private boolean checkToShow(int count,int sum){
//        以上问题，每种体质只要有一个达到5分或两个问题的总分达到7分，则该体质下的其他问题就会随之显示，否则该体质下的问题将不再显示。
        if(count>=5 || sum>=7){
            return true;
        }else{
            return false;
        }
    }

    private void getNextQuestion(){
        if(currentGroup==9 && currentNo>=nums[8]){
            doAfterChooseAnswer();
            return;
        }

        if(currentGroup<=9){
            if(currentNo<nums[currentGroup-1]){
                updateQuestion();
//                if(currentQuestion.getIsK()==0 && checkEnd[currentGroup-1]){    //不为关键问题，且要全部询问
                if(checkEnd[currentGroup-1] || currentQuestion.getIsK()==1){    //要全部询问 或为关键问题
                    int sameId=currentQuestion.getSameId();

                    if(sameId!=0){ //出现不止一次
                        int groupId=sameId/10;
                        int noId=sameId%10;
                        if(groupId<currentGroup){ //之前有出现该问题的可能性
                            if(noId<=steps[groupId-1]){ //肯定出现
                                currentNo++;
                                getNextQuestion();
                            }else{
                                showQuestion();
                            }
                        }else{
                            showQuestion();
                        }
                    }else{
                        showQuestion();

                    }
                }else {
                    currentGroup++;
                    updateGroupQues();
                    getNextQuestion();
                }

            }else{
                currentGroup++;
//                currentNo=0;
                updateGroupQues();
                getNextQuestion();
            }
        }else{
            doAfterChooseAnswer();
        }
    }

    private TzAllQuestion updateQuestion(){
        TzAllQuestion question=currentQuesList.get(currentNo);
        currentQuestion=question;
//        textContent.setText(question.getText());
        return question;
    }

    protected void showQuestion(){
        cnt++;
//        String s="问题"+cnt+":"+currentGroup+" "+(currentNo+1)+currentQuestion.getText();
        String s="问题"+cnt+":"+currentQuestion.getText();
        textContent.setText(s);
        updateProgress();
    }

    private void doAfterChooseAnswer(){
        buttonMY.setVisibility(View.INVISIBLE);
        buttonHS.setVisibility(View.INVISIBLE);
        buttonYS.setVisibility(View.INVISIBLE);
        buttonJC.setVisibility(View.INVISIBLE);
        buttonZS.setVisibility(View.INVISIBLE);
        buttonMY.setClickable(false);
        buttonHS.setClickable(false);
        buttonYS.setClickable(false);
        buttonJC.setClickable(false);
        buttonZS.setClickable(false);
        progressBar.setProgress(72);

        Toast.makeText(getContext(),"finish",Toast.LENGTH_SHORT).show();
        String s="";
        for(int i=0;i<9;i++){
            scores[i]=Math.max(0,getScore(sums[i],nums[i]));
            s+=" "+sums[i]+" "+nums[i]+" "+scores[i];

        }
        buttonAnalyze.setVisibility(View.VISIBLE);
    }

    private void updateProgress(){
        progressBar.setProgress(8*(currentGroup-1)+currentNo);
    }

    private void updateGroupScore(){
        textPingh.setText(""+sums[0]+" "+checkSums[0]);
        textYangx.setText(""+sums[1]+" "+checkSums[1]);
        textYinx.setText(""+sums[2]+" "+checkSums[2]);
        textQix.setText(""+sums[3]+" "+checkSums[3]);
        textTans.setText(""+sums[4]+" "+checkSums[4]);
        textShir.setText(""+sums[5]+" "+checkSums[5]);
        textXuey.setText(""+sums[6]+" "+checkSums[6]);
        textTeb.setText(""+sums[7]+" "+checkSums[7]);
        textQiy.setText(""+sums[8]+" "+checkSums[8]);
    }

    private int getScore(int sum,int num){
        int score=(int)((sum-num)*100/(double)(num*4));
        return score;
    }

    private void showResult(){
        Intent intent=new Intent(getContext(), TzResultActivity.class);
        Bundle b=new Bundle();
        b.putIntArray("score",scores);
        b.putIntArray("sum",sums);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(onClickText(v)){
            return;
        }
        switch (v.getId()){
            case R.id.button_meiyou:
                updateScore(1);
                getNextQuestion();
                break;
            case R.id.button_henshao:
                updateScore(2);
                getNextQuestion();
                break;
            case R.id.button_youshi:
                updateScore(3);
                getNextQuestion();
                break;
            case R.id.button_jingchang:
                updateScore(4);
                getNextQuestion();
                break;
            case R.id.button_zongshi:
                updateScore(5);
                getNextQuestion();
                break;
            case R.id.button_analyze:
//                doAfterChooseAnswer();
                showResult();
                break;
        }
    }
}
