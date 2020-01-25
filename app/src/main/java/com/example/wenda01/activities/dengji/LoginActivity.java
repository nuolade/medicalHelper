package com.example.wenda01.activities.dengji;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.beans.dj.Account;
import com.example.wenda01.utils.AccountKeeper;
import com.example.wenda01.utils.AgeUtils;
import com.example.wenda01.utils.DialogUtils;
import com.example.wenda01.utils.RegularUtils;
import com.example.wenda01.utils.StringUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseSpeakActivity implements View.OnClickListener {  //账号注册登录的活动
    private Button       buttonLogin;
    private Button       buttonSign;
    private Button       buttonLoginConfirm;
    private Button       buttonSignConfirm;
    private Button buttonUpdate;
    private Button buttonExit;
    private EditText     editSignPhone;
    private EditText     editSignPass;
    private EditText     editSignPassAgain;
    private EditText     editSignName;
    private EditText     editSignYear;
    private EditText     editSignMonth;
    private EditText     editSignDay;
    private EditText     editSignHeight;
    private EditText     editSignWeight;
    private EditText     editLoginPhone;
    private EditText     editLoginPassword;
    private RadioButton  radioSignMale;
    private RadioButton  radioSignFemale;

    private LinearLayout layoutLogin;
    private LinearLayout layoutSign;
    private LinearLayout layoutOnline;
    private LinearLayout layoutOffline;

    private FloatingActionButton fabSay;
    private FloatingActionButton fabSound;
    private FloatingActionButton fabDialog;

    private int part=1;    //模块标识，区别登录和注册
    private boolean isOnline;   //登录标记

    private String [] listZc={"注册"};
    private String [] listDl={"登录"};
    private String [] listTc={"退出账号","注销","退出"};
    private String [] listPhone={"电话","手机"};
    private String [] listPass={"密码"};
    private String [] listPassA={"重复"};
    private String [] listYes={"结束","完成","确定","保存","确认","提交"};
    private String [] listHeight={"高","身高"};
    private String [] listWeight={"重","体重"};
    private String [] listSex={"性"};
    private String [] listName={"叫","名"};
    private String [] listDate={"生日","日期","年"};
    List<View> viewList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preWork();
        updateVisiblePart();
        if(isOnline){
            doStayAfterSign();
            buttonSignConfirm.setText("修改");
        }else{
            buttonSignConfirm.setText("注册");
        }
    }

    private void preWork(){  //进行初始化操作
        viewList=new ArrayList<>();
        buttonLogin=findViewById(R.id.button_login);
        buttonSign=findViewById(R.id.button_sign);
        buttonUpdate=findViewById(R.id.button_update);
        buttonExit=findViewById(R.id.button_exit);
        buttonLoginConfirm=findViewById(R.id.button_login_confirm);
        buttonSignConfirm=findViewById(R.id.button_sign_confirm);
        editSignPhone=findViewById(R.id.edit_sign_phone);
        editSignPass=findViewById(R.id.edit_sign_password);
        editSignPassAgain=findViewById(R.id.edit_sign_password_again);
        editSignName=findViewById(R.id.edit_sign_name);
        editSignYear=findViewById(R.id.edit_sign_year);
        editSignMonth=findViewById(R.id.edit_sign_month);
        editSignDay=findViewById(R.id.edit_sign_day);
        editSignHeight=findViewById(R.id.edit_sign_height);
        editSignWeight=findViewById(R.id.edit_sign_weight);
        radioSignMale=findViewById(R.id.radio_sign_male);
        radioSignFemale=findViewById(R.id.radio_sign_female);
        editLoginPhone=findViewById(R.id.edit_login_phone);
        editLoginPassword=findViewById(R.id.edit_login_password);
        layoutLogin=findViewById(R.id.layout_account_login);
        layoutSign=findViewById(R.id.layout_account_sign);
        layoutOffline=findViewById(R.id.layout_login_offline);
        layoutOnline=findViewById(R.id.layout_login_online);
        buttonLogin.setOnClickListener(this);
        buttonSign.setOnClickListener(this);
        buttonLoginConfirm.setOnClickListener(this);
        buttonSignConfirm.setOnClickListener(this);
        buttonExit.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);

        isOnline=AccountKeeper.isOnline();
        preVisible();

        editLoginPhone.setText(AccountKeeper.getLastPhone());

        fabSay=findViewById(R.id.float_say);
        fabSound=findViewById(R.id.float_sound);
        fabDialog=findViewById(R.id.float_dialog);
        fabSay.setOnClickListener(this);
        fabSound.setOnClickListener(this);
        fabDialog.setOnClickListener(this);

        addEditText();
    }

    private void addEditText(){ //将EditText 放入列表，以方便一起修改
        viewList.add(editSignPhone);
        viewList.add(editSignPass);
        viewList.add(editSignPassAgain);
        viewList.add(editSignName);
        viewList.add(editSignYear);
        viewList.add(editSignMonth);
        viewList.add(editSignDay);
        viewList.add(editSignHeight);
        viewList.add(editSignWeight);
        viewList.add(editLoginPhone);
        viewList.add(editLoginPassword);
    }

    private EditText getFocusEdit(){  //得到列表中选中的EditText
        for(int i=0;i<viewList.size();i++){
            if(viewList.get(i).hasFocus()){
                return (EditText) viewList.get(i);
            }
        }
        return null;
    }

    private void preVisible(){  //根据账号是否登录更改子布局显示效果
        if(isOnline){
            layoutOnline.setVisibility(View.VISIBLE);
            layoutOffline.setVisibility(View.GONE);
        }else{
            layoutOffline.setVisibility(View.VISIBLE);
            layoutOnline.setVisibility(View.GONE);
        }
    }

    private void updateVisiblePart(){  //更新子布局显示效果
        if(isOnline){
            layoutSign.setVisibility(View.VISIBLE);
            layoutLogin.setVisibility(View.GONE);
        }else{
            if(part==1){
                layoutLogin.setVisibility(View.VISIBLE);
                layoutSign.setVisibility(View.GONE);
            }else if(part==2){
                layoutSign.setVisibility(View.VISIBLE);
                layoutLogin.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                if(part!=1){
                    part=1;
                    updateVisiblePart();
                }

                break;
            case R.id.button_sign:
                if(part!=2){
                    part=2;
                    updateVisiblePart();
                }
                break;
            case R.id.button_login_confirm:
                checkLogin();
                break;
            case R.id.button_sign_confirm:
                if(isOnline){
                    checkUpdate();
                }else{
                    checkSign();
                }
                break;
            case R.id.button_exit:
                doExit();

                break;
            case R.id.button_update:
                break;
            case R.id.float_say:
                clickMethod();
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

    @Override
    protected void onResume() {
        super.onResume();
        setFab();
    }

    private void setFab(){
        updateFabSound();
        updateFabDialog();
    }

    private void updateFabSound(){
        if(MyApplication.isIsSoundOpen()){
            fabSound.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButtonPress)));
            fabSound.setImageDrawable(getResources().getDrawable(R.drawable.base_sound_on));
        }else{
            fabSound.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAnalyze)));
            fabSound.setImageDrawable(getResources().getDrawable(R.drawable.base_sound_off));
        }
    }

    private void updateFabDialog(){
        if(MyApplication.isIsDialogOpen()){
            fabDialog.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButtonPress)));
            fabDialog.setImageDrawable(getResources().getDrawable(R.drawable.base_dialog_on));
        }else{
            fabDialog.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOrangeRed)));
            fabDialog.setImageDrawable(getResources().getDrawable(R.drawable.base_dialog_off));
        }
    }

    public void doSth(String s){
        if(StringUtils.myContainBack(s)){
            finish();
        }
        else if(StringUtils.myContainHome(s)){
            finish();
        }
        else if(StringUtils.myContainFinish(s)){
            DialogUtils.showExitDialog(this);
        }
        if(StringUtils.myContain(s,listZc)){
            if(isV(layoutOffline)){
                buttonSign.performClick();
            }
        }
        else if(StringUtils.myContain(s,listDl)){
            if(isV(layoutOffline)){
                buttonLogin.performClick();
            }
        }
        else if(StringUtils.myContain(s,listTc)){
            if(AccountKeeper.isOnline()){
                DialogUtils.showExitDialog(this);
            }
        }
        else if(StringUtils.myContain(s,listPhone)){
            if(isV(layoutLogin)){
                editLoginPhone.requestFocus();
                s=s.replace("电话","");
                s=s.replace("手机","");
                s=s.replace("号码","");
                s=s.replace("号","");
                s=s.replace("为","");
                s=s.replace("是","");
                boolean isRightPhone= RegularUtils.isPhone(s);
                if(isRightPhone){
                    editLoginPhone.setText(s);
                }
            }
            else if(isV(layoutSign)){
                editSignPhone.requestFocus();
                editLoginPhone.requestFocus();
                s=s.replace("电话","");
                s=s.replace("手机","");
                s=s.replace("号码","");
                s=s.replace("号","");
                s=s.replace("为","");
                s=s.replace("是","");
                boolean isRightPhone= RegularUtils.isPhone(s);
                if(isRightPhone){
                    editSignPhone.setText(s);
                }
            }
        }
        else if(StringUtils.myContain(s,listPass)){
            if(isV(layoutSign)){
                editSignPass.requestFocus();
                s=s.replace("密码","");
                s=s.replace("为","");
                s=s.replace("是","");
                boolean isRightPass=RegularUtils.isPassword(s);
                if(isRightPass){
                    editSignPass.setText(s);
                }
            }
            else if(isV(layoutLogin)){
                editLoginPassword.requestFocus();
                s=s.replace("密码","");
                s=s.replace("为","");
                s=s.replace("是","");
                boolean isRightPass=RegularUtils.isPassword(s);
                if(isRightPass){
                    editLoginPassword.setText(s);
                }
            }
        }
        else if(StringUtils.myContain(s,listPassA)){
            if(isV(layoutSign)){
                editSignPassAgain.requestFocus();
                s=s.replace("密码","");
                s=s.replace("为","");
                s=s.replace("是","");
                boolean isRightPass=RegularUtils.isPassword(s);
                if(isRightPass){
                    editSignPassAgain.setText(s);
                }
            }
        }
        else if(StringUtils.myContain(s,listHeight)){
            if(isV(layoutSign)){
                editSignHeight.requestFocus();
                String num=StringUtils.findNum(s);
                editSignHeight.setText(num);
            }
        }
        else if(StringUtils.myContain(s,listWeight)){
            if(isV(layoutSign)){
                editSignWeight.requestFocus();
                String num=StringUtils.findNum(s);
                editSignWeight.setText(num);
            }
        }
        else if(StringUtils.myContain(s,listName)){
            if(isV(layoutSign)){
                editSignName.requestFocus();
                s=s.replace("我","");
                s=s.replace("名字","");
                s=s.replace("名","");
                s=s.replace("为","");
                s=s.replace("是","");
                s=s.replace("叫","");
                editSignName.setText(s);
            }
        }
        else if(StringUtils.myContain(s,listSex)){
            if(isV(layoutSign)){
                if(s.indexOf("男")!=-1){
                    radioSignMale.setChecked(true);
                }
                else if(s.indexOf("女")!=-1){
                    radioSignFemale.setChecked(true);
                }
            }
        }
        else if(StringUtils.myContain(s,listYes)){
            if(isV(layoutSign)){
                buttonSignConfirm.performClick();
            }
            else if(isV(layoutLogin)){
                buttonLoginConfirm.performClick();
            }
        }
        else if(StringUtils.myContain(s,listDate)){
            if(isV(layoutSign)){
                s=s.replaceAll("\\D","_").replace("_+","_");
                String [] as=s.split("_");
                int i=0;
                if(i<as.length){
                    if(as[i].equals("")){
                        i++;
                    }
                }
                if(i< as.length){
                    editSignYear.setText(as[i]);
                    editSignYear.requestFocus();
                    i++;
                }
                if(i< as.length){
                    editSignMonth.setText(as[i]);
                    editSignMonth.requestFocus();
                    i++;
                }
                if(i< as.length){
                    editSignDay.setText(as[i]);
                    editSignDay.requestFocus();
                    i++;
                }
            }
        }
        else {
            EditText editText=getFocusEdit();
            editText.setText(s);
        }
    }

    private boolean isV(View view){   //判断控件是否可见
        return view.getVisibility()==View.VISIBLE;
    }


    private void doExit(){   //退出账号
        showExitDialog();
    }

    private void exitAccount(){  //退出账号，结束活动
        AccountKeeper.exit();
        finish();
    }

    private boolean checkHaveSigned(String p){  //判断是否之前登录过账号
        List<Account> list= DataSupport.where("phone = ?",p).find(Account.class);
        if(list.size()>0){
            return true;
        }else{
            return false;
        }
    }

    private void checkLogin(){   //当点击登录，判断是否正确
        String phone =editLoginPhone.getText().toString().trim();
        if(phone.equals("")){
            //请输入手机号
            showWarningDialog("请输入手机号。若没通过手机号注册过，请先注册。");
            return;
        }
        String password=editLoginPassword.getText().toString().trim();
        if(password.equals("")){
            //请输入密码
            showWarningDialog("请输入密码");
            return;
        }
        List<Account> list=DataSupport.where("phone = ?",phone).find(Account.class);
        if(list.size()==0){
            //账号不存在，或密码不正确
            showWarningDialog("账号不存在，或密码不正确");
            return;
        }
        Account account=list.get(0);
        String pass=account.getPassword();
        if(!pass.equals(password)){
            showWarningDialog("账号不存在，或密码不正确");
            return;
        }
        showLoginConfirmDialog(account);
    }

    private void showExitDialog(){   //显示退出提示对话框
        AlertDialog.Builder dialog;
        dialog=new AlertDialog.Builder(LoginActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("退出提示");
        dialog.setMessage(getResources().getString(R.string.exit_finish));
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exitAccount();
                isOnline=AccountKeeper.isOnline();
                finish();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    private void showWarningDialog(String s){   //显示错误提示对话框
        AlertDialog.Builder dialog;
        dialog=new AlertDialog.Builder(LoginActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("错误提示");
        dialog.setMessage(s);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    private void showSignConfirmDialog(final Account account){   //显示完成注册对话框
        AlertDialog.Builder dialog;
        dialog=new AlertDialog.Builder(LoginActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("注册成功");
        dialog.setMessage(getResources().getString(R.string.sign_finish));
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //保持登录
                AccountKeeper.login(account);
                isOnline=AccountKeeper.isOnline();
                finish();
            }
        });
        dialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //不保持登录
                finish();

            }
        });
        dialog.setNeutralButton("停留", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AccountKeeper.login(account);
                doStayAfterSign();
            }
        });
        dialog.show();
    }

    private void showUpdateConfirmDialog(Account account){   //显示信息更新对话框
        AlertDialog.Builder dialog;
        dialog=new AlertDialog.Builder(LoginActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("修改成功");
        dialog.setMessage(getResources().getString(R.string.sign_finish));
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //保持登录
                AccountKeeper.setAccount(account);
                finish();
            }
        });
        dialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //不保持登录
                AccountKeeper.exit();
                finish();

            }
        });
        dialog.setNeutralButton("停留", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AccountKeeper.setAccount(account);
                doStayAfterSign();
            }
        });
        dialog.show();
    }

    private void showLoginConfirmDialog(Account account){   //显示完成登录对话框
        AlertDialog.Builder dialog;
        dialog=new AlertDialog.Builder(LoginActivity.this);
        dialog.setCancelable(false);
        AccountKeeper.login(account);
        dialog.setTitle("登录成功");
        dialog.setMessage(getResources().getString(R.string.login_finish));
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //保持登录
                finish();
            }
        });
        dialog.setNeutralButton("停留", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doStayAfterSign();
            }
        });
        dialog.show();
    }

    private void doStayAfterSign(){  //登录后跟新布局显示
        isOnline=AccountKeeper.isOnline();
        part=2;
        preVisible();
        updateVisiblePart();
        getKeepAccount();

    }


    private void checkUpdate(){   //判断信息修改是否正确
        Account newAccount=new Account();
        String password=editSignPass.getText().toString();
        String passwordAgain=editSignPassAgain.getText().toString();
        if(!password.equals(passwordAgain)){
            //两次密码不相同
            showWarningDialog("两次密码不相同");
            return;
        }else{
            //判断是否有数字字母，6-12位
            boolean isRightPass=RegularUtils.isPassword(password);
            if(password.equals("")){
                showWarningDialog("密码不能为空");
                return;
            }
            if(!isRightPass){
                //密码未满足规范
                showWarningDialog("填写密码不规范，须有字母与数字，并且长度满足6-12位");
                return;
            }
        }
        newAccount.setPassword(password);

        String year=editSignYear.getText().toString().trim();
        String month=editSignMonth.getText().toString().trim();
        String day=editSignDay.getText().toString().trim();
        if(year.equals("") || month.equals("") || day.equals("")){
            showWarningDialog("日期三项都不能为空");
            return;
        }
        String date=year+"-"+month+"-"+day;
        int age=0;
        boolean isRightDate=RegularUtils.isValidDate(date);
        if(isRightDate){
            age= AgeUtils.getAgeFromBirthTime(date);
            if(age<=0){
                //填写错误日期
                showWarningDialog("填写错误日期");
                return;
            }
        }else{
            //日期错误
            return;
        }
        int y=Integer.parseInt(year);
        int m=Integer.parseInt(month);
        int d=Integer.parseInt(day);
        newAccount.setYear(y);
        newAccount.setMonth(m);
        newAccount.setDay(d);
        newAccount.setAge(age);

        boolean isMale=radioSignMale.isChecked();
        newAccount.setMale(isMale);

        String name=editSignName.getText().toString().trim();
        if(!"".equals(name)){
            boolean isRightName=RegularUtils.isName(name);
//            if(isRightName){
//
//            }else{
//                //错误的姓名
//                showWarningDialog("填写姓名不规范");
//                return;
//            }
        }
        newAccount.setName(name);

        String height=editSignHeight.getText().toString().trim();
        int h=0;
        if(!"".equals(height)){
            boolean isRightHeight=RegularUtils.isNum(height);
            if(isRightHeight){
                h= Integer.parseInt(height);
                if(h>0 && h<300){

                }else{
                    //身高数值不合理
                    showWarningDialog("身高数值不合理");
                    return;
                }
            }
        }
        newAccount.setHeight(h);


        String weight=editSignWeight.getText().toString().trim();
        int w=0;
        if(!"".equals(weight)){
            boolean isRightWeight=RegularUtils.isNum(weight);
            if(isRightWeight){
                w= Integer.parseInt(weight);
                if(w>0 && w<1500){

                }else{
                    //体重数值不合理
                    showWarningDialog("体重数值不合理");
                    return;
                }
            }
        }
        newAccount.setWeight(w);
        newAccount.updateAll("phone = ?",AccountKeeper.getLastPhone());

        showUpdateConfirmDialog(newAccount);
    }

    private void getKeepAccount(){   //得到账号的信息，并填入相应的控件中
        if(!AccountKeeper.isOnline()){
            return;
        }
        String lastPhone=AccountKeeper.getLastPhone();
        List<Account> list=DataSupport.where("phone = ?",lastPhone).find(Account.class);
        Account account=list.get(0);
        editSignPhone.setText(account.getPhone());
        editSignPhone.setEnabled(false);
        editSignPhone.setFocusable(false);
        editSignPass.setText(account.getPassword());
        editSignPassAgain.setText(account.getPassword());
        editSignName.setText(account.getName());
        editSignYear.setText(String.valueOf(account.getYear()));
        editSignMonth.setText(String.valueOf(account.getMonth()));
        editSignDay.setText(String.valueOf(account.getDay()));
        boolean isMale=account.isMale();
        if(isMale){
            radioSignMale.setChecked(true);
            radioSignFemale.setChecked(false);
        }else{
            radioSignFemale.setChecked(true);
            radioSignMale.setChecked(false);
        }
        if(account.getHeight()!=0){
            editSignHeight.setText(String.valueOf(account.getHeight()));
        }
        if(account.getWeight()!=0){
            editSignWeight.setText(String.valueOf(account.getWeight()));
        }


    }

    private void checkSign(){   //判断账号注册是否正确
        String phone=editSignPhone.getText().toString().trim();
        if(phone.equals("")){
            showWarningDialog("手机号不能为空");
            return;
        }
        boolean isRightPhone= RegularUtils.isPhone(phone);
        if(isRightPhone){
            //发送短信等
            //检查唯一性
            boolean haveChecked=checkHaveSigned(phone);
            if(haveChecked){
                showWarningDialog("手机号已被注册");
                return;
            }
        }else{
            //手机号不规范
            showWarningDialog("填写手机号不规范");
            return;
        }

        String password=editSignPass.getText().toString();
        String passwordAgain=editSignPassAgain.getText().toString();
        if(!password.equals(passwordAgain)){
            //两次密码不相同
            showWarningDialog("两次密码不相同");
            return;
        }else{
            //判断是否有数字字母，6-12位
            boolean isRightPass=RegularUtils.isPassword(password);
            if(password.equals("")){
                showWarningDialog("密码不能为空");
                return;
            }
            if(!isRightPass){
                //密码未满足规范
                showWarningDialog("填写密码不规范，须有字母与数字，并且长度满足6-12位");
                return;
            }
        }

        String year=editSignYear.getText().toString().trim();
        String month=editSignMonth.getText().toString().trim();
        String day=editSignDay.getText().toString().trim();
        if(year.equals("") || month.equals("") || day.equals("")){
            showWarningDialog("日期三项都不能为空");
            return;
        }
        String date=year+"-"+month+"-"+day;
        int age=0;
        boolean isRightDate=RegularUtils.isValidDate(date);
        if(isRightDate){
            age= AgeUtils.getAgeFromBirthTime(date);
            if(age<=0){
                //填写错误日期
                showWarningDialog("填写错误日期");
                return;
            }
        }else{
            //日期错误
            return;
        }
        int y=Integer.parseInt(year);
        int m=Integer.parseInt(month);
        int d=Integer.parseInt(day);

        boolean isMale=radioSignMale.isChecked();

        String name=editSignName.getText().toString().trim();
        if(!"".equals(name)){
            boolean isRightName=RegularUtils.isName(name);
            if(isRightName){

            }else{
                //错误的姓名
//                showWarningDialog("填写姓名不规范");
//                return;
            }
        }

        String height=editSignHeight.getText().toString().trim();
        int h=0;
        if(!"".equals(height)){
            boolean isRightHeight=RegularUtils.isNum(height);
            if(isRightHeight){
                h= Integer.parseInt(height);
                if(h>0 && h<300){

                }else{
                    //身高数值不合理
                    showWarningDialog("身高数值不合理");
                    return;
                }
            }
        }


        String weight=editSignWeight.getText().toString().trim();
        int w=0;
        if(!"".equals(weight)){
            boolean isRightWeight=RegularUtils.isNum(weight);
            if(isRightWeight){
                w= Integer.parseInt(weight);
                if(w>0 && w<1500){

                }else{
                    //体重数值不合理
                    showWarningDialog("体重数值不合理");
                    return;
                }
            }
        }


        Account account=new Account(phone,password,isMale,y,m,d);
        account.setName(name);
        account.setHeight(h);
        account.setWeight(w);
        account.setAge(age);
        account.save();
        showSignConfirmDialog(account);
    }
}
