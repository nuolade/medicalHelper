package com.example.wenda01.activities.tizhi;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.beans.tz.TzOneQuestion;
import com.example.wenda01.beans.tz.TzOneIntorduction;
import com.example.wenda01.utils.ExcelReader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TzActivity extends BaseSpeakActivity {  //体质主活动

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tz);
        isF();
    }

    private void isF(){   //判断是否是首次打开该模块，从而加载体质数据
        SharedPreferences sharedPreferences=getSharedPreferences("isFirst",MODE_PRIVATE);
        boolean isFirst=sharedPreferences.getBoolean("isFirstTZ",true);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(isFirst){
            loadData();
            editor.putBoolean("isFirstTZ",false);
            editor.commit();
        }
    }

    private void loadData(){   //加载全部体质数据
        addData();
//        addData2();
        addData3();
        addData4();
    }

    private void addData(){   //加载单项体质数据
        ArrayList<String> list=new ArrayList<>();
        AssetManager am=getAssets();
        try{
            InputStream is=am.open("tzOne.txt");
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            String str="";
            while((str=br.readLine())!=null){
                list.add(str);
            }
        }catch (Exception e){

        }

        for(int i=0;i<list.size();i++){
            String [] items=list.get(i).split(" ");
            TzOneQuestion tzOneQuestion =new TzOneQuestion(items[2],Integer.parseInt(items[0]),Integer.parseInt(items[1]));
            tzOneQuestion.save();
        }
    }

//    private void addData2(){
//        ArrayList<String> list=new ArrayList<>();
//        AssetManager am=getAssets();
//        try{
//            InputStream is=am.open("tztotalq.txt");
//            InputStreamReader isr=new InputStreamReader(is,"utf-8");
//            BufferedReader br=new BufferedReader(isr);
//            String str="";
//            while((str=br.readLine())!=null){
//                list.add(str);
//            }
//        }catch (Exception e){
//
//        }
//        for(int i=0;i<list.size();i++){
//            String [] items=list.get(i).split(" ");
//            TzAllQuestion questionAll=new TzAllQuestion(items[5],Integer.parseInt(items[0]),Integer.parseInt(items[1]),Integer.parseInt(items[2]),Integer.parseInt(items[3]),Integer.parseInt(items[4]));
//            questionAll.save();
//        }
//    }

    private void addData3(){   //加载单项体质数据
        ArrayList<String> list=new ArrayList<>();
        AssetManager am=getAssets();
        try{
            InputStream is=am.open("tzOneIntor.txt");
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            String str="";
            while((str=br.readLine())!=null){
                list.add(str);
            }
        }catch (Exception e){

        }
        for(int i=0;i<list.size();i++){
            String [] items=list.get(i).split(" ");
            TzOneIntorduction tzOneIntorduction =new TzOneIntorduction(Integer.parseInt(items[0]),items[1]);
            tzOneIntorduction.save();
        }
    }

    private void addData4(){   //加载儿童、成人常规、成人快捷体质数据
        ExcelReader.readExcelTzChild(this,"tzChild.xls");
        ExcelReader.readExcelTzAdult(this,"tzAdult.xls");
        ExcelReader.readExcelTzKey(this,"tzKey.xls");
    }
}
