package com.example.wenda01.fragments.base;

import android.content.res.ColorStateList;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.utils.ActivityCollector;
import com.example.wenda01.utils.DialogUtils;
import com.example.wenda01.utils.Ks;
import com.example.wenda01.utils.SpeakUtils;
import com.example.wenda01.utils.StringUtils;
import com.example.wenda01.utils.TTSUtility;
import com.example.wenda01.utils.ToastUtils;
import com.example.wenda01.utils.TxtReader;

import java.util.ArrayList;
import java.util.List;

public class FabFragment extends Fragment {  //具有辅助功能的父类碎片
    protected boolean isTwoPane; //判别是否为平板
    protected View view;
    protected Button buttonYes;
    protected Button buttonNo;
    protected Button buttonPre;

    protected FloatingActionButton fabFinish;
    protected FloatingActionButton fabHome;
    protected FloatingActionButton fabBack;
    protected FloatingActionButton fabReport;
    protected FloatingActionButton fabSay;
    protected FloatingActionButton fabSound;
    protected FloatingActionButton fabDialog;

    protected String reportString=""; //播报内容

    protected SpeakUtils speakUtils;

    protected List<View> viewList=new ArrayList<>();


    protected Handler handlerStart=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Ks.STARTKEY:
                    getData();
                    break;
                case Ks.WORDKEY:
                    String s=(String)msg.obj;
                    getSayingWords(s);
                    break;
                case Ks.SAYKEY:
                    doSayShow();
                    break;
                case Ks.HISTORYKEY:
                    String h=(String) msg.obj;
                    doHistoryItem(h);
                    break;
                case Ks.PHONEKEY:
                    String p=(String) msg.obj;
                    doPhoneSave(p);
                    break;

            }
        }
    };

    protected void doPhoneSave(String p){  //进行手机号保存

    }

    protected void doHistoryItem(String h){  //选择历史记录

    }

    protected void doSayShow(){  //进行语音输入功能
        fabSay.performClick();
    }

    protected void setViewEnabled(List<View> list, boolean b){  //设置控件可用性
        for(int i=0;i<list.size();i++){
            list.get(i).setEnabled(b);
        }
    }

    protected void setViewVisible(List<View> list, int key){  //设置控件可见性
        for(int i=0;i<list.size();i++){
            list.get(i).setVisibility(key);
        }
    }

    protected boolean canFindView(int id){  //检测控件是否存在
        return view.findViewById(id)==null;
    }

    protected void setFab(View view, View.OnClickListener listener){  //设置辅助功能
        fabBack=view.findViewById(R.id.float_back);
        fabFinish=view.findViewById(R.id.float_finish);
        fabHome=view.findViewById(R.id.float_home);
        fabReport=view.findViewById(R.id.float_report);
        fabSay=view.findViewById(R.id.float_say);
        fabBack.setOnClickListener(listener);
        fabFinish.setOnClickListener(listener);
        fabHome.setOnClickListener(listener);
        fabReport.setOnClickListener(listener);
        fabSay.setOnClickListener(listener);
    }

    protected void setQaFab(View view, View.OnClickListener listener){  //设置问答辅助功能
        fabReport=view.findViewById(R.id.float_report);
        fabSay=view.findViewById(R.id.float_say);
        fabSound=view.findViewById(R.id.float_sound);
        fabDialog=view.findViewById(R.id.float_dialog);
        fabDialog.setOnClickListener(listener);
        fabSound.setOnClickListener(listener);
        fabReport.setOnClickListener(listener);
        fabSay.setOnClickListener(listener);
        updateFabDialog();
        updateFabSound();
    }

    protected void setSDFab(View view, View.OnClickListener listener){  //设置语音和对话框辅助功能
        fabDialog=view.findViewById(R.id.float_dialog);
        fabSound=view.findViewById(R.id.float_sound);
        fabSound.setOnClickListener(listener);
        fabDialog.setOnClickListener(listener);
        updateFabDialog();
        updateFabSound();
    }

    protected void updateFabSound(){  //更新声音辅助功能控件
        if(MyApplication.isIsSoundOpen()){
            fabSound.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButtonPress)));
            fabSound.setImageDrawable(getResources().getDrawable(R.drawable.base_sound_on));
        }else{
            fabSound.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAnalyze)));
            fabSound.setImageDrawable(getResources().getDrawable(R.drawable.base_sound_off));
        }
    }

    protected void updateFabDialog(){  //更新对话框辅助功能控件
        if(MyApplication.isIsDialogOpen()){
            fabDialog.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButtonPress)));
            fabDialog.setImageDrawable(getResources().getDrawable(R.drawable.base_dialog_on));
        }else{
            fabDialog.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOrangeRed)));
            fabDialog.setImageDrawable(getResources().getDrawable(R.drawable.base_dialog_off));
        }
    }

    public void setReportString(String s){  //设置播报文字
        reportString=s;
    }

    protected boolean onClickFab(View v){  //判断是否点击辅助功能按钮
        boolean f=false;
        switch (v.getId()){
            case R.id.float_back:
                doFabBack();
                f= true;
                break;
            case R.id.float_finish:
                doFabSound();
                f= true;
                break;
            case R.id.float_home:
                doFabHome();
                f= true;
                break;
            case R.id.float_report:
                if(!MyApplication.isIsSoundOpen()){
                    ToastUtils.showS(getResources().getString(R.string.text_sound_intor));
                }
                doFabReport(reportString,true,true);
                f= true;
                break;
            case R.id.float_say:
                doFabSay();
                f= true;
                break;
            case R.id.float_sound:
                doFabSound();
                break;
            case R.id.float_dialog:
                doFabDialog();
                break;

        }
        return f;
    }

    protected boolean onClickAnswer(View v){  //判断是否点击答案按钮
        boolean f=false;
        switch (v.getId()){
            case R.id.button_meiyou:
                doChoose(1);

                f=true;
                break;
            case R.id.button_henshao:
                doChoose(2);

                f=true;
                break;
            case R.id.button_youshi:
                doChoose(3);
                f=true;
                break;
            case R.id.button_jingchang:
                doChoose(4);

                f=true;
                break;
            case R.id.button_zongshi:
                doChoose(5);

                f=true;
                break;
            case R.id.button_yes:
                doChoose(1);

                break;
            case R.id.button_no:
                doChoose(0);

                break;
        }
        return f;
    }

    protected void showIntor(String name){  //显示模块提示，有提示语音
        String intor= TxtReader.readAssetsTxt(getContext(),name);
        DialogUtils.showTapDialog(getActivity(),intor,handlerStart);
    }
    protected void showIntorOnlyString(String name){  //显示模块提示，无提示语音
        DialogUtils.showTapDialog(getActivity(),name,handlerStart);
    }

    protected void doChoose(int i){  //选择答案

    }

    protected void doFabBack(){  //执行回退辅助功能
        getActivity().finish();
    }

    protected void doFabSound(){  //执行声音辅助功能
        MyApplication.changeSoundOpen();
        updateFabSound();
    }

    protected void doFabDialog(){  //执行对话框辅助功能
        MyApplication.changeDialogOpen();
        updateFabDialog();
    }
    protected void doFabHome(){  //执行回到主界面辅助功能
        ActivityCollector.finishAll();
    }

    protected void doFabReport(String s,boolean isShowing,boolean isStoping){  //执行播报辅助功能
        if(MyApplication.isIsSoundOpen()){
            TTSUtility ttsUtility=TTSUtility.getInstance(MyApplication.getContext(),handlerStart);
            if(isStoping){
                if(ttsUtility.isSpeaking()){
                    TTSUtility.getInstance(MyApplication.getContext()).stopSpeaking();
                }else{
                    ttsUtility.speaking(s,isShowing);
                }
            }else{
                ttsUtility.speaking(s,isShowing);
            }

        }
    }
    protected void doFabSay(){  //执行辅助语音输入功能
        TTSUtility.getInstance(MyApplication.getContext()).stopSpeaking();
        if(speakUtils==null){
            speakUtils=new SpeakUtils(getContext(),handlerStart);
        }
        MyApplication.addSayLock();
        speakUtils.clickMethod();
    }

    protected void getData(){  //获得数据

    }

    protected void getSayingWords(String s){  //得到语音输入文字结果后的逻辑处理

    }

    @Override
    public void onPause() {
        super.onPause();
        TTSUtility.getInstance(MyApplication.getContext()).stopSpeaking();
    }

    protected boolean getFabSayingWords(String s){  //得到语音输入文字结果后的逻辑处理（处理辅助功能）
        boolean f=false;
        if(StringUtils.myContainBack(s)){
            fabBack.performClick();
            f=true;
        }else if (StringUtils.myContainHome(s)){
            fabHome.performClick();
            f=true;
        }else if(StringUtils.myContainSound(s)){
            fabFinish.performClick();
            f=true;
        }else if(StringUtils.myContainRepeat(s)){
            fabReport.performClick();
            f=true;
        }

        return f;
    }
}
