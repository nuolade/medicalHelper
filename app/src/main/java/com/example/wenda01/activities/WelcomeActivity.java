package com.example.wenda01.activities;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.activities.bingzheng.BzActivity;
import com.example.wenda01.activities.dengji.LoginActivity;
import com.example.wenda01.activities.jieqi.JqActivity;
import com.example.wenda01.activities.qa.QaActivity;
import com.example.wenda01.activities.speak.SpeakActivity;
import com.example.wenda01.activities.tizhi.TzActivity;
import com.example.wenda01.activities.xuanjiao.XjActivity;
import com.example.wenda01.activities.yaowu.YwActivity;
import com.example.wenda01.utils.AccountKeeper;
import com.example.wenda01.utils.DialogUtils;
import com.example.wenda01.utils.StringUtils;

public class WelcomeActivity extends SpeakActivity implements View.OnClickListener {  //欢迎活动
    //七个进入相应模块的按钮
    private Button buttonRydj;  //进入入院登记模块
    private Button buttonZnyz;  //进入入院登记模块
    private Button buttonTzbs;  //进入体质辨识模块
    private Button buttonJkxj;  //进入健康宣教模块
    private Button buttonYwcx;  //进入健康宣教模块
    private Button buttonJqys;  //进入节气养生模块
    private Button buttonQa;    //进入知识问答模块

    //四个辅助功能按钮
    private FloatingActionButton fabSay;     //语音输入功能
    private FloatingActionButton fabSound;   //全局声音开启及关闭功能
    private FloatingActionButton fabDialog;  //全局对话框开启及关闭功能
    private FloatingActionButton fabExit;    //账号登录及退出功能


