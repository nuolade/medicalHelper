package com.example.wenda01.fragments.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wenda01.R;
import com.example.wenda01.activities.tizhi.TzResultActivity;
import com.example.wenda01.adapters.QuestionIndexAdapter;
import com.example.wenda01.beans.tz.TzIndexQuestion;
import com.example.wenda01.utils.DialogUtils;
import com.example.wenda01.utils.ToastUtils;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TzFabFragment extends FabFragment implements View.OnClickListener {  //具有辅助功能的体质父类碎片

    protected Button buttonMY;
    protected Button buttonHS;
    protected Button buttonYS;
    protected Button buttonJC;
    protected Button buttonZS;

    protected ImageView buttonShow;
    protected Button buttonAnalyze;

    protected ProgressBar progressBar;

    protected LinearLayout layoutNum;

    protected TextView textTitle;
    protected TextView textType;
    protected LinearLayout layoutFive;
    protected LinearLayout layoutSeven;
    protected LinearLayout layoutNine;
    protected LinearLayout layoutFour;

    protected TextView textPingh;
    protected TextView textYangx;
    protected TextView textYinx;
    protected TextView textQix;
    protected TextView textTans;
    protected TextView textXuey;
    protected TextView textShir;
    protected TextView textQiy;
    protected TextView textTeb;
    protected TextView textJin;
    protected TextView textMu;
    protected TextView textShui;
    protected TextView textHuo;
    protected TextView textTu;
    protected TextView textSjws;
    protected TextView textPx;
    protected TextView textJz;
    protected TextView textRz;
    protected TextView textSz;
    protected TextView textXhw;
    protected TextView textYb;

    protected LinearLayout layoutPinghe;
    protected LinearLayout layoutYangxu;
    protected LinearLayout layoutYinxu;
    protected LinearLayout layoutQixu;
    protected LinearLayout layoutTanshi;
    protected LinearLayout layoutShire;
    protected LinearLayout layoutXueyu;
    protected LinearLayout layoutTebing;
    protected LinearLayout layoutQiyu;
    protected LinearLayout layoutJin;
    protected LinearLayout layoutMu;
    protected LinearLayout layoutShui;
    protected LinearLayout layoutHuo;
    protected LinearLayout layoutTu;
    protected LinearLayout layoutSjws;
    protected LinearLayout layoutPx;
    protected LinearLayout layoutJz;
    protected LinearLayout layoutRz;
    protected LinearLayout layoutSz;
    protected LinearLayout layoutXhw;
    protected LinearLayout layoutYb;

    protected TextView textContent;

    protected List<Integer> s1;
    protected List<Integer> s2;
    protected List<Integer> s3;
    protected List<Integer> s4;
    protected List<Integer> s5;
    protected List<Integer> s6;
    protected List<Integer> s7;
    protected List<Integer> s8;
    protected List<Integer> s9;
    protected List<Integer> answers;

    protected int boxNum; //问题索引列表列数

    protected List<TzIndexQuestion> tzIndexQuestionList;

    protected int key;  //体质模块key
    protected int current=0; //当前问题标号
    protected int totalQues;    //全部问题数
    protected int cntQues;  //已回答问题数
    protected QuestionIndexAdapter adapter;
    protected RecyclerView recyclerView;

    protected Handler handlerQuesNumChange=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tzIndexQuestionList.get(current).setOn(false);
            current=msg.what;
            tzIndexQuestionList.get(current).setOn(true);
            adapter.notifyDataSetChanged();
            showQuestion();
        }
    };


    protected void setAdapter(){  //设置适配器
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(boxNum,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new QuestionIndexAdapter(tzIndexQuestionList,handlerQuesNumChange);
        recyclerView.setAdapter(adapter);
    }

    protected void showQuestion(){  //显示问题

    }

    protected void doAnalyze(int [] sums,int [] nums,int key){  //分析体质
        Intent intent=new Intent(getContext(), TzResultActivity.class);
        Bundle b=new Bundle();
        b.putIntArray("sum",sums);
        b.putIntArray("num",nums);
        b.putInt("key",key);
        b.putBoolean("isTwoPane",isTwoPane);
        intent.putExtras(b);
        startActivity(intent);
    }

    protected void doPre(){  //上一题
        if(current<=0){
            ToastUtils.showSWihtSound(getResources().getString(R.string.text_ques_to_first));
            return;
        }
        tzIndexQuestionList.get(current).setOn(false);
        current--;
        tzIndexQuestionList.get(current).setOn(true);
        adapter.notifyDataSetChanged();
        showQuestion();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.float_back:
                break;
        }
    }

    protected void sendRequest4TzQues(String type){  //访问服务器获得问题
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("requestType",type)
                            .add("member_id","88")
                            .add("key_dm","lblc6wcj3ogh0uyhfek53b5z")
                            .add("funcmods_code","tz")
                            .build();
                    Request request=new Request.Builder()
                            .url("http://miaolangzhong.com/erzhentang/saas100Business/bodyIdentify.do")
                            .post(requestBody)
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    getQuesResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }



    protected void getQuesResponse(final String response){  //获得服务器返回内容
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parseJson4Ques(response);
            }
        });
    }

    protected void parseJson4Ques(String s){  //进行服务器返回内容

    }

    protected void changeShow(){  //更改标号选择子布局可见性
        if(layoutNum.getVisibility()==View.VISIBLE){
            layoutNum.setVisibility(View.GONE);
            buttonShow.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.down));
        }else{
            layoutNum.setVisibility(View.VISIBLE);
            buttonShow.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.up));
        }
    }

    protected boolean onClickText(View v){
        boolean f=false;
        switch (v.getId()){
            case R.id.layout_nine_pinghe:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_pinghe));
                f= true;
                break;
            case R.id.layout_nine_yangxu:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_yangxu));
                f= true;
                break;
            case R.id.layout_nine_yinxu:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_yinxu));
                f= true;
                break;
            case R.id.layout_nine_qixu:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_qixu));
                f= true;
                break;
            case R.id.layout_nine_tanshi:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_tanshi));
                f= true;
                break;
            case R.id.layout_nine_shire:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_shire));
                f= true;
                break;
            case R.id.layout_nine_xueyu:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_xueyu));
                f= true;
                break;
            case R.id.layout_nine_tebing:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_tebing));
                f= true;
                break;
            case R.id.layout_nine_qiyu:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_qiyu));
                f= true;
                break;
            case R.id.layout_seven_shengji:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_shengji));
                break;
            case R.id.layout_seven_pixu:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_pixu));
                break;
            case R.id.layout_seven_jizhi:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_jizhi));
                break;
            case R.id.layout_seven_rezhi:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_rezhi));
                break;
            case R.id.layout_seven_shizhi:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_shizhi));
                break;
            case R.id.layout_seven_xinhuowang:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_xinhuowang));
                break;
            case R.id.layout_seven_yibing:
                DialogUtils.showTapDialog(getActivity(),getResources().getString(R.string.text_yibing));
                break;
        }
        return f;
    }

}
