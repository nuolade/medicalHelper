package com.example.wenda01.activities.tizhi;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.beans.tz.Bisic;
import com.example.wenda01.beans.tz.TzRecordFirstResult;
import com.example.wenda01.beans.tz.TzRecordSaveResult;
import com.example.wenda01.beans.tz.TzSuggestResult;
import com.example.wenda01.beans.tz.TzWxSuggestResult;
import com.example.wenda01.utils.AccountKeeper;
import com.example.wenda01.utils.Ks;
//import com.lixs.charts.RadarChartView;
import com.example.wenda01.utils.RegularUtils;
import com.example.wenda01.utils.TTSUtility;
import com.google.gson.Gson;
import com.lsy.radarview.SyRadarView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TzResultActivity extends BaseSpeakActivity implements View.OnClickListener {  //体质记录结果和建议的活动
    private SyRadarView radarView;
    private TextView textRes;
    private Button buttonAnalyze;
    private Button buttonSave;
    private EditText editAge;
    private EditText editPhone;
    private LinearLayout layoutInfo;
    private EditText editName;
    private RadioButton radioSignMale;
    private RadioButton  radioSignFemale;
    private RadioButton  radioSignOther;
    private RadioGroup radioGroup;

    private LinearLayout layoutRecord;
    private LinearLayout layoutSuggest;
    private TextView textName;
    private TextView textPhone;
    private TextView textAge;
    private TextView textGender;
    private TextView textTz;
    private RecyclerView recyclerBisic1;
    private RecyclerView recyclerCon1;
    private RecyclerView recyclerSport1;
    private RecyclerView recyclerBisic2;
    private RecyclerView recyclerCon2;
    private RecyclerView recyclerSport2;
    private TextView textRecord;
    private TextView textSuggest;
    private TextView     textBisic0;
    private TextView     textCon0;
    private TextView     textSport0;
    private TextView     textBisic1;
    private TextView     textCon1;
    private TextView     textSport1;
    private TextView     textBisic2;
    private TextView     textCon2;
    private TextView     textSport2;
    private LinearLayout layoutBisic2;
    private LinearLayout layoutCon2;
    private LinearLayout layoutSport2;
    private LinearLayout layoutBisic0;
    private LinearLayout layoutCon0;
    private LinearLayout layoutSport0;
    private LinearLayout layoutSuggestAll;
    private LinearLayout layoutRecordAll;

    private FloatingActionButton fabSound;
    private FloatingActionButton fabDialog;

    private boolean isTwoPane;

    private String[] tz={"阳虚质","阴虚质","气虚质","痰湿质","血瘀质","湿热质","气郁质","特禀质","平和质"};
    private String[] tzChild={"脾虚质","积滞质","热滞质","湿滞质","心火旺质","异禀质","生机旺盛质"};
    private String[] tzWx={"金型体质","木型体质","水型体质","火型体质","土型体质"};
    private int [] res=new int[9]; //每种体质判别结果的标识
    private int [] score=new int[9];    //每种体质得分
    private int [] sum; //每种体质的累加得分
    private int [] num; //每种体质
    private int key; //体质子模块的key，为Ks中的值
    private int len;    //体质的总数
    private Double [] doubles=new Double[9]; //体质分数转换为double

    private int id=-1;  //服务器返回的记录id
    private int type=-1;    //区别成人、儿童、五型体质
    private String phone; //手机号
    private String [] suggestType={"9","11.2","10"};    //服务器模块标记

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tz_result);
        preWork();
        getData();
        setVisible();
        analyze();
        setRader();
        setSuggest();
    }



    public void disableRadioGroup(RadioGroup radioGroup) {  //是radioGroup中的单选按钮不可用
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(false);
        }
    }

    private void setSuggest(){   //获得体质建议
        if(key<0){
            buttonSave.setVisibility(View.INVISIBLE);
            buttonAnalyze.performClick();
        }
    }

    private void setRader(){   //更新雷达图
        if(key==Ks.TZ_CH_R || key==Ks.TZ_CH_W){
            radarView.setTextList(tzChild);
            radarView.setPercentList(doubles);
        }
        else if(key==Ks.TZ_WX_R || key==Ks.TZ_WX_W){
            radarView.setTextList(tzWx);
            radarView.setPercentList(doubles);
        }
        else{
            radarView.setTextList(tz);
            radarView.setPercentList(doubles);
        }

        radarView.setNgonColor(getResources().getColor(R.color.colorChooseNothingOn));
        radarView.setLineColor(getResources().getColor(R.color.colorChooseNothingOn));
        radarView.setShadowColor(getResources().getColor(R.color.colorBGTitle));
        radarView.setShadowAlpha(70);
        radarView.setTextColor(getResources().getColor(R.color.colorTextBlack));
        radarView.setTextSize(18f);
        radarView.setNgonSize(0.5f);
    }

    private void analyze(){  //根据传入的模块值，进行相应模块的体质分析
        if(key==Ks.TZ_OM_W || key==Ks.TZ_OW_W || key==Ks.TZ_OM_R || key==Ks.TZ_OW_R || key==Ks.TZ_OK_R || key==Ks.TZ_OK_W || key==Ks.TZ_AD_W || key==Ks.TZ_AD_R){
            doNewAnalyze(sum,17,11,9,tz);
        }
        else if(key==Ks.TZ_YK_W || key==Ks.TZ_YK_R || key==Ks.TZ_YM_W || key==Ks.TZ_YM_R || key==Ks.TZ_YW_W || key==Ks.TZ_YW_R){
            doNewAnalyze(score,60,30,40,tz);
        }
        else if(key==Ks.TZ_CH_R || key==Ks.TZ_CH_W){
            doNewAnalyze(score,60,30,50,tzChild);
        }
        else if(key==Ks.TZ_WX_W || key==Ks.TZ_WX_R){
            doWxAnalyze(score,40,60,tzWx);
        }

    }

    private void doWxAnalyze(int [] score,int sOther1,int sOther2,String [] tz){   //进行五型体质分析
        //sMain为正常体质，1-2,分数变高
        int len=score.length;
        int maxS=0;
        int index=-1;

        for(int i=0;i<len;i++){
            if(maxS<=score[i]){
                maxS=Math.max(maxS,score[i]);
                index=i;
            }
            if(score[i]>sOther2){
                res[i]=1;
            }else if(score[i]>=40){
                res[i]=2;
            }else{
                res[i]=0;
            }
        }
        String r="";
        if(index==-1){
            return;
        }
        if(res[index]==0){
        }else if(res[index]==2){
            r+="您是"+tz[index]+"。";
        }else{
            r+="您是"+tz[index]+"。";
//            int index2=-1;
//            int maxS2=0;
//            for(int j=0;j<len;j++){
//                if(j!=index){
//                    if(maxS2<score[j]){
//                        maxS2=score[j];
//                        index2=j;
//                    }
//                }
//            }
//            if(res[index2]==1){
//                r+="您还兼有"+tz[index2]+"。";
//            }
        }
        textRes.setText(r);
    }

    private void doNewAnalyze(int [] score,int sMain1,int sOther1,int sOther2,String [] tz){   //进行非五型体质分析
        //sMain为正常体质，1-2,分数变高
        int len=score.length;
        int maxS=0;
        int index=-1;

        for(int i=0;i<len;i++){
            if(i!=len-1){
                if(maxS<score[i]){
                    maxS=Math.max(maxS,score[i]);
                    index=i;
                }
            }
        }
        if(score[len-1]>=sMain1){
            if(maxS<sOther1){
                res[len-1]=1;
            }else if(maxS<sOther2){
                res[len-1]=2;
            }else{
                res[len-1]=0;
            }
        }else {
            if(maxS<sOther1){
                res[len-1]=2;
            }else{
                res[len-1]=0;
            }
        }

        for(int i=0;i<len-1;i++){
            if(score[i]>=sOther2){
                res[i]=1;
            }else if(score[i]>=sOther1){
                res[i]=2;
            }else{
                res[i]=0;
            }
        }
        String r="";
        if(res[len-1]!=0){
            if(res[len-1]==1){
                r+="您是"+tz[len-1]+"。";
            }else{
                r+="您基本是"+tz[len-1]+"。";
            }
            if(res[index]==1){
                r+="您还兼有"+tz[index]+"。";
            }
        }else{
            if(res[index]==2){
                r+="您倾向是"+tz[index]+"。";
            }else{
                r+="您是"+tz[index]+"。";
                int maxS2=0;
                int index2=-1;
                for(int j=0;j<len-1;j++){
                    if(j!=index){
                        if(maxS2<score[j]){
                            maxS2=score[j];
                            index2=j;
                        }
                    }
                }
                if(res[index2]==1){
                    r+="您还兼有"+tz[index2]+"。";
                }
            }
        }
//        Toast.makeText(TzResultActivity.this,r,Toast.LENGTH_SHORT).show();
        textRes.setText(r);
    }