    private String [] listRy={"登记","入院","个人","信息","登录","注册"};
    private String [] listBz={"预诊","病症"};
    private String [] listTz={"体质","体"};
    private String [] listXj={"宣教","健康","知识","学习"};
    private String [] listJq={"节气","天气"};
    private String [] listYw={"药"};
    private String [] listBack={"退出","注销"};
    private String [] listQa={"对话","问答"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //询问权限设置
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if(ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(WelcomeActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},1);
        }
        preWork();
    }

    public void preWork(){  //设置控件
        buttonRydj=findViewById(R.id.button_rydj);
        buttonZnyz=findViewById(R.id.button_znyz);
        buttonTzbs=findViewById(R.id.button_tzbs);
        buttonJkxj=findViewById(R.id.button_jkxj);
        buttonYwcx=findViewById(R.id.button_ywcx);
        buttonJqys=findViewById(R.id.button_jqys);
        buttonQa=findViewById(R.id.button_zswd);
        buttonQa.setOnClickListener(this);
        buttonRydj.setOnClickListener(this);
        buttonZnyz.setOnClickListener(this);
        buttonTzbs.setOnClickListener(this);
        buttonJkxj.setOnClickListener(this);
        buttonYwcx.setOnClickListener(this);
        buttonJqys.setOnClickListener(this);
        fabSay=findViewById(R.id.float_say);
        fabExit=findViewById(  R.id.float_exit);
        fabSound=findViewById(R.id.float_sound);
        fabDialog=findViewById(R.id.float_dialog);
        fabSay.setOnClickListener(this);
        fabExit.setOnClickListener(this);
        fabSound.setOnClickListener(this);
        fabDialog.setOnClickListener(this);
    }

    private void setFab(){  //更新辅助功能按钮
        updateFabSound();
        updateFabDialog();
        updateFabAccount();
    }

    private void updateFabAccount(){  //更新账号功能按钮
        if(AccountKeeper.isOnline()){
            fabExit.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            fabExit.setImageDrawable(getResources().getDrawable(R.drawable.base_exit));
            buttonRydj.setText("个人信息"+"（已登录："+AccountKeeper.getHiddenPhone()+")");
        }else{
            fabExit.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOrangeRed)));
            fabExit.setImageDrawable(getResources().getDrawable(R.drawable.base_login));
            buttonRydj.setText("登录/注册");
        }
    }

    private void updateFabSound(){  //更新声音功能按钮
        if(MyApplication.isIsSoundOpen()){
            fabSound.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButtonPress)));
            fabSound.setImageDrawable(getResources().getDrawable(R.drawable.base_sound_on));
        }else{
            fabSound.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAnalyze)));
            fabSound.setImageDrawable(getResources().getDrawable(R.drawable.base_sound_off));
        }
    }

    private void updateFabDialog(){   //更新对话功能按钮
        if(MyApplication.isIsDialogOpen()){
            fabDialog.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButtonPress)));
            fabDialog.setImageDrawable(getResources().getDrawable(R.drawable.base_dialog_on));
        }else{
            fabDialog.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOrangeRed)));
            fabDialog.setImageDrawable(getResources().getDrawable(R.drawable.base_dialog_off));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFab();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //入院登记
            case R.id.button_rydj:
                doZhuCeDengLu();
                break;
                //智能预诊
            case R.id.button_znyz:
                doZhiNengYuZhen();
                break;
                //体质辨识
            case R.id.button_tzbs:
                doTiZhiBianShi();
                break;
                //健康宣教
            case R.id.button_jkxj:
                doJianKangXuanJiao();
                break;
                //药物查询
            case R.id.button_ywcx:
                doYaoWuChaXun();
                break;
            case R.id.button_jqys:
                doJieQiYangSheng();
                break;
            case R.id.button_zswd:
                doQa();
                break;
            case R.id.float_say:
                clickMethod();
                break;
            case R.id.float_exit:
                doAccount();
                break;
            case R.id.float_sound:
                MyApplication.changeSoundOpen();
                updateFabSound();
                break;
            case R.id.float_dialog:
                MyApplication.changeDialogOpen();
                updateFabDialog();
                break;
        }
    }

    private void doAccount(){  //点击账号功能按钮触发的函数
        if(AccountKeeper.isOnline()){
            DialogUtils.showExitDialog(this);
            updateFabAccount();
        }else{
            doZhuCeDengLu();
        }
        updateFabAccount();
    }

    private void doQa(){   //进入知识问答模块的函数
//        Intent intent=new Intent(WelcomeActivity.this, QaContentActivity.class);
        Intent intent=new Intent(WelcomeActivity.this, QaActivity.class);
        startActivity(intent);
    }


    private void doOther(){
        Intent intent =new Intent(WelcomeActivity.this,JqActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("state",1);
        bundle.putInt("id",17);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //高版本需要渠道
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            //只在Android O之上需要渠道，这里的第一个参数要和下面的channelId一样
            NotificationChannel notificationChannel = new NotificationChannel("1","name",NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，通知才能正常弹出
            manager.createNotificationChannel(notificationChannel);
        }
        //这里的第二个参数要和上面的第一个参数一样
        Notification notification =new NotificationCompat.Builder(WelcomeActivity.this,"1")
                .setContentTitle("title")
                .setContentText("text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)  //设置小图标
                .setContentIntent(pi)
                .build();
        manager.notify(1,notification);
    }


    private void doJieQiYangSheng(){  //进入节气养生模块的函数
        Intent intent=new Intent(WelcomeActivity.this, JqActivity.class);
        startActivity(intent);
    }

    private void doYaoWuChaXun(){  //进入药物查询模块的函数
        Intent intent=new Intent(WelcomeActivity.this, YwActivity.class);
        startActivity(intent);
    }

    private void doJianKangXuanJiao(){  //进入健康宣教模块的函数
        Intent intent=new Intent(WelcomeActivity.this, XjActivity.class);
        startActivity(intent);
    }

    private void doZhiNengYuZhen(){  //进入智能问诊模块的函数
        Intent intent=new Intent(WelcomeActivity.this, BzActivity.class);
        startActivity(intent);
    }
    private void doTiZhiBianShi(){  //进入体质辨识模块的函数
        Intent intent=new Intent(WelcomeActivity.this, TzActivity.class);
        startActivity(intent);
    }

    private void doZhuCeDengLu(){  //进入注册登录模块的函数
        Intent intent=new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void doSth(String s){  //语音输入返回时调用的函数

        if(StringUtils.myContain(s,listRy)){
            buttonRydj.performClick();
        }
        else if(StringUtils.myContain(s,listBz)){
            buttonZnyz.performClick();
        }
        else if(StringUtils.myContain(s,listTz)){
            buttonTzbs.performClick();
        }
        else if(StringUtils.myContain(s,listJq)){
            buttonJqys.performClick();
        }
        else if(StringUtils.myContain(s,listXj)){
            buttonJkxj.performClick();
        }
        else if(StringUtils.myContain(s,listYw)){
            buttonYwcx.performClick();
        }
        else if(StringUtils.myContain(s,listQa)){
            buttonQa.performClick();
        }
        else if(StringUtils.myContain(s,listBack)){
            if(AccountKeeper.isOnline()){
                DialogUtils.showExitDialog(this);
                updateFabAccount();
            }
        }
    }

}
