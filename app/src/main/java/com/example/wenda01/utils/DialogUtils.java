package com.example.wenda01.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;


public class DialogUtils {   //对话框显示工具
    public static void showExitDialog(Activity activity){   //显示退出对话框
        AlertDialog.Builder dialog;
        dialog=new AlertDialog.Builder(activity);
        dialog.setCancelable(false);
        dialog.setTitle("退出提示");
        dialog.setMessage(MyApplication.getContext().getResources().getString(R.string.exit_finish));
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll();
                AccountKeeper.exit();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    public static void showTapDialog(Activity activity, String s){  //显示传入文字的对话框
        if(MyApplication.isIsSoundOpen()){
//            TTSUtility.getInstance(MyApplication.getContext()).speaking(s);
        }
        if(MyApplication.isIsDialogOpen()){
            AlertDialog.Builder dialog;
            dialog=new AlertDialog.Builder(activity);
            dialog.setCancelable(true);
            dialog.setTitle("提示");
            dialog.setMessage(s);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    TTSUtility.getInstance(MyApplication.getContext()).stopSpeaking();
                }
            });
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TTSUtility.getInstance(MyApplication.getContext()).stopSpeaking();
                }
            });

            dialog.show();
        }
    }

    public static void showIntorDialog(Activity activity, String s){  //显示介绍对话框

        if(MyApplication.isIsDialogOpen()){
            AlertDialog.Builder dialog;
            dialog=new AlertDialog.Builder(activity);
            dialog.setCancelable(true);
            dialog.setTitle("提示");
            dialog.setMessage(s);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    TTSUtility.getInstance(MyApplication.getContext()).stopSpeaking();
                }
            });
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TTSUtility.getInstance(MyApplication.getContext()).stopSpeaking();
                }
            });

            dialog.show();
        }
        else{
            ToastUtils.showS(s);
        }


    }

    public static void showPhoneInputDialog(Activity activity,Handler handler,String s){  //显示电话输入对话框
    /*@setView
 装入一个EditView
     */
        EditText editText = new EditText(activity);
        if(AccountKeeper.isOnline()){
            editText.setText(AccountKeeper.getLastPhone());
        }
        AlertDialog.Builder inputDialog =new AlertDialog.Builder(activity);
        inputDialog.setTitle(s).setView(editText);
        inputDialog.setPositiveButton("保存", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phone=editText.getText().toString().trim();
                if(RegularUtils.isPhone(phone)){
                    Message message=new Message();
                    message.what=Ks.PHONEKEY;
                    message.obj=phone;
                    handler.sendMessage(message);
                }else{
                    ToastUtils.showSWihtSound(activity.getResources().getString(R.string.text_phone_error));
                }
            }
        }).show();
        inputDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    public static void showTapDialog(Activity activity, String s, Handler handler){   //显示需要消息处理的对话框
        if(MyApplication.isIsSoundOpen()){
//            TTSUtility.getInstance(MyApplication.getContext()).speaking(s);
        }
        if(MyApplication.isIsDialogOpen()){
            AlertDialog.Builder dialog;
            dialog=new AlertDialog.Builder(activity);
            dialog.setCancelable(true);
            dialog.setTitle("提示");
            dialog.setMessage(s);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    TTSUtility.getInstance(MyApplication.getContext()).stopSpeaking();
                    Message message=new Message();
                    message.what=Ks.STARTKEY;
                    handler.sendMessage(message);
                }
            });
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    TTSUtility.getInstance(MyApplication.getContext()).stopSpeaking();
                    Message message=new Message();
                    message.what=Ks.STARTKEY;
                    handler.sendMessage(message);
                }
            });
            dialog.show();
        }else {
//            ToastUtils.showS(s);
            Message message=new Message();
            message.what=Ks.STARTKEY;
            handler.sendMessage(message);
        }


    }



    static int yourChoice=0;
    public static void showSingleChoiceDialog(Activity activity,String title,String [] items,Handler handler){  //显示单选对话框

//        final String[] items = { "我是1","我是2","我是3","我是4" };
        AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(activity);
        singleChoiceDialog.setTitle(title);
        yourChoice=-1;
        //第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items,-1,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice= which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Message message=new Message();
                        if (yourChoice!= -1){
                            if(yourChoice==0){
                                message.what=Ks.TZ_YK_W;
                            }else{
                                message.what=Ks.TZ_OK_W;
                            }

                        }else{
                            message.what=Ks.TZ_YK_W;
                        }
                        handler.sendMessage(message);
                        ToastUtils.showS("你选择了" +  items[0]);
                    }
                });
        singleChoiceDialog.setCancelable(false);
        singleChoiceDialog.show();
    }

}