//    private String getS(){
//        return ""+score[0]+" "+score[1]+" "+score[2]+" "+score[3]+" "+score[4]+" "+score[5]+" "+score[6]+" "+score[7]+" "+score[8]+" ";
//    }

    private void getData(){   //获得体质记录的数据，并进行预处理
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        isTwoPane=bundle.getBoolean("isTwoPane");
        key=bundle.getInt("key");
        sum=bundle.getIntArray("sum");
        num=bundle.getIntArray("num");
        len=num.length;

        if(key<0){
            id=bundle.getInt("id");
            editAge.setText(""+bundle.getInt("age"));
            editName.setText(bundle.getString("name"));
            editPhone.setText(bundle.getString("phone"));
            phone=bundle.getString("phone");

            int gender=bundle.getInt("gender");
            if(gender==0){
                radioSignMale.setChecked(true);
            }else if(gender==1){
                radioSignFemale.setChecked(true);
            }else{
                radioSignOther.setChecked(true);
            }

            disableRadioGroup(radioGroup);
            editAge.setFocusable(false);
            editName.setFocusable(false);
            editPhone.setFocusable(false);
            editAge.setEnabled(false);
            editName.setEnabled(false);
            editPhone.setEnabled(false);
            buttonSave.setEnabled(false);
            buttonSave.setClickable(false);
            buttonSave.setVisibility(View.INVISIBLE);
        }else{
            if(AccountKeeper.isOnline()){
                editPhone.setText(AccountKeeper.getLastPhone());
                editAge.setText(""+AccountKeeper.getAccount().getAge());
                editName.setText(AccountKeeper.getAccount().getName());
            }
            buttonAnalyze.setEnabled(false);
            buttonAnalyze.setClickable(false);
            setGender();
        }

        for(int i=0;i<len;i++){
            //从网上读的年轻男女，儿童，五型就是转换分
            if(key== Ks.TZ_YM_R || key==Ks.TZ_YW_R || key==Ks.TZ_CH_R ||key==Ks.TZ_WX_R || key==Ks.TZ_YK_R){
                score[i]=sum[i];
            }
            else if(key==Ks.TZ_WX_W){
                score[i]=100*sum[i]/num[i];
            }
            else{
                score[i]=100* (sum[i]-num[i]) / (num[i]*4);
            }

            doubles[i]=new Double((double)score[i]/100);
        }


    }

    private void setGender(){   //设置性别
        if(key==Ks.TZ_OM_W || key==Ks.TZ_YM_W){
            radioSignMale.setChecked(true);
            disableRadioGroup(radioGroup);
        }else if(key==Ks.TZ_YM_W || key==Ks.TZ_YW_W){
            radioSignFemale.setChecked(true);
            disableRadioGroup(radioGroup);
        }else if(key==Ks.TZ_OK_W || key==Ks.TZ_YK_W || key==Ks.TZ_AD_W){
            radioSignOther.setChecked(true);
            disableRadioGroup(radioGroup);
        }
    }

    private void setVisible(){   //设置子布局的外观

        layoutSuggest.setVisibility(View.INVISIBLE);
        if(isTwoPane){
            layoutSuggest.setVisibility(View.INVISIBLE);
            layoutRecord.setVisibility(View.VISIBLE);
            layoutBisic2.setVisibility(View.GONE);
            layoutCon2.setVisibility(View.GONE);
            layoutSport2.setVisibility(View.GONE);
//            buttonBack.setEnabled(false);
//            buttonBack.setClickable(false);
//            buttonBack.setVisibility(View.GONE);
        }else{
            layoutSuggest.setVisibility(View.GONE);
            layoutRecord.setVisibility(View.VISIBLE);
            layoutBisic2.setVisibility(View.GONE);
            layoutCon2.setVisibility(View.GONE);
            layoutSport2.setVisibility(View.GONE);
        }

    }

    private void doAnalyze(){   //进行分析
        if(id==-1){
            Toast.makeText(TzResultActivity.this,"请先保存检测记录",Toast.LENGTH_SHORT);
            return ;
        }
        if(key==Ks.TZ_CH_R || key==Ks.TZ_CH_W){
            doRecordAnalyze(1);
            type=1;
        }
        else if(key==Ks.TZ_WX_W || key==Ks.TZ_WX_R){
            doRecordAnalyze(2);
            type=2;
        }
        else{
            doRecordAnalyze(0);
            type=0;
        }
    }

    private void doRecordAnalyze(int type){   //访问服务器，以获得体质记录的建议
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("requestType",suggestType[type])
                            .add("name","")
                            .add("phone","")
                            .add("id",""+id)
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
                    getSuggestResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getSuggestResponse(String s){  //获得服务器返回的体质建议
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getSuggestWithGSON(s);
            }
        });
    }

    private void getSuggestWithGSON(String s){   //解析服务器返回的体质建议
        Gson gson=new Gson();
        TzRecordFirstResult tzRecordFirstResult =gson.fromJson(s, TzRecordFirstResult.class);
        if(tzRecordFirstResult.getRes().equals("1001")){
            List<Bisic> listB;
            List<Bisic> listC;
            List<Bisic> listS;
            String sTz;
            if(type==2){
                TzWxSuggestResult tzSuggestResult=gson.fromJson(s,TzWxSuggestResult.class);
                textName.setText("姓名："+tzSuggestResult.getRec().getName());
                textPhone.setText("电话："+phone);
                textAge.setText("年龄："+tzSuggestResult.getRec().getAge());
                String sex;
                if(tzSuggestResult.getRec().getGender()==0){
                    sex="男";
                }else if(tzSuggestResult.getRec().getGender()==1){
                    sex="女";
                }else{
                    sex="通用";
                }
                textGender.setText("性别："+sex);
                sTz=tzSuggestResult.getRec().getTizhi_end();
                textTz.setText("体质状况："+sTz);
                listB=tzSuggestResult.getRec().getWxBisic();
                listC=tzSuggestResult.getRec().getWxConditioning();
                listS=tzSuggestResult.getRec().getWxSports();

            }else{
                TzSuggestResult tzSuggestResult=gson.fromJson(s,TzSuggestResult.class);
                textName.setText("姓名："+tzSuggestResult.getRec().getName());
                textPhone.setText("电话："+phone);
                textAge.setText("年龄："+tzSuggestResult.getRec().getAge());
                String sex;
                if(tzSuggestResult.getRec().getGender()==0){
                    sex="男";
                }else if(tzSuggestResult.getRec().getGender()==1){
                    sex="女";
                }else{
                    sex="通用";
                }
                textGender.setText("性别："+sex);
                sTz=tzSuggestResult.getRec().getTizhi_end();
                textTz.setText("体质状况："+sTz);
                listB=tzSuggestResult.getRec().getZyBisic();
                listC=tzSuggestResult.getRec().getZyConditioning();
                listS=tzSuggestResult.getRec().getZySports();
            }

            setSuggestAdapter(sTz,listB,listC,listS);

            if(isTwoPane){
                layoutSuggest.setVisibility(View.VISIBLE);
            }else{
//                layoutRecord.setVisibility(View.GONE);
                layoutSuggest.setVisibility(View.VISIBLE);
            }

        }else{
            Toast.makeText(TzResultActivity.this,"获取建议失败",Toast.LENGTH_SHORT).show();
        }
    }

    private void setSuggestAdapter(String s,List<Bisic> listB,List<Bisic> listC,List<Bisic> listS){  //更新体质建议的适配器，展示数据

        LinearLayoutManager layoutManager1=new LinearLayoutManager(this);
        LinearLayoutManager layoutManager2=new LinearLayoutManager(this);
        LinearLayoutManager layoutManager3=new LinearLayoutManager(this);
        if(s.contains("兼") || s.contains("倾")){
            String s1,s2;
            s=s.trim();
            int index;
            if(s.contains("兼")){
                index=s.indexOf("兼有");
                if(index!=-1){
                    s1=s.substring(0,index);
                    s2=s.substring(index+2);
                }else{
                    index=s.indexOf("兼");
                    s1=s.substring(0,index);
                    s2=s.substring(index+1);
                }
            }else{
                index=s.indexOf("倾向于");
                if(index!=-1){
                    s1=s.substring(0,index);
                    s2=s.substring(index+3);
                }else{
                    index=s.indexOf("倾向是");
                    if(index!=-1){
                        s1=s.substring(0,index);
                        s2=s.substring(index+3);
                    }else {
                        index=s.indexOf("倾向");
                        s1=s.substring(0,index);
                        s2=s.substring(index+2);
                    }

                }
            }

            layoutBisic2.setVisibility(View.VISIBLE);
            layoutCon2.setVisibility(View.VISIBLE);
            layoutSport2.setVisibility(View.VISIBLE);
            textBisic1.setText(s1);
            textBisic2.setText(s2);
            textCon1.setText(s1);
            textCon2.setText(s2);
            textSport1.setText(s1);
            textSport2.setText(s2);
            List<Bisic> listB1=new ArrayList<>();
            List<Bisic> listB2=new ArrayList<>();
            List<Bisic> listC1=new ArrayList<>();
            List<Bisic> listC2=new ArrayList<>();
            List<Bisic> listS1=new ArrayList<>();
            List<Bisic> listS2=new ArrayList<>();

            for(int i=0;i<listB.size();i++){
                if(i%2==0){
                    listB1.add(listB.get(i));
                }else{
                    listB2.add(listB.get(i));
                }
            }
            for(int i=0;i<listC.size();i++){
                if(i%2==0){
                    listC1.add(listC.get(i));
                }else{
                    listC2.add(listC.get(i));
                }
            }
            for(int i=0;i<listS.size();i++){
                if(i%2==0){
                    listS1.add(listS.get(i));
                }else{
                    listS2.add(listS.get(i));
                }
            }
            listB=listB1;
            listC=listC1;
            listS=listS1;
            LinearLayoutManager layoutManager12=new LinearLayoutManager(this);
            LinearLayoutManager layoutManager22=new LinearLayoutManager(this);
            LinearLayoutManager layoutManager32=new LinearLayoutManager(this);
            recyclerBisic2.setLayoutManager(layoutManager12);
            recyclerCon2.setLayoutManager(layoutManager22);
            recyclerSport2.setLayoutManager(layoutManager32);
            TzSuggestAdapter adapter12=new TzSuggestAdapter((listB2));
            TzSuggestAdapter adapter22=new TzSuggestAdapter((listC2));
            TzSuggestAdapter adapter32=new TzSuggestAdapter((listS2));
            recyclerBisic2.setAdapter(adapter12);
            recyclerCon2.setAdapter(adapter22);
            recyclerSport2.setAdapter(adapter32);
        }else{
            textBisic1.setText(s);
            textCon1.setText(s);
            textSport1.setText(s);

        }

        recyclerBisic1.setLayoutManager(layoutManager1);
          recyclerCon1.setLayoutManager(layoutManager2);
        recyclerSport1.setLayoutManager(layoutManager3);
        TzSuggestAdapter adapter1=new TzSuggestAdapter((listB));
        TzSuggestAdapter adapter2=new TzSuggestAdapter((listC));
        TzSuggestAdapter adapter3=new TzSuggestAdapter((listS));
        recyclerBisic1.setAdapter(adapter1);
        recyclerCon1.setAdapter(adapter2);
        recyclerSport1.setAdapter(adapter3);
    }

    private void showWarningDialog(String s){  //显示提示对话框
        AlertDialog.Builder dialog;
        dialog=new AlertDialog.Builder(TzResultActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("提示");
        dialog.setMessage(s);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    private void preWork(){
        radarView=findViewById(R.id.radar_tz_result);
        textRes=findViewById(R.id.text_tz_res);
        buttonAnalyze=findViewById(R.id.button_result_analyze);
        buttonSave=findViewById(R.id.button_result_save);
        editAge=findViewById(R.id.edit_age);
        editPhone=findViewById(R.id.edit_phone);
        editName=findViewById(R.id.edit_name);
        radioSignFemale=findViewById(R.id.radio_sign_female);
        radioSignMale=findViewById(R.id.radio_sign_male);
        radioSignOther=findViewById(R.id.radio_sign_other);
        layoutInfo=findViewById(R.id.layout_tz_info);
        radioGroup=findViewById(R.id.radio_sign_sex);
        textName=findViewById(R.id.suggest_name);
        textPhone=findViewById(R.id.suggest_phone);
        textAge=findViewById(R.id.suggest_age);
        textGender=findViewById(R.id.suggest_gender);
        textTz=findViewById(R.id.suggest_tz);
//        buttonBack=findViewById(R.id.suggest_back);
        recyclerBisic1=findViewById(R.id.recycler_bisic);
        recyclerCon1=findViewById(R.id.recycler_conditioning);
        recyclerSport1=findViewById(R.id.recycler_sports);
        recyclerBisic2=findViewById(R.id.recycler_bisic_other);
        recyclerCon2=findViewById(R.id.recycler_conditioning_other);
        recyclerSport2=findViewById(R.id.recycler_sports_other);
        textBisic0 =findViewById(R.id.text_bisic_all);
        textCon0   =findViewById(R.id.text_condition_all);
        textSport0 =findViewById(R.id.text_sports_all);
        textBisic1 =findViewById(R.id.text_bisic_1);
        textCon1   =findViewById(R.id.text_condition_1);
        textSport1 =findViewById(R.id.text_sports_1);
        textBisic2 =findViewById(R.id.text_bisic_2);
        textCon2   =findViewById(R.id.text_condition_2);
        textSport2 =findViewById(R.id.text_sports_2);
        textRecord=findViewById(R.id.record_title);
        textSuggest=findViewById(R.id.suggest_title);
        layoutRecord=findViewById(R.id.layout_tz_record);
        layoutSuggest=findViewById(R.id.layout_tz_suggest);
        layoutBisic2=findViewById( R.id.layout_bisic_other);
        layoutCon2=findViewById(R.id.layout_con_other);
        layoutSport2=findViewById(R.id.layout_sports_other);
        layoutBisic0=findViewById(R.id.layout_bisic_all);
        layoutCon0=findViewById(R.id.layout_con_all);
        layoutSport0=findViewById(R.id.layout_sports_all);
        layoutSuggestAll=findViewById(R.id.layout_suggest_all);
        fabDialog=findViewById(R.id.float_dialog);
        fabSound=findViewById(R.id.float_sound);

        fabSound.setOnClickListener(this);
        fabDialog.setOnClickListener(this);
        buttonAnalyze.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        textBisic0.setOnClickListener(this);
        textCon0.setOnClickListener(this);
        textSport0.setOnClickListener(this);
        textBisic1.setOnClickListener(this);
        textCon1.setOnClickListener(this);
        textSport1.setOnClickListener(this);
        textBisic2.setOnClickListener(this);
        textCon2.setOnClickListener(this);
        textSport2.setOnClickListener(this);
        textSuggest.setOnClickListener(this);
        textRecord.setOnClickListener(this);


    }

    private void saveRecord(){   //将测试完得到的体质检测记录进行预处理，以保存在服务器或本地
        if(key<0){
            return;
        }
        String name=editName.getText().toString().trim();
        String phone=editPhone.getText().toString().trim();
        String age=editAge.getText().toString().trim();
        if(name.equals("") && phone.equals("")){
            Toast.makeText(TzResultActivity.this,"姓名手机不可同时为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!phone.equals("")){
            if(!RegularUtils.isPhone(phone)){
                Toast.makeText(TzResultActivity.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(age.equals("")){
            Toast.makeText(TzResultActivity.this,"未填入年龄，将采用默认年龄",Toast.LENGTH_SHORT).show();
            if(key==Ks.TZ_OM_W || key==Ks.TZ_OW_W || key==Ks.TZ_OK_W){
                age="61";
            }else if(key==Ks.TZ_YM_W || key==Ks.TZ_YW_W || key==Ks.TZ_YK_W){
                age="30";
            }else if(key==Ks.TZ_CH_W){
                age="10";
            }
        }else{
            if(!RegularUtils.isNum(age)){
                Toast.makeText(TzResultActivity.this,"未填入正确年龄",Toast.LENGTH_SHORT).show();
                return;
            }
            int a=Integer.parseInt(age);
            boolean f=true;
            if(key==Ks.TZ_OM_W || key==Ks.TZ_OW_W || key==Ks.TZ_OK_W){
                if(a<60){
                    f=false;
                }
            }else if(key==Ks.TZ_YM_W || key==Ks.TZ_YW_W || key==Ks.TZ_YK_W){
                if(a>60){
                    f=false;
                }
            }else if(key==Ks.TZ_CH_W){
                if(a>12){
                    f=false;
                }
            }
            if(!f){
                Toast.makeText(TzResultActivity.this,"填入年龄与测试模块年龄范围不符",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        String gender;
        if(key==Ks.TZ_CH_W){
            if(radioSignMale.isChecked()){
                gender="0";
            }else if(radioSignFemale.isChecked()){
                gender="1";
            }else{
                gender="2";
            }
            doChildRecordSave(name,phone,gender,age,score);
        }
        else if(key==Ks.TZ_WX_W){
            if(radioSignMale.isChecked()){
                gender="0";
            }else if(radioSignFemale.isChecked()){
                gender="1";
            }else{
                gender="2";
            }
            doWxRecordSave(name,phone,gender,age,score);
        }
        else{
            if(key==Ks.TZ_OM_W || key==Ks.TZ_YM_W){
                gender="0";
            }else if(key==Ks.TZ_OW_W || key==Ks.TZ_YW_W){
                gender="1";
            }else{
                gender="2";
            }
            if(key==Ks.TZ_YM_W || key==Ks.TZ_YW_W || key==Ks.TZ_YK_W){
                doAdultRecordSave(name,phone,gender,age,score);
            }else{
                doAdultRecordSave(name,phone,gender,age,sum);
            }

        }
        this.phone=phone;
    }

    private void doWxRecordSave(String name,String phone,String gender,String age,int [] score){  //将五型体质记录保存到服务器上
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("requestType","6")
                            .add("name",name)
                            .add("phone",phone)
                            .add("gender",gender)
                            .add("age",age)
                            .add("jin",  ""+score[0])
                            .add("mu",    ""+score[1])
                            .add("shui",  ""+score[2])
                            .add("huo",   ""+score[3])
                            .add("tu",  ""+score[4])
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
                    showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void doChildRecordSave(String name,String phone,String gender,String age,int [] score){  //将儿童体质记录保存到服务器上
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("requestType","11")
                            .add("name",name)
                            .add("phone",phone)
                            .add("gender",gender)
                            .add("age",age)
                            .add("pixu",  ""+score[0])
                            .add("jizhi",    ""+score[1])
                            .add("rezhi",  ""+score[2])
                            .add("shizhi",   ""+score[3])
                            .add("xinhuo",  ""+score[4])
                            .add("yibing",   ""+score[5])
                            .add("shengjie",   ""+score[6])
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
                    showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void doAdultRecordSave(String name,String phone,String gender,String age,int [] score){  //将成人体质记录保存到服务器上
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("requestType","4")
                            .add("name",name)
                            .add("phone",phone)
                            .add("gender",gender)
                            .add("age",age)
                            .add("pinghe",  ""+score[8])
                            .add("qixu",    ""+score[2])
                            .add("yangxu",  ""+score[0])
                            .add("yinxu",   ""+score[1])
                            .add("tanshi",  ""+score[3])
                            .add("shire",   ""+score[5])
                            .add("xueyu",   ""+score[4])
                            .add("qiyu",    ""+score[6])
                            .add("tebing",  ""+score[7])
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
                    showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response){   //获得保存体质记录的返回结果
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parseJSONWithGSON(response);
            }
        });
    }

    private void parseJSONWithGSON(String jsonData){   //解析服务器返回的体质记录保存结果
        Gson gson=new Gson();
        TzRecordFirstResult tzRecordFirstResult =gson.fromJson(jsonData, TzRecordFirstResult.class);
        if(tzRecordFirstResult.getRes().equals("1001")){
            TzRecordSaveResult tzRecordSaveResult =gson.fromJson(jsonData, TzRecordSaveResult.class);
            id= tzRecordSaveResult.getRec().getId();
            buttonAnalyze.setEnabled(true);
            buttonAnalyze.setClickable(true);
            buttonAnalyze.performClick();
            Toast.makeText(TzResultActivity.this, tzRecordFirstResult.getMsg(),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(TzResultActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
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
            case R.id.button_result_analyze:
                doAnalyze();
                break;
            case R.id.button_result_save:
                saveRecord();
                break;
            case R.id.text_bisic_all:
                changeVis(layoutBisic0);
                break;
            case R.id.text_condition_all:
                changeVis(layoutCon0);
                break;
            case R.id.text_sports_all:
                changeVis(layoutSport0);
                break;
            case R.id.text_bisic_1:
                changeVis(recyclerBisic1);
                break;
            case R.id.text_condition_1:
                changeVis(recyclerCon1);
                break;
            case R.id.text_sports_1:
                changeVis(recyclerSport1);
                break;
            case R.id.text_bisic_2:
                changeVis(recyclerBisic2);
                break;
            case R.id.text_condition_2:
                changeVis(recyclerCon2);
                break;
            case R.id.text_sports_2:
                changeVis(recyclerSport2);
                break;
            case R.id.suggest_title:
                if(!isTwoPane) {
                    changeVis(layoutSuggestAll);
                }
                break;
            case R.id.record_title:
                if(!isTwoPane) {
                    changeVis(layoutRecord);
                }
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

    private void changeVis(View v){   //更改控件的可见性
        if(v.getVisibility()==View.GONE){
            v.setVisibility(View.VISIBLE);
        }else{
            v.setVisibility(View.GONE);
        }
    }

    public class TzSuggestAdapter extends RecyclerView.Adapter<TzSuggestAdapter.ViewHolder> {  //体质建议适配器

        private List<Bisic> mList;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView textName;
            TextView textContent;

            public ViewHolder(View view){
                super(view);
                textName=view.findViewById(R.id.suggest_title);
                textContent=view.findViewById(R.id.suggest_content);
            }
        }

        public TzSuggestAdapter(List<Bisic> list){
            mList=list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tz_suggest_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Bisic body=mList.get(position);
            holder.textName.setText(body.getName());
            holder.textContent.setText(body.getContent());
            holder.textName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.textContent.getVisibility()==View.GONE){
                        holder.textContent.setVisibility(View.VISIBLE);
                    }else{
                        holder.textContent.setVisibility(View.GONE);
                    }
                }
            });
            holder.textContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TTSUtility.doReport(holder.textContent.getText().toString().trim());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
